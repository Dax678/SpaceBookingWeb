import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Button, Container, Image, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import './FloorPlan.css';

import apiFloorService from  '../../../services/api-floor.service';
import apiReservationService from '../../../services/api-reservation.service';
import apiSpaceService from '../../../services/api-space.service';

import AuthService from "../../../services/auth.service";

import SeatData from "../../SeatData";

const Seat = ({ name, position, selected, disabled, onClick, available }) => {
  let buttonClass = 'seat-button rounded-circle open';

  // if (selected) {
  //   buttonClass += ' selected';
  // } else if (available) {
  //   buttonClass += ' notAvailable';
  // } else {
  //   buttonClass += ' open';
  // }

  const selectedButtonStyle = {
    backgroundColor: '#0082ff',
    borderColor: '#0082ff',
    color: 'white'
  };

  const disabledButtonStyle = {
    backgroundColor: 'red', // Tło przycisku wyłączonego
    border: 'red', // Obramowanie przycisku wyłączonego
    color: 'white', // Kolor tekstu przycisku wyłączonego
  };

  return (
    <Button
      className={buttonClass}
      style={{
        top: position.top,
        left: position.left,
        padding: '0',
        fontSize: '12px',
        ...(disabled ? selectedButtonStyle : {}),
        ...(available ? disabledButtonStyle : {}),
    }}
      disabled={disabled || available}
      onClick={onClick}
    >
      {name}
    </Button>
  );
};

const FloorPlan = () => {
  const [selectedSeat, setSelectedSeat] = useState([]);
  const [disabledSpaces, setDisabledSpaces] = useState([]);
  const [showConfirmationModal, setConfirmationShowModal] = useState(false);
  const [seatInfo, setSeatInfo] = useState({ id: '', name: '', floorId: '', position: { top: '0', left: '0' }, isSelected: false, isAvailable: false });
  const [reservations, setReservations] = useState([]);
  const [seatData, setSeatData] = useState(SeatData);

  const [showSuccessModal, setSuccessShowModal] = useState(false);
  const [showFailureModal, setFailureShowModal] = useState(false);

  let navigate = useNavigate();
  const location = useLocation();
  const { floorId, date } = location.state || {};

  useEffect(() => {
    apiFloorService.getFloorReservations(floorId, date)
      .then((response) => {
        setReservations(response.data);
        const reservedSeats = response.data.map(reservation => reservation.spaceId);
        setSelectedSeat(reservedSeats);
      })
      .catch((error) => {
        console.error('Błąd podczas pobierania rezerwacji:', error);
      });
  }, [floorId, date]);

  useEffect(() => {
    apiSpaceService.getDisabledSpaces(false)
        .then((response) => {
          const responseList = response.data.map(space => space.id);
          setDisabledSpaces(responseList);
        })
        .catch((error) => {
          console.error('Błąd podczas pobierania miejsc:', error);
        });
  }, []);


  useEffect(() => {
    const updatedSeatData = seatData.map(seat => ({
      ...seat,
      isSelected: selectedSeat.includes(seat.id),
      isAvailable: disabledSpaces.includes(seat.id),
    }));
    setSeatData(updatedSeatData);
  }, [selectedSeat, disabledSpaces]);

  const handleSeatClick = (id, name, floorId, position) => {
      setSeatInfo({id, name, floorId, position});
      setConfirmationShowModal(true);
  };

  const handleReservation = () => {
    if (selectedSeat !== null) {
      const reservationData = {
        seatNumber: selectedSeat,
      };

      const user = AuthService.getCurrentUser();

      apiReservationService.bookSpace(user.id, seatInfo.id, date, true)
          .then((response) => {
            setSuccessShowModal(true)
          })
          .catch((error) => {
            setConfirmationShowModal(false);
            setFailureShowModal(true);
          });
    }
  };

  const handleCloseModal = () => {
    setConfirmationShowModal(false);
  };

  const handleNavigationHomePage = () => {
    setConfirmationShowModal(false);
    navigate("/home");
    window.location.reload();
  }

  const handleNavigationReservationListPage = () => {
    setConfirmationShowModal(false);
    navigate("/bookingList");
    window.location.reload();
  }

  const handleFailure = () => {
    window.location.reload();
  }

  return (
    <Container>
      <h1>Floor Plan</h1>
      <div className="floor-plan-container">
        <Image
            src={`/floor-${floorId}.png`}
            alt="Floor Plan"
            className="floor-plan-image" />
        {seatData.map(seat => (
          <Seat
            key={seat.id}
            name={seat.name}
            position={seat.position}
            selected={seat.isSelected}
            disabled={seat.isSelected}
            available={seat.isAvailable}
            onClick={() => handleSeatClick(seat.id, seat.name, seat.position)}
          />
        ))}
        
      </div>
      <Modal show={showConfirmationModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Reservation</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Do you want to reserve {seatInfo.name} {seatInfo.number}?
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleReservation}>
            Reserve
          </Button>
        </Modal.Footer>
      </Modal>

      <Modal show={showSuccessModal} onHide={handleNavigationHomePage}>
        <Modal.Header closeButton>
          <Modal.Title>Success Reservation</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Your reservation has been saved.
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleNavigationHomePage}>
            Go to Home Page
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

export default FloorPlan;
