import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Button, Container, Image, Modal } from 'react-bootstrap';
import Form from "react-validation/build/form";

import apiReservationService from '../../../services/api-reservation.service';

const DashboardBookingListPage = () => {
    const [reservations, setReservations] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(10);
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [formData, setFormData] = useState({
        startDate: '',
        endDate: '',
      });

    const location = useLocation();
    const { startDate, endDate } = location.state || {};

    useEffect(() => {
        setFormData((prevData) => ({
            ...prevData,
            spaceType: "Tech",
            startDate: startDate || getCurrentDate(),
            endDate: endDate || getCurrentDate,
          }));
    }, [startDate, endDate]);

    // Oblicz indeksy początkowy i końcowy dla bieżącej strony
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = reservations.slice(indexOfFirstItem, indexOfLastItem);

    // Zmienia stronę
    const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
      };

    const handleSearch = (event) => {
        event.preventDefault();
        apiReservationService.getReservationDetails(formData.startDate, formData.endDate)
        .then((response) => {
            setReservations(response.data.sort((a, b) => {
                return new Date(a.reservation_date) - new Date(b.reservation_date)
            }))
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
                <Form className="form" onSubmit={handleSearch}>
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
                    Znajdź Wolne Miejsce
                </button>
              </Form>
                </div>
            </div>
        </div>
        <div className="card shadow">
            <div className="card-header py-3">
                <p className="text-primary m-0 fw-bold">Lista rezerwacji</p>
            </div>
            <div className="card-body">
                <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
                    <table className="table my-0">
                        <thead>
                        <tr>
                            <th>ID Rezerwacji</th>
                            <th>Imię</th>
                            <th>Nazwisko</th>
                            <th>Nazwa Miejca</th>
                            <th>Typ Miejsca</th>
                            <th>Regulacja wysokości</th>
                            <th>Numer Piętra</th>
                            <th>Data rezerwacji</th>
                        </tr>
                        </thead>
                        <tbody>
                            {currentItems.map((reservation) => (
                                <tr key={reservation.reservationId}>
                                    <td>{reservation.reservationId}</td>
                                    <td>{reservation.name}</td>
                                    <td>{reservation.surname}</td>
                                    <td>{reservation.spaceName}</td>
                                    <td>{reservation.spaceType}</td>
                                    <td>{reservation.isHeightAdjustable ? 'Tak' : 'Nie'}</td>
                                    <td>{reservation.floorNum}</td>
                                    <td>{reservation.reservationDate}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <div className="row">
                    <div className="col-md-12">
                        <nav className="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                            <ul className="pagination">
                                {Array.from({ length: Math.ceil(reservations.length / itemsPerPage) }).map((_, index) => (
                                    <li key={index} className={`page-item ${currentPage === index + 1 ? 'active' : ''}`}>
                                    <a className="page-link" href="#!" onClick={() => paginate(index + 1)}>
                                        {index + 1}
                                    </a>
                                    </li>
                                ))}
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </Container>
);
};

export default DashboardBookingListPage;