var board={

    init: function(){

        $("#btn_save").on('click',()=>{

            var str = "";
            //var formData = new FormData($("#upload01")[0]);
            var formData = new FormData($(".board_detail")[0]);


            // var tt = $(uploadResultArr)[0].data;
            //var t22 = $(".uploadResult li img").attr('src');
            var t22 = $(".uploadResult li div img");
            console.log(t22);


            $.each(t22, function(i, obj) {

                console.log(obj);
                var t33 = obj.src;
                console.log(t33);

                formData.append("boardImage", t33);
            });


            console.log("_______________");

            for(var value of formData.values()){
                console.log(value);

            }
            this.save(formData);

        }),

        $("#btn_update").on('click',()=>{
            this.update();

        }),
        $("#btn_delete").on('click',()=>{
            this.delete();

        })


    },

    save: function(formData){

        console.log("upload 함수");
           for(var value of formData.values()){
               console.log(value);
           }

        $.ajax({
            url: '/api/v1/board',
            processData: false,
            contentType: false,
            data: formData,
            type: 'POST',
            dataType: 'json',

        }).done(function(result){

            console.log("result :" + result);
            location.href = '/board/list';

            if(result.status == 200){
                alert("글이 등록되었습니01다");

            }else{

                alert(result.data);
            }

        }).fail(function(error){

            alert(JSON.stringify(error));
            alert("글 등록 에러");
        });
    },

    update: function(){

        var id = $('#boardId').val();

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


         var boardId = $('#boardId').val();

         console.log(boardId);


         $.ajax({

            type: 'DELETE',
            url:'/api/v1/board/'+boardId,
            dataType: 'json',


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

board.init();

