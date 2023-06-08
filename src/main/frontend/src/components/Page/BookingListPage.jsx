import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AuthService from "./../../services/auth.service";
import BookingListService from "./../../services/bookingList.service";

function BookingListPage() {
    const [reservations, setReservations] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5);

    useEffect(() => {
        const user = AuthService.getCurrentUser();

      const fetchReservations = async () => {
        try {
        const response = await BookingListService.getBookingList(user.id);
        //Zapis posortowanych danych do zmiennej
        setReservations(response.data.sort((a, b) => {
            return new Date(a.reservation_date) - new Date(b.reservation_date);
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

  return (
    <div>
      <h3 className="text-dark mb-4 text-center">Moje rezerwacje</h3>
      <div className="card shadow">
          <div className="card-header py-3">
              <p className="text-primary m-0 fw-bold">Lista rezerwacji</p>
          </div>
          <div className="card-body">
              <div className="row">
                  <div className="col-md-12">
                      <div className="text-md-end dataTables_filter" id="dataTable_filter">
                          <label className="form-label">
                              <input type="search" className="form-control form-control-sm" aria-controls="dataTable" placeholder="Search"></input>
                          </label>
                      </div>
                  </div>
              </div>
              <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
                  <table className="table my-0">
                      <thead>
                      <tr>
                          <th>Nazwa</th>
                          <th>Typ</th>
                          <th>Piętro</th>
                          <th>Regulacja wysokości</th>
                          <th>Data rezerwacji</th>
                      </tr>
                      </thead>
                      <tbody>
                        {currentItems.map((reservation) => (
                            <tr key={reservation.id}>
                                <td>{reservation.space_name}</td>
                                <td>{reservation.space_type}</td>
                                <td>{reservation.floor_num}</td>
                                <td>{reservation.height_adjustable ? 'Tak' : 'Nie'}</td>
                                <td>{reservation.reservation_date}</td>
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
                          </tr>
                      </tfoot>
                  </table>
              </div>
              <div className="row">
                  {/* <div className="col-md-2 align-self-center">
                      <div id="dataTable_length" className="dataTables_length" aria-controls="dataTable"><label className="form-label">Show&nbsp;
                          <select className="d-inline-block form-select form-select-sm">
                              <option value="5">5</option>
                              <option value="10">10</option>
                              <option value="25">25</option>
                              <option value="100">100</option>
                          </select>&nbsp;</label>
                      </div>
                  </div> */}
                  {/* <div className="col-md-6 align-self-center">
                      <p id="dataTable_info" className="dataTables_info" role="status" aria-live="polite">Showing 1 to 10 of 27</p>
                  </div> */}
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
    );
  };
  
  export default BookingListPage;