/* global UserListManage, PersonalBox, UserBox, UserSender, user_head_images, userListChatPaneView, UserListManager, GroupListManager */


function AppService(appContext) {
    var own = this;
    own.initApp = function () {

    };

    own.systemLoad = function () {
        if (appContext.isReload) {
            var task = new Task();
            task.push(function () {

                var userChatSender = appContext.getSender(UserChatSender);
                var roomSender = appContext.getSender(RoomSender);
                userChatSender.getLastList();
                userChatSender.getOfflineMessage();

                roomSender.getRoomList();

            });
            task.start();
        }
    };
}

function PersonalService(appContext) {
    var own = this;
    own.setUserData = function (userData) {
        var personalBox = appContext.getBox(PersonalBox);
        personalBox.setUserData(userData);

        var userBox = appContext.getBox(UserBox);
        userBox.putUserData(userData);

        setOwnUserData(userData);
        //mainView.setUser(userData);
    };

    own.loginBack = function (info) {
        loginView.setShowWaiting(false);
        if (info && info.success) {

            loginView.hide();
            mainView.show();
            if (appContext.isReload) {
                var task = new Task();
                task.push(function () {

                    var userChatSender = appContext.getSender(UserChatSender);
                    userChatSender.getLastList();
                    userChatSender.getOfflineMessage();

                    var personalSender = appContext.getSender(PersonalSender);
                    personalSender.getUserData();

                    var userSender = appContext.getSender(UserSender);
                    userSender.getUserCategoryWithUserList();

                    var groupSender = appContext.getSender(GroupSender);
                    groupSender.getGroupCategoryWithGroupList();
                });
                task.start();
            }

        } else {
            var errorText = getDefaultErrorText(info);
            promptMessage(errorText, "warning");
        }
    };
}

function UserService(appContext) {
    var own = this;

    own.setUserCategoryWithUserList = function (userCategoryList, userDataList, userCategoryMemberList) {
        var ulm = appContext.getManage(UserListManager);
        ulm.setUserCategoryWithUserList(userCategoryList, userDataList, userCategoryMemberList);
    };

    own.updateUserDataById = function (userId) {
        var back = function (message) {
            var head = message.head;
            var body = message.body;
            var userData = body.userData;
            own.updateUserData(userData);
        };
        var uh = appContext.getSender(UserSender);
        uh.getUserDataById(userId, back);
    };

    own.updateUserData = function (userData) {
        var ulm = appContext.getManage(UserListManager);
        ulm.updateUserData(userData);
    };
}

function GroupService(appContext) {
    var own = this;
    own.setGroupCategoryWithGroupList = function (groupCategoryList, groupList, groupCategoryMemberList) {
        var glm = appContext.getManage(GroupListManager);
        glm.setGroupCategoryWithGroupList(groupCategoryList, groupList, groupCategoryMemberList);
    };
}

function RoomService(appContext) {
    var own = this;
    own.setRoomList = function (roomList) {
        var room = appContext.getManage(RoomListManager);
        room.setRoomList(roomList);
    };
}

function UserChatService(appContext) {
    var own = this;

    own.removeUserChatById = function (userId) {
        var ucm = appContext.getManage(UserChatManager);
        ucm.removeUserChatById(userId);
    };

    own.showUserChatById = function (userId) {
        var ub = appContext.getBox(UserBox);
        var userData = ub.getUserData(userId);
        if (userData) {
            own.showUserChat(userData);
        } else {
            var back = function (message) {
                var body = message.body;
                var userData = body.userData;
                if (userData) {
                    ub.putUserData(userData);
                    own.showUserChat(userData);
                }
            };
            var uh = appContext.getSender(UserSender);
            uh.getUserDataById(userId, back);
        }
    };

    own.showUserChat = function (userData) {
        if (userData) {
            var ucm = appContext.getManage(UserChatManager);
            ucm.showUserChat(userData);
        }
    };

    own.showUserChatItemById = function (userId) {
        var ub = appContext.getBox(UserBox);
        var userData = ub.getUserData(userId);
        if (userData) {
            own.showUserChatItem(userData);
        } else {
            var back = function (message) {
                var body = message.body;
                var userData = body.userData;
                if (userData) {
                    ub.putUserData(userData);
                    own.showUserChatItem(userData);
                }
            };
            var uh = appContext.getSender(UserSender);
            uh.getUserDataById(userId, back);
        }
    };

    own.showUserChatItem = function (userData) {
        if (userData) {
            var userHead = userData.head;
            var itemId = userData.id;
            var showName = getShowName(userData);
            var head = getHeadImage(userData);
            var status = userData.status;
            var gray = isOffline(status);

            var type = "user";
            chatListView.addOrUpdateItem(type, itemId, showName, head, false);
            chatListView.selectItem(type, itemId);
        }
    };

    own.receiveUserChatMessage = function (messageId, sendUserId, receiveUserId, content) {
        var pb = appContext.getBox(PersonalBox);
        var ub = appContext.getBox(UserBox);

        var ownUser = pb.getUserData();
        var isOwn = sendUserId == (ownUser.id);

        var showUserId = (isOwn) ? receiveUserId : sendUserId;
        var showUserData = ub.getUserData(showUserId);

        var chatUserData = (isOwn) ? ownUser : showUserData;

        var showName = getShowName(showUserData);
        // 先从好友集合里面获取用户信息，如果用户不是好友，那么从服务器下载用户信息

        var userChatManager = appContext.getManage(UserChatManager);
        userChatManager.putUserMessageId(showUserId, messageId);

        if (isEmpty(showUserData)) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了
            var back = function (message) {
                var body = message.body;
                showUserData = body.userData;
                if (showUserData) {
                    ub.putUserData(showUserData);
                    showName = getShowName(showUserData);
                    chatUserData = (isOwn) ? ownUser : showUserData;
                    showChatMessage(showUserId, showName, isOwn, chatUserData, content);
                    showChatItem(showUserData, isOwn);
                }
            };
            var uh = appContext.getSender(UserSender);
            uh.getUserDataById(sendUserId, back);
        } else {
            showChatMessage(showUserId, showName, isOwn, chatUserData, content);
            showChatItem(showUserData, isOwn);
        }
    };


    own.setLastList = function (userId, userLastList, groupLastList) {
        var ub = appContext.getBox(UserBox);

        if (!isEmpty(userLastList)) {
            var lastManager = appContext.getManage(LastManager);
            var pb = appContext.getBox(PersonalBox);
            var u = pb.getUserData();
            var id = u.id;


            var length = userLastList.length;
            for (var i = 0; i < length; i++) {
                var uc = userLastList[i];
                if (uc) {
                    var content = uc.content;
                    var sd = uc.sendUserData;
                    var rd = uc.receiveUserData;

                    var ud = (id == sd.id) ? rd : sd;
                    var uid = ud.id;
                    var userData = ub.getUserData(uid);
                    if (null != userData) {
                        ud = userData;
                    }
                    lastManager.addOrUpdateLastUserData(ud);
                }
            }
        }
    };

    function showChatMessage(showUserId, itemName, isOwn, userData, content) {

        var showName = getShowName(userData);
        var userHead = userData.head;
        var head = getHeadImage(userData);
        userChatPaneBox.insertAfterMessage(showUserId, itemName, isOwn, head, showName, content);
    }

    function showChatItem(userData, isOwn) {
        var showUserId = userData.id;
        var has = chatListView.hasItem("user", showUserId);
        if (!has) {
            var userHead = userData.head;
            var itemId = userData.id;
            var showName = getShowName(userData);
            var head = getHeadImage(userData);
            var status = userData.status;
            var gray = isOffline(status);

            var type = "user";
            chatListView.addOrUpdateItem(type, itemId, showName, head, gray);
        }

        var isSelected = tabView.isSelected("chat_tab");
        if (isSelected) {
            var isShowing = userChatPaneBox.isChatPaneShowing(showUserId);
            //if (!isShowing) {

            // }
        } else {
            //playAudio(1);
            setMessageTabRed(true);
        }
        if (!isOwn) {
            chatListView.setItemRed("user", showUserId, true);
            playAudio(1);
        }
    }
}


function GroupChatService(appContext) {
    var own = this;
    var groupTempUserMap = new Map();

    own.removeGroupChatById = function (groupId) {
        var rcm = appContext.getManage(GroupChatManager);
        rcm.removeGroupChatById(groupId);
    };


    own.showGroupChatById = function (groupId) {
        var rb = appContext.getBox(GroupBox);
        var group = rb.getGroup(groupId);
        if (group) {
            own.showGroupChat(group);
        } else {
            var back = function (message) {
                var body = message.body;
                var group = body.group;
                if (group) {
                    rb.putGroup(group);
                    own.showGroupChat(group);
                }
            };
            var rs = appContext.getSender(GroupSender);
            rs.getGroupById(groupId, back);
        }
    };

    own.showGroupChat = function (group) {
        if (group) {
            var rcm = appContext.getManage(GroupChatManager);
            rcm.showGroupChat(group);
        }
    };

    own.showGroupChatItemById = function (groupId) {
        var rb = appContext.getBox(GroupBox);
        var group = rb.getGroup(groupId);
        if (group) {
            own.showGroupChatItem(group);
        } else {
            var back = function (message) {
                var body = message.body;
                var group = body.group;
                if (group) {
                    rb.putGroup(group);
                    own.showGroupChatItem(group);
                }
            };
            var rs = appContext.getSender(GroupSender);
            rs.getGroupById(groupId, back);
        }
    };

    own.showGroupChatItem = function (group) {
        if (group) {
            var groupHead = group.head;
            var itemId = group.id;
            var showName = group.name;
            var head = group_head_images + "1.png";

            var gray = false;

            var type = "group";
            chatListView.addOrUpdateItem(type, itemId, showName, head, false);
            chatListView.selectItem(type, itemId);
        }
    };

    own.receiveGroupChatMessage = function (messageId, userId, groupId, content) {

        var groupChatManager = appContext.getManage(GroupChatManager);

        var has = groupChatManager.hasGroupMessageId(groupId, messageId);
        if (has) {
            return;
        }
        groupChatManager.putGroupMessageId(groupId, messageId);

        var rb = appContext.getBox(GroupBox);
        var group = rb.getGroup(groupId);//
        if (group) {
            var showName = group.name;

            var pb = appContext.getBox(PersonalBox);
            var ub = appContext.getBox(UserBox);

            var ownUser = pb.getUserData();
            var isOwn = userId == (ownUser.id);

            var chatUserData = isOwn ? ownUser : ub.getUserData(userId);

            if (isEmpty(chatUserData)) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了
                chatUserData = getGroupUserData(groupId, userId)
                if (isEmpty(chatUserData)) {
                    var back = function (message) {
                        var body = message.body;
                        chatUserData = body.userData;
                        if (chatUserData) {
                            ub.putUserData(chatUserData);
                            showChatMessage(groupId, showName, isOwn, chatUserData, content);
                            showChatItem(group, isOwn);
                        } else {
                            chatUserData = getOrCreateGroupUserData(groupId, userId);
                            showChatMessage(groupId, showName, isOwn, chatUserData, content);
                            showChatItem(group, isOwn);
                        }
                    };
                    var uh = appContext.getSender(UserSender);
                    uh.getUserDataById(userId, back);
                } else {
                    showChatMessage(groupId, showName, isOwn, chatUserData, content);
                    showChatItem(group, isOwn);
                }
            } else {
                showChatMessage(groupId, showName, isOwn, chatUserData, content);
                showChatItem(group, isOwn);
            }
        }
    };

    function showChatMessage(groupId, itemName, isOwn, userData, content) {

        var showName = getShowName(userData);
        var userHead = userData.head;
        var head = getHeadImage(userData);
        groupChatPaneBox.insertAfterMessage(groupId, itemName, isOwn, head, showName, content);
    }


    function showChatItem(group, isOwn) {
        var groupId = group.id;
        var has = chatListView.hasItem("group", groupId);
        if (!has) {

            var groupHead = group.head;
            var itemId = group.id;
            var showName = group.name;
            var head = group_head_images + "1.png";

            var gray = false;

            var type = "group";
            chatListView.addOrUpdateItem(type, itemId, showName, head, false);
            chatListView.selectItem(type, itemId);
        }

        var isSelected = tabView.isSelected("chat_tab");
        if (isSelected) {
            // var isShowing = groupChatPaneBox.isChatPaneShowing(groupId);
            //if (!isShowing) {

            //}
        } else {
            //playAudio(1);
            setMessageTabRed(true);
        }
        if (!isOwn) {
            chatListView.setItemRed("group", groupId, true);
            playAudio(1);
        }
    }

    function getGroupUserMap(groupId) {
        var userMap = groupTempUserMap.get(groupId);
        if (isEmpty(userMap)) {
            userMap = new Map();
            groupTempUserMap.put(groupId, userMap);
        }
        return userMap;
    }

    function getGroupUserData(groupId, userId) {
        var userMap = getGroupUserMap(groupId);
        var userData = userMap.get(userId);
        return userData;
    }

    function getOrCreateGroupUserData(groupId, userId) {
        var userMap = getGroupUserMap(groupId);

        var userData = userMap.get(userId);
        if (isEmpty(userData)) {
            userData = {"id": userId, "nickname": userId, "name": userId};
            userMap.put(userId, userData);
        }
        return userData;
    }

//    own.receiveUserChatMessage = function (messageId, userId, groupId, content) {
//
//
//        var gb = appContext.getBox(GroupBox);
//        var group = gb.getGroup(groupId);//
//        if (group) {
//            var showName = group.name;
//
//            var pb = appContext.getBox(PersonalBox);
//            var ub = appContext.getBox(UserBox);
//
//            var ownUser = pb.getUserData();
//            var isOwn = userId == (ownUser.id);
//
//            var sendUserData = isOwn ? ownUser : ub.getUserData(userId);
//
//            if (isEmpty(sendUserData)) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了
//                var back = function (message) {
//                    var body = message.body;
//                    sendUserData = body.userData;
//                    if (sendUserData) {
//                        ub.putUserData(sendUserData);
//                        showChatMessage(groupId, showName, isOwn, sendUserData, content);
//                    }
//                };
//                var uh = appContext.getSender(UserSender);
//                uh.getUserDataById(userId, back);
//            } else {
//                showChatMessage(groupId, showName, isOwn, sendUserData, content);
//            }
//        }
//    };
//
//    function showChatMessage(groupId, itemName, isOwn, userData, content) {
//        var showName = getShowName(userData);
//        var userHead = userData.head;
//        var head = user_head_images + userHead + ".png";
//        groupListPaneView.insertChatMessage(groupId, itemName, isOwn, head, showName, content);
//    }
}

function RoomChatService(appContext) {
    var own = this;
    var roomTempUserMap = new Map();

    own.removeRoomChatById = function (roomId) {
        var rcm = appContext.getManage(RoomChatManager);
        rcm.removeRoomChatById(roomId);
    };

    own.showRoomChatById = function (roomId) {
        var rb = appContext.getBox(RoomBox);
        var room = rb.getRoom(roomId);
        if (room) {
            own.showRoomChat(room);
        } else {
            var back = function (message) {
                var body = message.body;
                var room = body.room;
                if (room) {
                    rb.putRoom(room);
                    own.showRoomChat(room);
                }
            };
            var rs = appContext.getSender(RoomSender);
            rs.getRoomById(roomId, back);
        }
    };

    own.showRoomChat = function (room) {
        if (room) {
            var rcm = appContext.getManage(RoomChatManager);
            rcm.showRoomChat(room);
        }
    };

    own.showRoomChatItemById = function (roomId) {
        var rb = appContext.getBox(RoomBox);
        var room = rb.getRoom(roomId);
        if (room) {
            own.showRoomChatItem(room);
        } else {
            var back = function (message) {
                var body = message.body;
                var room = body.room;
                if (room) {
                    rb.putRoom(room);
                    own.showRoomChatItem(room);
                }
            };
            var rs = appContext.getSender(RoomSender);
            rs.getRoomById(roomId, back);
        }
    };

    own.showRoomChatItem = function (room) {
        if (room) {
            var roomHead = room.head;
            var itemId = room.id;
            var showName = room.name;
            var head = group_head_images + "1.png";

            var gray = false;

            var type = "room";
            chatListView.addOrUpdateItem(type, itemId, showName, head, false);
            chatListView.selectItem(type, itemId);
        }
    };


    own.receiveRoomChatMessage = function (messageId, userId, roomId, content) {

        var roomChatManager = appContext.getManage(RoomChatManager);

        var has = roomChatManager.hasRoomMessageId(roomId, messageId);
        if (has) {
            return;
        }
        roomChatManager.putRoomMessageId(roomId, messageId);

        var rb = appContext.getBox(RoomBox);
        var room = rb.getRoom(roomId);//
        if (room) {
            var showName = room.name;

            var pb = appContext.getBox(PersonalBox);
            var ub = appContext.getBox(UserBox);

            var ownUser = pb.getUserData();
            var isOwn = userId == (ownUser.id);

            var chatUserData = isOwn ? ownUser : ub.getUserData(userId);

            if (isEmpty(chatUserData)) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了
                chatUserData = getRoomUserData(roomId, userId)
                if (isEmpty(chatUserData)) {
                    var back = function (message) {
                        var body = message.body;
                        chatUserData = body.userData;
                        if (chatUserData) {
                            ub.putUserData(chatUserData);
                            showChatMessage(roomId, showName, isOwn, chatUserData, content);
                            showChatItem(room, isOwn);
                        } else {
                            chatUserData = getOrCreateRoomUserData(roomId, userId);
                            showChatMessage(roomId, showName, isOwn, chatUserData, content);
                            showChatItem(room, isOwn);
                        }
                    };
                    var uh = appContext.getSender(UserSender);
                    uh.getUserDataById(userId, back);
                } else {
                    showChatMessage(roomId, showName, isOwn, chatUserData, content);
                    showChatItem(room, isOwn);
                }
            } else {
                showChatMessage(roomId, showName, isOwn, chatUserData, content);
                showChatItem(room, isOwn);
            }
        }
    };

    function showChatMessage(roomId, itemName, isOwn, userData, content) {

        var showName = getShowName(userData);
        var userHead = userData.head;
        var head = getHeadImage(userData);
        roomChatPaneBox.insertAfterMessage(roomId, itemName, isOwn, head, showName, content);
    }


    function showChatItem(room, isOwn) {
        var roomId = room.id;
        var has = chatListView.hasItem("room", roomId);
        if (!has) {

            var roomHead = room.head;
            var itemId = room.id;
            var showName = room.name;
            var head = group_head_images + "1.png";

            var gray = false;

            var type = "room";
            chatListView.addOrUpdateItem(type, itemId, showName, head, false);
            chatListView.selectItem(type, itemId);
        }

        var isSelected = tabView.isSelected("chat_tab");
        if (isSelected) {
            // var isShowing = roomChatPaneBox.isChatPaneShowing(roomId);
            //if (!isShowing) {

            //}
        } else {
            //playAudio(1);
            setMessageTabRed(true);
        }
        if (!isOwn) {
            chatListView.setItemRed("room", roomId, true);
            playAudio(1);
        }
    }

    function getRoomUserMap(roomId) {
        var userMap = roomTempUserMap.get(roomId);
        if (isEmpty(userMap)) {
            userMap = new Map();
            roomTempUserMap.put(roomId, userMap);
        }
        return userMap;
    }

    function getRoomUserData(roomId, userId) {
        var userMap = getRoomUserMap(roomId);
        var userData = userMap.get(userId);
        return userData;
    }

    function getOrCreateRoomUserData(roomId, userId) {
        var userMap = getRoomUserMap(roomId);

        var userData = userMap.get(userId);
        if (isEmpty(userData)) {
            userData = {"id": userId, "nickname": userId, "name": userId};
            userMap.put(userId, userData);
        }
        return userData;
    }
}