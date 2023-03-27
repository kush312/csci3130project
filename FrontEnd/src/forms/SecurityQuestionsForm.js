import { useEffect, useState } from "react";
import axios from "axios";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import TextField from "@mui/material/TextField";
import { useNavigate } from "react-router-dom";

const SecurityQuestionsForm = () => {
  const navigate = useNavigate();
  const [securityQuestion, setSecurityQuestion] = useState([]);
  const [answer, setAnswer] = useState();

  const getSecurityQuestion = () => {
    axios
      .get("/user/getSecurityQuestion")
      .then((response) => setSecurityQuestion(response.data));
  };

  useEffect(function () {
    getSecurityQuestion();
  }, []);

  const checkResponse = (check) => {
    if (check != null) {
      navigate("/updatepassword", { replace: true });
    } else {
      navigate("/securityquestions", { replace: true });
    }
  };

  const clickHandler = async (event) => {
    event.preventDefault();
    await axios
      .post(
        "/user/checkanswer",
        {
          emailID: securityQuestion.emailID,
          answer: answer,
        },
        { headers: { "Content-Type": "application/json" } }
      )
      .then((response) => {
        console.log(response.data);
        const check = response.data.id;
        checkResponse(check);
      });
  };

  return (
    <div>
      <Stack
        component="form"
        sx={{
          width: "60ch",
        }}
        spacing={2}
        noValidate
        autoComplete="off"
      >
        <Typography variant={"h5"} align={"center"} marginBottom={"16px"}>
          Answer the following security question:
        </Typography>
        <Typography variant={"h6"} align={"center"} marginBottom={"16px"}>
          {securityQuestion.question}
        </Typography>
        <TextField
          onChange={(e) => {
            setAnswer(e.target.value);
          }}
        />
        <Button type="submit" onClick={clickHandler}>
          Submit
        </Button>
      </Stack>
    </div>
  );
};
export default SecurityQuestionsForm;
