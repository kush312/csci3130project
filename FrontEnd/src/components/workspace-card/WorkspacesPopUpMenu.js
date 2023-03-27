import * as React from "react";
import CreateIcon from "@mui/icons-material/Create";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import IconButton from "@mui/material/IconButton";
import axios from "axios";
import EditWorkspaceModal from "./EditWorkspaceModal";
import AddUsersWorkspaceModal from "./AddUsersWorkspaceModal";

export default function WorkspacesPopUpMenu(props) {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const deleteFunction = async () => {
    setAnchorEl(null);
    await axios
      .delete(`workspace/delete/${props.workspace.id}`)
      .then(function (response) {
        console.log(response.data);
        props.getWorkspacesFunction();
      });
  };

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
                <EditWorkspaceModal menuAnchor={setAnchorEl} workspace={props.workspace} getWorkspacesFunction={props.getWorkspacesFunction}/>
                <AddUsersWorkspaceModal menuAnchor={setAnchorEl} workspace={props.workspace} getWorkspacesFunction={props.getWorkspacesFunction}/>
                <MenuItem onClick={deleteFunction}>Delete</MenuItem>
            </Menu>
        </div>
    );
}
