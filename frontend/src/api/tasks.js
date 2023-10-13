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
    },{
      withCredentials: true
    });
    return response.data;
  } catch (error) {
    console.error("Error sending message:", error);
  }
};

export const removeTask = async (taksId) => {
  try {
    const response = await axios.delete(BASE_URL + taksId);
    return response.data
  } catch (error) {
    console.error("Error deleting message", error);
  }
};

export const updateTask = async (taskID, newDescription) => {
  try{
    const response = await axios.put(BASE_URL + taskID, {
      description: newDescription,
      completed: false
    });
    return response.data
  } catch (error) {
    console.error("Error deleting message", error)
  }
}
