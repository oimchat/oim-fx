package com.oim.server.api.tools.ui.main;

import java.util.ArrayList;
import java.util.List;

import com.oim.common.util.KeyUtil;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author: XiaHui
 * @date: 2017-12-12 20:18:45
 */
public class ShowPane extends StackPane {

	WebView webView = new WebView();
	WebPage webPage;
	WebEngine webEngine;
	boolean isLoad = false;
	List<String> jsList = new ArrayList<String>();
	Object member;

	public ShowPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(webView);
		webEngine = webView.getEngine();
		webPage = Accessor.getPageFor(webEngine);
		webPage.setEditable(true);
		//webPage.setContextMenuEnabled(false);
		webView.setFocusTraversable(true);
		// webView.setBlendMode(BlendMode.DARKEN);// 透明
		initializeHtml();
	}

	private void iniEvent() {
		webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> ov, State oldState, State newState) -> {

			if (newState == State.SUCCEEDED) {
				isLoad = true;
				initWeb(webEngine);
				if (!jsList.isEmpty()) {
					int size = jsList.size();
					for (int i = 0; i < size; i++) {
						String js = jsList.get(i);
						webEngine.executeScript(js);
					}
					jsList.clear();
				}
			}
		});
		webView.setOnMouseClicked(m -> {
			m.consume();
		});
		webView.setOnDragDropped(a -> {

		});
	}

	public void setWebOnDragDropped(EventHandler<? super DragEvent> value) {
		webView.setOnDragDropped(value);
	}

	public void initializeHtml() {
		webView.getEngine().load(ShowPane.class.getResource("/html/api.html").toExternalForm());
	}

	public String getHtml() {
		String html = webPage.getHtml(webPage.getMainFrame());
		return html;
	}

	public void insertLastHtml(String html) {
		String h = escapeJavaStyleString(html, true, true);
		StringBuilder js = new StringBuilder();

		js.append("function insertHtml() {");

		js.append("    var position=getScrollPosition();");

		js.append("insertLastHtml('");
		js.append(h);
		js.append("');");

		js.append("    if(position==\"bottom\"){");
		js.append("        scrollToBottom();");
		js.append("    }");

		js.append("}");

		js.append("insertHtml();");

		executeScript(js.toString());
	}

	public void insertLastJsonViewer(String json) {
		String id = KeyUtil.getKey();

		StringBuilder sb = new StringBuilder();
		sb.append("<pre style=\"border: 1px solid #aaa;\" id=\"" + id + "\">");
		sb.append(json);
		sb.append("</pre>");
		insertLastHtml(sb.toString());

		StringBuilder js = new StringBuilder();
		js.append("function insertJson() {");

		js.append("	var json=" + json + ";");

		js.append("	$('#" + id + "').jsonViewer(json);");
		js.append("}");

		js.append("insertJson();");
		executeScript(js.toString());
	}

	public void setBodyHtml(String html) {
		String h = escapeJavaStyleString(html, true, true);
		StringBuilder js = new StringBuilder();
		js.append("setBodyHtml('");
		js.append(h);
		js.append("');");
		executeScript(js.toString());
	}

	public void checkElement() {
		checkElement(500);
	}

	public void checkElement(int count) {
		StringBuilder js = new StringBuilder();
		js.append("checkElement(");
		// js.append(count);
		js.append(");");
		executeScript(js.toString());
	}

	public void executeScript(String js) {
		if (isLoad) {
			webEngine.executeScript(js);
		} else {
			jsList.add(js);
		}
	}

	private String hex(int i) {
		return Integer.toHexString(i);
	}

	public String escapeJavaStyleString(String str, boolean escapeSingleQuote, boolean escapeForwardSlash) {
		StringBuilder out = new StringBuilder("");
		if (str == null) {
			return null;
		}
		int sz;
		sz = str.length();
		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);
			// handle unicode
			if (ch > 0xfff) {
				out.append("\\u").append(hex(ch));
			} else if (ch > 0xff) {
				out.append("\\u0").append(hex(ch));
			} else if (ch > 0x7f) {
				out.append("\\u00").append(hex(ch));
			} else if (ch < 32) {
				switch (ch) {
				case '\b':
					out.append('\\');
					out.append('b');
					break;
				case '\n':
					out.append('\\');
					out.append('n');
					break;
				case '\t':
					out.append('\\');
					out.append('t');
					break;
				case '\f':
					out.append('\\');
					out.append('f');
					break;
				case '\r':
					out.append('\\');
					out.append('r');
					break;
				default:
					if (ch > 0xf) {
						out.append("\\u00").append(hex(ch));
					} else {
						out.append("\\u000").append(hex(ch));
					}
					break;
				}
			} else {
				switch (ch) {
				case '\'':
					if (escapeSingleQuote) {
						out.append('\\');
					}
					out.append('\'');
					break;
				case '"':
					out.append('\\');
					out.append('"');
					break;
				case '\\':
					out.append('\\');
					out.append('\\');
					break;
				case '/':
					if (escapeForwardSlash) {
						out.append('\\');
					}
					out.append('/');
					break;
				default:
					out.append(ch);
					break;
				}
			}
		}
		return out.toString();
	}

	private void initWeb(WebEngine webEngine) {
		JSObject window = (JSObject) webEngine.executeScript("window");
		if (isLoad && null != member) {
			window.setMember("oim", member);
		}
	}

	class HtmlData {
		String type;
		String html;
	}

	public void setJavaScriptInterface(Object member) {
		this.member = member;
		if (isLoad) {
			initWeb(webEngine);
		}
	}
}
