import axios from 'axios';
//import { getToken } from './token';

const token = localStorage.getItem('token');

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        Authorization: token ? 'Bearer ${token}' : '',
        withCreadentials: true,
    }
});

/*
axiosInstance.interceptors.request.use((config) => {
    const token = getToken();
    if(token) {
        config.headers.Authorization = 'Bearer ${token}';
    }
    return config;
});
*/

export default axiosInstance;