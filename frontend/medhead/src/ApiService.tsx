import axios from 'axios';

const API_URL = 'http://localhost:9000/hospital/nearest'; // Endpoint pour trouver hopital le plus proche
const USERNAME = 'admin';
const PASSWORD = 'adminpassword';

export const getNearestAvailableHospital = async (lat: number, lng: number, specialization: string) => {
  try {
    const response = await axios.get(`${API_URL}`, {
      params: { lat, lng, specialization },
      auth: {
        username: USERNAME,
        password: PASSWORD
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching nearest available hospital:', error);
    throw error;
  }
};
