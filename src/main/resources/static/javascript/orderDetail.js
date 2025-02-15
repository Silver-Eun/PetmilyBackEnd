"use strict"

// OrderDetail(주문상세내역)
function orderDetailList() {
	let url = "cart/orderDetail";
	axios.get(url
	).then(response => {
		document.getElementById('resultArea2').innerHTML = response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});

	document.getElementById('resultArea2').innerHTML = "";
}

// OrderDetailInsert(주문상세 추가페이지)
function orderDetailInsertf() {
	let url = "cart/orderDetailInsert";
	axios.get(url
	).then(response => {
		document.getElementById('resultArea2').innerHTML = response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});

	document.getElementById('resultArea2').innerHTML = "";
}

// OrderDetailInsert(주문상세 추가페이지) Post
function orderDetailInsert() {
	// 1) Data 준비
	// => JS의 내장객체 FormData에 담아서 전송
	let formData = new FormData(document.getElementById('orderdetailform'));
	
	// axios 요청
	let url="/api/cart/orderDetailInsertP";
	
	axios.post(url, formData,
				{headers:{"Content-Type":"multipart/form-data"}
	}).then( response => {
				alert(`response.data : ${response.data}`);
				// 주문상세내역 리스트로 이동
				orderDetailList();
	}).catch( err => {
				if ( err.response.status === 502 ) alert("입력 오류");
				else alert("시스템 오류 : " + err.message);
	});
	
	document.getElementById('resultArea2').innerHTML="";
}

// OrderDetailDetail(주문상세 세부내역)
function odDetail(order_detail_key) {
	let url = "cart/oddetail?jCode=U&order_detail_key=" + order_detail_key;
	axios.get(url
	).then(response => {
		document.getElementById('resultArea2').innerHTML = response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});

	document.getElementById('resultArea2').innerHTML = "";
}

// OrderDetailUpdate(주문상세내역 추가페이지) Post
function orderDetailUpdate() {
	// 1) Data 준비
	// => JS의 내장객체 FormData에 담아서 전송
	let formData = new FormData(document.getElementById('orderdetailUpdateform'));
	
	// axios 요청
	let url="/api/cart/orderDetailUpdateP";
	
	axios.post(url, formData,
				{headers:{"Content-Type":"multipart/form-data"}
	}).then( response => {
				alert(`${response.data}`);
				// 주문상세내역 리스트로 이동
				orderDetailList();
	}).catch( err => {
				if ( err.response.status===502 ) alert("입력 오류");
				else alert("시스템 오류 : " + err.message);
	});
	
	document.getElementById('resultArea2').innerHTML="";
}

// delete(주문상세 삭제)
function odDelete(order_key) {
	let url = "/api/cart/oddelete/" + order_key;
	axios.delete(url).then(response => {
		alert(response.data);
		// 주문상세내역 리스트로 이동
		orderDetailList();
	}).catch(err => {
		if (err.response.status === 502) alert(err.response.data);
		else alert("시스템 오류 : " + err.message);
	});
}

// pagination
function orderDetailPageList(a) {
	let url = "/api/cart/orderDetail" + a;
	axios.get(url
	).then(response => {
		document.getElementById('resultArea2').innerHTML = response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});

	document.getElementById('resultArea2').innerHTML = "";
}