"use strict"

function userForm() {
    const newPage = document.getElementById('newPage');
    const loginID = newPage.getAttribute('data-login-id');
    const loginName = newPage.getAttribute('data-login-name');

    let resultHtml = ``;

    if (loginName) {
        resultHtml += `=> ${loginName}님 안녕하세요<br>`;
    }

    resultHtml +=
        `
	<span class="textlink" onclick="Loginf()">Login</span>&nbsp;
	&nbsp;
	<span class="textlink" onclick="Joinf()">Join</span>&nbsp;
	&nbsp;
	<span class="textlink" onclick="UserList()">userList</span>&nbsp;
	`;

    // 로그인 상태일 경우, Logout 버튼을 UserList와 Home 사이에 추가
    if (loginID) {
        resultHtml += `&nbsp;<span class="textlink" onclick="userLogout()">Logout</span>&nbsp;`;
    }

    // 마지막에 Home 링크 추가
    resultHtml += `&nbsp;<a href="/home">[Home]</a>`;

    document.getElementById('newPage').innerHTML = resultHtml;
    document.getElementById('pTableListArea').innerHTML = "";
    document.getElementById('insertTableArea').innerHTML = "";
    document.getElementById('resultArea1').innerHTML = "";
    document.getElementById('resultArea2').innerHTML = "";
}