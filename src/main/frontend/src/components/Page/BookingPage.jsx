import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Form from "react-validation/build/form";
import bookingService from '../../services/booking.service';
import AuthService from "./../../services/auth.service";

function BookingPage() {
  const [formData, setFormData] = useState({
    floorName: '',
    spaceType: '',
    date: '',
  });
  let navigate = useNavigate();
  const [spaces, setSpaces] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  // const [currentPage, setCurrentPage] = useState(1);
  // const [itemsPerPage] = useState(5);
  // const indexOfLastItem = currentPage * itemsPerPage;
  // const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  // var currentItems;

  useEffect(() => {
    const setDefaultValues = () => {
      const defaultFormData = {
        floorName: '1',
        spaceType: 'Tech',
        date: getCurrentDate(),
      };
      setFormData(defaultFormData);
      //handleSearch();
    };
    setDefaultValues();
    //console.log(formData);
  }, []);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSearch = (event) => {
      event.preventDefault();
      bookingService.getSpaceList(formData.floorName, formData.spaceType, formData.date)
      .then((response) => {
        setSpaces(response.data);
      })
      .catch((error) => {
        console.error('Błąd podczas pobierania miejsc:', error);
      });
    // currentItems = spaces.slice(indexOfFirstItem, indexOfLastItem);
  };

  const handleBooking = (event, spaceId) => {
    event.preventDefault();
    const user = AuthService.getCurrentUser();

    setLoading(true);

    bookingService.bookSpace(user.id, spaceId, formData.date, true).then(
      () => {
        navigate("/home");
        window.location.reload();
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

  const getCurrentDate = () => {
    const dateObj = new Date();
    const formattedDate = dateObj.toISOString().substr(0, 10); // Format: YYYY-MM-DD
    return formattedDate;
  };

    // Zmienia stronę
    // const paginate = (pageNumber) => {
    // setCurrentPage(pageNumber);
    // };

return (
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
                      <option value="Tech">Tech</option>
                      <option value="Standard">Standard</option>
                      <option value="Parking">Parking</option>
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
                <button className="d-flex justify-content-center btn btn-primary d-block btn-user w-50" type="submit">Potwierdź</button>
              </Form>
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
                  {spaces.map((space) => (
                    <tr key={space.id}>
                      <td>{space.name}</td>
                      <td>{space.type}</td>
                      <td>{space.monitorNumber ? 'Tak' : 'Nie'}</td>
                      <td>{space.heightAdjustable}</td>
                      <td>
                      <Form className="form" onSubmit={(event) => handleBooking(event, space.id)}>
                        <button className="btn btn-primary d-block btn-user w-100" type="submit" data-bs-toggle="modal" data-bs-target="#successModal">Rezerwuj</button>
                      </Form>
                    </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    {message && (
                      <div className="form-group">
                        <div className="alert alert-danger" role="alert">
                          {message}
                        </div>
                      </div>
                    )}
    <div className="modal fade" id="successModal" data-bs-backdrop="static" data-bs-keyboard="false" tabIndex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="staticBackdropLabel">Modal title</h5>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div className="modal-body">
            <p>
                Rezerwacja zakończyła się pomyślnie!
            </p>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Zamknij</button>
            <button type="button" className="btn btn-primary">Wyświetl wszystkie rezerwacje</button>
          </div>
        </div>
      </div>
    </div>
  </div>
);
};
  
  export default BookingPage;