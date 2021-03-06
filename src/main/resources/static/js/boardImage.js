var boardImage ={

    init: function(){

    var regex = new RegExp("(.*?)/.(exe|sh|zip|alz|tiff)$");
    var maxSize = 10485760; //10MB


    // 유효성 검사
    function checkExtension(fileName, fileSize){

          if(fileSize >= maxSize){
              alert("파일 사이즈 초과");
              return false;
          }

          if(regex.test(fileName)){
              alert("해당 종류의 파일은 업로드할 수 없습니다.");
              return false;
          }
          return true;
    }



    // 이미지 선택
    $(".custom-file-input").on("change", function() {       // this = $(".custom-file-input")


//            var fileName = $(this).val().split("\\").pop();     // \\ 를 기준으로 앞,뒤로 나누고 뒤에 있는 거 꺼낸다
//            console.log(fileName);
//            $(this).siblings(".custom-file-label").addClass("selected").html(fileName);

            var formData = new FormData();              // formData 생성
            var inputFile = $(this);                    // $(".custom-file-input")

            //console.log(inputFile);

            var files = inputFile[0].files;
            console.log(files);

            var appended = false;

            for (var i = 0; i < files.length; i++) {

                 // 파일 체크
                if(!checkExtension(files[i].name, files[i].size)){
                    return false;
                }

                //console.log(files[i]);
                formData.append("uploadFiles", files[i]);
                appended = true;
            }

            //upload를 하지 않는다.
            if (!appended) {return;}


            //formData 객체를 iter 출력
            for (var value of formData.values()) {
                console.log(value);
            }

            //실제 업로드 부분     -> upload ajax
            $.ajax({
                url: '/uploadAjax',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                dataType:'json',
                success: function(result){
                    console.log(result);
                    showResult(result);
                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(textStatus);
                }
            }); //$.ajax
        }); //end change event




        // 파일 'x' 버튼 눌렀을 때
        $(".uploadResult ").on("click", "li button", function(e){

            console.log("delete file");
            console.log($(this));

            var targetFile = $(this).data("file");
            console.log(targetFile);

            var targetLi = $(this).closest("li");
            console.log(targetLi);


            $.ajax({
                url: '/removeFile',
                data: {fileName: targetFile},
                dataType:'text',
                type: 'POST',
                success: function(result){
                    alert(result);

                    targetLi.remove();
                }
            }); //$.ajax
        });


        function showResult(uploadResultArr){

            console.log(uploadResultArr);

            var tt = $(uploadResultArr)[0].data;
            console.log(tt);


            var uploadUL = $(".uploadResult");
            var str ="";

            str += "<ul>";
            $.each(tt, function(i, obj) {


                str += "<li data-name='" + obj.fileName + "' data-uuid='"+obj.uuid+"'>";
                str += " <div>";
                str += "<button type='button' data-file=\'" + obj.imageURL + "\' "
                str += "class='btn-warning btn-sm'>X</button><br>";
                str += "<img src='"+obj.imageURL + "'>";
                str += "</div>";
                str += "</li>";

            });
            str += "</ul>";

            uploadUL.append(str);
        }


    }

}

boardImage.init();











