import{o as l,c as i,a as t,F as b,r as h,t as d,i as D,j as y,b as s,n as _,k as B,e as C,l as F,f as N}from"./index.cbb1edb5.js";import{u as w}from"./board.301ca718.js";const x=c=>{if(c!=null){let u=new Date(c),e=new Date(u.getTime()),p=e.getFullYear(),m=e.getMonth()+1,a=e.getDate(),r=e.getHours(),o=e.getMinutes(),n=e.getSeconds(),g=m<10?"0"+m:m,k=a<10?"0"+a:a,f=r<10?"0"+r:r,$=o<10?"0"+o:o,v=n<10?"0"+n:n;return`${p}-${g}-${k} ${f}:${$}:${v}`}else return null},I={class:"container p-2"},T={class:"table"},V=t("thead",null,[t("tr",null,[t("th",{scope:"col"},"#"),t("th",{scope:"col"},"\uC81C\uBAA9"),t("th",{scope:"col"},"\uB4F1\uB85D\uC77C\uC2DC")])],-1),P={scope:"row"},L={"aria-label":"..."},S={class:"pagination"},M=["onClick"],j={__name:"BoardList",setup(c){const u=F(),e=w();e.resetItem(),e.getItems();const p=()=>{u.push({name:"boardwrite"})};return(m,a)=>{const r=B("router-link");return l(),i("div",I,[t("table",T,[V,t("tbody",null,[(l(!0),i(b,null,h(s(e).items.content,(o,n)=>(l(),i("tr",{key:n},[t("th",P,d(o.id),1),t("td",null,[D(r,{to:{name:"boardread",query:{id:o.id}}},{default:y(()=>[C(d(o.title),1)]),_:2},1032,["to"])]),t("td",null,d(s(x)(o.regDate)),1)]))),128))])]),t("nav",L,[t("ul",S,[t("li",{class:_(["page-item",{disabled:s(e).items.pageNo==0}])},[t("button",{class:"page-link",onClick:a[0]||(a[0]=o=>s(e).getItems(s(e).items.pageNo-1))},"Prev")],2),(l(!0),i(b,null,h(s(e).arrPage,(o,n)=>(l(),i("li",{class:"page-item",key:n},[t("button",{class:_(["page-link",{active:o==s(e).items.pageNo+1}]),onClick:g=>s(e).getItems(o-1)},d(o),11,M)]))),128)),t("li",{class:_(["page-item",{disabled:s(e).items.pageNo==s(e).items.endPage-1}])},[t("button",{class:"page-link",onClick:a[1]||(a[1]=o=>s(e).getItems(s(e).items.pageNo+1))},"Prev")],2)])]),t("button",{type:"button",class:"btn btn-primary",onClick:p},"\uAE00\uC4F0\uAE30")])}}},H={__name:"BoardListView",setup(c){return(u,e)=>(l(),N(j))}};export{H as default};
