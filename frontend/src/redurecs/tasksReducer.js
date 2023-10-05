
function taskReducer(tasks, action) {
  switch (action.type) {
    case "added": {
      return [
        ...tasks,
        {
          id: action.id,
          description: action.description,
          completed: false,
        },
      ];
    }
    case "delete": {
      return tasks.filter((t) => t.id !== action.id);
    }
    case "update": {
      return tasks.map((task) =>
        task.id === action.id
          ? {
              ...task,
              description: action.description,
            }
          : task
      );
    }
    case "toggleDone": {
      return tasks.map((t) =>
        t.id === action.id
          ? {
              ...t,
              completed: action.done,
            }
          : t
      );
    }
    case "SET_TASKS": {
      return action.tasks
    }

    default: {
      throw Error("Uknown action:" + action.type);
    }
  }
}

export default taskReducer;
