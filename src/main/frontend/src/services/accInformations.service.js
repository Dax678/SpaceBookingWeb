import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/user/";

const getBookingList = async (id) => {
    const response = await axios
        .get(API_URL + 'getActiveReservation/' + id, { headers: authHeader() });
    return response;
};

const getInformation = async (id) => {
    const response = await axios
        .get(API_URL + 'getInformation/' + id, { headers: authHeader() });
    return response;
};

const accInformationService = {
    getBookingList,
    getInformation
};

export default accInformationService;