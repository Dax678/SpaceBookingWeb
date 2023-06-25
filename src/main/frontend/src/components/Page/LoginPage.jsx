import React, { useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import AuthService from "./../../services/auth.service";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const LoginPage = () => {
  let navigate = useNavigate();

  const form = useRef();
  const checkBtn = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    setMessage("");
    setLoading(true);

    if (checkBtn.current.context._errors.length === 0) {
      AuthService.login(username, password).then(
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
    } else {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-9 col-lg-12 col-xl-10">
          <div className="card shadow-lg o-hidden border-0 my-5 rounded-5">
            <div className="card-body p-0 rounded-5">
              <div className="row">
                <div className="col">
                  <div className="p-5">
                    <div className="text-center">
                      <h1 className="text-dark mb-4">Logowanie</h1>
                    </div>
                    <Form className="user" onSubmit={handleLogin}>
                      <div className="mb-3 text-center">
                        <label htmlFor="username" style={{ fontSize: '1.375rem' }}>Login</label>
                        <input 
                          type="text" 
                          className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3" 
                          name="username" 
                          value={username} 
                          onChange={onChangeUsername} 
                          placeholder="Wprowadź Login" 
                          validations={[required]}
                        />
                      </div>
                      <div className="mb-3 text-center">
                        <label htmlFor="password" style={{ fontSize: '1.375rem' }}>Hasło</label>
                        <input 
                          type="password" 
                          className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3" 
                          name="password" 
                          value={password} 
                          onChange={onChangePassword} 
                          placeholder="Wprowadź Hasło"
                        />
                      </div>
                      <div className="mb-3 text-center">
                        <button className="btn btn-primary w-50 rounded-5 pt-2 pb-2 mt-3" disabled={loading} style={{ fontSize: '1.375rem' }}>
                          {loading && (
                            <span className="spinner-border spinner-border-sm"></span>
                          )}
                          <span>Zaloguj</span>
                        </button>
                        <hr></hr>
                      </div>
                      {message && (
                        <div className="form-group">
                          <div className="alert alert-danger" role="alert">
                            {message}
                          </div>
                        </div>
                      )}
                      <CheckButton style={{ display: "none" }} ref={checkBtn} />
                    </Form>
                    <div className="text-center">
                      <h5><a className="small" href="forgot-password">Nie pamiętam hasła</a></h5>
                    </div>
                    <div className="text-center">
                    <h5><a className="small" href="/register">Stwórz nowe konto</a></h5>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;