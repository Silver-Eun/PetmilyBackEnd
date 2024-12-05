"use strict"

// CartForm(장바구니, 주문, 주문상세)
function cartForm() {
	let resultHtml =
	`<h3>장바구니 주문 주문상세 테이블 관리</h3>
	<table id="subTable">
		<tr>
			<th><a onclick="cartInsertf()">장바구니추가</a></th>
			<th><a onclick="cartList()">장바구니목록</a></th>
			<th><a onclick="orderProductInsertf()">주문추가</a></th>
			<th><a onclick="orderList()">주문목록</a></th>
			<th><a onclick="orderDetailInsertf()">주문상세추가</a></th>
			<th><a onclick="orderDetailList()">주문상세목록</a></th>
		</tr>
	</table>
	`;
	document.getElementById('newPage').innerHTML = "";
	document.getElementById('pTableListArea').innerHTML = "";
	document.getElementById('insertTableArea').innerHTML = "";
	document.getElementById('resultArea1').innerHTML = resultHtml;
	document.getElementById('resultArea2').innerHTML = "";
}

// CartList(장바구니)
function cartList() {
	let url = "cart/cartList";
	axios.get(url
	).then(response => {
		document.getElementById('resultArea2').innerHTML = response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});

	document.getElementById('resultArea2').innerHTML = "";
}

// CartInsert(장바구니 추가페이지) Get
function cartInsertf() {
	let url = "cart/cartInsert";
	axios.get(url
	).then(response => {
		document.getElementById('resultArea2').innerHTML = response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});

	document.getElementById('resultArea2').innerHTML = "";
}

// CartInsert(장바구니 추가페이지) Post
function cartInsert() {
	// 1) Data 준비
	// => JS의 내장객체 FormData에 담아서 전송
	let formData = new FormData(document.getElementById('cartform'));
	
	// axios 요청
	let url="/api/cart/cartInsertP";
	
	axios.post(url, formData,
				{headers:{"Content-Type":"multipart/form-data"}
	}).then( response => {
				alert("장바구니 추가 완료");
				// 장바구니 리스트로 이동
				cartList();
	}).catch( err => {
				if ( err.response.status === 502 ) alert("입력 오류");
				else alert("시스템 오류 : => " + err.message);
	});
	
	document.getElementById('resultArea2').innerHTML="";
}

// cartDelete(장바구니 삭제)
function cDelete(user_id, product_id) {
	let url = "/api/cart/cdelete/" + user_id + "/" + product_id;
	axios.delete(url).then(response => {
		alert(response.data);
		// 장바구니 리스트로 이동
		cartList();
	}).catch(err => {
		if (err.response.status) alert(err.response.data);
		else alert("시스템 오류 : => " + err.message);
	});
}

// pagination
function cartPageList(a) {
	let url = "/api/cart/cartList" + a;
	axios.get(url
	).then(response => {
		document.getElementById('resultArea2').innerHTML = response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});

	document.getElementById('resultArea2').innerHTML = "";
}