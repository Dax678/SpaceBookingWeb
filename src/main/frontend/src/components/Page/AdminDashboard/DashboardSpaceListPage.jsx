import React, {useState} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';
import {Button, Container, Modal} from 'react-bootstrap';
import Form from "react-validation/build/form";

import apiSpaceService from './../../../services/api-space.service';

const DashboardSpaceListPage = () => {
    const [spaces, setSpaces] = useState([]);
    const [statusChangeInfo, setStatusChangeInfo] = useState({
        "id": 1,
        "floorId": "",
        "name": "",
        "type": "",
        "monitorNumber": "",
        "isHeightAdjustable": false,
        "isAvailable": false
    });
    const [formData, setFormData] = useState({
        floorId: '1',
    });

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
    const location = useLocation();
    let navigate = useNavigate();

    // Pagination
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(10);

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = spaces.slice(indexOfFirstItem, indexOfLastItem);

    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // Input change handler
    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setFormData((prevData) => ({...prevData, [name]: value}));
    };

    // Search handler
    const handleSearch = (event) => {
        event.preventDefault();
        apiSpaceService.getSpaceListById(formData.floorId)
            .then((response) => {
                setLoading(false);
                setSpaces(response.data.sort((a, b) => {
                    return a.name.localeCompare(b.name);
                }))
            })
            .catch((error) => {
                if(error.response && error.response.status === 404) {
                    setErrorMessage(error.response.data.message);
                    setInformationModal(true);
                } else {
                    setErrorMessage(error.response.data.object);
                    setErrorShowModal(true);
                    setLoading(false);
                }
            });
    };

    // Status change handler
    const handleStatusChange = (event) => {
        event.preventDefault();
        setConfirmationShowModal(false);
        setLoading(true);
        apiSpaceService.changeSpaceStatus(
            statusChangeInfo.id,
            statusChangeInfo.floorId,
            statusChangeInfo.name,
            statusChangeInfo.type,
            statusChangeInfo.monitorNumber,
            statusChangeInfo.isHeightAdjustable,
            !statusChangeInfo.isAvailable
        ).then(
            (response) => {
                setSuccessMessage(response.data.message);
                setSuccessShowModal(true);
                setLoading(false);
            },
            (error) => {
                if(error.response && error.response.status === 404) {
                    setErrorMessage(error.response.data.message);
                    setInformationModal(true);
                } else {
                    setErrorMessage(error.response.data.message);
                    setErrorShowModal(true);
                    setLoading(false);
                }
            }
        );
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
                                {!showInformationModal && currentItems.map((space) => (
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
                                                    setStatusChangeInfo({
                                                        id: space.id,
                                                        floorId: space.floorId,
                                                        name: space.name,
                                                        type: space.type,
                                                        monitorNumber: space.monitorNumber,
                                                        isHeightAdjustable: space.isHeightAdjustable,
                                                        isAvailable: space.isAvailable
                                                    });
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
                                <nav
                                    className="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                                    <ul className="pagination">
                                        {!showInformationModal && Array.from({length: Math.ceil(spaces.length / itemsPerPage)}).map((_, index) => (
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
            <Modal show={showConfirmationModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Space Status Change</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Are you sure you want to change space's status to <b>{statusChangeInfo.isAvailable ? 'unavailable' : 'available'}</b>?
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
                    <Modal.Title>Space status has been changed</Modal.Title>
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

export default DashboardSpaceListPage;