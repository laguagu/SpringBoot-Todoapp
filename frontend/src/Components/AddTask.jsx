import { useState } from "react";

export default function AddTask({ dispatch }) {
  const [text, setText] = useState("");
  let nextId = 3;

  const handleAddTask = (text) => {
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
      <button
        onClick={() => {
          setText("");
          handleAddTask(text);
        }}
      >
        Add
      </button>
    </>
  );
}
