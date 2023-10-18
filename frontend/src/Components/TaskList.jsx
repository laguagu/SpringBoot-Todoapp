import { useContext, useState } from "react";
import { TaskContext } from "../contexts/TaskContext";
import Button from "react-bootstrap/Button";
import { removeTask, updateTask, togleCompleted } from "../api/tasks";

export default function TaskList() {
  const { tasks, dispatch } = useContext(TaskContext);
  return (
    <div className="d-flex flex-column justify-content-center align-items-center p-3 mb-2 bg-secondary text-white rounded">
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
  const deleteTask = async (taskId) => {
    try {
      const removed = await removeTask(taskId);
      if (removed && removed.id) {
        dispatch({
          type: "delete",
          id: removed.id,
        });
      }
    } catch (error) {
      console.error("Error occured during deleting task", error);
    }
  };

  const updateSelectedTask = async (taskId, newDescription) => {
    const updated = await updateTask(taskId, newDescription);
    dispatch({
      type: "update",
      id: taskId,
      description: newDescription,
    });
  };

  const saveChanges = () => {
    updateSelectedTask(task.id, editText);
    setEditing(false);
  };

  const setTaskDone = async (e) => {
    const checkbox = e.target.checked;
    console.log(checkbox);
    const togleDone = await togleCompleted(task, checkbox);
    dispatch({
      type: "toggleDone",
      id: task.id,
      completed: e.target.checked,
    });
  };

  return (
    <div className="d-flex justify-content-between p-3 mb-2 bg-light text-dark rounded">
      <div className="">
        <input
          type="checkbox"
          checked={task.completed}
          onChange={setTaskDone}
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
          <Button onClick={saveChanges}>Save</Button>
        ) : (
          <Button onClick={() => setEditing(true)}>Edit</Button>
        )}
        <Button onClick={() => deleteTask(task.id)}>Delete</Button>
      </div>
    </div>
  );
}
