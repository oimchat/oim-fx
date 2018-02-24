function Cache() {
    var hasCache=(!isEmpty(window)&&!isEmpty(window.localStorage))
    var own = this;
    own.key = "chat-cache";
    own.map = new Map();
    own.isCache = hasCache;
    own.get = function (key) {
        var object;
        var saveKey = own.key + key;
        if (own.isCache) {
            var storageString = localStorage.getItem(saveKey);
            if (!isEmpty(storageString)) {
                object = JSON.parse(storageString);//转成json
            }
        } else {
            object = own.map.get(saveKey);
        }
        return object;
    };
    own.put = function (key, value) {
        var saveKey = own.key + key;
        if (!isEmpty(value)) {
            if (own.isCache) {
                if (typeof (value) == "string") {

                }
                if (typeof (value) == "number") {

                }
                if (typeof (value) == "boolean") {

                }
                var text = JSON.stringify(value);
                localStorage.setItem(saveKey, text);
            } else {
                own.map.put(saveKey, value);
            }
        }
    };
    own.remove = function (key) {
        var saveKey = own.key + key;
        localStorage.removeItem(saveKey);
        own.map.remove(saveKey);
    };
}



function Session() {
    var own = this;
    own.key = "chat-cache";
    own.map = new Map();
    own.isCache = !isEmpty(window.sessionStorage);
    own.get = function (key) {
        var object;
        var saveKey = own.key + key;
        if (own.isCache) {
            var storageString = sessionStorage.getItem(saveKey);
            if (!isEmpty(storageString)) {
                object = JSON.parse(storageString);//转成json
            }
        } else {
            object = own.map.get(saveKey);
        }
        return object;
    };
    own.put = function (key, value) {
        var saveKey = own.key + key;
        if (!isEmpty(value)) {
            if (own.isCache) {
                if (typeof (value) == "string") {

                }
                if (typeof (value) == "number") {

                }
                if (typeof (value) == "boolean") {

                }
                var text = JSON.stringify(value);
                sessionStorage.setItem(saveKey, text);
            } else {
                own.map.put(saveKey, value);
            }
        }
    };
    own.remove = function (key) {
        var saveKey = own.key + key;
        sessionStorage.removeItem(saveKey);
        own.map.remove(saveKey);
    };
}

function MessageStorage() {
    var own = this;
    own.key = "chat-history";
    own.map = new Map();
    own.isCache = !isEmpty(window.localStorage);
    own.isSessionStorage = true;
    own.getList = function (key) {
        var array = [];
        if (own.isCache) {

            var storageString = "";
            if (own.isSessionStorage) {
                storageString = sessionStorage.getItem(own.key + key);//游客，当前会话有效
            } else {
                storageString = localStorage.getItem(own.key + key);//登录用户，本地存储，每次初始化时比较时间，过期清除
            }
            if (isEmpty(storageString)) {
                array = [];
            } else {
                var arrayTemp = JSON.parse(storageString);//转成json

                var length = arrayTemp.length;
                var start = 0;

                for (var i = start; i < length; i++) {
                    var temp = arrayTemp[i];
                    array.push(temp);
                }
            }
        } else {
            var arrayTemp = own.map.get(key);
            if (!isEmpty(arrayTemp)) {
                array = arrayTemp;
            }
        }
        return array;
    };
    own.add = function (key, value) {
        if (own.isCache) {
            var array = [];
            var storageString = "";
            if (own.isSessionStorage) {
                storageString = sessionStorage.getItem(own.key + key);//游客，当前会话有效
            } else {
                storageString = localStorage.getItem(own.key + key);//登录用户，本地存储，每次初始化时比较时间，过期清除
            }
            if (isEmpty(storageString)) {

            } else {
                var arrayTemp = JSON.parse(storageString);//转成json

                var length = arrayTemp.length;
                var start = 0;
                if (length > 100) {
                    start = length - 100;
                }
                for (var i = start; i < length; i++) {
                    var temp = arrayTemp[i];
                    array.push(temp);
                }
            }
            array.push(value);
            var text = JSON.stringify(array);
            if (own.isSessionStorage) {
                sessionStorage.setItem(own.key + key, text);//游客，当前会话有效
            } else {
                localStorage.setItem(own.key + key, text);//登录用户，本地存储，每次初始化时比较时间，过期清除
            }
        } else {
            var array = [];
            var arrayTemp = own.map.get(key);
            if (!isEmpty(arrayTemp)) {
                var length = arrayTemp.length;
                var start = 0;
                if (length > 100) {
                    start = length - 100;
                }
                for (var i = start; i < length; i++) {
                    var temp = arrayTemp[i];
                    array.push(temp);
                }
            }
            array.push(value);
            own.map.put(key, array);
        }
    };

    own.setIsSessionStorage = function (value) {
        own.isSessionStorage = value;
    };
    own.init = function () {
        if (window.localStorage) {
            var storageTime = new Date();//storage消息过期时间	
            var saveTime = localStorage.getItem("historyTime");
            var time = 0;
            if (saveTime != null && saveTime != "") {
                time = parseInt(saveTime);
            }
            if (storageTime.getTime() > time) {//已过期
                //设置localstorage过期时间为当天23:59:59
                storageTime.setHours(23);
                storageTime.setMinutes(59);
                storageTime.setSeconds(59);
                for (var i = 0; i < localStorage.length; i++) {
                    var key = localStorage.key(i);
                    if (key.indexOf(own.key) == 0) {//删除聊天记录消息
                        localStorage.removeItem(key);
                    }
                }
                localStorage.setItem("historyTime", storageTime.getTime());
            }
        }
    };
    own.init();
}