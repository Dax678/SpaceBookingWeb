import React from 'react';
import Form from "react-validation/build/form";

function BookingPage() {
return (
  <div>
    <h3 className="text-dark mb-4 text-center">Rezerwuj Miejsce</h3>
    <div className="card shadow">
      <div className="card-body">
        <div className="row">
          <div className="col-md-4">
            <div className="table-responsive table mt-2" role="grid" aria-describedby="dataTable_info">
              <h3 className="text-dark mb-4 text-center">Szukaj</h3>
              <Form className="form" action="@{/web/booking}" method="get">
                <p>
                  <label htmlFor="floorName">Wybierz piętro</label>
                  <select id="floorName" name="floorName" className="form-control">
                    <option>Wybierz piętro</option>
                    <option>Domyślne pietro</option>
                  </select>
                </p>
                <p>
                  <label htmlFor="spaceType">Wybierz typ miejsca</label>
                  <select id="spaceType" name="spaceType" className="form-control">
                      <option>Wybierz typ miejsca</option>
                      <option>Tech</option>
                      <option>Standard</option>
                      <option>Parking</option>
                  </select>
                </p>
                <p>
                  <label htmlFor="date">Ustaw date</label>
                  <input className="form-control" id="date" type="date" name="date" value="${selectedDate}" required />
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
                  <tr>
                    <td>F2_021</td>
                    <td>Tech</td>
                    <td>2</td>
                    <td>True</td>
                    <td>
                      <Form className="form" action="@{/web/booking/{id}(id=${space.getId()})}" method="post">
                        <label>
                          <input className="invisible" type="date" name="reservationDate" value="${selectedDate}" />
                        </label>
                        <button className="btn btn-primary d-block btn-user w-100" type="submit" data-bs-toggle="modal" data-bs-target="#successModal">Rezerwuj</button>
                      </Form>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
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