gsap.registerPlugin(ScrollTrigger);
let pc = gsap.matchMedia();
pc.add("(min-width: 1000px)", () => {
    let topBanner = gsap.timeline({
        scrollTrigger: {
            trigger: "#mainBanner",
            start: "0% 30%",//시작 지점
            end: "100% 100%",//끝 지점
            scrub: 1.5,//부드러운 스크러빙
            //markers: true,//개발가이드선
        }
    });

    topBanner.to(".cloud_2",  { y: 200,x:120,scale:1.2}, 0)
        .to(".cloud_1",  { y: 10,x:120,scale:1}, 0)
        .to(".bg",  {y: 120,scale:1.5}, 0)
        .to(".bg_4",  { y: -20,x:-200,scale:1.6}, 0)
        .to(".bg_3",  { y: 100,x:-200,scale:1.5}, 0)
        .from(".txt",  {scale:0.4}, 0)
        .from(".txt .t_2",  { y: 200,opacity:-3}, 0);




    let section1 = gsap.timeline({
        scrollTrigger: {
            trigger: ".sec_board_keyword",
            start: "0% 50%",//시작 지점
            end: "top 100px",//끝 지점
            scrub: 1.5,//부드러운 스크러빙
            //markers: true,//개발가이드선
        }
    });
    section1.from(".sec_board_keyword .tit",{y:100,opacity: 0},0)
        .from(".sec_board_keyword .subTitle",{y:100,opacity: 0},0)
        .from(".keywordWrap",{opacity: 0,width: 50},0)
        .from(".keywordWrap a",{opacity: 0},0)

    let section2 = gsap.timeline({
        scrollTrigger: {
            trigger: ".sec_board_img",
            start: "0% 50%",//시작 지점
            end: "top 100px",//끝 지점
            scrub: 1.5,//부드러운 스크러빙
            //markers: true,//개발가이드선
        }
    });
    section2.from(".sec_board_img .tit",{y:100,opacity: 0},0)
        .from(".sec_board_img .subTitle",{y:100,opacity: 0},0)
        .from(".board_wrap .board_content_img_wrap",{x:-100,opacity: 0},0)
        .from(".board_wrap .board_ranking",{y:100,opacity: 0},0)



    let section3 = gsap.timeline({
        scrollTrigger: {
            trigger: ".sec_board_new",
            start: "0% 50%",//시작 지점
            end: "top 100px",//끝 지점
            scrub: 1.5,//부드러운 스크러빙
            //markers: true,//개발가이드선
        }
    });
    section3.from(".sec_board_new .tit",{y:100,opacity: 0},0)
        .from(".sec_board_new .subTitle",{y:100,opacity: 0},0)
        .from(".board_content",{y:100,opacity: 0},0)



});
