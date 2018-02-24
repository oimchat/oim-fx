////////////////////////////////////////////////////////////////////////////
//user业务接口start
///////////////////////////////////////////////////////////////////////////
/*************************************************************/
/**
 * 
 * 获取用户信息
 "action": "1-101" 
 "method": "1-10005"
 */
request = {
	"head" : {
		"key" : "1479884508485",
		"name" : "",
		"clientVersion" : "2.0",
		"clientType" : "1",
		"action" : "1-101",
		"method" : "1-10005",
		"version" : "",
		"time" : 1479884508485
	},
	"body" : {
		"userId" : "99A22AFBF987F43D2F83C035A6F159A3" //[not null] 用户id
	}
};

response = {
	"head" : {
		"key" : "1479884508485",
		"name" : "",
		"action" : "1-101",
		"method" : "1-10005",
		"version" : "1",
		"resultCode" : "1",
		"resultMessage" : "",
		"time" : 1479884528961
	},
	"info" : {
		"success" : true,
		"errors" : [],
		"warnings" : []
	},
	"body" : {
		"userData" : {
			"id" : "46", //用户id
			"head" : "http://headimg.jiaoyijie.cn/FhCFxg_vcxofgLh4W4MfVBjBpcX2", //用户头像
			"nickname" : "交易街-管理员", //用户昵称
			"name" : "交易街-管理员", //用户姓名
			"role" : "1", //用户角色 0：普通用户 1：管理员 2：老师助手 3：游客
			"onlineDateTime" : "2016-11-23 14:59:22", //用户上线时间，字符类型
			"onlineTimestamp" : 1479884362368 //用户上线时间的时间戳，long
		}
	}
};