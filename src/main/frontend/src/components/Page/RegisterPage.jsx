import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";

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
  
  const validEmail = (value) => {
    if (!isEmail(value)) {
      return (
        <div className="alert alert-danger" role="alert">
          This is not a valid email.
        </div>
      );
    }
  };
  
  const vusername = (value) => {
    if (value.length < 3 || value.length > 20) {
      return (
        <div className="alert alert-danger" role="alert">
          The username must be between 3 and 20 characters.
        </div>
      );
    }
  };
  
  const vpassword = (value) => {
    if (value.length < 6 || value.length > 40) {
      return (
        <div className="alert alert-danger" role="alert">
          The password must be between 6 and 40 characters.
        </div>
      );
    }
  };

const RegisterPage = () => {
    const form = useRef();
    const checkBtn = useRef();
  
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");
  
    const onChangeUsername = (e) => {
      const username = e.target.value;
      setUsername(username);
    };
  
    const onChangeEmail = (e) => {
      const email = e.target.value;
      setEmail(email);
    };
  
    const onChangePassword = (e) => {
      const password = e.target.value;
      setPassword(password);
    };
  
    const handleRegister = (e) => {
      e.preventDefault();
  
      setMessage("");
      setSuccessful(false);
  
      form.current.validateAll();
  
      if (checkBtn.current.context._errors.length === 0) {
        AuthService.register(username, email, password).then(
          (response) => {
            setMessage(response.data.message);
            setSuccessful(true);
          },
          (error) => {
            const resMessage =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();
  
            setMessage(resMessage);
            setSuccessful(false);
          }
        );
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
                                <Form className="userRegisterForm" onSubmit={handleRegister} ref={form}>
                                {!successful && (
                                  <div>
                                    <div className="mb-3">
                                        <label htmlFor="username">Username</label>
                                        <input 
                                          type="text"
                                          name="username" 
                                          className="form-control form-control-user" 
                                          placeholder="Wprowadź Login" 
                                          value={username}
                                          onChange={onChangeUsername}
                                          validations={[required, vusername]}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="email">Email</label>
                                        <input 
                                          type="email"
                                          name="email" 
                                          className="form-control form-control-user" 
                                          placeholder="Wprowadź Address Email" 
                                          value={email}
                                          onChange={onChangeEmail}
                                          validations={[required, validEmail]}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="password">Password</label>
                                        <input 
                                          type="password"
                                          name="password" 
                                          className="form-control form-control-user" 
                                          placeholder="Wprowadź Hasło" 
                                          value={password}
                                          onChange={onChangePassword}
                                          validations={[required, vpassword]}
                                        />

                                        </div>
                                    <button className="btn btn-primary d-block btn-user w-100">Potwierdź</button>
                                  </div>
                                )}
                                {message && (
                                  <div className="form-group">
                                    <div
                                      className={ successful ? "alert alert-success" : "alert alert-danger" }
                                      role="alert"
                                    >
                                      {message}
                                    </div>
                                  </div>
                                )}
                                <CheckButton style={{ display: "none" }} ref={checkBtn} />
                                </Form>
                                <div className="text-center"><a className="small">Nie pamiętam hasła</a></div>
                                <div className="text-center"><a className="small">Posiadam już konto</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
  );
};

export default RegisterPage;