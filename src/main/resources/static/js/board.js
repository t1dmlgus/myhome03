var main={

    init: function(){

        $("#btn_save").on('click',()=>{

            this.save();

        })

    },

    save: function(){

        var data ={

            title : $('#title').val(),
            content : $('#content').val()

        };

        console.log(data);

        $.ajax({

            type: 'POST',
            url: '/api/v1/board',
            dataType: 'json',
            contentType: 'application/json; charset = utf-8',
            data: JSON.stringify(data)

        }).done(function(vv){

            alert("글이 등록되었습니01다");
            alert(vv);
            location.href = '/board/list';


        }).fail(function(error){

            alert(JSON.stringify(error));
            alert("글 등록 에러");
        });
    }







}

main.init();
