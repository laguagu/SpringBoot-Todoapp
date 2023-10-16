import { useEffect, useState } from "react";
import { fetchUsers, userlogOut } from "../api/users";

export default function Appbar() {
  const [appuser, setAppuser] = useState("null");
  useEffect(() => {
    const findUser = async() => {
      const response = await fetchUsers()
      setAppuser(response.username)
    }
    findUser()
  }, []);
  
  async function logOut() {
    console.log("User logged out");
    await userlogOut();
    window.location.href = "http://localhost:8080/login";
  }

  return (
    <div style={{ padding: 10 }}>
      <div>
        Hei {appuser}
        {appuser === "admin" ? (
          <span className="material-symbols-outlined">manage_accounts</span>
          ) : (
          <span className="material-symbols-outlined">person</span>
        )}
      </div>
      <button onClick={() => logOut()}>Log Out</button>
    </div>
  );
}
