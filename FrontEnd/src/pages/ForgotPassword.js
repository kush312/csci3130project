import RegisterForgotPasswordForm from "../forms/ForgotPasswordForm";
import MaverickNavBar from "../components/MaverickNavBar";
import { Grid } from "@mui/material";

function ForgotPasswordPage() {
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
          <RegisterForgotPasswordForm />
        </Grid>
      </Grid>
    </div>
  );
}
export default ForgotPasswordPage;
