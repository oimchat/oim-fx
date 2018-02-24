package com.oim.core.business.action;

import com.oim.core.business.service.PersonalService;
import com.only.common.result.Info;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;

/**
 * 描述：用户个人业务处理
 *
 * @author XiaHui
 * @date 2016年1月7日 下午7:45:00
 * @version 0.0.1
 */
@ActionMapping(value = "1.100")
public class PersonalAction extends AbstractAction {

    public PersonalAction(AppContext appContext) {
        super(appContext);
    }

    /**
     * 登录后执行操作
     *
     * @param info
     * @param user
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     */
    @MethodMapping(value = "1.1.0001")
    public void loginBack(Info info , 
    		@Define("userData") UserData userData, 
    		@Define("userHead") UserHead userHead) {
    }

    /**
     * 接受服务器推送的账号其他客户端登录信息
     *
     * @param info
     * @author: XiaHui
     * @createDate: 2017年4月6日 下午2:22:23
     * @update: XiaHui
     * @updateDate: 2017年4月6日 下午2:22:23
     */
    @MethodMapping(value = "1.2.0001")
    public void handleDifferentLogin(Info info) {
        PersonalService ps = appContext.getService(PersonalService.class);
        ps.handleDifferentLogin();
    }

    /**
     * 接受账号另外被登录信息
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param message
     */
    @MethodMapping(value = "0003")
    public void handleDifferentLogin() {

    }

    /**
     * *
     * 修改个人信息后接受服务器推送更新
     *
     * @Author: XiaHui
     * @Date: 2016年2月16日
     * @ModifyUser: XiaHui
     * @ModifyDate: 2016年2月16日
     * @param userMessage
     */
    @MethodMapping(value = "0004")
    public void updateUserBack(Info info) {

    }
}
