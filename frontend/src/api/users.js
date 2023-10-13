import axios from "axios";

const BASE_URL = "http://localhost:8080/api/user/"

export const fetchUsers = async () => {
    const response = await axios.get(BASE_URL+"me")
    return response.data
}