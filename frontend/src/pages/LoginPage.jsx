import { useState } from 'react';
import { login } from '../api/auth';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await login(userId, password);
            console.log(localStorage.getItem('accessToken'));
            navigate('/todos');
        } catch (err) {
            setError('아이디 또는 비밀번호를 확인해주세요. ');
        }
    };

    return (
        <div className='flex flex-col items-center mt-32'>
            <h2 className='text-2xl font-bold mb-4'>로그인</h2>
            <form onSubmit={handleSubmit} className='space-y-4 w-64'>
                <input
                type='text'
                placeholder='User ID'
                className='w-full border p-2 rounded'
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
                />

                <input
                type='password'
                placeholder='Password'
                className='w-full border p-2 rounded'
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                />

                {error && <p className='text-red-500'>{error}</p>}

                <button type='submit' className='w-full bg-blue-500 text-white p-2 rounded'>로그인</button>
            </form>
        </div>
    )
}