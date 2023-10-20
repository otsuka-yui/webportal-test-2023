/* 開発用にデータ削除を追加 : リリース時は消す */
DELETE FROM m_user;
DELETE FROM task_t;

/* ユーザマスタのデータ（ADMIN権限） PASS:password */
INSERT INTO m_user (user_id, encrypted_password, user_name, role, enabled)
VALUES('taro@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '情報太郎', 'ROLE_ADMIN', true);
/* ユーザマスタのデータ（上位権限） PASS:password */
INSERT INTO m_user (user_id, encrypted_password, user_name, role, enabled)
VALUES('hanako@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '情報花子', 'ROLE_TOP', true);
/* ユーザマスタのデータ（一般権限） PASS:password */
INSERT INTO m_user (user_id, encrypted_password, user_name, role, enabled)
VALUES('goro@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '情報五郎', 'ROLE_GENERAL', true);

/* タスクテーブルのデータ */
INSERT INTO task_t (id, user_id, title, limitday, complate) VALUES (1, 'admin', 'このレコードは消さないこと', '2022-11-11', true);
INSERT INTO task_t (id, user_id, title, limitday, complate) VALUES (2, 'user', '食材を購入するためのリストを作成', '2023-06-24', false);
INSERT INTO task_t (id, user_id, title, limitday, complate) VALUES (3, 'user', '明日の夕食にレストランを予約しているので、確認の電話をかける', '2023-07-11', false);
INSERT INTO task_t (id, user_id, title, limitday, complate) VALUES (4, 'user', '近所のスポーツクラブに登録するため、必要な書類と登録費用を準備する', '2024-02-27', false);
