export const initialTasks = [
  { id: 0, text: "Philosopher’s Path", done: false },
  { id: 1, text: "Visit the temple", done: false },
  { id: 2, text: "Drink matcha", done: false },
];

function taskReducer(tasks, action) {
  switch (action.type) {
    case "added": {
      return [
        ...tasks,
        {
          id: action.id,
          text: action.text,
          done: false,
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
              text: action.text,
            }
          : task
      );
    }
    case "toggleDone": {
      return tasks.map((t) =>
        t.id === action.id
          ? {
              ...t,
              done: action.done,
            }
          : t
      );
    }

    default: {
      throw Error("Uknown action:" + action.type);
    }
  }
}

export default taskReducer;
