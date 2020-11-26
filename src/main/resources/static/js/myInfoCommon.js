const myInfo = {
    flag: false,
    init() {
        const selectAllBtn = document.querySelector('.select_all');
        selectAllBtn.addEventListener('click', this.toggle);
    },
    toggle() {
        const checkBoxes = Array.from(document.querySelectorAll('.check_box'));
        this.flag = !this.flag;
        for (let i = 0, n = checkBoxes.length; i < n; i++) {
            checkBoxes[i].checked = this.flag;
        }
    }
}

myInfo.init();