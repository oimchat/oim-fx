/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global PersonalSender */

function PersonalController(app) {
    var own = this;
    own.login = function (account, password) {
        var back = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;
            var userData = body.userData;

            var personalService = app.getService(PersonalService);

            if (info && info.success) {

                if (userData) {
                    var personalBox = appContext.getBox(PersonalBox);
                    var ud = personalBox.getUserData();

                    if (isEmpty(ud)) {
                        personalBox.setUserData(userData);
                    }
                }
            }

            personalService.loginBack(info)
        };

        var pb = app.getBox(PersonalBox);
        var ps = app.getSender(PersonalSender);

        var loginData = {
            "account": account,
            "password": password,
            "status": "1"
        };

        pb.setLoginData(loginData);
        ps.login(loginData, back);
    };


    own.reconnect = function (loginData) {
        var back = function (message) {

        };
        var ps = app.getSender(PersonalSender);
        ps.reconnect(loginData, back);
    };

    own.updateUserData = function (userData) {
        var ps = app.getSender(PersonalSender);

        var back = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;
            if (info.success) {
                ps.getUserData();
                promptMessage("修改成功。", "info");
            } else {
                var errorText = getDefaultErrorText(info);
                promptMessage(errorText, "warning");
            }
        };
        ps.updateUserData(userData, back);
    };

    own.updateUserHead = function (head) {
        var ps = app.getSender(PersonalSender);

        var back = function (message) {
            //var head = message.head;
            var body = message.body;
            var info = message.info;
            if (info.success) {
                ps.getUserData();
                promptMessage("修改成功。", "info");
            } else {
                var errorText = getDefaultErrorText(info);
                promptMessage(errorText, "warning");
            }
        };
        ps.updateUserHead(head, back);
    };

    own.updatePassword = function (password) {
        var back = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;
            if (info.success) {
                var pb = app.getBox(PersonalBox);

                var loginData = pb.getLoginData();
                loginData.password = password;
                pb.setLoginData(loginData);
                promptMessage("修改成功。", "info");
            } else {
                var errorText = getDefaultErrorText(info);
                promptMessage(errorText, "warning");
            }
        };
        var ps = app.getSender(PersonalSender);
        ps.updatePassword(password, back);
    };
}

function UserChatController(app) {
    var own = this;
    own.sendUserMessage = function (receiveUserId, content) {
        var back = function (message) {

        }

        var pb = app.getBox(PersonalBox);
        var sendUserId = pb.getOwnerUserId();
        var us = app.getSender(UserChatSender);
        us.sendUserChatMessage(receiveUserId, sendUserId, content, back);
    }
}

function GroupChatController(app) {
    var own = this;
    own.sendGroupMessage = function (groupId, content) {
        var back = function (message) {

        };

        var pb = app.getBox(PersonalBox);
        var userId = pb.getOwnerUserId();
        var gcs = app.getSender(GroupChatSender);
        gcs.sendGroupChatMessage(userId, groupId, content, back);
    }
}

function RoomChatController(app) {
    var own = this;
    own.sendRoomMessage = function (roomId, content) {
        var back = function (message) {

        };

        var pb = app.getBox(PersonalBox);
        var userId = pb.getOwnerUserId();
        var rcs = app.getSender(RoomChatSender);
        rcs.sendRoomChatMessage(userId, roomId, content, back);
    }
}

function UserController(app) {
    var own = this;

    own.addUser = function (account, remark, categoryId) {
        var back = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;
            if (info.success) {

                promptMessage("添加成功。", "info");

                var userCategoryMember = body.userCategoryMember;

                var ownUserId = userCategoryMember.ownUserId;
                var userCategoryId = userCategoryMember.userCategoryId;
                var memberUserId = userCategoryMember.memberUserId;
                var remark = userCategoryMember.remark;

                var ulm = appContext.getBox(UserListManager);
                var ub = appContext.getBox(UserBox);
                var hasUserCategory = ub.hasUserCategory(userCategoryId);
                if (!hasUserCategory) {
                    var userCategoryInfo = {"id": userCategoryId, "name": userCategoryId};
                    ulm.addOrUpdateUserCategoryInfo(userCategoryInfo);
                }

                var userData = ub.getUserData(memberUserId);

                if (isEmpty(userData)) {
                    var back = function (message) {
                        var body = message.body;
                        var userData = body.userData;
                        if (userData) {
                            userData.remarkName = remark;
                            ub.putUserData(userData);
                            ulm.addUserCategoryMember(userCategoryMember);
                        }
                    };
                    var uh = appContext.getSender(UserSender);
                    uh.getUserDataById(memberUserId, back);
                } else {
                    userData.remarkName = remark;
                    ulm.addUserCategoryMember(userCategoryMember);
                }

            } else {
                var errorText = getDefaultErrorText(info);
                promptMessage(errorText, "warning");
            }
        };
        var ucms = app.getSender(UserCategoryMemberSender);
        ucms.addUserCategoryMember(categoryId, account, remark, back);
    };

    own.updateUserRemarkName = function (memberUserId, remark) {
        var back = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;
            if (info.success) {
                promptMessage("修改成功。", "info");
                var userService = app.getService(UserService);
                userService.updateUserDataById(memberUserId);
            } else {
                var errorText = getDefaultErrorText(info);
                promptMessage(errorText, "warning");
            }
        };
        var ucms = app.getSender(UserCategoryMemberSender);
        ucms.updateUserCategoryMemberRemark(memberUserId, remark, back)
    };
}


function RoomController(app) {
    var own = this;

    own.joinRoom = function (roomId, nickname, password) {
        var back = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;
            if (info.success) {

                promptMessage("添加成功。", "info");
                var roomSender = app.getSender(RoomSender);
                roomSender.getRoomList();

            } else {
                var errorText = getDefaultErrorText(info);
                promptMessage(errorText, "warning");
            }
        };
        var roomSender = app.getSender(RoomSender);
        roomSender.joinRoom(roomId, nickname, password, back);
    };

    own.createRoom = function (roomName, nickname, password) {
        var back = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;
            if (info.success) {

                promptMessage("创建成功。", "info");
                var roomSender = app.getSender(RoomSender);
                roomSender.getRoomList();

            } else {
                var errorText = getDefaultErrorText(info);
                promptMessage(errorText, "warning");
            }
        };
        var roomSender = app.getSender(RoomSender);
        roomSender.createRoom(roomName, nickname, password, back);
    };
}