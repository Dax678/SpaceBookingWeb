import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/";

const getSpaceList = async (floorId, floorType, date) => {
    const response = await axios
        .get(API_URL + 'space/getByFloorIdAndType/' + floorId + "/" + floorType + "/" + date, { headers: authHeader() });
    return response;
};

const bookSpace = (user_id, space_id, date, reservation_status) => {
    return axios.post(API_URL + "reservation/add", {
      user_id,
      space_id,
      date,
      reservation_status
    }, { headers: authHeader() });
  };

const deleteBooking = (user_id, reservation_id) => {
  return axios.put(API_URL + "reservation/delete", {
    user_id,
    reservation_id
  }, { headers: authHeader() });
};


const bookingService = {
    getSpaceList,
    bookSpace,
    deleteBooking
};

export default bookingService;