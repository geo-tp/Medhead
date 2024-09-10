import { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';
import { getNearestAvailableHospital } from './ApiService';

interface Hospital {
  name: string;
  address: string;
  availableBeds: number;
}

function App() {
  const [nearestHospital, setNearestHospital] = useState<Hospital | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [latitude, setLatitude] = useState<string>('41.8785');
  const [longitude, setLongitude] = useState<string>('-87.6292');
  const [specialization, setSpecialization] = useState<string>('General'); // État pour la spécialisation
  const [error, setError] = useState<string | null>(null);

  const fetchNearestHospital = async (lat: number, lon: number) => {
    setLoading(true);
    try {
      const data = await getNearestAvailableHospital(lat, lon, specialization);
      setNearestHospital(data);
      setError(null);
    } catch (error: any) {
      console.error('Error fetching nearest hospital:', error);
      setError(error.response.data.error);
    }
    setLoading(false);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    const lat = parseFloat(latitude);
    const lon = parseFloat(longitude);

    fetchNearestHospital(lat, lon);
  };

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Nearest Hospital</h1>
        <form className="App-form" onSubmit={handleSubmit}>
          <div>
            <label>
              Lattitude
            </label>
              <input
                required
                type="text"
                value={latitude}
                onChange={(e) => setLatitude(e.target.value)}
              />
          </div>
          <div>
            <label>
              Longitude
            </label>
            <input
              required
              type="text"
              value={longitude}
              onChange={(e) => setLongitude(e.target.value)}
            />
          </div>
          <div>
            <label>
              Specialization
            </label>
            <select
              value={specialization}
              onChange={(e) => setSpecialization(e.target.value)}
            >
              <option value="General">General</option>
              <option value="Cardiology">Cardiology</option>
            </select>
          </div>
          <button type="submit">Find Hospital</button>
        </form>
        {loading && <div className="App-card">Loading...</div>}
        {error && <div className="App-card">{error}</div>}
        {nearestHospital && (
          <div className="App-card">
            <h2>{nearestHospital.name}</h2>
            <p>{nearestHospital.address}</p>
            <p>Available Beds: {nearestHospital.availableBeds}</p>
          </div>
        )}
      </header>
    </div>
  );
}

export default App;
