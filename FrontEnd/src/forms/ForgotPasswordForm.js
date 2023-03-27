import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

const RegisterForgotPasswordForm = () => {
  const navigate = useNavigate();

  const clickHandler = async (event) => {
    event.preventDefault();
    await axios
      .post(
        "/user/forgotpassword",
        {
          emailID: emailRef,
        },
        { headers: { "Content-Type": "application/json" } }
      )
      .then(function (response) {
        console.log(response.data);
        navigate("/securityquestions", { replace: true });
      });
  };
  const [emailRef, setEmailRef] = useState();
  return (
    <div>
      <Typography variant={"h5"} align={"center"} marginBottom={"16px"}>
        Forgot Password
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
          label={"Email"}
          onChange={(e) => {
            setEmailRef(e.target.value);
          }}
        />

        <Button type="submit" onClick={clickHandler}>Submit</Button>
      </Stack>
    </div>
  );
};
export default RegisterForgotPasswordForm;
