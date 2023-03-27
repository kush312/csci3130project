import MaverickNavBar from "../components/MaverickNavBar";
import Box from "@mui/material/Box";
import CreateWorkspaceForm from "../forms/CreateWorkspaceForm";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const CreateWorkspacePage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("token") == null) {
      navigate(`/login`, { replace: true });
    }
  }, []);

    return (
        <Box>
          <MaverickNavBar/>
          <CreateWorkspaceForm/>
        </Box>
    );
};

export default CreateWorkspacePage;