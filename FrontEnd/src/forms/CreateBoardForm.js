import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {useState} from "react";
import Box from "@mui/material/Box";


const CreateBoardForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [boardNameErrorState, setBoardNameErrorState] = useState(false);
  const [boardName, setBoardName] = useState("");
  const [boardDesc, setBoardDesc] = useState("");

  const handleSubmit = async () => {
    if (boardName !== "") {
      await axios.post(
          '/board/create',
          {
            board_name: boardName,
            description: boardDesc,
            workspaceID: id
          },
          { headers: { 'Content-Type': 'application/json' } }
      ).then(function () {
        navigate(`/workspace/${id}`, { replace: true })
      });
    } else {
      setBoardNameErrorState(true);
    }
  };


  return (
      <Box>
        <Typography variant='h4' sx={{marginTop: '16px', marginBottom: '16px'}}>Create a new board!</Typography>
          <TextField
              error={boardNameErrorState}
              helperText={
                boardNameErrorState?"*Board name is a required field":""
              }
              label={"Board name"}
              required={true}
              fullWidth={true}
              onChange={(e) => {
                setBoardName(e.target.value);
              }}
              style={{marginBottom: '16px'}}
          />
          <TextField
              label={"Board description"}
              minRows={5}
              multiline={true}
              fullWidth={true}
              onChange={(e) => {
                setBoardDesc(e.target.value);
              }}
          />
          <Button type="submit" onClick={handleSubmit}>Submit</Button>
    </Box>
  )
}

export default CreateBoardForm;