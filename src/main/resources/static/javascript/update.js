"use strict"

function Userupdate(id) {
    let url = "user/Updatef/" + id;

    axios.get(url)
        .then(response => {
            document.getElementById('resultArea1').innerHTML = response.data;
        })
        .catch(err => {
            alert("회원정보 수정 불러오기 실패 : " + err.message);
        });

    document.getElementById('resultArea1').innerHTML = "";
}
