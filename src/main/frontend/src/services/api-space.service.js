import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/space/";

const getSpaceList = async (floorId, floorType, date, status) => {
    return await axios
        .get(API_URL + floorId + "/" + floorType + "/" + date + "/" + status, { headers: authHeader() });
};

const getSpaceListById = async (floorId) => {
    return await axios
        .get(API_URL + "floor/" + floorId, { headers: authHeader() });
}

const changeSpaceStatus = async (spaceId, newStatus) => {
    return axios.put(API_URL + spaceId, {
        newStatus
      }, { headers: authHeader() });
}

const generateSpaceReport = async (floorId) => {
    return await axios.get(API_URL + 'floor/' + floorId + '/filePDF', {
      responseType: 'arraybuffer',
      headers: authHeader()
    });
  }

const apiSpaceService = {
    getSpaceList,
    getSpaceListById,
    changeSpaceStatus,
    generateSpaceReport
};

export default apiSpaceService;