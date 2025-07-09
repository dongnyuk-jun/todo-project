export async function fetchTodos() {
    const token = localStorage.getItem('accessToken');
    const response = await fetch('http://localhost:8080/todos', {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if(!response.ok) {
        throw new Error('할 일 목록 불러오기 실패');
    }

    return await response.json();
}