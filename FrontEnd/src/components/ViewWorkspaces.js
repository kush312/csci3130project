import {Card, CardContent, Typography} from '@mui/material';
import WorkspacesPopUpMenu from './workspace-card/WorkspacesPopUpMenu'
import CardHeader from '@mui/material/CardHeader';
import {Link} from "react-router-dom";
import Stack from "@mui/material/Stack";


function ViewWorkspaces(props) {

  return (
    <Stack>
      <Stack spacing={2}>
        {props.workspaces.map((workspace, index) => (
          <Stack key={workspace.id}>
            <Card>
              <CardHeader
                  action={
                    <WorkspacesPopUpMenu workspace={workspace} getWorkspacesFunction={props.getWorkspacesFunction}/>
                  }
                  title={
                    <Typography
                        component={Link}
                        to={{pathname: `/workspace/${workspace.id}`}}
                        variant={"h5"}
                    >
                      {workspace.name}
                    </Typography>
                  }
              />
              <CardContent>
                <Typography variant="body1" color="text.secondary">
                  {workspace.description}
                </Typography>
              </CardContent>
              <CardContent>
                <Typography variant={"body2"} color={"text.secondary"} sx={{mb: 1}}>
                  Workspace Members: {
                  workspace.users.map((user) => (
                          <Typography variant="body3" color="text.secondary" key={user.id}>
                            {user.name + " "}
                          </Typography>
                      )
                  )
                }
                </Typography>
              </CardContent>
            </Card>
          </Stack>
          )
        )}
      </Stack>
    </Stack>
  );
}

export default ViewWorkspaces;