<!-- submit 시 editor.getData() -> hidden newContent 로 주입 -->
document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form.version-form');
    if (!form) return;

    form.addEventListener('submit', () => {
        // main.js에서 window.editorInstance = editor 를 세팅
        if (window.editorInstance) {
            const html = window.editorInstance.getData();
            const hidden = document.querySelector('#newContentHidden');
            if (hidden) hidden.value = html;
        }
    });
});