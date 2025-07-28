const api = axios.create({
  baseURL: "http://localhost:8080/bmart",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("jwtToken");

  // Skip adding token for public routes like login/register
  const isPublic =
    config.url.includes("/auth/login") ||
    config.url.includes("/customers") ||
    config.url.includes("/admin");

  if (token && !isPublic) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;
