/* global layer */

function getContent(value, type) {
    var item = {
        "type": type,
        "value": value
    };
    var font = {underline: false, bold: false, italic: false, color: "000000", name: "微软雅黑", size: 12};
    var section = {font: font, items: [item]};
    var content = {sections: [section]};
    return content;
}

function createMessageItem(isOwn, head, name, content) {

    var timestamp = content.timestamp;
    var date = (timestamp) ? new Date(timestamp) : new Date();
    var dateTimestamp = new Date().getTime();
    var isOverDay = (dateTimestamp - timestamp) > (1000 * 60 * 60 * 12);
    var time = (isOverDay) ? date.format("MM-dd hh:mm:ss") : date.format("hh:mm:ss");

    var css = isOwn ? "right" : "left";

    var contentText = createChatContent(content);

    var text = "";
    text += "<li>";
    text += "    <div class=\"main " + css + "\">";
    text += "        <p class=\"time\"><span>" + time + "</span></p>";
    text += "        <div>";
    text += "            <img class=\"avatar\" width=\"30\" height=\"30\" src=\"" + head + "\">";
    text += "            <p class=\"name\">" + name + "</p>";
    text += "        </div>";
    text += "        <div class=\"text\">" + contentText + "</div>";
    text += "    </div>";
    text += "</li>";
    return text;
}


function createChatContent(content) {
    var text = "";
    if (content.sections) {
        var sections = content.sections;
        var sectionsLength = sections.length;
        for (var i = 0; i < sectionsLength; i++) {
            var section = sections[i];
            if (section.items) {
                var items = section.items;
                var itemsLength = items.length;
                for (var j = 0; j < itemsLength; j++) {
                    var item = items[j];
                    var type = item.type;
                    var value = item.value;
                    text += createChatSectionItem(type, value);
                }
            }
        }
    }
    return text;
}

function createChatSectionItem(type, value) {
    var text = "[不支持的消息]";

    if ("text" == type) {
        text = htmlEncode(value);
        //text = sinaEmotion.parseEmotion(text);
    }

    if ("image" == type) {
        var httpUrl = "";
        if (!isEmpty(value)) {
            var url = "";
            var json = isJson(value);
            if (json) {
                var imageServer = "/api/v1/oim/image/download.do?id=";
                var imageData = jsonToObject(value);
                var url = imageData.url;
                var id = imageData.id;
                if (isEmpty(url)) {
                    httpUrl = file_server_http_url + imageServer + id
                } else {
                    httpUrl = imageData.url;
                }
            } else {


                if (value.startWith("http")) {
                    httpUrl = value;
                } else {

                    var array = value.split(",");//b9b7c2b4-c611-481a-9e09-31ca06d1f026,jpg
                    if (array.length >= 2) {
                        var imageServer = "/api/v1/oim/image/download.do?id=";
                        var id = array[0];
                        httpUrl = file_server_http_url + imageServer + id
                    } else {
                        httpUrl = file_server_http_url + "/" + value
                    }
                }
            }
        }
        // text = "<a rel=\"image_group\" href=\"javascript:void(0);\" ><img src=\"" + httpUrl + "\"></a>";
        text = "<a rel=\"image_group\" href=\"javascript:showImage('" + httpUrl + "')\" ><img src=\"" + httpUrl + "\"></a>";
    }

    if ("face" == type) {
        var categoryId;
        var key;
        var json = isJson(value);
        if (json) {
            var faceData = jsonToObject(value);
            categoryId = faceData.categoryId;
            key = faceData.key;
        } else {
            var faceArray = value.split(",");
            if (faceArray.length > 1) {
                categoryId = faceArray[0];
                key = faceArray[1];
            }
        }
        if (!isEmpty(categoryId) && !isEmpty(key)) {
            var p = "";
            var path = "";

            if ("emotion" == categoryId) {
                p = ".png";
                path = "emotion";
            }
            if ("classical" == categoryId) {
                p = ".gif";
                path = "classical/gif";
            }
            if ("emoji" == categoryId) {
                p = ".png";
                path = "emoji";
            }
            var url = "images/common/face/" + path + "/" + key + p;
            //var httpUrl = sever_url + "/" + url;
            var httpUrl = url;
            text = "<img src=\"" + httpUrl + "\">";
        }
    }

    if ("url" == type) {
        text = "<a  href=\"" + value + "\" >" + value + "</a>";
    }

    if ("file" == type) {
        var json = isJson(value);
        if (json) {
            var fileData = jsonToObject(value);
            var url = fileData.url;
            var fileName = fileData.name;

            var httpUrl = "";
            if (!isEmpty(url)) {
                if (url.startWith("http")) {
                    httpUrl = url;
                } else {
                    httpUrl = sever_url + "/" + url
                }
            }
            // text = "<label>文件：" + fileName + "</label>";
            // text += "<a target=\"_blank\" href=\"" + httpUrl + "\" >点击下载</a>";
            text = "";
            text += "<div class=\"file\" style='min-width: 150px;' >";
            text += "   <div style=\"display: inline-block;\"><span>" + fileName + "</span></div>";
            text += "   <img src=\"images/chat/bar/Rfile.png\" style=\"float:right;padding:13px 0 5px 5px;\"/>";
            text += "   <div>";
            text += "       <a target=\"_blank\" href=\"" + httpUrl + "\">下载</a>";
            text += "       <a target=\"_blank\" href=\"" + httpUrl + "\" style=\"margin-left:6%;\">另存为</a>";
            text += "   </div>";
            text += "</div>";
        }
    }

    if ("audio" == type) {
        var json = isJson(value);
        if (json) {
            var fileData = jsonToObject(value);
            var url = fileData.url;
            var fileName = fileData.name;

            var httpUrl = "";
            if (!isEmpty(url)) {
                if (url.startWith("http")) {
                    httpUrl = url;
                } else {
                    httpUrl = sever_url + "/" + url
                }
            }
            var timestamp = new Date().getTime();
            var key = "audio_" + timestamp;
            // text = "";
            // text += "<audio controls style=\"width:250px;\">";
            // text += "    <source src=\"" + httpUrl + "\" >";
            // text += "    您的浏览器不支持播放，请下载文件播放";
            // text += "</audio>";
            // text += "<br>";
            // text += "<label>文件：" + fileName + "</label>";
            // text += "<a target=\"_blank\" href=\"" + httpUrl + "\" >点击下载</a>";
            text = "";
            text += "<div class=\"btn-audio\" onclick=\"audioPlay('" + key + "')\">";
            text += "    <img  src=\"images/chat/bar/yuyin3.png\" style=\"position:absolute;top:5px;left:12px;\"/>";
            text += "    <audio controls id=\"" + key + "\">";
            text += "        <source src=\"" + httpUrl + "\" >";
            text += "        您的浏览器不支持播放，请下载文件播放";
            text += "    </audio>";
            text += "</div>";
        }
    }

    if ("video" == type) {
        var json = isJson(value);
        if (json) {
            var fileData = jsonToObject(value);
            var url = fileData.url;
            var fileName = fileData.name;

            var httpUrl = "";
            if (!isEmpty(url)) {
                if (url.startWith("http")) {
                    httpUrl = url;
                } else {
                    httpUrl = sever_url + "/" + url
                }
            }
            text = "";
            text += "<div style=\"width:320px;\">";
            text += "    <video width=\"320\" controls >";
            text += "        <source src=\"" + httpUrl + "\" >";
            text += "        您的浏览器不支持播放，请下载文件播放";
            text += "    </video>";
            text += "</div>";
            text += "<br>";
            text += "<label>文件：" + fileName + "</label>";
            text += "<a target=\"_blank\" href=\"" + httpUrl + "\" >点击下载</a>";
        }
    }

    if ("position" == type) {
        var json = isJson(value);
        if (json) {
            var positionItem = jsonToObject(value);
            var name = positionItem.name;
            var address = positionItem.address;
            var longitude = positionItem.longitude;
            var latitude = positionItem.latitude;

            text = "<button  onclick=\"openMap(" + longitude + "," + latitude + ");\" >点击查看位置</button>";
        }
    }
    return text;
}

function getHeadImage(userData) {
    var image = "";
    var head = userData.head;
    if (isJson(head)) {
        var headData = jsonToObject(head);
        var type = headData.type;
        var value = headData.value;
        if ("key" == type) {
            image = user_head_images + value + ".png";
        }
        if ("base64" == type) {
            image = "data:image/png;base64," + value;
        }
        if ("url" == type) {
            image = value;
        }
    }
    if (isEmpty(image)) {
        image = user_head_images + "1.png";
    }
    return image;
}

function isJson(text) {
    if (typeof text == 'string') {
        try {
            var o = JSON.parse(text);
            if (text.indexOf('{') > -1 || text.indexOf('[') > -1) {
                return true;
            } else {
                return false;
            }
        } catch (e) {
            return false;
        }
    }
    return false;
}

function promptMessage(message, type) {
    var t = type || "prompt";

    if (t == "prompt") {//提示
        layer.msg(message);
    } else if (t == "info") {//信息框
        layer.msg(message);
    } else if (t == "warning") {
        layer.msg(message);
    } else if (t == "error") {
        layer.msg(message);
    } else {
        layer.msg(message);
    }
}
