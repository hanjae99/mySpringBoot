import { Button, Container, Grid, TextField, Typography } from "@mui/material";
import React from "react";
import { signin } from "../ApiService";
import { Link } from "react-router-dom";

function Login() {
  const handleSubmit = (e) => {
    e.preventDefault();

    const data = new FormData(e.target);
    const userName = data.get("userName");
    const password = data.get("password");

    signin({ userName: userName, password: password });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <Grid item xs={12}>
        <Typography component="h1" variant="h5">
          로그인
        </Typography>
      </Grid>
      <form noValidate onSubmit={handleSubmit}>
        {" "}
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="userName"
              label="아이디"
              name="userName"
              autoComplete="userName"
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              name="password"
              label="패스워드"
              type="password"
              id="password"
              autoComplete="current-password"
            />
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" fullWidth variant="contained" color="primary">
              로그인
            </Button>
          </Grid>
          <Grid item>
            <Link to="/signup" variant="body2">
              계정이 없습니까? 여기서 가입 하세요.
            </Link>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default Login;
