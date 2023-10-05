import { useContext, useEffect, useState } from "react";
import { TaskContext } from "../contexts/TaskContext";
import Button from "react-bootstrap/Button";
import { fetchTasks } from "../api/tasks";

export default function TaskList() {
  const { tasks, dispatch } = useContext(TaskContext);
  
  useEffect(() => {
    console.log(fetchTasks)
  }, [])


  return (
    <div className="d-flex flex-column justify-content-center align-items-center">
    <div className="d-flex flex-column justify-content-center align-items-center">
      <h1>Tasks</h1>
      <ul className="list-unstyled">
      <ul className="list-unstyled">
        {tasks.map((task) => (
          <li key={task.id} className="mb-2">
            <Task task={task} dispatch={dispatch} />
          <li key={task.id} className="mb-2">
            <Task task={task} dispatch={dispatch} />
          </li>
        ))}
      </ul>
    </div>
    </div>
  );
}

function Task({ task, dispatch }) {
  const [editing, setEditing] = useState(false);
  const [editText, setEditText] = useState(task.text);
  const [editText, setEditText] = useState(task.text);

  const deleteTask = (taskId) => {
    dispatch({
      type: "delete",
      id: taskId,
    });
  };
      type: "delete",
      id: taskId,
    });
  };

  const updateTask = (taskId, newText) => {
    dispatch({
      type: "update",
      type: "update",
      id: taskId,
      text: newText,
    });
  };
      text: newText,
    });
  };

  const saveChanges = () => {
    updateTask(task.id, editText);
    setEditing(false);
  };
    updateTask(task.id, editText);
    setEditing(false);
  };

  const setTaskDone = (e) => {
    dispatch({
      type: "toggleDone",
      type: "toggleDone",
      id: task.id,
      done: e.target.checked,
      done: e.target.checked,
    });
  };

  };

  return (
    <div className="d-flex justify-content-between">
      <div className="">
        <input
          type="checkbox"
          checked={task.done}
          onChange={(e) => setTaskDone(e)}
        />
        {editing ? (
          <input
            value={editText}
            onChange={(e) => setEditText(e.target.value)}
          />
        ) : (
          <span>{task.text}</span>
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
    <div className="d-flex justify-content-between">
      <div className="">
        <input
          type="checkbox"
          checked={task.done}
          onChange={(e) => setTaskDone(e)}
        />
        {editing ? (
          <input
            value={editText}
            onChange={(e) => setEditText(e.target.value)}
          />
        ) : (
          <span>{task.text}</span>
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
