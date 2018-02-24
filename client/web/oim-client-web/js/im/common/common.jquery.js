/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$.fn.insertText = function(text) {

    this.each(function() {

        if (this.tagName !== 'INPUT' && this.tagName !== 'TEXTAREA') {
            return;
        }
        if (document.selection) {
            this.focus();
            var cr = document.selection.createRange();
            cr.text = text;
            cr.collapse();
            cr.select();
        } else if (this.selectionStart !== undefined) {
            var start = this.selectionStart;
            var end = this.selectionEnd;
            this.value = this.value.substring(0, start) + text + this.value.substring(end, this.value.length);
            this.selectionStart = this.selectionEnd = start + text.length;
        } else {
            this.value += text;
        }
    });

    return this;
}

jQuery.fn.maxLength = function (max) {
    this.each(function () {
        var type = this.tagName.toLowerCase();
        var inputType = this.type ? this.type.toLowerCase() : null;
        if (type == "input" && inputType == "text" || inputType == "password") {
            //Apply the standard maxLength
            this.maxLength = max;
        } else if (type == "textarea") {
            this.onkeypress = function (e) {
                var ob = e || event;
                var keyCode = ob.keyCode;
                var hasSelection = document.selection ? document.selection.createRange().text.length > 0 : this.selectionStart != this.selectionEnd;
                return !(this.value.length >= max && (keyCode > 50 || keyCode == 32 || keyCode == 0 || keyCode == 13) && !ob.ctrlKey && !ob.altKey && !hasSelection);
            };
            this.onkeyup = function () {
                if (this.value.length > max) {
                    this.value = this.value.substring(0, max);
                }
            };
        }
    });
};
$.fn.getHexBackgroundColor = function () {
    var rgb = $(this).css('background-color');
    if (rgb) {
        if (!$.browser.msie) {
            rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);

            function hex(x) {
                return ("0" + parseInt(x).toString(16)).slice(-2);
            }

            if (rgb && rgb.length > 0) {
                rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
            } else {
                rgb = "";
            }
        }
    }
    return rgb;
};

/**
 * 异步处理请求
 * @param {type} url 请求路径
 * @param {type} data 请求数据
 * @param {type} success 请求成功后要处理的事情
 * @param {type} ajaxType 请求方式，如果设置为get方式则不会去验证
 * @returns {undefined}
 */
function doAjax(url, data, success, ajaxType) {
    doOnlyAjax(url, data, success, ajaxType, true);
}

/**
 * 异步处理请求
 * @param {type} url
 * @param {type} data
 * @param {type} success
 * @param {type} ajaxType
 * @param {type} sync
 * @returns {undefined}
 */
function doOnlyAjax(url, data, success, ajaxType, sync) {
    var param = getURLParameter(url, data);
    url = url.split("?")[0];
    $.ajax({
        type: (ajaxType == null || ajaxType == undefined) ? "POST" : ajaxType, //ajaxType||"POST",//使用get方法访问后台
        dataType: "json", //返回json格式的数据
        url: url, //要访问的后台地址
        data: param, //要发送的数据
        complete: null,
        async: sync,
        success: success
    });
}

/**
 * 跨域请求
 * @param {type} url
 * @param {type} data
 * @param {type} success
 * @param {type} ajaxType
 * @returns {undefined}
 */
function doCrossAjax(url, data, success, ajaxType) {
    doOnlyCrossAjax(url, data, success, ajaxType, true);
}

/**
 * 跨域请求
 * @param {type} url
 * @param {type} data
 * @param {type} success
 * @param {type} ajaxType
 * @param {type} sync
 * @returns {undefined}
 */
function doOnlyCrossAjax(url, data, success, ajaxType, sync) {
    var param = getURLParameter(url, data);
    url = url.split("?")[0];
    $.ajax({
        type: (ajaxType == null || ajaxType == undefined) ? "POST" : ajaxType, //ajaxType||"POST",//使用get方法访问后台
        url: url,
        async: sync,
        dataType: 'jsonp',
        jsonp: 'callback',
        data: param,
        success: success//请求成功后的回调函数有两个参数
    });
}




/**
 * 将url中的参数与传递进来的对象param进行合并，并返回合并后的对象
 * @param url  url
 * @param param   data
 * @return
 */
function getURLParameter(url, param) {
    if (typeof (param) == "string" && !isEmpty(param)) {
        if (url.indexOf("?") == -1)
            url += "?";
        else {
            url += "&";
        }
        url += param;
        url = url.replace("&&", "&");
        url = url.replace("??", "?");
        url = url.replace("?&", "?");
        param = {};
    }
    if (url.indexOf("?") == -1) {
        return param || {};
    }
    var parameter;
    var data = {};
    parameter = url.split("?")[1];
    var arr = parameter.split("&");
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].indexOf("=") != -1) {
            data[arr[i].split("=")[0]] = arr[i].split("=")[1];
        }
    }
    if (null == param) {
        param = {};
    }
    return $.extend(param, data);
}