import React from "react";
import {Outlet} from "react-router-dom";
import Navbar from "../Navbar";

const Layout = () => {
  return (
    <>
    <div className="row">
      <div className="col-md-2">
        <Navbar />
      </div>
      <div className="col-md-8">
        <Outlet />
      </div>
    </div>
    </>
  );
};

export default Layout;