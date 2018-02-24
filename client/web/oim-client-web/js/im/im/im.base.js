function HandlerData() {
    var o = this;
    o.message = {};
    o.backExecute = function () {
    }; //信息发送后都回调
    o.lostExecute = function () {
    };//发送失败执行
    o.timeOutExecute = function () {
    };//发送超时执行
    o.sendTime = 0;
}

function MessageHandler() {
    var own = this;
    own.timeOut = 6000;
    own.dataMap = new Map();
    own.putHandlerData = function (key, message, backExecute, lostExecute, timeOutExecute, sendTime) {
        if (key) {
            var handlerData = new HandlerData();
            handlerData.sendTime = sendTime || new Date().getTime();
            handlerData.message = message;
            handlerData.backExecute = backExecute;
            handlerData.lostExecute = lostExecute;
            handlerData.timeOutExecute = timeOutExecute;
            own.dataMap.put(key, handlerData);
        }
    };
    own.backMessage = function (key, message) {
        var object = own.dataMap.get(key);
        if (object) {
            var backExecute = object.backExecute; //信息发送后都回调
            own.dataMap.remove(key);
            if (typeof (backExecute) == "function") {
                backExecute(message);
            }
        }
    };
    own.handlerTimeOut = function () {
        var timeOut = own.timeOut;
        var keySet = own.dataMap.keySet();
        for (var key in keySet) {
            var timestamp = new Date().getTime();
            var object = own.dataMap.get(key);
            if (object) {
                var message = object.message;
                var sendTime = object.sendTime;
                //var backExecute = object.backExecute; //信息发送后都回调
                //var lostExecute = object.lostExecute;//发送失败执行
                var timeOutExecute = object.timeOutExecute;//发送超时执行
                if (timestamp - sendTime >= timeOut) {
                    own.dataMap.remove(key);
                    if (typeof (timeOutExecute) == "function") {
                        timeOutExecute(message);
                    }
                }
            }
        }
    };
    own.initialize = function () {
        own.timer = window.setInterval(own.handlerTimeOut, 2000);
    };
    own.initialize();
}

function Head() {
    this.key = "";
    this.name = "";
    this.clientVersion = "";
    this.clientType = "";
    this.action = "";
    this.method = "";
    this.version = "";
    this.resultCode = "";
    this.resultMessage = "";
    this.time = 0;
}

function Message() {
    this.head = {};
    this.body = {};
}

function getMessage(action, method) {
    var timestamp = new Date().getTime();
    var key = uuid();
    var m = new Message();
    var h = new Head();
    h.clientType = "1";
    h.clientVersion = "2.0";
    h.time = timestamp;
    h.key = key;
    h.action = action;
    h.method = method;
    m.head = h;
    return m;
}


function getTimestamp() {
    var timestamp = new Date().getTime();
    return timestamp;
}

function uuid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}


function Task() {
    var o = this;
    o.list = [];
    o.push = function (task) {
        o.list.push(task);
    };
    o.start = function () {
        var length = o.list.length;
        if (length > 0) {
            for (var i = 0; i < length; i++) {
                var object = o.list[i];
                if (typeof (object) == "function") {
                    object();
                }
            }
            o.list = [];
        }
    };
}


function getDefaultErrorText(info) {
    var text = "";
    if (!isEmpty(info)) {
        var warnings = info.warnings;
        var errors = info.errors;
        if (warnings && warnings.length > 0) {
            for (var i = 0; i < warnings.length; i++) {
                text = text + warnings[i].text + "\n";
            }
        } else if (errors && errors.length > 0) {
            for (var i = 0; i < errors.length; i++) {
                text = text + errors[i].text + "\n";
            }
        }
    }
    return text;
}


function getContentText(content) {
    var text = "";
    if (!isEmpty(content) && !isEmpty(content.sections)) {
        var sections = content.sections;
        var sectionsLength = sections.length;
        for (var i = 0; i < sectionsLength; i++) {
            var section = sections[i];
            if (section.items) {
                var items = section.items;
                var itemsLength = items.length;
                for (var j = 0; j < itemsLength; j++) {
                    var item = items[j];
                    var type = item.type;
                    var value = item.value;

                    if ("text" == type) {
                        text += value;
                    }

                    if ("image" == type) {
                        text += "[图片]";
                    }

                    if ("face" == type) {
                        text += "[表情]";
                    }
                }
            }
        }
    }
    return text;
}

