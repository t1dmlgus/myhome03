var main ={

    init: function(){

         $('#btn_join').on('click',()=>{
            this.join();
         })
//         $('#btn_login').on('click',()=>{
//            this.login();
//         })

    },

    join: function(){

        alert("회원가입");

        var data = {

            username : $('#username').val(),
            password : $('#password').val(),
            email : $('#email').val(),
            age : $('#age').val()

        };

        console.log(data);

        $.ajax({
            type: "POST",
            url: "/auth/user/join",
            dataType:"json",
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify(data)

        }).done(function(vvv){

            alert('회원가입되었습니다!!')
            alert(vvv);
            location.href="/board/list"

        }).fail(function(error){

            alert(JSON.stringify(error));
            alert('회원등록 에러');


        });


    },

    login: function(){

        alert("로그인");

        var data ={

            username : $('#username').val(),
            password : $('#password').val()
        };

        console.log(data);
        console.log(JSON.stringify(data));
        $.ajax({

            type: 'POST',
            url: '/login',
            dataType: 'json',                       // 서버에서 반환되는 데이터 형식을 지정
            contentType: 'application/x-www-form-urlencoded; charset=utf-8',    // 서버에 데이터를 보낼 때 사용 형식
            data: data                              // 서버에 보낼 데이터

        }).done(function(v2){

            alert(v2);
            alert('로그인되었습니다ㅍㅍ');
            location.href='/';



        }).fail(function(error){

            alert(JSON.stringify(error));
            alert('로그인 에러');

        });


    }


}

main.init();

