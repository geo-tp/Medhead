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
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchNearestHospital = async () => {
      try {
        const data = await getNearestAvailableHospital(41.8785, -87.6292); // Coords proche General Hospital
        setNearestHospital(data);
        setLoading(false)
      } catch (error) {
        console.error('Error fetching nearest hospital:', error);
      }
    };

    fetchNearestHospital();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }


  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>{nearestHospital?.name}</h1>
        <p>Address: {nearestHospital?.address}</p>
        <p>Available Beds: {nearestHospital?.availableBeds}</p>
      </header>
    </div>
  );

  
}

export default App;
