import { useEffect, useState } from "react";
import { userlogOut } from "../api/users";

export default function Appbar() {
  const [appuser, setAppuser] = useState("Ei vielä fetched");

  useEffect(() => {
    const fetchUser = async () => {
      const response = await fetch("http://localhost:8080/api/user/me", {
        credentials: "include",
      });
      const data = await response.json();
      const appUser = data.appUser.username;
      setAppuser(appUser);
      console.log(appUser);
    };
    fetchUser();
  }, []);

  async function logUser() {
    const response = await fetch("http://localhost:8080/api/user/me", {
      credentials: "include",
    });
    const text = await response.text();
    console.log("VASTAUS:", text);

    if (response.ok) {
      const user = JSON.parse(text);
      const userName = user.appUser.username;
      console.log(user);
      console.log("Käyttäjä ", userName);
      return userName;
    }
  }

  async function logOut() {
    console.log("User logged out");
    await userlogOut();
    window.location.href = "http://localhost:8080/login";
  }

  // function showUser() {
  //   if (appuser === "user") {
  //     return (
  //       <div>
  //         Moi {appuser}
  //         <span class="material-symbols-outlined">person</span>
  //       </div>
  //     );
  //   } else {
  //     return <div>Hei {appuser}</div>;
  //   }
  // }

  return (
    <div style={{ padding: 10 }}>
      <div>
        Hei {appuser}{" "}
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
