import React, { useState } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import Form from "react-validation/build/form";
import AuthService from "../../../services/auth.service";
import {Button, Container, Modal} from "react-bootstrap";
import {useNavigate} from "react-router-dom";


// Validation schema
const validationSchema = Yup.object({
    username: Yup.string()
        .min(3, 'Minimum 3 znaki')
        .required('To pole jest wymagane'),
    email: Yup.string()
        .email('Nieprawidłowy format adresu email')
        .required('To pole jest wymagane'),
    password: Yup.string()
        .min(6, 'Minimum 6 znaków')
        .required('To pole jest wymagane'),
    name: Yup.string()
        .min(3, 'Minimum 6 znaków')
        .required('To pole jest wymagane'),
    surname: Yup.string()
        .min(3, 'Minimum 6 znaków')
        .required('To pole jest wymagane'),
    phoneNumber: Yup.string()
        .min(9, 'Minimum 9 znaków')
        .required('To pole jest wymagane'),
    address: Yup.string()
        .min(6, 'Minimum 6 znaków')
        .required('To pole jest wymagane'),
});

const RegisterPage = () => {
    // Messages from the server
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    // Modals
    const [showSuccessModal, setSuccessShowModal] = useState(false);
    const [showErrorModal, setErrorShowModal] = useState(false);
    const [showInformationModal, setInformationModal] = useState(false);

    // Navigation
    let navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            username: "",
            email: "",
            password: "",
            name: "",
            surname: "",
            phoneNumber: "",
            address: "",
        },
        validationSchema,
        onSubmit: (values) => {
            AuthService.register(
                values.username,
                values.email,
                values.password,
                values.name,
                values.surname,
                values.phoneNumber,
                values.address
            ).then(
                (response) => {
                    setSuccessMessage(response.data.message);
                    setSuccessShowModal(true);
                },
                (error) => {
                    if(error.response && error.response.status === 400) {
                        setErrorMessage(error.response.data.message);
                        setInformationModal(true);
                    } else {
                        setErrorMessage(error.response.data.message);
                        setErrorShowModal(true);
                    }
                }
            );
        },
    });

    //Modal
    const handleNavigationHomePage = () => {
        navigate("/landing-page");
        window.location.reload();
    }

    // Navigation: Reload the page
    const handleNavigationReloadPage = () => {
        window.location.reload();
    }

    // Modal settings
    return (
        <Container>
        <div className="row justify-content-center">
            <div className="col-md-9 col-lg-12 col-xl-10">
                <div className="card shadow-lg o-hidden border-0 my-5">
                    <div className="card-body p-0">
                        <div className="row">
                            <div className="col">
                                <div className="row p-5">
                                    <div className="text-center">
                                        <h1 className="text-dark mb-4">Rejestracja</h1>
                                    </div>
                                    <Form
                                        className="userRegisterForm"
                                        onSubmit={formik.handleSubmit}>
                                        <div>
                                            <div className="row">
                                                <div className="col-md-6 mb-3 text-center">
                                                    <label
                                                        htmlFor="username"
                                                        style={{fontSize: "1.375rem"}}
                                                    >
                                                        Login
                                                    </label>
                                                    <input
                                                        type="text"
                                                        name="username"
                                                        className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3"
                                                        placeholder="Wprowadź Login"
                                                        value={formik.values.username}
                                                        onChange={formik.handleChange}
                                                        onBlur={formik.handleBlur}
                                                    />
                                                    {formik.touched.username && formik.errors.username ? (
                                                        <div>{formik.errors.username}</div>
                                                    ) : null}
                                                </div>
                                                <div className="col-md-6 mb-3 text-center">
                                                    <label htmlFor="email"
                                                           style={{fontSize: '1.375rem'}}>Email</label>
                                                    <input
                                                        type="email"
                                                        name="email"
                                                        className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3"
                                                        placeholder="Wprowadź Address Email"
                                                        value={formik.values.email}
                                                        onChange={formik.handleChange}
                                                        onBlur={formik.handleBlur}
                                                    />
                                                    {formik.touched.email && formik.errors.email ? (
                                                        <div>{formik.errors.email}</div>
                                                    ) : null}
                                                </div>
                                                <div className="col-md-6 mb-3 text-center">
                                                    <label htmlFor="password"
                                                           style={{fontSize: '1.375rem'}}>Hasło</label>
                                                    <input
                                                        type="password"
                                                        name="password"
                                                        className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3"
                                                        placeholder="Wprowadź Hasło"
                                                        value={formik.values.password}
                                                        onChange={formik.handleChange}
                                                        onBlur={formik.handleBlur}
                                                    />
                                                    {formik.touched.password && formik.errors.password ? (
                                                        <div>{formik.errors.password}</div>
                                                    ) : null}
                                                </div>
                                                <div className="col-md-6 mb-3 text-center">
                                                    <label htmlFor="name"
                                                           style={{fontSize: '1.375rem'}}>Imię</label>
                                                    <input
                                                        type="text"
                                                        name="name"
                                                        className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3"
                                                        placeholder="Wprowadź Imię"
                                                        value={formik.values.name}
                                                        onChange={formik.handleChange}
                                                        onBlur={formik.handleBlur}
                                                    />
                                                    {formik.touched.name && formik.errors.name ? (
                                                        <div>{formik.errors.name}</div>
                                                    ) : null}
                                                </div>
                                                <div className="col-md-6 mb-3 text-center">
                                                    <label htmlFor="surname"
                                                           style={{fontSize: '1.375rem'}}>Nazwisko</label>
                                                    <input
                                                        type="text"
                                                        name="surname"
                                                        className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3"
                                                        placeholder="Wprowadź Nazwisko"
                                                        value={formik.values.surname}
                                                        onChange={formik.handleChange}
                                                        onBlur={formik.handleBlur}
                                                    />
                                                    {formik.touched.surname && formik.errors.surname ? (
                                                        <div>{formik.errors.surname}</div>
                                                    ) : null}
                                                </div>
                                                <div className="col-md-6 mb-3 text-center">
                                                    <label htmlFor="phoneNumber"
                                                           style={{fontSize: '1.375rem'}}>Numer Telefonu</label>
                                                    <input
                                                        type="text"
                                                        name="phoneNumber"
                                                        className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3"
                                                        placeholder="Wprowadź Numer Telefonu"
                                                        value={formik.values.phoneNumber}
                                                        onChange={formik.handleChange}
                                                        onBlur={formik.handleBlur}
                                                    />
                                                    {formik.touched.phoneNumber && formik.errors.phoneNumber ? (
                                                        <div>{formik.errors.phoneNumber}</div>
                                                    ) : null}
                                                </div>
                                                <div className="col mb-3 text-center">
                                                    <label htmlFor="Address"
                                                           style={{fontSize: '1.375rem'}}>Adres</label>
                                                    <input
                                                        type="text"
                                                        name="address"
                                                        className="form-control form-control-user w-50 mx-auto rounded-5 pt-3 pb-3 mt-3"
                                                        placeholder="Wprowadź Adres"
                                                        value={formik.values.address}
                                                        onChange={formik.handleChange}
                                                        onBlur={formik.handleBlur}
                                                    />
                                                    {formik.touched.address && formik.errors.address ? (
                                                        <div>{formik.errors.address}</div>
                                                    ) : null}
                                                </div>
                                            </div>
                                            <div className="text-center">
                                                <button
                                                    className="btn btn-primary w-50 rounded-5 pt-2 pb-2 mt-3"
                                                    style={{fontSize: '1.375rem'}}>Stwórz konto
                                                </button>
                                            </div>
                                            <hr/>
                                        </div>
                                        {showInformationModal && (
                                            <div className="form-group">
                                                <div
                                                    className="alert alert-danger"
                                                    role="alert">
                                                    {errorMessage}
                                                </div>
                                            </div>
                                        )}
                                    </Form>
                                    <div className="text-center">
                                        <h5>
                                            <a className="small" href="/login">Posiadam już konto</a>
                                        </h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <Modal show={showSuccessModal} onHide={handleNavigationReloadPage}>
                <Modal.Header closeButton>
                    <Modal.Title>Space has been reserved</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {successMessage}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleNavigationHomePage}>
                        Go to Home Page
                    </Button>
                    <Button variant="primary" onClick={handleNavigationReloadPage}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showErrorModal} onHide={handleNavigationReloadPage}>
                <Modal.Header closeButton>
                    <Modal.Title>Error</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <b>Error message:</b><br/> {errorMessage}<br/>
                    <b>More informations:</b><br/>
                    Please reload the page. If the problem persists, please contact the administrator.
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleNavigationHomePage}>
                        Go to Home Page
                    </Button>
                    <Button variant="danger" onClick={handleNavigationReloadPage}>
                        Reload Page
                    </Button>
                </Modal.Footer>
            </Modal>
    </Container>
    );
};

export default RegisterPage;