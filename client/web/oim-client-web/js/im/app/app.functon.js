/**
 * Created by XiaHui on 2017/11/4.
 */

function openCreateRoom() {

    var nameInput = $("#create_room_name");
    var nicknameInput = $("#create_room_nickname");
    var passwordInput = $("#create_room_password");

    nameInput.val("");
    nicknameInput.val("");
    passwordInput.val("");

    layer.open({
        type: 1,
        shade: false,
        title: false, //不显示标题
        area: ['400px', '250px'], //宽高
        content: $("#create_room_pane"), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {
            $("#create_room_pane").hide();
        },
        btn: ['确定', '取消'],
        btn1: function (index) {
            layer.close(index);
            $("#create_room_pane").hide();
            createRoom();
        },
        btn2: function (index) {
            layer.close(index);
            $("#create_room_pane").hide();
        }
    });
}


function openFindRoom() {

    var rs = appContext.getSender(RoomSender);
    rs.queryRoomList(showFindRoomList);
    layer.open({
        type: 1,
        shade: false,
        title: false, //不显示标题
        area: ['420px', '350px'], //宽高
        content: $("#find_room_pane"), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {
            $("#find_room_pane").hide();
        },
        btn: ['关闭'],
        btn1: function (index) {
            layer.close(index);
            $("#find_room_pane").hide();
        }
    });
}

function showFindRoomList(message) {
    var head = message.head;
    var body = message.body;
    var info = message.info;
    if (info.success) {

        var html = "";
        var roomList = body.roomList;
        var length = roomList.length;
        for (var i = 0; i < length; i++) {
            var room = roomList[i];
            if (room) {
                var id = room.id;
                var name = room.name;
                html += "<li>";
                html += "   <label>" + name + "</label>";
                html += "   <button onclick=\"openJoinRoom('" + id + "')\" >加入群</button></li>";
                html += "</li>";
            }
        }
        $("#find_room_list").html(html);
    } else {
        var errorText = getDefaultErrorText(info);
        promptMessage(errorText, "warning");
    }
}


function openJoinRoom(roomId) {

    var rc = appContext.getController(RoomController);
    var pb = appContext.getBox(PersonalBox);
    var userData = pb.getUserData();
    var ownerName = getShowName(userData);

    //var nicknameInput = $("#join_room_nickname");
    var passwordInput = $("#join_room_password");
    //nicknameInput.val("");
    passwordInput.val("");

    layer.open({
        type: 1,
        shade: false,
        title: false, //不显示标题
        area: ['400px', '150px'], //宽高
        content: $("#join_room_pane"), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {
            $("#join_room_pane").hide();
        },
        btn: ['确定加入群', '取消'],
        yes: function (index) {
            layer.close(index);
            $("#join_room_pane").hide();
            //var nickname = nicknameInput.val();
            var password = passwordInput.val();
            //nickname = isEmpty(nickname) ? ownerName : nickname;
            rc.joinRoom(roomId, '', password);
        },
        btn2: function (index) {
            layer.close(index);
            $("#join_room_pane").hide();
        }
    });
}


var sendFileFromId = "";
var sendFileToId = "";
var sendFileType = "";
function openSendFile(fromId, toId, type) {

    if (isEmpty(fromId)) {
        var pb = appContext.getBox(PersonalBox);
        fromId = pb.getOwnerUserId();
    }

    var fromInput = $("#file_from_user_id");
    var toInput = $("#file_to_user_id");

    fromInput.val(fromId);
    toInput.val(toId);

    sendFileFromId = fromId;
    sendFileToId = toId;
    sendFileType = type;

    //var url="http://47.92.117.28:8080/upload/api?uid=im&ticket=d39bf26b-ca54-4cb8-8f0f-6a74d8f25f88";
    // var url=sever_url + '/image/upload';
    // $("#file_upload_form").attr("action",url);

    layer.open({
        type: 1,
        shade: false,
        title: false, //不显示标题
        area: ['600px', '160px'], //宽高
        content: $("#file_send_pane"), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {
            $("#file_send_pane").hide();
        }
    });
}

function fileSendDone(file, json) {
    var data = jsonToObject(json);
    if (data.success) {
        var name = file.name;
        var size = file.size;
        var url = data.file_url;
        var fileData = {"name": name, "size": size, "url": url};

        if (sendFileType == "user") {
            sendUserFile(sendFileFromId, sendFileToId, fileData);
        }
        if (sendFileType == "room") {
            sendRoomFile(sendFileFromId, sendFileToId, fileData);
        }
    }
}

function sendUserImage(fromId, toId, imageData) {
    var name = imageData.name;
    var size = imageData.size;
    var url = imageData.url;

    var value = {"name": name, "size": size, "url": url};
    var json = objectToJson(value);
    var content = getContent(json, "image");
    var ucc = appContext.getController(UserChatController);
    ucc.sendUserMessage(toId, content);
}

function sendUserFile(fromId, toId, fileData) {
    var name = fileData.name;
    var size = fileData.size;
    var url = fileData.url;

    var value = {"name": name, "size": size, "url": url};
    var json = objectToJson(value);
    var content = getContent(json, "file");
    var ucc = appContext.getController(UserChatController);
    ucc.sendUserMessage(toId, content);
}


function sendRoomImage(fromId, toId, imageData) {
    var name = imageData.name;
    var size = imageData.size;
    var url = imageData.url;

    var value = {"name": name, "size": size, "url": url};
    var json = objectToJson(value);
    var content = getContent(json, "image");
    var rcc = appContext.getController(RoomChatController);
    rcc.sendRoomMessage(toId, content);
}

function sendRoomFile(fromId, toId, fileData) {
    var name = fileData.name;
    var size = fileData.size;
    var url = fileData.url;

    var value = {"name": name, "size": size, "url": url};
    var json = objectToJson(value);
    var content = getContent(json, "file");
    var rcc = appContext.getController(RoomChatController);
    rcc.sendRoomMessage(toId, content);
}


function createRoom() {
    var rc = appContext.getController(RoomController);
    var pb = appContext.getBox(PersonalBox);
    var userData = pb.getUserData();
    var ownerName = getShowName(userData);

    var name = $("#create_room_name").val();
    var nickname ="";// $("#create_room_nickname").val();
    var password = $("#create_room_password").val();

    nickname = isEmpty(nickname) ? ownerName : nickname;
    rc.createRoom(name, nickname, password);
}

function showRoomMemberList(roomId) {
    var isShowing = roomMemberPaneView.isShowing();
    if (isShowing) {
        roomMemberPaneView.setShow(false);
    } else {
       // var isSelected = roomMemberPaneView.isSelected(roomId);
        //if (!isSelected) {
            showRoomMemberUserList(roomId);
       // }
        roomMemberPaneView.setShow(true);
    }
    roomMemberPaneView.setSelected(roomId);
}

function showRoomMemberUserList(roomId) {

    var rb = appContext.getBox(RoomBox);
    var room = rb.getRoom(roomId);
    var roomName = isEmpty(room) ? "" : room.name;

    roomMemberPaneView.setRoomName(roomName);

    var back = function (message) {
        var head = message.head;
        var body = message.body;
        var info = message.info;
        if (info.success) {

            var userDataList = body.userDataList;
            var roomMemberList = body.roomMemberList;
            setRoomMemberListPane(roomId, userDataList, roomMemberList);
        } else {
            var errorText = getDefaultErrorText(info);
            promptMessage(errorText, "warning");
        }
    };
    var roomSender = appContext.getSender(RoomSender);
    roomSender.getRoomUserList(roomId, back);
}

function setRoomMemberListPane(roomId, userDataList, roomMemberList) {

    var memberMap = new Map();
    if (!isEmpty(roomMemberList)) {
        var length = roomMemberList.length;
        for (var i = 0; i < length; i++) {
            var member = roomMemberList[i];
            if (member) {
                var userId = member.userId;
                var nickname = member.nickname;
                memberMap.put(userId, nickname);
            }
        }
    }
    var pb = appContext.getBox(PersonalBox);
    var ud = pb.getUserData();
    var userId = pb.getOwnerUserId();
    // var ownNickname = memberMap.get(userId);
    var showName = getShowName(ud);
    // if (isEmpty(ownNickname)) {
    //     ownNickname = showName;
    // }
    roomMemberPaneView.setSelected(roomId);
    roomMemberPaneView.setOwnNickname(showName);
    roomMemberPaneView.setMemberList(userDataList, roomMemberList);
}

function quitRoom() {

    var roomId=roomMemberPaneView.getSelectedRoomId();
    if(!isEmpty(roomId)){
        layer.confirm('您确定退出群？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            layer.close(index);
            var roomSender = appContext.getSender(RoomSender);
            var back = function (message) {
                var head = message.head;
                var body = message.body;
                var info = message.info;
                if (info.success) {
                    roomMemberPaneView.setShow(false);
                    roomSender.getRoomList();
                } else {
                    var errorText = getDefaultErrorText(info);
                    promptMessage(errorText, "warning");
                }
            };
            roomSender.quitRoom(roomId,back);
        }, function () {

        });
    }
}


function setShowRoomMemberListPane(show) {
    var isShowing = document.getElementById("group-list").style.display == 'block';
    if (show) {
        if (!isShowing) {
            document.getElementById("group-list").style.display = 'block';
            document.getElementById("more").src = 'images/chat/top/more_s.png';
        }
    } else {
        if (isShowing) {
            document.getElementById("group-list").style.display = 'none';
            document.getElementById("more").src = 'images/chat/top/more_n.png';
        }
    }
}


function showGroupMemberList(groupId) {

}
function showFind() {
    document.getElementById("sousuo").style.display = "block";
    document.getElementById("find_text_input").style.background = "#fff";
    document.getElementById("find_switch").src = "images/main/search/close.png";
}

function hideFind() {
    document.getElementById("sousuo").style.display = "none";
    document.getElementById("find_text_input").style.background = "#3c4a62";
    document.getElementById("find_switch").src = "images/main/search/search.png";
}

function showFindResult() {
    var text = $("#find_text_input").val();

    var ub = appContext.getBox(UserBox);
    var list = ub.findUserDataList(text);
    var resultList = $("#find_result_list");
    var l = list.length;

    var html = "";
    html = html + "";

    for (var i = 0; i < l; i++) {
        var userData = list[i];

        var nickname = userData.nickname;
        var name = userData.name;
        var account = userData.account;
        var userHead = userData.head;
        var userId = userData.id;
        var showName = getShowName(userData);
        var head = getHeadImage(userData);

        showName = moreFilter(showName, 7);

        html = html + "<li onclick=\"showUserInfo('" + userId + "');\" >";
        html = html + "    <img  class=\"avatar \" width=\"30\" height=\"30\" src=\"" + head + "\">";
        html = html + "    <p  class=\"name\">" + showName + "</p>";
        html = html + "    <span class=\"\"></span>";
        html = html + "</li>";
    }

    resultList.html(html);
}

function showUserInfo(userId) {
    var ub = appContext.getBox(UserBox);
    var userData = ub.getUserData(userId);
    if (userData) {
        var utp = $("#user_tab_pane");
        var showing = !isEmpty(utp) && !utp.is(":hidden");
        if (!showing) {
            $("#user_tab").click();
        }
        userInfoPaneView.showInfo(userData);
    }
}


function audioPlay(id) {
    var tag = document.getElementById(id);
    if (tag) {
        tag.play();
    }
}


function headUploadDone(json) {
    var data = jsonToObject(json);
    if (data.success) {
        var url = data.file_url;
        var pc = appContext.getController(PersonalController);
        pc.updateUserHead(url);
    }
}


function setLoginEvent(disabled) {
    if(disabled){
        $("#account").unbind("keyup"); //移除click
        $("#password").unbind("keyup"); //移除click
        $("#login_button").unbind("keyup"); //移除click
    }else{
        $("#account").unbind("keyup"); //移除click
        $("#password").unbind("keyup"); //移除click
        $("#login_button").unbind("keyup"); //移除click

        $("#account").keyup(function (event) {
            var keyCode = (event.keyCode ? event.keyCode : event.which);
            if (keyCode == '13') {
                login();
            }
        });

        $("#password").keyup(function (event) {
            var keyCode = (event.keyCode ? event.keyCode : event.which);
            if (keyCode == '13') {
                login();
            }
        });

        $("#login_button").keyup(function (event) {
            var keyCode = (event.keyCode ? event.keyCode : event.which);
            if (keyCode == '13') {
                login();
            }
        });

        //    $("#login_button").keyup(function (event) {
//        var keyCode = (event.keyCode ? event.keyCode : event.which);
//        if (keyCode == '13') {
//            login();
//        }
//    });
        // document.onkeydown = function (e) {
        //     var theEvent = window.event || e;
        //     var code = theEvent.keyCode || theEvent.which;
        //     if (code == 13) {
        //         login();
        //     }
        // };
    }
}