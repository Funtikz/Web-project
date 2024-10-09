INSERT INTO product (product_type, name, description, original_price, discounted_price, discount_percentage, quantity, image_url) VALUES
  ('ACCESSORY', 'Soccer Ball', 'High quality soccer ball', 50.00, 45.00, 10, 100, 'url_to_image_ball'),
  ('ACCESSORY', 'Warm Gloves', 'Perfect for cold weather', 20.00, 15.00, 25, 200, 'url_to_image_gloves'),
  ('ACCESSORY', 'Stylish Scarf', 'Elegant scarf for all seasons', 30.00, 27.00, 10, 150, 'url_to_image_scarf'),
  ('CLOTHING', 'Football Jersey', 'Team jersey', 70.00, 65.00, 7, 50, 'url_to_image_jersey'),
  ('CLOTHING', 'Running Shorts', 'Lightweight running shorts', 40.00, 35.00, 12, 75, 'url_to_image_shorts');

INSERT INTO accessory (id, accessory_category) VALUES
   (1, 'BALL'),
   (2, 'GLOVES'),
   (3, 'SCARF');


INSERT INTO clothing (id, clothing_size, clothing_category) VALUES
    (4, 'L', 'JERSEY'),
    (5, 'M', 'SHORTS');