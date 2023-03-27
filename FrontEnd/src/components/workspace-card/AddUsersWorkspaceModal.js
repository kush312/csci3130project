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


const AddUsersWorkspaceModal = (props) => {
    const [open, setOpen] = useState(false);
    const [userList, setUserList] = useState([]);
    const [addedUsers, setAddedUsers] = useState([]);

    // const [workspaceDesc, setWorkspaceDesc] = React.useState(null);

    const handleClickOpen = () => {
        const getData = () => {
            fetch(`http://localhost:8080/user/getAll`)
                .then(response => response.json())
                .then(users => {
                    setUserList(users)
                });
        }
        getData();
        setOpen(true);
    };

    const handleClose = () => {
        props.menuAnchor();
        setOpen(false);
    };

    const handleSave = async () => {
        await axios.put(
            `/workspace/addUsers/${props.workspace.id}`,
            addedUsers,
            { headers: { 'Content-Type': 'application/json' } }
        ).then(function (response) {
            console.log(response.data);
        });

        props.menuAnchor();
        setOpen(false);
        props.getWorkspacesFunction();
    }

    return (
        <div>
            <MenuItem onClick={handleClickOpen}>
                Add User
            </MenuItem>
            <Dialog
                open={open}
                onClose={handleClose}
                fullWidth={true}
            >
                <DialogTitle style ={{ marginBottom: '16px' }}>Add User</DialogTitle>
                <DialogContent>
                    <Autocomplete
                        multiple
                        id="tags-standard"
                        options={userList}
                        getOptionLabel={(option) => option.emailID}
                        onChange={(event, newInputValue) => {
                            setAddedUsers(newInputValue);
                        }}
                        renderInput={(params) => (
                            <TextField
                                {...params}
                                variant="standard"
                                label="Add Users"
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

export default AddUsersWorkspaceModal;