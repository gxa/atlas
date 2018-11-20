var geneSearch=(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[4],{16:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.ResultPropTypes=t.FacetPropTypes=void 0;var r=function(e){return e&&e.__esModule?e:{default:e}}(n(0));var a={group:r.default.string.isRequired,value:r.default.string.isRequired,label:r.default.string.isRequired,description:r.default.string};t.FacetPropTypes=a;var o=r.default.shape({element:r.default.object.isRequired,facets:r.default.arrayOf(r.default.shape(a))});t.ResultPropTypes=o},168:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=s(n(1)),a=s(n(0)),o=s(n(5)),u=s(n(169)),i=s(n(170));function s(e){return e&&e.__esModule?e:{default:e}}function l(e){return(l="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function c(e,t,n,r,a,o,u){try{var i=e[o](u),s=i.value}catch(e){return void n(e)}i.done?t(s):Promise.resolve(s).then(r,a)}function f(e){return function(){var t=this,n=arguments;return new Promise(function(r,a){var o=e.apply(t,n);function u(e){c(o,r,a,u,i,"next",e)}function i(e){c(o,r,a,u,i,"throw",e)}u(void 0)})}}function d(){return(d=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}function p(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function m(e,t){return!t||"object"!==l(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function b(e){return(b=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function h(e,t){return(h=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}var y=function(e){function t(e){var n;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),(n=m(this,b(t).call(this,e))).state={data:null,loading:!0,error:null},n}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&h(e,t)}(t,r.default.Component),function(e,t,n){t&&p(e.prototype,t),n&&p(e,n)}(t,[{key:"render",value:function(){var e=this.props,t=e.ResultElementClass,n=e.noResultsMessageFormatter,a=e.resultsMessageFormatter,o=this.state,s=o.data,l=o.loading,c=o.error;return c?r.default.createElement(u.default,{error:c}):l?r.default.createElement("div",{id:"loader",className:"row expanded"},r.default.createElement("div",{className:"small-12 columns"},r.default.createElement("h5",null,"Loading, please wait..."))):s.results&&s.results.length>0?r.default.createElement(i.default,d({},s,{ResultElementClass:t,resultsMessage:a(s)})):r.default.createElement("div",{className:"row expanded"},r.default.createElement("div",{className:"small-12 columns"},r.default.createElement("h5",null,n(s))))}},{key:"componentDidUpdate",value:function(){var e=f(regeneratorRuntime.mark(function e(t,n){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(null!==this.state.data||null!==this.state.error){e.next=3;break}return e.next=3,this._loadAsyncData((0,o.default)(this.props.resource,this.props.host).toString());case 3:case"end":return e.stop()}},e,this)}));return function(t,n){return e.apply(this,arguments)}}()},{key:"componentDidMount",value:function(){var e=f(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,this._loadAsyncData((0,o.default)(this.props.resource,this.props.host).toString());case 2:case"end":return e.stop()}},e,this)}));return function(){return e.apply(this,arguments)}}()},{key:"_loadAsyncData",value:function(){var e=f(regeneratorRuntime.mark(function e(t){var n;return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,fetch(t);case 3:if((n=e.sent).ok){e.next=6;break}throw new Error("".concat(t," => ").concat(n.status));case 6:return e.t0=this,e.next=9,n.json();case 9:e.t1=e.sent,e.t2={data:e.t1,loading:!1,error:null},e.t0.setState.call(e.t0,e.t2),e.next=17;break;case 14:e.prev=14,e.t3=e.catch(0),this.setState({data:null,loading:!1,error:{description:"There was a problem communicating with the server. Please try again later.",name:e.t3.name,message:e.t3.message}});case 17:case"end":return e.stop()}},e,this,[[0,14]])}));return function(t){return e.apply(this,arguments)}}()},{key:"componentDidCatch",value:function(e,t){this.setState({error:{description:"There was a problem rendering this component.",name:e.name,message:"".concat(e.message," – ").concat(t)}})}}],[{key:"getDerivedStateFromProps",value:function(e,t){var n=(0,o.default)(e.resource,e.host).toString();return n!==t.url?{data:null,loading:!0,error:null,url:n}:null}}]),t}();y.propTypes={host:a.default.string.isRequired,resource:a.default.string.isRequired,ResultElementClass:a.default.func.isRequired,noResultsMessageFormatter:a.default.func,resultsMessageFormatter:a.default.func},y.defaultProps={noResultsMessageFormatter:function(){return""},resultsMessageFormatter:function(){return""}};var g=y;t.default=g},169:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=o(n(1)),a=o(n(0));function o(e){return e&&e.__esModule?e:{default:e}}var u=function(e){var t=e.error;return r.default.createElement("div",{className:"row column"},r.default.createElement("div",{className:"callout alert small"},r.default.createElement("h5",null,"Oops!"),r.default.createElement("p",null,t.description,r.default.createElement("br",null),"If the error persists, in order to help us debug the issue, please copy the URL and this message and send it to us via ",r.default.createElement("a",{href:"https://www.ebi.ac.uk/support/gxasc"},"the EBI Support & Feedback system"),":"),r.default.createElement("code",null,"".concat(t.name,": ").concat(t.message))))};u.propTypes={error:a.default.shape({description:a.default.string.isRequired,name:a.default.string.isRequired,message:a.default.string.isRequired})};var i=u;t.default=i},170:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=l(n(1)),a=l(n(0)),o=l(n(11)),u=l(n(171)),i=l(n(174)),s=n(16);function l(e){return e&&e.__esModule?e:{default:e}}function c(e){return(c="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function f(){return(f=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}function d(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=[],r=!0,a=!1,o=void 0;try{for(var u,i=e[Symbol.iterator]();!(r=(u=i.next()).done)&&(n.push(u.value),!t||n.length!==t);r=!0);}catch(e){a=!0,o=e}finally{try{r||null==i.return||i.return()}finally{if(a)throw o}}return n}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}function p(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{},r=Object.keys(n);"function"==typeof Object.getOwnPropertySymbols&&(r=r.concat(Object.getOwnPropertySymbols(n).filter(function(e){return Object.getOwnPropertyDescriptor(n,e).enumerable}))),r.forEach(function(t){m(e,t,n[t])})}return e}function m(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function b(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function h(e){return(h=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function y(e,t){return(y=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function g(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}var v=function(e){function t(e){var n;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),(n=function(e,t){return!t||"object"!==c(t)&&"function"!=typeof t?g(e):t}(this,h(t).call(this,e))).state={facets:(0,o.default)(e.results).flatMap("facets").compact().uniqWith(o.default.isEqual).map(function(e){return p({},e,{disabled:!1})}).value(),selectedFacets:{}},n._handleChange=n._handleChange.bind(g(g(n))),n}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&y(e,t)}(t,r.default.Component),function(e,t,n){t&&b(e.prototype,t),n&&b(e,n)}(t,[{key:"_filterResults",value:function(e){return this.props.results.filter(function(t){return Object.entries(e).every(function(e){var n=d(e,2),r=n[0],a=n[1];return t.facets.some(function(e){return r===e.group&&a.map(function(e){return e.value}).includes(e.value)})})})}},{key:"_hasNoResults",value:function(e,t){var n=o.default.mergeWith(o.default.defaultsDeep({},e),m({},t.group,t),function(e,t){return o.default.uniq((e||[]).concat(t))});return 0===this._filterResults(n).length}},{key:"_disableEnabledFacetsWithNoResults",value:function(e,t){var n=this;return o.default.cloneDeep(this.state.facets).map(function(r){return p({},r,{disabled:r.group!==t?!!r.disabled||n._hasNoResults(e,r):r.disabled})})}},{key:"_enableDisabledFacetsWithResults",value:function(e,t){var n=this;return o.default.cloneDeep(this.state.facets).map(function(r){return p({},r,{disabled:r.group!==t?!!r.disabled&&n._hasNoResults(e,r):r.disabled})})}},{key:"_handleChange",value:function(e,t){var n=o.default.defaultsDeep({},this.state.selectedFacets);n[e]=t;var r=Object.keys(n).filter(function(e){return n[e]&&n[e].length>0}).reduce(function(e,t){return e[t]=n[t],e},{}),a=this.state.selectedFacets[e]?this.state.selectedFacets[e].length:0,u={};u=n[e].length>a?1===n[e].length?this._disableEnabledFacetsWithNoResults(r,e):this._enableDisabledFacetsWithResults(r,e):0===n[e].length?this._enableDisabledFacetsWithResults(r,e):this._disableEnabledFacetsWithNoResults(r,e),this.setState({facets:u,selectedFacets:r})}},{key:"render",value:function(){var e=this.state.facets,t=this.props,n=t.checkboxFacetGroups,a=t.ResultElementClass,o=t.resultsMessage,s=this.state.selectedFacets;return r.default.createElement("div",{className:"row expanded"},e.length>0&&r.default.createElement("div",{className:"small-12 medium-4 large-3 columns"},r.default.createElement(u.default,f({facets:e,checkboxFacetGroups:n},{onChange:this._handleChange}))),r.default.createElement("div",{className:"small-12 medium-8 large-9 columns"},r.default.createElement(i.default,f({resultsMessage:o,ResultElementClass:a},{filteredResults:this._filterResults(s)}))))}}]),t}();v.propTypes={results:a.default.arrayOf(s.ResultPropTypes).isRequired,checkboxFacetGroups:a.default.arrayOf(a.default.string),resultsMessage:a.default.string,ResultElementClass:a.default.func.isRequired},v.defaultProps={resultsMessage:"",checkboxFacetGroups:[]};var w=v;t.default=w},171:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=l(n(1)),a=l(n(0)),o=l(n(11)),u=l(n(172)),i=l(n(173)),s=n(16);function l(e){return e&&e.__esModule?e:{default:e}}function c(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}var f=function(e){var t=e.facets,n=e.checkboxFacetGroups,a=e.onChange,s=(0,o.default)(t).sortBy(["group","label"]).groupBy("group").toPairs().partition(function(e){return n.includes(e[0])}).value();return[s[0].map(function(e){return r.default.createElement(u.default,{facetGroupName:e[0],facetGroupNameDescription:e[1][0].description,facets:e[1],onChange:a,key:e[0]})}),s[1].map(function(e){return r.default.createElement(i.default,{facetGroupName:e[0],facetGroupNameDescription:e[1][0].description,facets:e[1],onChange:a,key:e[0]})})]};f.propTypes={facets:a.default.arrayOf(a.default.shape(function(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{},r=Object.keys(n);"function"==typeof Object.getOwnPropertySymbols&&(r=r.concat(Object.getOwnPropertySymbols(n).filter(function(e){return Object.getOwnPropertyDescriptor(n,e).enumerable}))),r.forEach(function(t){c(e,t,n[t])})}return e}({},s.FacetPropTypes,{disabled:a.default.bool.isRequired}))).isRequired,checkboxFacetGroups:a.default.arrayOf(a.default.string),onChange:a.default.func.isRequired},f.defaultProps={checkboxFacetGroups:[]};var d=f;t.default=d},172:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=u(n(1)),a=n(11),o=u(n(64));function u(e){return e&&e.__esModule?e:{default:e}}function i(e){return(i="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function s(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function l(e){return(l=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function c(e,t){return(c=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function f(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}function d(){return(d=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}var p=function(e){var t=e.group,n=e.value,a=e.label,o=e.disabled,u=e.checked,i=e.onChange;return r.default.createElement("div",null,r.default.createElement("input",d({type:"checkbox"},{value:n,checked:u,disabled:o},{onChange:function(){return i({group:t,label:a,value:n,disabled:o})}})),r.default.createElement("label",{style:o?{color:"lightgrey"}:{}},a))},m={background:"white",border:"none"},b=function(e){function t(e){var n;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),(n=function(e,t){return!t||"object"!==i(t)&&"function"!=typeof t?f(e):t}(this,l(t).call(this,e))).state={checkedFacets:[]},n._handleChange=n._handleChange.bind(f(f(n))),n}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&c(e,t)}(t,r.default.Component),function(e,t,n){t&&s(e.prototype,t),n&&s(e,n)}(t,[{key:"_handleChange",value:function(e){var t=this;this.setState({checkedFacets:(0,a.xorBy)(this.state.checkedFacets,[e],"value")},function(){return t.props.onChange(e.group,t.state.checkedFacets)})}},{key:"render",value:function(){var e=this,t=this.props,n=t.facetGroupName,a=t.facetGroupNameDescription,o=t.facets,u=this.state.checkedFacets;return r.default.createElement("div",{className:"padding-bottom-xlarge"},r.default.createElement("h4",null,n,a&&r.default.createElement("span",null,r.default.createElement("sup",{"data-tooltip":!0,"aria-haspopup":"true",className:"has-tip tip-right",style:m,title:a},"?"))),o.map(function(t){return r.default.createElement(p,d({},t,{checked:u.some(function(e){return e.value===t.value}),onChange:e._handleChange,key:t.value}))}))}}]),t}();b.propTypes=o.default;var h=b;t.default=h},173:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=i(n(1)),a=i(n(27)),o=i(n(7)),u=i(n(64));function i(e){return e&&e.__esModule?e:{default:e}}function s(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{},r=Object.keys(n);"function"==typeof Object.getOwnPropertySymbols&&(r=r.concat(Object.getOwnPropertySymbols(n).filter(function(e){return Object.getOwnPropertyDescriptor(n,e).enumerable}))),r.forEach(function(t){l(e,t,n[t])})}return e}function l(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function c(){var e=function(e,t){t||(t=e.slice(0));return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}(["\n  background-image: url(\"data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='32' height='24' viewBox='0 0 32 24'><polygon points='0,0 32,0 16,24' style='fill: rgb%28138, 138, 138%29'></polygon></svg>\");\n  background-origin: content-box;\n  background-position: center;\n  background-repeat: no-repeat;\n  background-size: 9px 6px;\n  width: 1.5rem;\n  height: 1rem;\n"]);return c=function(){return e},e}var f=o.default.span(c()),d={control:function(e,t){return{minHeight:"2.4375rem",margin:"0 0 1rem",padding:"0",appearance:"none",border:t.isFocused?"1px solid #8a8a8a":"1px solid #777",borderRadius:"0",backgroundColor:t.isDisabled?"#e6e6e6":"#fefefe",fontFamily:"inherit",fontSize:"1rem",fontWeight:"normal",lineHeight:"1.5",color:"#0a0a0a",transition:"box-shadow 0.5s, border-color 0.25s ease-in-out",outline:t.isFocused?"none":"inherit",boxShadow:t.isFocused?"0 0 5px #777":"none",cursor:t.isDisabled?"not-allowed":"default",display:"flex"}},multiValueLabel:function(e){return s({},e,{whiteSpace:"pre-line"})},menu:function(e){return s({},e,{borderRadius:"0",padding:"0"})}},p={background:"white",border:"none"},m=function(e){var t=e.facetGroupName,n=e.facetGroupNameDescription,o=e.facets,u=e.onChange;return r.default.createElement("div",{className:"padding-bottom-xlarge"},r.default.createElement("h4",null,t,n&&r.default.createElement("span",null,r.default.createElement("sup",{"data-tooltip":!0,"aria-haspopup":"true",className:"has-tip tip-right",style:p,title:n},"?"))),r.default.createElement(a.default,{inputId:"facetGroupMultiSelectDropdown",components:{DropdownIndicator:f,IndicatorSeparator:null},styles:d,closeMenuOnSelect:!1,isMulti:!0,onChange:function(e){return u(t,e)},options:o.filter(function(e){return!e.disabled})}))};m.propTypes=u.default;var b=m;t.default=b},174:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=u(n(1)),a=u(n(0)),o=n(16);function u(e){return e&&e.__esModule?e:{default:e}}var i=function(e){var t=e.filteredResults,n=e.resultsMessage,a=e.ResultElementClass,o=t.map(function(e){return e.element});return r.default.createElement("div",null,r.default.createElement("h4",null,n),o.map(function(e,t){return r.default.createElement("div",{key:t},r.default.createElement(a,e))}))};i.propTypes={filteredResults:a.default.arrayOf(o.ResultPropTypes).isRequired,resultsMessage:a.default.string,ResultElementClass:a.default.func.isRequired},i.defaultProps={resultsMessage:""};var s=i;t.default=s},175:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=u(n(1)),a=u(n(0)),o=n(65);function u(e){return e&&e.__esModule?e:{default:e}}function i(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=[],r=!0,a=!1,o=void 0;try{for(var u,i=e[Symbol.iterator]();!(r=(u=i.next()).done)&&(n.push(u.value),!t||n.length!==t);r=!0);}catch(e){a=!0,o=e}finally{try{r||null==i.return||i.return()}finally{if(a)throw o}}return n}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}var s=function(e){var t=e.species,n=e.groupColors,a=e.color,u=i((0,o.lookUpIcon)(t),2),s=u[0],l=u[1];return r.default.createElement("span",{className:"icon icon-species",style:{color:a||n[s]||"black"},"data-icon":l||"❔",title:function(e){return e.charAt(0).toUpperCase()+e.slice(1).toLowerCase()}(t)})};s.propTypes={species:a.default.string.isRequired,groupColors:a.default.shape({warmBlooded:a.default.string.isRequired,plants:a.default.string.isRequired,other:a.default.string.isRequired}),color:a.default.string},s.defaultProps={species:"oryctolagus cuniculus",groupColors:{warmBlooded:"indianred",plants:"mediumseagreen",other:"deepskyblue"}};var l=s;t.default=l},176:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=i(n(1)),a=i(n(0)),o=i(n(7)),u=n(65);function i(e){return e&&e.__esModule?e:{default:e}}function s(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=[],r=!0,a=!1,o=void 0;try{for(var u,i=e[Symbol.iterator]();!(r=(u=i.next()).done)&&(n.push(u.value),!t||n.length!==t);r=!0);}catch(e){a=!0,o=e}finally{try{r||null==i.return||i.return()}finally{if(a)throw o}}return n}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}function l(){var e=f(["\n  @font-face {\n    font-family: 'EBI-Species';\n    src:url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot');\n    src:url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff2') format('woff2'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff') format('woff'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),\n      url('https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal;\n  }\n\n  ::before {\n    font-family: 'EBI-Species';\n    content: attr(data-icon);\n    text-transform: none;\n  }\n"]);return l=function(){return e},e}function c(){var e=f(["\n  text-decoration: none;\n  font-style: normal;\n  text-rendering: optimizeLegibility !important;\n  background-size: contain;\n  font-weight: 400;\n"]);return c=function(){return e},e}function f(e,t){return t||(t=e.slice(0)),Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}var d=o.default.span(c()),p=(0,o.default)(d)(l()),m=function(e){var t=e.species,n=e.groupColors,a=e.color,o=s((0,u.lookUpIcon)(t),2),i=o[0],l=o[1];return r.default.createElement(p,{style:{color:a||n[i]||"black"},"data-icon":l||"❔",title:function(e){return e.charAt(0).toUpperCase()+e.slice(1).toLowerCase()}(t)})};m.propTypes={species:a.default.string.isRequired,groupColors:a.default.shape({warmBlooded:a.default.string.isRequired,plants:a.default.string.isRequired,other:a.default.string.isRequired}),color:a.default.string},m.defaultProps={species:"oryctolagus cuniculus",groupColors:{warmBlooded:"indianred",plants:"mediumseagreen",other:"deepskyblue"}};var b=m;t.default=b},188:function(e,t,n){"use strict";n.r(t);var r=n(1),a=n.n(r),o=n(4),u=n.n(o),i=n(0),s=n.n(i),l=n(189),c=n(75),f=n(5),d=n.n(f),p=n(23),m=n.n(p),b=n(77),h=n.n(b),y=n(7),g=n(78),v=n.n(g),w=function(e){var t=e.iconSrc,n=e.description;return a.a.createElement("div",{className:"column column-block text-center combo card",style:{marginBottom:0,paddingBottom:"5px"}},a.a.createElement("span",{className:"species-icon",style:{fontSize:"300%"}},a.a.createElement(v.a,{species:t})),n&&a.a.createElement("h6",{className:"species-name"},n))};w.propTypes={iconSrc:s.a.string.isRequired,description:s.a.string};var E=w;function O(e){return(O="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function _(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function j(e,t){return!t||"object"!==O(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function R(e){return(R=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function k(e,t){return(k=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function S(){var e=I(["\n  width: 10%;\n  text-align: center;\n"]);return S=function(){return e},e}function P(){var e=I(["\n  width: 20%;\n  text-align: center;\n"]);return P=function(){return e},e}function x(){var e=I(["\n  width: 40%;\n  text-align: center;\n  margin-bottom: 0;\n"]);return x=function(){return e},e}function C(){var e=I(["\n  width: 15%;\n  text-align: center;\n"]);return C=function(){return e},e}function q(){var e=I(["\n  width: 15%;\n  text-align: center;\n"]);return q=function(){return e},e}function M(){var e=I(["\n  height: 100%;\n  width: 100%;\n  display: flex !important;\n  flex-wrap: nowrap;\n  align-items: center;\n  border: #e6e6e6 solid 1px;\n  margin-bottom: 0.5rem;\n  padding: 1rem;\n  &:hover {background-color: #eaeaea;}\n"]);return M=function(){return e},e}function I(e,t){return t||(t=e.slice(0)),Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}var F=y.default.div(M()),B=y.default.span(q()),N=y.default.div(C()),T=y.default.p(x()),D=y.default.div(P()),G=y.default.div(S()),A=function(e){function t(e){return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),j(this,R(t).call(this,e))}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&k(e,t)}(t,a.a.Component),function(e,t,n){t&&_(e.prototype,t),n&&_(e,n)}(t,[{key:"render",value:function(){var e=this.props,t=e.url,n=e.species,r=e.experimentDescription,o=e.markerGenes,u=e.numberOfAssays,i=e.factors,s=o&&o.map(function(e){return a.a.createElement("li",null,a.a.createElement("a",{href:e.url},"See cluster ",e.clusterIds.sort().join(", ")," for k = ",e.k))});return a.a.createElement("a",{href:t},a.a.createElement(F,null,a.a.createElement(B,null,a.a.createElement(E,{iconSrc:n,description:n})),o?a.a.createElement(N,null,a.a.createElement("ul",{style:{marginBottom:0}},s)):a.a.createElement(N,null,a.a.createElement("span",{"data-tooltip":!0,title:"Not a marker gene",class:"icon icon-functional","data-icon":"x"})),a.a.createElement(T,null," ",r," "),a.a.createElement(D,null,a.a.createElement("ul",{style:{marginBottom:0}},i.map(function(e){return a.a.createElement("li",null," ",e," ")}))),a.a.createElement(G,null," ",u," ")))}}]),t}();A.propTypes={url:s.a.string.isRequired,species:s.a.string.isRequired,experimentDescription:s.a.string.isRequired,markerGenes:s.a.arrayOf(s.a.shape({k:s.a.number.isRequired,clusterIds:s.a.array.isRequired,url:s.a.string.isRequired})),numberOfAssays:s.a.number.isRequired,factors:s.a.array.isRequired};var z=A;function U(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}var W=function(e){var t,n=e.atlasUrl,r=e.history,o=d()(location.search).query(!0),u=["q","ensgene","symbol","entrezgene","hgnc_symbol","mgi_id","mgi_symbol","flybase_gene_id","wbpsgene"],i=Object.keys(o).find(function(e){return u.includes(e)}),s=i?{term:o[i],category:i}:{};return a.a.createElement("div",null,a.a.createElement(m.a,{atlasUrl:n,wrapperClassName:"row expanded",actionEndpoint:"search",onSubmit:function(e,t,n){e.preventDefault(),r.push("/search?species="+n+"&"+t.category+"="+t.term)},autocompleteClassName:"small-8 columns",suggesterEndpoint:"json/suggestions/gene_ids",defaultValue:s,enableSpeciesSelect:!0,speciesEndpoint:"json/suggestions/species",speciesSelectClassName:"small-4 columns",defaultSpecies:o.species}),i&&a.a.createElement(h.a,{host:n,resource:"json/search?"+d.a.buildQuery((t={},U(t,s.category,s.term),U(t,"species",o.species),t)),ResultElementClass:z,noResultsMessageFormatter:function(e){return"".concat(s.term," is not expressed in any experiment: ").concat(e.reason)},resultsMessageFormatter:function(e){return"".concat(s.term," ").concat(e.matchingGeneId," is expressed in:")}}))};W.propTypes={atlasUrl:s.a.string.isRequired,history:s.a.object.isRequired};var L=W,H=function(e){var t=e.atlasUrl,n=e.basename;return a.a.createElement(l.a,{basename:n},a.a.createElement(c.a,{path:"/search",render:function(e){e.match,e.location;var n=e.history;return a.a.createElement(L,{atlasUrl:t,history:n})}}))};H.propTypes={atlasUrl:s.a.string.isRequired,basename:s.a.string.isRequired};var J=H;n.d(t,"render",function(){return Q});var Q=function(e,t){u.a.render(a.a.createElement(J,e),document.getElementById(t))}},64:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=function(e){return e&&e.__esModule?e:{default:e}}(n(0)),a=n(16);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}var u={facetGroupName:r.default.string.isRequired,facetGroupNameDescription:r.default.string,facets:r.default.arrayOf(r.default.shape(function(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{},r=Object.keys(n);"function"==typeof Object.getOwnPropertySymbols&&(r=r.concat(Object.getOwnPropertySymbols(n).filter(function(e){return Object.getOwnPropertyDescriptor(n,e).enumerable}))),r.forEach(function(t){o(e,t,n[t])})}return e}({},a.FacetPropTypes,{disabled:r.default.bool.isRequired}))).isRequired,onChange:r.default.func.isRequired};t.default=u},65:function(e,t,n){"use strict";function r(e){return function(e){if(Array.isArray(e)){for(var t=0,n=new Array(e.length);t<e.length;t++)n[t]=e[t];return n}}(e)||function(e){if(Symbol.iterator in Object(e)||"[object Arguments]"===Object.prototype.toString.call(e))return Array.from(e)}(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance")}()}Object.defineProperty(t,"__esModule",{value:!0}),t.allSpecies=t.lookUpIcon=void 0;var a={warmBlooded:{a:["alpaca","vicugna pacos"],l:["armadillo"],"(":["bat"],A:["cat","felis catus"],k:["chicken","gallus gallus"],i:["chimpanzee","pan paniscus","pan troglodytes"],C:["cow","bos taurus"],d:["dog","canis lupus","canis lupus familiaris"],D:["dolphin"],e:["elephant","loxodonta africana","loxodonta cyclotis","elephas maximus"],"!":["ferret","mustela putorius furo"],n:["finch","pyrrhula pyrrhula"],m:["goat"],G:["gorilla","gorilla gorilla"],g:["guinea pig","cavia porcellus"],o:["hedgehog","erinaceus europaeus"],h:["horse","equus caballus"],H:["human","homo sapiens"],3:["kangaroo rat"],r:["monkey","macaca mulatta"],9:["monodelphis","monodelphis domestica"],M:["mouse","mus musculus"],N:["mouse lemur"],"*":["orangutan","pongo abelii","pongo pygmaeus"],8:["papio anubis"],p:["pig","sus scrofa"],U:["platypus","ornithorhynchus anatinus"],t:["rabbit","oryctolagus cuniculus"],R:["rat","rattus norvegicus"],x:["sheep","ovis aries"],Q:["shrew"],I:["squirrel"],w:["wallaby"]},plants:{5:["barley","hordeum vulgare","hordeum vulgare subsp. vulgare"],B:["brassica","brassica oleracea","brassica rapa","arabidopsis","arabidopsis thaliana","arabidopsis lyrata"],"%":["brachypodium","brachypodium distachyon"],c:["corn","zea mays"],"^":["glycinemax","glycine max"],O:["grapes","vitis vinifera"],P:["plant","physcomitrella patens","sorghum bicolor","triticum aestivum"],6:["rice","oryza sativa","oryza sativa japonica group"],")":["tomatoes","solanum lycopersicum","solanum tuberosum"]},other:{0:["amoeba"],7:["anolis","anolis carolinensis"],"£":["aspergillus","aspergillus fumigatus"],$:["bee"],b:["bug"],W:["c elegans","caenorhabditis elegans","schistosoma mansoni"],2:["diatom"],L:["ecoli","escherichia coli"],F:["fly","drosophila melanogaster"],f:["frog","xenopus (silurana) tropicalis","xenopus tropicalis"],u:["fungus"],4:["louse"],1:["mosquito"],"@":["plasmodium"],E:["pufferfish","tetraodon nigroviridis"],"+":["ray"],s:["scorpion"],"'":["snail"],S:["spider"],"&":["tick"],v:["virus"],Y:["yeast","saccharomyces cerevisiae","schizosaccharomyces pombe"],Z:["zebrafish","danio rerio"]}},o=function(e,t){return Object.keys(a[e]).find(function(n){return a[e][n].includes(t.toLowerCase())})};t.lookUpIcon=function(e){for(var t in a){var n=o(t,e);if(n)return[t,n]}return["",""]};var u=[];for(var i in t.allSpecies=u,a)for(var s in a[i])u.push.apply(u,r(a[i][s]))},77:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=function(e){return e&&e.__esModule?e:{default:e}}(n(168)).default;t.default=r},78:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),Object.defineProperty(t,"default",{enumerable:!0,get:function(){return o.default}}),Object.defineProperty(t,"EbiSpeciesIconBare",{enumerable:!0,get:function(){return u.default}}),t.renderBare=t.render=void 0;var r=i(n(1)),a=i(n(4)),o=i(n(175)),u=i(n(176));function i(e){return e&&e.__esModule?e:{default:e}}t.render=function(e,t){a.default.render(r.default.createElement(o.default,e),document.getElementById(t))};t.renderBare=function(e,t){a.default.render(r.default.createElement(u.default,e),document.getElementById(t))}}},[[188,0]]]);