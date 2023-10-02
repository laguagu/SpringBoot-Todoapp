import { useState } from "react";
import Button from 'react-bootstrap/Button';

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
