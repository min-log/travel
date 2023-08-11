$(document).ready(function (){
    let today = new Date();
    console.log("today : "+today);

    $(".board_content li").each(function (index, item) {
       let startDay = $(this).find(".travel_day_start").text();
       let startEnd = $(this).find(".travel_day_end").text();
       console.log("startDay : "+ startDay);
        startDay = new Date(startDay);
        startEnd = new Date(startEnd);
        var total = startEnd - startDay; // 총 일수 : 종료 날짜 - 시작 날짜
        var perc = today - startDay; // 지난 날수 : 오늘 날짜 - 시작 날짜



        const totalDay = Math.floor(total / (1000*60*60*24));
        // const totalHour = Math.floor((total / (1000*60*60)) % 24);
        // const totalMin = Math.floor((total / (1000*60)) % 60);
        // const totalSec = Math.floor(total / 1000 % 60);
        //console.log(  index+" 일정 수 - "+ totalDay+"일 "+ totalHour+"시간 " +totalMin +"분 " +totalSec+"초");
        const diffDay = Math.floor(perc / (1000*60*60*24));
        // const diffHour = Math.floor((perc / (1000*60*60)) % 24);
        // const diffMin = Math.floor((perc / (1000*60)) % 60);
        // const diffSec = Math.floor(perc / 1000 % 60);

        let listD = totalDay - (totalDay-diffDay); // 추가된 날짜
        let DP = (listD * 100) / totalDay;
        //console.log( index+" 지난날짜 - "+ diffDay+"일 "+ diffHour+"시간 " +diffMin +"분 " +diffSec+"초");
        console.log("totalDay : " + totalDay)
        console.log("listD 남은 날짜 : " + listD)
        console.log("listD * 100 / totalDay : " + DP )

        if (totalDay == startEnd){
            $(".progress-bar").css({"width": '100%'});
        } else {
            $(".progress-bar").css({"width": DP});
        }


    });

});