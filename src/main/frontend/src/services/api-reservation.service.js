import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/reservation";

const bookSpace = (user_id, space_id, date, reservation_status) => {
    return axios.post(API_URL, {
      user_id,
      space_id,
      date,
      reservation_status
    }, { headers: authHeader() });
  };

const changeBookingStatus = (bookingStatus, reservation_id) => {
  return axios.put(API_URL + "/" + reservation_id, {
    bookingStatus
  }, { headers: authHeader() });
};


const apiReservationService = {
    bookSpace,
    changeBookingStatus
};

export default apiReservationService;