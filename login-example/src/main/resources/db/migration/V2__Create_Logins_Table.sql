CREATE TABLE logins (
  login_id VARCHAR(32) PRIMARY KEY,
  user_id BIGINT NOT NULL,
  last_login TIMESTAMP NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
)