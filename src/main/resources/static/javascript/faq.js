"use strict"

// ) Delete faq and reload list page
function faqDelete(id) {
	let url = "faq/delete/" + id;

	if (confirm("삭제하시겠습니까?")) {
		axios.delete(
			url
		).then(response => {
			alert(`자주묻는질문이 삭제 완료되었습니다.`);
			faqPagingList('board/faqPagingList'); // 목록 새로고침
		}).catch(error => {
			console.error(`에러 응답 = ${error.response},
			error status = ${error.response.status},
			error message = ${error.message}`);
		});
	}
}

// 2) Detail Faq
function faqDetail(id) {
	const popup = window.open('board/faqDetail?faq_id=' + id, 'popup',
		'width=600, height=400');

	const checkPopupClosed = setInterval(function () {
		if (popup.closed) {
			clearInterval(checkPopupClosed);
			faqPagingList('board/faqPagingList'); // 목록 새로고침
		}
	}, 1000); // 1초마다 확인
}

function updateFaq(id) {
	let url = "/faq/update";

	if (confirm("수정하시겠습니까?")) {
		axios({
			url: url,
			method: 'PUT',
			headers: { 'Content-Type': 'application/json' },
			data: {
				faq_id: id,
				faq_title: document.getElementById('faq_title').value,
				question_type: document.getElementById('question_type').value,
				faq_content: document.getElementById('faq_content').value,
				faq_regdate: document.getElementById('faq_regdate').value
			}

		}).then(response => {
			alert(`자주묻는질문 수정 완료되었습니다.`);
			window.close();
			faqPagingList('/board/faqPagingList'); // 목록 새로고침
		}).catch(error => {
			console.error(`에러 응답 = ${error.response},
			error status = ${error.response.status},
			error message = ${error.message}`);
		});
	}
}





// 4) Insert Faq Form
function faqInsertForm() {
	const popup = window.open('board/faqInsertForm', 'popup',
		'width=600, height=400');

	const checkPopupClosed = setInterval(function () {
		if (popup.closed) {
			clearInterval(checkPopupClosed);
			faqPagingList('board/faqPagingList'); // 목록 새로고침
		}
	}, 1000); // 1초마다 확인
}


// 5) Insert Faq
function faqInsert() {
	let url = "/faq/insert";

	if (confirm("작성하시겠습니까?")) {
		const questionType = document.getElementById('question_type').value;
		console.log(`선택한 글분류: ${questionType}`); // 디버깅

		axios({
			url: url,
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			data: {
				faq_title: document.getElementById('faq_title').value,
				question_type: questionType,
				faq_content: document.getElementById('faq_content').value
			}
		}).then(response => {
			alert(`자주묻는질문 작성이 완료되었습니다.`);
			window.close();
			faqPagingList('board/faqPagingList'); // 목록 새로고침
		}).catch(error => {
			console.error(`에러 응답 = ${error.response},
            error status = ${error.response.status},
            error message = ${error.message}`);
		});
	}
}

// ) Show Pagination Faq List
function faqPagingList(url) {
	axios.get(
		url
	).then(response => {
		document.getElementById('pTableListArea').innerHTML = response.data;
	}).catch(error => {
		alert("error message :" + error.message);
	})
}