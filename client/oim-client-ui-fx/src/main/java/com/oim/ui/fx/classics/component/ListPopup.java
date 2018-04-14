package com.oim.ui.fx.classics.component;

import com.sun.javafx.event.EventHandlerManager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.stage.Window;
import javafx.util.StringConverter;

/**
 * @author: XiaHui
 * @date: 2017年3月23日 下午3:25:11
 */
public class ListPopup<T> extends PopupControl {

	private final static int TITLE_HEIGHT = 28;
	private final ObservableList<T> suggestions = FXCollections.observableArrayList();
	private StringConverter<T> converter;

	private IntegerProperty visibleRowCount = new SimpleIntegerProperty(this, "visibleRowCount", 10);

	@SuppressWarnings("serial")
	public static class SuggestionEvent<TE> extends Event {
		@SuppressWarnings("rawtypes")
		public static final EventType<SuggestionEvent> SUGGESTION = new EventType<>("SUGGESTION"); //$NON-NLS-1$

		private final TE suggestion;

		public SuggestionEvent(TE suggestion) {
			super(SUGGESTION);
			this.suggestion = suggestion;
		}

		public TE getSuggestion() {
			return suggestion;
		}
	}

	@SuppressWarnings("serial")
	public static class RemoveEvent<TE> extends Event {
		@SuppressWarnings("rawtypes")
		public static final EventType<RemoveEvent> REMOVE = new EventType<>("REMOVE"); //$NON-NLS-1$

		private final TE remove;

		public RemoveEvent(TE remove) {
			super(REMOVE);
			this.remove = remove;
		}

		public TE getRemove() {
			return remove;
		}
	}

	/***************************************************************************
	 * * Constructors * *
	 **************************************************************************/

	/**
	 * Creates a new AutoCompletePopup
	 */
	public ListPopup() {
		this.setAutoFix(true);
		this.setAutoHide(true);
		this.setHideOnEscape(true);
		getStyleClass().add(DEFAULT_STYLE_CLASS);
	}

	public ObservableList<T> getSuggestions() {
		return suggestions;
	}

	public void show(Node node) {

		if (node.getScene() == null || node.getScene().getWindow() == null)
			throw new IllegalStateException("Can not show popup. The node must be attached to a scene/window."); //$NON-NLS-1$

		if (isShowing()) {
			return;
		}

		Window parent = node.getScene().getWindow();
		this.show(parent, parent.getX() + node.localToScene(0, 0).getX() + node.getScene().getX(),
				parent.getY() + node.localToScene(0, 0).getY() + node.getScene().getY() + TITLE_HEIGHT);

	}

	/**
	 * Set the string converter used to turn a generic suggestion into a string
	 */
	public void setConverter(StringConverter<T> converter) {
		this.converter = converter;
	}

	/**
	 * Get the string converter used to turn a generic suggestion into a string
	 */
	public StringConverter<T> getConverter() {
		return converter;
	}

	public final void setVisibleRowCount(int value) {
		visibleRowCount.set(value);
	}

	public final int getVisibleRowCount() {
		return visibleRowCount.get();
	}

	public final IntegerProperty visibleRowCountProperty() {
		return visibleRowCount;
	}

	/***************************************************************************
	 * * Properties * *
	 **************************************************************************/

	private final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);

	public final ObjectProperty<EventHandler<SuggestionEvent<T>>> onSuggestionProperty() {
		return onSuggestion;
	}

	public final void setOnSuggestion(EventHandler<SuggestionEvent<T>> value) {
		onSuggestionProperty().set(value);
	}

	public final EventHandler<SuggestionEvent<T>> getOnSuggestion() {
		return onSuggestionProperty().get();
	}

	public final ObjectProperty<EventHandler<RemoveEvent<T>>> onRemoveProperty() {
		return onRemove;
	}

	public final void setOnRemove(EventHandler<RemoveEvent<T>> value) {
		onRemoveProperty().set(value);
	}

	public final EventHandler<RemoveEvent<T>> getOnRemove() {
		return onRemoveProperty().get();
	}

	private ObjectProperty<EventHandler<SuggestionEvent<T>>> onSuggestion = new ObjectPropertyBase<EventHandler<SuggestionEvent<T>>>() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void invalidated() {
			eventHandlerManager.setEventHandler(SuggestionEvent.SUGGESTION,
					(EventHandler<SuggestionEvent>) (Object) get());
		}

		@Override
		public Object getBean() {
			return ListPopup.this;
		}

		@Override
		public String getName() {
			return "onSuggestion"; //$NON-NLS-1$
		}
	};

	private ObjectProperty<EventHandler<RemoveEvent<T>>> onRemove = new ObjectPropertyBase<EventHandler<RemoveEvent<T>>>() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void invalidated() {
			eventHandlerManager.setEventHandler(RemoveEvent.REMOVE, (EventHandler<RemoveEvent>) (Object) get());
		}

		@Override
		public Object getBean() {
			return ListPopup.this;
		}

		@Override
		public String getName() {
			return "onRemove";
		}
	};

	@Override
	public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
		return super.buildEventDispatchChain(tail).append(eventHandlerManager);
	}

	public static final String DEFAULT_STYLE_CLASS = "auto-complete-popup"; //$NON-NLS-1$

	@Override
	protected Skin<?> createDefaultSkin() {
		return new ListPopupSkin<>(this);
	}

}
