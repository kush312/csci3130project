import React, { useEffect, useState } from "react";
import ViewWorkspaces from "../components/ViewWorkspaces";
import MaverickNavBar from "../components/MaverickNavBar";
import { Grid, Typography } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import Button from "@mui/material/Button";

const Workspaces = () => {
  const navigate = useNavigate();
  const [workspaceData, setWorkspaceData] = useState([]);

  const getAllWorkspaces = () => {
    fetch(
      `http://localhost:8080/workspace/get/${localStorage.getItem("userID")}`
    )
      .then((response) => response.json())
      .then((workspaces) => {
        setWorkspaceData(workspaces);
      });
  };

  useEffect(
    function () {
      if (localStorage.getItem("token") == null) {
        navigate(`/login`, { replace: true });
      } else {
        getAllWorkspaces();
      }
    },
    [navigate]
  );

  return (
    <section>
      <MaverickNavBar pageType={"dashboard"} />
      <Grid
        container
        direction="row"
        justifyContent="space-between"
        alignItems="flex-end"
      >
        <Grid item>
          <Typography
            variant="h3"
            style={{ marginTop: "16px", marginBottom: "16px", float: "left" }}
          >
            Your Workspaces
          </Typography>
        </Grid>
        <Grid item>
          <Button component={Link} to={`/CreateWorkspace`}>
            New Workspace
          </Button>
        </Grid>
      </Grid>
      <ViewWorkspaces workspaces={workspaceData} getWorkspacesFunction={getAllWorkspaces}/>
    </section>
  );
};

export default Workspaces;
