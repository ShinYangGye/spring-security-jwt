import{_,u as p,o as r,c as u,a as e,w as c,v as d,b as i,p as m,d as b,e as f,f as v}from"./index.b9a70dc3.js";const s=n=>(m("data-v-1fb906a1"),n=n(),b(),n),g={class:"form-signin w-100 m-auto"},h=s(()=>e("h1",{class:"h3 mb-3 fw-normal"},"Log in",-1)),w={class:"form-floating pb-3"},x=s(()=>e("div",{class:"invalid-feedback"},null,-1)),k=s(()=>e("label",{for:"username"},"\uC544\uC774\uB514",-1)),y={class:"form-floating pb-3"},S=s(()=>e("div",{class:"invalid-feedback"},null,-1)),L=s(()=>e("label",{for:"password"},"\uBE44\uBC00\uBC88\uD638",-1)),V=s(()=>e("div",{class:"checkbox mb-3"},[e("label",null,[e("input",{type:"checkbox",value:"remember-me"}),f(" Remember me ")])],-1)),j=s(()=>e("p",{class:"mt-5 mb-3 text-muted"},"\xA9 2017\u20132022",-1)),B={__name:"Login",setup(n){const o=p(),l=()=>{o.login()};return(O,t)=>(r(),u("main",g,[h,e("div",w,[c(e("input",{type:"text",class:"form-control is-valid",id:"username","onUpdate:modelValue":t[0]||(t[0]=a=>i(o).loginObj.username=a)},null,512),[[d,i(o).loginObj.username]]),x,k]),e("div",y,[c(e("input",{type:"password",class:"form-control is-valid",id:"password","onUpdate:modelValue":t[1]||(t[1]=a=>i(o).loginObj.password=a)},null,512),[[d,i(o).loginObj.password]]),S,L]),V,e("button",{class:"w-100 btn btn-lg btn-primary",type:"button",onClick:t[2]||(t[2]=a=>l())},"Sign in"),j]))}},I=_(B,[["__scopeId","data-v-1fb906a1"]]),T={__name:"LoginView",setup(n){return(o,l)=>(r(),v(I))}};export{T as default};