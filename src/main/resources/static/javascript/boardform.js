function boardForm() {
    let resultHtml =
        `
        <table id="subTable">
            <tr>
                <th><a onclick="noticePagingList('/board/noticePagingList')">Notice</a></th>
                <th><a onclick="inquiryPagingList('/board/inquiryPagingList')">Inquiry</a></th>
                <th><a onclick="reviewPagingList('/board/reviewPagingList')">Review</a></th>
                <th><a onclick="faqPagingList('/board/faqPagingList')">FAQ</a></th>
            </tr>
        </table>
        `;
    document.getElementById('newPage').innerHTML = resultHtml;
    document.getElementById('pTableListArea').innerHTML = "";
    document.getElementById('insertTableArea').innerHTML = "";
    document.getElementById('resultArea1').innerHTML = "";
    document.getElementById('resultArea2').innerHTML = "";
}