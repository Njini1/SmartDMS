document.addEventListener('DOMContentLoaded', () => {
    const nav = document.querySelector('nav[data-authenticated]');
    if (!nav) return;

    const isAuthenticated = nav.dataset.authenticated === 'true';

    document.querySelectorAll('.document-create-link').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();

            if (!isAuthenticated) {
                alert('로그인 후 이용 가능합니다.');
                return;
            }

            window.location.href = link.dataset.url;
        });
    });
});
