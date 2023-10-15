import { useEffect, useState } from "react";
import "./App.css";
import Todo from "./Todo";
import {
  AppBar,
  Button,
  Container,
  Grid,
  List,
  Paper,
  Toolbar,
  Typography,
} from "@mui/material";
import AddTodo from "./AddTodo";
import { call, signOut } from "./ApiService";
import AccessTimeIcon from "@mui/icons-material/AccessTime";

function App() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    call("todo", "GET").then((response) => {
      setItems(response.data);
      setLoading(false);
    });
  }, []);

  const addItem = (item) => {
    // item.id = "ID-" + items.length;
    // item.done = false;
    // setItems([...items, item]);
    // console.log("===== items: " + items);

    call("todo", "POST", item).then((response) => {
      console.log(response);
      setItems(response.data);
    });
  };

  const editItem = (item) => {
    // setItems([...items]);

    call("todo", "PUT", item).then((response) => setItems(response.data));
  };

  const deleteItem = (item) => {
    // const newItems = items.filter((item) => item.id !== e.id);
    // setItems([...newItems]);

    call("todo", "DELETE", item).then((response) => {
      console.log(response);
      setItems(response.data);
    });
  };

  const todoItems = items && items.length > 0 && (
    <Paper style={{ margin: 16 }}>
      <List>
        {items.map((item) => (
          <Todo
            item={item}
            key={item.id}
            editItem={editItem}
            deleteItem={deleteItem}
          />
        ))}
      </List>
    </Paper>
  );

  const navigationBar = (
    <AppBar position="static">
      <Toolbar>
        <Grid justifyContent="space-between" container>
          <Grid item>
            <Typography variant="h6">오늘의 할일</Typography>
          </Grid>
          <Grid item>
            <Button color="inherit" raised="true" onClick={signOut}>
              로그아웃
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );

  const todoListPage = (
    <div className="App">
      {navigationBar}
      <Container maxWidth="md">
        <AddTodo addItem={addItem} />
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );

  let loadingPage = (
    <h1>
      <AccessTimeIcon />
    </h1>
  );
  let content = loadingPage;

  if (!loading) {
    content = todoListPage;
  }

  return <div className="App">{content}</div>;
}

export default App;
