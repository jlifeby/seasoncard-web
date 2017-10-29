<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<!-- Begin LeadBack code {literal} -->--%>
<%--<script>--%>
<%--var _emv = _emv || [];--%>
<%--_emv['campaign'] = '86ba98bcbd34664bd6a600d4';--%>

<%--(function () {--%>
<%--var em = document.createElement('script');--%>
<%--em.type = 'text/javascript';--%>
<%--em.async = true;--%>
<%--em.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'leadback.ru/js/leadback.js';--%>
<%--var s = document.getElementsByTagName('script')[0];--%>
<%--s.parentNode.insertBefore(em, s);--%>
<%--})();--%>
<%--</script>--%>
<%--<!-- End LeadBack code {/literal} -->--%>

<div>
    <div id="lb_button-wrapper" style="position: fixed; bottom: 50px; right: 50px; z-index: 19999998; display: block;"
         title="Нажмите, чтобы заказать обратный звонок">
        <style>
            /*
            ==============================================
            bigEntrance
            ==============================================
            */
            .bigEntrance {
                -webkit-animation-name: bigEntrance;
                -moz-animation-name: bigEntrance;
                -o-animation-name: bigEntrance;
                animation-name: bigEntrance;

                -webkit-animation-duration: 1.6s;
                -moz-animation-duration: 1.6s;
                -o-animation-duration: 1.6s;
                animation-duration: 1.6s;

                -webkit-animation-timing-function: ease-out;
                -moz-animation-timing-function: ease-out;
                -o-animation-timing-function: ease-out;
                animation-timing-function: ease-out;

                visibility: visible !important;
            }

            @-webkit-keyframes bigEntrance {
                0% {
                    -webkit-transform: scale(0.3) rotate(6deg) translateX(-30%) translateY(30%);
                    opacity: 0.2;
                }
                30% {
                    -webkit-transform: scale(1.03) rotate(-2deg) translateX(2%) translateY(-2%);
                    opacity: 1;
                }
                45% {
                    -webkit-transform: scale(0.98) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                60% {
                    -webkit-transform: scale(1.01) rotate(-1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                75% {
                    -webkit-transform: scale(0.99) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                90% {
                    -webkit-transform: scale(1.01) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                100% {
                    -webkit-transform: scale(1) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
            }

            @-moz-keyframes bigEntrance {
                0% {
                    -moz-transform: scale(0.3) rotate(6deg) translateX(-30%) translateY(30%);
                    opacity: 0.2;
                }
                30% {
                    -moz-transform: scale(1.03) rotate(-2deg) translateX(2%) translateY(-2%);
                    opacity: 1;
                }
                45% {
                    -moz-transform: scale(0.98) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                60% {
                    -moz-transform: scale(1.01) rotate(-1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                75% {
                    -moz-transform: scale(0.99) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                90% {
                    -moz-transform: scale(1.01) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                100% {
                    -moz-transform: scale(1) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
            }

            @-o-keyframes bigEntrance {
                0% {
                    -o-transform: scale(0.3) rotate(6deg) translateX(-30%) translateY(30%);
                    opacity: 0.2;
                }
                30% {
                    -o-transform: scale(1.03) rotate(-2deg) translateX(2%) translateY(-2%);
                    opacity: 1;
                }
                45% {
                    -o-transform: scale(0.98) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                60% {
                    -o-transform: scale(1.01) rotate(-1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                75% {
                    -o-transform: scale(0.99) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                90% {
                    -o-transform: scale(1.01) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                100% {
                    -o-transform: scale(1) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
            }

            @keyframes bigEntrance {
                0% {
                    transform: scale(0.3) rotate(6deg) translateX(-30%) translateY(30%);
                    opacity: 0.2;
                }
                30% {
                    transform: scale(1.03) rotate(-2deg) translateX(2%) translateY(-2%);
                    opacity: 1;
                }
                45% {
                    transform: scale(0.98) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                60% {
                    transform: scale(1.01) rotate(-1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                75% {
                    transform: scale(0.99) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                90% {
                    transform: scale(1.01) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                100% {
                    transform: scale(1) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
            }

            /*
            ==============================================
            lb-pulse
            ==============================================
            */

            .lb-pulse {
                -webkit-animation-name: lb-pulse;
                -moz-animation-name: lb-pulse;
                -o-animation-name: lb-pulse;
                animation-name: lb-pulse;

                -webkit-animation-duration: 1.5s;
                -moz-animation-duration: 1.5s;
                -o-animation-duration: 1.5s;
                animation-duration: 1.5s;

                -webkit-animation-timing-function: ease-out;
                -moz-animation-timing-function: ease-out;
                -o-animation-timing-function: ease-out;
                animation-timing-function: ease-out;

                -webkit-animation-iteration-count: infinite;
                -moz-animation-iteration-count: infinite;
                -o-animation-iteration-count: infinite;
                animation-iteration-count: infinite;
            }

            @-webkit-keyframes lb-pulse {
                0% {
                    opacity: 1;
                }
                30% {
                    -webkit-transform: scale(1.03) rotate(-2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                45% {
                    -webkit-transform: scale(0.98) rotate(2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                60% {
                    -webkit-transform: scale(1.03) rotate(-2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                75% {
                    -webkit-transform: scale(0.99) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                90% {
                    -webkit-transform: scale(1.03) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                100% {
                    -webkit-transform: scale(1) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
            }

            @-moz-keyframes lb-pulse {
                0% {
                    opacity: 1;
                }
                30% {
                    -moz-transform: scale(1.03) rotate(-2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                45% {
                    -moz-transform: scale(0.98) rotate(2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                60% {
                    -moz-transform: scale(1.03) rotate(-2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                75% {
                    -moz-transform: scale(0.99) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                90% {
                    -moz-transform: scale(1.03) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                100% {
                    -moz-transform: scale(1) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
            }

            @-o-keyframes lb-pulse {
                0% {
                    opacity: 1;
                }
                30% {
                    -o-transform: scale(1.03) rotate(-2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                45% {
                    -o-transform: scale(0.98) rotate(2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                60% {
                    -o-transform: scale(1.03) rotate(-2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                75% {
                    -o-transform: scale(0.99) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                90% {
                    -o-transform: scale(1.03) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                100% {
                    -o-transform: scale(1) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
            }

            @keyframes lb-pulse {
                0% {
                    opacity: 1;
                }
                30% {
                    transform: scale(1.03) rotate(-2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                45% {
                    transform: scale(0.98) rotate(2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                60% {
                    transform: scale(1.03) rotate(-2deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                75% {
                    transform: scale(0.99) rotate(1deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                90% {
                    transform: scale(1.03) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
                100% {
                    transform: scale(1) rotate(0deg) translateX(0%) translateY(0%);
                    opacity: 1;
                }
            }

            .phone-call_wave {
                position: absolute;
                left: 50%;
                top: 50%;
                -webkit-transform: translate(-50%, -50%);
                -moz-transform: translate(-50%, -50%);
                -o-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
                border-radius: 50%;
            }

            .phone-call_wave__stroke {
                border: 2px solid #3498DB;
                width: 90%;
                height: 90%;
                opacity: .9;
                -webkit-animation: wave-stroke 1.5s infinite cubic-bezier(.42, 0, .85, .75);
                -moz-animation: wave-stroke 1.5s infinite cubic-bezier(.42, 0, .85, .75);
                -o-animation: wave-stroke 1.5s infinite cubic-bezier(.42, 0, .85, .75);
                animation: wave-stroke 1.5s infinite cubic-bezier(.42, 0, .85, .75);
            }

            /*    Волна без заливки*/
            @-webkit-keyframes wave-stroke {
                100% {
                    width: 200%;
                    height: 200%;
                    border-color: transparent;
                    opacity: 0;
                }
            }

            @-moz-keyframes wave-stroke {
                100% {
                    width: 200%;
                    height: 200%;
                    border-color: transparent;
                    opacity: 0;
                }
            }

            @-o-keyframes wave-stroke {
                100% {
                    width: 200%;
                    height: 200%;
                    border-color: transparent;
                    opacity: 0;
                }
            }

            @keyframes wave-stroke {
                100% {
                    width: 200%;
                    height: 200%;
                    border-color: transparent;
                    opacity: 0;
                }
            }

            .lb_call-btn {
                background: url(data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiA/Pgo8IURPQ1RZUEUgc3ZnICBQVUJMSUMgJy0vL1czQy8vRFREIFNWRyAxLjEvL0VOJyAgJ2h0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCc+CjxzdmcgZW5hYmxlLWJhY2tncm91bmQ9Im5ldyAwIDAgMTIwIDEyMCIgaGVpZ2h0PSIxMDBweCIgaWQ9IkxheWVyXzEiIHZlcnNpb249IjEuMSIgdmlld0JveD0iMCAwIDEyMCAxMjAiIHdpZHRoPSIxMDBweCIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgb3JpZ2luYWw9IiM0OGIxZjciPgoJPHN0eWxlPgoJQC13ZWJraXQta2V5ZnJhbWVzIHR3aXJsLXBob25lIHsKCTAlIHsKCgl9CgkzMCUgewoJLXdlYmtpdC10cmFuc2Zvcm06IHJvdGF0ZSgtNmRlZykgdHJhbnNsYXRlWSgtMXB4KTsKCX0KCTc1JSB7Cgktd2Via2l0LXRyYW5zZm9ybTogcm90YXRlKDVkZWcpIHRyYW5zbGF0ZVkoLTFweCk7Cgl9CgkxMDAlIHsKCS13ZWJraXQtdHJhbnNmb3JtOiByb3RhdGUoMGRlZykgdHJhbnNsYXRlWSgtMXB4KTsKCX0KCgl9CglALW1vei1rZXlmcmFtZXMgdHdpcmwtcGhvbmUgewoJMCUgewoKCX0KCTMwJSB7CgktbW96LXRyYW5zZm9ybTogcm90YXRlKC02ZGVnKSB0cmFuc2xhdGVZKC0xcHgpOwoJfQoJNzUlIHsKCS1tb3otdHJhbnNmb3JtOiByb3RhdGUoNWRlZykgdHJhbnNsYXRlWSgtMXB4KTsKCX0KCTEwMCUgewoJLW1vei10cmFuc2Zvcm06IHJvdGF0ZSgwZGVnKSB0cmFuc2xhdGVZKC0xcHgpOwoJfQoKCX0KCUAtby1rZXlmcmFtZXMgdHdpcmwtcGhvbmUgewoJMCUgewoKCX0KCTMwJSB7Cgktby10cmFuc2Zvcm06IHJvdGF0ZSgtNmRlZykgdHJhbnNsYXRlWSgtMXB4KTsKCX0KCTc1JSB7Cgktby10cmFuc2Zvcm06IHJvdGF0ZSg1ZGVnKSB0cmFuc2xhdGVZKC0xcHgpOwoJfQoJMTAwJSB7Cgktby10cmFuc2Zvcm06IHJvdGF0ZSgwZGVnKSB0cmFuc2xhdGVZKC0xcHgpOwoJfQoKCX0KCUBrZXlmcmFtZXMgdHdpcmwtcGhvbmUgewoJMCUgewoKCX0KCTMwJSB7Cgl0cmFuc2Zvcm06IHJvdGF0ZSgtNmRlZykgdHJhbnNsYXRlWSgtMXB4KTsKCX0KCTc1JSB7Cgl0cmFuc2Zvcm06IHJvdGF0ZSg1ZGVnKSB0cmFuc2xhdGVZKC0xcHgpOwoJfQoJMTAwJSB7Cgl0cmFuc2Zvcm06IHJvdGF0ZSgwZGVnKSB0cmFuc2xhdGVZKC0xcHgpOwoJfQoKCX0KCgkjbGJfcGhvbmUtaGVhZCB7Cgktd2Via2l0LXRyYW5zZm9ybS1vcmlnaW46IDUwJSA1MCU7Cgktd2Via2l0LWFuaW1hdGlvbjogdHdpcmwtcGhvbmUgMC41cyBhbHRlcm5hdGUgaW5maW5pdGUgbGluZWFyOwoKCS1tb3otdHJhbnNmb3JtLW9yaWdpbjogNTAlIDUwJTsKCS1tb3otYW5pbWF0aW9uOiB0d2lybC1waG9uZSAwLjVzIGFsdGVybmF0ZSBpbmZpbml0ZSBsaW5lYXI7CgoJLW8tdHJhbnNmb3JtLW9yaWdpbjogNTAlIDUwJTsKCS1vLWFuaW1hdGlvbjogdHdpcmwtcGhvbmUgMC41cyBhbHRlcm5hdGUgaW5maW5pdGUgbGluZWFyOwoKCXRyYW5zZm9ybS1vcmlnaW46IDUwJSA1MCU7CglhbmltYXRpb246IHR3aXJsLXBob25lIDAuNXMgYWx0ZXJuYXRlIGluZmluaXRlIGxpbmVhcjsKCgl9CgoJQC13ZWJraXQta2V5ZnJhbWVzIHdhdmUtc3Ryb2tlIHsKCTEwMCUgewoJb3BhY2l0eTogMDsKCX0KCX0KCUAtbW96LWtleWZyYW1lcyB3YXZlLXN0cm9rZSB7CgkxMDAlIHsKCW9wYWNpdHk6IDA7Cgl9Cgl9CglALW8ta2V5ZnJhbWVzIHdhdmUtc3Ryb2tlIHsKCTEwMCUgewoJb3BhY2l0eTogMDsKCX0KCX0KCUBrZXlmcmFtZXMgd2F2ZS1zdHJva2UgewoJMTAwJSB7CglvcGFjaXR5OiAwOwoJfQoJfQoKCS5sYl9waG9uZS13YXZlIHsKCS13ZWJraXQtYW5pbWF0aW9uOiB3YXZlLXN0cm9rZSAxcyBhbHRlcm5hdGUgaW5maW5pdGUgbGluZWFyOwoJLW1vei1hbmltYXRpb246IHdhdmUtc3Ryb2tlIDFzIGFsdGVybmF0ZSBpbmZpbml0ZSBsaW5lYXI7Cgktby1hbmltYXRpb246IHdhdmUtc3Ryb2tlIDFzIGFsdGVybmF0ZSBpbmZpbml0ZSBsaW5lYXI7CglhbmltYXRpb246IHR3aXJsLXBob25lIDFzIGFsdGVybmF0ZSBpbmZpbml0ZSBsaW5lYXI7CgoJfQoKCS5kZWxheTEgewoJLXdlYmtpdC1hbmltYXRpb24tZGVsYXk6IDAuMzNzOwoJLW1vei1hbmltYXRpb24tZGVsYXk6IDAuMzNzOwoJLW1zLWFuaW1hdGlvbi1kZWxheTogMC4zM3M7Cgktby1hbmltYXRpb24tZGVsYXk6IDAuMzNzOwoJYW5pbWF0aW9uLWRlbGF5OiAwLjMzczsKCX0KCS5kZWxheTIgewoJLXdlYmtpdC1hbmltYXRpb24tZGVsYXk6IDAuNjZzOwoJLW1vei1hbmltYXRpb24tZGVsYXk6IDAuNjZzOwoJLW1zLWFuaW1hdGlvbi1kZWxheTogMC42NnM7Cgktby1hbmltYXRpb24tZGVsYXk6IDAuNjZzOwoJYW5pbWF0aW9uLWRlbGF5OiAwLjY2czsKCX0KCTwvc3R5bGU+Cgk8Y2lyY2xlIGN4PSI2MC4wMDEiIGN5PSI2MCIgZmlsbD0iIzM0OThEQiIgcj0iNTUuOTkzIiBpZD0iZmlsbC1iYWNrZ3JvdW5kIi8+Cgk8cGF0aCBkPSJNOTkuMjEyLDk5Ljk1NUw5My4wNSw3OC45NTNsLTMwLjY3OCwyLjQ0M0wzMy45OTMsNTkuMDA2bDE2LjQ3OCw1Ni4xNjJjMy4wOTgsMC41MzEsNi4yNzgsMC44MjQsOS41MywwLjgyNCAgQzc1LjI3MywxMTUuOTkyLDg5LjExMSwxMDkuODcxLDk5LjIxMiw5OS45NTV6IiBmaWxsPSIjMDAwMDAwIiBvcGFjaXR5PSIwLjA3IiAvPgoJPGcgaWQ9ImxiX3Bob25lLWhlYWQiPgoJCTxwYXRoIGQ9Ik05Mi44OTQsODEuMTk3Yy0wLjU1NywyLjU0My0zLjQ0Nyw4LjcwOC02LjE5OSw5LjM0M2MtMi4zMywwLjUzOS00Ljc1NCwwLjgxMi03LjIxOSwwLjgxMiAgIEM2Niw5MS4zMzIsNTEuMzExLDgzLjI5Nyw0MS4xMDksNzAuMzQ2QzMyLjE1OCw1OC45NTksMjguNTM3LDQ2LjA3LDMxLjIxMywzNC45OWMwLjY5Mi0yLjg3LDcuNjk5LTYuMzM4LDEwLjc2Mi02LjMzOCAgIGMxLjQyMywwLDIuMTk1LDAuNjE0LDIuNTk5LDEuMTM3YzAuNDI0LDAuNTU5LDcuODU1LDExLjg4OSw4LjI3NywxMi42MjJjMS42MTksMi43OTMtMS4xNzYsNC42NjEtMi44NjcsNS43OTcgICBjLTIuOTI4LDEuOTQ4LTUuNjc4LDMuNzk2LTMuMTM3LDkuMTUyYzAuMDM3LDAuMDgsMi44MDksNS45OTQsNy4yOTMsMTAuMjM0YzQuMzEzLDQuNTY2LDEwLjE4NSw3LjMwMywxMC4yNDMsNy4zNCAgIGMxLjM2OSwwLjYzNywyLjU0MSwwLjk2NSwzLjU0NSwwLjk2NWMyLjI1LDAsMy40MjYtMS41ODQsNC45ODItNC4wMWMxLjEzNS0xLjc3MSwyLjMxMS0zLjU4Miw0LjQzLTMuNTgyICAgYzAuNzExLDAsMS40MDYsMC4yMTEsMi4wNzgsMC42NTRjMC4xMTcsMC4wNzYsMTEuOTM4LDcuNzg1LDEyLjQ5NCw4LjIwOUM5Mi45NTMsNzcuOTYxLDkzLjI3OSw3OS4zMTEsOTIuODk0LDgxLjE5N3oiIGZpbGw9IiNGRkZGRkYiLz4KCTwvZz4KPC9zdmc+) no-repeat scroll center center transparent !important;
                background-size: contain !important;
                width: 80px;
                height: 80px;
                opacity: 0.5;
                z-index: 100000;
                cursor: pointer;

                visibility: hidden;

                -moz-transition-duration: 1s;
                -o-transition-duration: 1s;
                -webkit-transition-duration: 1s;
                transition-duration: 1s;
            }

            .lb_call-btn:hover {
                opacity: 1 !important;
            }
        </style>
        <div id="lb_button-call" style="position: relative; top: 0; left: 0;" data-toggle="modal"
             data-target="#myModal">
            <div class="lb_call-btn bigEntrance lb-pulse" style="opacity: 0.5;">
                <div class="phone-call_wave phone-call_wave__stroke"></div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header text-center">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 style="margin: 10px 0">У Вас остались вопросы?</h4>
                <h4 style="margin-bottom: 10px">Хотите, мы перезвоним вам?</h4>
            </div>
            <div class="modal-body">
                <div class="lb_form lb_pt-page-moveFromTopFade">
                    <div class="row">
                        <div class="input-field col-md-6 col-md-offset-1">
                            <i class="material-icons prefix">phone</i>
                            <input id="callBackPhone" type="tel" class="validate" placeholder="+">
                            <label for="callBackPhone">Телефон</label>
                        </div>
                        <div class="input-field col-md-4">
                            <button class="btn btn-default btn-bg" style="margin-top: 0px">Жду звонка!</button>
                            <div>Звонок бесплатный</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--<script>--%>
    <%--$("select[name=promotionId]").change(function () {--%>
<%--//        var re = /((^375(25|29|33|44|17)[\d]{7}$)|(^(8|7)\d{3}[\d]{7,10}))$/;--%>
<%--//        re.test(phone);--%>
        <%--var value = $("callBackPhone").val();--%>
        <%--if (value && value.length > 0) {--%>
            <%--var formData = new FormData();--%>
            <%--formData.append('productId', $('#productId').val());--%>
            <%--formData.append('promotionId', value);--%>
            <%--$.ajax('/company/cards/${card.cardUUID}/calculate-new-price', {--%>
                <%--method: "POST",--%>
                <%--data: formData,--%>
                <%--processData: false,--%>
                <%--contentType: false,--%>
                <%--success: function (data) {--%>
                    <%--$('#price').val(data);--%>
                    <%--$('#price').attr('readonly', 'readonly');--%>
                <%--},--%>
                <%--error: function (e) {--%>
                    <%--showError('Ошибка формирования цены со скидкой!')--%>
                <%--}--%>
            <%--});--%>
        <%--} else {--%>
            <%--$('#price').val(basePrice);--%>
            <%--$("#price").removeAttr('readonly');--%>
        <%--}--%>
    <%--});--%>
<%--</script>--%>