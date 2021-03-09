var main={

    init: function(){

        $("#btn_save").on('click',()=>{
            this.save();
        }),

        $("#btn_update").on('click',()=>{
            this.update();

        }),
        $("#btn_delete").on('click',()=>{
            this.delete();

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
    },

    update: function(){

        var id = $('#id').val();

        var data = {

            title : $('#title').val(),
            content : $('#content').val()

        };

        console.log(data);
        console.log(id);

        $.ajax({

            type:'PUT',
            url:'/api/v1/board/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)

        }).done(function(AA){

            alert("글이 수정되었습니다.");
            alert(AA);
            location.href="/board/list";
        }).fail(function(error){

            alert(JSON.stringify(error))
            alert("글 수정 에러")

        });

    },
    delete: function(){


         var id = $('#id').val()

         alert(id);

         $.ajax({

            type: 'DELETE',
            url:'/api/v1/board/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'

         }).done(function(aa){

            alert('글이 삭제되었습니다');
            alert(aa);
            location.href ='/board/list';

         }).fail(function(error){

            alert(JSON.stringify(error));
            alert('글 삭제 에러');

         })

    }


}

main.init();
