/******************************************************************************
 * map start                                                                  *
 ******************************************************************************/
function Map() {
    this.container = new Object();
}

Map.prototype.put = function (key, value) {
    this.container[key] = value;
};

Map.prototype.get = function (key) {
    return this.container[key];
};

Map.prototype.has = function (key) {
    return (key in this.container);
};

Map.prototype.containsKey = function (key) {
    return (key in this.container);
};

Map.prototype.keySet = function () {
    var keyset = new Array();
    var count = 0;
    for (var key in this.container) {
        if (key == 'extend') {// 跳过object的extend函数
            continue;
        }
        keyset[count] = key;
        count++;
    }
    return keyset;
};

Map.prototype.size = function () {
    var count = 0;
    for (var key in this.container) {
        if (key == 'extend') {// 跳过object的extend函数
            continue;
        }
        count++;
    }
    return count;
};

Map.prototype.remove = function (key) {
    var data = this.container[key];
    delete this.container[key];
    return data;
};

Map.prototype.clear = function () {
    for (var key in this.container) {
        if (key == 'extend') {// 跳过object的extend函数
            continue;
        }
        delete this.container[key];
    }
};

Map.prototype.values = function () {
    var values = new Array();
    var count = 0;
    for (var key in this.container) {
        if (key == 'extend') {// 跳过object的extend函数
            continue;
        }
        values[count] = this.container[key];
        count++;
    }
    return values;
};


Map.prototype.toString = function () {
    var text = "";
    for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
        text = text + keys[i] + "=" + this.container[keys[i]] + ";\n";
    }
    return text;
};

/******************************************************************************
 * map end                                                                    *
 ******************************************************************************/


/******************************************************************************
 * String start                                                               *
 ******************************************************************************/
String.prototype.endWith = function (s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length) {
        return false;
    }
    if (this.substring(this.length - s.length) == s) {
        return true;
    } else {
        return false;
    }
    return true;
};

String.prototype.startWith = function (s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length) {
        return false;
    }
    if (this.substring(0, s.length) == s) {
        return true;
    } else {
        return false;
    }
    return true;
};


String.prototype.trim = function () {
    return (this + "").replace(/(^\s*)|(\s*$)/g, '');
};


/**
 * 用于页面显示字数过多而只显示部分内容+..代替
 * @param {type} text
 * @param {type} size
 * @returns {String}
 */
function moreFilter(text, size) {
    var value = "";
    if (isEmpty(text))
        return value;
    if (text.length > size) {
        value = text.slice(0, size);
        value += '...';
        return value;
    } else {
        return text;
    }
}

function moreUtf8Filter(text, size) {
    var value = "";
    if (isEmpty(text))
        return value;
    if (lengthByUtf8(text) > size) {
        value = substringByUtf8(text, 0, size);
        value += '...';
        return value;
    } else {
        return text;
    }
}

/**
 * 
 * @param {type} text
 * @returns {String}
 */
function trim(text) {
    var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
    return text == null ? "" : (text + "").replace(rtrim, "");
}

/**
 * 获取字符utf8长度，中文按2字节算
 * @param text
 * @returns {Number}
 */
function lengthByUtf8(text) {
    var realLength = 0;
    if (text) {
        var length = text.length;
        var charCode = -1;
        for (var i = 0; i < length; i++) {
            charCode = text.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128) {
                realLength += 1;
            } else {
                realLength += 2;
            }
        }
    }
    return realLength;
}

/**
 * 按utf8格式截取字符，英文数字按1字符算，汉字按2算
 * @param text 原字符
 * @param start 开始位置
 * @param length 截字数
 */
function substringByUtf8(text, start, length) {
    //定位开始位置
    var startUtf8 = 0;
    var charCode = -1;
    var i = 0;
    var end = start + length;
    while (start > 0) {
        charCode = text.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) {//健盘字符，每次-1
            start -= 1;
        } else {//非健盘字符，每次-2
            start -= 2;
        }
        i++;
        startUtf8++;
    }

    //定位给束位置
    var endUtf8 = 0;
    while (end > start) {
        charCode = text.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) {//健盘字符，每次-1
            end -= 1;
        } else {//非健盘字符，每次-2
            end -= 2;
        }
        i++;
        endUtf8++;
    }

    startUtf8 = parseInt(startUtf8);
    endUtf8 = parseInt(endUtf8);
    return text.substring(startUtf8, endUtf8);
}


/**
 * 去除html标签
 * @param text
 * @returns
 */
function removeHTMLTag(text) {
    text = text.replace(/<\/?[^>]*>/g, ''); //去除HTML tag
    text = text.replace(/(^\s*)|(\s*$)/g, "");
    text = text.replace(/(^\s*)/g, "");
    text = text.replace(/(\s*$)/g, "");
    text = text.replace(/ /ig, '');//去掉 
    return text;
}

function replaceToHtml(text) {
    var type = typeof (text);
    if (!isEmpty(text) && "string" == type) {
        var value = "";
        for (var i = 0; i < text.length; i++) {
            var c = text[i].charCodeAt();
            if (text[i] == "&") {
                value += text[i];
                continue;
            }
            if (c == 38) {
                value += "&amp;";
            } else if (c == 60) {
                value += "&lt;";
            } else if (c == 62) {
                value += "&gt;";
            } else if (c == 34) {
                value += "&#034;";
            } else if (c == 39) {
                value += "&#039;";
            } else {
                value += text[i];
            }
        }
        return value;
    }
}

function htmlEncode(text) {
    var value = "";
    if (!isEmpty(text)) {
        value = text.replace("&", "&amp;");
        value = value.replace("\t", "&nbsp;&nbsp;");// 替换跳格
        value = value.replace("<", "&lt;");
        value = value.replace(">", "&gt;");
        value = value.replace(" ", "&nbsp;");
        value = value.replace("\'", "&#39;");
        value = value.replace("\"", "&quot;");
        value = value.replace("\n", "<br>");
    }
    return value;
}
/******************************************************************************
 * String end                                                                 *
 ******************************************************************************/


/******************************************************************************
 * Date start                                                                 *
 ******************************************************************************/

/**
 * 
 * @param {type} value
 * @returns {undefined}
 */
Date.prototype.AddDays = function (value) {
    this.setDate(this.getDate() + value);
};

// 增加月 
Date.prototype.AddMonths = function (value) {
    this.setMonth(this.getMonth() + value);
};

// 增加年 
Date.prototype.AddYears = function (value) {
    this.setFullYear(this.getFullYear() + value);
};


/**
 * 格式化日期
 * @param format 例如：new Date().format(new Date().format("yyyy-MM-dd"))
 * @return
 */

/**
 * 对Date的扩展，将 Date 转化为指定格式的String 
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 * 例子： 
 * (new Date()).format("yyyy-MM-dd HH:mm:ss.SSS") ==> 2006-07-02 08:09:04.423 
 * (new Date()).format("yyyy-M-d H:m:s.S")      ==> 2006-7-2 8:9:4.18
 * @param format
 * @return
 */
Date.prototype.format = function (format) {
    /*
     * format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substring(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substring(("" + o[k]).length));
        }
    }
    return format;
};

/* 
 * 获取当前时间
 * text：时间格式
 */
function toDate(text) {
    if (!text) {
        return "";
    }
    text = text.trim();
    text = text.split(' ');
    var date = new Date();
    if (text.length == 2) {
        var yymmdd = text[0].split('-');
        var hhmmss = text[1].split(':');
        date.setFullYear(yymmdd[0], yymmdd[1] - 1, yymmdd[2]);
        date.setHours(hhmmss[0], hhmmss[1], 0, 0);
    } else {
        text = text[0].split('-');
        date.setFullYear(text[0], text[1] - 1, text[2]);
        date.setHours(0, 0, 0, 0);
    }
    return date;
}
/* 
 * 字符串转换为时间格式
 * dateValue：时间字符串 
 */
function TimeData(dateValue) {
    var date;
    if (dateValue == "") {
        date = new Date();
    } else {
        date = toDate(dateValue);
    }
    this.year = date.getYear();
    this.month = date.getMonth() + 1;
    this.day = date.getDate();
    this.hour = date.getHours();
    this.minute = date.getMinutes();
    this.second = date.getSeconds();
    this.msecond = date.getMilliseconds();
    this.week = date.getDay();
}

/* 
 * 计算时间差
 * interval：返回精度为 s/second（秒），min/minute（分），h/hour（小时），d/day（天），m/month(月) 
 * date1：结束时间
 * date2：起始时间
 */
function getDateInterval(interval, date1, date2) {
    var TimeCom1 = new TimeData(date1);
    var TimeCom2 = new TimeData(date2);
    var result;
    switch (String(interval).toLowerCase()) {
        case "y":
        case "year":
            result = TimeCom1.year - TimeCom2.year;
            break;
        case "m":
        case "month":
            result = (TimeCom1.year - TimeCom2.year) * 12 + (TimeCom1.month - TimeCom2.month);
            break;
        case "d":
        case "day":
            result = Math.round((Date.UTC(TimeCom1.year, TimeCom1.month - 1, TimeCom1.day) - Date.UTC(TimeCom2.year, TimeCom2.month - 1, TimeCom2.day)) / (1000 * 60 * 60 * 24));
            break;
        case "h":
        case "hour":
            result = Math.round((Date.UTC(TimeCom1.year, TimeCom1.month - 1, TimeCom1.day, TimeCom1.hour) - Date.UTC(TimeCom2.year, TimeCom2.month - 1, TimeCom2.day, TimeCom2.hour)) / (1000 * 60 * 60));
            break;
        case "min":
        case "minute":
            result = Math.round((Date.UTC(TimeCom1.year, TimeCom1.month - 1, TimeCom1.day, TimeCom1.hour, TimeCom1.minute) - Date.UTC(TimeCom2.year, TimeCom2.month - 1, TimeCom2.day, TimeCom2.hour, TimeCom2.minute)) / (1000 * 60));
            break;
        case "s":
        case "second":
            result = Math.round((Date.UTC(TimeCom1.year, TimeCom1.month - 1, TimeCom1.day, TimeCom1.hour, TimeCom1.minute, TimeCom1.second) - Date.UTC(TimeCom2.year, TimeCom2.month - 1, TimeCom2.day, TimeCom2.hour, TimeCom2.minute, TimeCom2.second)) / 1000);
            break;
        case "ms":
        case "msecond":
            result = Date.UTC(TimeCom1.year, TimeCom1.month - 1, TimeCom1.day, TimeCom1.hour, TimeCom1.minute, TimeCom1.second, TimeCom1.msecond) - Date.UTC(TimeCom2.year, TimeCom2.month - 1, TimeCom2.day, TimeCom2.hour, TimeCom2.minute, TimeCom2.second, TimeCom1.msecond);
            break;
        case "w":
        case "week":
            result = Math.round((Date.UTC(TimeCom1.year, TimeCom1.month - 1, TimeCom1.day) - Date.UTC(TimeCom2.year, TimeCom2.month - 1, TimeCom2.day)) / (1000 * 60 * 60 * 24)) % 7;
            break;
        default:
            result = "invalid";
    }
    return (result);
}


/******************************************************************************
 * Date end                                                                 *
 ******************************************************************************/




/******************************************************************************
 * 数组相关方法start                                                                *
 ******************************************************************************/


Array.prototype.in_array = function (e) {
    var index = this.getIndexByProperty("", e);
    if (index > -1) {
        return true;
    }
    return false;
};

Array.prototype.in_arrayById = function (e) {
    var index = this.getIndexByProperty("id", e);
    if (index > -1) {
        return true;
    }
    return false;
};

Array.prototype.remove = function (e) {
    var index = this.getIndexByProperty("", e);
    if (index > -1)
        this.splice(index, 1);
};

Array.prototype.removeByProperty = function (property, value) {
    var index = this.getIndexByProperty(property, value);
    if (index > -1)
        this.splice(index, 1);
};


Array.prototype.getIndexByProperty = function (property, value) {
    for (var i = 0; i < this.length; i++) {
        if (property) {
            if ((this[i]) && this[i][property] == value)
                return i;
        } else {
            if (this[i] == value)
                return i;
        }
    }
    return -1;
};

Array.prototype.getObjectsByProperty = function (property, value) {
    var temp = [];
    for (var i = 0; i < this.length; i++) {
        if (property) {
            if ((this[i]) && this[i][property] == value) {
                temp.push(this[i]);
            }
        } else {
            if (this[i] == value) {
                temp.push(this[i]);
            }
        }
    }
    return temp;
};

Array.prototype.getObjectByProperty = function (property, value) {
    var index = this.getIndexByProperty(property, value);
    if (index == -1) {
        return undefined;
    }
    return this[index];
};

/**
 * 是否为数组
 * @param {type} value
 * @returns {Array}
 */
function isArray(value) {
    return (value instanceof Array);
}

/******************************************************************************
 * 数组相关方法end                                                               *
 ******************************************************************************/


/******************************************************************************
 * json和对象相关方法start                                                      *
 ******************************************************************************/

/**
 * 对象转成json字符串
 * @param {type} value
 * @returns {String}
 */
function objectToJson(value) {
    if (isEmpty(value)) {
        return "";
    }
    var json = JSON.stringify(value);
    return json;//$.toJSON(value);
}

/**
 * 将json字符串转成json对象
 * @param {type} json
 * @returns {undefined|Function}
 */
function jsonToObject(json) {
    if (isEmpty(json)) {
        return undefined;
    }
    try {
        var object = (new Function("return " + json))();
        return object;//$.evalJSON(json);
    } catch (e) {
        return json;
    }
}

/******************************************************************************
 * json和对象相关方法end                                                       *
 ******************************************************************************/


/**
 * 判断对象是否是为空，为空返回true，否则返回false
 * @param {type} value
 * @returns {Boolean}
 */
function isEmpty(value) {
    var isEmpty = false;
    if (value instanceof Array) {
        isEmpty = value.length <= 0;
    } else {
        isEmpty = trim(value) == "" || value == undefined || value == null || value == "undefined" || value == "null" || value == "&nbsp;";
    }
    return isEmpty;
}

/**
 * 获取浏览器类型
 * @returns {String}
 */
function getBrowserType() {
    //2014-10-29这里兼容不了IE11的高度，这里特此进行修改
    //if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
        return "ie";
    } else if (navigator.userAgent.indexOf('Firefox') >= 0) {
        return "firefox";
    } else if (navigator.userAgent.indexOf('Chrome') >= 0) {
        return "chrome";
    } else if (navigator.userAgent.indexOf('Opera') >= 0) {
        return "opera";
    } else {
        return "undefined";
    }
}

/**
 * 地址添加前缀
 * @param {type} prefix
 * @param {type} url
 * @returns {unresolved}
 */
function addPrefixForURL(prefix, url) {
    if (!isEmpty(url) && url.indexOf(prefix) != -1) {
        return url;
    }
    return prefix + url;
}

/**
 * 弹出浏览器的窗口
 * @param {type} url
 * @param {type} name
 * @param {type} width
 * @param {type} height
 * @returns {Window|Number|screen.width}
 */
function openWindow(url, name, width, height) {
    var top = 0;
    var left = 0;
    var w = screen.width;
    var h = screen.height;
    if (width === '') {
        width = w;
    } else if (width < w) {
        left = (w - width) / 2;
    }
    if (height === '') {
        height = h;
    } else if (height < h) {
        top = (h - height) / 2;
    }
    //  window.open('page.html', 'newwindow', 'height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
    var w = window.open(url, name, 'height=' + height + 'px,width=' + width + 'px,top=' + top + 'px,left=' + left + 'px,resizable=yes,toolbar:0,menubar:0,scrollbars=1');
    return w;
}
