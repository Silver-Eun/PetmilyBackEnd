"use strict"

// 프로모션 상품 수정
function pmpUpdate(id) {
	let url = "product/pmpUpdateForm/" + id;
	
	axios.get(
		url
	).then(response => {
		document.getElementById('resultArea1').innerHTML=response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});
	
	document.getElementById('insertTableArea').innerHTML="";
} // pmpUpdate


// 상품 수정
function pUpdate(id) {
	let url = "product/pUpdateForm/" + id;
	
	axios.get(
		url
	).then(response => {
		document.getElementById('resultArea1').innerHTML=response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});
	
	document.getElementById('insertTableArea').innerHTML="";
} // pUpdate


// 상품 이미지 수정
function piUpdate(id) {

	let url = "product/piUpdateForm/" + id;
	
	axios.get(
		url
	).then(response => {
		document.getElementById('resultArea1').innerHTML=response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});
	
	document.getElementById('insertTableArea').innerHTML="";
} // piUpdate


// 상품 이미지 수정
function eUpdate(id) {

	let url = "product/eUpdateForm/" + id;
	
	axios.get(
		url
	).then(response => {
		document.getElementById('resultArea1').innerHTML=response.data;
	}).catch(err => {
		alert("response 실패 : " + err.message);
	});
	
	document.getElementById('insertTableArea').innerHTML="";
} // eUpdate