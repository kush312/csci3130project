import * as React from "react";
import Menu from "@mui/material/Menu";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";

export default function FilterPopUpMenu(props) {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const boardId = props.boardID;
  const open = Boolean(anchorEl);
  const setTasks = props.setTaskMethod;
  const filterFlag = props.setFilterFlag;

  const filterOverdue = () => {
    fetch(`http://localhost:8080/task/sortDueOverdue/${boardId}`)
      .then((response) => response.json())
      .then((tasks) => {
        setTasks(tasks);
        filterFlag(true);
        setAnchorEl();
      });
  };

  const filterToday = () => {
    fetch(`http://localhost:8080/task/sortDueToday/${boardId}`)
      .then((response) => response.json())
      .then((tasks) => {
        filterFlag(true);
        setTasks(tasks);
        setAnchorEl();
      });
  };

  const filterThisWeek = () => {
    fetch(`http://localhost:8080/task/sortDueWeek/${boardId}`)
      .then((response) => response.json())
      .then((tasks) => {
        filterFlag(true);
        setTasks(tasks);
        setAnchorEl();
      });
  };

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <div>
      <Button
        aria-label="more"
        id="long-button"
        aria-controls={open ? "long-menu" : undefined}
        aria-expanded={open ? "true" : undefined}
        aria-haspopup="true"
        onClick={handleClick}
      >
        Filter Tasks
      </Button>
      <Menu
        id="modify"
        aria-labelledby="modify"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
      >
        <Stack>
          <Button onClick={filterOverdue}>Overdue</Button>

          <Button onClick={filterToday}>Due Today</Button>

          <Button onClick={filterThisWeek}>Due Next Week</Button>
        </Stack>
      </Menu>
    </div>
  );
}
