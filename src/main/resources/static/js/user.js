let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
    },

    save: function () {
        // alert('user의 save함수 호출');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email   : $("#email").val(),
        };
        // console.log(data);

        // ajax호출 시 default가 비동기 호출,
        // ajax통신을 활용해서 3개의 데이터를 json으로 변경하여 insert요청!
        $.ajax({
            //회원가입 수행 요청
            type: "POST",
            url : "/blog/api/user",
            data: JSON.stringify(data), //http body데이터
            contentType:"application/json; charset=utf-8", //request body데이터가 어떤 타입인지(MIME)
            dataType:"json" // 응답? json으로 받을게. 기본적으로 문자열( 생긴 게 json이라면 => javascript오브젝트로 변경 )
        }).done(function (resp) {
            alert("회원가입이 완료되었습니다");
            location.href="/blog";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!
    },

    login: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
        };

        $.ajax({
            type: "POST",
            url : "/blog/api/user/login",
            data: JSON.stringify(data),
            contentType:"application/json; charset=utf-8",
            dataType:"json"
        }).done(function (resp) {
            alert("로그인이 완료되었습니다");
            location.href="/blog";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!
    }
}

index.init();