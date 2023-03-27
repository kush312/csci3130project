import * as React from 'react';
import { useState } from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import MenuItem from "@mui/material/MenuItem";
import axios from "axios";
import {List, ListItem, ListItemText} from "@mui/material";
import Typography from "@mui/material/Typography";


const AssignUsersTaskModal = (props) => {
    const [open, setOpen] = useState(false);
    const [userList, setUserList] = useState([]);
    const [currentlyAssignedUsers, setCurrentlyAssignedUsers] = useState([]);
    const [addedUsers, setAddedUsers] = useState([]);

    const handleClickOpen = () => {
        const getData = () => {
            fetch(`http://localhost:8080/workspace/getusers/${props.workspaceID}`)
                .then((response) => response.json())
                .then((workspaceUsers) => {
                    setUserList(workspaceUsers);
                });
            fetch(`http://localhost:8080/task/getAssignedUsers/${props.task.id}`)
                .then((response) => response.json())
                .then((currentlyAssignedUserList) => {
                    setCurrentlyAssignedUsers(currentlyAssignedUserList);
                })
        };
        getData();
        setOpen(true);
    };

    const handleClose = () => {
        props.menuAnchor();
        setOpen(false);
    };

    const handleSave = async () => {
        await axios.put(
            `/task/assignUsers/${props.task.id}`,
            addedUsers,
            { headers: { 'Content-Type': 'application/json' } }
        ).then(function (response) {
            console.log(response.data);
        });

        props.menuAnchor();
        setOpen(false);
        props.getDataFunction()
    }

        let keys = Object.keys(currentlyAssignedUsers.reduce((a, {id, emailID}) => Object.assign(a, {[id + "_" + emailID] : undefined}), {}))

        let unAssignedUsers = userList.filter(({id, emailID}) => !keys.includes(id + "_" + emailID));


    return (
        <div>
            <MenuItem onClick={handleClickOpen}>
                Assign Users
            </MenuItem>
            <Dialog
                open={open}
                onClose={handleClose}
                fullWidth={true}
            >
                <DialogTitle style ={{ marginBottom: '4px' }}>Assign Users</DialogTitle>
                <DialogContent>
                    <Typography sx={{ mt: 2, mb: 1 }} variant="h7" component="div">
                        Currently Assigned Users
                    </Typography>
                    <List dense={true}>
                        {currentlyAssignedUsers.map((user) => (
                            <ListItem>
                                <ListItemText
                                    primary={user.name}
                                />
                            </ListItem>)
                        )}
                    </List>
                    <Autocomplete
                        multiple
                        id="tags-standard"
                        options={unAssignedUsers}
                        getOptionLabel={(option) => option.emailID}
                        onChange={(event, newInputValue) => {
                            setAddedUsers(newInputValue);
                        }}
                        renderInput={(params) => (
                            <TextField
                                {...params}
                                variant="standard"
                                label="Assign Users"
                                placeholder="Users"
                            />
                        )}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSave}>Add</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default AssignUsersTaskModal;