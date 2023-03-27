import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {useState} from "react";
import Box from "@mui/material/Box";


const CreateWorkspaceForm = () => {
  const navigate = useNavigate();

  const [workspaceName, setWorkspaceName] = useState("");
  const [workspaceDesc, setWorkspaceDesc] = useState("");
  const [workspaceNameErrorState, setWorkspaceNameErrorState] = useState(false);

  const clickHandler = async () => {
    if (workspaceName !== "") {
      await axios.post(
          '/workspace/create',
          {
            name: workspaceName,
            description: workspaceDesc,
            userID: localStorage.getItem("userID")
          },
          {headers: {'Content-Type': 'application/json'}}
      ).then(function (response) {
        console.log(response.data);
        navigate('/workspaces', {replace: true})
      });
    } else {
      setWorkspaceNameErrorState(true);
    }
  };

  return (
      <Box>
        <Typography variant='h4' sx={{marginTop: '16px', marginBottom: '16px'}}>Create a new Workspace!</Typography>
        <TextField
            error={workspaceNameErrorState}
            helperText={
              workspaceNameErrorState?"*Workspace name is a required field":""
            }
            label={"Workspace name"}
            fullWidth={true}
            onChange={(e) => {
              setWorkspaceName(e.target.value);
            }}
            style={{marginBottom: '16px'}}
        />
        <TextField
            label={"Description"}
            minRows={5}
            multiline={true}
            fullWidth={true}
            onChange={(e) => {
              setWorkspaceDesc(e.target.value);
            }}
        />
        <Button type="submit" onClick={clickHandler}>Submit</Button>
      </Box>
  )
}

export default CreateWorkspaceForm;