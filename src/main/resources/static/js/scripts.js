String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined'
			? args[number]
			: match
			;
	});
};

function onSuccess(data, status) {
	console.log(data);
	const answerTemplate = $("#answerTemplate").html();
	const template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);

	$(template).insertBefore(".answer-write");

	$(".answer-write textarea").val("");
}

function onError() {

}

function addAnswer(e) {
	e.preventDefault();

	const queryString = $(".answer-write").serialize();
	const url = $(".answer-write").attr("action");

	$.ajax({
		type: 'post',
		url: url,
		data: queryString,
		dataType: 'json',
		error: onError,
		success: onSuccess
	});
}

$(".answer-write > input[type=submit]").click(addAnswer);

$(document).on('click', '.link-delete-article', function(e) {
	e.preventDefault();
	
	const deleteBtn = $(this);
	const url = $(this).attr("href");
	
	$.ajax({
		type: 'delete',
		url: url,
		dataType: 'json',
		error: function(xhr, status) {
			console.log('error');
		},
		success: function(data, status) {
			console.log('success');
			if(data.valid) {
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});
	
});







