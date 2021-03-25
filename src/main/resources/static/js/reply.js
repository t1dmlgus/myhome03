var reply ={


    init: function(){


         $("#reply_save").on('click',()=>{

                console.log(this);
                this.replySave();


         })



    },

    replySave: function(){

            var data = {

                boardId : $('#boardId').val(),
                content : $('#reply_content').val()
            };

           console.log(data);

           $.ajax({

              type: 'POST',
              url:'/api/v1/reply',
              dataType: 'json',
              contentType: 'application/json; charset=utf-8',
              data: JSON.stringify(data)


           }).done(function(aa){

              alert('댓글이 등록되었습니다');

              location.href ='/board/detail?id='+data.boardId;

           }).fail(function(error){

              alert(JSON.stringify(error));
              alert('댓글 등록 에러');

           })

    },
    replyDelete: function(vv){

            var id = $('#boardId').val();

            var data = {
                replyId : vv

            }

            console.log(data.replyId);
            console.log("gdgdg");
            console.log(vv);


            $.ajax({

               type: 'DELETE',
               url:'/api/v1/reply/'+vv,
               dataType: 'json',
               contentType: 'application/json; charset=utf-8',
               data: JSON.stringify(data)                              // 서버에 보낼 데이터

            }).done(function(aa){

               alert('댓글이 삭제되었습니다');

               location.href ='/board/detail?id='+id;

            }).fail(function(error){

               alert(JSON.stringify(error));
               alert('댓글 삭제 에러');

            })

    }

}

reply.init();