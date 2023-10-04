import { useContext, useState } from "react";
import { TaskContext } from "../contexts/TaskContext";

export default function TaskList() {
  const {tasks, dispatch} = useContext(TaskContext)
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
  let taskContent;

  const deleteTask = (taskId) => {
    dispatch({
        type:"delete",
        id:taskId
    })
  }

  if (editing) {
    taskContent = (
      <>
        <input value={task.text} onChange={(e) => {}} />
        <button onClick={() => setEditing(false)}>Save</button>
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
