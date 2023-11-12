import React, {useEffect, useState} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';
import {Button, Container, Image, Modal} from 'react-bootstrap';
import Form from "react-validation/build/form";

import apiReservationService from '../../../services/api-reservation.service';
import apiSpaceService from '../../../services/api-space.service';

const DashboardReportsPage = () => {
    const [formData, setFormData] = useState({
        startDate: '',
        endDate: '',
        floorId: '1',
    });

    // Messages from the server
    const [errorMessage, setErrorMessage] = useState("XD");

    // Modals
    const [showInformationModal, setInformationModal] = useState(false);
    const [showErrorModal, setErrorShowModal] = useState(false);

    // Navigation
    const location = useLocation();
    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);
    const {startDate, endDate} = location.state || {};

    // Default configuration
    useEffect(() => {
        setFormData((prevData) => ({
            ...prevData,
            startDate: startDate || getCurrentDate(),
            endDate: endDate || getCurrentDate,
        }));
    }, [startDate, endDate]);

    // Input change handler
    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setFormData((prevData) => ({...prevData, [name]: value}));
    };

    // Reservation report generation
    const generateReservationReport = (event) => {
        event.preventDefault();
        setInformationModal(false);

        apiReservationService.generateReservationReport(formData.startDate, formData.endDate)
            .then((response) => {
                // Otwieramy plik PDF w nowej karcie
                const blob = new Blob([response.data], {type: 'application/pdf'});
                const url = window.URL.createObjectURL(blob);
                window.open(url);
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

    // Space report generation
    const generateSpaceReport = (event) => {
        event.preventDefault();
        setInformationModal(false);

        apiSpaceService.generateSpaceReport(formData.floorId)
            .then((response) => {
                // Otwieramy plik PDF w nowej karcie
                const blob = new Blob([response.data], {type: 'application/pdf'});
                const url = window.URL.createObjectURL(blob);
                window.open(url);
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

    const getCurrentDate = () => {
        const dateObj = new Date();
        const formattedDate = dateObj.toISOString().substr(0, 10); // Format: YYYY-MM-DD
        return formattedDate;
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
                                    Wygeneruj Raport Rezerwacji
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
                                    Wygeneruj Raport Miejsc
                                </button>
                            </Form>
                        </div>
                    </div>
                </div>
                {showInformationModal ? (
                <div className="card shadow">
                    <div className="card-header py-3">
                        <div>
                            <h4><center>{errorMessage}</center></h4>
                        </div>
                    </div>
                </div>) : null}
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

export default DashboardReportsPage;