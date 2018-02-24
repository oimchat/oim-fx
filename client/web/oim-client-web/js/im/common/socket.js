/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//webSocket.readyState 状态官方说明：
//0 connecting 连接尚未建立
//1 open WebSocket的链接已经建立
//2 closing 连接正在关闭
//3 closed 连接已经关闭或不可用
function NetSocket(onMessage, onOpen, onClose, onError) {
    var own=this;
    own.socket;
    own.onMessage = onMessage || function () {};
    own.onOpen = onOpen || function () {};
    own.onClose = onClose || function () {};
    own.onError = onError || function () {};
    own.closeWebSocket = function () {
        if (own.socket) {
            own.socket.close();
        }
    };
    own.sendMessage = function (message, lostExecute) {
        if (own.socket&&own.socket.readyState == 1) {
            try {
                own.socket.send(message);
            } catch (e) {
                if (typeof (lostExecute) == "function") {
                    lostExecute(message);
                }
            }
        } else {
            if (typeof (lostExecute) == "function") {
                lostExecute(message);
            }
        }
    };
    own.connect = function (host) {
        var mark = true;
        if ('WebSocket' in window) {
            own.socket = new WebSocket(host);
        } else if ('MozWebSocket' in window) {
            own.socket = new MozWebSocket(host);
        } else {
            return  mark = false;
        }

        //连接发生错误的回调方法
        own.socket.onerror = function () {
            if (typeof (own.onError) == "function") {
                own.onError();
            }
        };

        //连接成功建立的回调方法
        own.socket.onopen = function (event) {
            if (typeof (own.onOpen) == "function") {
                own.onOpen(event.data);
            }
        };

        //接收到消息的回调方法
        own.socket.onmessage = function (event) {
            if (typeof (own.onMessage) == "function") {
                own.onMessage(event.data);
            }
        };

        //连接关闭的回调方法
        own.socket.onclose = function () {
            if (typeof (own.onClose) == "function") {
                own.onClose(event);
            }
        };


        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        var netSocket = this.socket;
        window.onbeforeunload = function () {
            var isIE10 = false;
            if (window.ActiveXObject) {
                var reg = /10\.0/;
                var text = navigator.userAgent;
                if (reg.test(text)) {
                    isIE10 = true;
                }
            }
            if (!isIE10) {
                netSocket.close();
            }
        };
        return mark;
    };
}

