"use strict"

// 1) Delete review and reload list page
function reviewDelete(id) {
	let url = "/api/review/delete/" + id;

	if (confirm("삭제하시겠습니까?")) {
		axios.delete(
			url
		).then(response => {
			alert(`상품후기가 삭제 완료되었습니다.`);
			// 목록 새로고침
			reviewPagingList('board/reviewPagingList');
		}).catch(error => {
			console.error(`에러 응답 = ${error.response},
			error status = ${error.response.status},
			error message = ${error.message}`);
		});
	}
}

// 2) Detail Review
function reviewDetail(id) {
	const popup = window.open('board/reviewDetail?review_id=' + id, 'popup',
		'width=600, height=600');

	const checkPopupClosed = setInterval(function () {
		if (popup.closed) {
			clearInterval(checkPopupClosed);
			reviewPagingList('board/reviewPagingList'); // 목록 새로고침
		}
	}, 1000); // 1초마다 확인
}

// 3) Update Review
function updateReview() {
	let formData = new FormData(document.getElementById('updateReviewForm'));
	
	if (confirm("수정하시겠습니까?")) {
		axios.post(
			'/api/review/update',
			formData,
			{
				headers: { 'Content-Type': 'multipart/form-data' }
			}).then(response => {
				alert(`상품후기 수정 완료되었습니다.`);
				window.close();
				reviewPagingList('board/reviewPagingList'); // 목록 새로고침
			}).catch(error => {
				if (error.response.status === 502) alert("입력 오류")
				else alert("시스템 오류 : " + error.message);
			});
	}
}

// ) Show Pagination Review List
function reviewPagingList(url) {
	axios.get(
		url
	).then(response => {
		document.getElementById('pTableListArea').innerHTML = response.data;
	}).catch(error => {
		alert("error message :" + error.message);
	})
}