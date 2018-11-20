var atlasHomepageCard=(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[2],{17:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=function(e){return e&&e.__esModule?e:{default:e}}(r(0));var a={iconType:n.default.oneOf(["species","image"]).isRequired,iconSrc:n.default.string.isRequired,description:n.default.shape({text:n.default.string.isRequired,url:n.default.string}),content:n.default.arrayOf(n.default.shape({text:n.default.string.isRequired,url:n.default.string}))};t.default=a},178:function(e,t,r){"use strict";r.r(t),r.d(t,"renderSceaHomepageSpeciesContainer",function(){return l}),r.d(t,"renderHcaLandingPageContainer",function(){return s});var n=r(1),a=r.n(n),o=r(4),i=r.n(o),u=r(38),l=function(e,t){i.a.render(a.a.createElement(u.SceaHomepageSpeciesContainer,e),document.getElementById(t))},s=function(e,t){i.a.render(a.a.createElement(u.HcaLandingPageContainer,e),document.getElementById(t))}},179:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=u(r(1)),a=u(r(0)),o=u(r(5)),i=u(r(180));function u(e){return e&&e.__esModule?e:{default:e}}function l(e){return(l="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function s(e,t,r,n,a,o,i){try{var u=e[o](i),l=u.value}catch(e){return void r(e)}u.done?t(l):Promise.resolve(l).then(n,a)}function c(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}function f(e,t){return!t||"object"!==l(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function d(e){return(d=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function p(e,t){return(p=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}var m=function(e){var t=function(t){function r(e){var t;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,r),(t=f(this,d(r).call(this,e))).state={data:null,isLoading:!0,hasError:null},t}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&p(e,t)}(r,n.default.Component),function(e,t,r){t&&c(e.prototype,t),r&&c(e,r)}(r,[{key:"componentDidMount",value:function(){var e=function(e){return function(){var t=this,r=arguments;return new Promise(function(n,a){var o=e.apply(t,r);function i(e){s(o,n,a,i,u,"next",e)}function u(e){s(o,n,a,i,u,"throw",e)}i(void 0)})}}(regeneratorRuntime.mark(function e(){var t,r;return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return this.setState({isLoading:!0}),t=(0,o.default)(this.props.resource,this.props.host).toString(),e.prev=2,e.next=5,fetch(t);case 5:if((r=e.sent).ok){e.next=8;break}throw new Error("".concat(t," => ").concat(r.status));case 8:return e.t0=this,e.next=11,r.json();case 11:e.t1=e.sent,e.t2={data:e.t1,isLoading:!1,hasError:null},e.t0.setState.call(e.t0,e.t2),e.next=19;break;case 16:e.prev=16,e.t3=e.catch(2),this.setState({data:null,isLoading:!1,hasError:{description:"There was a problem communicating with the server. Please try again later.",name:e.t3.name,message:e.t3.message}});case 19:case"end":return e.stop()}},e,this,[[2,16]])}));return function(){return e.apply(this,arguments)}}()},{key:"componentDidCatch",value:function(e,t){this.setState({hasError:{description:"There was a problem rendering this component.",name:e.name,message:"".concat(e.message," – ").concat(t)}})}},{key:"render",value:function(){var t=this.state,r=t.data,a=t.isLoading,o=t.hasError;return o?n.default.createElement(i.default,{error:o}):a?n.default.createElement("p",{className:"row column",id:"loading-message"}," Loading, please wait..."):n.default.createElement(e,{cards:r})}}]),r}();return t.propTypes={host:a.default.string.isRequired,resource:a.default.string.isRequired},t};t.default=m},180:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=o(r(1)),a=o(r(0));function o(e){return e&&e.__esModule?e:{default:e}}var i=function(e){var t=e.error;return n.default.createElement("div",{className:"row column"},n.default.createElement("div",{className:"callout alert small"},n.default.createElement("h5",null,"Oops!"),n.default.createElement("p",null,t.description,n.default.createElement("br",null),"If the error persists, in order to help us debug the issue, please copy the URL and this message and send it to us via ",n.default.createElement("a",{href:"https://www.ebi.ac.uk/support/gxasc"},"the EBI Support & Feedback system"),":"),n.default.createElement("code",null,"".concat(t.name,": ").concat(t.message))))};i.propTypes={error:a.default.shape({description:a.default.string.isRequired,name:a.default.string.isRequired,message:a.default.string.isRequired})};var u=i;t.default=u},181:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=l(r(1)),a=l(r(0)),o=l(r(7)),i=l(r(17)),u=l(r(182));function l(e){return e&&e.__esModule?e:{default:e}}function s(){var e=function(e,t){t||(t=e.slice(0));return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}(["\n  border-radius: 8px;\n  :hover {\n    background: AliceBlue;\n  }\n"]);return s=function(){return e},e}var c=o.default.div(s()),f=function(e){var t=e.cards;return n.default.createElement("div",{className:"row small-up-2 medium-up-3"},Array.isArray(t)&&t.map(function(e,t){return n.default.createElement(c,{className:"column column-block",key:t},n.default.createElement(u.default,e))}))};f.propTypes={cards:a.default.arrayOf(a.default.shape(i.default)).isRequired};var d=f;t.default=d},182:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=u(r(1)),a=(u(r(0)),u(r(66))),o=u(r(68)),i=u(r(17));function u(e){return e&&e.__esModule?e:{default:e}}var l=function(e){var t=e.iconSrc,r=e.description,i=e.content;return n.default.createElement("div",{style:{marginBottom:0,paddingBottom:"2rem",textAlign:"center"}},r&&r.url?n.default.createElement("a",{style:{fontSize:"6rem",borderBottom:0},href:r.url},n.default.createElement(a.default,{species:t})):n.default.createElement("span",{style:{fontSize:"6rem"}},n.default.createElement(a.default,{species:t})),r&&n.default.createElement("h5",null,r.url?n.default.createElement("a",{href:r.url},r.text):r.text),Array.isArray(i)&&n.default.createElement("ul",{style:{listStyle:"none"}},(0,o.default)(i)))};l.propTypes=i.default;var s=l;t.default=s},183:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=i(r(1)),a=i(r(0)),o=r(67);function i(e){return e&&e.__esModule?e:{default:e}}function u(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=[],n=!0,a=!1,o=void 0;try{for(var i,u=e[Symbol.iterator]();!(n=(i=u.next()).done)&&(r.push(i.value),!t||r.length!==t);n=!0);}catch(e){a=!0,o=e}finally{try{n||null==u.return||u.return()}finally{if(a)throw o}}return r}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}var l=function(e){var t=e.species,r=e.groupColors,a=e.color,i=u((0,o.lookUpIcon)(t),2),l=i[0],s=i[1];return n.default.createElement("span",{className:"icon icon-species",style:{color:a||r[l]||"black"},"data-icon":s||"❔",title:function(e){return e.charAt(0).toUpperCase()+e.slice(1).toLowerCase()}(t)})};l.propTypes={species:a.default.string.isRequired,groupColors:a.default.shape({warmBlooded:a.default.string.isRequired,plants:a.default.string.isRequired,other:a.default.string.isRequired}),color:a.default.string},l.defaultProps={species:"oryctolagus cuniculus",groupColors:{warmBlooded:"indianred",plants:"mediumseagreen",other:"deepskyblue"}};var s=l;t.default=s},184:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=u(r(1)),a=u(r(0)),o=u(r(7)),i=r(67);function u(e){return e&&e.__esModule?e:{default:e}}function l(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=[],n=!0,a=!1,o=void 0;try{for(var i,u=e[Symbol.iterator]();!(n=(i=u.next()).done)&&(r.push(i.value),!t||r.length!==t);n=!0);}catch(e){a=!0,o=e}finally{try{n||null==u.return||u.return()}finally{if(a)throw o}}return r}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}function s(){var e=f(["\n  @font-face {\n    font-family: 'EBI-Species';\n    src:url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot');\n    src:url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff2') format('woff2'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff') format('woff'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal;\n  }\n\n  ::before {\n    font-family: 'EBI-Species';\n    content: attr(data-icon);\n    text-transform: none;\n  }\n"]);return s=function(){return e},e}function c(){var e=f(["\n  text-decoration: none;\n  font-style: normal;\n  text-rendering: optimizeLegibility !important;\n  background-size: contain;\n  font-weight: 400;\n"]);return c=function(){return e},e}function f(e,t){return t||(t=e.slice(0)),Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}var d=o.default.span(c()),p=(0,o.default)(d)(s()),m=function(e){var t=e.species,r=e.groupColors,a=e.color,o=l((0,i.lookUpIcon)(t),2),u=o[0],s=o[1];return n.default.createElement(p,{style:{color:a||r[u]||"black"},"data-icon":s||"❔",title:function(e){return e.charAt(0).toUpperCase()+e.slice(1).toLowerCase()}(t)})};m.propTypes={species:a.default.string.isRequired,groupColors:a.default.shape({warmBlooded:a.default.string.isRequired,plants:a.default.string.isRequired,other:a.default.string.isRequired}),color:a.default.string},m.defaultProps={species:"oryctolagus cuniculus",groupColors:{warmBlooded:"indianred",plants:"mediumseagreen",other:"deepskyblue"}};var y=m;t.default=y},185:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=l(r(1)),a=l(r(0)),o=l(r(7)),i=l(r(17)),u=l(r(186));function l(e){return e&&e.__esModule?e:{default:e}}function s(){var e=function(e,t){t||(t=e.slice(0));return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}(["\n  border-radius: 8px;\n  :hover {\n    background: AliceBlue;\n  }\n"]);return s=function(){return e},e}var c=o.default.div(s()),f=function(e){var t=function(e,t){for(var r=[],n=[],a=0;a<e.length;a++)t-n.length==0&&(r.push(n),n=[]),n.push(e[a]);return 0!==n.length&&r.push(n),r}(e.cards,3);return n.default.createElement("div",{className:"row"},t.map(function(e,t){return n.default.createElement("div",{className:"columns small-".concat(4*e.length," small-centered"),key:t},n.default.createElement("div",{className:"row"},e.map(function(r,a){return n.default.createElement(c,{className:"small-".concat(12/e.length," columns"),key:"".concat(t,"-").concat(a)},n.default.createElement(u.default,r))})))}))};f.propTypes={cards:a.default.arrayOf(a.default.shape(i.default)).isRequired};var d=f;t.default=d},186:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=u(r(1)),a=(u(r(0)),u(r(66))),o=u(r(68)),i=u(r(17));function u(e){return e&&e.__esModule?e:{default:e}}function l(e){return(l="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function s(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}function c(e){return(c=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function f(e,t){return(f=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function d(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}var p=5,m=function(e){function t(e){var r;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),(r=function(e,t){return!t||"object"!==l(t)&&"function"!=typeof t?d(e):t}(this,c(t).call(this,e))).state={isHidden:r.props.content&&r.props.content.length>=p},r.onClick=r.onClick.bind(d(d(r))),r}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&f(e,t)}(t,n.default.Component),function(e,t,r){t&&s(e.prototype,t),r&&s(e,r)}(t,[{key:"onClick",value:function(){this.setState({isHidden:!this.state.isHidden})}},{key:"render",value:function(){var e=this.props,t=e.iconType,r=e.iconSrc,i=e.description,u=e.content,l=Array.isArray(u)&&(0,o.default)(u),s="species"===t?n.default.createElement("span",{style:{fontSize:"8rem"}},n.default.createElement(a.default,{species:r})):n.default.createElement("img",{src:r,style:{height:"8rem",marginBottom:"2.35rem",marginTop:"2.50rem"}});return n.default.createElement("div",{style:{marginBottom:0,paddingBottom:"2rem",textAlign:"center"}},i&&(i.url?n.default.createElement("h4",null,n.default.createElement("a",{href:i.url},i.text)):n.default.createElement("h4",null,i.text)),i&&(i.url?n.default.createElement("a",{href:i.url,style:{borderBottom:0}},s):s),n.default.createElement("ul",{className:"content",style:{listStyle:"none"}},this.state.isHidden?l.slice(0,p):l),Array.isArray(u)&&u.length>p&&n.default.createElement("button",{className:"button",onClick:this.onClick},this.state.isHidden?"Show all":"Show fewer"))}}]),t}();m.propTypes=i.default;var y=m;t.default=y},38:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.HcaLandingPageContainer=t.SceaHomepageSpeciesContainer=void 0;var n=i(r(179)),a=i(r(181)),o=i(r(185));function i(e){return e&&e.__esModule?e:{default:e}}var u=(0,n.default)(a.default);t.SceaHomepageSpeciesContainer=u;var l=(0,n.default)(o.default);t.HcaLandingPageContainer=l},66:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),Object.defineProperty(t,"default",{enumerable:!0,get:function(){return o.default}}),Object.defineProperty(t,"EbiSpeciesIconBare",{enumerable:!0,get:function(){return i.default}}),t.renderBare=t.render=void 0;var n=u(r(1)),a=u(r(4)),o=u(r(183)),i=u(r(184));function u(e){return e&&e.__esModule?e:{default:e}}t.render=function(e,t){a.default.render(n.default.createElement(o.default,e),document.getElementById(t))};t.renderBare=function(e,t){a.default.render(n.default.createElement(i.default,e),document.getElementById(t))}},67:function(e,t,r){"use strict";function n(e){return function(e){if(Array.isArray(e)){for(var t=0,r=new Array(e.length);t<e.length;t++)r[t]=e[t];return r}}(e)||function(e){if(Symbol.iterator in Object(e)||"[object Arguments]"===Object.prototype.toString.call(e))return Array.from(e)}(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance")}()}Object.defineProperty(t,"__esModule",{value:!0}),t.allSpecies=t.lookUpIcon=void 0;var a={warmBlooded:{a:["alpaca","vicugna pacos"],l:["armadillo"],"(":["bat"],A:["cat","felis catus"],k:["chicken","gallus gallus"],i:["chimpanzee","pan paniscus","pan troglodytes"],C:["cow","bos taurus"],d:["dog","canis lupus","canis lupus familiaris"],D:["dolphin"],e:["elephant","loxodonta africana","loxodonta cyclotis","elephas maximus"],"!":["ferret","mustela putorius furo"],n:["finch","pyrrhula pyrrhula"],m:["goat"],G:["gorilla","gorilla gorilla"],g:["guinea pig","cavia porcellus"],o:["hedgehog","erinaceus europaeus"],h:["horse","equus caballus"],H:["human","homo sapiens"],3:["kangaroo rat"],r:["monkey","macaca mulatta"],9:["monodelphis","monodelphis domestica"],M:["mouse","mus musculus"],N:["mouse lemur"],"*":["orangutan","pongo abelii","pongo pygmaeus"],8:["papio anubis"],p:["pig","sus scrofa"],U:["platypus","ornithorhynchus anatinus"],t:["rabbit","oryctolagus cuniculus"],R:["rat","rattus norvegicus"],x:["sheep","ovis aries"],Q:["shrew"],I:["squirrel"],w:["wallaby"]},plants:{5:["barley","hordeum vulgare","hordeum vulgare subsp. vulgare"],B:["brassica","brassica oleracea","brassica rapa","arabidopsis","arabidopsis thaliana","arabidopsis lyrata"],"%":["brachypodium","brachypodium distachyon"],c:["corn","zea mays"],"^":["glycinemax","glycine max"],O:["grapes","vitis vinifera"],P:["plant","physcomitrella patens","sorghum bicolor","triticum aestivum"],6:["rice","oryza sativa","oryza sativa japonica group"],")":["tomatoes","solanum lycopersicum","solanum tuberosum"]},other:{0:["amoeba"],7:["anolis","anolis carolinensis"],"£":["aspergillus","aspergillus fumigatus"],$:["bee"],b:["bug"],W:["c elegans","caenorhabditis elegans","schistosoma mansoni"],2:["diatom"],L:["ecoli","escherichia coli"],F:["fly","drosophila melanogaster"],f:["frog","xenopus (silurana) tropicalis","xenopus tropicalis"],u:["fungus"],4:["louse"],1:["mosquito"],"@":["plasmodium"],E:["pufferfish","tetraodon nigroviridis"],"+":["ray"],s:["scorpion"],"'":["snail"],S:["spider"],"&":["tick"],v:["virus"],Y:["yeast","saccharomyces cerevisiae","schizosaccharomyces pombe"],Z:["zebrafish","danio rerio"]}},o=function(e,t){return Object.keys(a[e]).find(function(r){return a[e][r].includes(t.toLowerCase())})};t.lookUpIcon=function(e){for(var t in a){var r=o(t,e);if(r)return[t,r]}return["",""]};var i=[];for(var u in t.allSpecies=i,a)for(var l in a[u])i.push.apply(i,n(a[u][l]))},68:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=function(e){return e&&e.__esModule?e:{default:e}}(r(1));var a=function(e){return e.map(function(e,t){var r="".concat(e.text,"-").concat(t);return e.url?n.default.createElement("li",{style:{marginBottom:"0.3rem"},key:r},n.default.createElement("a",{href:e.url},e.text)):n.default.createElement("li",{style:{marginBottom:"0.3rem"},key:r},e.text)})};t.default=a}},[[178,0]]]);