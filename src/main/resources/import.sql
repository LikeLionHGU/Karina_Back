

--
-- User 테이블 더미 데이터
--
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (1, '기업', 'auth_file_1.pdf', 'factory1', 'password123', '김공장', '010-1111-2222', '서울특별시', '강남구');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (2, '개인', 'auth_file_2.jpg', 'user2', 'password123', '박개인', '010-3333-4444', '경기도', '성남시 분당구');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (3, '기업', 'auth_file_3.pdf', 'factory3', 'password123', '최공장', '010-5555-6666', '부산광역시', '해운대구');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (4, '개인', 'auth_file_4.jpg', 'user4', 'password123', '이개인', '010-7777-8888', '대구광역시', '수성구');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (5, '기업', 'auth_file_5.pdf', 'factory5', 'password123', '정공장', '010-9999-0000', '광주광역시', '서구');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (6, '개인', 'auth_file_6.jpg', 'user6', 'password123', '조개인', '010-1234-5678', '대전광역시', '유성구');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (7, '기업', 'auth_file_7.pdf', 'factory7', 'password123', '윤공장', '010-1111-3333', '울산광역시', '남구');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (8, '개인', 'auth_file_8.jpg', 'user8', 'password123', '장개인', '010-2222-4444', '강원도', '강릉시');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (9, '기업', 'auth_file_9.pdf', 'factory9', 'password123', '임공장', '010-5555-7777', '제주특별자치도', '제주시');
INSERT INTO user (id, role, authentication_file, login_id, password, name, phone_number, main_address, detail_address) VALUES (10, '개인', 'auth_file_10.jpg', 'user10', 'password123', '한개인', '010-8888-9999', '충청북도', '청주시');

--
-- Article 테이블 더미 데이터
--
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (1, 2, '{"salmon": 10, "tuna": 5}', '2025-08-22', '10:00', '2025-08-25', '18:00', '2025-08-22 11:00', '판매중', 'thumb_1.jpg', 'video_1.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (2, 4, '{"mackerel": 20}', '2025-08-21', '09:00', '2025-08-24', '17:00', '2025-08-21 10:30', '판매중', 'thumb_2.jpg', 'video_2.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (3, 6, '{"cod": 15}', '2025-08-20', '11:30', '2025-08-23', '19:00', '2025-08-20 12:00', '거래완료', 'thumb_3.jpg', 'video_3.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (4, 8, '{"squid": 30}', '2025-08-19', '14:00', '2025-08-22', '16:00', '2025-08-19 15:00', '판매중', 'thumb_4.jpg', 'video_4.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (5, 10, '{"crab": 8, "shrimp": 10}', '2025-08-18', '16:00', '2025-08-21', '18:00', '2025-08-18 17:00', '판매중', 'thumb_5.jpg', 'video_5.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (6, 2, '{"seaweed": 50}', '2025-08-17', '13:00', '2025-08-20', '15:00', '2025-08-17 14:00', '거래완료', 'thumb_6.jpg', 'video_6.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (7, 4, '{"octopus": 3}', '2025-08-16', '10:00', '2025-08-19', '12:00', '2025-08-16 11:00', '판매중', 'thumb_7.jpg', 'video_7.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (8, 6, '{"oyster": 100}', '2025-08-15', '08:00', '2025-08-18', '10:00', '2025-08-15 09:00', '판매중', 'thumb_8.jpg', 'video_8.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (9, 8, '{"shrimp": 20}', '2025-08-14', '15:00', '2025-08-17', '17:00', '2025-08-14 16:00', '거래완료', 'thumb_9.jpg', 'video_9.mp4');
INSERT INTO article (id, user_id, fish_info, get_date, get_time, limit_date, limit_time, post_time, status, thumbnail, video) VALUES (10, 10, '{"flounder": 5}', '2025-08-13', '12:00', '2025-08-16', '14:00', '2025-08-13 13:00', '판매중', 'thumb_10.jpg', 'video_10.mp4');

--
-- Matching 테이블 더미 데이터
--
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (1, 1, 1, '2025-08-23', '대기중');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (2, 3, 2, '2025-08-22', '대기중');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (3, 5, 3, '2025-08-21', '매칭완료');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (4, 7, 4, '2025-08-20', '대기중');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (5, 9, 5, '2025-08-19', '대기중');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (6, 1, 6, '2025-08-18', '매칭완료');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (7, 3, 7, '2025-08-17', '대기중');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (8, 5, 8, '2025-08-16', '대기중');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (9, 7, 9, '2025-08-15', '매칭완료');
INSERT INTO matching (id, factory_id, article_id, request_date, matching_status) VALUES (10, 9, 10, '2025-08-14', '대기중');


