document.addEventListener("DOMContentLoaded", () => {
    const tokenMeta = document.querySelector('meta[name="_csrf"]');
    const headerMeta = document.querySelector('meta[name="_csrf_header"]');

    const token = tokenMeta ? tokenMeta.content : null;
    const header = headerMeta ? headerMeta.content : null;

    const boardRoot = document.querySelector(".board-detail");
    if (!boardRoot) {
        return;
    }

    const boardId = boardRoot.dataset.boardId;
    const isLoggedIn = boardRoot.dataset.loggedIn === "true";

    /* =======================
     *  좋아요 토글
     * ======================= */
    const likeBtn = document.getElementById("like-btn");
    if (likeBtn && boardId && token && header) {
        likeBtn.addEventListener("click", () => {
            if (likeBtn.dataset.loggedIn !== "true") {
                alert("로그인 후 이용 가능합니다.");
                return;
            }

            fetch(`/like/${boardId}/toggle`, {
                method: "POST",
                headers: {
                    [header]: token,
                    "X-Requested-With": "XMLHttpRequest"
                }
            })
                .then(res => {
                    if (!res.ok) throw new Error("서버 오류가 발생했습니다.");
                    return res.json();
                })
                .then(data => {
                    if (!data.success) {
                        alert(data.message || "처리 중 오류가 발생했습니다.");
                        return;
                    }
                    const icon = document.getElementById("like-icon");
                    const count = document.getElementById("like-count");
                    if (!icon || !count) return;

                    icon.textContent = data.liked ? "❤️" : "🤍";
                    icon.style.color = data.liked ? "red" : "gray";
                    icon.style.fontSize = "1.5rem";
                    count.textContent = data.likeCount;
                })
                .catch(err => alert(err.message));
        });
    }

    /* =======================
     *  댓글: 답글 폼
     * ======================= */
    const baseForm = document.getElementById("comment-base-form");

    document.querySelectorAll(".reply-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            if (!isLoggedIn) {
                alert("로그인 후 이용 가능합니다.");
                return;
            }
            if (!baseForm) return;

            // 기존 열려 있는 답글 폼 제거
            const existingForm = document.querySelector(".inline-reply-form");
            if (existingForm) existingForm.remove();

            // 폼 복제
            const replyForm = baseForm.cloneNode(true);
            replyForm.classList.add("inline-reply-form");
            replyForm.removeAttribute("id"); // id 중복 방지

            // 복제된 폼 내부 요소들 (name 기반)
            const parentInput = replyForm.querySelector('input[name="parentCommentId"]');
            const contentArea = replyForm.querySelector('textarea[name="content"]');
            const cancelBtn = replyForm.querySelector(".cancel-reply-btn");

            if (parentInput) {
                parentInput.value = btn.dataset.id;
            }
            if (contentArea) {
                contentArea.value = "@" + btn.dataset.user + " ";
                contentArea.focus();
            }
            if (cancelBtn) {
                cancelBtn.style.display = "inline-block";
                cancelBtn.addEventListener("click", () => replyForm.remove());
            }

            const commentDiv = btn.closest(".comment");
            if (commentDiv) {
                commentDiv.insertAdjacentElement("afterend", replyForm);
            }
        });
    });

    /* =======================
     *  댓글: 수정 폼
     * ======================= */
    if (token && boardId) {
        document.querySelectorAll(".edit-btn").forEach(btn => {
            btn.addEventListener("click", () => {
                const commentDiv = btn.closest(".comment");
                if (!commentDiv) return;

                const actions = commentDiv.querySelector(".comment-actions");
                const body = commentDiv.querySelector(".comment-body");
                const commentId = btn.dataset.id;
                const currentContent = btn.dataset.content || "";

                // 이미 열려 있는 수정 폼 있으면 제거
                const existing = commentDiv.querySelector(".inline-edit-form");
                if (existing) existing.remove();

                // 수정 폼 생성
                const editForm = document.createElement("form");
                editForm.classList.add("inline-edit-form");
                editForm.method = "post";
                editForm.action = `/comment/${commentId}/update`;

                editForm.innerHTML = `
                    <input type="hidden" name="_csrf" value="${token}">
                    <input type="hidden" name="boardId" value="${boardId}">
                    <textarea name="newContent" rows="3" class="form-control mb-2">${currentContent}</textarea>
                    <button type="submit" class="btn btn-sm btn-main">수정 완료</button>
                    <button type="button" class="btn btn-sm btn-secondary cancel-edit">취소</button>
                `;

                if (actions) {
                    actions.insertAdjacentElement("afterend", editForm);
                    actions.style.display = "none";
                }
                if (body) {
                    body.style.display = "none";
                }

                const cancelBtn = editForm.querySelector(".cancel-edit");
                if (cancelBtn) {
                    cancelBtn.addEventListener("click", () => {
                        editForm.remove();
                        if (body) body.style.display = "block";
                        if (actions) actions.style.display = "block";
                    });
                }
            });
        });
    }
});
