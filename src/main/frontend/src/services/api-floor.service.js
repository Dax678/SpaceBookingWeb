import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/floor/";

const getFloorReservations = async (floorId, date) => {
    const response = await axios
        .get(API_URL + floorId + "/reservations/" + date, { headers: authHeader() });
    return response;
};

const apiFloorService = {
    getFloorReservations,
};

export default apiFloorService;