import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import MenuItem from "@mui/material/MenuItem";
import axios from "axios";
import {useState} from "react";
import {LocalizationProvider} from "@mui/x-date-pickers/LocalizationProvider";
import {AdapterDateFns} from "@mui/x-date-pickers/AdapterDateFns";
import {DesktopDatePicker} from "@mui/x-date-pickers/DesktopDatePicker";
import TextField from "@mui/material/TextField";

const ChangeDueDate = (props) => {
  const [open, setOpen] = React.useState(false);
  const [taskDueDate, setTaskDueDate] = useState(props.task.dueDate);

  const handleDateChange = (event) => {
    setTaskDueDate(event);
    console.log(taskDueDate);
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
        `/task/setDueDate/${props.task.id}`,
        taskDueDate
    ).then(function (response) {
      console.log(response.data);
    });

    props.menuAnchor();
    setOpen(false);
    props.getDataFunction()
    // window.location.reload();
  }


  return (
      <div>
        <MenuItem onClick={handleClickOpen}>
          Change Due Date
        </MenuItem>
        <Dialog open={open} onClose={handleClose}>
          <DialogTitle sx={{mb: 2}}>Change Due Date</DialogTitle>
          <DialogContent>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DesktopDatePicker
                  inputFormat="MM/dd/yyyy"
                  value={taskDueDate}
                  onChange={handleDateChange}
                  renderInput={(params) => <TextField {...params} />}
                  style={{marginTop:"16px", marginBottom: "16px" }}
              />
            </LocalizationProvider>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button onClick={handleSave}>Save</Button>
          </DialogActions>
        </Dialog>
      </div>
  );
}

export default ChangeDueDate;