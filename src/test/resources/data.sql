INSERT INTO user (id, email, firstname, lastname, password, role, token, token_valid_until) VALUES
                 (1, 'admin@email.com', 'Admin', 'Chef', 'geheim', 0, 'token', DATE('2023-12-31'));
INSERT INTO cart (id, checked_out, user_id) VALUES (1, false, 1);
UPDATE user SET current_cart_id = 1 WHERE id = 1;

INSERT INTO event (id, description, end, name, start) VALUES (1, 'Maturaball der Abteilungen EL, IT, ME, Y', '2023-03-03 06:00:00.000000', 'Maturaball HTL Steyr 2024', '2023-03-02 21:00:00.000000');
INSERT INTO category (id, name, price, stock, event_id) VALUES (1, 'Normal', 2500, 500, 1);
INSERT INTO category (id, name, price, stock, event_id) VALUES (2, 'Maturanten', 0, 500, 1);
INSERT INTO category (id, name, price, stock, event_id) VALUES (3, 'Prechtl', 5000, 1, 1);

INSERT INTO cart_entry (id, amount, cart_id, category_id) VALUES (3, 1, 2, 3);

