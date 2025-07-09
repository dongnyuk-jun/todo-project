import { useEffect, useState } from "react";
import { fetchTodos } from "../api/todo";
import { useNavigate } from "react-router-dom";

export default function TodoPage() {
    const [todos, setTodos] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if(!token) {
            navigate('/login');
            return;
        }

        fetchTodos()
        .then(setTodos)
        .catch(() => setError('할 일 목록을 불러오지 못했습니다. '));
    }, [navigate]);

    return (
        <div className="p-8">
            <h1 className="text-3xl font-bold-mb-4">할 일 목록</h1>
            {error && <p className="text-red-500">{error}</p>}
            <ul className="space-y-4">
                {todos.map((todo) => (
                    <li key={todo.id} className="border p-2 rounded shadow">
                        <div className="flex justify-between items-center">
                            <div>
                                <h2 className={`text-xl font-semi-bold ${todo.completed ? 'line-through text-gray-500' : ''}`}>
                                    {todo.title}
                                </h2>
                                <p className="text-gray-600">{todo.description}</p>
                                <p className="text-sm text-gray-500">
                                    마감일: {new Date(todo.dueDate).toLocaleString()}
                                </p>
                            </div>
                            <span className={todo.completed ? 'line-through text-gray-500' : ''}>
                                {todo.completed ? '완료' : '미완료'}
                            </span>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}