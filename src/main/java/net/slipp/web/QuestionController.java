package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionReposetory;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionedUser, title, contents);

		questionReposetory.save(newQuestion);

		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionReposetory.findById(id).get());

		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		try {
			Question question = questionReposetory.findById(id).get();
			
			hasPermission(session, question);
			model.addAttribute("question", question);
			
			return "/qna/updateForm";

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessege", e.getMessage());
			
			return "/user/login";
		}
	}

	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}

		return true;
	}

	@PutMapping("/{id}")
	public String show(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		try {
			Question question = questionReposetory.findById(id).get();
			
			hasPermission(session, question);
			
			question.update(title, contents);
			questionReposetory.save(question);

			return String.format("redirect:/questions/%d", id);

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessege", e.getMessage());
			
			return "/user/login";
		}
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		try {
			Question question = questionReposetory.findById(id).get();

			hasPermission(session, question);
			
			questionReposetory.deleteById(id);

			return "redirect:/";

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessege", e.getMessage());
			
			return "/user/login";
		}	
	}

}
