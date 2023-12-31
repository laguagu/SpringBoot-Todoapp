import axios from "axios";

const BASE_URL = "/api/todos/";

export const fetchTasks = async () => {
  const response = await axios.get(BASE_URL, { withCredentials: true });
  return response.data;
};

export const sendTask = async (taskText) => {
  try {
    const response = await axios.post(
      BASE_URL,
      {
        description: taskText,
        completed: false,
      },
      {
        withCredentials: true,
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error sending message:", error);
  }
};

export const removeTask = async (taksId) => {
  try {
    const response = await axios.delete(BASE_URL + taksId, {
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    console.error("Error deleting message", error);
  }
};

export const updateTask = async (taskID, newDescription) => {
  try {
    const response = await axios.put(
      BASE_URL + taskID,
      {
        description: newDescription,
        completed: false,
      },
      { withCredentials: true }
    );
    return response.data;
  } catch (error) {
    console.error("Error deleting message", error);
  }
};

export const togleCompleted = async (task, checkbox) => {
  try {
    const response = await axios.put(
      BASE_URL + task.id,
      {
        ...task,
        completed: checkbox,
      },
      {
        withCredentials: true,
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error: ", error);
  }
};
