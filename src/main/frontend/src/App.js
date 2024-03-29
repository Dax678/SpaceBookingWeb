import './App.css';
import React, { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.css';


//Main site imports
import LoginPage from './components/Page/UserPage/LoginPage';
import RegisterPage from './components/Page/UserPage/RegisterPage';
import HomePage from './components/Page/UserPage/HomePage';
import LandingPage from './components/Page/UserPage/LandingPage';
import BookingPage from './components/Page/UserPage/BookingPage';
import BookingMapPage from './components/Page/UserPage/BookingMapPage';
import BookingListPage from './components/Page/UserPage/BookingListPage';
import NoPage from './components/Page/UserPage/NoPage';

//Dashboards imports
import DashboardHomePage from './components/Page/AdminDashboard/DashboardHomePage';
import DashboardBookingListPage from './components/Page/AdminDashboard/DashboardBookingListPage';
import DashboardSpaceListPage from './components/Page/AdminDashboard/DashboardSpaceListPage';
import DashboardReportsPage from './components/Page/AdminDashboard/DashboardReportsPage';

import AuthService from "./services/auth.service";
import EventBus from "./common/EventBus";

function App() {
  // const [showModeratorBoard, setShowModeratorBoard] = useState(false);
  // const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      // setShowModeratorBoard(user.roles.includes("ROLE_MODERATOR"));
      // setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    // setShowModeratorBoard(false);
    // setShowAdminBoard(false);
    setCurrentUser(undefined);
  };

  return (
    <div className="row">
      <div className="col-md-2">
        <nav className="nav__cont">
        {currentUser ? (
          <ul className="nav">
            {currentUser.roles.includes("ROLE_ADMIN") && (
            <div>
              <li className="nav__items">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                  <path d="M981.4 502.3c-9.1 0-18.3-2.9-26-8.9L539 171.7c-15.3-11.8-36.7-11.8-52 0L70.7 493.4c-18.6 14.4-45.4 10.9-59.7-7.7-14.4-18.6-11-45.4 7.7-59.7L435 104.3c46-35.5 110.2-35.5 156.1 0L1007.5 426c18.6 14.4 22 41.1 7.7 59.7-8.5 10.9-21.1 16.6-33.8 16.6z" fill="#5F6379"/>
                  <path d="M810.4 981.3H215.7c-70.8 0-128.4-57.6-128.4-128.4V534.2c0-23.5 19.1-42.6 42.6-42.6s42.6 19.1 42.6 42.6v318.7c0 23.8 19.4 43.2 43.2 43.2h594.8c23.8 0 43.2-19.4 43.2-43.2V534.2c0-23.5 19.1-42.6 42.6-42.6s42.6 19.1 42.6 42.6v318.7c-0.1 70.8-57.7 128.4-128.5 128.4z" fill="#3688FF"/>
                </svg>
              <Link to={"/dashboard/home"}>Strona<br/>Główna</Link>
              </li>
              <li className="nav__items">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                  <path d="M981.3 170.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7zM981.3 938.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7zM981.3 554.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7z" fill="#3688FF"/>
                  <path d="M106.7 128m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                  <path d="M106.7 512m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                  <path d="M106.7 896m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                </svg>
              <Link to={"/dashboard/bookingList"}>Lista<br/>Rezerwacji</Link>
              </li>
              <li className="nav__items">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                  <path d="M981.3 170.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7zM981.3 938.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7zM981.3 554.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7z" fill="#3688FF"/>
                  <path d="M106.7 128m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                  <path d="M106.7 512m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                  <path d="M106.7 896m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                </svg>
              <Link to={"/dashboard/spaceList"}>Lista<br/>Miejsc</Link>
              </li>
              <li className="nav__items">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                  <path d="M981.3 170.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7zM981.3 938.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7zM981.3 554.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7z" fill="#3688FF"/>
                  <path d="M106.7 128m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                  <path d="M106.7 512m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                  <path d="M106.7 896m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                </svg>
              <Link to={"/dashboard/reports"}>Raporty</Link>
              </li>
            </div>
            )}
            {currentUser.roles.includes("ROLE_USER") && (
              <div>
                <li className="nav__items">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                        <path d="M981.4 502.3c-9.1 0-18.3-2.9-26-8.9L539 171.7c-15.3-11.8-36.7-11.8-52 0L70.7 493.4c-18.6 14.4-45.4 10.9-59.7-7.7-14.4-18.6-11-45.4 7.7-59.7L435 104.3c46-35.5 110.2-35.5 156.1 0L1007.5 426c18.6 14.4 22 41.1 7.7 59.7-8.5 10.9-21.1 16.6-33.8 16.6z" fill="#5F6379"/>
                        <path d="M810.4 981.3H215.7c-70.8 0-128.4-57.6-128.4-128.4V534.2c0-23.5 19.1-42.6 42.6-42.6s42.6 19.1 42.6 42.6v318.7c0 23.8 19.4 43.2 43.2 43.2h594.8c23.8 0 43.2-19.4 43.2-43.2V534.2c0-23.5 19.1-42.6 42.6-42.6s42.6 19.1 42.6 42.6v318.7c-0.1 70.8-57.7 128.4-128.5 128.4z" fill="#3688FF"/>
                    </svg>
                    <Link to={"/home"}>Strona<br/>Główna</Link>
                </li>
                <li className="nav__items">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                        <path d="M981.3 170.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7zM981.3 938.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7zM981.3 554.7H320c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h661.3c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7z" fill="#3688FF"/>
                        <path d="M106.7 128m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                        <path d="M106.7 512m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                        <path d="M106.7 896m-64 0a64 64 0 1 0 128 0 64 64 0 1 0-128 0Z" fill="#5F6379"/>
                    </svg>
                    <Link to={"/bookingList"}>Rezerwacje</Link>
                </li>
                  <li className="nav__items">
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                          <path d="M823.3 938.8H229.4c-71.6 0-129.8-58.2-129.8-129.8V215.1c0-71.6 58.2-129.8 129.8-129.8h297c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7h-297c-24.5 0-44.4 19.9-44.4 44.4V809c0 24.5 19.9 44.4 44.4 44.4h593.9c24.5 0 44.4-19.9 44.4-44.4V512c0-23.6 19.1-42.7 42.7-42.7s42.7 19.1 42.7 42.7v297c0 71.6-58.2 129.8-129.8 129.8z" fill="#3688FF"/>
                          <path d="M483 756.5c-1.8 0-3.5-0.1-5.3-0.3l-134.5-16.8c-19.4-2.4-34.6-17.7-37-37l-16.8-134.5c-1.6-13.1 2.9-26.2 12.2-35.5l374.6-374.6c51.1-51.1 134.2-51.1 185.3 0l26.3 26.3c24.8 24.7 38.4 57.6 38.4 92.7 0 35-13.6 67.9-38.4 92.7L513.2 744c-8.1 8.1-19 12.5-30.2 12.5z m-96.3-97.7l80.8 10.1 359.8-359.8c8.6-8.6 13.4-20.1 13.4-32.3 0-12.2-4.8-23.7-13.4-32.3L801 218.2c-17.9-17.8-46.8-17.8-64.6 0L376.6 578l10.1 80.8z" fill="#5F6379"/>
                      </svg>
                      <Link to={"/booking"}>Rezerwuj</Link>
                </li>
              </div>
              )}
                <li className="nav__items">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                      <path d="M810.7 938.7H213.3c-17.3 0-34-3.4-49.9-10.1-15.2-6.4-29-15.7-40.7-27.5-11.7-11.7-21-25.4-27.4-40.7-6.7-15.8-10.1-32.6-10.1-49.8V213.3c0-17.2 3.4-34 10.1-49.8 6.4-15.2 15.7-29 27.5-40.7 11.7-11.7 25.4-20.9 40.7-27.4 15.8-6.7 32.5-10.1 49.8-10.1h597.3c17.3 0 34 3.4 49.9 10.1 15.2 6.4 29 15.7 40.7 27.5 11.7 11.7 21 25.4 27.4 40.7 6.7 15.8 10.1 32.6 10.1 49.8V320c0 23.6-19.1 42.7-42.7 42.7s-42.7-19.1-42.7-42.7V213.3c0-5.8-1.1-11.4-3.4-16.6-2.1-5.1-5.2-9.6-9.1-13.5-4-4-8.5-7-13.6-9.2-5.2-2.2-10.8-3.3-16.6-3.3H213.3c-5.8 0-11.4 1.1-16.6 3.3-5.1 2.2-9.7 5.2-13.6 9.1-4 4-7.1 8.5-9.2 13.6-2.2 5.3-3.4 10.9-3.4 16.6v597.3c0 5.8 1.1 11.4 3.4 16.6 2.1 5.1 5.2 9.6 9.1 13.5 4 4 8.5 7 13.6 9.2 5.2 2.2 10.8 3.3 16.6 3.3h597.3c5.8 0 11.4-1.1 16.6-3.3 5.1-2.2 9.7-5.2 13.6-9.1 4-4 7.1-8.5 9.2-13.6 2.2-5.3 3.4-10.9 3.4-16.6V704c0-23.6 19.1-42.7 42.7-42.7s42.7 19.1 42.7 42.7v106.7c0 17.2-3.4 34-10.1 49.8-6.4 15.2-15.7 29-27.5 40.7-11.7 11.7-25.4 20.9-40.7 27.4-15.7 6.7-32.5 10.1-49.7 10.1z" fill="#3688FF"/>
                      <path d="M768 682.7c-10.9 0-21.8-4.2-30.2-12.5-16.7-16.7-16.7-43.7 0-60.3l97.8-97.8-97.8-97.8c-16.7-16.7-16.7-43.7 0-60.3 16.7-16.7 43.7-16.7 60.3 0l128 128c16.7 16.7 16.7 43.7 0 60.3l-128 128c-8.3 8.2-19.2 12.4-30.1 12.4z" fill="#5F6379"/>
                      <path d="M896 554.7H512c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h384c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7z" fill="#5F6379"/>
                    </svg>
                  <a href="/login" onClick={logOut}>Wyloguj</a>
                </li>
              </ul>
        ) : (
          <ul className="nav">
            <li className="nav__items">
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                      <path d="M981.4 502.3c-9.1 0-18.3-2.9-26-8.9L539 171.7c-15.3-11.8-36.7-11.8-52 0L70.7 493.4c-18.6 14.4-45.4 10.9-59.7-7.7-14.4-18.6-11-45.4 7.7-59.7L435 104.3c46-35.5 110.2-35.5 156.1 0L1007.5 426c18.6 14.4 22 41.1 7.7 59.7-8.5 10.9-21.1 16.6-33.8 16.6z" fill="#5F6379"/>
                      <path d="M810.4 981.3H215.7c-70.8 0-128.4-57.6-128.4-128.4V534.2c0-23.5 19.1-42.6 42.6-42.6s42.6 19.1 42.6 42.6v318.7c0 23.8 19.4 43.2 43.2 43.2h594.8c23.8 0 43.2-19.4 43.2-43.2V534.2c0-23.5 19.1-42.6 42.6-42.6s42.6 19.1 42.6 42.6v318.7c-0.1 70.8-57.7 128.4-128.5 128.4z" fill="#3688FF"/>
                  </svg>
                    <Link to={"/landing-page"}>Strona<br/>Główna</Link>
                </li>
                <li className="nav__items">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                      <path d="M810.7 938.7H213.3c-17.3 0-34-3.4-49.9-10.1-15.2-6.4-29-15.7-40.7-27.5-11.7-11.7-21-25.4-27.4-40.7-6.7-15.8-10.1-32.6-10.1-49.8V213.3c0-17.2 3.4-34 10.1-49.8 6.4-15.2 15.7-29 27.5-40.7 11.7-11.7 25.4-20.9 40.7-27.4 15.8-6.7 32.5-10.1 49.8-10.1h597.3c17.3 0 34 3.4 49.9 10.1 15.2 6.4 29 15.7 40.7 27.5 11.7 11.7 21 25.4 27.4 40.7 6.7 15.8 10.1 32.6 10.1 49.8V320c0 23.6-19.1 42.7-42.7 42.7s-42.7-19.1-42.7-42.7V213.3c0-5.8-1.1-11.4-3.4-16.6-2.1-5.1-5.2-9.6-9.1-13.5-4-4-8.5-7-13.6-9.2-5.2-2.2-10.8-3.3-16.6-3.3H213.3c-5.8 0-11.4 1.1-16.6 3.3-5.1 2.2-9.7 5.2-13.6 9.1-4 4-7.1 8.5-9.2 13.6-2.2 5.3-3.4 10.9-3.4 16.6v597.3c0 5.8 1.1 11.4 3.4 16.6 2.1 5.1 5.2 9.6 9.1 13.5 4 4 8.5 7 13.6 9.2 5.2 2.2 10.8 3.3 16.6 3.3h597.3c5.8 0 11.4-1.1 16.6-3.3 5.1-2.2 9.7-5.2 13.6-9.1 4-4 7.1-8.5 9.2-13.6 2.2-5.3 3.4-10.9 3.4-16.6V704c0-23.6 19.1-42.7 42.7-42.7s42.7 19.1 42.7 42.7v106.7c0 17.2-3.4 34-10.1 49.8-6.4 15.2-15.7 29-27.5 40.7-11.7 11.7-25.4 20.9-40.7 27.4-15.7 6.7-32.5 10.1-49.7 10.1z" fill="#3688FF"/>
                      <path d="M768 682.7c-10.9 0-21.8-4.2-30.2-12.5-16.7-16.7-16.7-43.7 0-60.3l97.8-97.8-97.8-97.8c-16.7-16.7-16.7-43.7 0-60.3 16.7-16.7 43.7-16.7 60.3 0l128 128c16.7 16.7 16.7 43.7 0 60.3l-128 128c-8.3 8.2-19.2 12.4-30.1 12.4z" fill="#5F6379"/>
                      <path d="M896 554.7H512c-23.6 0-42.7-19.1-42.7-42.7s19.1-42.7 42.7-42.7h384c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7z" fill="#5F6379"/>
                    </svg>
                    <Link to={"/login"}>Logowanie</Link>
                </li>
                <li className="nav__items">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" className="icon" version="1.1">
                        <path d="M823.3 938.8H229.4c-71.6 0-129.8-58.2-129.8-129.8V215.1c0-71.6 58.2-129.8 129.8-129.8h297c23.6 0 42.7 19.1 42.7 42.7s-19.1 42.7-42.7 42.7h-297c-24.5 0-44.4 19.9-44.4 44.4V809c0 24.5 19.9 44.4 44.4 44.4h593.9c24.5 0 44.4-19.9 44.4-44.4V512c0-23.6 19.1-42.7 42.7-42.7s42.7 19.1 42.7 42.7v297c0 71.6-58.2 129.8-129.8 129.8z" fill="#3688FF"/>
                        <path d="M483 756.5c-1.8 0-3.5-0.1-5.3-0.3l-134.5-16.8c-19.4-2.4-34.6-17.7-37-37l-16.8-134.5c-1.6-13.1 2.9-26.2 12.2-35.5l374.6-374.6c51.1-51.1 134.2-51.1 185.3 0l26.3 26.3c24.8 24.7 38.4 57.6 38.4 92.7 0 35-13.6 67.9-38.4 92.7L513.2 744c-8.1 8.1-19 12.5-30.2 12.5z m-96.3-97.7l80.8 10.1 359.8-359.8c8.6-8.6 13.4-20.1 13.4-32.3 0-12.2-4.8-23.7-13.4-32.3L801 218.2c-17.9-17.8-46.8-17.8-64.6 0L376.6 578l10.1 80.8z" fill="#5F6379"/>
                    </svg>
                    <Link to={"/register"}>Rejestracja</Link>
                </li>
          </ul>
        )}
        </nav>
      </div>
      <div className="col-md-8">
      {currentUser && currentUser.roles && currentUser.roles.includes("ROLE_USER") ? (
        <Routes>
          <Route path="/home" element={<HomePage />} />
          <Route path="/bookingList" element={<BookingListPage />} />
          <Route path="/booking" element={<BookingPage />} />
          <Route path="/booking/map" element={<BookingMapPage />} />
          <Route path="*" element={<NoPage />} />
        </Routes>
      ) :
      currentUser && currentUser.roles && currentUser.roles.includes("ROLE_ADMIN") ? (
        <Routes>
          <Route path="/dashboard/home" element={<DashboardHomePage />} />
          <Route path="/dashboard/bookingList" element={<DashboardBookingListPage />} />
          <Route path="/dashboard/spaceList" element={<DashboardSpaceListPage />} />
          <Route path="/dashboard/reports" element={<DashboardReportsPage />} />
          <Route path="*" element={<NoPage />} />
        </Routes>
      ) : (
        <Routes>
          <Route path="/landing-page" element={<LandingPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="*" element={<NoPage />} />
        </Routes>
      )}
      </div>
    </div>  
  );
}

export default App;
