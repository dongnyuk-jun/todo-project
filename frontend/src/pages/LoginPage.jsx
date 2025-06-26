import React, { useState } from 'react';
import axios from 'axios';
import { saveToken } from '../utils/token';
import { useNavigate } from 'react-router-dom'

import { login } from '../api/auth';

function LoginPage() {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate;

    const handleLogin = async() => {
        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', 
                {userId, password,}
            );
            const token = response.data.token;
            saveToken(token);
            localStorage.setItem("token", response.data.token);
            alert('로그인 성공');
            navigate('/todos');
        } catch (error) {
            alert('로그인 실패: ' + error.response?.data?.message || error.message)
        }
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h2 className="text-2xl font-bold mb-4">로그인</h2>
            <input
                type="text"
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
                className='border p-2 mb-2'
            />
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
                className='border p-2 mb-4'
            />
            <button type="submit" className="w-full bg-bule-500 text-white px-4 py-2 rounded">로그인</button>
        </div>
    );
}

export default LoginPage;