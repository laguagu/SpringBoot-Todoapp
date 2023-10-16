import axios from "axios";

const BASE_URL = "/api/user/me";
const LOGOUT_URL = "/logoutUser";

export const fetchUsers = async () => {
  try {
    const response = await axios.get(BASE_URL, { withCredentials: true});
    return response.data;
    
  } catch (error) {
    console.error("Error fetching user:", error.message);
  }
};

export const userlogOut = async () => {
  try {
    const logOut = await axios.post(LOGOUT_URL, { withCredentials: true });
    return logOut.data;
  } catch (error) {
    console.error("LogOut error: ", error);
  }
};
