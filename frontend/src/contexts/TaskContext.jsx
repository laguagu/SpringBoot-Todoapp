import { createContext, useEffect, useReducer } from 'react';
import taskReducer from '../redurecs/tasksReducer';
import { fetchTasks } from '../api/tasks';

export const TaskContext = createContext();


export const TaskProvider = ({ children }) => {
  const [tasks, dispatch] = useReducer(taskReducer, []);

  useEffect(() => {
    const loadData = async () => {
      const fetchedTaskes = await fetchTasks();
      dispatch({type:"SET_TASKS", tasks: fetchedTaskes})
    }
    loadData()
  }, [])

  return (
    <TaskContext.Provider value={{ tasks, dispatch }}>
      {children}
    </TaskContext.Provider>
  );
};