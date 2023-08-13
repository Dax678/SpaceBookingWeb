import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/space/";

const getSpaceList = async (floorId, floorType, date, status) => {
    const response = await axios
        .get(API_URL + floorId + "/" + floorType + "/" + date + "/" + status, { headers: authHeader() });
    return response;
};

const apiSpaceService = {
    getSpaceList,
};

export default apiSpaceService;