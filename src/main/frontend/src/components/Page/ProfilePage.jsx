import React, { useEffect, useState, useRef } from 'react';
import AuthService from "./../../services/auth.service";
import accInformationService from "../../services/accInformations.service";

import { useNavigate } from 'react-router-dom';

function ProfilePage() {
  let navigate = useNavigate();

  const form = useRef();
  const checkBtn = useRef();

  const [informations, setInformations] = useState([]);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phoneNumer, setPhoneNumber] = useState("");
  const [address, setAddress] = useState("");

  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [showModal, setShowModal] = useState(false);

  const openModal = () => {
    setShowModal(showModal => !showModal);
  };

  //TODO: Rozwiazac problem z nieladujacymi się danymi
  useEffect(() => {
    const user = AuthService.getCurrentUser();
  
    const fetchUserInformations = async () => {
      try {
        const response = await accInformationService.getInformation(user.id);
        setInformations(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Błąd podczas pobierania informacji o profilu:', error);
        setLoading(false);
      }
    };
  
    fetchUserInformations();
  }, []);

  const openModalEmailChange = () => {
    setIsModalOpen(true);
  };

  const openModalPasswordChange = () => {
    setIsModalOpen(true);
  };

  const openModalPhoneNumberChange = () => {
    setIsModalOpen(true);
  };

  const openModalAddressChange = () => {
    setIsModalOpen(true);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    setMessage("");
    setLoading(true);

    if (checkBtn.current.context._errors.length === 0) {
      // AuthService.login(username, password).then(
      //   () => {
      //     //Dodac wyskakiwany modal z informacja o pomyslnej zmianie danych osobowych
      //     navigate("/home");
      //     window.location.reload();
      //   },
      //   (error) => {
      //     const resMessage =
      //       (error.response &&
      //         error.response.data &&
      //         error.response.data.message) ||
      //       error.message ||
      //       error.toString();

      //     setLoading(false);
      //     setMessage(resMessage);
      //   }
      // );
    } else {
      setLoading(false);
    }
  };

  return (
    <div className="row">
      <div className="col-sm-4">
          <div className="row text-center">
              <div className="col-md-12 mt-5">
                  <img src={informations.profile_image} alt="Profile Image" />
                  <form className="row" method="post">
                    <div className="form-group">
                      <label htmlFor="image">Prześlij zdjęcie</label><br/>
                      <input type="file" className="form-control-file" id="image" />
                    </div>
                  </form>
              </div>
          </div>
      </div>
      <div className="col-sm-8 text-center">
          <h1 className="text-center mt-3 mb-5">Dane użytkownika</h1>
              <div className='row  mb-5'>
                <div className="form-group col-md-6">
                  <h3>Imię: </h3>
                  <p>{loading ? (
                    'Loading...'
                  ) : (
                    informations.name
                  )
                    }
                    {/* {informations.name ? informations.name : 'Loading...'}</p> */}
                  </p>
                </div>
                <div className="form-group col-md-6">
                <h3>Nazwisko:</h3>
                  <p>{informations.surname ? informations.surname : 'Loading...'}</p>
                </div>
              </div>
              <div className='row  mb-5'>
                <div className="form-group col-md-6">
                  <h3>Email:</h3>
                  <p>{informations.email}Nazwa</p>
                  <button className="btn bg-primary text-white toggle-button" value="Zmień Email" onClick = {e => { this.showModal(e); }}/>
                </div>
                <div className="form-group col-md-6">
                  <h3>Hasło:</h3>
                  <p>*******</p>
                  <input className="btn bg-primary text-white" value="Zmień Hasło" onClick={openModalPasswordChange}/>
                </div>
              </div>
              <div className='row  mb-5'>
                <div className="form-group col-md-6">
                  <h3>Numer telefonu:</h3>
                  <p>{informations.phone_number}000000000</p>
                  <input className="btn bg-primary text-white" value="Zmień Numer Telefonu" onClick={openModalPhoneNumberChange}/>
                </div>
                <div className="form-group col-md-6">
                  <h3>Adres:</h3>
                  <p>{informations.address}Nazwa</p>
                  <input className="btn bg-primary text-white" value="Zmień Numer Telefonu" onClick={openModalPhoneNumberChange}/>
                </div>
              </div>
      </div>
    </div>
  );
};

export default ProfilePage;