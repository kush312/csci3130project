import * as React from 'react';
import CreateIcon from '@mui/icons-material/Create';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import IconButton from "@mui/material/IconButton";
import axios from "axios";
import EditBoardModal from "./EditBoardModal";
import {Grid} from "@mui/material";

export default function BoardPopUpMenu(props) {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    }

    const deleteFunction = async () => {
        setAnchorEl(null);
        await axios.delete(
            `board/delete/${props.board.id}`
        ).then(function (response) {
            console.log(response.data);
            props.getBoardsFunction();
        });
    };

    return (
        <Grid>
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
                    horizontal: 'left',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'left',
                }}
            >
                <EditBoardModal menuAnchor={setAnchorEl} board={props.board} getBoardsFunction={props.getBoardsFunction}/>
                <MenuItem onClick={deleteFunction}>Delete</MenuItem>
            </Menu>
        </Grid>
    );
}