import { useEffect, useState } from "react";
import "./App.css";
import Todo from "./Todo";
import {AppBar, Button, Container, Grid, List, Paper, Toolbar, Typography} from "@mui/material";
import AddTodo from "./AddTodo";
import {call, signout} from "./ApiService";

function App() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  //
  useEffect(() => {
    call("/todo", "GET", null).then((response) => {
      setItems(response.data);
      setLoading(false);
    });
  }, []);

  /*

  위 코드로 간단하게 call만 호출해서 사용하도록 외부로 뺌

  const requestOptions = {
    method: "GET",
    headers: {
      "content-type": "application/json",
    },
  };

  // 8282/todo 에 요청
  fetch("http://localhost:8282/todo", requestOptions)
    .then((response) => response.json())
    .then(
      (response) => {
        return setItems(response.data);
      },
      (error) => {}
    );
    */

  const addItem = (item) => {
    call("/todo", "POST", item).then((response) => setItems(response.data));
    console.log("[addItem] items : ", items);
    /*
    위의 call 코드로 대체
    item.id = "ID-" + items.length;
    item.done = false;
    setItems([...items, item]);
    */
  };

  const editItem = (item) => {
    call("/todo", "PUT", item).then((response) => setItems(response.data));
    setItems([...items]);
  };

  const deleteItem = (item) => {
    call("/todo", "DELETE", item).then((response) => setItems(response.data));
    // const newItems = items.filter((e) => e.id !== item.id);
    // setItems([...newItems]);
  };

  const todoItems = items?.length > 0 && (
    <Paper style={{ margin: 16 }}>
      <List>
        {items.map((item) => (
          <Todo
            item={item}
            key={item.id}
            editItem={editItem}
            deleteItem={deleteItem}
          ></Todo>
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
            <Button color="inherit" raised onClick={signout}>
              로그아웃
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );

  const todoListPage = (
    <div>
      {navigationBar}
      <Container maxWidth="md">
        <AddTodo addItem={addItem}></AddTodo>
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  )

  let loadingPage = <h1> 로딩중... </h1>;
  let content = loadingPage;

  if(!loading) {
    content = todoListPage;
  }
  return (
    <div className="App">
      {content}
    </div>
  );
}

export default App;
