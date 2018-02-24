/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global UserService, GroupService */
function SystemAction(appContext) {
    var own = this;
    own.systemLoad = function (message) {
        if (appContext.isReload) {
            var task = new Task();
            task.push(function () {

                var userChatSender=appContext.getSender(UserChatSender);
                userChatSender.getLastList();

                var roomSender = appContext.getSender(RoomSender);
                roomSender.getRoomList();
                
                var userSender = appContext.getSender(UserSender);
                userSender.sendUpdateStatus("1");

            });
            task.start();
        }
    };
}
function PersonalAction(appContext) {
    var own = this;


    own.setUserData = function (message) {
        var head = message.head;
        var body = message.body;
        var userData = body.userData;
        var personalService = appContext.getService(PersonalService);
        personalService.setUserData(userData);
    };
}

function UserAction(appContext) {
    var own = this;
    own.setUserCategoryWithUserList = function (message) {
        var head = message.head;
        var body = message.body;
        var userCategoryList = body.userCategoryList;
        var userDataList = body.userDataList;
        var userCategoryMemberList = body.userCategoryMemberList;
        var userService = appContext.getService(UserService);
        userService.setUserCategoryWithUserList(userCategoryList, userDataList, userCategoryMemberList);
    };

    own.setUserData = function (message) {
        var head = message.head;
        var body = message.body;
        var userData = body.userData;

    };

    own.updateUserData = function (message) {

        var head = message.head;
        var body = message.body;
        var userId = body.userId;

        var userService = appContext.getService(UserService);
        userService.updateUserDataById(userId);
    }
}


function GroupAction(appContext) {
    var own = this;
    own.setGroupCategoryWithGroupList = function (message) {
        var head = message.head;
        var body = message.body;
        var groupCategoryList = body.groupCategoryList;
        var groupList = body.groupList;
        var groupCategoryMemberList = body.groupCategoryMemberList;
        var groupService = appContext.getService(GroupService);
        groupService.setGroupCategoryWithGroupList(groupCategoryList, groupList, groupCategoryMemberList);
    };

    own.updateGroup = function (message) {
        var head = message.head;
        var body = message.body;
        var group = body.group;
        // var userService=appContext.getService(UserService);
        // userService.setUserCategoryWithUserList(userCategoryList, userDataList, userCategoryMemberList);
    };
}


function RoomAction(appContext) {
    var own = this;
    own.setRoomList = function (message) {
        var head = message.head;
        var body = message.body;
        var roomList = body.roomList;
        var roomService = appContext.getService(RoomService);
        roomService.setRoomList(roomList);
    };
}

function UserChatAction(appContext) {
    var own = this;

    own.receiveUserChatMessage = function (message) {
        var head = message.head;
        var body = message.body;
        var messageKey = body.messageKey;
        var sendUserId = body.sendUserId;
        var receiveUserId = body.receiveUserId;
        var content = body.content;
        var userChatService = appContext.getService(UserChatService);
        userChatService.receiveUserChatMessage(messageKey, sendUserId, receiveUserId, content);
    }

    own.setLastList= function (message) {


        var head = message.head;
        var body = message.body;
        var userId = body.userId;
        var userLastList = body.userLastList;
        var groupLastList = body.groupLastList;

        var userChatService = appContext.getService(UserChatService);
        userChatService.setLastList(userId, userLastList, groupLastList);
    }
}


function GroupChatAction(appContext) {
    var own = this;

    own.receiveGroupChatMessage = function (message) {
        var head = message.head;
        var body = message.body;
        var messageKey = body.messageKey;
        var userId = body.userId;
        var groupId = body.groupId;
        var content = body.content;
        var groupChatService = appContext.getService(GroupChatService);
        groupChatService.receiveGroupChatMessage(messageKey, userId, groupId, content)
    }
}


function RoomChatAction(appContext) {
    var own = this;

    own.receiveRoomChatMessage = function (message) {
        var head = message.head;
        var body = message.body;
        var messageKey = body.messageKey;
        var userId = body.userId;
        var roomId = body.roomId;
        var content = body.content;
        var roomChatService = appContext.getService(RoomChatService);
        roomChatService.receiveRoomChatMessage(messageKey, userId, roomId, content)
    }
}

/*****************************/
