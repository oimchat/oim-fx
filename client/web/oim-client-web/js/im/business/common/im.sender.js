/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var lost = function () {
    var text = "网络异常，发送失败！";
    promptMessage(text);
};

function SystemSender(app) {
    var own = this;
    own.sendBeartbeat = function () {
        var message = getMessage("000", "0001");
        app.sendMessage(message);
    };
}

function PersonalSender(app) {
    var own = this;
    own.login = function (loginData, back) {
        var message = getMessage("1.100", "1.1.0001");
        message.body.loginData = loginData;
        app.sendMessage(message, back);
    };

    own.reconnect = function (loginData, back) {
        var message = getMessage("1.100", "1.1.0002");
        message.body.loginData = loginData;
        app.sendMessage(message, back);
    };

    own.getUserData = function () {
        var message = getMessage("1.100", "1.1.0003");
        app.sendMessage(message);
    };

    own.updateUserData = function (userData, back) {
        var message = getMessage("1.100", "1.1.0004");
        var body = {"userData": userData};
        message.body = body;

        var lost = function () {
            var text = "网络异常，修改失败！";
            promptMessage(text);
        };
        app.sendMessage(message, back, lost);
    };

    own.updateUserHead = function (head, back) {
        var message = getMessage("1.100", "1.1.0006");
        var userHead = {"url": head}
        var body = {"userHead": userHead};
        message.body = body;

        var lost = function () {
            var text = "网络异常，修改失败！";
            promptMessage(text);
        };
        app.sendMessage(message, back, lost);
    };


    own.updatePassword = function (password, back) {
        var message = getMessage("1.100", "1.1.0005");
        var body = {"newPassword": password};
        message.body = body;

        var lost = function () {
            var text = "网络异常，发送失败！";
            promptMessage(text);
        };
        app.sendMessage(message, back, lost);
    };

}


function UserSender(app) {
    var own = this;
    own.getUserCategoryWithUserList = function () {
        var message = getMessage("1.101", "1.1.0004");
        app.sendMessage(message);
    };

    own.getUserDataById = function (userId, back) {
        var message = getMessage("1.101", "1.1.0005");
        var body = {"userId": userId};
        message.body = body;
        app.sendMessage(message, back);
    };

    own.sendUpdateStatus = function (status) {
        var message = getMessage("1.101", "1.1.0008");
        var body = {"userId": "", "status": status};
        message.body = body;
        app.sendMessage(message);
    };
}

function GroupSender(app) {
    var own = this;
    own.getGroupCategoryWithGroupList = function () {
        var message = getMessage("1.201", "1.1.0004");
        app.sendMessage(message);
    };

    own.getGroupMemberListWithUserDataList = function (groupId, back) {
        var message = getMessage("1.201", "1.1.0010");
        var body = {"groupId": groupId};
        message.body = body;
        app.sendMessage(message);
    };
    
    own.getGroupById = function (groupId, back) {
        var message = getMessage("1.201", "1.1.0005");
        var body = {"groupId": groupId};
        message.body = body;
        app.sendMessage(message, back);
    };
}

function UserChatSender(app) {
    var own = this;
    own.sendUserChatMessage = function (receiveUserId, sendUserId, content, back) {
        var message = getMessage("1.500", "1.1.1001");
        var body = {
            "receiveUserId": receiveUserId,
            "sendUserId": sendUserId,
            "content": content
        };
        message.body = body;
        var lost = function () {
            var text = "网络异常，发送失败！";
            promptMessage(text);
        }
        app.sendMessage(message, back, lost);
    };

    own.getLastList = function () {
        var message = getMessage("1.500", "1.1.1011");
        var body = {
            "userId": "",
            "type": "",
            "count": 20
        };
        message.body = body;
        app.sendMessage(message);
    }

    own.getOfflineMessage = function () {
        var message = getMessage("1.500", "1.1.1014");
        app.sendMessage(message);
    }

    /**
     * 查询用户聊天记录
     *
     * @param sendUserId
     * @param receiveUserId
     * @param chatQuery
     * @param page
     * @param back
     */
    own.queryUserChatLog = function (sendUserId, receiveUserId, chatQuery, page, back) {
        var message = getMessage("1.500", "1.1.1004");

        var body = {
            "sendUserId": sendUserId,
            "receiveUserId": receiveUserId,
            "chatQuery": chatQuery ? chatQuery : {},
            "page": page
        };
        message.body = body;
        app.sendMessage(message, back, lost);
    };

    own.removeLastChat = function (userId, chatId) {
        var message = getMessage("1.500", "1.1.1013");

        var body = {
            "userId": userId,
            "chatId": chatId,
            "type": "1"
        };
        message.body = body;
        app.sendMessage(message);
    };
}

function GroupChatSender(app) {
    var own = this;
    own.sendGroupChatMessage = function (userId, groupId, content, back) {
        var message = getMessage("1.500", "1.1.2001");
        var body = {
            "userId": userId,
            "groupId": groupId,
            "content": content
        };
        message.body = body;
        var lost = function () {
            var text = "网络异常，发送失败！";
            promptMessage(text);
        };
        app.sendMessage(message, back, lost);
    };

    own.queryGroupChatLog = function (groupId, chatQuery, page, back) {
        var message = getMessage("1.500", "1.1.2004");
        message.body = {
            "groupId": groupId,
            "chatQuery": chatQuery || {"startDate": new Date().format("yyyy-MM-dd")},
            "page": page || {pageSize: 35, pageNumber: 1}
        };
        app.sendMessage(message, back);
    };
}

function RoomChatSender(app) {
    var own = this;
    own.sendRoomChatMessage = function (userId, roomId, content, back) {
        var message = getMessage("1.500", "1.1.3001");
        var body = {
            "userId": userId,
            "roomId": roomId,
            "content": content
        };
        message.body = body;
        var lost = function () {
            var text = "网络异常，发送失败！";
            promptMessage(text);
        }
        app.sendMessage(message, back, lost);
    };

    own.queryRoomChatLog = function (roomId, chatQuery, page, back) {
        var message = getMessage("1.500", "1.1.3004");
        message.body = {
            "roomId": roomId,
            "chatQuery": chatQuery || {"startDate": new Date().format("yyyy-MM-dd")},
            "page": page || {pageSize: 35, pageNumber: 1}
        };
        app.sendMessage(message, back);
    };
}

/**
 * 用户成员相关
 * @param app
 * @constructor
 */
function UserCategoryMemberSender(app) {
    var own = this;
    var lost = function () {
        var text = "网络异常，添加失败！";
        promptMessage(text);
    };
    own.addUserCategoryMember = function (userCategoryId, memberUserId, remark, back) {
        var message = getMessage("1.103", "1.1.0001");

        var userCategoryMember = {
            "userCategoryId": userCategoryId,
            "memberUserId": memberUserId,
            "remark": remark
        };

        var body = {
            "userCategoryMember": userCategoryMember
        };
        message.body = body;
        var lost = function () {
            var text = "网络异常，添加失败！";
            promptMessage(text);
        }
        app.sendMessage(message, back, lost);
    };

    own.updateUserCategoryMemberRemark = function (memberUserId, remark, back) {
        var message = getMessage("1.103", "1.1.0002");
        var body = {
            "memberUserId": memberUserId,
            "remark": remark
        };
        message.body = body;
        var lost = function () {
            var text = "网络异常，修改失败！";
            promptMessage(text);
        }
        app.sendMessage(message, back, lost);
    };
}


function RoomSender(app) {
    var own = this;
    var lost = function () {
        var text = "网络异常，添加失败！";
        promptMessage(text);
    };

    own.joinRoom = function (roomId, nickname, password, back) {
        var message = getMessage("1.300", "1.1.0001");

        var body = {
            "roomId": roomId,
            "nickname": nickname,
            "password": password
        };
        message.body = body;
        app.sendMessage(message, back, lost);
    };

    own.quitRoom = function (roomId, back) {
        var message = getMessage("1.300", "1.1.0002");

        var body = {
            "roomId": roomId
        };
        message.body = body;
        app.sendMessage(message, back, lost);
    };


    own.getRoomUserList = function (roomId, back) {
        var message = getMessage("1.300", "1.1.0004");

        var body = {
            "roomId": roomId
        };
        message.body = body;
        app.sendMessage(message, back, lost);
    };

    own.getRoomList = function () {
        var message = getMessage("1.300", "1.1.0009");
        app.sendMessage(message);
    };

    own.queryRoomList = function (back) {
        var message = getMessage("1.300", "1.1.0011");

        var roomQuery = {};
        var page = {
            "pageSize": 30,
            "pageNumber": 1
        };

        var body = {
            "roomQuery": roomQuery,
            "page": page
        };
        message.body = body;
        app.sendMessage(message, back, lost);
    };

    own.getRoomById = function (roomId, back) {
        var message = getMessage("1.300", "1.1.0013");
        var body = {"roomId": roomId};
        message.body = body;
        app.sendMessage(message, back);
    };

    own.createRoom = function (roomName, nickname, password, back) {
        var message = getMessage("1.300", "1.1.0014");
        var body = {
            "roomName": roomName,
            "nickname": nickname,
            "password": password
        };
        message.body = body;
        app.sendMessage(message, back, lost);
    };

    own.invitationJoinRoom = function (roomId, userIds, reason, back) {
        var message = getMessage("1.300", "1.1.0015");
        var body = {
            "roomId": roomId,
            "userIds": userIds,
            "reason": reason
        };
        message.body = body;
        app.sendMessage(message, back, lost);
    };

    own.rejectInvite = function (roomId, receiveUserId, reason, back) {
        var message = getMessage("1.300", "1.1.0016");
        var body = {
            "roomId": roomId,
            "receiveUserId": receiveUserId,
            "reason": reason
        };
        message.body = body;
        app.sendMessage(message, back, lost);
    };
}