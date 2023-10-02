import AddTask from "./Components/AddTask";
import TaskList from "./Components/TaskList";
import { useReducer } from "react";
import taskReducer, {initialTasks} from "./redurecs/tasksReducer";

export default function TaskApp() {
  const [tasks, dispatch] = useReducer(taskReducer, initialTasks);
  return (
    <div>
      <h1>Add tasks</h1>
      <AddTask dispatch={dispatch} />
      <TaskList tasks={tasks} dispatch={dispatch}/>
    </div>
  );
}