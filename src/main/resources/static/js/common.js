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