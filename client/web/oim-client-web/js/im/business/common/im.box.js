/* global public_static_status_offline */

function UserBox(app) {
    var own = this;


    /** 所有用户<userId,userData> */
    var allUserMap = new Map();
    /** 所有分组<userCategoryId,UserCategory>*/
    var allUserCategoryMap = new Map();

    /** 分组中的成员列表  <userCategoryId,Map<userId, UserCategoryMember>>*/
    var categoryMemberListMap = new Map();
    /** 用户所在的分组 <userId,Map<userCategoryId, UserCategoryMember>>*/
    var userInCategoryMemberMap = new Map();

    /**
     * 存放用户
     *
     * @author: XiaHui
     * @param userData
     */
    own.putUserData = function (userData) {
        var userId = userData.id;
        allUserMap.put(userId, userData);
    };

    /**
     * 存放好友分组
     *
     * @author: XiaHui
     * @param userCategory
     */
    own.putUserCategory = function (userCategory) {
        var userCategoryId = userCategory.id;
        allUserCategoryMap.put(userCategoryId, userCategory);
    };

    /**
     * 存放分组成员
     *
     * @author: XiaHui
     * @param userCategoryMember
     * @createDate: 2017年8月21日 下午5:34:23
     * @update: XiaHui
     * @updateDate: 2017年8月21日 下午5:34:23
     */
    own.putUserCategoryMember = function (userCategoryMember) {

        var memberUserId = userCategoryMember.memberUserId;
        var userCategoryId = userCategoryMember.userCategoryId;

        var userCategoryMemberMap = own.getUserCategoryMemberMap(userCategoryId);
        userCategoryMemberMap.put(memberUserId, userCategoryMember);

        var userInCategoryMemberMap = own.getUserInCategoryMemberMapByUserId(memberUserId);
        userInCategoryMemberMap.put(userCategoryId, userCategoryMember);

    };

    own.clearUserCategory = function () {
        allUserCategoryMap.clear();
    };

    own.clearUserCategoryMember = function () {
        categoryMemberListMap.clear();
        userInCategoryMemberMap.clear();
    };

    own.setUserCategoryList = function (userCategoryList) {
        if (userCategoryList) {
            var length = userCategoryList.length;
            for (var i = 0; i < length; i++) {
                var userCategory = userCategoryList[i];
                own.putUserCategory(userCategory);
            }
        }
    };

    own.setUserDataList = function (userDataList) {
        if (userDataList) {
            var length = userDataList.length;
            for (var i = 0; i < length; i++) {
                var userData = userDataList[i];
                own.putUserData(userData);
            }
        }
    };

    own.setUserCategoryMemberList = function (userCategoryMemberList) {
        if (userCategoryMemberList) {
            var length = userCategoryMemberList.length;
            for (var i = 0; i < length; i++) {
                var userCategoryMember = userCategoryMemberList[i];
                own.putUserCategoryMember(userCategoryMember);
            }
        }
    };


    own.removeUserCategoryMemberList = function (userId) {
        var map = userInCategoryMemberMap.remove(userId);
        var list;
        if (!isEmpty(map)) {
            list = (map.values());
            var length = list.length;
            for (var i = 0; i < length; i++) {
                var userCategoryMember = list[i];
                if (userCategoryMember) {
                    var userCategoryId = userCategoryMember.userCategoryId;
                    var userCategoryMemberMap = getUserCategoryMemberMap(userCategoryId);
                    userCategoryMemberMap.remove(userId);
                }
            }
        }
        return list;
    };

    /**
     * 移除分组中的好友
     *
     * @author XiaHui
     * @date 2017年9月5日 上午10:01:44
     * @param userId
     * @return
     */
    own.removeUserCategoryMember = function (userCategoryId, userId) {

        var userCategoryMemberMap = own.getUserCategoryMemberMap(userCategoryId);
        var userCategoryMember = userCategoryMemberMap.remove(userId);
        var map = userInCategoryMemberMap.get(userId);
        if (map) {
            map.remove(userCategoryId);
            if (map.size() == 0) {
                userInCategoryMemberMap.remove(userId);
            }
        }
        return userCategoryMember;
    };

    /**
     * 获取分组成员列表
     *
     * @author: XiaHui
     * @param userCategoryId
     * @return
     * @createDate: 2017年8月21日 下午5:36:30
     * @update: XiaHui
     * @updateDate: 2017年8月21日 下午5:36:30
     */
    own.getUserCategoryMemberList = function (userCategoryId) {
        var userCategoryMemberMap = own.getUserCategoryMemberMap(userCategoryId);
        return userCategoryMemberMap.values();
    };

    own.getUserCategoryMemberMap = function (userCategoryId) {
        var userCategoryMemberMap = categoryMemberListMap.get(userCategoryId);
        if (!userCategoryMemberMap) {
            userCategoryMemberMap = new Map();
            categoryMemberListMap.put(userCategoryId, userCategoryMemberMap);
        }
        return userCategoryMemberMap;
    };

    own.getUserCategoryMemberSize = function (userCategoryId) {
        var userCategoryMemberList = categoryMemberListMap.get(userCategoryId);
        return isEmpty(userCategoryMemberList) ? 0 : userCategoryMemberList.size();
    };

    own.getUserCategory = function (userCategoryId) {
        return allUserCategoryMap.get(userCategoryId);
    };

    own.hasUserCategory = function (userCategoryId) {
        return allUserCategoryMap.containsKey(userCategoryId);
    };

    own.getUserCategoryList = function () {
        return allUserCategoryMap.values();
    };

    // own.getUserCategoryMemberByUserId = function (userId) {
    //     var ucm = userInCategoryMemberMap.get(userId);
    //     return ucm;
    // };

    own.getUserInCategoryMemberListByUserId = function (userId) {
        var map = own.getUserInCategoryMemberMapByUserId(userId);
        return map.values();
    };

    own.getUserInCategoryMemberMapByUserId = function (userId) {
        var map = userInCategoryMemberMap.get(userId);
        if (map == null) {
            map = new Map();
            userInCategoryMemberMap.put(userId, map);
        }
        return map;
    };
    /**
     * 是否在好友列表
     *
     * @author: XiaHui
     * @param userId
     */
    own.inMemberList = function (userId) {
        var has = userInCategoryMemberMap.containsKey(userId);
        return has;
    };

    own.getUserData = function (userId) {
        var ud = allUserMap.get(userId);
        return ud;
    };

    own.getUserStatus = function (userId) {
        var ud = own.getUserData(userId);
        var status = isEmpty(ud) ? public_static_status_offline : ud.status;
        return status;
    };

    own.isOnline = function (userId) {
        var status = own.getUserStatus(userId);
        var mark = isOffline(status);
        return !mark;
    };

    own.findUserDataList = function (text) {
        var list = [];
        var allList = allUserMap.values();
        //int allSize = allList.size();
        var l = allList.length;
        var size = 0;

        for (var i = 0; i < l; i++) {
            var ud = allList[i];
            var account = ud.account;
            var email = ud.email;
            var mobile = ud.mobile;
            var name = ud.name;
            var nickname = ud.nickname;
            var mark = false;

            if (null != account && !mark) {
                mark = (account.indexOf(text) != -1);
            }
            if (null != email && !mark) {
                mark = (email.indexOf(text) != -1);
            }
            if (null != mobile && !mark) {
                mark = (mobile.indexOf(text) != -1);
            }
            if (null != name && !mark) {
                mark = (name.indexOf(text) != -1);
            }
            if (null != nickname && !mark) {
                mark = (nickname.indexOf(text) != -1);
            }

            if (mark) {
                list.push(ud);
                size++;
            }

            if (size > 20) {
                return list;
            }
        }
        return list;
    };
}

function GroupBox(app) {
    var own = this;

    var allGroupMap = new Map();

    var allGroupCategoryMap = new Map();
    var categoryMemberListMap = new Map();
    var groupInCategoryMemberMap = new Map();

    own.putGroup = function (group) {
        var groupId = group.id;
        allGroupMap.put(groupId, group);
    };

    own.putGroupCategory = function (groupCategory) {
        allGroupCategoryMap.put(groupCategory.id, groupCategory);
    };

    /**
     * 存放分组成员
     *
     * @author: XiaHui
     * @param groupCategoryMember
     * @createDate: 2017年8月21日 下午5:34:23
     * @update: XiaHui
     * @updateDate: 2017年8月21日 下午5:34:23
     */
    own.putGroupCategoryMember = function (groupCategoryMember) {

        var groupId = groupCategoryMember.groupId;
        var groupCategoryId = groupCategoryMember.groupCategoryId;

        var groupCategoryMemberMap = own.getGroupCategoryMemberMap(groupCategoryId);
        groupCategoryMemberMap.put(groupId, groupCategoryMember);

        var groupInCategoryMap = own.getGroupInCategoryMemberMapByGroupId(groupId);
        groupInCategoryMap.put(groupCategoryId, groupCategoryMember);

    };

    own.clearGroupCategory = function () {
        allGroupCategoryMap.clear();
    };

    own.clearGroupCategoryMember = function () {
        categoryMemberListMap.clear();
        groupInCategoryMemberMap.clear();
    };

    own.setGroupCategoryList = function (groupCategoryList) {
        if (groupCategoryList) {
            var length = groupCategoryList.length;
            for (var i = 0; i < length; i++) {
                var groupCategory = groupCategoryList[i];
                own.putGroupCategory(groupCategory);
            }
        }
    };

    own.setGroupList = function (groupList) {
        if (groupList) {
            var length = groupList.length;
            for (var i = 0; i < length; i++) {
                var group = groupList[i];
                own.putGroup(group);
            }
        }
    };

    own.setGroupCategoryMemberList = function (groupCategoryMemberList) {
        if (groupCategoryMemberList) {
            var length = groupCategoryMemberList.length;
            for (var i = 0; i < length; i++) {
                var groupCategoryMember = groupCategoryMemberList[i];
                own.putGroupCategoryMember(groupCategoryMember);
            }
        }
    };

    own.removeGroupCategoryMemberList = function (groupId) {
        var list = [];
        var map = groupInCategoryMemberMap.remove(groupId);
        if (!isEmpty(map)) {
            list = (map.values());
            var length = list.length;
            for (var i = 0; i < length; i++) {
                var groupCategoryMember = list[i];
                if (groupCategoryMember) {
                    var groupCategoryMemberMap = getGroupCategoryMemberMap(groupCategoryMember.groupCategoryId);
                    groupCategoryMemberMap.remove(groupId);
                }
            }
        }
        return list;
    };

    own.removeGroupCategoryMember = function (groupCategoryId, groupId) {
        var groupCategoryMemberMap = own.getGroupCategoryMemberMap(groupCategoryId);
        var groupCategoryMember = groupCategoryMemberMap.remove(groupId);
        var map = groupInCategoryMemberMap.get(groupId);
        if (map) {
            map.remove(groupCategoryId);
            if (map.size() == 0) {
                groupInCategoryMemberMap.remove(groupId);
            }
        }
        return groupCategoryMember;
    };

    own.getGroupCategoryMemberList = function (groupCategoryId) {
        var groupCategoryMemberMap = own.getGroupCategoryMemberMap(groupCategoryId);
        var list = [];
        list = (groupCategoryMemberMap.values());
        return list;
    };

    own.getGroupCategoryMemberMap = function (groupCategoryId) {
        var groupCategoryMemberList = categoryMemberListMap.get(groupCategoryId);
        if (!groupCategoryMemberList) {
            groupCategoryMemberList = new Map();
            categoryMemberListMap.put(groupCategoryId, groupCategoryMemberList);
        }
        return groupCategoryMemberList;
    };

    own.getGroupCategoryMemberSize = function (groupCategoryId) {
        var groupCategoryMemberList = categoryMemberListMap.get(groupCategoryId);
        return isEmpty(groupCategoryMemberList) ? 0 : groupCategoryMemberList.size();
    };

    own.getGroupCategory = function (groupCategoryId) {
        return allGroupCategoryMap.get(groupCategoryId);
    };

    own.hasGroupCategory = function (groupCategoryId) {
        return allGroupCategoryMap.containsKey(groupCategoryId);
    };

    own.getGroupCategoryList = function () {
        return allGroupCategoryMap.values();
    };

    // public GroupCategoryMember getGroupCategoryMemberByGroupId(String
    // groupId) {
    // GroupCategoryMember ucm = groupInCategoryMemberMap.get(groupId);
    // return ucm;
    // }

    own.getGroupInCategoryMemberListByGroupId = function (groupId) {
        var list = [];
        var map = groupInCategoryMemberMap.get(groupId);
        if (map) {
            list = (map.values());
        }
        return list;
    };

    own.getGroupInCategoryMemberMapByGroupId = function (groupId) {
        var memberMap = groupInCategoryMemberMap.get(groupId);
        if (!memberMap) {
            memberMap = new Map();
            groupInCategoryMemberMap.put(groupId, memberMap);
        }
        return memberMap;
    };

    own.hasGroup = function (groupId) {
        return allGroupMap.containsKey(groupId);
    };

    own.getGroup = function (groupId) {
        return allGroupMap.get(groupId);
    };
}


function RoomBox(app) {
    var own = this;

    /** 所有聊天室<userId,room> */
    var allRoomMap = new Map();

    own.putRoom = function (room) {
        var userId = room.id;
        allRoomMap.put(userId, room);
    };

    own.setRoomList = function (roomList) {
        if (roomList) {
            var length = roomList.length;
            for (var i = 0; i < length; i++) {
                var room = roomList[i];
                own.putRoom(room);
            }
        }
    };

    own.clearRoom = function () {
        allRoomMap.clear();
    };

    own.getRoomList = function () {
        return allRoomMap.values();
    };

    own.getRoomListSize = function () {
        return allRoomMap.size();
    };

    own.inRoomList = function (roomId) {
        var has = allRoomMap.containsKey(roomId);
        return has;
    };

    own.getRoom = function (roomId) {
        var room = allRoomMap.get(roomId);
        return room;
    };
}

function PersonalBox(app) {
    var own = this;

    var userData;
    var userHead;
    var loginData;

    own.isLogin = function () {
        var user = own.getUserData();
        var isLogin = !isEmpty(user);
        return isLogin;
    };

    own.getOwnerUserId = function () {
        var userData = app.session.get("loginUser");
        return null == userData ? "" : userData.id;
    };

    own.getUserData = function () {
        var userData = app.session.get("loginUser");
        return userData;
    };

    own.setUserData = function (userData) {
        app.session.put("loginUser", userData);
    };

    /**
     * 移除用户信息
     */
    own.removeUserData = function () {
        app.session.remove("loginUser");
    };

    own.getLoginData = function () {
        var loginData = app.session.get("loginData");
        return loginData;
    };

    own.setLoginData = function (loginData) {
        app.session.put("loginData", loginData);
    };

    /**
     * 移除登录信息
     */
    own.removeLoginData = function () {
        app.session.remove("loginData");
    };

    own.getUserHead = function () {
        return userHead;
    };

    own.setUserHead = function (uh) {
        userHead = uh;
    };


    own.getDefaultHead = function () {
        var ud = getUserData();
        var head = !isEmpty(ud) ? ud.head : "1";
        return head;
    };
}