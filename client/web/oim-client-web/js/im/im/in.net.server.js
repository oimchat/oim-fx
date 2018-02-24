/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function NetServer() {
    var own = this;

    own.connectCount = 0;//记录打开页面后第几次成功连接

    own.reconnectTryCount = 3;
    own.reconnectWaitTime = 1000 * 60 * 2;

    own.reconnectCount = 0;
    own.reconnectTime = 0;

    own.reconnectHasShowWait = false;//是否提示过网络异常等待

    own.autoConnect = false;

    var code_fail = "0";
    var code_success = "1";

    own.doConnected = false;//是否已经连接过
    own.handleTimer = null;
    own.socketHost = null;
    own.connectedTemp = false;
    own.activeTime = 0;//记录客户端和服务端最后通信时间
    own.actionMap = new Map();
    own.messageHandler = new MessageHandler();
    own.messageKeyCount = 0;

    own.onOpen = function () {
    };
    own.onClose = function () {
    };
    own.onError = function () {
    };
    own.onIdle = function () {
    };
    own.onBreak = function () {
    };
    own.promptMessage = function () {
    };

    own.onConnectStatusChange = function () {
    };

    own.prompt = function (message) {
        if (typeof (own.promptMessage) == "function") {
            own.promptMessage(message);
        }
    };

    own.showErrorMessage = function (message) {
        if (message && message.head) {
            var head = message.head;
            if (head.resultMessage) {
                var resultMessage = head.resultMessage;
                own.prompt(resultMessage);
            }
        }
    }

    own.putAction = function (key, action) {
        own.actionMap.put(key, action);
    };

    own.setAutoConnect = function (autoConnect) {
        own.autoConnect = autoConnect;
    }

    own.setSocketHost = function (socketHost) {
        own.socketHost = socketHost;
    }

    own.connect = function (socketHost) {
        own.closeNetSocket();
        var mark = own.netSocket.connect(socketHost);
        own.doConnected = true;
        if (!mark) {
            own.prompt("您的浏览器不支持聊天功能.请更新到新版的浏览器！");
        }
    };

    own.closeNetSocket = function () {
        if (own.netSocket) {
            own.netSocket.closeWebSocket();
        }
    };

    own.sendMessage = function (message, backExecute, lostExecute, timeOutExecute) {
        if (message && message.head) {

            var head = message.head;
            if (0 == head.time || isEmpty(head.time) || isEmpty(head.key)) {
                var timestamp = new Date().getTime();
                if (0 == head.time || isEmpty(head.time)) {
                    head.time = timestamp;
                }
                if (isEmpty(head.key)) {
                    own.messageKeyCount++;
                    head.key = (own.messageKeyCount + timestamp ) + "";
                }
            }

            own.messageHandler.putHandlerData(head.key, message, backExecute, lostExecute, timeOutExecute, head.time);
            own.send(message, lostExecute);
        }
    };

    own.send = function (message, lostExecute) {
        var json = objectToJson(message);
        if (own.netSocket) {
            own.netSocket.sendMessage(json, lostExecute);
            own.activeTime = new Date().getTime();
        }
    };

    own.onMessage = function (message) {
        own.activeTime = new Date().getTime();
        var data = jsonToObject(message);
        if (data) {
            var head = data.head;
            var action = head.action;
            var method = head.method;
            var resultCode = head.resultCode;
            if (resultCode == code_fail) {
                own.showErrorMessage(message);
            } else {
                var key = action + "-" + method;
                var object = own.actionMap.get(key);
                if (typeof (object) == "function") {
                    object(data);
                }
                own.messageHandler.backMessage(head.key, data);
            }
        }
    };

    own.isConnected = function () {
        var isConnected = true;
        if (own.netSocket) {
            var socket = own.netSocket.socket;
            if (socket) {
                if (socket.readyState != 1) {
                    socket.close();
                    isConnected = false;
                }
            } else {
                isConnected = false;
            }
        } else {
            isConnected = false;
        }
        return isConnected;
    };
    /**
     * 获取连接次数
     * @returns {number}
     */
    own.getConnectCount = function () {
        return own.connectCount;
    }

    /**
     * 处理连接状态变化
     */
    own.handleConnectStatusChange = function () {
        var isConnected = own.isConnected();
        if (isConnected != own.connectedTemp) {
            own.connectedTemp = isConnected;
            own.onConnectStatusChange(isConnected);
        }
    };
    /**
     * 处理空闲
     */
    own.handleIdle = function () {
        var isConnected = own.isConnected();
        if (isConnected) {
            var timestamp = new Date().getTime();
            if ((timestamp - own.activeTime) > (40 * 1000)) {//40秒发送一次心跳包
                own.onIdle();
            }
        } else if (own.doConnected) {
            own.doConnected = false;
            own.onBreak();
        }
    };

    own.handleAutoConnect = function () {

        if (own.autoConnect && !isEmpty(own.socketHost) && !own.isConnected()) {
            if (own.reconnectCount < own.reconnectTryCount) {
                own.reconnectCount++;
                own.reconnectTime = new Date().getTime();
                own.connect(own.socketHost);
            } else {
                var timestamp = new Date().getTime();
                if ((timestamp - own.reconnectTime) < own.reconnectWaitTime) {//2分钟尝试一次

                    if (!own.reconnectHasShowWait) {
                        own.reconnectHasShowWait = true;
                        own.prompt("网络异常！");
                    }
                } else {
                    own.reconnectHasShowWait = false;
                    own.reconnectCount = 0;
                }
            }
        }
    };

    own.handle = function () {
        own.handleAutoConnect();
        own.handleConnectStatusChange();
        own.handleIdle();
    };

    own.init = function () {
        own.netSocket = new NetSocket(own.onMessage, function (data) {
            own.connectCount++;
            own.onOpen(data);
        }, function () {
            own.onClose();
        }, function () {
            own.onError();
        });
        own.handleTimer = window.setInterval(own.handle, 1000);
    };
    own.init();
}


