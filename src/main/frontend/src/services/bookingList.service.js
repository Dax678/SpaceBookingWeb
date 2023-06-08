import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/";

const getBookingList = async (id) => {
    const response = await axios
        .get(API_URL + 'getActiveReservationByUserId/' + id, { headers: authHeader() });
    return response;
};

const BookingListService = {
    getBookingList
};

export default BookingListService;