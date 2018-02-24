/* global layer */


function TabView() {
    var own = this;
    own.tabMap = new Map();
    own.tabPanelMap = new Map();
    own.selectTabId;
    own.selectTab;
    own.selectPanel;

    own.onSelect = function () {
    };


    own.put = function (tabId, panelId, click) {
        var tab = $('#' + tabId);
        var panel = $('#' + panelId);

        own.tabMap.put(tabId, tab);
        own.tabPanelMap.put(tabId, panel);

        panel.hide();

        tab.click(function () {
            own.select(tabId);
            if (typeof (click) == "function") {
                click(tabId);
            }
        });
        if (!own.selectTabId) {
            own.select(tabId);
        }
    };


    own.select = function (tabId) {
        if (own.selectTabId != tabId) {
            var tab = own.tabMap.get(tabId);
            if (tab) {
                own.selectTab = tab;
                own.selectTabId = tabId;
            }

            var panel = own.tabPanelMap.get(tabId);
            if (panel) {

                if (own.selectPanel) {
                    own.selectPanel.hide();
                }

                panel.show();

                own.selectPanel = panel;
            }
            if (typeof (own.onSelect) == "function") {
                own.onSelect(tabId, tab, panel);
            }
        }
    };
    own.clear = function () {
        own.tabMap.clear();
        own.tabPanelMap.clear();
        own.selectTabId = "";
    };

    own.isSelected = function (id) {
        return own.selectTabId == id;
    }
    own.init = function () {
    };
    own.init();
}


function ChatListView(paneId, listPaneId) {
    var own = this;
    own.pane = $("#" + paneId);
    own.listPane = $("#" + listPaneId);
    own.viewKey = getTimestamp();
    var itemMap = new Map();

    var onSelected;
    var onRemove;
    var tempItemKey;

    own.setOnItemSelected = function (onItemSelected) {
        onSelected = onItemSelected;
    };

    own.setOnItemRemove = function (onItemRemove) {
        onRemove = onItemRemove;
    };

    own.clearItemList = function () {
        own.listPane.html("");
    };

    own.addOrUpdateItem = function (type, itemId, name, head, gray) {

        var md5Key = isEmpty(itemId) ? itemId : md5(itemId);
        var idKey = type + "_" + md5Key;

        var key = idKey + "_" + own.viewKey;

        var id = key + "_item";
        var nameId = key + "_name";
        var textId = key + "_text";
        var headId = key + "_head";
        var redId = key + "_red";
        var css = gray ? "gray" : "";

        name = moreFilter(name, 7);

        var item = itemMap.get(idKey);
        if (isEmpty(item)) {
            var html = "";
            html = html + "";
            html = html + "<li id=\"" + id + "\">";
            html = html + "    <img id=\"" + headId + "\" class=\"avatar " + css + "\" width=\"30\" height=\"30\" src=\"" + head + "\">";
            html = html + "    <span id=\"" + redId + "\" class=\"\"></span>";
            html = html + "    <p class=\"name\">";
            html = html + "        <span id=\"" + nameId + "\" style=\"display:block;\">" + name + "</span>";
            html = html + "        <span id=\"" + textId + "\" class=\"words\"></span>";
            html = html + "    </p>";
            html = html + "    <em></em>";
            html = html + "</li>";
            item = $(html);
            setItemEvent(item, type, itemId);
            itemMap.put(idKey, item);
            own.listPane.append(item);
        } else {
            var nameLabel = $("#" + nameId);
            var headImage = $("#" + headId);
            nameLabel.text(name);
            headImage.attr("src", head);
            var hasClass = headImage.hasClass("gray");
            if (gray) {
                if (!hasClass) {
                    headImage.addClass("gray");
                }
            } else {
                if (hasClass) {
                    headImage.removeClass("gray");
                }
            }
        }
    };

    own.setItemText = function (type, itemId, text) {
        var md5Key = isEmpty(itemId) ? itemId : md5(itemId);
        var idKey = type + "_" + md5Key;

        var key = idKey + "_" + own.viewKey;

        var textId = key + "_text";
        var textTag = $("#" + textId);
        if (textTag) {
            textTag.text(text);
        }
    };

    own.hasItem = function (type, itemId) {
        var md5Key = isEmpty(itemId) ? itemId : md5(itemId);
        var idKey = type + "_" + md5Key;
        return itemMap.containsKey(idKey);
    }

    own.removeItem = function (type, itemId) {
        var md5Key = isEmpty(itemId) ? itemId : md5(itemId);
        var idKey = type + "_" + md5Key;

        var key = idKey + "_" + own.viewKey;

        var id = key + "_item";
        var item = $("#" + id);
        item.remove();

        //itemMap.remove(idKey);
    };


    own.setItemRed = function (type, itemId, red) {
        var md5Key = isEmpty(itemId) ? itemId : md5(itemId);
        var idKey = type + "_" + md5Key;

        var key = idKey + "_" + own.viewKey;

        var redId = key + "_red";
        var redTag = $("#" + redId);//notice layui-badge-dot
        if (redTag) {
            if (red) {
                var hasClass = redTag.hasClass("head-notice ");
                if (!hasClass) {
                    redTag.attr("class", "head-notice ");
                }
            } else {
                redTag.attr("class", "");
            }
        }
    };


    own.isItemShowing = function (type, itemId) {
        var md5Key = isEmpty(itemId) ? itemId : md5(itemId);
        var idKey = type + "_" + md5Key;

        var key = idKey + "_" + own.viewKey;

        var id = key + "_item";
        var item = $("#" + id);
        var showing = !isEmpty(item) && !item.is(":hidden");
        return showing;
    };

    own.isItemSelected = function (type, itemId) {
        var itemKey = type + "_" + itemId;
        return tempItemKey == itemKey;
    };

    own.selectItem = function (type, itemId) {
        var md5Key = isEmpty(itemId) ? itemId : md5(itemId);
        var idKey = type + "_" + md5Key;


        var itemKey = type + "_" + itemId;


        var item = itemMap.get(idKey);
        if (item) {
            var span = item.find('span');
            if (span) {
                span.attr("class", "");
            }
            // if (tempItemKey != itemKey) {
            //
            // }
            select(item);
            tempItemKey = itemKey;
        }
    };



    function setItemEvent(item, type, itemId) {
        var itemKey = type + "_" + itemId;
        item.unbind("click"); //移除click
        item.click(function () {
            var node = $(this);
            var span = node.find('span');
            if (span) {
                span.attr("class", "");
            }
            // if (tempItemKey != itemKey) {
            //
            // }
            select(item);
            if (typeof (onSelected) == "function") {
                onSelected(type, itemId);
            }
            tempItemKey = itemKey;
        });

        item.on("click", "em", function (e) {//绑定关闭用户
            e.stopPropagation();//不触发父组件事件。
            own.removeItem(type, itemId);
            if (typeof (onRemove) == "function") {
                onRemove(type, itemId);
            }
        });
    }


    var temp;

    function select(object) {
        var item = $(object);
        if (item) {
            if (temp && temp.hasClass("active")) {
                temp.removeClass("active");
            }
            if (item && !item.hasClass("active")) {
                item.addClass("active");
            }
            temp = item;
        }
    }

    own.init = function () {

    };
    own.init();
    return this;
}


function ListPaneView(paneId, listPaneId) {
    var own = this;
    own.pane = $("#" + paneId);
    own.listPane = $("#" + listPaneId);

    own.viewKey = "list_" + getTimestamp();

    var nodePaneMap = new Map();
    var nodeItemMap = new Map();
    var onSelected;
    var tempItemId;

    own.setOnItemSelected = function (onItemSelected) {
        onSelected = onItemSelected;
    }

    own.clearCategory = function () {
        own.listPane.html("");
        nodePaneMap.clear();
    };

    own.addOrUpdateCategory = function (categoryId, name) {
        addOrUpdateCategoryItem(categoryId, name);
    };

    own.clearCategoryMember = function (categoryId) {
        var key = isEmpty(categoryId) ? categoryId : md5(categoryId);
        key = own.viewKey + "_" + key;

        var listId = key + "_list";
        var list = $("#" + listId);
        if (list) {
            list.html("");
        }
        var map = getItemMap(categoryId);
        map.clear();
    };

    own.addOrUpdateItem = function (categoryId, itemId, name, head, gray) {
        var ck = isEmpty(categoryId) ? categoryId : md5(categoryId);
        var ik = isEmpty(itemId) ? itemId : md5(itemId);

        var listKey = own.viewKey + "_" + ck;

        var key = ck + "_" + ik;
        key = own.viewKey + "_" + key;

        var id = key + "_item";
        var nameId = key + "_name";
        var headId = key + "_head";
        var redId = key + "_red";
        var css = gray ? "gray" : "";

        name = moreFilter(name, 7);

        var map = getItemMap(categoryId);
        var item = map.get(itemId);
        if (isEmpty(item)) {
            var html = "";
            html = html + "";
            html = html + "<li id=\"" + id + "\">";
            html = html + "    <img id=\"" + headId + "\" class=\"avatar " + css + "\" width=\"30\" height=\"30\" src=\"" + head + "\">";
            html = html + "    <p id=\"" + nameId + "\" class=\"name\">" + name + "</p>";
            html = html + "    <span id=\"" + redId + "\" class=\"\"></span>";
            html = html + "</li>";
            item = $(html);
            setItemEvent(item, itemId, name);
            map.put(itemId, item);
        } else {
            var nameLabel = $("#" + nameId);
            var headImage = $("#" + headId);
            nameLabel.text(name);
            headImage.attr("src", head);
            var hasClass = headImage.hasClass("gray");
            if (gray) {

                if (!hasClass) {
                    headImage.addClass("gray");
                }
            } else {
                if (hasClass) {
                    headImage.removeClass("gray");
                }
            }
        }

        var listId = listKey + "_list";
        var list = $("#" + listId);
        if (list) {
            list.append(item);
        }
    };

    own.removeCategoryMember = function (categoryId, itemId) {
        var ck = isEmpty(categoryId) ? categoryId : md5(categoryId);
        var ik = isEmpty(itemId) ? itemId : md5(itemId);

        var key = ck + "_" + ik;
        key = own.viewKey + "_" + key;

        var id = key + "_item";
        var item = $("#" + id);
        item.remove();
    };

    own.updateCategoryMemberCount = function (categoryId, countText) {
        var key = isEmpty(categoryId) ? categoryId : md5(categoryId);
        key = own.viewKey + "_" + key;

        var countId = key + "_count";
        $("#" + countId).text(countText);
    };


    own.setCategoryRed = function (categoryId, red) {
        var key = isEmpty(categoryId) ? categoryId : md5(categoryId);
        key = own.viewKey + "_" + key;

        var redId = key + "_red";
        var redTag = $("#" + redId);
        if (redTag) {
            if (red) {
                var hasClass = redTag.hasClass("layui-badge-dot");
                if (!hasClass) {
                    redTag.attr("class", "layui-badge-dot");
                }
            } else {
                redTag.attr("class", "");
            }
        }
    };

    own.setItemRed = function (categoryId, itemId, red) {
        var ck = isEmpty(categoryId) ? categoryId : md5(categoryId);
        var ik = isEmpty(itemId) ? itemId : md5(itemId);

        var key = ck + "_" + ik;
        key = own.viewKey + "_" + key;

        var redId = key + "_red";
        var redTag = $("#" + redId);
        if (redTag) {
            if (red) {
                var hasClass = redTag.hasClass("layui-badge-dot");
                if (!hasClass) {
                    redTag.attr("class", "layui-badge-dot");
                }
            } else {
                redTag.attr("class", "");
            }
        }
    };

    own.isCategoryShowing = function (categoryId) {
        var key = isEmpty(categoryId) ? categoryId : md5(categoryId);
        key = own.viewKey + "_" + key;

        var id = key + "_category";
        var category = $("#" + id);
        var showing = !isEmpty(category) && !category.is(":hidden");
        return showing;
    };

    own.isItemShowing = function (categoryId, itemId) {
        var ck = isEmpty(categoryId) ? categoryId : md5(categoryId);
        var ik = isEmpty(itemId) ? itemId : md5(itemId);

        var key = ck + "_" + ik;
        key = own.viewKey + "_" + key;

        var id = key + "_item";
        var category = $("#" + id);
        var showing = !isEmpty(category) && !category.is(":hidden");
        return showing;
    };

    function getItemMap(categoryId) {
        var map = nodeItemMap.get(categoryId);
        if (isEmpty(map)) {
            map = new Map();
            nodeItemMap.put(categoryId, map);
        }
        return map;
    }

    function addOrUpdateCategoryItem(categoryId, name) {
        var key = isEmpty(categoryId) ? categoryId : md5(categoryId);
        key = own.viewKey + "_" + key;

        var id = key + "_category";
        var nameId = key + "_name";

        var countId = key + "_count";
        var redId = key + "_red";
        var contentId = key + "_content";
        var listId = key + "_list";

        name = moreFilter(name, 7);

        var category = nodePaneMap.get(categoryId);
        if (isEmpty(category)) {
            var html = "";
            html = html + "";
            html = html + "<div id=\"" + id + "\" class=\"list-node-pane\">";
            html = html + "    <div class=\"title\">";
            html = html + "        <i class=\"fa fa-angle-right\" ></i>";
            html = html + "        <label id=\"" + nameId + "\" class=\"name\">" + name + "</label>";
            html = html + "        <label id=\"" + countId + "\" class=\"count\">[0/0]</label>";
            html = html + "        <span id=\"" + redId + "\" class=\"\"></span>";
            html = html + "    </div>";
            html = html + "    <div id=\"" + contentId + "\" class=\"content\">";
            html = html + "        <ul id=\"" + listId + "\" class=\"list-node-item\" >";
            html = html + "        </ul>";
            html = html + "    </div>";
            html = html + "</div>";

            category = $(html);
            own.listPane.append(category);
            setCategoryEvent(category);

            nodePaneMap.put(categoryId, category);
        } else {
            $("#" + nameId).text(name);
        }
    }

    function setCategoryEvent(category) {
        category.find(".title").unbind("click"); //移除click
        category.find(".title").click(function () {
            var node = $(this);
            var nodePane = node.parent('div').find('.content');
            var i = node.find('i');
            var span = node.find('span');
            if (nodePane.is(":hidden")) {
                nodePane.show();
                if (i) {
                    i.attr("class", "fa fa-angle-down");
                }
                if (span) {
                    span.attr("class", "");
                }
            } else {
                nodePane.hide();
                if (i) {
                    i.attr("class", "fa fa-angle-right");
                }
            }
        });
    }

    function setItemEvent(item, itemId, name) {
        item.unbind("click"); //移除click
        item.click(function () {
            var node = $(this);
            var span = node.find('span');
            if (span) {
                span.attr("class", "");
            }
            // if (tempItemId != itemId) {
            //     select(item);
            //     if (typeof (onSelected) == "function") {
            //         onSelected(itemId);
            //     }
            // }

            select(item);
            if (typeof (onSelected) == "function") {
                onSelected(itemId);
            }
            tempItemId = itemId;
        });
    }


    var temp;

    function select(object) {
        var item = $(object);
        if (item) {
            if (temp && temp.hasClass("active")) {
                temp.removeClass("active");
            }
            if (item && !item.hasClass("active")) {
                item.addClass("active");
            }
            temp = item;
        }
    }

    own.init = function () {

    };
    own.init();
    return this;
}


function UserInfoPaneView(paneId) {
    var own = this;
    own.pane = $("#" + paneId);
    own.viewKey = "user_info_" + getTimestamp();
    own.onChat;
    var tempItemId;
    own.showInfo = function (userData) {
        var itemId = userData.id;
        tempItemId = itemId;

        var html = getInfoPane(userData);
        own.pane.html(html);

        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var buttonId = key + "open_chat_button";
        var button = $("#" + buttonId);
        setButtonEvent(button, itemId);
    };

    own.isUserInfoShowing = function (userId) {
        return tempItemId == userId;
    }

    own.setOnChat = function (onChat) {
        own.onChat = onChat;
    }

    function getInfoPane(userData) {
        var itemId = userData.id;
        var remarkName = userData.remarkName;
        var nickname = userData.nickname;
        var name = userData.name;
        var account = userData.account;
        var email = userData.email;
        var mobile = userData.mobile;
        var signature = userData.signature;
        var head = getHeadImage(userData);
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var buttonId = key + "open_chat_button";

        var editHtml = "";
        editHtml += "<a onclick='openUpdateUserRemarkName(\"" + itemId + "\")'>";
        editHtml += "    <i class=\"fa fa-edit\" aria-hidden=\"true\"></i>";
        editHtml += "</a>";

        var html = "";
        html += "     <div class=\"info-base\">";
        html += "        <div class=\"info-pane\">";
        html += "            <div class=\"info-head\">";
        html += "               <div class=\"info-head-left\">";
        html += "                   <p>" + (isEmpty(nickname) ? "" : nickname) + "</p>";
        html += "                   <p>" + (isEmpty(signature) ? "" : signature) + "</p>";
        html += "                </div>";
        html += "                <img class=\"avatar\" width=\"40\" height=\"40\" src=\"" + head + "\" alt=\"\">";
        html += "            </div>";
        html += "            <div class=\"info-content\">";
        html += "                <ul>";
        html += "                    <li><label>备注：<span>" + (isEmpty(remarkName) ? "" : remarkName) + editHtml + "</span></label></li>";
        html += "                    <li><label>账号：<span>" + (isEmpty(account) ? "" : account) + "</span></label></li>";
        html += "                    <li><label>昵称：<span>" + (isEmpty(nickname) ? "" : nickname) + "</span></label></li>";
        html += "                    <li><label>手机：<span>" + (isEmpty(mobile) ? "" : mobile) + "</span></label></li>";
        html += "                    <li><label>Email：<span>" + (isEmpty(email) ? "" : email) + "</span></label></li>";
        html += "                </ul>";
        html += "            </div>";
        html += "            <div>";
        html += "                <button id=\"" + buttonId + "\" class=\"layui-btn\">发送消息</button>";
        html += "            </div>";
        html += "        </div>";
        html += "    </div>";

        return html;
    }

    function setButtonEvent(button, itemId) {
        button.unbind("click"); //移除click
        button.click(function () {
            if (typeof (own.onChat) == "function") {
                own.onChat(itemId);
            }
        });
    }
}


function GroupInfoPaneView(paneId) {
    var own = this;
    own.pane = $("#" + paneId);
    own.viewKey = "group_info_" + getTimestamp();
    own.onChat;
    var tempItemId;
    own.showInfo = function (group) {
        var itemId = group.id;
        tempItemId = itemId;

        var html = getInfoPane(group);
        own.pane.html(html);

        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var buttonId = key + "open_chat_button";
        var button = $("#" + buttonId);
        setButtonEvent(button, itemId);
    };

    own.isGroupInfoShowing = function (groupId) {
        return tempItemId == groupId;
    }

    own.setOnChat = function (onChat) {
        own.onChat = onChat;
    }

    function getInfoPane(group) {
        var itemId = group.id;
        var remark = group.remark;
        var introduce = group.introduce;
        var name = group.name;
        var romHead = group.head;
        var head = group_head_images + "1.png";

        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var buttonId = key + "open_chat_button";

        var editHtml = "";
        // editHtml += "<a onclick='openUpdateUserRemarkName(\"" + itemId + "\")'>";
        // editHtml += "    <i class=\"fa fa-edit\" aria-hidden=\"true\"></i>";
        // editHtml += "</a>";

        var html = "";
        html += "    <div class=\"info-base\">";
        html += "        <div class=\"info-pane\">";
        html += "            <div class=\"info-head\">";
        html += "               <div class=\"info-head-left\">";
        html += "                   <p>" + (isEmpty(name) ? "" : name) + "</p>";
        html += "                </div>";
        html += "                <img class=\"avatar\" width=\"40\" height=\"40\" src=\"" + head + "\" alt=\"\">";
        html += "            </div>";
        html += "            <div class=\"info-content\">";
        html += "                <ul>";
        // html += "                    <li><label>群名称：<span>" + (isEmpty(name) ? "" : name) + editHtml + "</span></label></li>";
        html += "                    <li><label>群简介：<span>" + (isEmpty(introduce) ? "" : introduce) + "</span></label></li>";
        html += "                </ul>";
        html += "            </div>";
        html += "            <div>";
        html += "                <button id=\"" + buttonId + "\" class=\"layui-btn\">发送消息</button>";
        html += "            </div>";
        html += "        </div>";
        html += "    </div>";

        return html;
    }

    function setButtonEvent(button, itemId) {
        button.unbind("click"); //移除click
        button.click(function () {
            if (typeof (own.onChat) == "function") {
                own.onChat(itemId);
            }
        });
    }
}


function RoomInfoPaneView(paneId) {
    var own = this;
    own.pane = $("#" + paneId);
    own.viewKey = "room_info_" + getTimestamp();
    own.onChat;
    var tempItemId;
    own.showInfo = function (room) {
        var itemId = room.id;
        tempItemId = itemId;

        var html = getInfoPane(room);
        own.pane.html(html);

        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var buttonId = key + "open_chat_button";
        var button = $("#" + buttonId);
        setButtonEvent(button, itemId);
    };

    own.isRoomInfoShowing = function (roomId) {
        return tempItemId == roomId;
    };

    own.setOnChat = function (onChat) {
        own.onChat = onChat;
    };

    function getInfoPane(room) {
        var itemId = room.id;
        var remark = room.remark;
        var introduce = room.introduce;
        var name = room.name;
        var romHead = room.head;
        var head = group_head_images + "1.png";

        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var buttonId = key + "open_chat_button";

        var editHtml = "";
        // editHtml += "<a onclick='openUpdateUserRemarkName(\"" + itemId + "\")'>";
        // editHtml += "    <i class=\"fa fa-edit\" aria-hidden=\"true\"></i>";
        // editHtml += "</a>";

        var html = "";
        html += "    <div class=\"info-base\">";
        html += "        <div class=\"info-pane\">";
        html += "            <div class=\"info-head\">";
        html += "               <div class=\"info-head-left\">";
        html += "                   <p>" + (isEmpty(name) ? "" : name) + "</p>";
        html += "                </div>";
        html += "                <img class=\"avatar\" width=\"40\" height=\"40\" src=\"" + head + "\" alt=\"\">";
        html += "            </div>";
        html += "            <div class=\"info-content\">";
        html += "                <ul>";
       // html += "                    <li><label>群名称：<span>" + (isEmpty(name) ? "" : name) + editHtml + "</span></label></li>";
        html += "                    <li><label>群简介：<span>" + (isEmpty(introduce) ? "" : introduce) + "</span></label></li>";
        html += "                </ul>";
        html += "            </div>";
        html += "            <div>";
        html += "                <button id=\"" + buttonId + "\" class=\"layui-btn\">发送消息</button>";
        html += "            </div>";
        html += "        </div>";
        html += "    </div>";

        return html;
    }

    function setButtonEvent(button, itemId) {
        button.unbind("click"); //移除click
        button.click(function () {
            if (typeof (own.onChat) == "function") {
                own.onChat(itemId);
            }
        });
    }
}

function UserChatPaneBox() {
    var own = this;
    own.viewKey = "user_chat_" + getTimestamp();

    own.itemChatPaneMap = new Map();
    own.onSend;

    own.setOnSend = function (onSend) {
        own.onSend = onSend;
    };

    own.getChatPane = function (itemId, name) {
        return getItemChatPane(itemId, name);
    };

    own.isChatPaneShowing = function (itemId) {
        var chatPane = own.itemChatPaneMap.get(itemId);
        var showing = !isEmpty(chatPane) && !chatPane.is(":hidden");
        return showing;
    };

    own.insertAfterMessage = function (itemId, itemName, isOwn, head, showName, content) {
        var chatPane = getItemChatPane(itemId, itemName);
        if (chatPane) {
            var key = isEmpty(itemId) ? itemId : md5(itemId);
            key = own.viewKey + "_" + key;
            var messageListId = key + "_chat_message_list";
            var messageList = chatPane.find("#" + messageListId);
            if (messageList) {
                var p = messageList.parent();
                var position = getScrollPosition(p);
                var messageItem = createMessageItem(isOwn, head, showName, content);
                messageList.append(messageItem);
                var children = messageList.children("li");
                if (children.length > 300) {//超过最大条数，最早一条删除
                    var endIndex = children.length - 300;//删除元素的终点下标
                    children.slice(0, endIndex).remove();//最后messageMax之前的删除
                }
                var isBottom = position == "bottom";
                if (isBottom) {
                    toScrollBottom(p);
                }
            }
        }
    };

    own.insertBeforeMessage = function (itemId, itemName, isOwn, head, showName, content) {
        var chatPane = getItemChatPane(itemId, itemName);
        if (chatPane) {
            var key = isEmpty(itemId) ? itemId : md5(itemId);
            key = own.viewKey + "_" + key;
            var messageListId = key + "_chat_message_list";
            var messageList = chatPane.find("#" + messageListId);
            if (messageList) {
                //var p = messageList.parent();
                //var position = getScrollPosition(p);
                var messageItem = createMessageItem(isOwn, head, showName, content);
                messageList.prepend(messageItem);
            }
        }
    };

    own.toBottom = function (itemId, chatPane) {
        setChatScrollToBottom(itemId, chatPane);
    };

    function setChatScrollToBottom(itemId, chatPane) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;
        var messageListId = key + "_chat_message_list";
        var messageList = chatPane.find("#" + messageListId);
        if (messageList) {
            var p = messageList.parent();
            toScrollBottom(p);
        }
    }

    function getScrollPosition(object) {

        // var scrollbar = object.scrollbar;
        // var top = scrollbar.scrollTop();
        // var height = (scrollbar[0] && scrollbar[0].scrollHeight) ? scrollbar[0].scrollHeight : 1000;
        // var clientHeight = (scrollbar[0] && scrollbar[0].clientHeight) ? scrollbar[0].clientHeight : 0;
//var tag=$(object);

        //var t = tag.scrollTop();

        var height = object[0].scrollHeight;
        var top = object[0].scrollTop;

        var clientHeight = object[0].clientHeight;
        var position = "";

        var a = (height - top);
        var b = (clientHeight + 25);

        if (a < b) {
            position = "bottom";
        } else if (top == 0) {
            position = "top";
        } else {
            position = "middle";
        }
        return position;
    }

    function toScrollBottom(object) {
        var height = object[0].scrollHeight;
        object[0].scrollTop = height;
    }

    function getItemChatPane(itemId, name) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var id = key + "_chat";
        var nameId = key + "_chat_name";
        var messageListId = key + "_chat_message_list";

        var writeId = key + "_chat_write";

        var faceId = key + "_chat_face";
        var imageId = key + "_chat_image";
        var fileId = key + "_chat_file";

        var sendId = key + "_chat_send";

        var chatPane = own.itemChatPaneMap.get(itemId);
        name = moreFilter(name, 12);
        if (chatPane) {
            var nameLabel = $("#" + nameId);
            nameLabel.text(name);
        } else {
            var html = "";
            html = html + "    <div id=\"" + id + "\" class=\"chat-pane\">";
            html = html + "        <fieldset class=\"layui-elem-field layui-field-title\" style=\"margin-top: 20px;position: relative;\">";
            html = html + "            <legend id=\"" + nameId + "\">" + name + "</legend>";
            html = html + "            <div class=\"state\"></div>";
            //html = html + "            <div class=\"more\" onclick=\"pop()\"><img id=\"more\" src=\"images/chat/top/more_n.png\" alt=\"\"/></div>";
            html = html + "        </fieldset>";
            html = html + "        <div class=\"message-list\">";
            html = html + "            <ul id=\"" + messageListId + "\"></ul>";
            html = html + "        </div>";
            html = html + "        <div class=\"tool-bar\">";
            html = html + "            <span>";
            html = html + "                <a id=\"" + faceId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" src=\"images/chat/bar/face.png\">";
            html = html + "                </a>";
            html = html + "                <a id=\"" + imageId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" src=\"images/chat/bar/picture.png\">";
            html = html + "                </a>";
            html = html + "                <a id=\"" + fileId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" src=\"images/chat/bar/file.png\">";
            html = html + "                </a>";
            html = html + "            </span>";
            html = html + "        </div>";
            html = html + "        <div class=\"text-input-pane\">";
            html = html + "            <div class=\"text-input\">";
            html = html + "                <textarea id=\"" + writeId + "\"  class=\"reply\"></textarea>";
            html = html + "            </div>";
            html = html + "            <div class=\"send\">";
            html = html + "                <button id=\"" + sendId + "\" class=\"layui-btn\">发送</button>";
            html = html + "            </div>";
            html = html + "        </div>";
            html = html + "    </div>";

            chatPane = $(html);

            own.itemChatPaneMap.put(itemId, chatPane);
        }
        setShowChatPaneEvent(itemId, chatPane);
        setChatScrollToBottom(itemId, chatPane);
        return chatPane;
    }


    function setShowChatPaneEvent(itemId, chatPane) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var writeId = key + "_chat_write";

        var faceId = key + "_chat_face";
        var imageId = key + "_chat_image";
        var fileId = key + "_chat_file";

        var sendId = key + "_chat_send";

        var writeInput = chatPane.find("#" + writeId);

        var faceButton = chatPane.find("#" + faceId);
        var imageButton = chatPane.find("#" + imageId);
        var fileButton = chatPane.find("#" + fileId);

        var sendButton = chatPane.find("#" + sendId);

        //writeInput.unbind("keydown"); //移除click
        writeInput.unbind("keyup"); //移除click

        faceButton.unbind("click"); //移除click
        imageButton.unbind("click"); //移除click
        fileButton.unbind("click"); //移除click

        sendButton.unbind("click"); //移除click

        sendButton.on("click", function () {//发送消息事件
            if (typeof (own.onSend) == "function") {
                var text = writeInput.val();
                writeInput.val("");
                own.onSend(itemId, text);
            }
        });

        writeInput.keyup(function (event) {
            var shiftKey = event.shiftKey;
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13' && !(shiftKey)) {
                if (typeof (own.onSend) == "function") {
                    var text = writeInput.val();
                    writeInput.val("");
                    own.onSend(itemId, text);
                }
            }
        });
        faceButton.click(function (event) {
            sinaEmotion.showEmotion(faceButton, writeInput);
            return false;
        });

        imageButton.click(function (event) {
            uploadImage("", itemId, "user");
            return false;
        });

        fileButton.click(function (event) {
            openSendFile("", itemId, "user");
            return false;
        });
        // fileButton.uploadify({
        //     height: 30,
        //     width: 10,
        //     swf: '/uploadify/uploadify.swf',
        //     uploader: '/uploadify/uploadify.php'
        //
        // });
        // faceButton.sinaEmotion({target: writeInput});
    }


    function insertChatMessage(itemMessageList) {

    }

    var temp;


    own.init = function () {

    };
    own.init();
    return this;
}


function GroupChatPaneBox() {
    var own = this;
    own.viewKey = "group_chat_" + getTimestamp();

    own.itemChatPaneMap = new Map();
    own.onSend;

    own.setOnSend = function (onSend) {
        own.onSend = onSend;
    };

    own.getChatPane = function (itemId, name) {
        return getItemChatPane(itemId, name);
    };

    own.isChatPaneShowing = function (itemId) {
        var chatPane = own.itemChatPaneMap.get(itemId);
        var showing = !isEmpty(chatPane) && !chatPane.is(":hidden");
        return showing;
    };

    own.insertAfterMessage = function (itemId, itemName, isOwn, head, showName, content) {
        var chatPane = getItemChatPane(itemId, itemName);
        if (chatPane) {
            var key = isEmpty(itemId) ? itemId : md5(itemId);
            key = own.viewKey + "_" + key;
            var messageListId = key + "_chat_message_list";
            var messageList = chatPane.find("#" + messageListId);
            if (messageList) {
                var p = messageList.parent();
                var position = getScrollPosition(p);
                var messageItem = createMessageItem(isOwn, head, showName, content);
                messageList.append(messageItem);
                var children = messageList.children("li");
                if (children.length > 300) {//超过最大条数，最早一条删除
                    var endIndex = children.length - 300;//删除元素的终点下标
                    children.slice(0, endIndex).remove();//最后messageMax之前的删除
                }
                var isBottom = position == "bottom";
                if (isBottom) {
                    toScrollBottom(p);
                }
            }
        }
    };

    own.insertBeforeMessage = function (itemId, itemName, isOwn, head, showName, content) {
        var chatPane = getItemChatPane(itemId, itemName);
        if (chatPane) {
            var key = isEmpty(itemId) ? itemId : md5(itemId);
            key = own.viewKey + "_" + key;
            var messageListId = key + "_chat_message_list";
            var messageList = chatPane.find("#" + messageListId);
            if (messageList) {
                //var p = messageList.parent();
                //var position = getScrollPosition(p);
                var messageItem = createMessageItem(isOwn, head, showName, content);
                messageList.prepend(messageItem);
            }
        }
    };

    own.toBottom = function (itemId, chatPane) {
        setChatScrollToBottom(itemId, chatPane);
    };

    function setChatScrollToBottom(itemId, chatPane) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;
        var messageListId = key + "_chat_message_list";
        var messageList = chatPane.find("#" + messageListId);
        if (messageList) {
            var p = messageList.parent();
            toScrollBottom(p);
        }
    }

    function getScrollPosition(object) {


        var height = object[0].scrollHeight;
        var top = object[0].scrollTop;

        var clientHeight = object[0].clientHeight;
        var position = "";

        var a = (height - top);
        var b = (clientHeight + 25);

        if (a < b) {
            position = "bottom";
        } else if (top == 0) {
            position = "top";
        } else {
            position = "middle";
        }
        return position;
    }

    function toScrollBottom(object) {
        var height = object[0].scrollHeight;
        object[0].scrollTop = height;
    }

    function getItemChatPane(itemId, name) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var id = key + "_chat";
        var nameId = key + "_chat_name";
        var messageListId = key + "_chat_message_list";

        var writeId = key + "_chat_write";

        var faceId = key + "_chat_face";
        var imageId = key + "_chat_image";
        var fileId = key + "_chat_file";

        var sendId = key + "_chat_send";

        var chatPane = own.itemChatPaneMap.get(itemId);
        name = moreFilter(name, 12);
        if (chatPane) {
            var nameLabel = $("#" + nameId);
            nameLabel.text(name);
        } else {
            var html = "";

            html = html + "    <div id=\"" + id + "\" class=\"chat-pane\">";
            html = html + "        <fieldset class=\"layui-elem-field layui-field-title\" style=\"margin-top: 20px;position: relative;\">";
            html = html + "            <legend id=\"" + nameId + "\">" + name + "</legend>";
            html = html + "            <div class=\"state\"></div>";
            html = html + "            <div class=\"more\" onclick=\"showGroupMemberList('" + itemId + "')\"><img id=\"more\" src=\"images/chat/top/more_n.png\" alt=\"\"/></div>";
            html = html + "        </fieldset>";

            html = html + "        <div class=\"message-list\">";
            html = html + "            <ul id=\"" + messageListId + "\"></ul>";
            html = html + "        </div>";
            html = html + "        <div class=\"tool-bar\">";
            html = html + "            <span>";
            html = html + "                <a id=\"" + faceId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" width=\"20\" height=\"20\" src=\"images/chat/bar/face.png\">";
            html = html + "                </a>";
            html = html + "                <a id=\"" + imageId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" width=\"20\" height=\"20\" src=\"images/chat/bar/picture.png\">";
            html = html + "                </a>";
            html = html + "                <a id=\"" + fileId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" width=\"22\" height=\"22\" src=\"images/chat/bar/file.png\">";
            html = html + "                </a>";
            html = html + "            </span>";
            html = html + "        </div>";
            html = html + "        <div class=\"text-input-pane\">";
            html = html + "            <div class=\"text-input\">";
            html = html + "                <textarea id=\"" + writeId + "\" ></textarea>";
            html = html + "            </div>";
            html = html + "            <div class=\"send\">";
            html = html + "                <button id=\"" + sendId + "\" class=\"layui-btn\">发送</button>";
            html = html + "            </div>";
            html = html + "        </div>";
            html = html + "    </div>";

            chatPane = $(html);

            own.itemChatPaneMap.put(itemId, chatPane);
        }
        setShowChatPaneEvent(itemId, chatPane);
        setChatScrollToBottom(itemId, chatPane);
        return chatPane;
    }


    function setShowChatPaneEvent(itemId, chatPane) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var writeId = key + "_chat_write";

        var faceId = key + "_chat_face";
        var imageId = key + "_chat_image";
        var fileId = key + "_chat_file";

        var sendId = key + "_chat_send";

        var writeInput = chatPane.find("#" + writeId);

        var faceButton = chatPane.find("#" + faceId);
        var imageButton = chatPane.find("#" + imageId);
        var fileButton = chatPane.find("#" + fileId);

        var sendButton = chatPane.find("#" + sendId);

        //writeInput.unbind("keydown"); //移除click
        writeInput.unbind("keyup"); //移除click

        faceButton.unbind("click"); //移除click
        imageButton.unbind("click"); //移除click
        fileButton.unbind("click"); //移除click

        sendButton.unbind("click"); //移除click

        sendButton.on("click", function () {//发送消息事件
            if (typeof (own.onSend) == "function") {
                var text = writeInput.val();
                writeInput.val("");
                own.onSend(itemId, text);
            }
        });

        writeInput.keyup(function (event) {
            var shiftKey = event.shiftKey;
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13' && !(shiftKey)) {
                if (typeof (own.onSend) == "function") {
                    var text = writeInput.val();
                    writeInput.val("");
                    own.onSend(itemId, text);
                }
            }
        });
        faceButton.click(function (event) {
            sinaEmotion.showEmotion(faceButton, writeInput);
            return false;
        });

        imageButton.click(function (event) {
            uploadImage("", itemId, "group");
            return false;
        });

        fileButton.click(function (event) {
            openSendFile("", itemId, "group");
            return false;
        });
        // fileButton.uploadify({
        //     height: 30,
        //     width: 10,
        //     swf: '/uploadify/uploadify.swf',
        //     uploader: '/uploadify/uploadify.php'
        //
        // });
        // faceButton.sinaEmotion({target: writeInput});
    }


    function insertChatMessage(itemMessageList) {

    }

    var temp;


    own.init = function () {

    };
    own.init();
    return this;
}

function RoomChatPaneBox() {
    var own = this;
    own.viewKey = "room_chat_" + getTimestamp();

    own.itemChatPaneMap = new Map();
    own.onSend;

    own.setOnSend = function (onSend) {
        own.onSend = onSend;
    };

    own.getChatPane = function (itemId, name) {
        return getItemChatPane(itemId, name);
    };

    own.isChatPaneShowing = function (itemId) {
        var chatPane = own.itemChatPaneMap.get(itemId);
        var showing = !isEmpty(chatPane) && !chatPane.is(":hidden");
        return showing;
    };

    own.insertAfterMessage = function (itemId, itemName, isOwn, head, showName, content) {
        var chatPane = getItemChatPane(itemId, itemName);
        if (chatPane) {
            var key = isEmpty(itemId) ? itemId : md5(itemId);
            key = own.viewKey + "_" + key;
            var messageListId = key + "_chat_message_list";
            var messageList = chatPane.find("#" + messageListId);
            if (messageList) {
                var p = messageList.parent();
                var position = getScrollPosition(p);
                var messageItem = createMessageItem(isOwn, head, showName, content);
                messageList.append(messageItem);
                var children = messageList.children("li");
                if (children.length > 300) {//超过最大条数，最早一条删除
                    var endIndex = children.length - 300;//删除元素的终点下标
                    children.slice(0, endIndex).remove();//最后messageMax之前的删除
                }
                var isBottom = position == "bottom";
                if (isBottom) {
                    toScrollBottom(p);
                }
            }
        }
    };

    own.insertBeforeMessage = function (itemId, itemName, isOwn, head, showName, content) {
        var chatPane = getItemChatPane(itemId, itemName);
        if (chatPane) {
            var key = isEmpty(itemId) ? itemId : md5(itemId);
            key = own.viewKey + "_" + key;
            var messageListId = key + "_chat_message_list";
            var messageList = chatPane.find("#" + messageListId);
            if (messageList) {
                //var p = messageList.parent();
                //var position = getScrollPosition(p);
                var messageItem = createMessageItem(isOwn, head, showName, content);
                messageList.prepend(messageItem);
            }
        }
    };

    own.toBottom = function (itemId, chatPane) {
        setChatScrollToBottom(itemId, chatPane);
    };

    function setChatScrollToBottom(itemId, chatPane) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;
        var messageListId = key + "_chat_message_list";
        var messageList = chatPane.find("#" + messageListId);
        if (messageList) {
            var p = messageList.parent();
            toScrollBottom(p);
        }
    }

    function getScrollPosition(object) {


        var height = object[0].scrollHeight;
        var top = object[0].scrollTop;

        var clientHeight = object[0].clientHeight;
        var position = "";

        var a = (height - top);
        var b = (clientHeight + 25);

        if (a < b) {
            position = "bottom";
        } else if (top == 0) {
            position = "top";
        } else {
            position = "middle";
        }
        return position;
    }

    function toScrollBottom(object) {
        var height = object[0].scrollHeight;
        object[0].scrollTop = height;
    }

    function getItemChatPane(itemId, name) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var id = key + "_chat";
        var nameId = key + "_chat_name";
        var messageListId = key + "_chat_message_list";

        var writeId = key + "_chat_write";

        var faceId = key + "_chat_face";
        var imageId = key + "_chat_image";
        var fileId = key + "_chat_file";

        var sendId = key + "_chat_send";

        var chatPane = own.itemChatPaneMap.get(itemId);
        name = moreFilter(name, 12);
        if (chatPane) {
            var nameLabel = $("#" + nameId);
            nameLabel.text(name);
        } else {
            var html = "";

            html = html + "    <div id=\"" + id + "\" class=\"chat-pane\">";
            html = html + "        <fieldset class=\"layui-elem-field layui-field-title\" style=\"margin-top: 20px;position: relative;\">";
            html = html + "            <legend id=\"" + nameId + "\">" + name + "</legend>";
            html = html + "            <div class=\"state\"></div>";
            html = html + "            <div class=\"more\" onclick=\"showRoomMemberList('" + itemId + "')\"><img id=\"more\" src=\"images/chat/top/more_n.png\" alt=\"\"/></div>";
            html = html + "        </fieldset>";

            html = html + "        <div class=\"message-list\">";
            html = html + "            <ul id=\"" + messageListId + "\"></ul>";
            html = html + "        </div>";
            html = html + "        <div class=\"tool-bar\">";
            html = html + "            <span>";
            html = html + "                <a id=\"" + faceId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" width=\"20\" height=\"20\" src=\"images/chat/bar/face.png\">";
            html = html + "                </a>";
            html = html + "                <a id=\"" + imageId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" width=\"20\" height=\"20\" src=\"images/chat/bar/picture.png\">";
            html = html + "                </a>";
            html = html + "                <a id=\"" + fileId + "\" class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"bar-yang\" width=\"22\" height=\"22\" src=\"images/chat/bar/file.png\">";
            html = html + "                </a>";
            html = html + "            </span>";
            html = html + "        </div>";
            html = html + "        <div class=\"text-input-pane\">";
            html = html + "            <div class=\"text-input\">";
            html = html + "                <textarea id=\"" + writeId + "\" ></textarea>";
            html = html + "            </div>";
            html = html + "            <div class=\"send\">";
            html = html + "                <button id=\"" + sendId + "\" class=\"layui-btn\">发送</button>";
            html = html + "            </div>";
            html = html + "        </div>";
            html = html + "    </div>";

            chatPane = $(html);

            own.itemChatPaneMap.put(itemId, chatPane);
        }
        setShowChatPaneEvent(itemId, chatPane);
        setChatScrollToBottom(itemId, chatPane);
        return chatPane;
    }


    function setShowChatPaneEvent(itemId, chatPane) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);
        key = own.viewKey + "_" + key;

        var writeId = key + "_chat_write";

        var faceId = key + "_chat_face";
        var imageId = key + "_chat_image";
        var fileId = key + "_chat_file";

        var sendId = key + "_chat_send";

        var writeInput = chatPane.find("#" + writeId);

        var faceButton = chatPane.find("#" + faceId);
        var imageButton = chatPane.find("#" + imageId);
        var fileButton = chatPane.find("#" + fileId);

        var sendButton = chatPane.find("#" + sendId);

        //writeInput.unbind("keydown"); //移除click
        writeInput.unbind("keyup"); //移除click

        faceButton.unbind("click"); //移除click
        imageButton.unbind("click"); //移除click
        fileButton.unbind("click"); //移除click

        sendButton.unbind("click"); //移除click

        sendButton.on("click", function () {//发送消息事件
            if (typeof (own.onSend) == "function") {
                var text = writeInput.val();
                writeInput.val("");
                own.onSend(itemId, text);
            }
        });

        writeInput.keyup(function (event) {
            var shiftKey = event.shiftKey;
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13' && !(shiftKey)) {
                if (typeof (own.onSend) == "function") {
                    var text = writeInput.val();
                    writeInput.val("");
                    own.onSend(itemId, text);
                }
            }
        });
        faceButton.click(function (event) {
            sinaEmotion.showEmotion(faceButton, writeInput);
            return false;
        });

        imageButton.click(function (event) {
            uploadImage("", itemId, "room");
            return false;
        });

        fileButton.click(function (event) {
            openSendFile("", itemId, "room");
            return false;
        });
        // fileButton.uploadify({
        //     height: 30,
        //     width: 10,
        //     swf: '/uploadify/uploadify.swf',
        //     uploader: '/uploadify/uploadify.php'
        //
        // });
        // faceButton.sinaEmotion({target: writeInput});
    }


    function insertChatMessage(itemMessageList) {

    }

    var temp;


    own.init = function () {

    };
    own.init();
    return this;
}

function ChatPaneBox() {
    var own = this;

    function getItemChatPane(itemId, name) {
        var key = isEmpty(itemId) ? itemId : md5(itemId);

        var id = key + "_chat";
        var nameId = key + "_chat_name";
        var messageListId = key + "_chat_message_list";

        var writeId = key + "_chat_write";

        var faceId = key + "_chat_face";
        var sendId = key + "_chat_send";

        var chatPane = own.itemChatPaneMap.get(itemId);
        name = moreFilter(name, 12);
        if (chatPane) {
            var nameLabel = $("#" + nameId);
            nameLabel.text(name);
        } else {
            var html = "";
            html = html + "    <div id=\"" + id + "\" class=\"chat-pane\">";
            html = html + "        <fieldset class=\"layui-elem-field layui-field-title\" style=\"margin-top: 20px;\">";
            html = html + "            <legend id=\"" + nameId + "\">" + name + "</legend>";
            html = html + "        </fieldset>";
            html = html + "        <div class=\"message-list\">";
            html = html + "            <ul id=\"" + messageListId + "\"></ul>";
            html = html + "        </div>";
            html = html + "        <div class=\"tool-bar\">";
            html = html + "            <span>";
            html = html + "                <a id=\"" + faceId + "\" class=\"bar-icon face\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"avatar\" width=\"20\" height=\"20\" src=\"images/chat/bar/face.png\">";
            html = html + "                </a>";
            html = html + "                <a class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"avatar\" width=\"20\" height=\"20\" src=\"images/chat/bar/picture.png\">";
            html = html + "                </a>";
            html = html + "                <a class=\"bar-icon\" href=\"javascript:void(0)\" >";
            html = html + "                    <img class=\"avatar\" width=\"22\" height=\"22\" src=\"images/chat/bar/file.png\">";
            html = html + "                </a>";
            html = html + "            </span>";
            html = html + "        </div>";
            html = html + "        <div class=\"text-input-pane\">";
            html = html + "            <div class=\"text-input\">";
            html = html + "                <textarea id=\"" + writeId + "\" ></textarea>";
            html = html + "            </div>";
            html = html + "            <div class=\"send\">";
            html = html + "                <button id=\"" + sendId + "\" class=\"layui-btn\">发送</button>";
            html = html + "            </div>";
            html = html + "        </div>";
            html = html + "    </div>";

            chatPane = $(html);

            own.itemChatPaneMap.put(itemId, chatPane);


        }
        setShowChatPaneEvent(itemId, chatPane);
        return chatPane;
    }

    return this;
}

function LoginView(paneId, loginPaneId, waitingPaneId) {
    var own = this;
    own.pane = $("#" + paneId);

    own.loginPane = $("#" + loginPaneId);
    own.waitingPane = $("#" + waitingPaneId);

    own.show = function () {
        own.pane.show();
    };
    own.hide = function () {
        own.pane.hide();
    };

    own.setShowWaiting = function (waiting) {
        if (waiting) {
            own.loginPane.hide();
            own.waitingPane.show();
        } else {
            own.waitingPane.hide();
            own.loginPane.show();
        }
    };

    function init() {
        own.setShowWaiting(false);
    }

    init();
    return this;
}


function initContextMenu() {
    // {text: '', subMenu: [
    //     {text: '15分钟', action: function (e) {
    //
    //     }},
    //     {text: '30分钟', action: function (e) {
    //
    //     }},
    //     {text: '1小时', action: function (e) {
    //
    //     }},
    //     {text: '12小时', action: function (e) {
    //
    //     }},
    //     {text: '24小时', action: function (e) {
    //
    //     }}
    // ]},

    var menu = [
        // {
        //     text: '修改头像', action: function (e) {
        //     uploadHead();
        // }
        // },
        {
            text: '修改资料', action: function (e) {
            var pb = appContext.getBox(PersonalBox);
            var ownUser = pb.getUserData();
            showUpdateUserData(ownUser);
        }
        },
        // {
        //     text: '修改密码', action: function (e) {
        //     openUpdatePassword();
        // }
        // },
        // {
        //     text: '添加联系人', action: function (e) {
        //     openAddUser();
        // }
        // }, {
        //     text: '查找群', action: function (e) {
        //         openFindRoom();
        //     }
        // }, {
        //     text: '创建群', action: function (e) {
        //         openCreateRoom();
        //     }
        // },
        {
            text: '退出', action: function (e) {
            logout();
        }
        }
    ];
    new ContextMenu($("#bottom_menu"), menu);
}

function ContextMenu(selector, menu) {
    context.init({preventDoubleContext: false, above: true});
    context.attach(selector, "click", menu);
}

function showImage(url) {
    $.fancybox.open("<img src=\"" + url + "\">");
}