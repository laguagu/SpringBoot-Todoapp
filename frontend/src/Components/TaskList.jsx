import { useContext, useEffect, useState } from "react";
import { TaskContext } from "../contexts/TaskContext";
import Button from "react-bootstrap/Button";


export default function TaskList() {
  const { tasks, dispatch } = useContext(TaskContext);
  return (
    <div className="d-flex flex-column justify-content-center align-items-center">
      <h1>Tasks</h1>
      <ul className="list-unstyled">
        {tasks.map((task) => (
          <li key={task.id} className="mb-2">
            <Task task={task} dispatch={dispatch} />
          </li>
        ))}
      </ul>
    </div>
  );
}

function Task({ task, dispatch }) {
  const [editing, setEditing] = useState(false);
  const [editText, setEditText] = useState(task.description);

  const deleteTask = (taskId) => {
    dispatch({
      type: "delete",
      id: taskId,
    });
  };

  const updateTask = (taskId, newDescription) => {
    dispatch({
      type: "update",
      id: taskId,
      description: newDescription,
    });
  };

  const saveChanges = () => {
    updateTask(task.id, editText);
    setEditing(false);
  };

  const setTaskDone = (e) => {
    dispatch({
      type: "toggleDone",
      id: task.id,
      completed: e.target.checked,
    });
  };

  return (
    <div className="d-flex justify-content-between">
      <div className="">
        <input
          type="checkbox"
          checked={task.completed}
          onChange={(e) => setTaskDone(e)}
        />
        {editing ? (
          <input
            value={editText}
            onChange={(e) => setEditText(e.target.value)}
          />
        ) : (
          <span>{task.description}</span>
        )}
      </div>
      <div>
        {editing ? (
          <Button onClick={saveChanges}>
            Save
          </Button>
        ) : (
          <Button onClick={() => setEditing(true)}>
            Edit
          </Button>
        )}
        <Button onClick={() => deleteTask(task.id)}>
          Delete
        </Button>
      </div>
    </div>
  );
}
