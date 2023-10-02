export default function TaskList({ tasks }) {
  const log = (tasks) => {
    console.log(tasks);
  };
  return (
      <>
      <h1>Todos</h1>
      <ul>
        {tasks.map(task => (
            <li>
                {task.text}
            </li>
        ))}
      </ul>
      <button onClick={() => log(tasks)}>ON click</button>
    </>
  );
}
