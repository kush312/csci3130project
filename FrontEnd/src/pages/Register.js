import RegisterForm from "../forms/RegisterForm";
import MaverickNavBar from "../components/MaverickNavBar";
import { Grid } from "@mui/material";

function RegisterPage() {
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
          <RegisterForm />
        </Grid>
      </Grid>
    </div>
  );
}
export default RegisterPage;
