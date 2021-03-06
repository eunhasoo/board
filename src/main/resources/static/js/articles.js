const articles = {
    commentEditFlag: false,
    init() {
        const form = Array.from(document.querySelectorAll('.delete_form'));
        if (form.length > 0) form.forEach(f => f.addEventListener('submit', e => e.preventDefault()));

        const deleteBtn = Array.from(document.querySelectorAll('.delete_btn'));
        if (deleteBtn.length > 0) deleteBtn.forEach(btn => btn.addEventListener('click', this.onDeleteBtnClicked));

        const commentEditBtn = Array.from(document.querySelectorAll('.comment_edit_btn'));
        if (commentEditBtn.length > 0) commentEditBtn.forEach(btn => btn.addEventListener('click', this.onCommentEditBtnClicked));

        const commentSubmitBtn = Array.from(document.querySelectorAll('.cmt_submit'));
        if (commentSubmitBtn.length > 0) commentSubmitBtn.forEach(btn => btn.addEventListener('click', this.onCommentSubmit));

        const summernote = document.querySelector('#summernote');
        if (summernote) this.manageSummernote();
    },
    onDeleteBtnClicked() {
        const res = confirm('정말로 삭제하시겠습니까?');
        if (res == true) {
            $(this).closest('form').submit();
            alert('삭제되었습니다.');
        }
    },
    onCommentEditBtnClicked(e) {
        function replaceStr(str) {
            return str.replace(/<br>/g, "\n")
                      .replace(/&lt;/g, '<')
                      .replace(/&gt;/g, '>');
        }

        const articleId = parseInt(document.getElementById('articleId').value);
        const comment = e.target.parentElement.nextElementSibling;
        const commentBody = replaceStr(comment.innerHTML);
        const commentId = comment.id;

        if (e.target.textContent === '수정') {
            // 수정 취소는 오직 하나만 존재하도록 감시
           (Array.from(document.querySelectorAll('.comment_edit_btn'))).forEach(btn => btn.innerText = '수정');

            $('#comment_body').val(commentBody);
            $('#comment_body').focus();

            document.querySelector('.cmt_submit').textContent = '수정';
            document.querySelector('#comment_form').action = '/comment/edit/' + commentId;
            e.target.textContent = '수정 취소';
        } else {
            $('#comment_body').val('');
            document.querySelector('.cmt_submit').textContent = '등록';
            document.querySelector('#comment_form').action = '/comment/new/' + articleId;
            e.target.textContent = '수정';
        }
    },
    onCommentSubmit(e) {
        if (e.target.textContent === '수정') {
            alert('수정이 완료되었습니다.');
        }
    },
    manageSummernote() {
        $(function() {
            $('#summernote').summernote({
                height: 500,
                lang: 'ko-KR',
                codeviewFilter: true,
                codeviewIframeFilter: true
            });
        });
    }
}

articles.init();