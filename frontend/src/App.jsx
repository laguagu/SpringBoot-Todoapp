import AddTask from './Components/AddTaks.jsx';
import TaskList from './Components/TaskList.jsx';
import { TasksProvider } from './Components/TaksContext.jsx';

export default function TaskApp() {
  return (
    <TasksProvider>
      <h1>Day off in Kyoto</h1>
      <AddTask />
      <TaskList />
    </TasksProvider>
  );
}
