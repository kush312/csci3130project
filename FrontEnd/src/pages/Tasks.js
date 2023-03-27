import MaverickNavBar from "../components/MaverickNavBar";
import { Grid, Typography } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import { useState } from "react";
import { useEffect } from "react";
import * as React from "react";
import ViewTask from "../components/ViewTask.js";
import { Link } from "react-router-dom";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FilterPopUpMenu from "../components/task-card/FilterPopUpMenu";

const Tasks = () => {
  const { boardId, workspaceId } = useParams();
  const [workspace, setWorkspace] = useState();
  const [board, setBoard] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [name, setName] = useState("");
  const [isLoading, setLoading] = useState(true);
  const [clearFilterFlag, setClearFilterFlag] = useState(false);
  const [clearSearchFlag, setClearSearchFlag] = useState(false);
  const navigate = useNavigate();

  const getData = () => {
    setClearFilterFlag(false);
    setClearSearchFlag(false);
    fetch(`http://localhost:8080/board/getBoard/${boardId}`)
      .then((response) => response.json())
      .then((board) => {
        setBoard(board);
      });

    fetch(`http://localhost:8080/workspace/${workspaceId}`)
      .then((response) => response.json())
      .then((workspace) => {
        setWorkspace(workspace);
        setLoading(false);
      });

    fetch(`http://localhost:8080/task/get/${boardId}`)
      .then((response) => response.json())
      .then((tasks) => {
        setTasks(tasks);
      });
  };

  const searchName = () => {
    if (name !== "") {
      fetch(`http://localhost:8080/task/searchByName/${boardId}/${name}`)
        .then((response) => response.json())
        .then((tasks) => {
          setTasks(tasks);
        });
      setName("");
      setClearSearchFlag(true);
    } else {
      getData();
    }
  };

  useEffect(() => {
    if (localStorage.getItem("token") == null) {
      navigate(`/login`, { replace: true });
    } else {
      getData();
    }
  }, []);

  if (isLoading === false) {
    return (
      <section>
        <MaverickNavBar pageType={"board"} boardID={boardId}/>
        <Grid sx={{mt: 2}}>
          <Typography component={Link} to={{pathname: `/workspaces`}} variant={"h7"} >Dashboard</Typography>
          <Typography variant={"h7"}>-></Typography>
          <Typography
            component={Link}
            to={{ pathname: `/workspace/${workspaceId}` }}
            variant={"h7"}
          >
            {workspace.name}
          </Typography>
        </Grid>
        <Grid
          container
          direction="row"
          justifyContent="space-between"
          alignItems="flex-end"
        >
          <Grid item>
            <Typography
              variant="h4"
              component="h4"
              style={{ marginTop: "8px" }}
            >
              {"Board name: " + board.board_name}
            </Typography>
          </Grid>
          <Grid item>
            <Grid
              container
              direction="row"
              justifyContent="right"
              alignItems="center"
            >
              {clearSearchFlag && (
                <Grid item>{<Button onClick={getData}>Clear</Button>}</Grid>
              )}
              <Grid item>
                <TextField
                  label={"Task Name search"}
                  minRows={1}
                  multiline={false}
                  fullWidth={false}
                  value={name}
                  onChange={(e) => {
                    setName(e.target.value);
                  }}
                />
              </Grid>
              <Grid item>
                <Button type={"submit"} onSubmit={searchName} onClick={searchName}>
                  Search
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
        <Grid
          container
          direction="row"
          justifyContent="space-between"
          alignItems="center"
        >
          <Grid item>
            <Typography
              variant="h6"
              style={{ marginTop: "16px", marginBottom: "16px", float: "left" }}
            >
              {"Board description: " + board.description}
            </Typography>
          </Grid>
          <Grid item>
            <Grid
              container
              direction="row"
              justifyContent="right"
              alignItems="flex-end"
            >
              <Grid item>
                <Button
                  component={Link}
                  to={`/createTask/${boardId}/${workspaceId}`}
                >
                  Create new Task
                </Button>
              </Grid>
              <Grid item>
                <FilterPopUpMenu
                  setFilterFlag={setClearFilterFlag}
                  setTaskMethod={setTasks}
                  boardID={boardId}
                  getDataFunction={getData}
                />
              </Grid>
              {clearFilterFlag && (
                <Grid item>
                  {<Button onClick={getData}>Clear Filter</Button>}
                </Grid>
              )}
            </Grid>
          </Grid>
        </Grid>
        <ViewTask
          tasks={tasks}
          workspaceID={workspaceId}
          flag={clearFilterFlag}
          getDataFunction={getData}
        />
      </section>
    );
  }
};
export default Tasks;
