import React from 'react';

const DashboardHomePage = () => {
  return (
    <h3 className="text-dark mb-4 text-center">
      Witam w konsoli dla administratorów,<br /> 
      Aby sprawdzić listę aktualnych rezerwacji <a className="text-decoration-none" href="/dashboard/bookingList">kliknij tutaj</a><br />
      Natomiast, aby pobrać raport zajętych miejsc <a className="text-decoration-none" href="/dashboard/reports">kliknij tutaj</a>.
      
    </h3>
  );
};

export default DashboardHomePage;