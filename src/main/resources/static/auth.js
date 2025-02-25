$(document).ready(function () {
    $("#loginForm").submit(function (e) {
        e.preventDefault();

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
                localStorage.setItem("username", response.account); // 사용자 계정 저장

                // ✅ account 값이 "admin"이면 관리자 페이지 이동
                if (response.account === "admin") {
                    window.location.href = "admin.html";
                } else {
                    window.location.href = "chat.html";
                }
            },
            error: function () {
                alert("로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
            }
        });
    });
});
