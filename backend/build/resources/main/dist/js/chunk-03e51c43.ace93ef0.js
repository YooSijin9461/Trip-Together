(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-03e51c43"],{7581:function(t,e,c){"use strict";c("91d7")},"91d7":function(t,e,c){},e2f5:function(t,e,c){"use strict";c.r(e);c("fb6a"),c("bf19");var n=c("7a23"),i={class:"container"},a=Object(n["p"])("h1",{class:"mt-5 mb-4"},"공지사항",-1),o=Object(n["p"])("hr",{class:"notice-line my-0"},null,-1),s={class:"ms-3 mb-0 title col-10"},l={class:"col"},b={key:0},u={class:"mb-0 date"},p=Object(n["p"])("i",{class:"far fa-clock date me-2"},null,-1),r={key:1},O={class:"mb-0 date"},j=Object(n["p"])("i",{class:"far fa-clock date me-2"},null,-1),d=Object(n["p"])("hr",{class:"notice-line my-0"},null,-1),f={class:"pagination"};function k(t,e,c,k,m,h){var g=Object(n["N"])("el-pagination");return Object(n["G"])(),Object(n["k"])(n["b"],null,[Object(n["p"])("div",i,[a,(Object(n["G"])(!0),Object(n["k"])(n["b"],null,Object(n["L"])(k.state.noticeList,(function(t){return Object(n["G"])(),Object(n["k"])("span",{key:t},[o,Object(n["p"])("div",{class:"notice-box d-flex",onClick:function(e){return k.clickNotice(t.noticeNo)}},[Object(n["p"])("p",s,Object(n["R"])(t.noticeTitle),1),Object(n["p"])("div",l,[t.noticeTime.slice(0,10)===k.state.today.toJSON().slice(0,10)?(Object(n["G"])(),Object(n["k"])("span",b,[Object(n["p"])("p",u,[p,Object(n["o"])(Object(n["R"])(k.UTCtoKST(t.noticeTime)),1)])])):(Object(n["G"])(),Object(n["k"])("span",r,[Object(n["p"])("p",O,[j,Object(n["o"])(Object(n["R"])(t.noticeTime.slice(0,10)),1)])]))])],8,["onClick"])])})),128)),d]),Object(n["p"])("div",f,[Object(n["p"])(g,{"page-size":10,layout:"prev, pager, next",total:k.state.noticeCount,onCurrentChange:k.pageChange},null,8,["total","onCurrentChange"])])],64)}var m=c("5502"),h=c("6c02"),g={setup:function(){var t=Object(m["b"])(),e=Object(h["c"])(),c=Object(n["I"])({noticeList:[],today:new Date,noticeCount:0}),i=function(c){t.dispatch("noticeDetail",c).then((function(){e.push({name:"Notice",params:{noticeId:c}})}))},a=function(t){return new Date(t).getHours()+":"+new Date(t).getMinutes()};Object(n["D"])((function(){t.dispatch("noticePageList").then((function(t){var e=t.data;c.noticeList=e.content,c.noticeCount=e.totalElements}))}));var o=function(e){t.dispatch("noticePageList",e).then((function(t){var e=t.data;c.noticeList=e.content,c.noticeCount=e.totalElements}))};return{state:c,clickNotice:i,UTCtoKST:a,pageChange:o}}};c("7581");g.render=k;e["default"]=g}}]);
//# sourceMappingURL=chunk-03e51c43.ace93ef0.js.map