# MedHead - Nearest Hospital Finder

MedHead is a POC with Spring Boot to find the nearest hospital with available beds. The project leverages Google Maps Distance Matrix API to calculate distances between the user's location and hospitals.

## Prerequisites

### Backend
- Java 11 or higher
- Maven
- Google Maps API Key

### Frontend
- Node 14 or higher 

## Configuration

Update the application.properties file in the src/main/resources directory with the following content:
 ```map.key=YOUR_GOOGLE_MAPS_API_KEY```


## Endpoints

### Get Hospitals
- **URL**: /hospitals
- **Method**: GET
- **Auth**: Not Protected
- **Query Parameters**: None
- **Example Request**: ```curl -X GET "http://localhost:9000/hospitals"```
- **Response**: Returns all hospitals.
```
[
  {
    "id": 1,
    "name": "Hospital Saint John",
    "address": "123 Main St, Springfield",
    "latitude": 40.7128,
    "longitude": -74.006,
    "availableBeds": 150
  },
  {
    "id": 2,
    "name": "General Hospital",
    "address": "456 Elm St, Shelbyville",
    "latitude": 41.8781,
    "longitude": -87.6298,
    "availableBeds": 200
  },
  {
    "id": 3,
    "name": "City Clinic",
    "address": "789 Oak St, Capital City",
    "latitude": 34.0522,
    "longitude": -118.2437,
    "availableBeds": 75
  }
]
```

### Get an Hospital
- **URL**: /hospital/{ id }
- **Method**: GET
- **Auth**: Authentication required
- **Query Parameters**:
    - id (required): Id of the hospital.
- **Example Request**: ```curl -X GET "http://localhost:9000/hospital/1"```
- **Response**: Returns an Hospital.
```
{
    "id": 1,
    "name": "Hospital Saint John",
    "address": "123 Main St, Springfield",
    "latitude": 40.7128,
    "longitude": -74.006,
    "availableBeds": 150
}
```

### Get Nearest Available Hospital
- **URL**: /hospital/nearest
- **Method**: GET
- **Auth**: Authentication required
- **Query Parameters**:
    - lat (required): Latitude of the user's location.
    - lng (required): Longitude of the user's location.
- **Example Request**: ```curl -X GET "http://localhost:9000/hospital/nearest?lat=40.7128&lng=-74.0060"```
- **Response**: Returns the nearest hospital with available beds.
```
{
  "id": 1,
  "name": "Hospital Saint John",
  "address": "123 Main St, Springfield",
  "latitude": 40.7128,
  "longitude": -74.0060,
  "availableBeds": 150
}
```