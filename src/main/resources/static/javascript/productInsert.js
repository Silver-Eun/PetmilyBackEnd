"use strict"

// 프로모션 상품 데이터 추가 테이블
function pmpInsertTable() {
	let url = "product/pmpInsertForm";
	
	axios.get(
		url
	).then(response => {
		document.getElementById('insertTableArea').innerHTML=response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});
} // pmpInsertTable

// 상품 데이터 추가 테이블
function pInsertTable() {
	let url = "product/pInsertForm";
	
	axios.get(
		url
	).then(response => {
		document.getElementById('insertTableArea').innerHTML=response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});
} // pInsertTable

// 상품 이미지 데이터 추가 테이블
function piInsertTable() {
	let url = "product/piInsertForm";
	
	axios.get(
		url
	).then(response => {
		document.getElementById('insertTableArea').innerHTML=response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});
} // piInsertTable

// 이벤트 데이터 추가 테이블
function eInsertTable() {
	let url = "product/eInsertForm";
	
	axios.get(
		url
	).then(response => {
		document.getElementById('insertTableArea').innerHTML=response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});
} // eInsertTable