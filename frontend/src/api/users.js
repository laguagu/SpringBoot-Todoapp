import axios from "axios";

const BASE_URL = "/api/user/";
const LOGOUT_URL = "/logoutUser";

export const fetchUsers = async () => {
  try {
    const response = await axios.get(BASE_URL + "me");
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 401) {
      // Käyttäjä ei ole kirjautunut, ohjaa hänet kirjautumissivulle
      window.location.href = "http://localhost:5173/login";
    } else {
      console.error("Käyttäjä ei ole kirjautunut");
      throw error;
    }
  }
};

export const userlogOut = async () => {
    try {
        const logOut = await axios.post(LOGOUT_URL, { withCredentials: true });
        return logOut.data;
    } catch (error) {
        console.error("LogOut error: ", error)
    }
};
