import { Box, Typography } from "@mui/material";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "../App";
import Login from "./Login";
import Signup from "./Signup";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {new Date().getFullYear()}
      {". Copyright @ "}새싹마켓
    </Typography>
  );
}

function AppRouter() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
        </Routes>
      </BrowserRouter>
      <Box mt={5}>
        <Copyright />
      </Box>
    </div>
  );
}

export default AppRouter;
