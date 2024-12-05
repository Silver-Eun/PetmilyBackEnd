"use strict"

function boardForm() {
    let resultHtml =
        `
        <h3>공지사항 문의 리뷰 FAQ 테이블 관리</h3>
        <table id="subTable">
            <tr>
                <th><a onclick="noticePagingList('board/noticePagingList')">공지사항</a></th>
                <th><a onclick="inquiryPagingList('board/inquiryPagingList')">상품문의</a></th>
                <th><a onclick="reviewPagingList('board/reviewPagingList')">상품후기</a></th>
                <th><a onclick="faqPagingList('board/faqPagingList')">FAQ</a></th>
            </tr>
        </table>
        `;
    document.getElementById('newPage').innerHTML = resultHtml;
    document.getElementById('pTableListArea').innerHTML = "";
    document.getElementById('insertTableArea').innerHTML = "";
    document.getElementById('resultArea1').innerHTML = "";
    document.getElementById('resultArea2').innerHTML = "";
}