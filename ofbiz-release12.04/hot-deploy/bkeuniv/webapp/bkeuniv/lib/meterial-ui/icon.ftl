<#include "util.ftl"/>

<style>

    @-webkit-keyframes rotator {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(270deg); }
    }

    @-webkit-keyframes colors {
        0% { stroke: #4285F4; }
        25% { stroke: #DE3E35; }
        50% { stroke: #F7C223; }
        75% { stroke: #1B9A59; }
        100% { stroke: #4285F4; }
    }

    @-webkit-keyframes dash {
        0% { stroke-dashoffset: 187; }
        50% {
            stroke-dashoffset: 47;
            transform:rotate(135deg);
        }
        100% {
            stroke-dashoffset: 187;
            transform:rotate(450deg);
        }
    }

    @keyframes colors {
        0% { stroke: #4285F4; }
        25% { stroke: #DE3E35; }
        50% { stroke: #F7C223; }
        75% { stroke: #1B9A59; }
        100% { stroke: #4285F4; }
    }

    @keyframes dash {
        0% { stroke-dashoffset: 187; }
        50% {
            stroke-dashoffset: 47;
            transform:rotate(135deg);
        }
        100% {
            stroke-dashoffset: 187;
            transform:rotate(450deg);
        }
    }

    @-moz-keyframes colors {
        0% { stroke: #4285F4; }
        25% { stroke: #DE3E35; }
        50% { stroke: #F7C223; }
        75% { stroke: #1B9A59; }
        100% { stroke: #4285F4; }
    }

    @-moz-keyframes dash {
        0% { stroke-dashoffset: 187; }
        50% {
            stroke-dashoffset: 47;
            transform:rotate(135deg);
        }
        100% {
            stroke-dashoffset: 187;
            transform:rotate(450deg);
        }
    }
</style>

<#macro IconSpinner >
    <#local code=random(1, 999999)?string["000000"] />
    <svg id="spinner-${code}" style="-webkit-animation: rotator 1.4s linear infinite; animation: rotator 1.4s linear infinite; -moz-animation: rotator 1.4s linear infinite; -ms-animation: rotator 1.4s linear infinite; -o-animation: rotator 1.4s linear infinite;" width="65px" height="65px" viewBox="0 0 66 66">
        <circle style="stroke-dasharray: 187; stroke-dashoffset: 0; transform-origin: center; -webkit-animation: dash 1.4s ease-in-out infinite, colors (5.6s) ease-in-out infinite; -moz-animation: dash 1.4s ease-in-out infinite, colors (5.6s) ease-in-out infinite; -ms-animation: dash 1.4s ease-in-out infinite, colors (5.6s) ease-in-out infinite; -o-animation: dash 1.4s ease-in-out infinite, colors (5.6s) ease-in-out infinite; animation: dash 1.4s ease-in-out infinite, colors 5.6s ease-in-out infinite;" fill="none" stroke-width="6" stroke-linecap="round" cx="33" cy="33" r="30"></circle>
    </svg>
</#macro>