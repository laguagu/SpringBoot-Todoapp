import { useContext, useState } from "react";
import Button from 'react-bootstrap/Button';
import { TaskContext } from "../contexts/TaskContext";

export default function AddTask() {
  const {tasks,dispatch} = useContext(TaskContext)
  const [text, setText] = useState("");

  const handleAddTask = (text) => {
    let nextId = tasks.length > 0 ? Math.max(...tasks.map(t => t.id)) + 1 : 0;
    dispatch({
      type: "added",
      id: nextId++,
      text: text,
    });
  };

  return (
    <>
      <input
        placeholder="Add task"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <Button
        onClick={() => {
          setText("");
          handleAddTask(text);
        }}
      >
        Add
      </Button>

    </>
  );
}
