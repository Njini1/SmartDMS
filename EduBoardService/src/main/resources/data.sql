-- 유저 더미
INSERT INTO users (email, password, nickname, role, created_date, updated_date)
VALUES
    ('user1@test.com', '$2b$12$suEcCRh9/1YlM2e6gscJHuqNKY5HZqCMBQW6Gx8fzzwuFUoY2exci', '사용자1', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user2@test.com', '$2a$10$EIX/hASHedPW.zbO7Y2oF', '사용자2', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user3@test.com', '$2a$10$EIX/hASHedPW.zbO7Y2oF', '사용자3', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user4@test.com', '$2a$10$EIX/hASHedPW.zbO7Y2oF', '사용자4', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user5@test.com', '$2a$10$EIX/hASHedPW.zbO7Y2oF', '사용자5', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 게시글 더미
INSERT INTO board (user_id, title, content, status, created_date, updated_date)
VALUES
    (1,
     '첫 번째 게시글입니다.',
     '안녕하세요. 이것은 사용자1이 작성한 첫 번째 게시글입니다. 싸이웍스 과제의 테스트용 더미 데이터로 작성된 글입니다.
     이번 게시판 프로젝트는 Spring Boot, JPA, Thymeleaf를 활용해 로그인, 댓글, 좋아요 기능 등을 구현하는 것을 목표로 하고 있습니다.
     현재는 데이터베이스 연동과 기본 CRUD 기능이 정상적으로 작동하는지 확인 중이며, 추후에는 파일 업로드, 검색 기능, 그리고 게시글 정렬 기능도 추가할 예정입니다.
     이 글은 테스트를 위해 길게 작성된 예시 문장으로, 문단이 여러 줄로 구성되어도 화면에서 자연스럽게 보이도록 하기 위한 목적이 있습니다.
     또한 한글 줄바꿈 처리나 HTML 렌더링 테스트, CSS 적용 여부 등을 확인하는 데에도 사용됩니다.
     읽어주셔서 감사합니다.',
     'ACTIVE',
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP),
    (2,
     '댓글 및 좋아요 기능 테스트 중입니다.',
     '안녕하세요. 사용자2입니다. 현재 댓글 기능과 좋아요 토글 기능을 테스트하고 있습니다.
     게시글에 여러 명이 동시에 좋아요를 눌렀을 때, 정상적으로 카운트가 증가하고 중복 방지가 이루어지는지를 확인 중입니다.
     또한 댓글 작성 시 @닉네임 기능이 올바르게 작동하는지, 대댓글 구조가 올바르게 표현되는지도 함께 검증하고 있습니다.
     테스트 중 발견된 버그는 즉시 노션에 정리하여 수정 이력을 관리하고 있습니다.
     이 글은 프론트엔드와 백엔드 간의 통신 테스트를 위한 용도로 작성되었습니다.',
     'ACTIVE',
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP),
    (3,
     '긴 본문 렌더링 테스트 게시글입니다.',
     '안녕하세요. 사용자3입니다. 이 게시글은 화면 내 긴 본문이 어떻게 렌더링되는지를 확인하기 위한 테스트 글입니다.
     게시글 본문이 길어질수록 줄바꿈 처리, 여백, 글자 크기, 폰트, 그리고 반응형 CSS 설정이 올바르게 적용되어야 합니다.
     특히 모바일 화면에서 긴 글이 잘림 없이 자연스럽게 표시되는지를 확인하는 것이 중요합니다.
     또한 긴 글에서는 “white-space: pre-line” 속성이 적용되어 있는지도 함께 테스트합니다.
     추가로, 데이터베이스의 TEXT 컬럼이 충분히 긴 데이터를 저장할 수 있는지, 쿼리 성능에 영향을 주지 않는지도 함께 점검합니다.
     이 문장은 단순히 테스트용으로 작성되었으며, 실제 내용은 의미가 없습니다.
     읽고 계신다면 이 게시판은 정상적으로 작동 중입니다. 감사합니다.',
     'ACTIVE',
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP),
    (1,
     '게시글 수정 및 삭제 기능 테스트',
     '이 글은 사용자1이 작성한 게시글 수정 및 삭제 기능 테스트용 더미 데이터입니다.
     현재 게시글 수정 시 기존 내용이 textarea에 올바르게 불러와지는지, 변경된 내용이 정상적으로 DB에 반영되는지를 확인 중입니다.
     삭제 기능은 실제 데이터 삭제 대신 상태(Status)를 DELETED로 변경하는 소프트 삭제 방식을 사용하고 있습니다.
     이 방식은 데이터 복구가 용이하고, 감사 로그 관리에도 유리합니다.
     이 테스트를 통해 서비스의 안정성과 유지보수성을 높이고자 합니다.',
     'ACTIVE',
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP),
    (4, '삭제된 게시글', '이 글은 삭제 상태입니다.', 'DELETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, '다섯 번째 게시글', '이것은 사용자5가 작성한 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, '여섯 번째 게시글', '이것은 사용자2가 작성한 두 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, '일곱 번째 게시글', '이것은 사용자3이 작성한 두 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, '여덟 번째 게시글', '이것은 사용자4가 작성한 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, '아홉 번째 게시글', '이것은 사용자5가 작성한 두 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, '열 번째 게시글', '이것은 사용자1이 작성한 세 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, '열한 번째 게시글', '이것은 사용자2가 작성한 세 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, '열두 번째 게시글', '이것은 사용자3이 작성한 세 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, '열세 번째 게시글', '이것은 사용자4가 작성한 두 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, '열네 번째 게시글', '이것은 사용자5가 작성한 세 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, '열다섯 번째 게시글', '이것은 사용자1이 작성한 네 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, '열여섯 번째 게시글', '이것은 사용자2가 작성한 네 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, '열일곱 번째 게시글', '이것은 사용자3이 작성한 네 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, '열여덟 번째 게시글', '이것은 사용자4가 작성한 세 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, '열아홉 번째 게시글', '이것은 사용자5가 작성한 네 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, '스무 번째 게시글', '이것은 사용자1이 작성한 다섯 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, '스물한 번째 게시글', '이것은 사용자2가 작성한 다섯 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, '스물두 번째 게시글', '이것은 사용자3이 작성한 다섯 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, '스물세 번째 게시글', '이것은 사용자4가 작성한 네 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, '스물네 번째 게시글', '이것은 사용자5가 작성한 다섯 번째 게시글입니다.', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 댓글 더미
INSERT INTO comment (board_id, user_id, parent_comment_id, root_comment_id, content, status, created_date, updated_date)
VALUES
    (1, 2, null, 1,'안녕하세요~~~', 'ACTIVE', '2025-10-01 12:30:00', '2025-10-01 12:30:00'),
    (1, 3, 1, 1,'반갑습니다!', 'ACTIVE', '2025-10-02 15:45:10', '2025-10-02 15:45:10'),
    (1, 1, 2, 1,'오랜만이에요', 'ACTIVE', '2025-10-03 09:20:00', '2025-10-03 09:20:00'),
    (1, 5, 1, 1,'과제 하시는 건가요?', 'ACTIVE', '2025-10-04 11:00:00', '2025-10-04 11:00:00'),
    (1, 2, null, 5,'모두 행복한 하루 보내세요~', 'ACTIVE', '2025-10-02 16:10:00', '2025-10-05 16:10:00'),
    (1, 3, 5, 5, '주말까지 화이팅!', 'ACTIVE', '2025-10-05 16:10:00', '2025-10-05 16:10:00'),
    (1, 4, 5, 5,'첫 번째 게시글에 대한 삭제된 세번째 댓글1입니다.', 'DELETED', '2025-10-06 13:00:00', '2025-10-06 13:00:00'),
    (2, 4, null, 8, '삭제된 게시글(두 번째 게시글)에 대한 댓글입니다.', 'DELETED', '2025-10-07 08:40:00', '2025-10-07 08:40:00');
-- 댓글1(작성자A) - 댓글 내용
-- ├─ 대댓글1-1(작성자B) - @작성자A 댓글 내용
-- │  대댓글1-2(작성자C) - @작성자B 댓글 내용
-- ├─ 대댓글1-3(작성자D) - @작성자A 댓글 내용
-- 댓글2 (작성자E) - 댓글 내용
-- └─ 대댓글2-1 (작성자F) - @작성자E 댓글 내용
-- └─ 대댓글2-2 (작성자F) - @작성자E 댓글 내용 - 삭제

-- ✅ 좋아요 더미 데이터
INSERT INTO like_boards (board_id, user_id, liked)
VALUES
    (1, 2, true),   -- 사용자2 → 게시글1 좋아요
    (1, 3, true),   -- 사용자3 → 게시글1 좋아요
    (1, 4, true),   -- 사용자4 → 게시글1 좋아요
    (1, 5, false),  -- 사용자5 → 게시글1 싫어요 (취소 상태)

    (2, 1, true),   -- 사용자1 → 게시글2 좋아요
    (2, 3, true),   -- 사용자3 → 게시글2 좋아요
    (2, 5, true),   -- 사용자5 → 게시글2 좋아요

    (3, 1, true),   -- 사용자1 → 게시글3 좋아요
    (3, 2, true),   -- 사용자2 → 게시글3 좋아요
    (3, 5, true),   -- 사용자5 → 게시글3 좋아요

    (4, 3, true),   -- 사용자3 → 게시글4 좋아요
    (4, 5, true),   -- 사용자5 → 게시글4 좋아요

    (5, 1, false),  -- 사용자1 → 삭제된 게시글5 (좋아요 취소)
    (6, 4, true),   -- 사용자4 → 게시글6 좋아요
    (7, 1, true),   -- 사용자1 → 게시글7 좋아요
    (8, 2, true),   -- 사용자2 → 게시글8 좋아요
    (9, 3, true),   -- 사용자3 → 게시글9 좋아요
    (10, 5, true);  -- 사용자5 → 게시글10 좋아요
