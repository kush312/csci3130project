import * as React from "react";
import CreateIcon from "@mui/icons-material/Create";
import Menu from "@mui/material/Menu";
import IconButton from "@mui/material/IconButton";
import ChangeStatus from "./ChangeStatus";
import AssignUsersTaskModal from "./AssignUsersTaskModal";
import ChangeDueDate from "./ChangeDueDate";

export default function TasksPopUpMenu(props) {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  // const deleteFunction = async () => {
  //   setAnchorEl(null);
  //   await axios
  //     .delete(`workspace/delete/${props.workspace.id}`)
  //     .then(function (response) {
  //       console.log(response.data);
  //       window.location.reload();
  //     });
  // };

    return (
        <div>
            <IconButton
                aria-label="more"
                id="long-button"
                aria-controls={open ? 'long-menu' : undefined}
                aria-expanded={open ? 'true' : undefined}
                aria-haspopup="true"
                onClick={handleClick}
            >
                <CreateIcon />
            </IconButton>
            <Menu
                id="modify"
                aria-labelledby="modify"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
            >
                <ChangeStatus menuAnchor={setAnchorEl} task={props.task} getDataFunction={props.getDataFunction}/>
                <ChangeDueDate menuAnchor={setAnchorEl} task={props.task} getDataFunction={props.getDataFunction}/>
                <AssignUsersTaskModal menuAnchor={setAnchorEl} task={props.task} workspaceID={props.workspaceID} getDataFunction={props.getDataFunction}/>
                {/*<MenuItem onClick={deleteFunction}>Delete</MenuItem>*/}
            </Menu>
        </div>
    );
}
