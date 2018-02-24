/*!
 * jQuery Sina Emotion v2.0.0
 * http://www.clanfei.com/
 *
 * Copyright 2012-2014 Lanfei
 * Released under the MIT license
 *
 * Date: 2014-01-09T00:24:18+0800
 */
(function($) {

	var opts;

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

		$.getJSON('https://api.weibo.com/2/emotions.json?callback=?', {
			source: opts.appKey,
			language: opts.language
		}, function(json) {

			var item, category;
			var data = json.data;

			$('#sinaEmotion').html('<div class="right"><a href="#" class="prev">&laquo;</a><a href="#" class="next">&raquo;</a></div><ul class="categories"></ul><ul class="faces"></ul><ul class="pages"></ul>');

			for (var i = 0, l = data.length; i < l; ++i) {
				item = data[i];
				category = item.category || defCategory;

				if (!emotions[category]) {
					emotions[category] = [];
					categories.push(category);
				}

				emotions[category].push({
					icon: item.icon,
					phrase: item.phrase
				});

				emotionsMap[item.phrase] = item.icon;
			}

			showCatPage(0);
			showCategory(defCategory);
			callback && callback(data);
		});
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

	$.fn.sinaEmotion = function(options) {

		if (!opts) {
			opts = $.extend({}, $.fn.sinaEmotion.defaults, options);
		}

		this.each(function() {
			var $this = $(this);
			var $target = $(opts.target);
			if (!$target.length) {
				$target = $this.parents('form').find('textarea:eq(0)');
			}
			if (!$target.length) {
				$target = $this.parents('form').find('input[type=text]:eq(0)');
			}

			$this.click(function(event) {
				var offset = $this.offset();
				if (!emotions) {
					loadEmotions();
				}
				$('#sinaEmotion').css({
					top: offset.top -180,//+ $this.outerHeight() + 155,
					left: offset.left
				}).data('target', $target).show();
				return false;
			});
		});

		return this;
	};

	$.fn.parseEmotion = function(options) {

		var that = this;

		if (!opts) {
			opts = $.extend({}, $.fn.sinaEmotion.defaults, options);
		}

		if (!emotions) {
			loadEmotions(function() {
				that.parseEmotion();
			});
			return;
		}

		this.each(function() {
			var $this = $(this);
			var html = $this.html();
			html = html.replace(/<.*?>/g, function($1) {
				$1 = $1.replace('[', '&#91;');
				$1 = $1.replace(']', '&#93;');
				return $1;
			}).replace(/\[[^\[\]]*?\]/g, function($1) {
				var url = emotionsMap[$1];
				if (url) {
					return '<img src="' + url + '" alt="' + $1 + '" />';
				}
				return $1;
			});
			$this.html(html);
		});

		return this;
	};

	$.fn.insertText = function(text) {

		this.each(function() {

			if (this.tagName !== 'INPUT' && this.tagName !== 'TEXTAREA') {
				return;
			}
			if (document.selection) {
				this.focus();
				var cr = document.selection.createRange();
				cr.text = text;
				cr.collapse();
				cr.select();
			} else if (this.selectionStart !== undefined) {
				var start = this.selectionStart;
				var end = this.selectionEnd;
				this.value = this.value.substring(0, start) + text + this.value.substring(end, this.value.length);
				this.selectionStart = this.selectionEnd = start + text.length;
			} else {
				this.value += text;
			}
		});

		return this;
	}

	// default options
	// rows：每页显示的表情数
	// target：表情所要插入的文本框
	// language：简体（cnname）、繁体（twname）
	// appKey：你在新浪微博开放平台的应用ID
	$.fn.sinaEmotion.defaults = {
		rows: 72,
		target: null,
		language: 'cnname',
		appKey: '1362404091'
	};
})(jQuery);