// common javascript
// navBar click event
$(function (){
    let navBox = $(".navbar");
    $(".btn_navbar").on('click',function (){
        if (navBox.hasClass("close")){
            navBox.removeClass("close");
        }else {
            navBox.addClass("close");
        }
    });
})


// 링크 공유
function sharePopupShow(){
    $("#popupShare").modal("show");
}

var url_default_ks = "https://story.kakao.com/share?url=";
var url_default_fb = "https://www.facebook.com/sharer/sharer.php?u=";
var url_default_tw_txt ="https://twitter.com/intent/tweet?text=[TEXT]&amp;url=[PAGE_URL]";
var url_default_tw_url = "&url=";
var url_default_band = "http://band.us/plugin/share?body=";
var url_route_band = "&route=";
var url_default_naver = "http://share.naver.com/web/shareView.nhn?url=";
var title_default_naver = "&title=";
var url_this_page = location.href;
var title_this_page = document.title;
var url_combine_ks = url_default_ks + url_this_page;
var url_combine_fb = url_default_fb + url_this_page;
var url_combine_tw = url_default_tw_txt; //트위터
var url_combine_band = url_default_band + encodeURI(url_this_page)+ '%0A' + encodeURI(title_this_page)+'%0A' + '&route=tistory.com';
var url_combine_naver = url_default_naver + encodeURI(url_this_page) + title_default_naver + encodeURI(title_this_page);

//현제링크복사

$(function(){

    $(document).ready(function(){

        $('#myInput').attr('value',url_this_page);

    });

});

function copy_to_clipboard() {

    var copyText = document.getElementById("myInput");

    copyText.select();

    document.execCommand("Copy");

    alert("링크가 복사되었습니다.");

}



//인쇄

var initBody;

function beforePrint()

{

    initBody = document.body.innerHTML;

    document.body.innerHTML = div_page.innerHTML;

}

function afterPrint()

{

    document.body.innerHTML = initBody;

}

function pageprint()

{

    window.onbeforeprint = beforePrint;

    window.onafterprint = afterPrint;

    window.print();

}