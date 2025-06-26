import React, { userState, useState } from "react";
import axiosInstance from "../utils/axiosInstance";
import { useNavigate } from "react-router-dom";

const SignupPage = () => {
    const [userId, setUserId] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirm, setPasswordConfirm] = useState("");
    const navigate = useNavigate();

    const handleSignup = async (e) => {
        e.preventDefault();
        if(password !== passwordConfirm) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }
    }

    try {
        await axiosInstance.post("/api/auth/signup", { userId, password });
        alert("회원가입 성공");
        navigate("/login");
    } catch (error) {
        alert("회원가입 실패: " + (error.response?.data || error.message));
    }
}