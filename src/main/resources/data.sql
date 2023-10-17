-- Lisää käyttäjiä
INSERT INTO users(username, password, roles) VALUES ('user1', 'password1', 'ROLE_USER');
INSERT INTO users(username, password, roles) VALUES ('user2', 'password2', 'ROLE_USER');

-- Lisätään tehtäviä u1
INSERT INTO todo(user_id, description, completed) VALUES (1, 'Tehtävä 1 user1:lle', false);
INSERT INTO todo(user_id, description, completed) VALUES (1, 'Tehtävä 2 user1:lle', true);

-- Lisätään tehtäviä u2
INSERT INTO todo(user_id, description, completed) VALUES (2, 'Tehtävä 1 user2:lle', false);