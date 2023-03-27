import { Card, CardContent, Typography } from "@mui/material";
import CardHeader from "@mui/material/CardHeader";
import TasksPopUpMenu from "./task-card/TasksPopUpMenu";
import Stack from "@mui/material/Stack";

const ViewTasks = (props) => {
  return (
    <Stack spacing={2}>
      {props.tasks.map((task) => (
        <Stack xs={12} sm={12} md={4} lg={3} key={task.id}>
          <Card>
            <CardHeader
              action={
                <TasksPopUpMenu
                  task={task}
                  workspaceID={props.workspaceID}
                  getDataFunction={props.getDataFunction}
                />
              }
              title={task.taskName}
            />
            <CardContent>
              <Typography variant="body2" color="text.secondary">
                Task Description: {task.taskDescription}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Task Status: {task.status}
              </Typography>
              <Typography variant={"body2"} color={"text.secondary"}>
                Due Date:{" "}
                {new Date(Date.parse(task.dueDate)).toLocaleString("en-US", {
                  day: "numeric",
                  month: "short",
                  year: "numeric",
                })}
              </Typography>
              <Typography variant={"body2"} color={"text.secondary"} sx={{mt: 2}}>
                Assigned Users: {
                task.task_users.map((user) => (
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
      ))}
    </Stack>
  );
};
export default ViewTasks;
