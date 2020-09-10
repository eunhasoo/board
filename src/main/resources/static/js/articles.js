const articles = {
    init() {
        document.querySelector('#deleteForm').addEventListener('submit', e => e.preventDefault());
        document.querySelector('#delete').addEventListener('click', this.onDeleteBtnClicked);
    },
    onDeleteBtnClicked() {
        const res = confirm('정말 삭제하시겠습니까?');
        if (res == true) {
            document.querySelector('#deleteForm').submit();
            alert('삭제되었습니다.');
        }
    }
}

articles.init();