import UpdatePasswordForm from "../forms/UpdatePasswordForm";
import MaverickNavBar from "../components/MaverickNavBar";
import { Grid } from "@mui/material";
function UpdatePasswordPage() {
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
          <UpdatePasswordForm />
        </Grid>
      </Grid>
    </div>
  );
}

export default UpdatePasswordPage;
