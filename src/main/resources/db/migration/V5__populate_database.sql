INSERT INTO categories (name)
VALUES ('Produce'),
       ('Dairy & Eggs'),
       ('Bakery'),
       ('Meat & Seafood'),
       ('Beverages'),
       ('Pantry Staples'),
       ('Frozen Foods'),
       ('Snacks');
INSERT INTO products (name, price, description, category_id)
VALUES
-- Produce (Category ID 1)
('Organic Bananas (Per Pound)', 0.79, 'Fresh, ripe organic bananas, perfect for snacking or smoothies.', 1),

-- Dairy & Eggs (Category ID 2)
('Large Grade A Eggs (Dozen)', 4.99, 'A dozen large, white, cage-free chicken eggs.', 2),

-- Bakery (Category ID 3)
('Whole Wheat Sliced Bread', 3.50, 'A loaf of soft, 100% whole wheat sliced bread.', 3),

-- Meat & Seafood (Category ID 4)
('Ground Beef (80/20) - 1 lb', 6.99, 'One pound of 80% lean, 20% fat ground beef.', 4),

-- Beverages (Category ID 5)
('Sparkling Water - Original (12-pack)', 5.99, 'A 12-pack of unflavored sparkling water cans.', 5),

-- Pantry Staples (Category ID 6)
('Basmati Rice - 5 lb bag', 8.50, 'A 5-pound bag of premium aged Basmati rice.', 6),

-- Frozen Foods (Category ID 7)
('Frozen Mixed Berries - 16 oz', 4.59, 'A 16-ounce bag of frozen strawberries, blueberries, and raspberries.', 7),

-- Snacks (Category ID 8)
('Sea Salt Potato Chips - Large Bag', 3.75, 'A large bag of classic, lightly salted potato chips.', 8),

-- Produce (Category ID 1)
('Avocados (Each)', 1.50, 'Ready-to-eat Hass avocados, great for guacamole.', 1),

-- Dairy & Eggs (Category ID 2)
('Whole Milk - Half Gallon', 3.25, 'A half-gallon container of pasteurized whole dairy milk.', 2);