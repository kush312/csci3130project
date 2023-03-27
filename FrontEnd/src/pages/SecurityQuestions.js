import SecurityQuestionsForm from "../forms/SecurityQuestionsForm";
import MaverickNavBar from "../components/MaverickNavBar";
import { Grid } from "@mui/material";

function SecurityQuestionsPage() {
  return (
    <div>
      <MaverickNavBar />
      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        justifyContent="center"
        style={{ minHeight: "100vh" }}
      >
        <Grid item xs={3}>
          <SecurityQuestionsForm />
        </Grid>
      </Grid>
    </div>
  );
}

export default SecurityQuestionsPage;
