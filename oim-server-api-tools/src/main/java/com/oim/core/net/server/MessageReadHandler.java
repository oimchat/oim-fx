package com.oim.core.net.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oim.core.business.back.DataBackActionImpl;
import com.oim.core.business.view.MainView;
import com.oim.core.util.JsonFormatUtil;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.net.connect.ReadHandler;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.bean.HandlerData;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;

public class MessageReadHandler extends AbstractComponent implements ReadHandler {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private final JsonParser jsonParser = new JsonParser();
	
	
	public MessageReadHandler(AppContext appContext) {
		super(appContext);
	}
	

	@Override
	public void read(Object data, Map<String, HandlerData> handlerDataMap) {
		String text = (data instanceof String) ? (String) data : "";
		if (OnlyJsonUtil.mayBeJSON(text)) {
			JsonObject jo = jsonParser.parse(text).getAsJsonObject();

			JsonElement headElement = jo.get("head");

			Head head = gson.fromJson(headElement, Head.class);

			String key = head.getKey();

			HandlerData hd = handlerDataMap.remove(null == key ? "" : key);
			if (null != hd && null != hd.getDataBackAction()) {
				DataBackAction dataBackAction = hd.getDataBackAction();
				if(dataBackAction instanceof DataBackActionImpl){
					text=JsonFormatUtil.formatJson(text);
					((DataBackActionImpl)dataBackAction).back(text);
				}
			}else{
				MainView mv = appContext.getSingleView(MainView.class);
				text=JsonFormatUtil.formatJson(text);
				mv.receive(text);
			}
		}
	}
}
