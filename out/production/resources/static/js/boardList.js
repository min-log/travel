

    function btnLike(e){ // 좋아요 버튼 클릭
        //console.log(e);
        let box = ".col_"+e;
        let con='';

        $.ajax({
            url:'/boardApi/likeSave?no=' + e,
            type: 'get',
            success:function (data){
                console.log(data);
                if (data == 1){
                    con = '<div class="text-center">게시글이 찜리스트에 저장 되었습니다.</div>';
                    con += '<div><a class="btn btn-primary w-100 btn-sm mt-3" href="/mypage/boardLikeList" >찜리스트 바로가기</a></div>';
                    $("#modalMsg").modal("show");
                    $("#modalMsg .modal-message").html(con);
                    $(box).find(".btn_heart .bi").removeClass("bi-heart");
                    $(box).find(".btn_heart .bi").addClass("bi-heart-fill");
                }else if (data == 0){
                    con = '<div class="text-center">게시글 저장을 삭제 했습니다.</div>';
                    $("#modalMsg").modal("show");
                    $("#modalMsg .modal-message").html(con);

                    $(box).find(".btn_heart .bi").removeClass("bi-heart-fill");
                    $(box).find(".btn_heart .bi").addClass("bi-heart");

                }else {
                    con = '<div class="text-center"><p class="point-color tit mb-2">Travel Road</p>로그인이 필요한 기능입니다. </div>';
                    con += '<div><a class="btn btn-primary w-100 btn-sm mt-5" href="/member/loginForm" >로그인하기</a></div>';
                    $('#modalMsg').modal("show");
                    $('#modalMsg .modal-message').html(con);

                    // alert("로그인이 필요한 기능입니다.");
                }
            },
            error:function (){
                alert("저장이 실패했습니다. 관리자에게 문의해주세요.")
            }

        })
    }
