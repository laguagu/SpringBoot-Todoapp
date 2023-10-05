import axios from "axios";

const BASE_URL = "http://localhost:8080/api/todos/";

export const fetchTasks = async () => {
  const response = await axios.get(BASE_URL);
  return response.data;
};

export const sendTask = async (taskText) => {
  try {
    const response = await axios.post(BASE_URL, {
      description: taskText,
      completed: false,
    });
    return response.data;
  } catch (error) {
    console.error("Error sendin message:", error);
  }
};
