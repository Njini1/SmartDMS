document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('#docCreateForm');
    if (!form) return;

    form.addEventListener('submit', () => {
        if (!window.editorInstance) {
            console.warn('editorInstance not found');
            return;
        }

        const html = window.editorInstance.getData();
        const hidden = document.querySelector('#contentHidden');
        if (hidden) hidden.value = html;
    });
});
