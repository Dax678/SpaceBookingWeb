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

    // form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      AuthService.login(username, password).then(
        () => {
          navigate("/bookinglist");
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
    <div className="row justify-content-center">
      <div className="col-md-9 col-lg-12 col-xl-10">
        <div className="card shadow-lg o-hidden border-0 my-5">
          <div className="card-body p-0">
            <div className="row">
              <div className="col">
                <div className="p-5">
                  <div className="text-center">
                    <h4 className="text-dark mb-4">Logowanie</h4>
                  </div>
                  <Form className="user" onSubmit={handleLogin}>
                    <div className="mb-3">
                      <label htmlFor="username">Username</label>
                      <input 
                        type="text" 
                        className="form-control form-control-user" 
                        name="username" 
                        value={username} 
                        onChange={onChangeUsername} 
                        placeholder="Wprowadź Login" 
                        validations={[required]}
                      />
                    </div>
                    <div className="mb-3">
                      <label htmlFor="password">Password</label>
                      <input 
                        type="password" 
                        className="form-control form-control-user" 
                        name="password" 
                        value={password} 
                        onChange={onChangePassword} 
                        placeholder="Wprowadź Hasło"
                      />
                    </div>
                    <div className="mb-3">
                      <div className="custom-control custom-checkbox small">
                        <div className="form-check">
                          <input className="form-check-input custom-control-input" type="checkbox" id="formCheck-1"></input>
                            <label className="form-check-label custom-control-label">Zapamiętaj dane logowania</label>
                        </div>
                      </div>
                      <button className="btn btn-primary d-block btn-user w-100" disabled={loading}>
                        {loading && (
                          <span className="spinner-border spinner-border-sm"></span>
                        )}
                        <span>Login</span>
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
                  <div className="text-center"><a className="small" href="forgot-password">Nie pamiętam hasła</a></div>
                  <div className="text-center"><a className="small" href="/register">Stwórz nowe konto</a></div>
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