import React from 'react';

const LandingPage = () => {
  return (
    <h3 className="text-dark mb-4 text-center"><br/>
        Aby dokonać rezerwacji musisz posiadać konto,<br />
        <a className="text-decoration-none" href="/login">zaloguj się</a> lub 
        <a className="text-decoration-none" href="/register"> stwórz nowe konto</a>
    </h3>
  );
};

export default LandingPage;