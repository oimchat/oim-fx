////////////////////////////////////////////////////////////////////////////
//聊天室业务接口start
///////////////////////////////////////////////////////////////////////////
/*************************************************************/
/**
 * 加入聊天室
 * "action": "1-300"
 * "method": "1-10001"
 */
request = {
	"head" : {
		"key" : "1479884555276",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-300",
		"method" : "1-10001",
		"version" : "1",
		"time" : 1479884555276
	},
	"body" : {
		"userId" : "99A22AFBF987F43D2F83C035A6F159A3", //[not null]
		"roomId" : "default" //[not null]目前就一个聊天室，默认default，之后所有接口均为default
	}
};

response = {
	"head" : {
		"key" : "1479884555276",
		"name" : "",
		"action" : "1-300",
		"method" : "1-10001",
		"version" : "",
		"resultCode" : "1",
		"resultMessage" : "",
		"time" : 1479884567926
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
 * 退出聊天室
 * "action": "1-300"
 * "method": "1-10002"
 */
request = {
	"head" : {
		"key" : "1479884555276",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-300",
		"method" : "1-10002",
		"version" : "1",
		"time" : 1479884555276
	},
	"body" : {
		"userId" : "99A22AFBF987F43D2F83C035A6F159A3", //[not null]
		"roomId" : "default" //[not null]目前就一个聊天室，默认default，之后所有接口均为default
	}
};

response = {
	"head" : {
		"key" : "1479884555276",
		"name" : "",
		"action" : "1-300",
		"method" : "1-10002",
		"version" : "",
		"resultCode" : "1",
		"resultMessage" : "",
		"time" : 1479884567926
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
 * 获取聊天室是否被全体禁言
 * "action": "1-300"
 * "method": "1-10003"
 */
request = {
	"head" : {
		"key" : "1479884585789",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-300",
		"method" : "1-10003",
		"version" : "1",
		"time" : 1479884585789
	},
	"body" : {
		"roomId" : "default"
	}
};

response = {
	"head" : {
		"key" : "1479884585789",
		"name" : "",
		"action" : "1-300",
		"method" : "1-10003",
		"version" : "1",
		"resultCode" : "1",
		"resultMessage" : "",
		"time" : 1479884605069
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"isBan" : false //true：聊天室被禁言 false：聊天室没有被禁言
	}
};

/*************************************************************/
/**
 * 获取聊天室用户列表
 * "action": "1-300" 
 * "method": "1-10004"
 */
request = {
	"head" : {
		"key" : "1479884604884",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-300",
		"method" : "1-10004",
		"version" : "1",
		"time" : 1479884604884
	},
	"body" : {
		"roomId" : "default"
	}
};

response = {
	"head" : {
		"key" : "1479884604884",
		"name" : "",
		"action" : "1-300",
		"method" : "1-10004",
		"version" : "",
		"resultCode" : "1",
		"resultMessage" : "",
		"time" : 1479884620435
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"userDataList" : [
			{
				"id" : "46", //用户id
				"head" : "http://headimg.jiaoyijie.cn/FhCFxg_vcxofgLh4W4MfVBjBpcX2", //用户头像
				"nickname" : "交易街-管理员", //用户昵称
				"name" : "交易街-管理员", //用户姓名
				"role" : "1", //用户角色 0：普通用户 1：管理员 2：老师助手 3：游客
				"onlineDateTime" : "2016-11-23 14:59:22", //用户上线时间，字符类型
				"onlineTimestamp" : 1479884362368 //用户上线时间的时间戳，long
			},
			{
				"id" : "99A22AFBF987F43D2F83C035A6F159A3",
				"head" : "http://jiaoyijie.cn/static/images/head/head_body_2.png",
				"nickname" : "游客1123150006",
				"name" : "游客1123150006",
				"role" : "3",
				"onlineDateTime" : "2016-11-23 15:01:48",
				"onlineTimestamp" : 1479884406743
			}
		],
		"roomId" : "default"
	}
};

