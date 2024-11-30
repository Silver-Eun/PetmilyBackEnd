"use strict"

function UserList() {

    let url = "user/UserList";
    $.ajax({
        type: 'Get',
        url: url,
        success: function (resultPage) {
            document.getElementById('resultArea1').innerHTML = resultPage;
        },
        error: function () {
            document.getElementById('resultArea1').innerHTML = "Ajax Error";
        }
    }); //ajax
    document.getElementById('resultArea1').innerHTML = "";
}

function UserDelete(id) {
    let url = "/api/user/UserDelete/" + id;
    axios.delete(url).then(response => {
        alert("삭제 성공 : " + response.data);
        UserList()
    }).catch(err => {
        alert("시스템 오류 : " + err.message);
    });


}
