import "./App.css";
import { Route, BrowserRouter, Routes } from "react-router-dom";
import CreateWorkspace from "./pages/CreateWorkspace";
import axios from "axios";
import Container from "@mui/material/Container";
import Workspaces from "./pages/Workspaces";
import Workspace from "./pages/Workspace";
import Tasks from "./pages/Tasks";
import React from "react";
import CreateBoard from "./pages/CreateBoard";
import CreateTask from "./pages/CreateTask";
import RegisterPage from "./pages/Register";
import LoginPage from "./pages/Login";
import ForgotPasswordPage from "./pages/ForgotPassword";
import Logout from "./pages/Logout";
import SecurityQuestionsPage from "./pages/SecurityQuestions";
import UpdatePasswordPage from "./pages/UpdatePassword";

const App = () => {
  axios.defaults.baseURL = "http://localhost:8080";
  return (
    <Container>
      <BrowserRouter>
        <Routes>
          <Route path={"/"} element={<Workspaces />} />
          <Route path={"/CreateWorkspace"} element={<CreateWorkspace />} />
          <Route path={"/workspaces"} element={<Workspaces />} />
          <Route path={"/tasks/:boardId/:workspaceId"} element={<Tasks />} />
          <Route path={"/workspace/:id"} element={<Workspace />} />
          <Route path={"/createBoard/:id"} element={<CreateBoard />} />
          <Route
            path={"/createTask/:id/:workspaceId"}
            element={<CreateTask />}
          />
          <Route path={"/register"} element={<RegisterPage />} />
          <Route path={"/login"} element={<LoginPage />} />
          <Route path={"/forgotPassword"} element={<ForgotPasswordPage />} />
          <Route path={"/logout"} element={<Logout />} />
          <Route
            path={"/securityquestions"}
            element={<SecurityQuestionsPage />}
          />
          <Route path={"/updatepassword"} element={<UpdatePasswordPage />} />
        </Routes>
      </BrowserRouter>
    </Container>
  );
};

export default App;
