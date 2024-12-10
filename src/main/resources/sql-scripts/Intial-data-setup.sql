INSERT INTO user (id, firstname, lastname, email, password, role)
VALUES
    (2, 'Alice', 'Smith', 'alice.smith@example.com', '$2a$10$B6JmQRoGQhXzH5rYdGdN5XeY.cKnqlX.vYr9HFu0lzkq.ZtHf6BPxK', 'CHEF'),
    (3, 'Bob', 'Johnson', 'bob.johnson@example.com', '$2a$10$TkfhJj21h5Xvcz7ZTnLD2dZvYomqVv..r4R2yf/h07oBVe1lqz45yI', 'CHEF');


INSERT INTO chef (id, user_id, profile_image_url, bio, rating, cuisine_specialties, contact_email, phone_number, created_at, updated_at)
VALUES
    (1, 2, 'https://example.com/chef-john.jpg', 'Expert in Italian and French cuisines', 4.8, 'Italian, French', 'chef.john@example.com', '123-456-7890', NOW(), NOW()),
    (2, 3, 'https://example.com/chef-alice.jpg', 'Passionate about vegetarian and vegan cooking', 4.7, 'Vegetarian, Vegan', 'chef.alice@example.com', '987-654-3210', NOW(), NOW());


INSERT INTO meal (id, chef_id, name, description, cuisine_type, price_per_serving, gluten_free, vegetarian, vegan, spice_level, image_url, created_at, updated_at)
VALUES
    (1, 1, 'Spaghetti Carbonara', 'Traditional Italian pasta with eggs, cheese, pancetta, and pepper', 'Italian', 15.99, true, true, false, 'Medium', 'https://example.com/spaghetti.jpg', NOW(), NOW()),
    (2, 1, 'Beef Wellington', 'Classic English dish with beef fillet, mushrooms, and puff pastry', 'British', 39.99, false, false, false, 'Medium', 'https://example.com/wellington.jpg', NOW(), NOW()),
    (3, 2, 'Vegan Buddha Bowl', 'A nourishing vegan bowl with quinoa, vegetables, and tahini dressing', 'Vegan', 12.99, true, true, true, 'Mild', 'https://example.com/buddha-bowl.jpg', NOW(), NOW());


INSERT INTO customer (id, user_id, loyalty_points, default_delivery_instructions)
VALUES
    (1, 1, 100, 'Please deliver to the back entrance');
