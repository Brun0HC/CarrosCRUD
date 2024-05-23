import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [data, setData] = useState([])

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


    const [carData, setCarData] = useState({
      nome: '',
      ano: 0,
      potencia: '',
      preco: 0,
      fabricante: ''
    });
  
    const handleChange = (e) => {
      const { name, value } = e.target;
      setCarData(prevState => ({
        ...prevState,
        [name]: value
      }));
    };

    const handleEdit = (carro) => {
      setCarData({
        id: carro.id,
        nome: carro.nome,
        ano: carro.ano,
        potencia: carro.potencia,
        preco: carro.preco,
        fabricante: carro.fabricante
      });
    };
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        const { id, ...car } = carData;
        const method = id ? 'PUT' : 'POST';
        const url = id ? `https://ifsp.ddns.net/webservices/carro/carro/${id}` : 'https://ifsp.ddns.net/webservices/carro/carro';
        const response = await fetch(url, {
          method: method,
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(car)
        });
        if (response.ok) {
          alert(id ? 'Carro editado com sucesso!' : 'Carro cadastrado com sucesso!');
          setCarData({
            id: '',
            nome: '',
            ano: 0,
            potencia: '',
            preco: 0,
            fabricante: ''
          });
          fetchCars();
        } else {
          alert(`Erro ao ${id ? 'editar' : 'cadastrar'} o carro.`);
        }
      } catch (error) {
        console.error('Erro:', error);
        alert(`Erro ao ${id ? 'editar' : 'cadastrar'} o carro.`);
      }
    };  

    const handleDelete = async (id) => {
      if (window.confirm('Tem certeza que deseja excluir este carro?')) {
        try {
          const response = await fetch(`https://ifsp.ddns.net/webservices/carro/carro/${id}`, {
            method: 'DELETE'
          });
          if (response.ok) {
            alert('Carro excluído com sucesso!');
            fetchCars();
          } else {
            alert('Erro ao excluir o carro.');
          }
        } catch (error) {
          console.error('Erro:', error);
          alert('Erro ao excluir o carro.');
        }
      }
    };

  return (
    <>
      <div>
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
              <td>
                <button onClick={() => handleDelete(carro.id)}>Excluir</button>
              </td>
              <td>
                <button onClick={() => handleEdit(carro)}>Editar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <h1>Formulario de Carros</h1>
      <form onSubmit={handleSubmit}>
        <input type="hidden" name="id" value={carData.id} />
        <label>
          Nome:
          <input type="text" name="nome" value={carData.nome} onChange={handleChange} />
        </label>
        <br />
        <label>
          Ano:
          <input type="number" name="ano" value={carData.ano} onChange={handleChange} />
        </label>
        <br />
        <label>
          Potência:
          <input type="text" name="potencia" value={carData.potencia} onChange={handleChange} />
        </label>
        <br />
        <label>
          Preço:
          <input type="number" name="preco" value={carData.preco} onChange={handleChange} />
        </label>
        <br />
        <label>
          Fabricante:
          <input type="text" name="fabricante" value={carData.fabricante} onChange={handleChange} />
        </label>
        <br />
        <button type="submit">Enviar</button>
      </form>
      </div>
    </>
  );
}

export default App
