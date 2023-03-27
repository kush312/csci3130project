import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { useState } from "react";
import Box from "@mui/material/Box";
import { FormControl, InputLabel, Select } from "@mui/material";
import MenuItem from "@mui/material/MenuItem";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DesktopDatePicker } from "@mui/x-date-pickers/DesktopDatePicker";
import { useEffect } from "react";
import DialogContent from "@mui/material/DialogContent";
import Autocomplete from "@mui/material/Autocomplete";

const CreateTaskForm = () => {
  const navigate = useNavigate();
  const [taskNameErrorState, setTaskNameErrorState] = useState(false);

  const handleSubmit = async () => {
    if (taskName !== "") {
      await axios
        .post(
          "/task/create",
          {
            taskName: taskName,
            taskDescription: taskDescription,
            boardID: id,
            status: taskStatus,
            dueDate: taskDueDate,
            dateCreated: newDate,
            task_users: addedUsers,
          },
          { headers: { "Content-Type": "application/json" } }
        )
        .then(function () {
          navigate(`/tasks/${id}/${workspaceId}`, { replace: true });
        });
    } else {
      setTaskNameErrorState(true);
    }
  };

  let newDate = new Date();
  let defaultDueDate = new Date(newDate.getFullYear(), newDate.getMonth(), newDate.getDate() + 7, 23, 59, 59);

  const statusType = ["To-do", "Doing", "Done"];
  const { id, workspaceId } = useParams();
  const [taskName, setTaskName] = useState("");
  const [taskDescription, setTaskDescription] = useState("");
  const [taskStatus, setTaskStatus] = useState(statusType[0]);
  const [taskDueDate, setTaskDueDate] = useState(defaultDueDate);

  const [workspaceUsers, setWorkspaceUsers] = useState([]);
  const [addedUsers, setAddedUsers] = useState([]);




  useEffect(() => {
    const getData = () => {
      fetch(`http://localhost:8080/workspace/getusers/${workspaceId}`)
        .then((response) => response.json())
        .then((workspaceUsers) => {
          setWorkspaceUsers(workspaceUsers);
        });
    };
    getData();
  }, [workspaceId]);

  const handleDateChange = (event) => {
    setTaskDueDate(event);
  };

  const handleChange = (event) => {
    setTaskStatus(event.target.value);
  };

  return (
    <Box>
      <Typography variant="h4" sx={{ marginTop: "16px", marginBottom: "16px" }}>
        Create a new Task!
      </Typography>

      <TextField
        error={taskNameErrorState}
        helperText={
          taskNameErrorState?"*Task name is a required field":""
        }
        label={"Task name"}
        fullWidth={true}
        onChange={(e) => {
          setTaskName(e.target.value);
        }}
        style={{ marginBottom: "16px" }}
      />
      <TextField
        label={"Task description"}
        minRows={5}
        multiline={true}
        fullWidth={true}
        onChange={(e) => {
          setTaskDescription(e.target.value);
        }}
        style={{ marginBottom: "16px" }}
      />

      <FormControl fullWidth>
        <InputLabel id="Status">Status</InputLabel>
        <Select
          labelId="status"
          id="status"
          defaultValue={statusType[0]}
          // value={taskStatus}
          onChange={handleChange}
          label="Status"
          style={{ marginBottom: "16px" }}
        >
          {statusType.map((status) => (
            <MenuItem key={status} value={status}>
              {status}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <LocalizationProvider dateAdapter={AdapterDateFns}>
        <DesktopDatePicker
          label="Due-Date"
          inputFormat="MM/dd/yyyy"
          value={taskDueDate}
          onChange={handleDateChange}
          renderInput={(params) => <TextField {...params} />}
          style={{ marginBottom: "16px" }}
        />
      </LocalizationProvider>

      <DialogContent>
        <Autocomplete
          multiple
          id="tags-standard"
          options={workspaceUsers}
          getOptionLabel={(option) => option.name}
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

      <Button type="submit" onClick={handleSubmit}>
        Submit
      </Button>
    </Box>
  );
};
export default CreateTaskForm;
