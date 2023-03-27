import LoginForm from "../forms/LoginForm";
import MaverickNavBar from "../components/MaverickNavBar";
import { Grid } from "@mui/material";
import Box from "@mui/material/Box";

function Login() {
  return (
    <Box>
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
          <LoginForm />
        </Grid>
      </Grid>
    </Box>
  );
}

export default Login;
