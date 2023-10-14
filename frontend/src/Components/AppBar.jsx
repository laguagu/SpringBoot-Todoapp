


export default function Appbar() {
  async function logUser() {
    const response = await fetch("http://localhost:8080/api/user/me", {
      credentials: "include",
    });
    const text = await response.text();
    console.log("SIUU",text);

    if (response.ok) {
      const user = JSON.parse(text);
      console.log(user);
    }
  }

  return (
    <div style={{padding: 10}}>
      <button onClick={() => logUser()}>Näytä Käyttäjä</button>
      <button onClick={() => console.log("Logout")} style={{marginLeft:10}}>Log Out</button>
    </div>
  );
}