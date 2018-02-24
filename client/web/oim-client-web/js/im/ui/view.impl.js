function RoomMemberPaneView() {
    var own = this;
    var pane = $("#group-list");
    var listTag = $("#room_member_list");
    var roomNameTag = $("#room_member_pane_name");
    var ownNicknameTag = $("#room_member_own_nickname");
    var tempRoomId;
    own.setMemberList = function (userDataList, roomMemberList) {
        var html = "";

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

        // html = html + "<li>";
        // html = html + "    <img src=\"images/addto.png\" class=\"member-img\" onclick=\"\"/>";
        // html = html + "    <p>添加</p>";
        // html = html + "</li>";
        if (!isEmpty(userDataList)) {
            var length = userDataList.length;
            for (var i = 0; i < length; i++) {
                var userData = userDataList[i];
                if (userData) {

                    var userId = userData.id;
                    var nickname = userData.nickname;
                    var name = userData.name;
                    var account = userData.account;

                    var showName = getShowName(userData);
                    var head = getHeadImage(userData);

                    // var roomNickname = memberMap.get(userId);
                    // if (!isEmpty(roomNickname)) {
                    //     showName = roomNickname;
                    // }
                    showName = moreFilter(showName, 7);

                    html = html + "<li>";
                    html = html + "   <img src=\"" + head + "\" class=\"member-img\" />";
                    html = html + "   <p>" + showName + "</p>";
                    html = html + "</li>";
                }
            }
        }
        listTag.html(html);
    };

    own.setRoomName = function (name) {
        roomNameTag.text(name);
    };

    own.setOwnNickname = function (nickname) {
        ownNicknameTag.text(nickname);
    };

    own.setShow = function (show) {
        var isShowing = own.isShowing();
        if (show) {
            if (!isShowing) {
                pane.show();
            }
        } else {
            if (isShowing) {
                pane.hide();
            }
        }
    };

    own.isShowing = function () {
        return !pane.is(":hidden");
    };

    own.isSelected = function (roomId) {
        return tempRoomId == roomId;
    };

    own.setSelected = function (roomId) {
        tempRoomId = roomId;
    };

    own.getSelectedRoomId = function () {
        return tempRoomId;
    };
}