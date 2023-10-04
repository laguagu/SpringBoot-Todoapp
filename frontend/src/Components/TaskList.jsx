import { useContext, useState } from "react";
import { TaskContext } from "../contexts/TaskContext";

export default function TaskList() {
  const {tasks, dispatch} = useContext(TaskContext)
  console.log(tasks)
  return (
    <>
      <h1>Tasks</h1>
      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            <Task task={task} dispatch={dispatch}/>
          </li>
        ))}
      </ul>
    </>
  );
}

function Task({ task, dispatch }) {
  const [editing, setEditing] = useState(false);
  const [editText, setEditText] = useState(task.text)
  let taskContent;

  const deleteTask = (taskId) => {
    dispatch({
        type:"delete",
        id:taskId
    })
  }

  const updateTask = (taskId, newText) => {
    dispatch({
      type:"update",
      id: taskId,
      text: newText
    })
  }

  const saveChanges = () => {
    updateTask(task.id,editText)
    setEditing(false)
  }


  if (editing) {
    taskContent = (
      <>
        <input value={editText} onChange={e => setEditText(e.target.value)} />
        <button onClick={() => saveChanges()}>Save</button>
      </>
    );
  } else {
    taskContent = (
      <>
        {task.text}
        <button onClick={() => setEditing(true)}>Edit</button>
      </>
    );
  }
  return (
    <label>
      <input type="checkbox" checked={task.done} onChange={(e) => {}} />
      {taskContent}
      <button onClick={() => deleteTask(task.id)}>Delete</button>
    </label>
  );
}
