/* global backUpdateRoomUserList, backHeartbeat, backRoomBan, backRoomMessageLog, backRoomUserCount, receiveRoomRemoveUser, receiveRoomAddUser, receiveRoomBan, receiveUserChat, receiveRoomChat, receiveRoomChatInfo, receiveBusiness, receivePopupMessage, receiveRoomUserChat, backGetRoomBan, backGetRoomUserList, backGetRoomUserCount, backQueryRoomMessageLog, UserAction, SystemSender, GroupAction, UserChatAction */

/**
 * Created by Only on 2017/6/14.
 */

var public_static_status_online = "1";
var public_static_status_call_me = "2";
var public_static_status_away = "3";
var public_static_status_busy = "4";
var public_static_status_mute = "5";
var public_static_status_invisible = "6";
var public_static_status_offline = "7";

function AppContext() {
    var own = this;
    var objectMap = new Map();
    own.netServer = new NetServer();
    own.cache = new Cache();
    own.session = new Session();
    own.messageStorage = new MessageStorage();
    own.isReload = true;

    own.getAction = function (clazz) {
        return getObject(clazz);
    };

    own.getBox = function (clazz) {
        return getObject(clazz);
    };

    own.getManage = function (clazz) {
        return getObject(clazz);
    };

    own.getService = function (clazz) {
        return getObject(clazz);
    };

    own.getController = function (clazz) {
        return getObject(clazz);
    };

    own.getSender = function (clazz) {
        return getObject(clazz);
    };

    own.getView = function (clazz) {
        return getObject(clazz);
    };

    own.initNetSocket = function (socketHost) {
        own.netServer.connect(socketHost);
        own.netServer.setSocketHost(socketHost);
    };

    own.sendMessage = function (message, backExecute, lostExecute, timeOutExecute) {
        own.netServer.sendMessage(message, backExecute, lostExecute, timeOutExecute);
    };

    own.prompt = function (message, type) {

    };

    own.isLogin = function () {
        var pb = own.getBox(PersonalBox);
        return pb.isLogin();
    };


    function getObject(clazz) {
        var object = objectMap.get(clazz);
        if (isEmpty(object)) {
            object = new clazz(own);
            objectMap.put(clazz, object);
        }
        return object;
    }

    function initMap() {
        /*****************************/
        /**
         * 请求回掉处理
         */
        var systemAction = own.getAction(SystemAction);
        var personalAction = own.getAction(PersonalAction);
        var userAction = own.getAction(UserAction);
        var groupAction = own.getAction(GroupAction);
        var roomAction=own.getAction(RoomAction);

        var userChatAction = own.getAction(UserChatAction);
        var groupChatAction = own.getAction(GroupChatAction);
        var roomChatAction=own.getAction(RoomChatAction);

        own.netServer.putAction("1.100-1.1.0003", personalAction.setUserData); //
        own.netServer.putAction("1.101-1.1.0004", userAction.setUserCategoryWithUserList); //

        own.netServer.putAction("1.000-1.2.0000", systemAction.systemLoad); //
        own.netServer.putAction("1.201-1.1.0004", groupAction.setGroupCategoryWithGroupList); //

        own.netServer.putAction("1.300-1.1.0009", roomAction.setRoomList); //

        own.netServer.putAction("1.500-1.1.1011", userChatAction.setLastList); //

        //own.netServer.putAction("1.201-1.2.0009", groupAction.up); //
        own.netServer.putAction("1.101-1.2.0009", userAction.updateUserData); //
        own.netServer.putAction("1.500-1.2.1001", userChatAction.receiveUserChatMessage); //
        own.netServer.putAction("1.500-1.2.2001", groupChatAction.receiveGroupChatMessage); //
        own.netServer.putAction("1.500-1.2.3001", roomChatAction.receiveRoomChatMessage); //

    }

    function initNetServer() {
        own.netServer.setAutoConnect(true);
        own.netServer.onConnectStatusChange = function (isConnected) {

        };

        own.netServer.promptMessage = function (message) {
            if (typeof (own.prompt) == "function") {
                own.prompt(message);
            }
        };

        own.netServer.onOpen = function () {

            // var account = "10000";
            // var password = "123456";
            // password = md5(password);
            // var pc = own.getController(PersonalController);
            // pc.login(account, password);

            var pb = own.getBox(PersonalBox);
            var isLogin = pb.isLogin();
            if (isLogin) {
                loginView.hide();
                mainView.show();
                setLoginEvent(true);
                var pc = own.getController(PersonalController);
                var loginData = pb.getLoginData();

                if (!own.isReload) {
                    pc.reconnect(loginData);
                } else {
                    pc.login(loginData.account, loginData.password);
                }
            } else {
                mainView.hide();
                loginView.show();
                setLoginEvent(false);
            }
        };
        own.netServer.onIdle = function () {
            //sendHeartbeat();
            var ss = own.getSender(SystemSender);
            ss.sendBeartbeat();
        };
        own.netServer.onBreak = function () {
            var pb = own.getBox(PersonalBox);
            var isLogin = pb.isLogin();
            if (isLogin) {
                var loginData = pb.getLoginData();
                var pc = own.getController(PersonalController);
                pc.reconnect(loginData);
            }
        };
    }

    initMap();
    initNetServer();
    return this;
}

function isOffline(status) {
    var offline = true;
    if (public_static_status_online == status) {
        offline = false;
    } else if (public_static_status_online == status) {
        offline = false;
    } else if (public_static_status_call_me == status) {
        offline = false;
    } else if (public_static_status_away == status) {
        offline = false;
    } else if (public_static_status_busy == status) {
        offline = false;
    } else if (public_static_status_mute == status) {
        offline = false;
    } else if (public_static_status_invisible == status) {
        offline = true;
    } else if (public_static_status_offline == status) {
        offline = true;
    } else {
        offline = true;
    }
    return offline;
}

function getShowName(userData) {
    var showName = "";
    if (userData) {

        var remarkName = userData.remarkName;
        var nickname = userData.nickname;
        var name = userData.name;
        var account = userData.account;
        var id = userData.id;

        showName = remarkName;

        if (isEmpty(showName)) {
            showName = nickname;
        }
        if (isEmpty(showName)) {
            showName = name;
        }
        if (isEmpty(showName)) {
            showName = account;
        }
        if (isEmpty(showName)) {
            showName = id;
        }
    }
    return showName;
}