import axios from "axios";

// In local dev, .env points VITE_API_URL at the backend (http://localhost:8080).
// In the Docker build there is no VITE_API_URL, so we call a relative "/api"
// which nginx proxies to the backend container — same origin, no CORS needed.
const baseURL = import.meta.env.VITE_API_URL
  ? `${import.meta.env.VITE_API_URL}/api`
  : "/api";

const client = axios.create({ baseURL });

// attach the JWT to every request when logged in
client.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// if the token is expired or invalid, log the user out
client.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 && localStorage.getItem("token")) {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default client;
