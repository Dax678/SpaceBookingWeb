import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Button, Container, Image, Modal } from 'react-bootstrap';
import Form from "react-validation/build/form";

import apiSpaceService from './../../../services/api-space.service';

const DashboardSpaceListPage = () => {
  const [spaces, setSpaces] = useState([]);
  const [statusChangeInfo, setStatusChangeInfo] = useState({
    spaceId: '',
    newStatus: '',
  });
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [formData, setFormData] = useState({
      floorId: '1',
    });

  const [showConfirmationModal, setConfirmationShowModal] = useState(false);
  const [showSuccessModal, setSuccessShowModal] = useState(false);
  const [showFailureModal, setFailureShowModal] = useState(false);

  const location = useLocation();
  let navigate = useNavigate();



  // Oblicz indeksy początkowy i końcowy dla bieżącej strony
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = spaces.slice(indexOfFirstItem, indexOfLastItem);

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
      apiSpaceService.getSpaceListById(formData.floorId)
      .then((response) => {
          setSpaces(response.data.sort((a, b) => {
              return a.name.localeCompare(b.name);
          }))
      })
      .catch((error) => {
        console.error('Błąd podczas pobierania miejsc:', error);
      });
  };

  const handleCloseModal = () => {
    setConfirmationShowModal(false);
  };

  const handleStatusChange = (event) => {
    event.preventDefault();

    setLoading(true);
    console.log(statusChangeInfo.spaceId, statusChangeInfo.newStatus)
    apiSpaceService.changeSpaceStatus(statusChangeInfo.spaceId, statusChangeInfo.newStatus).then(
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

        setConfirmationShowModal(false);
        setFailureShowModal(true);

        setLoading(false);
        setMessage(resMessage);
      }
    );
  };

  const handleNavigationHomePage = () => {
    setConfirmationShowModal(false);
    navigate("/dashboard/home");
    window.location.reload();
  }

  const handleNavigationReloadPage = () => {
    setConfirmationShowModal(false);
    window.location.reload();
  }

  const handleFailure = () => {
    window.location.reload();
  }

  return (
    <Container>
        <div>
        <h3 className="text-dark mb-4 text-center">Lista Przestrzeni</h3>
        <div className="card shadow">
            <div className="card-header py-3">
                <p className="text-primary m-0 fw-bold">Szukaj Miejsc</p>
            </div>
            <div className="card-body">
                <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
                <Form className="form" onSubmit={handleSearch}>
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
        <div className="card shadow">
            <div className="card-header py-3">
                <p className="text-primary m-0 fw-bold">Lista przestrzeni</p>
            </div>
            <div className="card-body">
                <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
                    <table className="table my-0">
                        <thead>
                        <tr>
                            <th>ID Miejsca</th>
                            <th>Nazwa</th>
                            <th>Typ</th>
                            <th>Liczba monitorów</th>
                            <th>Regulacja wysokości</th>
                            <th>Czy jest aktywne?</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                            {currentItems.map((space) => (
                                <tr key={space.id}>
                                    <td>{space.id}</td>
                                    <td>{space.name}</td>
                                    <td>{space.type}</td>
                                    <td>{space.monitorNumber}</td>
                                    <td>{space.isHeightAdjustable ? 'Tak' : 'Nie'}</td>
                                    <td>{space.isAvailable ? 'Tak' : 'Nie'}</td>
                                    <td>
                                    <button 
                                            className="btn btn-primary d-block btn-user w-100" 
                                            type="submit" 
                                            data-bs-toggle="modal" 
                                            data-bs-target="#successModal"
                                            onClick={() => {
                                                setStatusChangeInfo({spaceId: space.id, newStatus: !space.isAvailable});
                                                setConfirmationShowModal(true);
                                            }}
                                            >
                                                Zmień Status
                                            </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <div className="row">
                    <div className="col-md-12">
                        <nav className="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                            <ul className="pagination">
                                {Array.from({ length: Math.ceil(spaces.length / itemsPerPage) }).map((_, index) => (
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
                Are you sure you want to change space status?
            </Modal.Body>
            <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
                Cancel
            </Button>
            <Button variant="primary" onClick={handleStatusChange}>
                Change Status
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
        <Modal show={showFailureModal} onHide={handleFailure}>
        <Modal.Header closeButton>
          <Modal.Title>Failure Reservation</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Something goes wrong. Please reload the page.
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleFailure}>
            Refresh Page
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default DashboardSpaceListPage;