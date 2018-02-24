/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global PersonalController, UserChatController, socketHost */

var chatListView;

var userListPaneView;
var groupListPaneView;
var roomListPaneView;

var userInfoPaneView;
var groupInfoPaneView;
var roomInfoPaneView;

var userChatPaneBox;
var groupChatPaneBox;
var roomChatPaneBox;

var chatPaneView;

var roomMemberPaneView;

var loginView;
var mainView;
var tabView;
var appContext;
var sinaEmotion;
$(function () {
    initURL();
    sinaEmotion = new SinaEmotion();
    initView();
    //initTestData();
    initEvent();
    initApp();
});

function initURL() {
    var path = (isEmpty(webSocketPath)) ? "/oim/websocket" : webSocketPath;
    var port = (isEmpty(webSocketPort)) ? 12020 : webSocketPort;
    var hostname = top.window.location.hostname;
    var protocol = top.window.location.protocol;
    var host = top.window.location.host;
    if (protocol.startWith("http")) {
        socketHost = "ws://" + hostname + ":" + port + path;
        sever_url = protocol + "//" + host;//(isEmpty(basePath)) ? host : basePath;
    }
}

function initView() {

    loginView = new LoginView("login_pane", "login_data_pane", "login_waiting_pane");
    mainView = $("#main_pane");

    userListPaneView = new ListPaneView("user_tab_pane", "user_root", "chat_show_pane");
    groupListPaneView = new ListPaneView("group_tab_pane", "group_root", "chat_show_pane");
    roomListPaneView = new ListPaneView("user_tab_pane", "room_root", "chat_show_pane");

    chatListView = new ChatListView("chat_tab_pane", "chat_root");

    userInfoPaneView = new UserInfoPaneView("user_show_pane");
    groupInfoPaneView = new GroupInfoPaneView("group_show_pane");
    roomInfoPaneView = new RoomInfoPaneView("user_show_pane");

    userChatPaneBox = new UserChatPaneBox();
    roomChatPaneBox = new RoomChatPaneBox();
    groupChatPaneBox=new GroupChatPaneBox();

    roomMemberPaneView = new RoomMemberPaneView();

    chatPaneView = $("#chat_show_pane");

    var c_n = "images/main/tab/message_normal.png";
    var c_s = "images/main/tab/message_selected.png";

    var u_n = "images/main/tab/user_normal.png";
    var u_s = "images/main/tab/user_selected.png";

    var g_n = "images/main/tab/group_normal.png";
    var g_s = "images/main/tab/group_selected.png";

    var chatTab = $("#chat_tab");
    var userTab = $("#user_tab");
    var groupTab = $("#group_tab");

    var chatShowPane = $("#chat_show_pane");
    var userShowPane = $("#user_show_pane");
    var groupShowPane = $("#group_show_pane");


    tabView = new TabView();
    tabView.put("chat_tab", "chat_tab_pane", function () {
        var ci = chatTab.find("img");
        var ui = userTab.find("img");
        var gi = groupTab.find("img");

        ci.attr("src", c_s);
        ui.attr("src", u_n);
        gi.attr("src", g_n);


        userShowPane.hide();
        groupShowPane.hide();
        chatShowPane.show();

        setMessageTabRed(false);
    });
    tabView.put("user_tab", "user_tab_pane", function () {
        var ci = chatTab.find("img");
        var ui = userTab.find("img");
        var gi = groupTab.find("img");

        ci.attr("src", c_n);
        ui.attr("src", u_s);
        gi.attr("src", g_n);

        chatShowPane.hide();
        groupShowPane.hide();
        userShowPane.show();
    });

    tabView.put("group_tab", "group_tab_pane", function () {
        var ci = chatTab.find("img");
        var ui = userTab.find("img");
        var gi = groupTab.find("img");

        ci.attr("src", c_n);
        ui.attr("src", u_n);
        gi.attr("src", g_s);

        chatShowPane.hide();
        userShowPane.hide();
        groupShowPane.show();
    });


    $("a[rel=image_group]").on("click", function () {//聊天内容图片点击放大
        var imageUrl = $(this).find("img").attr("src");
        //$.fancybox("<img src=\"" + imageUrl + "\">");
        $.fancybox.open("<img src=\"" + imageUrl + "\">");
    });

    $("#own_user_head").on("click", function () {
        var pb = appContext.getBox(PersonalBox);
        var ownUser = pb.getUserData();
        showUserDataInfo(ownUser)
    });

    initContextMenu();


    //roomListPaneView.addOrUpdateCategory("room_chat_list", "群聊");
}


function initEvent() {

    userListPaneView.setOnItemSelected(function (userId) {
        var ub = appContext.getBox(UserBox);
        var userData = ub.getUserData(userId);
        if (userData) {
            userInfoPaneView.showInfo(userData);
        }
        roomMemberPaneView.setShow(false);
    });

    groupListPaneView.setOnItemSelected(function (groupId) {
        var gb = appContext.getBox(GroupBox);
        var group = gb.getGroup(groupId);
        if (group) {
            groupInfoPaneView.showInfo(group);
        }
        roomMemberPaneView.setShow(false);
    });

    roomListPaneView.setOnItemSelected(function (roomId) {
        var rb = appContext.getBox(RoomBox);
        var room = rb.getRoom(roomId);
        if (room) {
            roomInfoPaneView.showInfo(room);
        }
        roomMemberPaneView.setShow(false);
    });

    userInfoPaneView.setOnChat(function (userId) {
        $("#chat_tab").click();
        var userChatService = appContext.getService(UserChatService);
        userChatService.showUserChatById(userId);
        userChatService.showUserChatItemById(userId);
        roomMemberPaneView.setShow(false);
    });

    groupInfoPaneView.setOnChat(function (groupId) {
        $("#chat_tab").click();
        var groupChatService = appContext.getService(GroupChatService);
        groupChatService.showGroupChatById(groupId);
        groupChatService.showGroupChatItemById(groupId);
        roomMemberPaneView.setShow(false);
    });

    roomInfoPaneView.setOnChat(function (roomId) {
        $("#chat_tab").click();
        var roomChatService = appContext.getService(RoomChatService);
        roomChatService.showRoomChatById(roomId);
        roomChatService.showRoomChatItemById(roomId);
        roomMemberPaneView.setShow(false);
    });

    chatListView.setOnItemSelected(function (type, id) {
        if ("user" == type) {
            var userChatService = appContext.getService(UserChatService);
            userChatService.showUserChatById(id);
        }
        if ("group" == type) {
            var groupChatService = appContext.getService(GroupChatService);
            groupChatService.showGroupChatById(id);
        }
        if ("room" == type) {
            var roomChatService = appContext.getService(RoomChatService);
            roomChatService.showRoomChatById(id);
        }
        roomMemberPaneView.setShow(false);
    });

    chatListView.setOnItemRemove(function (type, id) {
        if ("user" == type) {
            var userChatService = appContext.getService(UserChatService);
            userChatService.removeUserChatById(id);
        }
        if ("group" == type) {
            var groupChatService = appContext.getService(GroupChatService);
            groupChatService.removeGroupChatById(id);
        }
        if ("room" == type) {
            var roomChatService = appContext.getService(RoomChatService);
            roomChatService.removeRoomChatById(id);
        }
        roomMemberPaneView.setShow(false);
    });

    userChatPaneBox.setOnSend(function (id, text) {
        if (!isEmpty(text)) {
            var content = getContent(text, "text");
            var ucc = appContext.getController(UserChatController);
            ucc.sendUserMessage(id, content);
        }
    });

    groupChatPaneBox.setOnSend(function (id, text) {
        if (!isEmpty(text)) {
            var content = getContent(text, "text");
            var gcc = appContext.getController(GroupChatController);
            gcc.sendGroupMessage(id, content);
        }
    });

    roomChatPaneBox.setOnSend(function (id, text) {
        if (!isEmpty(text)) {
            var content = getContent(text, "text");
            var rcc = appContext.getController(RoomChatController);
            rcc.sendRoomMessage(id, content);
        }
    });
}

function initTestData() {

    userListPaneView.addOrUpdateCategory("0001", "好友");
    userListPaneView.addOrUpdateCategory("0002", "同学");
    userListPaneView.addOrUpdateCategory("0003", "亲人");

    userListPaneView.addOrUpdateCategory("0001", "好友zu");

    userListPaneView.addOrUpdateItem("0001", "10000", "QQ非", "images/Head/User/1.png");

    userListPaneView.addOrUpdateItem("0002", "10001", "及阿娇的", "images/Head/User/3.png");

    userListPaneView.updateCategoryMemberCount("0001", "[1/1]");

    groupListPaneView.addOrUpdateCategory("0001", "好友");
}


function initApp() {
    appContext = new AppContext();
    appContext.initNetSocket(socketHost);
    appContext.prompt = function (message, type) {
        promptMessage(message, type);
    };
}

function login() {
    var account = $("#account").val();
    var password = $("#password").val();

    if (isEmpty(account)) {
        layer.msg("请输入账号！");
        return;
    }
    if (isEmpty(account)) {
        layer.msg("请输入密码！");
        return;
    }
    password = md5(password);
    loginView.setShowWaiting(true);
    var pc = appContext.getController(PersonalController);
    pc.login(account, password);
}

function setOwnUserData(userData) {
    var nickname = userData.nickname;
    var name = userData.name;
    var account = userData.account;
    var signature = userData.signature;

    var showName = getShowName(userData);// nickname;
    if (isEmpty(showName)) {
        showName = name;
    }
    if (isEmpty(showName)) {
        showName = account;
    }
    var head = getHeadImage(userData);

    var headTag = $("#own_user_head");
    var nicknameTag = $("#own_nickname");
    var signatureTag = $("#own_signature");
    headTag.attr("src", head);
    nicknameTag.text(showName);
    signatureTag.text(signature);
}

var timeInterval = 0;

function playAudio(type) {
    var t = getTimestamp();
    var temp = t - timeInterval;
    if (temp > 3000) {
        document.getElementById('audio-message').play();
        timeInterval = getTimestamp();
    }
}

function setMessageTabRed(red) {
    var tag = $("#chat_tab_red");
    var hasClass = tag.hasClass("notice");
    if (red) {
        if (!hasClass) {
            tag.addClass("notice");
        }
    } else {
        if (hasClass) {
            tag.removeClass("notice");
        }
    }
}

function logout() {
    var pb = appContext.getBox(PersonalBox);
    pb.removeUserData();
    pb.removeLoginData();
    history.go(0);
}

function showUserDataInfo(userData) {
    var itemId = userData.id;
    var remarkName = userData.remarkName;
    var signature = userData.signature;
    var nickname = userData.nickname;
    var name = userData.name;
    var account = userData.account;
    var email = userData.email;
    var mobile = userData.mobile;
    var userHead = userData.head;
    var head = getHeadImage(userData);

    var html = "";
    html += "    <div class=\"info-base\">";
    html += "        <div class=\"show-info-pane\">";
    html += "            <div class=\"info-head\">";
    html += "               <div class=\"info-head-left\">";
    html += "                   <p>" + (isEmpty(nickname) ? "" : nickname) + "</p>";
    html += "                   <p>" + (isEmpty(signature) ? "" : signature) + "</p>";
    html += "                </div>";
    html += "                <img class=\"avatar\" width=\"40\" height=\"40\" src=\"" + head + "\" alt=\"\">";
    html += "            </div>";
    html += "            <div class=\"info-content\">";
    html += "                <ul>";
    html += "                    <li><label>账号：<span>" + (isEmpty(account) ? "" : account) + "</span></label></li>";
    html += "                    <li><label>昵称：<span>" + (isEmpty(nickname) ? "" : nickname) + "</span></label></li>";
    html += "                    <li><label>手机：<span>" + (isEmpty(mobile) ? "" : mobile) + "</span></label></li>";
    html += "                    <li><label>Email：<span>" + (isEmpty(email) ? "" : email) + "</span></label></li>";
    html += "                </ul>";
    html += "            </div>";
    html += "        </div>";
    html += "    </div>";

    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['500px', '400px'], //宽高
        content: html
    });
}


function showUpdateUserData(userData) {
    var itemId = userData.id;
    var signature = userData.signature;
    var remarkName = userData.remarkName;
    var nickname = userData.nickname;
    var name = userData.name;
    var account = userData.account;
    var email = userData.email;
    var mobile = userData.mobile;
    var userHead = userData.head;
    var head = getHeadImage(userData);

    var signatureInput = $("#user_data_signature");
    var nicknameInput = $("#user_data_nickname");
    var mobileInput = $("#user_data_mobile");
    var emailInput = $("#user_data_email");

    $("#user_data_head").attr("src", head);
    $("#user_data_account").val(account);
    signatureInput.val(signature);
    nicknameInput.val(nickname);
    mobileInput.val(mobile);
    emailInput.val(email);

    var pc = appContext.getController(PersonalController);

    layer.open({
        type: 1,
        shade: false,
        title: false, //不显示标题
        area: ['450px', '400px'], //宽高
        content: $("#update_user_info"), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {
            $("#update_user_info").hide();
        },
        btn: ['确定', '取消'],
        yes: function (index) {
            layer.close(index);
            $("#update_user_info").hide();

            signature = signatureInput.val();
            nickname = nicknameInput.val();
            mobile = mobileInput.val();
            email = emailInput.val();

            userData.signature = signature;
            userData.nickname = nickname;
            userData.mobile = mobile;
            userData.email = email;
            pc.updateUserData(userData);
        },
        btn2: function (index) {
            $("#update_user_info").hide();
            layer.close(index);
        }
    });
}

function openUpdatePassword() {
    layer.prompt({title: '输入新密码，并确认', formType: 1}, function (password, index) {
        layer.close(index);
        if (!isEmpty(password)) {
            var pc = appContext.getController(PersonalController);
            pc.updatePassword(password);
        }
    });
}

function openUpdateUserRemarkName(userId) {
    layer.prompt({title: '输入备注名，并确认', formType: 3}, function (remark, index) {
        layer.close(index);
        if (!isEmpty(remark)) {
            var uc = appContext.getController(UserController);
            uc.updateUserRemarkName(userId, remark);
        }
    });
}

function openAddUser() {

    return;
    var userBox = appContext.getBox(UserBox);
    var userCategoryList = userBox.getUserCategoryList();

    var list = [];
    if (!isEmpty(userCategoryList)) {
        var length = userCategoryList.length;
        for (var i = 0; i < length; i++) {
            var userCategory = userCategoryList[i];
            var sort = userCategory.sort;
            if (2 == sort) {
                list.push(userCategory);
            }
        }
    }
    var accountInput = $("#add_user_account");
    var remarkInput = $("#add_user_remark");
    var categoryInput = $("#add_user_category");

    var html = "";

    if (!isEmpty(list)) {
        var length = list.length;
        for (var i = 0; i < length; i++) {
            var userCategory = list[i];
            var id = userCategory.id;
            var name = userCategory.name;
            html += "<option value=\"" + id + "\" >" + name + "</option>";
        }
    } else {
        html += "<option value=\"我的好友\" >我的好友</option>";
    }
    categoryInput.html(html);
    accountInput.val("");
    remarkInput.val("");

    var uc = appContext.getController(UserController);

    layer.open({
        type: 1,
        shade: false,
        title: false, //不显示标题
        area: ['400px', '250px'], //宽高
        content: $("#add_user"), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {
            $("#add_user").hide();
        },
        btn: ['确定', '取消'],
        yes: function (index) {
            $("#add_user").hide();

            var account = accountInput.val();
            var remark = remarkInput.val();
            var category = categoryInput.val();

            if (isEmpty(account)) {
                layer.msg("请输入联系人账号！");
                return;
            }
            layer.close(index);
            uc.addUser(account, remark, category);
        },
        btn2: function (index) {
            $("#add_user").hide();
            layer.close(index);
        }
    });
}

