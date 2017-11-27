<#include "util.ftl"/>



<#macro IconSpinner size="65px" strokeWidth=2>
    <#local code=random(1, 999999)?string["000000"] />
    <style>
        @keyframes spinner-rotate {
            100% {
                transform: rotate(360deg);
            }
        }

        @keyframes spinner-dash {
            0% {
                stroke-dasharray: 1, 200;
                stroke-dashoffset: 0;
            }
            50% {
                stroke-dasharray: 89, 200;
                stroke-dashoffset: -35px;
            }
            100% {
                stroke-dasharray: 89, 200;
                stroke-dashoffset: -124px;
            }
        }

        @keyframes spinner-color {
            100%,
            0% {
                stroke: #d62d20;
            }
            40% {
                stroke: #0057e7;
            }
            66% {
                stroke: #008744;
            }
            80%,
            90% {
                stroke: #ffa700;
            }
        }

        @-webkit-keyframes spinner-rotate {
            100% {
                transform: rotate(360deg);
            }
        }

        @-webkit-keyframes spinner-dash {
        0% {
            stroke-dasharray: 1, 200;
            stroke-dashoffset: 0;
        }
        50% {
            stroke-dasharray: 89, 200;
            stroke-dashoffset: -35px;
        }
        100% {
            stroke-dasharray: 89, 200;
            stroke-dashoffset: -124px;
        }
        }

        @-webkit-keyframes spinner-color {
            100%,
            0% {
                stroke: #d62d20;
            }
            40% {
                stroke: #0057e7;
            }
            66% {
                stroke: #008744;
            }
            80%,
            90% {
                stroke: #ffa700;
            }
        }

        @-moz-keyframes spinner-rotate {
            100% {
                transform: rotate(360deg);
            }
        }

        @-moz-keyframes spinner-dash {
        0% {
            stroke-dasharray: 1, 200;
            stroke-dashoffset: 0;
        }
        50% {
            stroke-dasharray: 89, 200;
            stroke-dashoffset: -35px;
        }
        100% {
            stroke-dasharray: 89, 200;
            stroke-dashoffset: -124px;
        }
        }

        @-moz-keyframes spinner-color {
            100%,
            0% {
                stroke: #d62d20;
            }
            40% {
                stroke: #0057e7;
            }
            66% {
                stroke: #008744;
            }
            80%,
            90% {
                stroke: #ffa700;
            }
        }
        #spinner-${code} {
            position: relative; margin: 0 auto; width: ${size}; height: ${size};
        }

        #spinner-${code} svg {
            animation: spinner-rotate 2s linear infinite; height: 100%; transform-origin: center center; width: 100%; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;
        }

        #spinner-${code} svg circle {
            stroke-dasharray: 1, 200; stroke-dashoffset: 0; animation: spinner-dash 1.5s ease-in-out infinite, spinner-color 6s ease-in-out infinite; stroke-linecap: round;
        }
    </style>
    <div id="spinner-${code}">
        <svg viewBox="25 25 50 50">
            <circle cx="50" cy="50" r="20" fill="none" stroke-width="${strokeWidth}" stroke-miterlimit="10"/>
        </svg>
    </div>
</#macro>
