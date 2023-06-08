import React from "react";

function ProfilePage() {
  return (
    <div className="row">
      <div className="col-sm-4">
          <div className="row text-center">
              <div className="col-md-12 mt-5">
                  <img src="https://via.placeholder.com/250" alt="Zdjęcie użytkownika" />
                  <form className="row" method="post">
                    <div class="form-group">
                      <label for="image">Prześlij zdjęcie</label><br/>
                      <input type="file" class="form-control-file" id="image" />
                    </div>
                  </form>
              </div>
          </div>
      </div>
      <div className="col-sm-8">
          <h2 className="text-center">Dane użytkownika</h2>
              <div className="form-group">
                  <label for="name">Imię:</label>
                  <input type="text" className="form-control" id="name" />
              </div>
              <div className="form-group">
                  <label for="surname">Nazwisko:</label>
                  <input type="text" className="form-control" id="surname" />
              </div>
          <form className="row" method="post" action="@{/api/changeEmail}">
              <div className="form-group col-md-8">
                  <label for="email">Email:</label>
                  <input type="email" className="form-control" id="email" />
              </div>
              <div className="text-center col-md-4 mt-4">
                  <input type="submit" className="btn bg-primary text-white" value="Zmień Email" />
              </div>
          </form>
          <form className="row" method="post" action="@{/api/changePassword}">
              <div className="form-group col-md-8">
                  <label for="password">Hasło:</label>
                  <input type="password" className="form-control" id="password" />
              </div>
              <div className="text-center col-md-4 mt-4">
                  <input type="submit" className="btn bg-primary text-white" value="Zmień Hasło" />
              </div>
          </form>
          <form className="row" method="post" action="@{/api/changePhoneNumber}">
              <div className="form-group col-md-8">
                  <label for="phoneNumber">Numer telefonu:</label>
                  <input type="tel" className="form-control" id="phoneNumber" />
              </div>
              <div className="text-center col-md-4 mt-4">
                  <input type="submit" className="btn bg-primary text-white" value="Zmień Numer Telefonu" />
              </div>
          </form>
          <form className="row" method="post" action="@{/api/changeAddress}">
              <div className="form-group col-md-8">
                  <label for="address">Adres:</label>
                  <input type="text" className="form-control" id="address" />
              </div>
              <div className="text-center col-md-4 mt-4">
                  <input type="submit" className="btn bg-primary text-white" value="Zmień Adres" />
              </div>
          </form>
      </div>
    </div>
  );
};

export default ProfilePage;