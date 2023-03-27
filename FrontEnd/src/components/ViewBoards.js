import { Card, CardContent, Typography } from "@mui/material";
import CardHeader from "@mui/material/CardHeader";
import BoardPopUpMenu from "./board-card/BoardPopUpMenu";
import { Link } from "react-router-dom";
import Stack from "@mui/material/Stack";

const ViewBoards = (props) => {
  return (
    <Stack spacing={2}>
      {props.boards.map((board) => (
        <Stack xs={12} sm={12} md={4} lg={3} key={board.id}>
          <Card>
            <CardHeader
              action={
              <BoardPopUpMenu board={board} getBoardsFunction={props.getBoardsFunction}/>
            }
              title={
                <Typography
                  component={Link}
                  to={{
                    pathname: `/tasks/${board.id}/${props.workspace.id}`,
                  }}
                  variant={"h5"}
                >
                  {board.board_name}
                </Typography>
              }
            />
            <CardContent>
              <Typography variant="body2" color="text.secondary">
                {board.description}
              </Typography>
            </CardContent>
          </Card>
        </Stack>
      ))}
    </Stack>
  );
};

export default ViewBoards;
