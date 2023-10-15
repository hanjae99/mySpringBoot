import { Button, Container, Grid, TextField, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import { signUp } from "../ApiService";

function Signup() {
  const handleSubmit = (e) => {
    e.preventDefault();

    const data = new FormData(e.target);
    const userName = data.get("userName");
    const password = data.get("password");

    signUp({ userName: userName, password: password }).then((response) => {
      alert("계정이 성공적으로 생성되었습니다.");
      window.location.href = "/login";
    });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <form noValidate onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Typography component="h1" variant="h5">
              계정 생성
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <TextField
              autoComplete="fname"
              name="userName"
              variant="outlined"
              required
              fullWidth
              id="userName"
              label="아이디"
              autoFocus
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
              계정 생성
            </Button>
          </Grid>
          <Grid item xs={12}>
            <Link to="/login" variant="body2">
              이미 계정이 있습니까? 로그인 하세요.
            </Link>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default Signup;
