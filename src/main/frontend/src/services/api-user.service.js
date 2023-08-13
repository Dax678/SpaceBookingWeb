import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/user/";

const getUserReservations = async (id, status) => {
    const response = await axios
        .get(API_URL + id + '/reservations/' + status, { headers: authHeader() });
    return response;
};

const getUserInformations = async (id) => {
    const response = await axios
        .get(API_URL + id + '/details', { headers: authHeader() });
    return response;
};

const apiUserService = {
    getUserReservations,
    getUserInformations
};

export default apiUserService;