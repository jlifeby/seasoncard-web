
var scWidget = {
    id: '49600',
    href: 'http://localhost:8080/widget',
    lang: 'rus',
    isNew: '1',
    buttonPosition: 'bottom right',
    buttonColor: '#1c84c6',
    buttonAnimation: (1 == 1) ? true : false,
    formPosition: 'right',
    text: 'Онлайн запись',
    block: null,
    button: null,
    buttonAutoShow: (1 == 1) ? true : false,
    iFrame: null,
    cover: null,
    closeIcon: null,
    isMobile: {
        Android: function() {
            return navigator.userAgent.match(/Android/i);
        },
        BlackBerry: function() {
            return navigator.userAgent.match(/BlackBerry/i);
        },
        iOS: function() {
            return navigator.userAgent.match(/iPhone|iPod|iPad/i);
        },
        Opera: function() {
            return navigator.userAgent.match(/Opera Mini/i);
        },
        Windows: function() {
            return navigator.userAgent.match(/IEMobile/i);
        },
        any: function() {
            return (scWidget.isMobile.Android() || scWidget.isMobile.BlackBerry() || scWidget.isMobile.iOS() || scWidget.isMobile.Opera() || scWidget.isMobile.Windows() || (window.innerWidth <= 600));
        }
    },
    mobileAndTabletcheck : function() {
        var check = false;
        (function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))check = true})(navigator.userAgent||navigator.vendor||window.opera);
        return check;
    },
    mobilecheck : function() {
        var check = false;
        (function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))check = true})(navigator.userAgent||navigator.vendor||window.opera);
        return check;
    },
    mobileEvent: ('ontouchstart' in window) ? true : false,
    clickevent: function() {
        var event = scWidget.mobileAndTabletcheck() ? 'touchstart' : 'click';
        return event;
    },
    addCSS: function() {
        var ss = document.createElement('link');
        ss.type = "text/css";
        ss.rel = "stylesheet";
        if (scWidget.isNew > 0) {
            ss.href = scWidget.href + "/widget.css";
        } else {
            ss.href = scWidget.href + "css/scWidget/website.css";
        }
        document.getElementsByTagName('head')[0].appendChild(ss);
    },
    createButton: function() {
        var button = document.createElement('a'),
            zIndex = scWidget.getMaxZIndex(),
            wave = scWidget.createButtonWave(),
            text = scWidget.createButtonText(),
            icon = scWidget.createButtonIcon(),
            background = scWidget.createButtonBackground();
        button.className = 'scButton';
        button.href = '#';
        button.style.zIndex = zIndex + 1;

        var position = scWidget.buttonPosition.split(' ');
        scWidget.addClass(position[0], button);
        scWidget.addClass(position[1], button);

        button.appendChild(background);

        if (scWidget.buttonAnimation) {
            button.appendChild(wave);
        }

        button.appendChild(text);
        button.appendChild(icon);

        scWidget.addClickEventToButton(button);

        document.getElementsByTagName('body')[0].appendChild(button);
        return button;
    },
    createButtonBackground: function() {
        var background = document.createElement('div');
        background.className = 'scButtonBackground';
        background.style.backgroundColor = scWidget.buttonColor;
        return background;
    },
    createButtonWave: function() {
        var wave = document.createElement('div');
        wave.className = 'scButtonWave';
        wave.style.borderColor = scWidget.buttonColor;
        wave.style.color = scWidget.buttonColor;
        return wave;
    },
    createButtonText: function() {
        var text = document.createElement('div');
        text.className = 'scButtonText';
        text.innerHTML = scWidget.text;
        return text;
    },
    createButtonIcon: function() {
        var icon = document.createElement('div');
        icon.className = 'scButtonIcon';
        return icon;
    },
    createWidgetBlock: function() {
        var block = document.createElement('div'),
            zIndex = scWidget.getMaxZIndex('body');
        block.className = 'scWidgetBlock';
        block.style.zIndex = zIndex + 1;
        scWidget.addClass(scWidget.formPosition, block);
        document.getElementsByTagName('body')[0].appendChild(block);
        scWidget.iFrame = scWidget.createIFrame();
        block.appendChild(scWidget.iFrame);
        return block;
    },
    createWindowCover: function() {
        var cover = document.createElement('div'),
            zIndex = scWidget.getMaxZIndex('body');
        cover.className = 'scWidgetCover';
        cover.style.zIndex = zIndex + 1;
        cover.addEventListener('click', function(e) {
            e.preventDefault();
            scWidget.hide();
        }, false);
        document.getElementsByTagName('body')[0].appendChild(cover);
        return cover;
    },
    createIFrame: function() {
        var iFrame = document.createElement('iframe');
        iFrame.className = 'scWidgetIFrame';
        iFrame.setAttribute('frameborder', 0);
        iFrame.setAttribute('allowtransparency', 'true');
        iFrame.src = scWidget.href;
        return iFrame;
    },
    createCloseIcon: function() {
        var button = document.createElement('a'),
            zIndex = scWidget.getMaxZIndex();
        button.className = 'scCloseIcon';
        button.href = '#';
        button.style.zIndex = zIndex + 1;
        scWidget.addClass(scWidget.formPosition, button);
        button.addEventListener(scWidget.click, function(e) {
            e.preventDefault();
            scWidget.hide();
        }, false);
        document.getElementsByTagName('body')[0].appendChild(button);
        return button;
    },
    fixWindowScroll: function(type) {
        if (type == 'hidden') {
            scWidget.addClass('scBodyOverflowHidden', document.getElementsByTagName('body')[0]);
        } else {
            scWidget.removeClass('scBodyOverflowHidden', document.getElementsByTagName('body')[0]);
        }
    },
    setConfing: function() {

        if (typeof scWidgetSettings != 'undefined') {
            if (typeof scWidgetSettings.buttonAutoShow != 'undefined') {
                scWidget.buttonAutoShow = scWidgetSettings.buttonAutoShow;
            }
            if (typeof scWidgetSettings.buttonColor != 'undefined') {
                scWidget.buttonColor = scWidgetSettings.buttonColor;
            }
            if (typeof scWidgetSettings.buttonPosition != 'undefined') {
                scWidget.buttonPosition = scWidgetSettings.buttonPosition;
            }
            if (typeof scWidgetSettings.buttonAnimation != 'undefined') {
                scWidget.buttonAnimation = scWidgetSettings.buttonAnimation;
            }
            if (typeof scWidgetSettings.formPosition != 'undefined') {
                scWidget.formPosition = scWidgetSettings.formPosition;
            }
            if (typeof scWidgetSettings.buttonText != 'undefined') {
                scWidget.text = scWidgetSettings.buttonText;
            }
        }

    },
    setButtons: function() {
        var buttons = document.getElementsByClassName('ms-button');
        var old_buttons = document.getElementsByClassName('ms_booking');

        for (index = 0; index < buttons.length; ++index) {
            var button = buttons[index];
            scWidget.addClickEventToButton(button);
        }
        for (index = 0; index < old_buttons.length; ++index) {
            var old_button = old_buttons[index];
            scWidget.addClickEventToButton(old_button);
        }
    },
    addClickEventToButton: function(button) {
        button.addEventListener(scWidget.click, function(e) {
            e.preventDefault();

            if (typeof this.dataset.url != 'undefined') {
                var url = this.dataset.url;
            } else {
                var url = scWidget.href;
            }

            if (scWidget.isNew > 0) {
                url = url.replace('://w', '://n');
            }

            scWidget.show(url);
        }, false);
    },

    init: function() {

        scWidget.setConfing();
        scWidget.addCSS();
        scWidget.click = scWidget.clickevent();
        scWidget.setButtons();

        if (scWidget.buttonAutoShow) {
            scWidget.button = scWidget.createButton();
        }

        scWidget.cover = scWidget.createWindowCover();
        scWidget.closeIcon = scWidget.createCloseIcon();

    },

    show: function(url) {
        if (scWidget.isMobile.any()) {
            var str = '';
            //var str = '?from='+encode64(window.location.href);
            location.href = url;
            return false;
        }

        if (scWidget.block == null) {
            scWidget.block = scWidget.createWidgetBlock();
        }

        scWidget.removeClass('scWidgetHide', scWidget.block);
        scWidget.addClass('scWidgetShow', scWidget.block);
        scWidget.cover.style.display = 'block';
        scWidget.closeIcon.style.display = 'block';
        scWidget.fixWindowScroll('hidden');
        if (scWidget.iFrame.src != url) {
            scWidget.iFrame.src = '';
            setTimeout(function() {
                scWidget.iFrame.src = url;
            }, 250);
        }
        return false;
    },
    hide: function() {
        scWidget.removeClass('scWidgetShow', scWidget.block);
        scWidget.addClass('scWidgetHide', scWidget.block);
        scWidget.cover.style.display = 'none';
        scWidget.closeIcon.style.display = 'none';
        scWidget.fixWindowScroll('auto');
    },
    getMaxZIndex: function() {
        var zIndex, z = 0,
            all = document.getElementsByTagName('*');
        for (var i = 0, n = all.length; i < n; i++) {
            zIndex = document.defaultView.getComputedStyle(all[i], null).getPropertyValue("z-index");
            zIndex = parseInt(zIndex, 10);
            z = (zIndex) ? Math.max(z, zIndex) : z;
        }
        if (z < 9999) {
            z = 9999;
        }
        return z;
    },
    addClass: function(classname, element) {
        var cn = element.className;
        if (cn.indexOf(classname) != -1) {
            return;
        }
        if (cn != '') {
            classname = ' ' + classname;
        }
        element.className = cn + classname;
    },
    removeClass: function(classname, element) {
        var cn = element.className;
        var rxp = new RegExp("\\s?\\b" + classname + "\\b", "g");
        cn = cn.replace(rxp, '');
        element.className = cn;
    }
};
document.addEventListener('DOMContentLoaded', scWidget.init(), false);
