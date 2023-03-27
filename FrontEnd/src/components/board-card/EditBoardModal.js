import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import MenuItem from "@mui/material/MenuItem";
import axios from "axios";

const EditBoardModal = (props) => {
    const [open, setOpen] = React.useState(false);
    const [boardName, setBoardName] = React.useState(null);
    const [boardDesc, setBoardDesc] = React.useState(null);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        props.menuAnchor();
        setOpen(false);
    };

    const handleSave = async () => {
        await axios.put(
            `/board/update/${props.board.id}`,
            {
                board_name: boardName,
                description: boardDesc
            },
            { headers: { 'Content-Type': 'application/json' } }
        ).then(function (response) {
            console.log(response.data);
        });

        props.menuAnchor();
        setOpen(false);
        props.getBoardsFunction();
    }


    return (
        <div>
            <MenuItem onClick={handleClickOpen}>
                Edit
            </MenuItem>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Edit Workspace</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="name"
                        label="Name"
                        type="text"
                        fullWidth
                        variant="standard"
                        defaultValue={props.board.board_name}
                        onChange={(e) => {
                            setBoardName(e.target.value);
                        }}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="description"
                        label="Description"
                        type="text"
                        fullWidth
                        variant="standard"
                        defaultValue={props.board.description}
                        onChange={(e) => {
                            setBoardDesc(e.target.value);
                        }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSave}>Save</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default EditBoardModal;