/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function insertBeforeHtml(html) {
    var element = document.createElement('div');
    element.innerHTML = html;
    document.body.insertBefore(element, document.body.firstElementChild);
}

function insertLastHtml(html) {
    var element = document.createElement('div');
    element.innerHTML = html;
    document.body.appendChild(element);
}

function setBodyHtml(html) {
    document.body.innerHTML = html;
}

function checkElement() {
    var max = 500;
    var nodes = document.body.children;
    var length = nodes.length;
    if (length > max) {
        var size = length - max;
        for (var i = 0; i < size; i++) {
            document.body.removeChild(document.body.firstElementChild);
        }
    }
}


function replaceImage(id, src) {
    var e = document.getElementById(id);
    if (e) {
        e.src = src;
    }
}

function getScrollPosition() {
    var body = document.body;
    var doc = document.documentElement;

    var height = body.scrollHeight;
    var top = body.scrollTop;

    var clientHeight = body.clientHeight;
    var position = "";

    var a = (height - top);
    var b = (clientHeight + 10);

    if (a < b) {
        position = "bottom";
    } else if (top == 0) {
        position = "top";
    } else {
        position = "middle";
    }
    return position;
}


function scrollToBottom() {
    var body = document.body;
    var height = body.scrollHeight;
    body.scrollTop = height;
}


function hasSelection() {
    var selectionObject = null, rangeObject = null, selectedText = "", selectedHtml = "";
    if (window.getSelection) {
        selectionObject = window.getSelection();
        selectedText = selectionObject.toString();
        rangeObject = selectionObject.getRangeAt(0);
        var docFragment = rangeObject.cloneContents();
        var tempDiv = document.createElement("div");
        tempDiv.appendChild(docFragment);
        selectedHtml = tempDiv.innerHTML;
    } else if (document.selection) {
        selectionObject = document.selection;
        rangeObject = selectionObject.createRange();
        selectedText = rangeObject.text;
        selectedHtml = rangeObject.htmlText;
    }
    var has = false;
    var isEmpty = selectedHtml == undefined || selectedHtml == null || selectedHtml == "undefined" || selectedHtml == "null" || selectedHtml == "&nbsp;";
    has = !isEmpty;
    return has;
}


function insertLastShowHtml(name, head, time, orientation, html) {
    var text = getShowHtml(name, head, time, orientation, html);
    insertLastHtml(text);
}

function insertBeforeShowHtml(name, head, time, orientation, html) {
    var text = getShowHtml(name, head, time, orientation, html);
    insertBeforeHtml(text);
}


function getShowHtml(name, head, time, orientation, html) {
    var hasTime = !isEmpty(time);
    var left = "left" == orientation ? true : false;
    var css = left ? "" : "cur";

    var text = "";
    text = text + "<div class=\"yubeiChat\">";
    text = text + "<dl class=\"clearfix " + css + "\">";
    if (hasTime) {
        text = text + "    <p class=\"time\"><i>" + time + "</i></p>";
    }
    text = text + "    <dt>";
    text = text + "        <img src=\"" + head + "\" alt=\"\">";
    text = text + "    </dt>";
    text = text + "    <dd>";
    if (left) {
        text = text + "        <span class=\"name\">" + name + "</span>";
    }
    text = text + "        <div class=\"txt\">";
    text = text + "            <i class=\"tubaio\"></i>";
    text = text + html;
    text = text + "        </div>";
    text = text + "    </dd>";
    text = text + "</dl>";
    text = text + "</div>";
    return text;
}