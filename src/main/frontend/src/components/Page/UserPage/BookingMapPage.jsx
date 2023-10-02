import React, { useState, useEffect, useRef } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { Button, Container, Image, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import './FloorPlan.css';

import apiFloorService from  '../../../services/api-floor.service';
import apiReservationService from '../../../services/api-reservation.service';
import apiSpaceService from '../../../services/api-space.service';

import AuthService from "../../../services/auth.service";

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
  const [seatInfo, setSeatInfo] = useState({ id: '', name: '', position: { top: '0', left: '0' }, isSelected: false, isAvailable: false });
  const [reservations, setReservations] = useState([]);
  const [seatData, setSeatData] = useState([
    // SEATS PART 2
    { id: 1, name: 'F1_001', position: { top: '60px', left: '270px' }, isSelected: false, isAvailable: true },
    { id: 2, name: 'F1_002', position: { top: '110px', left: '270px' }, isSelected: false, isAvailable: true },
    { id: 3, name: 'F1_003', position: { top: '160px', left: '270px' }, isSelected: false, isAvailable: true },
    
    { id: 4, name: 'F1_004', position: { top: '60px', left: '370px' }, isSelected: false, isAvailable: true },
    { id: 5, name: 'F1_005', position: { top: '110px', left: '370px' }, isSelected: false, isAvailable: true },
    { id: 6, name: 'F1_006', position: { top: '160px', left: '370px' }, isSelected: false, isAvailable: true },

    { id: 7, name: 'F1_007', position: { top: '60px', left: '440px' }, isSelected: false, isAvailable: true },
    { id: 8, name: 'F1_008', position: { top: '110px', left: '440px' }, isSelected: false, isAvailable: true },
    { id: 9, name: 'F1_009', position: { top: '160px', left: '440px' }, isSelected: false, isAvailable: true },

    { id: 10, name: 'F1_010', position: { top: '60px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 11, name: 'F1_011', position: { top: '110px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 12, name: 'F1_012', position: { top: '160px', left: '540px' }, isSelected: false, isAvailable: true },

    { id: 13, name: 'F1_013', position: { top: '60px', left: '620px' }, isSelected: false, isAvailable: true },
    { id: 14, name: 'F1_014', position: { top: '110px', left: '620px' }, isSelected: false, isAvailable: true },
    { id: 15, name: 'F1_015', position: { top: '160px', left: '620px' }, isSelected: false, isAvailable: true },

    { id: 16, name: 'F1_016', position: { top: '60px', left: '720px' }, isSelected: false, isAvailable: true },
    { id: 17, name: 'F1_017', position: { top: '110px', left: '720px' }, isSelected: false, isAvailable: true },
    { id: 18, name: 'F1_018', position: { top: '160px', left: '720px' }, isSelected: false, isAvailable: true },

    // SEATS PART 2

    { id: 19, name: 'F1_019', position: { top: '610px', left: '270px' }, isSelected: false, isAvailable: true },
    { id: 20, name: 'F1_020', position: { top: '660px', left: '270px' }, isSelected: false, isAvailable: true },
    { id: 21, name: 'F1_021', position: { top: '710px', left: '270px' }, isSelected: false, isAvailable: true },

    { id: 22, name: 'F1_022', position: { top: '610px', left: '370px' }, isSelected: false, isAvailable: true },
    { id: 23, name: 'F1_023', position: { top: '660px', left: '370px' }, isSelected: false, isAvailable: true },
    { id: 24, name: 'F1_024', position: { top: '710px', left: '370px' }, isSelected: false, isAvailable: true },

    { id: 25, name: 'F1_025', position: { top: '610px', left: '440px' }, isSelected: false, isAvailable: true },
    { id: 26, name: 'F1_026', position: { top: '660px', left: '440px' }, isSelected: false, isAvailable: true },
    { id: 27, name: 'F1_027', position: { top: '710px', left: '440px' }, isSelected: false, isAvailable: true },

    { id: 28, name: 'F1_028', position: { top: '610px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 29, name: 'F1_029', position: { top: '660px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 30, name: 'F1_030', position: { top: '710px', left: '540px' }, isSelected: false, isAvailable: true },

    { id: 31, name: 'F1_031', position: { top: '610px', left: '620px' }, isSelected: false, isAvailable: true },
    { id: 32, name: 'F1_032', position: { top: '660px', left: '620px' }, isSelected: false, isAvailable: true },
    { id: 33, name: 'F1_033', position: { top: '710px', left: '620px' }, isSelected: false, isAvailable: true },

    { id: 34, name: 'F1_034', position: { top: '610px', left: '720px' }, isSelected: false, isAvailable: true },
    { id: 35, name: 'F1_035', position: { top: '660px', left: '720px' }, isSelected: false, isAvailable: true },
    { id: 36, name: 'F1_036', position: { top: '710px', left: '720px' }, isSelected: false, isAvailable: true },

    // ROOMS

    { id: 37, name: 'F1_037', position: { top: '385px', left: '480px' }, isSelected: false, isAvailable: true },
    { id: 38, name: 'F1_038', position: { top: '385px', left: '820px' }, isSelected: false, isAvailable: true },

    { id: 39, name: 'F1_101', position: { top: '110px', left: '1010px' }, isSelected: false, isAvailable: true },
    { id: 40, name: 'F1_102', position: { top: '650px', left: '1010px' }, isSelected: false, isAvailable: true },
  ]);

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

  const handleSeatClick = (id, name, position) => {
      setSeatInfo({id, name, position});
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
