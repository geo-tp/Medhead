DROP TABLE IF EXISTS hospitals;

CREATE TABLE hospitals (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  address VARCHAR(500) NOT NULL,
  latitude DECIMAL(9,6) NOT NULL,
  longitude DECIMAL(9,6) NOT NULL,
  available_beds INT NOT NULL
);

INSERT INTO hospitals (name, address, latitude, longitude, available_beds) VALUES
  ('Hospital Saint John', '123 Main St, Springfield', 40.7128, -74.0060, 150),
  ('General Hospital', '456 Elm St, Shelbyville', 41.8781, -87.6298, 200),
  ('City Clinic', '789 Oak St, Capital City', 34.0522, -118.2437, 75);