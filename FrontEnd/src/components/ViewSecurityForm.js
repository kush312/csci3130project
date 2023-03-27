// import TextField from "@mui/material/TextField";
import { Typography } from "@mui/material";
import MaverickNavBar from "../components/MaverickNavBar";

function ViewSecurityForm(props) {
  return (
    <stack>
      <MaverickNavBar />
      {props.questions.map((question) => (
        <Typography variant="body2" color="text.secondary">
          {question.question}
        </Typography>
      ))}
    </stack>
  );
}
export default ViewSecurityForm;
