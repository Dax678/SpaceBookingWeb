import React from 'react';
import AuthService from "./../../services/auth.service";

const HomePage = () => {
  const user = AuthService.getCurrentUser();

  return (
    <h3 className="text-dark mb-4 text-center">
      Witaj, {(user.username)}<br /> Aby dokonać rezerwacji 
      <a className="text-decoration-none" href="/booking"> kliknij tutaj</a>
    </h3>
  );
};

export default HomePage;