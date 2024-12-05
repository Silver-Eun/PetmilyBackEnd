"use strict"

function userForm() {
    const newPage = document.getElementById('newPage');
    const loginID = newPage.getAttribute('data-login-id');
    const loginName = newPage.getAttribute('data-login-name');

    let resultHtml =
        `
        <h3>회원 테이블 관리</h3>
         <table id="subTable">
            <tr>
        `;

    if (loginName) {
        resultHtml += `=> ${loginName}님 로그인 중<br>`;
    }

    // 로그인 상태가 아닌 경우, 로그인 버튼 추가
    if (!loginID) {
        resultHtml += `<th><a onclick="Loginf()">로그인</a></th>`;
    }

    resultHtml +=
        `
        <th><a onclick="Joinf()">회원가입</a></th>
        <th><a onclick="UserList()">회원목록</a></th>
        `;

    // 로그인 상태일 경우, 로그아웃 및 비밀번호 변경 추가
    if (loginID) {
        resultHtml +=
            `<th><a onclick="userLogout()">로그아웃</a></th>
<!--            <th><a onclick="newpwf()">ChangePW</a></th>-->
            `;
    }

    resultHtml +=
        `
            </tr>
        </table>
        `;

    document.getElementById('newPage').innerHTML = resultHtml;
    document.getElementById('pTableListArea').innerHTML = "";
    document.getElementById('insertTableArea').innerHTML = "";
    document.getElementById('resultArea1').innerHTML = "";
    document.getElementById('resultArea2').innerHTML = "";
}