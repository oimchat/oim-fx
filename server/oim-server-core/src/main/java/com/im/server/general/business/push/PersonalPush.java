package com.im.server.general.business.push;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.im.server.general.business.mq.MessageQueueWriteHandler;
import com.im.base.common.util.KeyUtil;
import com.only.net.session.SocketSession;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.server.PushMessage;

@Service
public class PersonalPush {

    @Resource
    MessageQueueWriteHandler messageQueueWriteHandler;

    public void updateUser(String key, UserData user, String userId) {
        Head head = new Head();
        head.setAction("1.100");
        head.setMethod("1.2.0008");
        head.setKey(key);
        head.setTime(System.currentTimeMillis());
        PushMessage message = new PushMessage();
        message.setHead(head);
        message.put("userData", user);
        messageQueueWriteHandler.push(userId, message);
    }

    /**
     * 推送其他客户端登陆信息
     *
     * @param set
     * @param offline
     * @param client
     */
    public void pushOtherOnline(CopyOnWriteArraySet<SocketSession> set, Map<String, Object> client, boolean offline) {

        PushMessage message = new PushMessage();
        message.put("client", client);
        message.put("offline", offline);// 当前设备是否下线：true：下线 false:不用下线

        Head head = new Head();
        head.setAction("1.100");
        head.setMethod("1.2.0001");
        head.setKey(KeyUtil.getKey());
        head.setTime(System.currentTimeMillis());
        message.setHead(head);

        for (SocketSession ss : set) {
            try {
                ss.write(message);
            } catch (Exception e) {
            }
        }

        for (SocketSession ss : set) {
            try {
                ss.close();
            } catch (Exception e) {
            }
        }
    }
}
