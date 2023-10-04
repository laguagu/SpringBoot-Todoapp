import "bootstrap/dist/css/bootstrap.min.css";
import AddTask from "./Components/AddTask";
import TaskList from "./Components/TaskList";
import { TaskProvider } from "./contexts/TaskContext";

export default function TaskApp() {
  return (
    <TaskProvider>
      <h1>Add tasks</h1>
      <AddTask  />
      <TaskList />
    </TaskProvider>
  );
}
