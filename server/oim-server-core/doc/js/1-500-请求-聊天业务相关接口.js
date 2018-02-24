////////////////////////////////////////////////////////////////////////////
//聊天业务接口start
///////////////////////////////////////////////////////////////////////////

/**
 * 聊天内容格式说明，所有聊天信息格式均为统一格式，目前聊天室尚未支持换行，但是当前设计中支持换行文字，先可兼容使用
 * 
 */
//看以下文字信息转为消息dome
/**
 * 你好啊！<img src="http://xxxx.jpg"/>,啦啦啦。/n
 * 哇哇哇哇哇<img src="http://face/1.jpg"/>()
 * 
 */

/**
 * 图文混排item有以下类型：<br>
 * 文本：{"type":"text","value":"你好啊！"}
 * 图片：{"type":"image","value":"http://xxxx.jpg"} 或者
 *	   {"type":"image","value":"{\"type\":\"1\",\"id\":\"b1a1a9c6-bd39-4d61-8984-f9af8c3afd60\",\"extension\":\"jpg\",\"name\":\"02.jpg\",\"url\":\"http://02.jpg\"}"}
 *     图片的value以前为图片地址，新版为json字符串（注意是字符串） 
 * 表情：{"type":"face","value":"classical,52"}表情value为categoryId,key 如果不支持，可以请求Htt接口中的获取表情接口
 * http://192.168.1.5:8010/im-api/api/v1/im/chat/face
 */
var content = {
	"sections" : [ //段落，表示换行
		{
			"items" : [ //内容集合，
				{
					"type" : "text", //内容类型目前有:text：文本 image：图片地址
					"value" : "你好啊！"
				},
				{
					"type" : "image",
					"value" : "http://xxxx.jpg"
				},
				{
					"type" : "text",
					"value" : "啦啦啦。"
				}
			]
		}, {
			"items" : [ //内容集合，
				{
					"type" : "text",
					"value" : "哇哇哇哇哇"
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
	"timestamp" : 1479699748949 //发送时间，通常为服务器时间
};

/*************************************************************/
/**
 * 获取聊天室聊天记录
 * "action": "1-500" 
 * "method": "1-13004"
 */

request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-13004",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"roomId" : "default", //聊天室id
		"chatQuery" : {
			"text" : "", //[allow null,allow ""]关键词
			"startDate" : "", //[allow null,allow ""]查询时间的开始时间，格式"2016-08-12 11:26:00"
			"endDate" : "" //[allow null,allow ""]查询时间的结束时间，格式"2016-10-12 11:26:00"
		},
		"page" : {
			"pageSize" : 30, //获取记录数量
			"pageNumber" : 1 //页码
		}
	}
};
response = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-13004",
		"version" : "1",
		"resultCode" : "1",
		"resultMessage" : "",
		"time" : 1479884634725
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"contents" : [
			{
				"userData" : {
					"id" : "7DA6C874D378CD274A04C939D31103D5",
					"head" : "http://jiaoyijie.cn/static/images/head/head_body_2.png",
					"nickname" : "游客1121091837",
					"name" : "游客1121091837",
					"role" : "3"
				},
				"content" : {
					"sections" : [
						{
							"items" : [
								{
									"type" : "text",
									"value" : "[衰]"
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
					"timestamp" : 1479699748949
				}
			},
			{
				"userData" : {
					"id" : "7DA6C874D378CD274A04C939D31103D5",
					"head" : "http://jiaoyijie.cn/static/images/head/head_body_2.png",
					"nickname" : "游客1121091837",
					"name" : "游客1121091837",
					"role" : "3"
				},
				"content" : {
					"sections" : [
						{
							"items" : [
								{
									"type" : "text",
									"value" : "[黑线]"
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
					"timestamp" : 1479699752506
				}
			}
		],
		"page" : { //分页信息
			"pageNumber" : 1, //当前页码
			"totalPage" : 1 //总页数
		},
		"roomId" : "default"
	}
};


/*************************************************************/
/**
 * 发送聊天室聊天信息
 * "action": "1-500" 
 * "method": "1-13001",
 */
request = {
	"head" : {
		"key" : "1479892857487",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-13001",
		"version" : "",
		"time" : 1479892857487
	},
	"body" : {
		"roomId" : "default",
		"userId" : "99A22AFBF987F43D2F83C035A6F159A3",
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
							"value" : "123"
						}
					]
				}
			]
		}
	}
};

//错误码/warnings："0010001" 全体禁言 ,"0010002" 表示被禁言

response = {
	"head" : {
		"key" : "1479892857487",
		"name" : "",
		"action" : "1-500",
		"method" : "1-13001",
		"version" : "",
		"resultCode" : "",
		"resultMessage" : "",
		"time" : 1479892870043
	},
	"info" : {
		"success" : false, //true：发送成功 false：发送失败
		"errors" : [],
		"warnings" : [
			{
				"code" : "0010001", //错误码，表示全体禁言
				"text" : ""
			}
		]
	},
	"body" : {}
};


/*************************************************************/
/**
 * 发送聊天室内的私聊信息
 *  "action": "1-500" 
 *  "method": "1-14001"
 */

request = {
	"head" : {
		"key" : "1479889659571",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-14001",
		"version" : "",
		"time" : 1479889659571
	},
	"body" : {
		"roomId" : "46",
		"sendUserId" : "46", //发送者用户id
		"receiveUserId" : "5606DE0F1D1E591C22883094528920CF", //接受信息用户id
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
							"value" : "[猪头]pig"
						}
					]
				}
			]
		}
	}
};

response = {
	"head" : {
		"key" : "1479889659571",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11001",
		"version" : "",
		"resultCode" : "",
		"resultMessage" : "",
		"time" : 1479889768841
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {}
};



/*************************************************************/
/**
 * 发送私聊信息
 *  "action": "1-500" 
 *  "method": "1-11001"
 */

request = {
	"head" : {
		"key" : "1479889659571",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11001",
		"version" : "",
		"time" : 1479889659571
	},
	"body" : {
		"sendUserId" : "46", //发送者用户id
		"receiveUserId" : "5606DE0F1D1E591C22883094528920CF", //接受信息用户id
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
							"value" : "[猪头]pig"
						}
					]
				}
			]
		}
	}
};

response = {
	"head" : {
		"key" : "1479889659571",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11001",
		"version" : "",
		"resultCode" : "",
		"resultMessage" : "",
		"time" : 1479889768841
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {}
};


/*************************************************************/
/**
 * 获取私聊聊天记录（此方法主要以数据库id为条件，为可靠的接口）
 * "action": "1-500" 
 * "method": "1-11005"
 */

request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11005",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"sendUserId" : "1", //发送用户id
		"receiveUserId" : "2", //接受用户id
		"startId" : "xxxxxxxxxxxxxx", //[allow null,allow ""]获取聊天记录的起始id,为空时返回最新记录
		"direction" : "history", //方向：history：获取startId之前记录；latest：获取startId之后记录
		"count" : 30 //获取记录数量
	}
};

response = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11005",
		"version" : "1",
		"resultCode" : "1",
		"resultMessage" : "",
		"time" : 1479884634725
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"page" : {
			"direction" : "history"
		},
		"sendUserId" : "1", //发送用户id(原请求时的值传回来)
		"receiveUserId" : "2", //接受用户id(原请求时的值传回来)
		"contents" : [
			{
				"messageKey" : "xxxxxxx", //原消息head中的key
				"contentId" : "xxxxxxx", //数据库保存消息内容的id
				"sendUserData" : {
					"id" : "7DA6C874D378CD274A04C939D31103D5",
					"head" : "http://jiaoyijie.cn/static/images/head/head_body_2.png",
					"nickname" : "游客1121091837",
					"name" : "游客1121091837",
					"role" : "3"
				},
				"receiveUserData" : {
					"id" : "xxxxxxxx",
					"head" : "http://jiaoyijie.cn/static/images/head/head_body_2.png",
					"nickname" : "xxxxx",
					"name" : "xxxxxx",
					"role" : "3"
				},
				"content" : {
					"sections" : [
						{
							"items" : [
								{
									"type" : "text",
									"value" : "[衰]"
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
					"timestamp" : 1479699748949
				}
			}
		]
	}
};



/*************************************************************/
/**
 * 获取私聊记录（此方法主要以消息key为条件，为不可靠的接口）
 * "action": "1-500" 
 * "method": "1-11006"
 */

request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11006",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"sendUserId" : "1", //发送用户id
		"receiveUserId" : "2", //接受用户id
		"startMessageKey" : "xxxxxxxxxxxxxx", //[allow null,allow ""]获取聊天记录的起始消息id，就是head中的key值,为空时返回最新记录
		"direction" : "history", //方向：history：获取startMessageKey之前记录；latest：获取startMessageKey之后记录
		"count" : 30 //获取记录数量
	}
};

response = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11006",
		"version" : "1",
		"resultCode" : "1",
		"resultMessage" : "",
		"time" : 1479884634725
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"page" : {
			"direction" : "history"
		},
		"sendUserId" : "1", //发送用户id(原请求时的值传回来)
		"receiveUserId" : "2", //接受用户id(原请求时的值传回来)
		"contents" : [
			{
				"messageKey" : "xxxxxxx", //原消息head中的key
				"contentId" : "xxxxxxx", //数据库保存消息内容的id
				"sendUserData" : {
					"id" : "7DA6C874D378CD274A04C939D31103D5",
					"head" : "http://jiaoyijie.cn/static/images/head/head_body_2.png",
					"nickname" : "游客1121091837",
					"name" : "游客1121091837",
					"role" : "3"
				},
				"receiveUserData" : {
					"id" : "xxxxxxxx",
					"head" : "http://jiaoyijie.cn/static/images/head/head_body_2.png",
					"nickname" : "xxxxx",
					"name" : "xxxxxx",
					"role" : "3"
				},
				"content" : {
					"sections" : [
						{
							"items" : [
								{
									"type" : "text",
									"value" : "[衰]"
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
					"timestamp" : 1479699748949
				}
			}
		]
	}
};



/*************************************************************/
/**
 * 获取给当前用户发送离线信息的用户列表
 * "action": "1-500" 
 * "method": "1-11008"
 */

request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11008",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"userId" : "55" //当前用户id
	}
};

response = {
	"head" : {
		"resultCode" : "1",
		"resultMessage" : "",
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11008",
		"version" : "1",
		"time" : 1493258679081
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"userIds" : [ "46" ] //给当前用户发送离线消息的用户数组
	}
};



/*************************************************************/
/**
 * 标记当前用户与其聊天用户的信息标记为已读
 * "action": "1-500" 
 * "method": "1-11009"
 */

request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11009",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"receiveUserId" : "55", //当前用户id
		"sendUserId" : "46" //发送信息的用户
	}
};


response = {
	"head" : {
		"resultCode" : "1",
		"resultMessage" : "",
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11009",
		"version" : "1",
		"time" : 1493259037180
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {}
};



/*************************************************************/
/**
 * 获取离线消息数量
 * "action": "1-500" 
 * "method": "1-11010"
 */

request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11010",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"userId" : "55", //当前用户id
		"count" : 20 //获取记录数
	}
};

/**
 * 响应结果
 */
response = {
	"head" : {
		"resultCode" : "1",
		"resultMessage" : "",
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11010",
		"version" : "1",
		"time" : 1493259241065
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"list" : [ {
			"sendUserId" : "46", //发送消息的用户id
			"count" : 5 //未读记录数量
		} ]
	}
};


/*************************************************************/
/**
 * 获取最近聊天列表
 * "action": "1-500" 
 * "method": "1-11011"
 */

request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11011",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"userId" : "55",
		"count" : 20
	}
};

response = {
	"head" : {
		"resultCode" : "1",
		"resultMessage" : "",
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11011",
		"version" : "1",
		"time" : 1493259343318
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"userId" : "55",
		"userLastList" : [ { //最近和用户聊天的列表
			"messageKey" : "1479889659571",
			"contentId" : "106d078c-06cb-4db4-a29b-d612311a05bc",
			"content" : {
				"font" : {
					"underline" : false,
					"bold" : false,
					"italic" : false,
					"color" : "000000",
					"name" : "微软雅黑",
					"size" : 12
				},
				"sections" : [ {
					"items" : [ {
						"type" : "text",
						"value" : "[猪头]pig"
					} ]
				} ],
				"timestamp" : 1493258481705
			},
			"receiveUserData" : {
				"id" : "55"
			},

			"sendUserData" : {
				"id" : "46"
			}
		} ],
		"groupLastList" : [], //最近和群聊天的列表
		"roomLastList" : [] //最近和聊天室的列表
	}
};


/*************************************************************/
/**
 * 根据离线发送者的用户id，获取离线消息数量
 * "action": "1-500" 
 * "method": "1-11012"
 */

request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11012",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"userId" : "55", //当前用户id
		"ids" : [ "46", "48" ]
	}
};

/**
 * 响应结果
 */
response = {
	"head" : {
		"resultCode" : "1",
		"resultMessage" : "",
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11010",
		"version" : "1",
		"time" : 1493259241065
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"list" : [ {
			"sendUserId" : "46", //发送消息的用户id
			"count" : 5 //未读记录数量
		} ]
	}
};




/*************************************************************/
/**
 * 删除最近聊天记录
 * "action": "1-500" 
 * "method": "1-11013"
 */

/**
 * 请求字段说明：
 * 
 */
//userId:当前用户的id
//chatId:与当前用户最后聊天的对象id，与用户聊天的的是userId,是聊天室的则是roomId,是群则是groupId
//type:聊天记录类型
/**
 * 1：私聊 ->chatId=userId
 * 2：群聊 ->chatId=groupId
 * 3：聊天室 ->chatId=roomId
 * 4：聊天组 ->chatId=teamId
 */
request = {
	"head" : {
		"key" : "1479884620262",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-500",
		"method" : "1-11013",
		"version" : "1",
		"time" : 1479884620262
	},
	"body" : {
		"userId" : "55",
		"chatId" : "55",
		"type" : "1"
	}
};

response = {
	"head" : {
		"resultCode" : "1",
		"resultMessage" : "",
		"key" : "1479884620262",
		"name" : "",
		"action" : "1-500",
		"method" : "1-11013",
		"version" : "1",
		"time" : 1493259343318
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
	}
};