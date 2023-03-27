import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { FormControl, InputLabel, Select } from "@mui/material";
import MenuItem from "@mui/material/MenuItem";
import * as React from "react";

const RegisterForm = () => {
  const [passwordState, setState1] = useState("");
  const [emailState, setState2] = useState("");
  const [userNameState, setState3] = useState("");
  const [answerState, setState4] = useState("");

  const navigate = useNavigate();
  const [nameRef, setNameRef] = useState();
  const [emailRef, setEmailRef] = useState();
  const [passwordRef, setPasswordRef] = useState();
  const [securityQuestionRef, setSecurityQuestionRef] = useState("");
  const [securityAnswerRef, setSecurityAnswerRef] = useState("");

  const securityQuestions = [
    "What city were you born in?",
    "What is your mothers maiden name?",
  ];

  const handleChange = (event) => {
    setSecurityQuestionRef(event.target.value);
  };

  const passwordchange = (event) => {
    const pass = event.target.value;
    const regex =
      "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    if (pass.match(regex)) {
      setPasswordRef(event.target.value);
      setState1(false);
    } else {
      setState1(true);
    }
  };

  const emailChange = (event) => {
    setEmailRef(event.target.value);
    if (event.target.value !== "") {
      setState2(false);
    } else {
      setState2(true);
    }
  };

  const userNameChange = (event) => {
    setNameRef(event.target.value);
    if (event.target.value !== "") {
      setState3(false);
    } else {
      setState3(true);
    }
  };

  const answerChange = (event) => {
    setSecurityAnswerRef(event.target.value);
    if (event.target.value !== "") {
      setState4(false);
    } else {
      setState4(true);
    }
  };

  const clickHandler = async (event) => {
    event.preventDefault();
    await axios
      .post(
        "/user/save",
        {
          name: nameRef,
          emailID: emailRef,
          password: passwordRef,
          question: securityQuestionRef,
          answer: securityAnswerRef,
        },
        { headers: { "Content-Type": "application/json" } }
      )
      .then(function (response) {
        console.log(response.data);
        navigate("/login", { replace: true });
      });
  };

  return (
    <Box>
      <Typography variant={"h5"} align={"center"} marginBottom={"16px"}>
        Register
      </Typography>
      <Stack
        component="form"
        sx={{
          width: "25ch",
        }}
        spacing={1}
        noValidate
        autoComplete="off"
      >
        <TextField
          label={"User Name"}
          align={"center"}
          error={userNameState}
          helperText={userNameState ? "*Enter a user name" : " "}
          onChange={userNameChange}
        />
        <TextField
          error={emailState}
          helperText={emailState ? "*Enter an Email Address" : " "}
          label={"Email"}
          onChange={emailChange}
        />
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
        <FormControl fullWidth>
          <InputLabel id="Security Question">SecurityQuestion</InputLabel>
          <Select
            labelId="security question"
            id="security question"
            value={securityQuestionRef}
            onChange={handleChange}
            label="Security Question"
          >
            {securityQuestions.map((question) => (
              <MenuItem key={question} value={question}>
                {question}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <TextField
          label={"Security Answer"}
          error={answerState}
          helperText={answerState ? "*Security answer required" : " "}
          onChange={answerChange}
        />
        <Button type="submit" onClick={clickHandler}>
          Submit
        </Button>
      </Stack>
    </Box>
  );
};
export default RegisterForm;
