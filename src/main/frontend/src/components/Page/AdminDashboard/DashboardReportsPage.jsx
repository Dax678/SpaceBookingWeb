import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Button, Container, Image, Modal } from 'react-bootstrap';
import Form from "react-validation/build/form";

import apiReservationService from '../../../services/api-reservation.service';
import apiSpaceService from '../../../services/api-space.service';

const DashboardReportsPage = () => {
    const [reservations, setReservations] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(10);
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [formData, setFormData] = useState({
        startDate: '',
        endDate: '',
        floorId: '1',
      });

    const location = useLocation();
    const { startDate, endDate } = location.state || {};

    useEffect(() => {
        setFormData((prevData) => ({
            ...prevData,
            startDate: startDate || getCurrentDate(),
            endDate: endDate || getCurrentDate,
          }));
    }, [startDate, endDate]);


    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
      };

    const generateReservationReport = (event) => {
        event.preventDefault();
        apiReservationService.generateReservationReport(formData.startDate, formData.endDate)
        .then((response) => {
          // Otwieramy plik PDF w nowej karcie
          console.log(response.data);
          const blob = new Blob([response.data], { type: 'application/pdf' });
          console.log(blob.size);
          const url = window.URL.createObjectURL(blob);
          // const a = document.createElement('a');
          // a.href = url;
          // a.download = 'reservation_report.pdf';
          // a.click();
          window.open(url);
        })
        .catch((error) => {
          console.error('Błąd podczas pobierania miejsc:', error);
        });
    };

    const generateSpaceReport = (event) => {
      event.preventDefault();
      apiSpaceService.generateSpaceReport(formData.floorId)
      .then((response) => {
        // Otwieramy plik PDF w nowej karcie
        console.log(response.data);
        const blob = new Blob([response.data], { type: 'application/pdf' });
        console.log(blob.size);
        const url = window.URL.createObjectURL(blob);
        window.open(url);
      })
      .catch((error) => {
        console.error('Błąd podczas pobierania miejsc:', error);
      });
  };

    const getCurrentDate = () => {
        const dateObj = new Date();
        const formattedDate = dateObj.toISOString().substr(0, 10); // Format: YYYY-MM-DD
        return formattedDate;
      };

  return (
    <Container>
        <div>
        <h3 className="text-dark mb-4 text-center">Moje rezerwacje</h3>
        <div className="card shadow">
            <div className="card-header py-3">
                <p className="text-primary m-0 fw-bold">Szukaj Rezerwacji</p>
            </div>
            <div className="card-body">
                <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
                <Form className="form" onSubmit={generateReservationReport}>
                <p>
                  <label htmlFor="date">Ustaw datę początkową</label>
                  <input 
                    className="form-control" 
                    id="startDate" 
                    type="date" 
                    name="startDate" 
                    value={formData.startDate}
                    onChange={handleInputChange}
                    required 
                  />
                </p>
                <p>
                  <label htmlFor="date">Ustaw datę końcową</label>
                  <input 
                    className="form-control" 
                    id="endDate" 
                    type="date" 
                    name="endDate" 
                    value={formData.endDate}
                    onChange={handleInputChange}
                    required 
                  />
                </p>
                <button 
                  className="d-flex justify-content-center btn btn-primary d-block btn-user w-100" 
                  type="submit">
                    Wygeneruj Raport
                </button>
              </Form>
                </div>
            </div>
        </div>
        <div className="card shadow">
            <div className="card-header py-3">
                <p className="text-primary m-0 fw-bold">Szukaj Miejsc</p>
            </div>
            <div className="card-body">
                <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
                <Form className="form" onSubmit={generateSpaceReport}>
                <p>
                  <label htmlFor="floorId">Wybierz piętro</label>
                  <select 
                    id="floorId" 
                    name="floorId" 
                    className="form-control"
                    value={formData.floorId}
                    onChange={handleInputChange}
                  >
                    <option value="1">Piętro 1</option>
                    <option value="2">Piętro 2</option>
                    <option value="3">Piętro 3</option>
                    <option value="4">Parking -1</option>
                    <option value="5">Parking -2</option>
                  </select>
                </p>
                <button 
                  className="d-flex justify-content-center btn btn-primary d-block btn-user w-100" 
                  type="submit">
                    Znajdź Miejsca
                </button>
              </Form>
                </div>
            </div>
        </div>
    </div>
    </Container>
  );
};

export default DashboardReportsPage;