import axiosInstance from "../utils/axiosInstance";

export const addTodo = async (content) => {
    const response = await axiosInstance.post('/todos', { content });
    return response.data;
};