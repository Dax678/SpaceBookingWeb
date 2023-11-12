import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/reservation";

const bookSpace = (userId, spaceId, reservationDate, reservationStatus) => {
    return axios.post(API_URL, {
      userId,
      spaceId,
      reservationDate,
      reservationStatus
    }, { headers: authHeader() });
  };

const changeBookingStatus = (bookingStatus, reservationId) => {
  return axios.put(API_URL + "/" + reservationId + "/status/" + bookingStatus, null, { headers: authHeader() });
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