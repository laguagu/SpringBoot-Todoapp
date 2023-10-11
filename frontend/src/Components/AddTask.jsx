import { useContext, useState } from "react";
import Button from "react-bootstrap/Button";
import { TaskContext } from "../contexts/TaskContext";
import { sendTask } from "../api/tasks";
import { fetchUsers } from "../api/users";

export default function AddTask() {
  const { tasks, dispatch } = useContext(TaskContext);
  const [text, setText] = useState("");

  const handleAddTask = async (text) => {
    const newTask = await sendTask(text);
    dispatch({
      type: "added",
      id: newTask.id,
      description: newTask.description,
      completed: newTask.completed,
    });
  };

  return (
    <div>
      <h1>Add tasks</h1>
      <input
        placeholder="Add task"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <Button
        onClick={async () => {
          setText("");
          handleAddTask(text);
        }}
      >
        Add
      </Button>
    </div>
  );
}
