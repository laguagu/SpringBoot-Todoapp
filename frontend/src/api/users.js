import axios from "axios";

const BASE_URL = "http://localhost:8080/api/user/"

export const fetchUsers = async () => {
    try {
        const response = await axios.get(BASE_URL+"me");
        return response.data;
    } catch (error) {
        if (error.response && error.response.status === 401) {
            // Käyttäjä ei ole kirjautunut, ohjaa hänet kirjautumissivulle
            window.location.href = "http://localhost:5173/login";
        } else {
            console.error("Käyttäjä ei ole kirjautunut")
            throw error;
        }
    }
}