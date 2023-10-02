import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import Form from "react-validation/build/form";
import {Button, Container, Modal} from 'react-bootstrap';

import apiReservationService from '../../../services/api-reservation.service';
import AuthService from "../../../services/auth.service";
import apiSpaceService from "../../../services/api-space.service";

function BookingPage() {
  const [showSuccessModal, setSuccessShowModal] = useState(false);
  const [showFailureModal, setFailureShowModal] = useState(false);

  const [formData, setFormData] = useState({
    floorName: '1',
    spaceType: 'Tech',
    date: '',
  });
  let navigate = useNavigate();
  const [spaces, setSpaces] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);

  const location = useLocation();
  const { floorNumber, date } = location.state || {};

  useEffect(() => {
    if (formData.floorName === "4" || formData.floorName === "5") {
      setFormData((prevData) => ({
        ...prevData,
        spaceType: "Parking",
        date: date || getCurrentDate(),
      }));
    } else {
      setFormData((prevData) => ({
        ...prevData,
        spaceType: "Tech",
        date: date || getCurrentDate(),
      }));
    }
  }, [floorNumber, date]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSearch = (event) => {
      event.preventDefault();
      apiSpaceService.getSpaceList(formData.floorName, formData.spaceType, formData.date, true)
      .then((response) => {
        setSpaces(response.data.sort((a, b) => {
          return a.name.localeCompare(b.name);
        }));
      })
      .catch((error) => {
        console.error('Błąd podczas pobierania miejsc:', error);
      });
  };

  const handleBooking = (event, spaceId) => {
    event.preventDefault();
    const user = AuthService.getCurrentUser();

    setLoading(true);

    apiReservationService.bookSpace(user.id, spaceId, formData.date, true).then(
      () => {
        setSuccessShowModal(true);
      },
      (error) => {
        setFailureShowModal(true);

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

  // Oblicz indeksy początkowy i końcowy dla bieżącej strony
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = spaces.slice(indexOfFirstItem, indexOfLastItem);

  // Zmienia stronę
  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };


  const handleFloorPageChange = () => {
    navigate(`/booking/map?floorId=${formData.floorName}&date=${formData.date}`, {
      state: {
        floorId: formData.floorName,
        date: formData.date,
      },
    });
  }

  const handleCloseModal = () => {
    setSuccessShowModal(false);
    window.location.reload();
  };

  const handleNavigationReservationListPage = () => {
    setSuccessShowModal(false);
    navigate("/bookingList");
    window.location.reload();
  }

  const handleFailure = () => {
    window.location.reload();
  }

  const getCurrentDate = () => {
    const dateObj = new Date();
     // Format: YYYY-MM-DD
    return dateObj.toISOString().substr(0, 10);
  };

  const availableSpaceTypes = {
    '1': ['Tech', 'Standard', 'Room'],
    '2': ['Tech', 'Standard'],
    '3': ['Tech', 'Standard'],
    '4': ['Parking'],
    '5': ['Parking'],
  };

return (
  <Container>
  <div>
    <h3 className="text-dark mb-4 text-center">Rezerwuj Miejsce</h3>
    <div className="card shadow">
      <div className="card-body">
        <div className="row">
          <div className="col-md-4">
            <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
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
            <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
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
                  {currentItems.map((space) => (
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
                        onClick={(event) => {
                          handleBooking(event, space.id);
                          setSuccessShowModal(true);
                        }}
                      >
                          Rezerwuj</button>
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
    </div>
  </div>
  <Modal show={showSuccessModal} onHide={handleCloseModal}>
      <Modal.Header closeButton>
          <Modal.Title>Success Reservation</Modal.Title>
      </Modal.Header>
      <Modal.Body>
          Your reservation has been saved.
      </Modal.Body>
      <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
          Close
          </Button>
          <Button variant="primary" onClick={handleNavigationReservationListPage}>
          Go to Reservation List Page
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
  
  export default BookingPage;