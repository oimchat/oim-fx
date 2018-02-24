package com.oim.fx.ui.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.HyperlinkEvent;

import org.codefx.libfx.control.webview.WebViewHyperlinkListener;
import org.codefx.libfx.control.webview.WebViews;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLDocument;
import org.w3c.dom.html.HTMLElement;

import com.sun.javafx.webkit.Accessor;
import com.sun.javafx.webkit.KeyCodeMap;
import com.sun.webkit.LoadListenerClient;
import com.sun.webkit.WebPage;
import com.sun.webkit.event.WCKeyEvent;
import com.sun.webkit.event.WCMouseEvent;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class ShowPanel {

	WebView webView = new WebView();
	WebPage webPage;
	WebEngine webEngine;
	ContextMenu contextMenu = new ContextMenu();

	boolean isLoad = false;
	List<String> jsList = new ArrayList<String>();
	Object member;
	
	public ShowPanel() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		MenuItem mi = new MenuItem("复制");
		contextMenu.getItems().add(mi);

		mi.setOnAction(a -> {
			KeyEvent ke = createKeyEvent(KeyEvent.KEY_PRESSED, KeyCode.C, "", "", false, true, false, false);
			processKeyEvent(ke);
			ke = createKeyEvent(KeyEvent.KEY_RELEASED, KeyCode.C, "", "", false, true, false, false);
			processKeyEvent(ke);
		});

		webEngine = webView.getEngine();
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
		webPage = Accessor.getPageFor(webEngine);
		// webPage.setEditable(false);
		// webPage.setContextMenuEnabled(false);
		webView.setFocusTraversable(true);
		webView.setContextMenuEnabled(false);
		// initWeb(webEngine);
		// webView.getEngine().setUserStyleSheetLocation(getClass().getResource("/resources/common/css/webview.css").toExternalForm());
		// webPage.setBackgroundColor(255);
		// webView.setOpacity(0.92);
		// Field f;
		// try {
		// f = webEngine.getClass().getDeclaredField("page");
		// f.setAccessible(true);
		// } catch (NoSuchFieldException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SecurityException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		webView.setBlendMode(BlendMode.DARKEN);// 透明
		// webView.setBlendMode(BlendMode.LIGHTEN);
		webView.setOnDragDropped(a -> {

		});

		webView.setOnContextMenuRequested(e -> {
			if (isLoad) {
				// boolean has = hasSelection();
				// String selectText = (null == webPage) ? null :
				// webPage.getClientSelectedText();
				// if (has||null == selectText || selectText.length() == 0) {
				// if (!has) {
				// mi.setDisable(true);
				// } else {
				// mi.setDisable(false);
				// }
				contextMenu.show(webView.getScene().getWindow(), e.getScreenX(), e.getScreenY());
			}
		});

		webView.setOnMouseClicked(m -> {
			m.consume();
		});

		webPage.addLoadListenerClient(new LoadListenerClient() {

			@Override
			public void dispatchLoadEvent(long frame, int state, String url, String contentType, double progress, int errorCode) {
			}

			@Override
			public void dispatchResourceLoadEvent(long frame, int state, String url, String contentType, double progress, int errorCode) {
				// TODO 自动生成的方法存根

			}
		});

		WebViews.addHyperlinkListener(webView, new WebViewHyperlinkListener() {
            
            @Override
            public boolean hyperlinkUpdate(HyperlinkEvent event) {
                System.out.println(event.getURL());
               
                return true;
            }
        },javax.swing.event.HyperlinkEvent.EventType.ACTIVATED);
		initializeHtml();

	}

	private void iniEvent() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public KeyEvent createKeyEvent(
			EventType<? extends KeyEvent> eventType,
			KeyCode code, String character, String text,
			boolean shiftDown, boolean controlDown,
			boolean altDown, boolean metaDown) {
		return new KeyEvent((EventType<KeyEvent>) eventType, character, text, code, shiftDown, controlDown, altDown, metaDown);
	}

	public void processKeyEvent(KeyEvent ev) {
		if (webPage == null)
			return;
		ev.consume();
		String text = null;
		String keyIdentifier = null;
		int windowsVirtualKeyCode = 0;
		if (ev.getEventType() == KeyEvent.KEY_TYPED) {
			text = ev.getCharacter();
		} else {
			KeyCodeMap.Entry keyCodeEntry = KeyCodeMap.lookup(ev.getCode());
			keyIdentifier = keyCodeEntry.getKeyIdentifier();
			windowsVirtualKeyCode = keyCodeEntry.getWindowsVirtualKeyCode();
		}

		WCKeyEvent keyEvent = new WCKeyEvent(
				idMap.get(ev.getEventType()),
				text,
				keyIdentifier,
				windowsVirtualKeyCode,
				ev.isShiftDown(), ev.isControlDown(),
				ev.isAltDown(), ev.isMetaDown(), System.currentTimeMillis());
		if (webPage.dispatchKeyEvent(keyEvent)) {
			ev.consume();
		}
	}

	public WebView getWebView() {
		return webView;
	}

	public void setWebOnDragDropped(EventHandler<? super DragEvent> value) {
		webView.setOnDragDropped(value);
	}

	public StringBuilder createStyle(String fontName, int fontSize, String color, boolean bold, boolean underline, boolean italic) {
		StringBuilder style = new StringBuilder();
		style.append("style=\"");
		style.append(createStyleValue(fontName, fontSize, color, bold, underline, italic));
		style.append("\"");
		return style;
	}

	public StringBuilder createStyleValue(String fontName, int fontSize, String color, boolean bold, boolean underline, boolean italic) {
		StringBuilder style = new StringBuilder();
		if (null != fontName && !"".equals(fontName)) {
			style.append("font-family:").append(fontName).append(";");
		}
		style.append("font-size:").append(fontSize).append("px;");
		if (underline) {
			style.append("margin-top:0;text-decoration:underline;");
		} else {
			style.append("margin-top:0;");
		}
		if (italic) {
			style.append("font-style:italic;");
		}
		if (bold) {
			style.append("font-weight:bold;");
		}
		if (null != color && !"".equals(color)) {
			style.append("color:#");
			style.append(color);
			style.append(";");
		}
		return style;
	}

	public void initializeHtml() {
		StringBuilder html = new StringBuilder();
		html.append("<html>");
		html.append("	<head>");
		html.append("		<title>show</title>");
		html.append("		<meta charset=\"UTF-8\">");
		html.append("		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		html.append("		<script type=\"text/javascript\">");

		html.append(createBeforeJavaScript());
		html.append(createLastJavaScript());
		html.append(createCheckElementJavaScript());
		html.append(createBodyJavaScript());
		html.append(createReplaceImageJavaScript());

		html.append(createGetScrollPositionJavaScript());
		html.append(createScrollToBottomJavaScript());

		html.append(createHasSelectionJavaScript());

		html.append("		</script>");

		html.append("	</head>");
		html.append("	<body style=\"word-wrap:break-word;\" >");
		html.append("	</body>");
		html.append("</html>");
		// webPage.load(webPage.getMainFrame(), html.toString(), "text/html");
		// webEngine.load(this.getClass().getResource("/resources/chat/html/show/show.html").toString());
		// webEngine.getLoadWorker().

		File file = new File("Resources/Html/Chat/show/show.html");
		String absolutePath = file.getAbsolutePath();
		absolutePath = absolutePath.replace("\\", "/");
		if (absolutePath.startsWith("/")) {
			webEngine.load("file:" + absolutePath);
		}else {
			webEngine.load("file:/" + absolutePath);
		}
	}

	public String getHtml() {
		String html = webPage.getHtml(webPage.getMainFrame());
		return html;
	}

	public void insertLast(String text) {
		insertLast(text, "", 12, null, false, false, false);
	}

	public void insertLast(String text, String fontName, int fontSize, String color, boolean bold, boolean underline, boolean italic) {
		Document doc = webPage.getDocument(webPage.getMainFrame());
		if (doc instanceof HTMLDocument) {
			HTMLDocument htmlDocument = (HTMLDocument) doc;
			HTMLElement htmlDocumentElement = (HTMLElement) htmlDocument.getDocumentElement();
			HTMLElement htmlBodyElement = (HTMLElement) htmlDocumentElement.getElementsByTagName("body").item(0);

			Element element = htmlDocument.createElement("div");
			element.setTextContent(text);
			element.setAttribute("style", createStyleValue(fontName, fontSize, color, bold, underline, italic).toString());
			htmlBodyElement.appendChild(element);

		}
	}

	public void insertSelectionHtml(String html) {
		String js = createSelectionJavaScript(escapeJavaStyleString(html, true, true));
		executeScript(js);
	}

	public void insertBeforeHtml(String html) {
		String h = escapeJavaStyleString(html, true, true);
		StringBuilder js = new StringBuilder();

		js.append("insertBeforeHtml('");
		js.append(h);
		js.append("');");
		executeScript(js.toString());
	}
	public void insertBeforeShowHtml(String name, String head, String time, String orientation, String html) {
		String h = escapeJavaStyleString(html, true, true);
		StringBuilder js = new StringBuilder();

		js.append("insertBeforeShowHtml('");
		js.append(name);
		js.append("','");
		js.append(head);
		js.append("','");
		js.append(time);
		js.append("','");
		js.append(orientation);
		js.append("','");
		js.append(h);
		js.append("');");
		
		executeScript(js.toString());
	}
	
	public void insertLastHtml(String html) {
		String h = escapeJavaStyleString(html, true, true);
		StringBuilder js = new StringBuilder();
		// js.append(createLastJavaScript());

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

	public void insertLastShowHtml(String name, String head, String time, String orientation, String html) {
		String h = escapeJavaStyleString(html, true, true);
		StringBuilder js = new StringBuilder();
		// js.append(createLastJavaScript());

		js.append("function insertLastShow() {");

		js.append("    var position=getScrollPosition();");

		js.append("insertLastShowHtml('");
		js.append(name);
		js.append("','");
		js.append(head);
		js.append("','");
		js.append(time);
		js.append("','");
		js.append(orientation);
		js.append("','");
		js.append(h);
		js.append("');");

		js.append("    if(position==\"bottom\"){");
		js.append("        scrollToBottom();");
		js.append("    }");
		js.append("}");

		js.append("insertLastShow();");

		executeScript(js.toString());
	}

	public void setBodyHtml(String html) {
		String h = escapeJavaStyleString(html, true, true);
		StringBuilder js = new StringBuilder();
		// js.append(createLastJavaScript());
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

	public void replaceImage(String id, String src) {
		StringBuilder js = new StringBuilder();
		js.append("replaceImage('");
		js.append(id);
		js.append("','");
		js.append(src);
		js.append("');");
		executeScript(js.toString());
	}

	private void executeScript(String js) {
		if (isLoad) {
			webEngine.executeScript(js);
		} else {
			jsList.add(js);
		}
	}

	// private boolean hasSelection() {
	// boolean has = (boolean) webEngine.executeScript("hasSelection();");
	// return has;
	// }

	private StringBuilder createBeforeJavaScript() {
		StringBuilder js = new StringBuilder();
		js.append("function insertBeforeHtml(html) {");
		js.append("     var element = document.createElement('div');");
		js.append("     element.innerHTML = html;");
		js.append("    document.body.insertBefore(element, document.body.firstElementChild);");
		js.append(" }");
		return js;
	}

	private StringBuilder createLastJavaScript() {
		StringBuilder js = new StringBuilder();
		js.append("			function insertLastHtml(html) {");
		js.append("				var element = document.createElement('div');");
		js.append("				element.innerHTML = html;");
		js.append("				document.body.appendChild(element);");
		js.append("			}");
		return js;
	}

	private StringBuilder createBodyJavaScript() {
		StringBuilder js = new StringBuilder();
		js.append("			function setBodyHtml(html) {");
		js.append("				document.body.innerHTML = html;");
		js.append("			}");
		return js;
	}

	private StringBuilder createCheckElementJavaScript() {
		StringBuilder js = new StringBuilder();

		js.append("function checkElement() {");
		js.append("    var max = 500;");
		js.append("    var nodes = document.body.children;");
		js.append("    var length = nodes.length;");
		js.append("    if (length > max) {");
		js.append("        var size = length - max;");
		js.append("        for (var i = 0; i < size; i++) {");
		js.append("            document.body.removeChild(document.body.firstElementChild);");
		js.append("        }");
		js.append("   }");
		js.append("}");
		return js;
	}

	private String createSelectionJavaScript(String html) {
		StringBuilder js = new StringBuilder();
		js.append("function insertHtmlAtCursor(html) {\n");
		js.append("    var range, node;\n");
		js.append("    var hasSelection = window.getSelection;\n");
		js.append("    var selection = window.getSelection();\n");
		js.append("    if (hasSelection &&selection.rangeCount>0&& selection.getRangeAt)  {\n");
		js.append("        range = window.getSelection().getRangeAt(0);\n");
		js.append("        node = range.createContextualFragment(html);\n");
		js.append("        range.insertNode(node);\n");
		js.append("    } else if (document.selection && document.selection.createRange) {\n");
		js.append("        document.selection.createRange().pasteHTML(html);\n");
		js.append("    } else {\n");
		js.append("        var element = document.createElement('div');\n");
		js.append("        element.innerHTML = html;");
		js.append("        node = element.childNodes[0];");
		js.append("        document.body.appendChild(node);\n");
		js.append("    }");
		js.append("}");
		js.append("insertHtmlAtCursor('");
		js.append(html);
		js.append("');");
		String jsInsertHtml = js.toString();
		return jsInsertHtml;
	}

	private StringBuilder createReplaceImageJavaScript() {
		StringBuilder js = new StringBuilder();
		js.append("			function replaceImage(id,src) {");
		js.append("				var e=document.getElementById(id);");
		js.append("				if(e){");
		js.append("					e.src=src;");
		js.append("				}");
		js.append("			}");
		return js;
	}

	private StringBuilder createGetScrollPositionJavaScript() {
		StringBuilder js = new StringBuilder();
		js.append("			function getScrollPosition() {");
		js.append("			    var body=document.body;");
		js.append("			    var doc=document.documentElement;");

		js.append("			    var height = body.scrollHeight;");
		js.append("			    var top = body.scrollTop;");

		js.append("			    var clientHeight =body.clientHeight;");
		js.append("			    var position=\"\";");

		js.append("			    var a=(height - top);");
		js.append("			    var b=(clientHeight + 10);");

		js.append("			    if (a<b) {");
		js.append("			        position = \"bottom\";");
		js.append("			    } else if (top == 0) {");
		js.append("			        position = \"top\";");
		js.append("			    } else {");
		js.append("			        position = \"middle\";");
		js.append("			    }");
		js.append("			    return position;");
		js.append("			}");

		return js;
	}

	private StringBuilder createScrollToBottomJavaScript() {
		StringBuilder js = new StringBuilder();

		js.append("			function scrollToBottom() {");
		js.append("			    var body=document.body;");
		js.append("			    var height = body.scrollHeight;");
		js.append("			    body.scrollTop=height;");
		js.append("			}");

		return js;
	}

	private StringBuilder createHasSelectionJavaScript() {
		StringBuilder js = new StringBuilder();
		js.append("	function hasSelection(){");
		js.append("	    var selectionObject = null, rangeObject = null, selectedText = \"\", selectedHtml = \"\";");
		js.append("	    if(window.getSelection){");
		js.append("	        selectionObject = window.getSelection();");
		js.append("	        selectedText = selectionObject.toString();");
		js.append("	        rangeObject = selectionObject.getRangeAt(0);");
		js.append("	        var docFragment = rangeObject.cloneContents();");
		js.append("	        var tempDiv = document.createElement(\"div\");");
		js.append("	        tempDiv.appendChild(docFragment);");
		js.append("	        selectedHtml = tempDiv.innerHTML;");
		js.append("	    }else if(document.selection){");
		js.append("	        selectionObject = document.selection;");
		js.append("	        rangeObject = selectionObject.createRange();");
		js.append("	       selectedText = rangeObject.text;");
		js.append("	        selectedHtml = rangeObject.htmlText;");
		js.append("	   }");
		js.append("	    var has=false;");
		js.append("	    var isEmpty = selectedHtml == undefined || selectedHtml == null || selectedHtml == \"undefined\" || selectedHtml == \"null\" || selectedHtml == \"&nbsp;\";");
		js.append("	    has=!isEmpty;");
		js.append("	   return has;");
		js.append("	}");
		return js;
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
		// JSObject window = (JSObject) webEngine.executeScript("document");
		if(isLoad&&null!=member){
			window.setMember("oim", member);
		}
	}

	// private void initOIM(WebEngine webEngine) {
	// JSObject app = (JSObject) webEngine.executeScript("getApp();");
	// app.setMember("oim", oim);
	// }

	class HtmlData {
		String type;
		String html;
	}

	public void setJavaScriptInterface(Object member){
		this.member=member;
		if(isLoad){
			initWeb(webEngine);
		}
	}
	
	static final Map<Object, Integer> idMap = new HashMap<Object, Integer>();

	static {
		idMap.put(MouseButton.NONE, WCMouseEvent.NOBUTTON);
		idMap.put(MouseButton.PRIMARY, WCMouseEvent.BUTTON1);
		idMap.put(MouseButton.MIDDLE, WCMouseEvent.BUTTON2);
		idMap.put(MouseButton.SECONDARY, WCMouseEvent.BUTTON3);

		idMap.put(MouseEvent.MOUSE_PRESSED, WCMouseEvent.MOUSE_PRESSED);
		idMap.put(MouseEvent.MOUSE_RELEASED, WCMouseEvent.MOUSE_RELEASED);
		idMap.put(MouseEvent.MOUSE_MOVED, WCMouseEvent.MOUSE_MOVED);
		idMap.put(MouseEvent.MOUSE_DRAGGED, WCMouseEvent.MOUSE_DRAGGED);

		idMap.put(KeyEvent.KEY_PRESSED, WCKeyEvent.KEY_PRESSED);
		idMap.put(KeyEvent.KEY_RELEASED, WCKeyEvent.KEY_RELEASED);
		idMap.put(KeyEvent.KEY_TYPED, WCKeyEvent.KEY_TYPED);
	}
}
