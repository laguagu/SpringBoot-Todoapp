import { useReducer, useState } from "react";
import taskReducer from "../redurecs/tasksReducer";


export default function AddTask() {
  const [text, setText] = useState("");
  const [tasks, dispatch] = useReducer(taskReducer);
  let nextId = 3;

  const handleAddTask = (task) => {
    dispatch({
      type: "added",
      text: task.text,
      id: nextId++
    })
  }
  
  return (
    <>
      <input
        placeholder="Add task"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <button
        onClick={() => {
          setText("");
        }}
      >
        Add
      </button>
    </>
  );
}
