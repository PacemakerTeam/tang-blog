let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
        // $("#btn-login").on("click", () => {
        //     this.login();
        // });
    },

    save: function () {
        // alert('user의 save함수 호출');
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };
        console.log(data);

        $.ajax({
            type: "POST",
            url : "/api/board",
            data: JSON.stringify(data), //http body데이터
            contentType:"application/json; charset=utf-8", //request body데이터가 어떤 타입인지(MIME)
            dataType:"json"
        }).done(function (resp) {
            alert("글쓰기가 완료되었습니다");
            location.href="/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

index.init();