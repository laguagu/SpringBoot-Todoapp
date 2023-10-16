import { userlogOut } from "../api/users";

export default function Appbar() {
  async function logUser() {
    const response = await fetch("http://localhost:8080/api/user/me", {
      credentials: "include",
    });
    const text = await response.text();
    console.log("VASTAUS:", text);

    if (response.ok) {
      const user = JSON.parse(text);
      console.log(user);
      console.log("Käyttäjä ", user.appUser.username);
    }
  }

  async function logOut() {
    console.log("User logged out");
    userlogOut();
  }

  return (
    <div style={{ padding: 10 }}>
      <button onClick={() => logUser()}>Näytä Käyttäjä</button>
      <button onClick={() => logOut()} style={{ marginLeft: 10 }}>
        Log Out
      </button>
    </div>
  );
}
