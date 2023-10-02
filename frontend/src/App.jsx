import AddTask from "./Components/AddTask";
import TaskList from "./Components/TaskList";
import { useReducer } from "react";
import taskReducer from "./redurecs/tasksReducer";

const initialTasks = [
  { id: 0, text: 'Philosopher’s Path', done: true },
  { id: 1, text: 'Visit the temple', done: false },
  { id: 2, text: 'Drink matcha', done: false }
];

export default function TaskApp() {
  const [task,dispatch] = useReducer(taskReducer,initialTasks)
  return (
    <div>
      <h1>Add taskes</h1>
      <AddTask />
      <TaskList tasks={task}/>
    </div>
  );
}
