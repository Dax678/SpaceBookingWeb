import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/reservation";

const bookSpace = (userId, spaceId, date, reservationStatus) => {
    return axios.post(API_URL, {
      userId,
      spaceId,
      date,
      reservationStatus
    }, { headers: authHeader() });
  };

const changeBookingStatus = (bookingStatus, reservationId) => {
  return axios.put(API_URL + "/" + reservationId, {
    bookingStatus
  }, { headers: authHeader() });
};

const getReservationDetails = async (startDate, endDate) => {
  return await axios.get(API_URL + '/details', {
    params: {
      reservationStartDate: startDate,
      reservationEndDate: endDate
    },
    headers: authHeader()
  });
}

const generateReservationReport = async (startDate, endDate) => {
  return await axios.get(API_URL + '/details/filePDF', {
    params: {
      reservationStartDate: startDate,
      reservationEndDate: endDate
    },
    responseType: 'arraybuffer',
    headers: authHeader()
  });
}


const apiReservationService = {
    bookSpace,
    changeBookingStatus,
    getReservationDetails,
    generateReservationReport
};

export default apiReservationService;