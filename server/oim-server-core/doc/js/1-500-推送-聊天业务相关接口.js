////////////////////////////////////////////////////////////////////////////
//收到聊天业务信息
///////////////////////////////////////////////////////////////////////////
/*************************************************************/
/**
 * 收到聊天室消息
 * "action": "1-500" 
 * "method": "1-23001" 
 */
push = {
	"head" : {
		"key" : "1479892181342",
		"name" : "",
		"action" : "1-500",
		"method" : "1-23001",
		"version" : "1",
		"time" : 1479892181405
	},
	"body" : {
		"userId" : "5606DE0F1D1E591C22883094528920CF",
		"roomId" : "default",
		"userData" : {
			"id" : "5606DE0F1D1E591C22883094528920CF",
			"head" : "http://www.jiaoyijie.cn/static/images/head/head_assistant.png",
			"nickname" : "交易街-老师助理",
			"name" : "15100000008",
			"role" : "2",
			"onlineTimestamp" : 0
		},
		"content" : {
			"font" : {
				"underline" : false,
				"bold" : false,
				"italic" : false,
				"color" : "000000",
				"name" : "微软雅黑",
				"size" : 12
			},
			"sections" : [
				{
					"items" : [
						{
							"type" : "text",
							"value" : "顶顶顶"
						}
					]
				}
			],
			"timestamp" : 1479892181405
		}
	}
};

/*************************************************************/
/**
 * 收到聊天室内的私聊信息
 * "action": "1-500" 
 * "method": "1-24001"
 */
push = {
	"head" : {
		"key" : "1479889659571",
		"name" : "",
		"action" : "1-500",
		"method" : "1-24001",
		"version" : "",
		"time" : 1479889659571
	},
	"body" : {
		"roomId" : "46",
		"sendUserId" : "46", //发送信息用户id
		"receiveUserId" : "5606DE0F1D1E591C22883094528920CF", //接受信息用户id
		"content" : {
			"sections" : [
				{
					"items" : [
						{
							"type" : "text",
							"value" : "[猪头]pig"
						}
					]
				}
			],
			"font" : { //文字样式，扩展用，目前忽略
				"underline" : false, //是否下划线
				"bold" : false, //是否粗体
				"italic" : false, //是否倾斜
				"color" : "000000", //字体颜色
				"name" : "微软雅黑", //
				"size" : 12 //字体尺寸
			},
			"timestamp" : 1479889768809
		}
	}
};


/*************************************************************/
/**
 * 收到私聊信息
 * "action": "1-500" 
 * "method": "1-21001"
 */
push = {
	"head" : {
		"key" : "1479889659571",
		"name" : "",
		"action" : "1-500",
		"method" : "1-21001",
		"version" : "",
		"time" : 1479889659571
	},
	"body" : {
		"sendUserId" : "46", //发送信息用户id
		"receiveUserId" : "5606DE0F1D1E591C22883094528920CF", //接受信息用户id
		"content" : {
			"sections" : [
				{
					"items" : [
						{
							"type" : "text",
							"value" : "[猪头]pig"
						}
					]
				}
			],
			"font" : { //文字样式，扩展用，目前忽略
				"underline" : false, //是否下划线
				"bold" : false, //是否粗体
				"italic" : false, //是否倾斜
				"color" : "000000", //字体颜色
				"name" : "微软雅黑", //
				"size" : 12 //字体尺寸
			},
			"timestamp" : 1479889768809
		}
	}
};


/*************************************************************/
/**
 * 收到聊天室消息提醒，如老师助理欢迎语
 * "action": "1-500" 
 * "method": "1-23003"
 */
push = {
	"head" : {
		"key" : "df6981a7b8594c4ea2d3cce9c0f52948",
		"name" : "",
		"action" : "1-500",
		"method" : "1-23003",
		"version" : "1",
		"time" : 1479884585975
	},
	"body" : {
		"userData" : {
			"id" : "53",
			"head" : "http://www.jiaoyijie.cn/static/images/head/head_assistant.png",
			"nickname" : "交易街-老师助理",
			"name" : "15100000008",
			"role" : "2",
			"onlineTimestamp" : 0
		},
		"roomId" : "default",
		"content" : {
			"sections" : [
				{
					"items" : [
						{
							"type" : "text",
							"value" : "欢迎 \"游客1123150006\" 光临直播室"
						}
					]
				}
			],
			"font" : { //文字样式，扩展用，目前忽略
				"underline" : false, //是否下划线
				"bold" : false, //是否粗体
				"italic" : false, //是否倾斜
				"color" : "000000", //字体颜色
				"name" : "微软雅黑", //
				"size" : 12 //字体尺寸
			},
			"timestamp" : 0
		}
	}
};
