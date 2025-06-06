CREATE TABLE IF NOT EXISTS product (
    id VARCHAR PRIMARY KEY,
    name VARCHAR NOT NULL,
    available_quantity INT NOT NULL
);

INSERT INTO product (id, name, available_quantity) VALUES
  ('prod-001', 'Dell XPS 13 Laptop', 25),
  ('prod-002', 'LG UltraWide Monitor', 40),
  ('prod-003', 'Logitech MX Master 3 Mouse', 60),
  ('prod-004', 'Keychron K8 Mechanical Keyboard', 30),
  ('prod-005', 'Seagate 2TB External Hard Drive', 75),
  ('prod-006', 'Logitech C920 Webcam', 50),
  ('prod-007', 'Sony WH-1000XM4 Headphones', 20),
  ('prod-008', 'FlexForm Ergonomic Chair', 15),
  ('prod-009', 'Samsung Galaxy S23 Smartphone', 10),
  ('prod-010', 'Apple iPad Air Tablet', 12);