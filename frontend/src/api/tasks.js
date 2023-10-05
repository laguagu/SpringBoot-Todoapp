import axios from "axios";

const BASE_URL = "http://localhost:8080/api/todos/"

export const fetchTasks = async () => {
    const response = await axios.get(BASE_URL);
    return response.data
}
