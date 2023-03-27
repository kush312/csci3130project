import {useNavigate, useParams} from "react-router-dom";
import { useState } from "react";
import { useEffect } from "react";
import MaverickNavBar from "../components/MaverickNavBar";
import ViewBoards from "../components/ViewBoards.js";
import { Grid, Typography } from "@mui/material";
import React from "react";
import { Link } from "react-router-dom";
import Button from "@mui/material/Button";

const Workspace = () => {
  const { id } = useParams();
  const [workspace, setWorkspace] = useState({});
  const [boards, setBoards] = useState([]);
  const navigate = useNavigate();
  const [isLoading, setLoading] = useState(true);

  const getData = () => {
    fetch(`http://localhost:8080/workspace/${id}`)
        .then((response) => response.json())
        .then((workspace) => {
          setWorkspace(workspace);
          setLoading(false);
        });

    fetch(`http://localhost:8080/board/get/${id}`)
        .then((response) => response.json())
        .then((boards) => {
          setBoards(boards);
        });
  };

  useEffect(() => {
      if (localStorage.getItem("token") == null) {
        navigate(`/login`, { replace: true });
      } else {
        getData();
      }
  }, [navigate]);

  if (isLoading === false) {
    return (
      <section>
        <MaverickNavBar pageType={"workspace"} workspaceID={id}/>
        <Grid sx={{mt: 2}}>
          <Typography component={Link} to={{pathname: `/workspaces`}} variant={"h7"}>Dashboard</Typography>
        </Grid>
        <Typography variant="h4" component="h3" style={{ marginTop: "8px" }}>
          {"Workspace Name: " + workspace.name}
        </Typography>
        <Grid
          container
          direction="row"
          justifyContent="space-between"
          alignItems="flex-start"
        >
          <Grid item>
            <Typography
              variant="h5"
              style={{ marginTop: "16px", marginBottom: "16px", float: "left" }}
            >
              {"Workspace description: " + workspace.description}
            </Typography>
          </Grid>
          <Grid item>
            <Button component={Link} to={`/createBoard/${workspace.id}`}>
              Create new Board
            </Button>
          </Grid>
        </Grid>
        <ViewBoards boards={boards} workspace={workspace} getBoardsFunction={getData}/>
      </section>
    );
  }
};

export default Workspace;
