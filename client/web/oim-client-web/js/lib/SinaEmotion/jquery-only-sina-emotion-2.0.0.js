/*!
 * jQuery Sina Emotion v2.0.0
 * http://www.clanfei.com/
 *
 * Copyright 2012-2014 Lanfei
 * Released under the MIT license
 *
 * Date: 2014-01-09T00:24:18+0800
 */

function SinaEmotion() {
	var own=this;

    own.showEmotion=function (button,inputTag) {
        input=inputTag;
        var offset = button.offset();
        if (!emotions) {
            loadEmotions();
        }
        $('#sinaEmotion').css({
            top: offset.top -220,//+ $this.outerHeight() + 155,
            left: offset.left
        }).data('target', inputTag).show();
    }

    own.parseEmotion = function(text) {
        text = text.replace(/<.*?>/g, function($1) {
            $1 = $1.replace('[', '&#91;');
            $1 = $1.replace(']', '&#93;');
            return $1;
        }).replace(/\[[^\[\]]*?\]/g, function($1) {

            if (!emotions) {
                loadEmotions(function() {

                });
                return $1;
            }
            var url = emotionsMap[$1];
            if (url) {
                return '<img src="' + url + '" alt="' + $1 + '" />';
            }
            return $1;
        });
        return text;
    };
    var input;

    var defaults = {
        rows: 72,
        target: null,
        language: 'cnname',
        appKey: '1362404091'
    };

    var opts=defaults;
    var emotions;
    var categories;
    var emotionsMap;
    var defCategory = '默认';

    var initEvents = function() {
        $('body').bind({
            click: function() {
                $('#sinaEmotion').hide();
            }
        });

        $('#sinaEmotion').bind({
            click: function(event) {
                event.stopPropagation();
            }
        }).delegate('.prev', {
            click: function(event) {
                var page = $('#sinaEmotion .categories').data('page');
                showCatPage(page - 1);
                event.preventDefault();
            }
        }).delegate('.next', {
            click: function(event) {
                var page = $('#sinaEmotion .categories').data('page');
                showCatPage(page + 1);
                event.preventDefault();
            }
        }).delegate('.category', {
            click: function(event) {
                $('#sinaEmotion .categories .current').removeClass('current');
                showCategory($.trim($(this).addClass('current').text()));
                event.preventDefault();
            }
        }).delegate('.page', {
            click: function(event) {
                $('#sinaEmotion .pages .current').removeClass('current');
                var page = parseInt($(this).addClass('current').text() - 1);
                showFacePage(page);
                event.preventDefault();
            }
        }).delegate('.face', {
            click: function(event) {
                var $target = $('#sinaEmotion').hide().data('target');
                $target.insertText($(this).children('img').prop('alt'));
                event.preventDefault();
            }
        });
    };

    var loadEmotions = function(callback) {

        emotions = {};
        categories = [];
        emotionsMap = {};

        $('body').append('<div id="sinaEmotion">正在加载，请稍后...</div>');

        initEvents();
        // var url=sever_url + "/images/face";
        //
        // $.post(url, {},
        //     function(data){
        //         var item, category;
        //         //var data = json.data;
        //
        //         $('#sinaEmotion').html('<div class="right"><a href="#" class="prev">&laquo;</a><a href="#" class="next">&raquo;</a></div><ul class="categories"></ul><ul class="faces"></ul><ul class="pages"></ul>');
        //
        //         for (var i = 0, l = data.length; i < l; ++i) {
        //             item = data[i];
        //             category = item.category || defCategory;
        //
        //             if (!emotions[category]) {
        //                 emotions[category] = [];
        //                 categories.push(category);
        //             }
        //
        //             emotions[category].push({
        //                 icon: item.icon,
        //                 phrase: item.phrase
        //             });
        //
        //             emotionsMap[item.phrase] = item.icon;
        //         }
        //
        //         showCatPage(0);
        //         showCategory(defCategory);
        //         callback && callback(data);
        //     }, "json");

        // doOnlyAjax(url, "", false, function (data) {
        //
        // });
        // $.postJSON(url, {
        //     source: opts.appKey,
        //     language: opts.language
        // }, function(json) {
        //
        //     var item, category;
        //     var data = json.data;
        //
        //     $('#sinaEmotion').html('<div class="right"><a href="#" class="prev">&laquo;</a><a href="#" class="next">&raquo;</a></div><ul class="categories"></ul><ul class="faces"></ul><ul class="pages"></ul>');
        //
        //     for (var i = 0, l = data.length; i < l; ++i) {
        //         item = data[i];
        //         category = item.category || defCategory;
        //
        //         if (!emotions[category]) {
        //             emotions[category] = [];
        //             categories.push(category);
        //         }
        //
        //         emotions[category].push({
        //             icon: item.icon,
        //             phrase: item.phrase
        //         });
        //
        //         emotionsMap[item.phrase] = item.icon;
        //     }
        //
        //     showCatPage(0);
        //     showCategory(defCategory);
        //     callback && callback(data);
        // });
    };

    var showCatPage = function(page) {

        var html = '';
        var length = categories.length;
        var maxPage = Math.ceil(length / 5);
        var $categories = $('#sinaEmotion .categories');
        var category = $categories.data('category') || defCategory;

        page = (page + maxPage) % maxPage;

        for (var i = page * 5; i < length && i < (page + 1) * 5; ++i) {
            html += '<li class="item"><a href="#" class="category' + (category == categories[i] ? ' current' : '') + '">' + categories[i] + '</a></li>';
        }

        $categories.data('page', page).html(html);
    };

    var showCategory = function(category) {
        $('#sinaEmotion .categories').data('category', category);
        showFacePage(0);
        showPages();
    };

    var showFacePage = function(page) {

        var face;
        var html = '';
        var pageHtml = '';
        var rows = opts.rows;
        var category = $('#sinaEmotion .categories').data('category');
        var faces = emotions[category];
        var length = faces.length;
        page = page || 0;

        for (var i = page * rows, l = faces.length; i < l && i < (page + 1) * rows; ++i) {
            face = faces[i];
            html += '<li class="item"><a href="#" class="face"><img src="' + face.icon + '" alt="' + face.phrase + '" /></a></li>';
        }

        $('#sinaEmotion .faces').html(html);
    };

    var showPages = function() {

        var html = '';
        var rows = opts.rows;
        var category = $('#sinaEmotion .categories').data('category');
        var faces = emotions[category];
        var length = faces.length;

        if (length > rows) {
            for (var i = 0, l = Math.ceil(length / rows); i < l; ++i) {
                html += '<li class="item"><a href="#" class="page' + (i == 0 ? ' current' : '') + '">' + (i + 1) + '</a></li>';
            }
            $('#sinaEmotion .pages').html(html).show();
        } else {
            $('#sinaEmotion .pages').hide();
        }
    }

    if (!emotions) {
        loadEmotions();
    }
}
