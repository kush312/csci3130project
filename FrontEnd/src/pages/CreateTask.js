import MaverickNavBar from "../components/MaverickNavBar";
import CreateTaskForm from "../forms/CreateTaskForm";
import Box from "@mui/material/Box";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const CreateTask = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("token") == null) {
      navigate(`/login`, { replace: true });
    }
  }, []);

  return (
    <Box>
      <MaverickNavBar />
      <CreateTaskForm />
    </Box>
  );
};

export default CreateTask;
