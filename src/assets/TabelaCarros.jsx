// src/TabelaCarros.jsx
import React, { useEffect, useState } from 'react';

const TabelaCarros = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    fetchCars();
  }, []);

  const fetchCars = async () => {
    try {
      const response = await fetch('https://ifsp.ddns.net/webservices/carro/carro');
      if (response.ok) {
        const data = await response.json();
        setData(data);
      } else {
        console.error('Erro ao buscar carros.');
      }
    } catch (error) {
      console.error('Erro:', error);
    }
  };



  return (
    <table className="styled-table">
      <thead>
        <tr>
          <th>Id</th>
          <th>Nome</th>
          <th>Ano</th>
          <th>Potência</th>
          <th>Preço</th>
          <th>Fabricante</th>
        </tr>
      </thead>
      <tbody>
        {data.map(carro => (
          <tr key={carro.id}>
            <td>{carro.id}</td>
            <td>{carro.nome}</td>
            <td>{carro.ano}</td>
            <td>{carro.potencia}</td>
            <td>{carro.preco}</td>
            <td>{carro.fabricante}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default TabelaCarros;
