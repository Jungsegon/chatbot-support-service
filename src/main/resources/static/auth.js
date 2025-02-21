$(document).ready(function () { // 페이지가 로드된 후 실행되는 코드 블록
    // ✅ 로그인 처리
    $("#loginForm").submit(function (e) {
        e.preventDefault(); // 폼의 기본 제출 동작(페이지 새로고침)을 막음.

        let loginData = {
            account: $("#loginAccount").val(),
            password: $("#loginPassword").val()
        };

        $.ajax({
            type: "POST",
            url: "/login",
            contentType: "application/json",
            data: JSON.stringify(loginData),
            success: function (response) {
                alert("로그인 성공!");
                localStorage.setItem("token", response.token); // JWT 저장
                window.location.href = "index.html"; // 로그인 후 메인 페이지로 이동
            },
            error: function () {
                alert("로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
            }
        });
    });

    // ✅ 회원가입 처리
    $("#registerForm").submit(function (e) {
        e.preventDefault();

        let registerData = {
            account: $("#registerAccount").val(),
            password: $("#registerPassword").val(),
            nickname: $("#registerNickname").val(),
            name: $("#registerName").val()
        };

        $.ajax({
            type: "POST",
            url: "/register",
            contentType: "application/json",
            data: JSON.stringify(registerData),
            success: function () {
                alert("회원가입 성공! 로그인 페이지로 이동합니다.");
                window.location.href = "login.html";
            },
            error: function () {
                alert("회원가입 실패! 다시 시도하세요.");
            }
        });
    });
});
