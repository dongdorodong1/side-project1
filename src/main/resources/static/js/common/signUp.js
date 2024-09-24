(function() {
//----------------------------
    /** signUp 객체 */
//----------------------------
    const SignUp = {
        name: 'SignUp',
        init: function() {
            this.bind();
        },
        bind: function() {
            let signUpBtn = document.getElementById('signUp_confirm');
            signUpBtn.addEventListener('click', this.fn.signUp);
        },
        fn : {
            signUp: function() {
                // 입력된 폼 데이터 가져오기
                const name = document.getElementById("signUp_name").value;
                const userId = document.getElementById("signUp_userId").value;
                const password = document.getElementById("signUp_passWord").value;
                const phoneNumber = document.getElementById("signUp_phoneNumber").value;

                // 유효성 검사
                if (!name || !userId || !password || !phoneNumber) {
                    alert("모든 필드를 입력해주세요.");
                    return;
                }

                // 서버로 보낼 데이터 객체 생성
                const formData = {
                    username: name,
                    userId: userId,
                    password: password,
                    phoneNumber: phoneNumber
                };

                fetch('/common/signUp', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(formData)
                })
                .then(response => {
                    debugger;
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error('네트워크 응답이 올바르지 않습니다.');
                    }
                })
                .then(data => {
                    console.log('서버 응답 데이터:', data);
                    alert("회원가입이 완료되었습니다!");
                    // 회원가입 완료 후 원하는 동작 추가
                    location.href='/';
                })
                .catch(error => {
                    console.error('Fetch 오류:', error);
                    alert("회원가입에 실패했습니다. 다시 시도해주세요.");
                });
            }
        },
        template: {
        }
    };
    window.signUp = SignUp;
//-------------------------
})();