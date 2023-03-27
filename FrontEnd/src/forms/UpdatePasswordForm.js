import { useEffect, useState } from "react";
import axios from "axios";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import TextField from "@mui/material/TextField";
import { useNavigate } from "react-router-dom";
import * as React from "react";

const UpdatePasswordForm = () => {
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState([]);
  const [password, setPassword] = useState();
  const [passwordState, setState1] = useState("");

  const getUserDetails = () => {
    axios
      .get("/user/getuserdetails")
      .then((response) => setUserDetails(response.data));
  };
  useEffect(function () {
    getUserDetails();
  }, []);

  const passwordchange = (event) => {
    const pass = event.target.value;
    const regex =
        "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    if (pass.match(regex)) {
      setPassword(event.target.value);
      setState1(false);
    } else {
      setState1(true);
    }
  };

  const clickHandler = () => {
    axios
      .put(
        `/user/updatepassword/${userDetails.id}`,
        {
          emailID: userDetails.emailID,
          password: password,
        },
        { headers: { "Content-Type": "application/json" } }
      )
      .then((response) => {
        console.log(response.data);
        navigate(`/login`, { replace: true });
      });
  };
  return (
    <div>
      <Typography variant={"h5"} align={"center"} marginBottom={"16px"}>
        Enter new Password
      </Typography>
      <Stack
        component="form"
        sx={{
          width: "25ch",
        }}
        spacing={2}
        noValidate
        autoComplete="off"
      >
        <TextField
            error={passwordState}
            helperText={
              passwordState
                  ? "*Password must have a minimum length of 8 characters, at least 1 uppercase character, 1 lowercase character, 1 number and 1 special character"
                  : " "
            }
            label={"Password"}
            type="password"
            onChange={passwordchange}
        />
        <Button onClick={clickHandler}>Submit</Button>
      </Stack>
    </div>
  );
};

export default UpdatePasswordForm;
