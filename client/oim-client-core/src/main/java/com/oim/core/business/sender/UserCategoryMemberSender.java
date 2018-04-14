package com.oim.core.business.sender;

import com.oim.core.business.box.PersonalBox;
import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.UserCategoryMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * 描述：
 *
 * @author XiaHui
 * @date 2016年1月8日 下午8:22:48
 * @version 0.0.1
 */
public class UserCategoryMemberSender extends BaseSender {

    public UserCategoryMemberSender(AppContext appContext) {
        super(appContext);
    }

    /**
     * 添加好友
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param userCategoryId
     * @param memberUserId
     * @param remark
     * @param action
     */
    public void addUserCategoryMember(String userCategoryId, String memberUserId, String remark, DataBackAction action) {
    	PersonalBox pb=appContext.getBox(PersonalBox.class);
        UserData user =pb.getUserData();

        UserCategoryMember userCategoryMember = new UserCategoryMember();

        userCategoryMember.setUserCategoryId(userCategoryId);
        userCategoryMember.setMemberUserId(memberUserId);
        userCategoryMember.setRemark(remark);
        userCategoryMember.setOwnUserId(user.getId());

        Message message = new Message();
        message.put("userCategoryMember", userCategoryMember);

        Head head = new Head();
        head.setAction("1.103");
        head.setMethod("1.1.0001");
        head.setTime(System.currentTimeMillis());
        message.setHead(head);
        this.write(message, action);
    }
    
    /**
     * 修改好友备注
     * @author XiaHui
     * @date 2017年9月4日 下午3:57:12
     * @param userCategoryMemberId
     * @param remark
     * @param action
     */
    public void updateUserCategoryMemberRemark(String memberUserId, String remark,DataBackAction action) {

        Message message = new Message();
        message.put("memberUserId", memberUserId);
        message.put("remark", remark);

        Head head = new Head();
        head.setAction("1.103");
        head.setMethod("1.1.0002");
        head.setTime(System.currentTimeMillis());
        message.setHead(head);
        this.write(message, action);
    }
    
    /**
     * 移动好友
     * @author XiaHui
     * @date 2017年9月4日 下午3:57:49
     * @param userCategoryMemberId
     * @param userCategoryId
     * @param action
     */
    public void moveUserToCategory(String memberUserId, String userCategoryId,DataBackAction action) {

        Message message = new Message();
        message.put("memberUserId", memberUserId);
        message.put("userCategoryId", userCategoryId);

        Head head = new Head();
        head.setAction("1.103");
        head.setMethod("1.1.0003");
        head.setTime(System.currentTimeMillis());
        message.setHead(head);
        this.write(message, action);
    }
    
    /**
     * 删除好友
     * @author XiaHui
     * @date 2017年9月4日 下午4:37:46
     * @param memberUserId
     * @param action
     */
    public void deleteUserCategoryMember(String memberUserId,DataBackAction action) {

        Message message = new Message();
        message.put("memberUserId", memberUserId);

        Head head = new Head();
        head.setAction("1.103");
        head.setMethod("1.1.0004");
        head.setTime(System.currentTimeMillis());
        message.setHead(head);
        this.write(message, action);
    }
}
