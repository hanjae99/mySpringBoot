import { API_BASE_URL } from "./api-config";

export function call(api, method, request) {
  let headers = new Headers({
    "Content-Type": "application/json",
  });

  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.append("Authorization", "Bearer " + accessToken);
  }

  let options = {
    headers,
    url: API_BASE_URL + "/" + api,
    method: method,
  };

  if (request) {
    options.body = JSON.stringify(request);
  }

  return fetch(options.url, options)
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 403) {
        // 로그인 실패 시
        console.log("login error");
        window.location.href = "/login";
      } else {
        console.log(options.url, options);
        throw Error(response);
      }
    })
    .catch((error) => {
      console.log(error);
    });
}

// 로그인
export function signin(userDTO) {
  return call("auth/signin", "POST", userDTO).then((response) => {
    localStorage.setItem("ACCESS_TOKEN", response.token);
    console.log("response: ", response);
    // alert("로그인 토큰: " + response.token);
    window.location.href = "/";
  });
}

// 로그아웃
export function signOut() {
  localStorage.setItem("ACCESS_TOKEN", null);
  window.location.href = "/login";
}

// 회원가입
export function signUp(userDTO) {
  return call("auth/signup", "POST", userDTO);
}
