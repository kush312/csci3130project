import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {useEffect} from "react";

const LoginForm = () => {
  const [emailRef, setEmailRef] = useState();
  const [passwordRef, setPasswordRef] = useState();
  const navigate = useNavigate();

  const setAxiosToken = (token) => {
    if (token != null) {
      localStorage.setItem("token", token);
      localStorage.setItem("userEmailID", emailRef);
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    } else {
      alert("The email address and/or password provided are incorrect")
      delete axios.defaults.headers.common["Authorization"];
    }
  };

  const clickHandler = async () => {
    await axios
      .post(
        "/user/authentication",
        {
          emailID: emailRef,
          password: passwordRef,
        },
        { headers: { "Content-Type": "application/json" } }
      )
      .then((response) => {
        const token = response.data.token;
        setAxiosToken(token);
      });

    if (localStorage.getItem("token") != null) {
      await axios.get("/token/getUserID").then((response) => {
        const userID = response.data.userID;

        localStorage.setItem("userID", userID);
      });
    }

    navigate(`/workspaces`, { replace: true });
  };

  const forgotPasswordClickHandler = () => {
    navigate(`/forgotpassword`, { replace: true });
  };

  useEffect(function () {
    if (localStorage.getItem("token") != null) {
      navigate(`/workspaces`, {replace: true});
    }
  },[navigate]);

    return (
    <div>
      <Typography variant={"h5"} align={"center"} marginBottom={"16px"}>
        Login
      </Typography>
      <Stack
        component="form"
        sx={{
          width: "25ch",
        }}
        spacing={2}
        noValidate
        autoComplete="on"
      >
        <TextField
          label={"Email"}
          align={"center"}
          onChange={(e) => {
            setEmailRef(e.target.value);
          }}
        />
        <TextField
          label={"Password"}
          type="password"
          onChange={(e) => {
            setPasswordRef(e.target.value);
          }}
        />
        <Button onClick={clickHandler}>Submit</Button>
        <Button onClick={forgotPasswordClickHandler}>Forgot Password?</Button>
      </Stack>
    </div>
  );
};

export default LoginForm;
