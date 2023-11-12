import React, {useEffect, useState} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';
import {Button, Container, Image, Modal} from 'react-bootstrap';
import Form from "react-validation/build/form";

import apiReservationService from '../../../services/api-reservation.service';

const DashboardBookingListPage = () => {
    const [reservations, setReservations] = useState([]);

    const [formData, setFormData] = useState({
        startDate: '',
        endDate: '',
    });

    // Messages from the server
    const [errorMessage, setErrorMessage] = useState("");

    // Modals
    const [showInformationModal, setInformationModal] = useState(false);
    const [showErrorModal, setErrorShowModal] = useState(false);

    // Pagination
    const [itemsPerPage] = useState(10);
    const [currentPage, setCurrentPage] = useState(1);

    // Navigation
    const location = useLocation();
    const navigate = useNavigate();

    const {startDate, endDate} = location.state || {};

    // Default configuration
    useEffect(() => {
        setFormData((prevData) => ({
            ...prevData,
            spaceType: "Tech",
            startDate: startDate || getCurrentDate(),
            endDate: endDate || getCurrentDate,
        }));
    }, [startDate, endDate]);

    // Pagination
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = reservations.slice(indexOfFirstItem, indexOfLastItem);

    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // Input change handler
    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value
        }));
    };

    // Search handler
    const handleSearch = (event) => {
        setInformationModal(false);
        event.preventDefault();
        apiReservationService.getReservationDetails(formData.startDate, formData.endDate)
            .then((response) => {
                setReservations(response.data.sort((a, b) => {
                    return new Date(a.reservationDate) - new Date(b.reservationDate)
                }))
            })
            .catch((error) => {
                if (error.response && error.response.status === 404) {
                    setErrorMessage(error.response.data.message);
                    setInformationModal(true);
                } else {
                    setErrorMessage(error.response.data.message);
                    setErrorShowModal(true);
                }
            });
    };

    // Navigation: Go to the home page
    const handleNavigationHomePage = () => {
        navigate("/dashboard/home");
        window.location.reload();
    }

    // Navigation: Reload the page
    const handleNavigationReloadPage = () => {
        window.location.reload();
    }

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
                                {!showInformationModal && currentItems.map((reservation) => (
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
                                <nav
                                    className="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                                    <ul className="pagination">
                                        {!showInformationModal && Array.from({length: Math.ceil(reservations.length / itemsPerPage)}).map((_, index) => (
                                            <li key={index}
                                                className={`page-item ${currentPage === index + 1 ? 'active' : ''}`}>
                                                <a className="page-link" href="#!" onClick={() => paginate(index + 1)}>
                                                    {index + 1}
                                                </a>
                                            </li>
                                        ))}
                                    </ul>
                                </nav>
                                <div>
                                    {showInformationModal ? (
                                        <h4><center>{errorMessage}</center></h4>
                                    ) : null}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Modal show={showErrorModal} onHide={handleNavigationReloadPage}>
                <Modal.Header closeButton>
                    <Modal.Title>Error</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <b>Error message:</b><br/> {errorMessage}<br/>
                    <b>More informations:</b><br/>
                    Please reload the page. If the problem persists, please contact the administrator.
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleNavigationHomePage}>
                        Go to Home Page
                    </Button>
                    <Button variant="danger" onClick={handleNavigationReloadPage}>
                        Reload Page
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default DashboardBookingListPage;