import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Container, Image, Modal } from 'react-bootstrap';

import Form from "react-validation/build/form";

import apiReservationService from '../../../services/api-reservation.service';
import AuthService from "../../../services/auth.service";
import apiUserService from "../../../services/api-user.service";

function BookingListPage() {
    const [showConfirmationModal, setConfirmationShowModal] = useState(false);
    const [showSuccessModal, setSuccessShowModal] = useState(false);
    const [deleteReservationInfo, setDeleteReservationInfo] = useState();
    const [reservations, setReservations] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5);
    const [spaces, setSpaces] = useState([]);
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    let navigate = useNavigate();

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        const fetchReservations = async () => {
            try {
                const response = await apiUserService.getUserReservations(user.id, true);
                //console.log(response);
                //Zapis posortowanych danych do zmiennej
                setReservations(response.data.sort((a, b) => {
                    return new Date(a.reservationDate) - new Date(b.reservationDate);
            }));
            } catch (error) {
                console.error('Błąd podczas pobierania rezerwacji:', error);
            }
        };
      fetchReservations();
    }, []);

    // Oblicz indeksy początkowy i końcowy dla bieżącej strony
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = reservations.slice(indexOfFirstItem, indexOfLastItem);

    // Zmienia stronę
    const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
    };

    const handleDeleteBooking = (event, reservation_id) => {
        event.preventDefault();
        const user = AuthService.getCurrentUser();
    
        setLoading(true);
        apiReservationService.changeBookingStatus("false", deleteReservationInfo).then(
          () => {
            setConfirmationShowModal(false);
            setSuccessShowModal(true);
          },
          (error) => {
            const resMessage =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();
    
            setLoading(false);
            setMessage(resMessage);
          }
        );
      };

      const handleCloseModal = () => {
        setConfirmationShowModal(false);
      };

      const handleNavigationHomePage = () => {
        setConfirmationShowModal(false);
        navigate("/home");
        window.location.reload();
      }
    
      const handleNavigationReloadPage = () => {
        setConfirmationShowModal(false);
        window.location.reload();
      }

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
                            <tfoot>
                                <tr>
                                    <td><strong>Nazwa</strong></td>
                                    <td><strong>Typ</strong></td>
                                    <td><strong>Piętro</strong></td>
                                    <td><strong>Regulacja wysokości</strong></td>
                                    <td><strong>Data rozpoczęcia</strong></td>
                                    <td></td>
                                </tr>
                            </tfoot>
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
                Your reservation has been deleted.
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
        </Container>
    );
};
  
  export default BookingListPage;