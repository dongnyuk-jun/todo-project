import React, { useEffect, useState } from "react";
import axios from '../utils/axiosInstance'

const TodoPage = () => {
    const [todos, setTodos] = useState('');

    useEffect(() => {
        fetchTodos();
    }, []);

    const fetchTodos = async () => {
        try {
            const response = await axios.get('/todos');
            setTodos(response.data);
        } catch (error) {
            alert('할 일을 불러오지 못했습니다: ' + error.message);
        }
    }

    return (
        <div className="p-8">
            <h1 className="text-2xl font-bold mb-4">할 일 목록</h1>
            <ul className="space-y-2">
                {todos.map((todo) => (
                    <li key={todo.id} className="border p-2 rounded bg-white shadow">
                        <span className={todo.completed ? 'line-through text-gray-400' : ''}>
                            {todo.title}
                        </span>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TodoPage;