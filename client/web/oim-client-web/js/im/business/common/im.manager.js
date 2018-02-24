/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global userListPaneView, UserBox, HeadBox, GroupBox, groupListPaneView */

var user_head_images = "images/common/head/user/";
var group_head_images = "images/common/head/group/";

function UserListManager(appContext) {
    var own = this;

    own.setUserCategoryWithUserList = function (userCategoryList, userDataList, userCategoryMemberList) {
        own.setUserCategoryList(userCategoryList);
        own.setUserDataList(userDataList);
        own.setUserCategoryMemberList(userCategoryMemberList);
    };

    own.setUserCategoryList = function (userCategoryList) {
        var ub = appContext.getBox(UserBox);
        ub.clearUserCategory();

        userListPaneView.clearCategory();

        if (userCategoryList) {
            var length = userCategoryList.length;
            for (var i = 0; i < length; i++) {
                var userCategory = userCategoryList[i];
                own.addOrUpdateUserCategoryInfo(userCategory);
            }
            own.updateAllUserCategoryMember();
            own.updateAllCategoryMemberCount();
        }
    };

    own.setUserDataList = function (userDataList) {
        var ub = appContext.getBox(UserBox);
        ub.setUserDataList(userDataList);
        if (userDataList) {
            own.updateAllUserCategoryMember();
            own.updateAllCategoryMemberCount();
        }
    };

    own.setUserCategoryMemberList = function (userCategoryMemberList) {
        var ub = appContext.getBox(UserBox);
        ub.clearUserCategoryMember();
        ub.setUserCategoryMemberList(userCategoryMemberList);

        if (userCategoryMemberList) {
            own.updateAllUserCategoryMember();
            own.updateAllCategoryMemberCount();
        }
    };

    own.setUserHeadList = function (headList) {
        var hb = appContext.getBox(HeadBox);
        hb.setUserHeadList(headList);
    };

    own.addOrUpdateUserCategory = function (userCategory) {
        var userCategoryId = userCategory.id;
        own.addOrUpdateUserCategoryInfo(userCategory);
        own.updateUserCategoryMember(userCategoryId);
        own.updateCategoryMemberCount(userCategoryId);
    };

    own.addOrUpdateUserCategoryInfo = function (userCategory) {
        var ub = appContext.getBox(UserBox);
        ub.putUserCategory(userCategory);

        var categoryId = userCategory.id;
        var name = userCategory.name;
        userListPaneView.addOrUpdateCategory(categoryId, name);
    };

    /**
     * 重置分组成员
     *
     * @author XiaHui
     * @date 2017年9月5日 上午10:50:59
     */
    own.updateAllUserCategoryMember = function () {
        var ub = appContext.getBox(UserBox);
        var userCategoryList = ub.getUserCategoryList();

        var length = userCategoryList.length;
        for (var i = 0; i < length; i++) {
            var userCategory = userCategoryList[i];
            own.updateUserCategoryMember(userCategory.id);
        }
    };

    /**
     * 重置分组成员
     *
     * @author XiaHui
     * @date 2017年9月5日 上午10:50:59
     */
    own.updateUserCategoryMember = function (userCategoryId) {

        userListPaneView.clearCategoryMember(userCategoryId);

        var ub = appContext.getBox(UserBox);
        var list = ub.getUserCategoryMemberList(userCategoryId);
        if (!isEmpty(list)) {
            var length = list.length;
            for (var i = 0; i < length; i++) {
                var m = list[i];
                var userData = ub.getUserData(m.memberUserId);
                if (userData) {
                    var nickname = userData.nickname;
                    var name = userData.name;
                    var account = userData.account;
                    var userHead = userData.head;

                    var categoryId = userCategoryId;
                    var itemId = userData.id;
                    var showName = getShowName(userData);
                    var head = getHeadImage(userData);

                    var status = userData.status;
                    var gray = isOffline(status);
                    userListPaneView.addOrUpdateItem(categoryId, itemId, showName, head, gray);
                }
            }
        }
    };

    /**
     * 更新所有的好友分组的在线人数和总人数
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     */
    own.updateAllCategoryMemberCount = function () {
        var ub = appContext.getBox(UserBox);
        var userCategoryList = ub.getUserCategoryList();

        var length = userCategoryList.length;
        for (var i = 0; i < length; i++) {
            var userCategory = userCategoryList[i];
            own.updateCategoryMemberCount(userCategory.id);
        }
    };

    /**
     * 更新好友分组的总数量和在线人数
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param userCategoryId
     */
    own.updateCategoryMemberCount = function (userCategoryId) {
        var ub = appContext.getBox(UserBox);
        var userCategoryMemberList = ub.getUserCategoryMemberList(userCategoryId);
        var totalCount = 0;
        var onlineCount = 0;
        var length = userCategoryMemberList.length;
        for (var i = 0; i < length; i++) {
            var userCategoryMember = userCategoryMemberList[i];
            var userData = ub.getUserData(userCategoryMember.memberUserId);
            if (!isEmpty(userData)) {
                totalCount++;
                if (!isOffline(userData.status)) {
                    onlineCount++;
                }
            }
        }
        var countText = "[" + onlineCount + "/" + totalCount + "]";
        userListPaneView.updateCategoryMemberCount(userCategoryId, countText);
    };

    own.add = function (userData, userCategoryMember) {
        own.addUserData(userData);
        own.addUserCategoryMember(userCategoryMember);
    };

    own.addUserData = function (userData) {
        var userId = userData.getId();
        var ub = appContext.getBox(UserBox);
        ub.putUserData(userData);
        var list = ub.getUserInCategoryMemberListByUserId(userId);
        if (list) {
            var length = list.length;
            for (var i = 0; i < length; i++) {
                var ucm = list[i];
                if (ucm) {
                    var userCategoryId = ucm.userCategoryId;


                    if (userData) {
                        var nickname = userData.nickname;
                        var name = userData.name;
                        var account = userData.account;
                        var userHead = userData.head;

                        var categoryId = userCategoryId;
                        var itemId = userData.id;
                        var showName = getShowName(userData);
                        var head = getHeadImage(userData);

                        var status = userData.status;
                        var gray = isOffline(status);
                        userListPaneView.addOrUpdateItem(categoryId, itemId, showName, head, gray);
                    }
                    own.updateCategoryMemberCount(userCategoryId);
                }
            }
        }
    };

    /***
     * 插入好友分组信息
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param userCategoryMember
     */
    own.addUserCategoryMember = function (userCategoryMember) {
        var ub = appContext.getBox(UserBox);
        ub.putUserCategoryMember(userCategoryMember);

        var userId = userCategoryMember.memberUserId;
        var userCategoryId = userCategoryMember.userCategoryId;

        var userData = ub.getUserData(userId);
        if (userData) {
            var nickname = userData.nickname;
            var name = userData.name;
            var account = userData.account;
            var userHead = userData.head;

            var categoryId = userCategoryId;
            var itemId = userData.id;
            var showName = getShowName(userData);
            var head = getHeadImage(userData);

            var status = userData.status;
            var gray = isOffline(status);
            userListPaneView.addOrUpdateItem(categoryId, itemId, showName, head, gray);
            own.updateCategoryMemberCount(userCategoryId);
        }
    };

    /**
     * 执行用户信息更新操作
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param userData
     */
    own.updateUserData = function (userData) {
        if (userData) {
            var ub = appContext.getBox(UserBox);
            ub.putUserData(userData);

            var userId = userData.id;

            if (userInfoPaneView.isUserInfoShowing(userId)) {
                userInfoPaneView.showInfo(userData);
            }

            var list = ub.getUserInCategoryMemberListByUserId(userId);
            if (list) {
                var length = list.length;
                for (var i = 0; i < length; i++) {
                    var ucm = list[i];
                    if (ucm) {
                        var userCategoryId = ucm.userCategoryId;
                        var nickname = userData.nickname;
                        var name = userData.name;
                        var account = userData.account;
                        var userHead = userData.head;

                        var categoryId = userCategoryId;
                        var itemId = userData.id;
                        var showName = getShowName(userData);
                        var head = getHeadImage(userData);

                        var status = userData.status;
                        var gray = isOffline(status);
                        userListPaneView.addOrUpdateItem(categoryId, itemId, showName, head, gray);
                        own.updateCategoryMemberCount(userCategoryId);
                    }
                }
            }
        }
    };

    own.updateRemarkName = function (userId, remarkName) {
        var ub = appContext.getBox(UserBox);

        var list = ub.getUserInCategoryMemberListByUserId(userId);
        if (list) {
            var length = list.length;
            for (var i = 0; i < length; i++) {
                var userCategoryMember = list[i];
                if (userCategoryMember) {
                    userCategoryMember.remark = remarkName;
                    own.addUserCategoryMember(userCategoryMember);
                }
            }
        }
    };

    own.refreshUserHead = function () {
        userListPaneView.refreshUserHead();
    };

    own.deleteUser = function (userId) {
        var ub = appContext.getBox(UserBox);
        var ucm = ub.removeUserCategoryMember(userId);
        if (ucm) {
            userListPaneView.removeCategoryMember(ucm.userCategoryId, userId);
        }
    };
}


function GroupListManager(appContext) {
    var own = this;

    own.setGroupCategoryWithGroupList = function (groupCategoryList, groupList, groupCategoryMemberList) {
        own.setGroupCategoryList(groupCategoryList);
        own.setGroupList(groupList);
        own.setGroupCategoryMemberList(groupCategoryMemberList);
    };

    own.setGroupCategoryList = function (groupCategoryList) {
        var gb = appContext.getBox(GroupBox);
        gb.clearGroupCategory();

        groupListPaneView.clearCategory();

        if (groupCategoryList) {
            var length = groupCategoryList.length;
            for (var i = 0; i < length; i++) {
                var groupCategory = groupCategoryList[i];
                own.addOrUpdateGroupCategoryInfo(groupCategory);
            }
            own.updateAllGroupCategoryMember();
            own.updateAllCategoryMemberCount();
        }
    };

    own.setGroupList = function (groupList) {
        var gb = appContext.getBox(GroupBox);
        gb.setGroupList(groupList);
        if (groupList) {
            own.updateAllGroupCategoryMember();
            own.updateAllCategoryMemberCount();
        }
    };

    own.setGroupCategoryMemberList = function (groupCategoryMemberList) {
        var gb = appContext.getBox(GroupBox);
        gb.clearGroupCategoryMember();
        gb.setGroupCategoryMemberList(groupCategoryMemberList);

        if (groupCategoryMemberList) {
            own.updateAllGroupCategoryMember();
            own.updateAllCategoryMemberCount();
        }
    };

    own.setGroupHeadList = function (headList) {
        var hb = appContext.getBox(HeadBox);
        hb.setGroupHeadList(headList);
    };

    own.addOrUpdateGroupCategory = function (groupCategory) {
        var groupCategoryId = groupCategory.id;
        own.addOrUpdateGroupCategoryInfo(groupCategory);
        own.updateGroupCategoryMember(groupCategoryId);
        own.updateCategoryMemberCount(groupCategoryId);
    };

    own.addOrUpdateGroupCategoryInfo = function (groupCategory) {
        var gb = appContext.getBox(GroupBox);
        gb.putGroupCategory(groupCategory);

        var categoryId = groupCategory.id;
        var name = groupCategory.name;
        groupListPaneView.addOrUpdateCategory(categoryId, name);
    };

    /**
     * 重置分组成员
     *
     * @author XiaHui
     * @date 2017年9月5日 上午10:50:59
     */
    own.updateAllGroupCategoryMember = function () {
        var gb = appContext.getBox(GroupBox);
        var groupCategoryList = gb.getGroupCategoryList();

        var length = groupCategoryList.length;
        for (var i = 0; i < length; i++) {
            var groupCategory = groupCategoryList[i];
            own.updateGroupCategoryMember(groupCategory.id);
        }
    };

    /**
     * 重置分组成员
     *
     * @author XiaHui
     * @date 2017年9月5日 上午10:50:59
     */
    own.updateGroupCategoryMember = function (groupCategoryId) {


        groupListPaneView.clearCategoryMember(groupCategoryId);

        var gb = appContext.getBox(GroupBox);
        var list = gb.getGroupCategoryMemberList(groupCategoryId);
        if (list) {
            var length = list.length;
            for (var i = 0; i < length; i++) {
                var m = list[i];

                var group = gb.getGroup(m.groupId);
                if (group) {

                    var name = group.name;
                    var groupHead = group.head;
                    var categoryId = groupCategoryId;
                    var itemId = group.id;

                    var head = group_head_images + groupHead + ".png";
                    groupListPaneView.addOrUpdateItem(categoryId, itemId, name, head);
                }
            }
        }
    };

    /**
     * 更新所有的好友分组的在线人数和总人数
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyGroup: XiaHui
     * @ModifyDate: 2016年2月16日
     */
    own.updateAllCategoryMemberCount = function () {
        var gb = appContext.getBox(GroupBox);
        var groupCategoryList = gb.getGroupCategoryList();
        var length = groupCategoryList.length;
        for (var i = 0; i < length; i++) {
            var groupCategory = groupCategoryList[i];
            own.updateCategoryMemberCount(groupCategory.id);
        }
    };

    /**
     * 更新好友分组的总数量和在线人数
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyGroup: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param groupCategoryId
     */
    own.updateCategoryMemberCount = function (groupCategoryId) {
        var gb = appContext.getBox(GroupBox);
        var totalCount = gb.getGroupCategoryMemberSize(groupCategoryId);

        var countText = "[" + totalCount + "]";
        groupListPaneView.updateCategoryMemberCount(groupCategoryId, countText);
    };

    own.addGroupWithGroupCategoryMember = function (group, groupCategoryMember) {
        own.addGroup(group);
        own.addGroupCategoryMember(groupCategoryMember);
    };

    own.addGroup = function (group) {
        var groupId = group.id;
        var gb = appContext.getBox(GroupBox);
        gb.putGroup(group);

        var list = gb.getGroupInCategoryMemberListByGroupId(groupId);
        if (list) {
            var length = list.length;
            for (var i = 0; i < length; i++) {
                var ucm = list[i];
                if (ucm) {
                    var groupCategoryId = ucm.groupCategoryId;

                    var name = group.name;
                    var groupHead = group.head;
                    var categoryId = groupCategoryId;
                    var itemId = group.id;

                    var head = group_head_images + groupHead + ".png";
                    groupListPaneView.addOrUpdateItem(categoryId, itemId, name, head);
                    updateCategoryMemberCount(groupCategoryId);
                }
            }
        }
    };

    /***
     * 插入好友分组信息
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyGroup: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param groupCategoryMember
     */
    own.addGroupCategoryMember = function (groupCategoryMember) {
        var gb = appContext.getBox(GroupBox);
        gb.putGroupCategoryMember(groupCategoryMember);

        var groupId = groupCategoryMember.groupId;
        var groupCategoryId = groupCategoryMember.groupCategoryId;

        var group = gb.getGroup(groupId);
        if (group) {
            var name = group.name;
            var groupHead = group.head;
            var categoryId = groupCategoryId;
            var itemId = group.id;

            var head = group_head_images + groupHead + ".png";
            groupListPaneView.addOrUpdateItem(categoryId, itemId, name, head);
            own.updateCategoryMemberCount(groupCategoryId);
        }
    };

    /**
     * 执行用户信息更新操作
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyGroup: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param group
     */
    own.updateGroup = function (group) {
        if (group) {
            var gb = appContext.getBox(GroupBox);
            gb.putGroup(group);

            var groupId = group.id;

            var list = gb.getGroupInCategoryMemberListByGroupId(groupId);
            if (list) {
                var length = list.length;
                for (var i = 0; i < length; i++) {
                    var ucm = list[i];
                    if (ucm) {
                        var groupCategoryId = ucm.groupCategoryId;
                        var name = group.name;
                        var groupHead = group.head;
                        var categoryId = groupCategoryId;
                        var itemId = group.id;

                        var head = group_head_images + groupHead + ".png";
                        groupListPaneView.addOrUpdateItem(categoryId, itemId, name, head);
                        own.updateCategoryMemberCount(groupCategoryId);
                    }
                }
            }
        }
    };
}

function RoomListManager(appContext) {
    var own = this;
    own.setRoomList = function (roomList) {
        var roomBox = appContext.getBox(RoomBox);
        roomBox.clearRoom();
        roomBox.setRoomList(roomList);
        var roomListSize = roomBox.getRoomListSize();
        var categoryId = "room_chat_list";
        roomListPaneView.clearCategoryMember(categoryId);
        if (roomList) {
            var length = roomList.length;
            for (var i = 0; i < length; i++) {
                var room = roomList[i];
                if (room) {

                    var roomId = room.id;
                    var name = room.name;
                    var head = room.head;
                    var itemId = roomId;

                    var head = group_head_images + 1 + ".png";
                    roomListPaneView.addOrUpdateItem(categoryId, itemId, name, head);
                }
            }
        }
        var countText = "[" + roomListSize + "]";
        roomListPaneView.updateCategoryMemberCount(categoryId, countText);
    }
}

function LastManager(appContext) {
    var own = this;

    own.addOrUpdateLastUserData = function (userData) {
        var userHead = userData.head;
        var itemId = userData.id;
        var showName = getShowName(userData);
        var head = getHeadImage(userData);

        var status = userData.status;
        var gray = isOffline(status);

        var type = "user";
        chatListView.addOrUpdateItem(type, itemId, showName, head, gray);
    }
}

function UserChatManager(appContext) {
    var own = this;

    var userChatLoadMap = new Map();
    var messageIdMap = new Map();
    var redMap = new Map();

    own.putUserId = function (userId) {
        redMap.put(userId, true);
    };

    own.putUserMessageId = function (userId, messageId) {
        var key = userId;

        var ids = messageIdMap.get(key);
        if (isEmpty(ids)) {
            ids = [];
            messageIdMap.put(key, ids);
        }
        ids.splice(0, 0, messageId);

        var size = ids.length;
        if (size > 50) {
            ids.splice((size - 1), 1);
        }
    };

    own.removeUserChatById = function (userId) {
        var itemId = userId;
        var showing = userChatPaneBox.isChatPaneShowing(itemId);
        if (showing) {
            var text = "";
            text = text + "<div class=\"chat-no\">";
            text = text + "   <img class=\"chat-no-img\" src=\"images/Logo/logo_256.png\" alt=\"\">";
            text = text + "</div>";
            chatPaneView.html(text);
        }

        var pb = appContext.getBox(PersonalBox);
        var ownerUserId = pb.getOwnerUserId();

        var ucs = appContext.getSender(UserChatSender);
        ucs.removeLastChat(ownerUserId, userId);
    };

    own.showUserChat = function (userData) {
        var userId = userData.id;

        var userHead = userData.head;
        var itemId = userData.id;
        var showName = getShowName(userData);
        var head = getHeadImage(userData);

        var status = userData.status;
        var gray = isOffline(status);

        var type = "user";
        // chatListView.addOrUpdateItem(type, itemId, showName, head, gray);


        var showing = userChatPaneBox.isChatPaneShowing(itemId);
        if (showing) {

        } else {
            var chatPane = userChatPaneBox.getChatPane(itemId, showName);
            if (chatPane) {
                //chatPaneView.html("")
                chatPaneView.html(chatPane);
                userChatPaneBox.toBottom(itemId, chatPane);
            }
        }

        redMap.remove(userId);

        if (redMap.size() == 0) {
            // mainView.setTabRed(0, false);
        }

        var key = userId;
        if (!userChatLoadMap.containsKey(key)) {
            var ids = messageIdMap.get(key);
            if (isEmpty(ids) || ids.length < 50) {
                showChatHistory(userData);
            }
            userChatLoadMap.put(key, true);
        }
    };


    function showChatHistory(userData) {
        var userId = userData.id;
        var dataBackAction = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;
            var sendUserId = body.sendUserId;
            var receiveUserId = body.receiveUserId;
            var contents = body.contents;
            var page = body.page;

            if (info && info.success) {

                if (!isEmpty(contents)) {

                    var key = userId;

                    var ids = messageIdMap.get(key);
                    var idMap = new Map();
                    if (!isEmpty(ids)) {
                        var l = ids.length;
                        for (var i = 0; i < l; i++) {
                            var id = ids[i];
                            idMap.put(id, id);
                        }
                    }
                    var list = [];

                    var size = contents.length;

                    for (var i = size - 1; i >= 0; i--) {
                        var content = contents[i];
                        var messageKey = content.messageKey;
                        if (!idMap.containsKey(messageKey)) {
                            list.push(content);
                        }
                    }
                    if (!isEmpty(list)) {
                        size = list.length;
                        for (var i = 0; i < size; i++) {
                            var chd = list[i];

                            var pb = appContext.getBox(PersonalBox);

                            var sendUserData = chd.sendUserData;
                            var ownUser = pb.getUserData();

                            var content = chd.content;

                            var isOwn = sendUserData.id == (ownUser.id);

                            var showUserId = userData.id;
                            var showUserData = userData;
                            var chatUserData = (isOwn) ? ownUser : showUserData;
                            var showName = getShowName(showUserData);

                            showChatMessage(showUserId, showName, isOwn, chatUserData, content);
                        }
                    }
                }
            }
        };

        var pb = appContext.getBox(PersonalBox);
        var ownerUserId = pb.getOwnerUserId();

        var chatQuery = {};
        var page = {"pageSize": 50, "pageNumber": 1};


        var ucs = appContext.getSender(UserChatSender);
        var sendUserId = (isEmpty(ownerUserId)) ? "00000" : ownerUserId;
        var receiveUserId = userData.id;

        ucs.queryUserChatLog(sendUserId, receiveUserId, chatQuery, page, dataBackAction);
    }
    ;


    function showChatMessage(showUserId, itemName, isOwn, userData, content) {

        var showName = getShowName(userData);
        var userHead = userData.head;
        var head = getHeadImage(userData);
        userChatPaneBox.insertBeforeMessage(showUserId, itemName, isOwn, head, showName, content);
    }
}

function GroupChatManager(appContext) {
    var own = this;

    var groupChatLoadMap = new Map();
    var messageIdMap = new Map();
    var redMap = new Map();

    own.putGroupId = function (groupId) {
        redMap.put(groupId, true);
    };

    own.putGroupMessageId = function (groupId, messageId) {
        setMessageId(groupId, messageId);
    };

    own.hasGroupMessageId = function (groupId, messageId) {
        return hasMessageId(groupId, messageId);
    };

    own.removeGroupChatById = function (groupId) {
        var itemId = groupId;
        var showing = groupChatPaneBox.isChatPaneShowing(itemId);
        if (showing) {
            var text = "";
            text = text + "<div class=\"chat-no\">";
            text = text + "   <img class=\"chat-no-img\" src=\"images/Logo/logo_256.png\" alt=\"\">";
            text = text + "</div>";
            chatPaneView.html(text);
        }
    };

    own.showGroupChat = function (group) {
        var groupId = group.id;

        var groupHead = group.head;
        var itemId = group.id;
        var showName = group.name;
        var head = group_head_images + "1.png";


        var type = "group";
        // chatListView.addOrUpdateItem(type, itemId, showName, head, gray);


        var showing = groupChatPaneBox.isChatPaneShowing(itemId);
        if (showing) {

        } else {
            var chatPane = groupChatPaneBox.getChatPane(itemId, showName);
            if (chatPane) {
                //chatPaneView.html("")
                chatPaneView.html(chatPane);
                groupChatPaneBox.toBottom(itemId, chatPane);
            }
        }

        redMap.remove(groupId);

        if (redMap.size() == 0) {
            // mainView.setTabRed(0, false);
        }

        var key = groupId;
        if (!groupChatLoadMap.containsKey(key)) {
            var map = messageIdMap.get(key);
            if (isEmpty(map) || map.size() < 500) {
                showChatHistory(group);
            }
            groupChatLoadMap.put(key, true);
        }
    };

    function setMessageId(groupId, messageId) {

        var map = messageIdMap.get(groupId);
        if (isEmpty(map)) {
            map = new Map();
            messageIdMap.put(groupId, map);
        }
        if (map.size() > 5000) {
            map.clear();
        }
        map.put(messageId, messageId);
    }
    ;

    function hasMessageId(groupId, messageId) {
        var has = false;

        var map = messageIdMap.get(groupId);
        if (!isEmpty(map)) {
            has = map.containsKey(messageId);
        }
        return has;
    }
    ;

    function showChatHistory(group) {
        var groupId = group.id;

        var showName = group.name;

        var dataBackAction = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;

            var contents = body.contents;
            var page = body.page;

            if (info && info.success) {

                if (!isEmpty(contents)) {

                    var key = groupId;


                    var list = [];

                    var size = contents.length;

                    for (var i = size - 1; i >= 0; i--) {
                        var content = contents[i];
                        var messageId = content.messageKey;
                        if (!hasMessageId(groupId, messageId)) {
                            list.push(content);
                            setMessageId(groupId, messageId);
                        }
                    }
                    if (!isEmpty(list)) {
                        size = list.length;
                        for (var i = 0; i < size; i++) {
                            var chd = list[i];

                            var pb = appContext.getBox(PersonalBox);
                            var ownUser = pb.getUserData();

                            var userData = chd.userData;
                            var content = chd.content;

                            var isOwn = userData.id == (ownUser.id);

                            var chatUserData = (isOwn) ? ownUser : userData;

                            showChatMessage(groupId, showName, isOwn, chatUserData, content);
                        }
                    }
                }
            }
        };

        var chatQuery = {};
        var page = {"pageSize": 50, "pageNumber": 1};

        var rcs = appContext.getSender(GroupChatSender);
        rcs.queryGroupChatLog(groupId, chatQuery, page, dataBackAction);
    }

    function showChatMessage(groupId, itemName, isOwn, userData, content) {

        var showName = getShowName(userData);
        var userHead = userData.head;
        var head = getHeadImage(userData);
        groupChatPaneBox.insertBeforeMessage(groupId, itemName, isOwn, head, showName, content);
    }
}

function RoomChatManager(appContext) {
    var own = this;

    var roomChatLoadMap = new Map();
    var messageIdMap = new Map();
    var redMap = new Map();

    own.putRoomId = function (roomId) {
        redMap.put(roomId, true);
    };

    own.putRoomMessageId = function (roomId, messageId) {
        setMessageId(roomId, messageId);
    };

    own.hasRoomMessageId = function (roomId, messageId) {
        return hasMessageId(roomId, messageId);
    };

    own.removeRoomChatById = function (roomId) {
        var itemId = roomId;
        var showing = roomChatPaneBox.isChatPaneShowing(itemId);
        if (showing) {
            var text = "";
            text = text + "<div class=\"chat-no\">";
            text = text + "   <img class=\"chat-no-img\" src=\"images/Logo/logo_256.png\" alt=\"\">";
            text = text + "</div>";
            chatPaneView.html(text);
        }
    };

    own.showRoomChat = function (room) {
        var roomId = room.id;

        var roomHead = room.head;
        var itemId = room.id;
        var showName = room.name;
        var head = group_head_images + "1.png";


        var type = "room";
        // chatListView.addOrUpdateItem(type, itemId, showName, head, gray);


        var showing = roomChatPaneBox.isChatPaneShowing(itemId);
        if (showing) {

        } else {
            var chatPane = roomChatPaneBox.getChatPane(itemId, showName);
            if (chatPane) {
                //chatPaneView.html("")
                chatPaneView.html(chatPane);
                roomChatPaneBox.toBottom(itemId, chatPane);
            }
        }

        redMap.remove(roomId);

        if (redMap.size() == 0) {
            // mainView.setTabRed(0, false);
        }

        var key = roomId;
        if (!roomChatLoadMap.containsKey(key)) {
            var map = messageIdMap.get(key);
            if (isEmpty(map) || map.size() < 500) {
                showChatHistory(room);
            }
            roomChatLoadMap.put(key, true);
        }
    }

    function setMessageId(roomId, messageId) {

        var map = messageIdMap.get(roomId);
        if (isEmpty(map)) {
            map = new Map();
            messageIdMap.put(roomId, map);
        }
        if (map.size() > 5000) {
            map.clear();
        }
        map.put(messageId, messageId);
    }
    ;

    function hasMessageId(roomId, messageId) {
        var has = false;

        var map = messageIdMap.get(roomId);
        if (!isEmpty(map)) {
            has = map.containsKey(messageId);
        }
        return has;
    }
    ;

    function showChatHistory(room) {
        var roomId = room.id;

        var showName = room.name;

        var dataBackAction = function (message) {
            var head = message.head;
            var body = message.body;
            var info = message.info;

            var contents = body.contents;
            var page = body.page;

            if (info && info.success) {

                if (!isEmpty(contents)) {

                    var key = roomId;


                    var list = [];

                    var size = contents.length;

                    for (var i = size - 1; i >= 0; i--) {
                        var content = contents[i];
                        var messageId = content.messageKey;
                        if (!hasMessageId(roomId, messageId)) {
                            list.push(content);
                            setMessageId(roomId, messageId);
                        }
                    }
                    if (!isEmpty(list)) {
                        size = list.length;
                        for (var i = 0; i < size; i++) {
                            var chd = list[i];

                            var pb = appContext.getBox(PersonalBox);
                            var ownUser = pb.getUserData();

                            var userData = chd.userData;
                            var content = chd.content;

                            var isOwn = userData.id == (ownUser.id);

                            var chatUserData = (isOwn) ? ownUser : userData;

                            showChatMessage(roomId, showName, isOwn, chatUserData, content);
                        }
                    }
                }
            }
        };

        var chatQuery = {};
        var page = {"pageSize": 50, "pageNumber": 1};

        var rcs = appContext.getSender(RoomChatSender);
        rcs.queryRoomChatLog(roomId, chatQuery, page, dataBackAction);
    }

    function showChatMessage(roomId, itemName, isOwn, userData, content) {

        var showName = getShowName(userData);
        var userHead = userData.head;
        var head = getHeadImage(userData);
        roomChatPaneBox.insertBeforeMessage(roomId, itemName, isOwn, head, showName, content);
    }
}
