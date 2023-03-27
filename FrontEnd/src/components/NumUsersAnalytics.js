import {useEffect, useState} from "react";
import Typography from "@mui/material/Typography";


const NumUsersAnalytics = (props) => {
  const [analyticsString, setAnalyticsString] = useState(' users working on open tasks');
  const [numUsers, setNumUsers] = useState('');

  const getWorkspacesAnalytics = () => {
    fetch(`http://localhost:8080/task/numUsersWorkingOpenTasksInWorkspaces/${localStorage.getItem("userID")}`)
        .then((response) => response.json())
        .then((userCount) => {
          setNumUsers(userCount);
        });
  }

  const getWorkspaceAnalytics = () => {
    fetch(`http://localhost:8080/task/numUsersWorkingOpenTasksInWorkspace/${props.workspaceID}`)
        .then((response) => response.json())
        .then((userCount) => {
          setNumUsers(userCount);
        });
  }

  const getBoardAnalytics = () => {
    fetch(`http://localhost:8080/task/numUsersWorkingOpenTasksInBoard/${props.boardID}`)
        .then((response) => response.json())
        .then((userCount) => {
          setNumUsers(userCount);
        });
  }

  useEffect(() => {
    if (props.pageType === "dashboard") {
      getWorkspacesAnalytics();
    } else if (props.pageType === "workspace") {
      getWorkspaceAnalytics();
    } else if (props.pageType === "board") {
      getBoardAnalytics();
    } else {
      setAnalyticsString('');
    }
  }, []);

  return(
      <Typography variant={"body2"}>
        {numUsers + analyticsString}
      </Typography>
  )
}

export default NumUsersAnalytics;