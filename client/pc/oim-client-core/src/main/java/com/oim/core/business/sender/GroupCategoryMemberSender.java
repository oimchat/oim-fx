package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.GroupCategoryMember;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * 描述：
 *
 * @author XiaHui
 * @date 2016年1月8日 下午8:22:48
 * @version 0.0.1
 */
public class GroupCategoryMemberSender extends BaseSender {

    public GroupCategoryMemberSender(AppContext appContext) {
        super(appContext);
    }

    /**
     * 向服务器发送添加加入群信息
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param groupCategoryId
     * @param groupId
     * @param remark
     * @param action
     */
    public void addGroupCategoryMember(String groupCategoryId, String groupId, String remark, DataBackAction action) {
        GroupCategoryMember groupCategoryMember = new GroupCategoryMember();

        groupCategoryMember.setGroupCategoryId(groupCategoryId);// 要加入的群分配的分组。
        groupCategoryMember.setGroupId(groupId);// 群id
        groupCategoryMember.setRemark(remark);// 群的备注名

        Message message = new Message();
        message.put("groupCategoryMember", groupCategoryMember);
        Head head = new Head();
        head.setAction("1.203");
        head.setMethod("1.1.0001");
        head.setTime(System.currentTimeMillis());
        message.setHead(head);

        this.write(message, action);
    }
    
    /**
     * 移动群
     * @author XiaHui
     * @date 2017年9月4日 下午5:00:34
     * @param groupCategoryId
     * @param groupId
     * @param action
     */
    public void moveGroupToCategory(String groupCategoryId, String groupId,DataBackAction action) {

        Message message = new Message();
        message.put("groupCategoryId", groupCategoryId);
        message.put("groupId", groupId);
        
        Head head = new Head();
        head.setAction("1.203");
        head.setMethod("1.1.0003");
        head.setTime(System.currentTimeMillis());
        message.setHead(head);
        this.write(message, action);
    }
    
    /**
     * 退出群
     * @author XiaHui
     * @date 2017年9月4日 下午5:00:10
     * @param groupId
     * @param action
     */
    public void quitGroup(String groupId,DataBackAction action) {

        Message message = new Message();
        message.put("groupId", groupId);
        
        Head head = new Head();
        head.setAction("1.203");
        head.setMethod("1.1.0004");
        head.setTime(System.currentTimeMillis());
        message.setHead(head);
        this.write(message, action);
    }
}
