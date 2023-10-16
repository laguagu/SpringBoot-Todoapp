import "bootstrap/dist/css/bootstrap.min.css";
import './styles.css';
import AddTask from "./Components/AddTask";
import TaskList from "./Components/TaskList";
import { TaskProvider } from "./contexts/TaskContext";
import Appbar from "./Components/AppBar";

export default function TaskApp() {
  return (
    <TaskProvider>
      <div className="container d-flex flex-column justify-content-center align-items-center vh-100">
      <Appbar/>
      <TaskList />
      <AddTask  />
      </div>
    </TaskProvider>
  );
}
