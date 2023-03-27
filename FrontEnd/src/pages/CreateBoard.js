import MaverickNavBar from "../components/MaverickNavBar";
import CreateBoardForm from "../forms/CreateBoardForm";
import Box from "@mui/material/Box";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";


const CreateBoard = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("token") == null) {
      navigate(`/login`, { replace: true });
    }
  }, []);

  return (
    <Box>
      <MaverickNavBar/>
      <CreateBoardForm/>
    </Box>
  );
}

export default CreateBoard;