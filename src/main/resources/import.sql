INSERT INTO USER(ID, USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES(1, 'jamie', '1234', '종명', 'jamie@gmail.com', CURRENT_TIMESTAMP());
INSERT INTO USER(ID, USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES(2, 'test', '1234', '테스트', 'test@gmail.com', CURRENT_TIMESTAMP());

INSERT INTO QUESTION(ID, WRITER_ID, TITLE, CONTENTS, COUNT_OF_ANSWER, CREATE_DATE) VALUES(1, 1, 'test1', 'test1 입니다.', 0, CURRENT_TIMESTAMP());
INSERT INTO QUESTION(ID, WRITER_ID, TITLE, CONTENTS, COUNT_OF_ANSWER, CREATE_DATE) VALUES(2, 2, 'test2', 'test2 입니다.', 0, CURRENT_TIMESTAMP());

