export async function login(userId, password) {
    const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({userId, password}),
    });

    if(!response.ok) {
        throw new Error("로그인 실패");
    }

    return await response.json();
}