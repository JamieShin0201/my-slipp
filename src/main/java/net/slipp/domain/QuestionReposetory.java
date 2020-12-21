package net.slipp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionReposetory extends JpaRepository<Question, Long>{

}
