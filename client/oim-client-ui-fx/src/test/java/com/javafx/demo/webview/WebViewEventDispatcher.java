package com.javafx.demo.webview;

import java.util.HashMap;
import java.util.LinkedList;
/**
 * @author XiaHui
 * @date 2017年6月10日 下午2:35:35
 */
import java.util.List;
import java.util.Map;

import com.sun.javafx.scene.NodeEventDispatcher;
import com.sun.javafx.webkit.Accessor;
import com.sun.javafx.webkit.InputMethodClientImpl;
import com.sun.javafx.webkit.KeyCodeMap;
import com.sun.webkit.WebPage;
import com.sun.webkit.event.WCInputMethodEvent;
import com.sun.webkit.event.WCKeyEvent;
import com.sun.webkit.event.WCMouseEvent;
import com.sun.webkit.event.WCMouseWheelEvent;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewEventDispatcher extends Application {
	WebView webView = new WebView();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new WebViewPane();
		primaryStage.setScene(new Scene(root, 1024, 768));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static final Map<Object, Integer> idMap = new HashMap<Object, Integer>();
	/**
	 * Create a resizable WebView pane
	 */

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
	WebPage page;
	NodeEventDispatcher internalEventDispatcher = new NodeEventDispatcher(webView) {

	};
	public class WebViewPane extends Pane {

		public WebViewPane() {
			VBox.setVgrow(this, Priority.ALWAYS);
			setMaxWidth(Double.MAX_VALUE);
			setMaxHeight(Double.MAX_VALUE);

			TextField locationField = new TextField("http://www.baidu.com");
			Button goButton = new Button("Go");

			WebEngine webEngine = webView.getEngine();
			page = Accessor.getPageFor(webEngine);
			page.setJavaScriptEnabled(true);

			webView.setMinSize(500, 400);
			webView.setPrefSize(500, 400);

			webEngine.load("http://www.baidu.com");

			page.setEditable(true);

			// EventDispatcher eventDispatcher=new EventDispatcher() {
			//
			// @Override
			// public Event dispatchEvent(Event event, EventDispatchChain tail)
			// {
			// //tail.dispatchEvent(event);
			// if(event.getEventType()==MouseEvent.ANY) {
			//
			// }
			// return event;
			// }
			//
			// };
			registerEventHandlers();
			

			webView.setEventDispatcher(internalEventDispatcher);
			
			System.out.println(webView.getEventDispatcher());
			// webView.addEventHandler(eventType, eventHandler);
			// webView.buildEventDispatchChain(tail)

			EventHandler<ActionEvent> goAction = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					webEngine.load(locationField.getText().startsWith("http://") ? locationField.getText() : "http://" + locationField.getText());
				}
			};

			goButton.setDefaultButton(true);
			goButton.setOnAction(goAction);

			locationField.setMaxHeight(Double.MAX_VALUE);
			locationField.setOnAction(goAction);

			webEngine.locationProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					locationField.setText(newValue);
				}
			});

			GridPane grid = new GridPane();
			grid.setVgap(5);
			grid.setHgap(5);
			GridPane.setConstraints(locationField, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES);
			GridPane.setConstraints(goButton, 1, 0);
			GridPane.setConstraints(webView, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
			grid.getColumnConstraints().addAll(
					new ColumnConstraints(100, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true),
					new ColumnConstraints(40, 40, 40, Priority.NEVER, HPos.CENTER, true));
			grid.getChildren().addAll(locationField, goButton, webView);
			getChildren().add(grid);
		}

		@Override
		protected void layoutChildren() {
			List<Node> managed = getManagedChildren();
			double width = getWidth();
			double height = getHeight();
			double top = getInsets().getTop();
			double right = getInsets().getRight();
			double left = getInsets().getLeft();
			double bottom = getInsets().getBottom();
			for (int i = 0; i < managed.size(); i++) {
				Node child = managed.get(i);
				layoutInArea(child, left, top, width - left - right, height - top - bottom, 0, Insets.EMPTY, true, true, HPos.CENTER, VPos.CENTER);
			}
		}
	}

	private void registerEventHandlers() {
		addEventHandler(KeyEvent.ANY,
				event -> {
					processKeyEvent(event);
				});
		addEventHandler(MouseEvent.ANY,
				event -> {
					processMouseEvent(event);
					if (event.isDragDetect() && !page.isDragConfirmed()) {
						// postpone drag recognition:
						// Webkit cannot resolve here is it a drag
						// or selection.
						event.setDragDetect(false);
					}
				});
		addEventHandler(ScrollEvent.SCROLL,
				event -> {
					processScrollEvent(event);
				});
		webView.setOnInputMethodTextChanged(
				event -> {
					processInputMethodEvent(event);
				});

		// Drop target implementation:
		EventHandler<DragEvent> destHandler = event -> {
			try {
				Dragboard db = event.getDragboard();
				LinkedList<String> mimes = new LinkedList<String>();
				LinkedList<String> values = new LinkedList<String>();
				for (DataFormat df : db.getContentTypes()) {
					// TODO: extend to non-string serialized values.
					// Please, look at the native code.
					Object content = db.getContent(df);
					if (content != null) {
						for (String mime : df.getIdentifiers()) {
							mimes.add(mime);
							values.add(content.toString());
						}
					}
				}
				if (!mimes.isEmpty()) {
					int wkDndEventType = getWKDndEventType(event.getEventType());
					int wkDndAction = page.dispatchDragOperation(
							wkDndEventType,
							mimes.toArray(new String[0]), values.toArray(new String[0]),
							(int) event.getX(), (int) event.getY(),
							(int) event.getScreenX(), (int) event.getScreenY(),
							getWKDndAction(db.getTransferModes().toArray(new TransferMode[0])));

					// we cannot accept nothing on drop (we skip FX exception)
					if (!(wkDndEventType == WebPage.DND_DST_DROP && wkDndAction == WK_DND_ACTION_NONE)) {
						event.acceptTransferModes(getFXDndAction(wkDndAction));
					}
					event.consume();
				}
			} catch (SecurityException ex) {
				// Just ignore the exception
				// ex.printStackTrace();
			}
		};
		webView.setOnDragEntered(destHandler);
		webView.setOnDragExited(destHandler);
		webView.setOnDragOver(destHandler);
		webView.setOnDragDropped(destHandler);

		// Drag source implementation:
		webView.setOnDragDetected(event -> {
			if (page.isDragConfirmed()) {
				page.confirmStartDrag();
				event.consume();
			}
		});
		webView.setOnDragDone(event -> {
			page.dispatchDragOperation(
					WebPage.DND_SRC_DROP,
					null, null,
					(int) event.getX(), (int) event.getY(),
					(int) event.getScreenX(), (int) event.getScreenY(),
					getWKDndAction(event.getAcceptedTransferMode()));
			event.consume();
		});

		webView.setInputMethodRequests(getInputMethodClient());
	}

	private volatile InputMethodClientImpl imClient;

	private InputMethodClientImpl getInputMethodClient() {
		if (imClient == null) {
			synchronized (webView) {
				if (imClient == null) {
					imClient = new InputMethodClientImpl(webView, page);
				}
			}
		}
		return imClient;
	}

	private static int getWKDndEventType(EventType<?> et) {
		int commandId = 0;
		if (et == DragEvent.DRAG_ENTERED)
			commandId = WebPage.DND_DST_ENTER;
		else if (et == DragEvent.DRAG_EXITED)
			commandId = WebPage.DND_DST_EXIT;
		else if (et == DragEvent.DRAG_OVER)
			commandId = WebPage.DND_DST_OVER;
		else if (et == DragEvent.DRAG_DROPPED)
			commandId = WebPage.DND_DST_DROP;
		return commandId;
	}

	private static TransferMode[] getFXDndAction(int wkDndAction) {
		LinkedList<TransferMode> tms = new LinkedList<TransferMode>();
		if ((wkDndAction & WK_DND_ACTION_COPY) != 0)
			tms.add(TransferMode.COPY);
		if ((wkDndAction & WK_DND_ACTION_MOVE) != 0)
			tms.add(TransferMode.MOVE);
		if ((wkDndAction & WK_DND_ACTION_LINK) != 0)
			tms.add(TransferMode.LINK);
		return tms.toArray(new TransferMode[0]);
	}

	private static final int WK_DND_ACTION_NONE = 0x0;
	private static final int WK_DND_ACTION_COPY = 0x1;
	private static final int WK_DND_ACTION_MOVE = 0x2;
	private static final int WK_DND_ACTION_LINK = 0x40000000;

	private static int getWKDndAction(TransferMode... tms) {
		int dndActionId = WK_DND_ACTION_NONE;
		for (TransferMode tm : tms) {
			if (tm == TransferMode.COPY)
				dndActionId |= WK_DND_ACTION_COPY;
			else if (tm == TransferMode.MOVE)
				dndActionId |= WK_DND_ACTION_MOVE;
			else if (tm == TransferMode.LINK)
				dndActionId |= WK_DND_ACTION_LINK;
		}
		return dndActionId;
	}
	
	 private void processScrollEvent(ScrollEvent ev) {
	        if (page == null) {
	            return;
	        }
	        double dx = - ev.getDeltaX() * webView.getFontScale() * webView.getScaleX();
	        double dy = - ev.getDeltaY() * webView.getFontScale() * webView.getScaleY();
	        WCMouseWheelEvent wheelEvent =
	                new WCMouseWheelEvent((int)ev.getX(), (int)ev.getY(),
	                    (int)ev.getScreenX(), (int)ev.getScreenY(),
	                    System.currentTimeMillis(),
	                    ev.isShiftDown(), ev.isControlDown(), ev.isAltDown(),
	                    ev.isMetaDown(), (float)dx, (float)dy);
	        page.dispatchMouseWheelEvent(wheelEvent);
	        ev.consume();
	    }
	 
	 private void processInputMethodEvent(InputMethodEvent ie) {
	        if (page == null) {
	            return;
	        }

	        if (!getInputMethodClient().getInputMethodState()) {
	            ie.consume();
	            return;
	        }

	        WCInputMethodEvent imEvent = InputMethodClientImpl.convertToWCInputMethodEvent(ie);
	        if (page.dispatchInputMethodEvent(imEvent)) {
	            ie.consume();
	            return;
	        }
	    }
	 
	 private void processKeyEvent(KeyEvent ev) {
	        if (page == null) return;

	        String text = null;
	        String keyIdentifier = null;
	        int windowsVirtualKeyCode = 0;
	        if(ev.getEventType() == KeyEvent.KEY_TYPED) {
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
	        if (page.dispatchKeyEvent(keyEvent)) {
	            ev.consume();
	        }
	    }
	 
	 private void processMouseEvent(MouseEvent ev) {
	        if (page == null) {
	            return;
	        }

	        // RT-24511
	        EventType<? extends MouseEvent> type = ev.getEventType();
	        double x = ev.getX();
	        double y = ev.getY();
	        double screenX = ev.getScreenX();
	        double screenY = ev.getScreenY();
	        if (type == MouseEvent.MOUSE_EXITED) {
	            type = MouseEvent.MOUSE_MOVED;
	            x = Short.MIN_VALUE;
	            y = Short.MIN_VALUE;
	            Point2D screenPoint = webView.localToScreen(x, y);
	            if (screenPoint == null) {
	                return;
	            }
	            screenX = screenPoint.getX();
	            screenY = screenPoint.getY();
	        }

	        final Integer id = idMap.get(type);
	        if (id == null) {
	            // not supported by webkit
	            return;
	        }
	        WCMouseEvent mouseEvent =
	                new WCMouseEvent(id, idMap.get(ev.getButton()),
	                    ev.getClickCount(), (int) x, (int) y,
	                    (int) screenX, (int) screenY,
	                    System.currentTimeMillis(),
	                    ev.isShiftDown(), ev.isControlDown(), ev.isAltDown(),
	                    ev.isMetaDown(), ev.isPopupTrigger());
	        page.dispatchMouseEvent(mouseEvent);
	        ev.consume();
	    }
	 
	 public final <T extends Event> void addEventHandler(
	            final EventType<T> eventType,
	            final EventHandler<? super T> eventHandler) {
		 internalEventDispatcher.getEventHandlerManager()
	                                    .addEventHandler(eventType, eventHandler);
	    }
}