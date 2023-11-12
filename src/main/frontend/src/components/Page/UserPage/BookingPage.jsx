import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import Form from "react-validation/build/form";
import {Button, Container, Modal} from 'react-bootstrap';

import apiReservationService from '../../../services/api-reservation.service';
import AuthService from "../../../services/auth.service";
import apiSpaceService from "../../../services/api-space.service";

function BookingPage() {
    const [formData, setFormData] = useState({
        floorName: '1',
        spaceType: 'Standard',
        date: '',
    });
    const [spaces, setSpaces] = useState([]);
    const [bookingInfo, setBookingInfo] = useState();

    // Messages from the server
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    // Modals
    const [showConfirmationModal, setConfirmationShowModal] = useState(false);
    const [showSuccessModal, setSuccessShowModal] = useState(false);
    const [showErrorModal, setErrorShowModal] = useState(false);
    const [showInformationModal, setInformationModal] = useState(false);


    // Pagination
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(10);

    //Navigation
    let navigate = useNavigate();
    const location = useLocation();

    const {floorName, date} = location.state || {};

    // Get all spaces
    useEffect(() => {
        if (formData.floorName === "4" || formData.floorName === "5") {
            setFormData((prevData) => ({
                ...prevData,
                spaceType: "Standard",
                date: date || getCurrentDate(),
            }));
        } else {
            setFormData((prevData) => ({
                ...prevData,
                spaceType: "Standard",
                date: date || getCurrentDate(),
            }));
        }
    }, [floorName, date]);

    // Input change handler
    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setFormData((prevData) => ({...prevData, [name]: value}));

        if (value === '1')
            setFormData((prevData) => ({
                ...prevData,
                spaceType: 'Standard',
            }))
    };

    // Search handler
    const handleSearch = (event) => {
        setInformationModal(false);
        event.preventDefault();
        apiSpaceService.getSpaceList(formData.floorName, formData.spaceType, formData.date, true)
            .then((response) => {
                setSpaces(response.data.sort((a, b) => {
                    return a.name.localeCompare(b.name);
                }));
            })
            .catch((error) => {
                setErrorMessage(error.response.data.message);
                setInformationModal(true);
            });
    };

    // Booking handler
    const handleBooking = (event) => {
        event.preventDefault();
        const user = AuthService.getCurrentUser();
        setLoading(true);

        apiReservationService.bookSpace(user.id, bookingInfo, formData.date, true).then(
            (response) => {
                setSuccessMessage(response.data.message)
                setConfirmationShowModal(false);
                setSuccessShowModal(true);
            },
            (error) => {
                setErrorMessage(error.response.data.message);
                setErrorShowModal(true);
            }
        );
    };

    // Pagination
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = spaces.slice(indexOfFirstItem, indexOfLastItem);

    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // Floor change handler
    const handleFloorPageChange = () => {
        navigate(`/booking/map?floorId=${formData.floorName}&date=${formData.date}`, {
            state: {
                floorId: formData.floorName,
                date: formData.date,
            },
        });
    }

    //Modal Settings
    const handleNavigationHomePage = () => {
        setConfirmationShowModal(false);
        navigate("/home");
        window.location.reload();
    }

    // Navigation: Reload the page
    const handleNavigationReloadPage = () => {
        setConfirmationShowModal(false);
        window.location.reload();
    }

    // Modal settings
    const handleCloseModal = () => {
        setConfirmationShowModal(false);
    };

    const getCurrentDate = () => {
        const dateObj = new Date();
        // Format: YYYY-MM-DD
        return dateObj.toISOString().substr(0, 10);
    };

    // Space types per floor
    const availableSpaceTypes = {
        '1': ['Standard'],
        '2': ['Tech', 'Standard', 'Room'],
        '3': ['Tech', 'Standard', 'Room'],
        '4': ['Standard', 'Motorbikes'],
        '5': ['Standard', 'Motorbikes'],
    };

    return (
        <Container>
            <div>
                <h3 className="text-dark mb-4 text-center">Rezerwuj Miejsce</h3>
                <div className="card shadow">
                    <div className="card-body">
                        <div className="row">
                            <div className="col-md-4">
                                <div className="table-responsive table mt-2" role="grid"
                                     aria-describedby="dataTable_info">
                                    <h3 className="text-dark mb-4 text-center">Szukaj</h3>
                                    <Form className="form" onSubmit={handleSearch}>
                                        <p>
                                            <label htmlFor="floorName">Wybierz piętro</label>
                                            <select
                                                id="floorName"
                                                name="floorName"
                                                className="form-control"
                                                value={formData.floorName}
                                                onChange={handleInputChange}
                                            >
                                                <option value="1">Piętro 1</option>
                                                <option value="2">Piętro 2</option>
                                                <option value="3">Piętro 3</option>
                                                <option value="4">Parking -1</option>
                                                <option value="5">Parking -2</option>
                                            </select>
                                        </p>
                                        <p>
                                            <label htmlFor="spaceType">Wybierz typ miejsca</label>
                                            <select
                                                id="spaceType"
                                                name="spaceType"
                                                className="form-control"
                                                value={formData.spaceType}
                                                onChange={handleInputChange}
                                            >
                                                {availableSpaceTypes[formData.floorName] ? (
                                                    availableSpaceTypes[formData.floorName].map((type) => (
                                                        <option key={type} value={type}>
                                                            {type}
                                                        </option>
                                                    ))
                                                ) : null}
                                            </select>
                                        </p>
                                        <p>
                                            <label htmlFor="date">Ustaw date</label>
                                            <input
                                                className="form-control"
                                                id="date"
                                                type="date"
                                                name="date"
                                                value={formData.date}
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
                                    <button
                                        className="d-flex justify-content-center btn btn-primary d-block btn-user w-100 mt-5"
                                        type="button"
                                        onClick={handleFloorPageChange}>
                                        Wyświetl Plan Piętra
                                    </button>
                                </div>
                            </div>
                            <div className="col-md-8">
                                <div className="table-responsive table mt-2" role="grid"
                                     aria-describedby="dataTable_info">
                                    <table className="table my-0 text-center">
                                        <thead>
                                        <tr>
                                            <th>Nazwa</th>
                                            <th>Typ</th>
                                            <th>Liczba monitorów</th>
                                            <th>Regulacja wysokości</th>
                                            <th>Rezerwuj</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {!showInformationModal && currentItems.map((space) => (
                                            <tr key={space.id}>
                                                <td>{space.name}</td>
                                                <td>{space.type}</td>
                                                <td>{space.monitorNumber}</td>
                                                <td>{space.isHeightAdjustable ? 'Tak' : 'Nie'}</td>
                                                <td>
                                                    <button
                                                        className="btn btn-primary d-block btn-user w-100"
                                                        type="submit"
                                                        data-bs-toggle="modal"
                                                        data-bs-target="#successModal"
                                                        onClick={() => {
                                                            setBookingInfo(space.id);
                                                            setConfirmationShowModal(true);
                                                        }}
                                                    >
                                                        Rezerwuj
                                                    </button>
                                                </td>
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
                                                {!showInformationModal && Array.from({length: Math.ceil(spaces.length / itemsPerPage)}).map((_, index) => (
                                                    <li key={index}
                                                        className={`page-item ${currentPage === index + 1 ? 'active' : ''}`}>
                                                        <a className="page-link" href="#!"
                                                           onClick={() => paginate(index + 1)}>
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
                </div>
            </div>
            <Modal show={showConfirmationModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Reservation</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Are you sure you want to book this space?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleBooking}>
                        Book Space
                    </Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showSuccessModal} onHide={handleNavigationReloadPage}>
                <Modal.Header closeButton>
                    <Modal.Title>Space has been reserved</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {successMessage}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleNavigationHomePage}>
                        Go to Home Page
                    </Button>
                    <Button variant="primary" onClick={handleNavigationReloadPage}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
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

export default BookingPage;