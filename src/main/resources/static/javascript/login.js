"use strict"

function Loginf() {
    let url = "/user/Loginf";
    axios.get(url
    ).then(response => {
        document.getElementById('resultArea1').innerHTML = response.data;
    }).catch(err => {
        alert("로그인 페이지 불러오기 실패 => " + err.message);
    });

    document.getElementById('resultArea2').innerHTML = "";
}

function Login() {
    let url = "/user/Login";

    const data = {
        user_id: document.getElementById('id').value,
        user_password: document.getElementById('password').value
    };

    axios.post(url, data, {headers: {'Content-Type': 'application/json'}})
        .then(response => {
            // 로그인 성공 시 사용자 정보 설정
            const newPage = document.getElementById('newPage');
            newPage.setAttribute('data-login-id', response.data.user_id);
            newPage.setAttribute('data-login-name', response.data.user_name);
            alert(`로그인 되었습니다`);
            location.reload();
        })
        .catch(err => {
            if (err.response.status === 401) {
                alert("아이디 또는 비밀번호가 일치하지 않습니다.");
            } else {
                alert("시스템 오류 => " + err.message);
            }
        });
}

function userLogout() {
    let url = "/user/logout";
    axios.get(url)
        .then(response => {
            alert('로그아웃 되었습니다.');
            location.reload();
        })
        .catch(error => {
            alert("로그아웃 실패 => " + err.message);
        });
}
