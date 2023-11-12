import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Button, Container, Image, Modal} from 'react-bootstrap';

import Form from "react-validation/build/form";

import apiReservationService from '../../../services/api-reservation.service';
import AuthService from "../../../services/auth.service";
import apiUserService from "../../../services/api-user.service";

function BookingListPage() {
    const [reservations, setReservations] = useState([]);
    const [spaces, setSpaces] = useState([]);
    const [deleteReservationInfo, setDeleteReservationInfo] = useState();

    //Pagination
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5);

    // Messages from the server
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [loading, setLoading] = useState(false);

    // Modals
    const [showConfirmationModal, setConfirmationShowModal] = useState(false);
    const [showSuccessModal, setSuccessShowModal] = useState(false);
    const [showErrorModal, setErrorShowModal] = useState(false);
    const [showInformationModal, setInformationModal] = useState(false);

    // Navigation
    let navigate = useNavigate();

    // Get all reservations
    useEffect(() => {
        const user = AuthService.getCurrentUser();

        const fetchReservations = async () => {
            const response = await apiUserService.getUserReservations(user.id, true).then(
                (response) => {
                    setSuccessMessage(response.data.message);
                    setReservations(response.data.sort((a, b) => {
                        return new Date(a.reservationDate) - new Date(b.reservationDate);
                    }));
                },
                (error) => {
                    if(error.response && error.response.status === 404) {
                        setErrorMessage(error.response.data.message);
                        setInformationModal(true);
                    }
                    else {
                        setErrorMessage(error.response.data.message);
                        setErrorShowModal(true);
                    }
                }
            )
        }
        fetchReservations();
    }, []);

    // Delete a reservation
    const handleDeleteBooking = (event, reservation_id) => {
        event.preventDefault();
        const user = AuthService.getCurrentUser();

        setLoading(true);
        apiReservationService.changeBookingStatus("false", deleteReservationInfo).then(
            (response) => {
                setSuccessMessage(response.data.message);
                setConfirmationShowModal(false);
                setSuccessShowModal(true);
            },
            (error) => {
                if(error.response && error.response.status === 404) {
                    setErrorMessage(error.response.data.message);
                    setInformationModal(true);
                } else {
                    setErrorMessage(error.response.data.object);
                    setErrorShowModal(true);
                    setLoading(false);
                }
            }
        );
    };

    // Pagination
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = reservations.slice(indexOfFirstItem, indexOfLastItem);

    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // Navigation to the home page
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

    return (
        <Container>
            <div>
                <h3 className="text-dark mb-4 text-center">Moje rezerwacje</h3>
                <div className="card shadow">
                    <div className="card-header py-3">
                        <p className="text-primary m-0 fw-bold">Lista rezerwacji</p>
                    </div>
                    <div className="card-body">
                        <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
                            <table className="table my-0">
                                <thead>
                                <tr>
                                    <th>Nazwa</th>
                                    <th>Typ</th>
                                    <th>Piętro</th>
                                    <th>Regulacja wysokości</th>
                                    <th>Data rezerwacji</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                {currentItems.map((reservation) => (
                                    <tr key={reservation.reservationId}>
                                        <td>{reservation.spaceName}</td>
                                        <td>{reservation.spaceType}</td>
                                        <td>{reservation.floorNum}</td>
                                        <td>{reservation.isHeightAdjustable ? 'Tak' : 'Nie'}</td>
                                        <td>{reservation.reservationDate}</td>
                                        <td>
                                            <button
                                                className="btn btn-primary d-block btn-user w-100"
                                                type="submit"
                                                data-bs-toggle="modal"
                                                data-bs-target="#successModal"
                                                onClick={() => {
                                                    setDeleteReservationInfo(reservation.reservationId);
                                                    setConfirmationShowModal(true);
                                                }}
                                            >
                                                Anuluj rezerwacje
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
                                        {Array.from({length: Math.ceil(reservations.length / itemsPerPage)}).map((_, index) => (
                                            <li key={index}
                                                className={`page-item ${currentPage === index + 1 ? 'active' : ''}`}>
                                                <a className="page-link" href="#!" onClick={() => paginate(index + 1)}>
                                                    {index + 1}
                                                </a>
                                            </li>
                                        ))}
                                    </ul>
                                </nav>
                            </div>
                            <div className="row">
                                <div className="col-md-12">
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
            <Modal show={showConfirmationModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Reservation</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Are you sure you want to delete your booking?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleDeleteBooking}>
                        Delete Booking
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showSuccessModal} onHide={handleNavigationReloadPage}>
                <Modal.Header closeButton>
                    <Modal.Title>Deleted Reservation</Modal.Title>
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

export default BookingListPage;