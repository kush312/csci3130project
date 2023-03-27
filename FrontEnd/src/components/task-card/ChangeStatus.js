import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import MenuItem from "@mui/material/MenuItem";
import axios from "axios";
import {FormControl, InputLabel, Select} from "@mui/material";

const ChangeStatus = (props) => {
    const [open, setOpen] = React.useState(false);
    const [taskStatus, setTaskStatus] = React.useState('');
    const statusType = ["To-do", "Doing", "Done"];

    const handleChange = (event) => {
        setTaskStatus(event.target.value);
    };

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        props.menuAnchor();
        setOpen(false);
    };

    const handleSave = async () => {
        await axios.put(
            `/task/changeTaskStatus/${props.task.id}`,
            taskStatus,
            {headers: {"Content-Type": "text/plain"}}
        ).then(function (response) {
            console.log(response.data);
        });

        props.menuAnchor();
        setOpen(false);
        props.getDataFunction()
    }


    return (
        <div>
            <MenuItem onClick={handleClickOpen}>
                Change Task Status
            </MenuItem>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Change Task Status</DialogTitle>
                <DialogContent>
                    <FormControl fullWidth>
                        <Select
                            onChange={handleChange}
                            defaultValue={props.task.status}
                            style={{ marginBottom: "16px" }}
                        >
                            {statusType.map((status) => (
                                <MenuItem key={status} value={status}>
                                    {status}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSave}>Save</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default ChangeStatus;