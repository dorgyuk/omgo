export interface SignupRequest{
    email: string;
    password: string;
    nickname: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export async function signup(data: SignupRequest) {
    const response = await fetch("/api/auth/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error("회원가입에 실패했습니다.");
    }

    return response;
}

export async function login(data: LoginRequest) {
    const response = await fetch("/api/auth/login")
}