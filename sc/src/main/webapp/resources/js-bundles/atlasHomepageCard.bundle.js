var atlasHomepageCard=(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[2],{17:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n,a=(n=r(0))&&n.__esModule?n:{default:n};var o={iconType:a.default.oneOf(["species","image"]).isRequired,iconSrc:a.default.string.isRequired,description:a.default.shape({text:a.default.string.isRequired,url:a.default.string}),content:a.default.arrayOf(a.default.shape({text:a.default.string.isRequired,url:a.default.string}))};t.default=o},216:function(e,t,r){"use strict";r.r(t),r.d(t,"renderSceaHomepageSpeciesContainer",function(){return s}),r.d(t,"renderHcaLandingPageContainer",function(){return l});var n=r(1),a=r.n(n),o=r(5),i=r.n(o),u=r(39),s=function(e,t){i.a.render(a.a.createElement(u.SceaHomepageSpeciesContainer,e),document.getElementById(t))},l=function(e,t){i.a.render(a.a.createElement(u.HcaLandingPageContainer,e),document.getElementById(t))}},217:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=u(r(1)),a=u(r(0)),o=u(r(4)),i=u(r(218));function u(e){return e&&e.__esModule?e:{default:e}}function s(e){return(s="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function l(e,t,r,n,a,o,i){try{var u=e[o](i),s=u.value}catch(e){return void r(e)}u.done?t(s):Promise.resolve(s).then(n,a)}function c(e){return function(){var t=this,r=arguments;return new Promise(function(n,a){var o=e.apply(t,r);function i(e){l(o,n,a,i,u,"next",e)}function u(e){l(o,n,a,i,u,"throw",e)}i(void 0)})}}function f(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}function d(e,t){return!t||"object"!==s(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function p(e){return(p=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function m(e,t){return(m=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}var y=function(e){var t=function(t){function r(e){var t;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,r),(t=d(this,p(r).call(this,e))).state={data:null,isLoading:!0,hasError:null},t}var a,u,s,l,y,h;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&m(e,t)}(r,n.default.Component),a=r,u=[{key:"componentDidUpdate",value:(h=c(regeneratorRuntime.mark(function e(t,r){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(null!==this.state.data||null!==this.state.hasError){e.next=3;break}return e.next=3,this._loadAsyncData((0,o.default)(this.props.resource,this.props.host).toString());case 3:case"end":return e.stop()}},e,this)})),function(e,t){return h.apply(this,arguments)})},{key:"componentDidMount",value:(y=c(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,this._loadAsyncData((0,o.default)(this.props.resource,this.props.host).toString());case 2:case"end":return e.stop()}},e,this)})),function(){return y.apply(this,arguments)})},{key:"_loadAsyncData",value:(l=c(regeneratorRuntime.mark(function e(t){var r;return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,fetch(t);case 3:if((r=e.sent).ok){e.next=6;break}throw new Error("".concat(t," => ").concat(r.status));case 6:return e.t0=this,e.next=9,r.json();case 9:e.t1=e.sent,e.t2={data:e.t1,isLoading:!1,hasError:null},e.t0.setState.call(e.t0,e.t2),e.next=17;break;case 14:e.prev=14,e.t3=e.catch(0),this.setState({data:null,isLoading:!1,hasError:{description:"There was a problem communicating with the server. Please try again later.",name:e.t3.name,message:e.t3.message}});case 17:case"end":return e.stop()}},e,this,[[0,14]])})),function(e){return l.apply(this,arguments)})},{key:"componentDidCatch",value:function(e,t){this.setState({hasError:{description:"There was a problem rendering this component.",name:e.name,message:"".concat(e.message," – ").concat(t)}})}},{key:"render",value:function(){var t=this.state,r=t.data,a=t.isLoading,o=t.hasError;return o?n.default.createElement(i.default,{error:o}):a?n.default.createElement("p",{className:"row column loading-message"}," Loading, please wait..."):n.default.createElement(e,{cards:r})}}],s=[{key:"getDerivedStateFromProps",value:function(e,t){var r=(0,o.default)(e.resource,e.host).toString();return r!==t.url?{data:null,loading:!0,hasError:null,url:r}:null}}],u&&f(a.prototype,u),s&&f(a,s),r}();return t.propTypes={host:a.default.string.isRequired,resource:a.default.string.isRequired},t};t.default=y},218:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=o(r(1)),a=o(r(0));function o(e){return e&&e.__esModule?e:{default:e}}var i=function(e){var t=e.error;return n.default.createElement("div",{className:"row column"},n.default.createElement("div",{className:"callout alert small"},n.default.createElement("h5",null,"Oops!"),n.default.createElement("p",null,t.description,n.default.createElement("br",null),"If the error persists, in order to help us debug the issue, please copy the URL and this message and send it to us via ",n.default.createElement("a",{href:"https://www.ebi.ac.uk/support/gxasc"},"the EBI Support & Feedback system"),":"),n.default.createElement("code",null,"".concat(t.name,": ").concat(t.message))))};i.propTypes={error:a.default.shape({description:a.default.string.isRequired,name:a.default.string.isRequired,message:a.default.string.isRequired})};var u=i;t.default=u},219:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=s(r(1)),a=s(r(0)),o=s(r(3)),i=s(r(17)),u=s(r(220));function s(e){return e&&e.__esModule?e:{default:e}}function l(){var e=function(e,t){t||(t=e.slice(0));return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}(["\n  border-radius: 8px;\n  :hover {\n    background: AliceBlue;\n  }\n"]);return l=function(){return e},e}var c=o.default.div(l()),f=function(e){var t=e.cards;return n.default.createElement("div",{className:"row small-up-2 medium-up-3"},Array.isArray(t)&&t.map(function(e,t){return n.default.createElement(c,{className:"column column-block",key:t},n.default.createElement(u.default,e))}))};f.propTypes={cards:a.default.arrayOf(a.default.shape(i.default)).isRequired};var d=f;t.default=d},220:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=u(r(1)),a=(u(r(0)),u(r(70))),o=u(r(72)),i=u(r(17));function u(e){return e&&e.__esModule?e:{default:e}}var s=function(e){var t=e.iconSrc,r=e.description,i=e.content;return n.default.createElement("div",{style:{marginBottom:0,paddingBottom:"2rem",textAlign:"center"}},r&&r.url?n.default.createElement("a",{style:{fontSize:"6rem",borderBottom:0},href:r.url},n.default.createElement(a.default,{species:t})):n.default.createElement("span",{style:{fontSize:"6rem"}},n.default.createElement(a.default,{species:t})),r&&n.default.createElement("h5",null,r.url?n.default.createElement("a",{href:r.url},r.text):r.text),Array.isArray(i)&&n.default.createElement("ul",{style:{listStyle:"none",marginLeft:0}},(0,o.default)(i)))};s.propTypes=i.default;var l=s;t.default=l},221:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=i(r(1)),a=i(r(0)),o=r(71);function i(e){return e&&e.__esModule?e:{default:e}}function u(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=[],n=!0,a=!1,o=void 0;try{for(var i,u=e[Symbol.iterator]();!(n=(i=u.next()).done)&&(r.push(i.value),!t||r.length!==t);n=!0);}catch(e){a=!0,o=e}finally{try{n||null==u.return||u.return()}finally{if(a)throw o}}return r}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}var s=function(e){var t,r=e.species,a=e.groupColors,i=e.color,s=u((0,o.lookUpIcon)(r),2),l=s[0],c=s[1];return n.default.createElement("span",{className:"icon icon-species",style:{color:i||a[l]||"black"},"data-icon":c||"❔",title:(t=r,t.charAt(0).toUpperCase()+t.slice(1).toLowerCase())})};s.propTypes={species:a.default.string.isRequired,groupColors:a.default.shape({warmBlooded:a.default.string.isRequired,plants:a.default.string.isRequired,other:a.default.string.isRequired}),color:a.default.string},s.defaultProps={species:"oryctolagus cuniculus",groupColors:{warmBlooded:"indianred",plants:"mediumseagreen",other:"deepskyblue"}};var l=s;t.default=l},222:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=u(r(1)),a=u(r(0)),o=u(r(3)),i=r(71);function u(e){return e&&e.__esModule?e:{default:e}}function s(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=[],n=!0,a=!1,o=void 0;try{for(var i,u=e[Symbol.iterator]();!(n=(i=u.next()).done)&&(r.push(i.value),!t||r.length!==t);n=!0);}catch(e){a=!0,o=e}finally{try{n||null==u.return||u.return()}finally{if(a)throw o}}return r}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}function l(){var e=f(["\n  @font-face {\n    font-family: 'EBI-Species';\n    src:url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot');\n    src:url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff2') format('woff2'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff') format('woff'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal;\n  }\n\n  ::before {\n    font-family: 'EBI-Species';\n    content: attr(data-icon);\n    text-transform: none;\n  }\n"]);return l=function(){return e},e}function c(){var e=f(["\n  text-decoration: none;\n  font-style: normal;\n  text-rendering: optimizeLegibility !important;\n  background-size: contain;\n  font-weight: 400;\n"]);return c=function(){return e},e}function f(e,t){return t||(t=e.slice(0)),Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}var d=o.default.span(c()),p=(0,o.default)(d)(l()),m=function(e){var t,r=e.species,a=e.groupColors,o=e.color,u=s((0,i.lookUpIcon)(r),2),l=u[0],c=u[1];return n.default.createElement(p,{style:{color:o||a[l]||"black"},"data-icon":c||"❔",title:(t=r,t.charAt(0).toUpperCase()+t.slice(1).toLowerCase())})};m.propTypes={species:a.default.string.isRequired,groupColors:a.default.shape({warmBlooded:a.default.string.isRequired,plants:a.default.string.isRequired,other:a.default.string.isRequired}),color:a.default.string},m.defaultProps={species:"oryctolagus cuniculus",groupColors:{warmBlooded:"indianred",plants:"mediumseagreen",other:"deepskyblue"}};var y=m;t.default=y},223:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=u(r(1)),a=u(r(0)),o=(u(r(3)),u(r(17))),i=u(r(224));function u(e){return e&&e.__esModule?e:{default:e}}var s=function(e){var t=e.cards,r=function(e,t){for(var r=[],n=[],a=0;a<e.length;a++)t-n.length==0&&(r.push(n),n=[]),n.push(e[a]);return 0!==n.length&&r.push(n),r}(t,2);return n.default.createElement("div",{className:"row"},r.map(function(e,t){return n.default.createElement("div",{className:"columns small-".concat(6*e.length," small-centered"),key:t},n.default.createElement("div",{className:"row"},e.map(function(r,a){return n.default.createElement("div",{className:"small-".concat(12/e.length," columns"),key:"".concat(t,"-").concat(a)},n.default.createElement(i.default,r))})))}))};s.propTypes={cards:a.default.arrayOf(a.default.shape(o.default)).isRequired};var l=s;t.default=l},224:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=s(r(1)),a=(s(r(0)),s(r(70))),o=s(r(72)),i=s(r(17)),u=s(r(3));function s(e){return e&&e.__esModule?e:{default:e}}function l(e){return(l="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function c(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}function f(e){return(f=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function d(e,t){return(d=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function p(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}function m(){var e=function(e,t){t||(t=e.slice(0));return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}(["\n  list-style: none;\n  width: 80%;\n  margin-left: auto;\n  margin-right: auto;\n"]);return m=function(){return e},e}var y=5,h=u.default.ul(m()),g=function(e){function t(e){var r,n,a;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),n=this,(r=!(a=f(t).call(this,e))||"object"!==l(a)&&"function"!=typeof a?p(n):a).state={isHidden:r.props.content&&r.props.content.length>=y},r.onClick=r.onClick.bind(p(p(r))),r}var r,i,u;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&d(e,t)}(t,n.default.Component),r=t,(i=[{key:"onClick",value:function(){this.setState({isHidden:!this.state.isHidden})}},{key:"render",value:function(){var e=this.props,t=e.iconType,r=e.iconSrc,i=e.description,u=e.content,s=Array.isArray(u)&&(0,o.default)(u),l="species"===t?n.default.createElement("span",{style:{fontSize:"8rem"}},n.default.createElement(a.default,{species:r})):n.default.createElement("img",{src:r,style:{height:"8rem",marginBottom:"2.35rem",marginTop:"2.50rem"}});return n.default.createElement("div",{style:{marginBottom:0,paddingBottom:"2rem",textAlign:"center"}},i&&(i.url?n.default.createElement("h4",null,n.default.createElement("a",{href:i.url},i.text)):n.default.createElement("h4",null,i.text)),i&&(i.url?n.default.createElement("a",{href:i.url,style:{borderBottom:0}},l):l),n.default.createElement(h,{className:"content"},this.state.isHidden?s.slice(0,y):s),Array.isArray(u)&&u.length>y&&n.default.createElement("button",{className:"button",onClick:this.onClick},this.state.isHidden?"Show all":"Show fewer"))}}])&&c(r.prototype,i),u&&c(r,u),t}();g.propTypes=i.default;var v=g;t.default=v},39:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.HcaLandingPageContainer=t.SceaHomepageSpeciesContainer=void 0;var n=i(r(217)),a=i(r(219)),o=i(r(223));function i(e){return e&&e.__esModule?e:{default:e}}var u=(0,n.default)(a.default);t.SceaHomepageSpeciesContainer=u;var s=(0,n.default)(o.default);t.HcaLandingPageContainer=s},70:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),Object.defineProperty(t,"default",{enumerable:!0,get:function(){return o.default}}),Object.defineProperty(t,"EbiSpeciesIconBare",{enumerable:!0,get:function(){return i.default}}),t.renderBare=t.render=void 0;var n=u(r(1)),a=u(r(5)),o=u(r(221)),i=u(r(222));function u(e){return e&&e.__esModule?e:{default:e}}t.render=function(e,t){a.default.render(n.default.createElement(o.default,e),document.getElementById(t))};t.renderBare=function(e,t){a.default.render(n.default.createElement(i.default,e),document.getElementById(t))}},71:function(e,t,r){"use strict";function n(e){return function(e){if(Array.isArray(e)){for(var t=0,r=new Array(e.length);t<e.length;t++)r[t]=e[t];return r}}(e)||function(e){if(Symbol.iterator in Object(e)||"[object Arguments]"===Object.prototype.toString.call(e))return Array.from(e)}(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance")}()}Object.defineProperty(t,"__esModule",{value:!0}),t.allSpecies=t.lookUpIcon=void 0;var a={warmBlooded:{a:["alpaca","vicugna pacos"],l:["armadillo"],"(":["bat"],A:["cat","felis catus"],k:["chicken","gallus gallus"],i:["chimpanzee","pan paniscus","pan troglodytes"],C:["cow","bos taurus"],d:["dog","canis lupus","canis lupus familiaris"],D:["dolphin"],e:["elephant","loxodonta africana","loxodonta cyclotis","elephas maximus"],"!":["ferret","mustela putorius furo"],n:["finch","pyrrhula pyrrhula"],m:["goat"],G:["gorilla","gorilla gorilla"],g:["guinea pig","cavia porcellus"],o:["hedgehog","erinaceus europaeus"],h:["horse","equus caballus"],H:["human","homo sapiens"],3:["kangaroo rat"],r:["monkey","macaca mulatta"],9:["monodelphis","monodelphis domestica"],M:["mouse","mus musculus"],N:["mouse lemur"],"*":["orangutan","pongo abelii","pongo pygmaeus"],8:["papio anubis"],p:["pig","sus scrofa"],U:["platypus","ornithorhynchus anatinus"],t:["rabbit","oryctolagus cuniculus"],R:["rat","rattus norvegicus"],x:["sheep","ovis aries"],Q:["shrew"],I:["squirrel"],w:["wallaby"]},plants:{5:["barley","hordeum vulgare","hordeum vulgare subsp. vulgare"],B:["brassica","brassica oleracea","brassica rapa","arabidopsis","arabidopsis thaliana","arabidopsis lyrata"],"%":["brachypodium","brachypodium distachyon"],c:["corn","zea mays"],"^":["glycinemax","glycine max"],O:["grapes","vitis vinifera"],P:["plant","physcomitrella patens","sorghum bicolor","triticum aestivum"],6:["rice","oryza sativa","oryza sativa japonica group"],")":["tomatoes","solanum lycopersicum","solanum tuberosum"]},other:{0:["amoeba"],7:["anolis","anolis carolinensis"],"£":["aspergillus","aspergillus fumigatus"],$:["bee"],b:["bug"],W:["c elegans","caenorhabditis elegans","schistosoma mansoni"],2:["diatom"],L:["ecoli","escherichia coli"],F:["fly","drosophila melanogaster"],f:["frog","xenopus (silurana) tropicalis","xenopus tropicalis"],u:["fungus"],4:["louse"],1:["mosquito"],"@":["plasmodium"],E:["pufferfish","tetraodon nigroviridis"],"+":["ray"],s:["scorpion"],"'":["snail"],S:["spider"],"&":["tick"],v:["virus"],Y:["yeast","saccharomyces cerevisiae","schizosaccharomyces pombe"],Z:["zebrafish","danio rerio"]}},o=function(e,t){return Object.keys(a[e]).find(function(r){return a[e][r].includes(t.toLowerCase())})};t.lookUpIcon=function(e){for(var t in a){var r=o(t,e);if(r)return[t,r]}return["",""]};var i=[];for(var u in t.allSpecies=i,a)for(var s in a[u])i.push.apply(i,n(a[u][s]))},72:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n,a=(n=r(1))&&n.__esModule?n:{default:n};var o=function(e){return e.map(function(e,t){var r="".concat(e.text,"-").concat(t);return e.url?a.default.createElement("li",{style:{marginBottom:"0.3rem"},key:r},a.default.createElement("a",{href:e.url},e.text)):a.default.createElement("li",{style:{marginBottom:"0.3rem"},key:r},e.text)})};t.default=o}},[[216,0]]]);