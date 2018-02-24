package com.only.fx.common.component.choose;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WritableValue;
import javafx.css.PseudoClass;
import javafx.css.StyleableProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * @author: XiaHui
 * @date: 2017-12-18 11:32:54
 */
public class ChoosePane extends StackPane implements Choose {
	private static final String DEFAULT_STYLE_CLASS = "choose-pane";
	private static final PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected");

	private final Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	public ChoosePane() {
		initialize();
	}

	public ChoosePane(String text) {
		initialize();
	}

	private void initialize() {
		getStyleClass().setAll(DEFAULT_STYLE_CLASS);
		setAccessibleRole(AccessibleRole.TOGGLE_BUTTON);
		// alignment is styleable through css. Calling setAlignment
		// makes it look to css like the user set the value and css will not
		// override. Initializing alignment by calling set on the
		// CssMetaData ensures that css will be able to override the value.
		((StyleableProperty<Pos>) (WritableValue<Pos>) alignmentProperty()).applyStyle(null, Pos.CENTER);

		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				doSelected();
			}
		});
	}

	private void doSelected() {
		ChooseGroup chooseGroup = getChooseGroup();
		if (null != chooseGroup) {
			chooseGroup.selectedChoose(this);
		}
	}

	private ObjectProperty<ChooseGroup> chooseGroup;

	@Override
	public final void setChooseGroup(ChooseGroup chooseGroup) {
		chooseGroupProperty().set(chooseGroup);
	}

	@Override
	public final ChooseGroup getChooseGroup() {
		return chooseGroup == null ? null : chooseGroup.get();
	}

	@Override
	public ObjectProperty<ChooseGroup> chooseGroupProperty() {
		if (chooseGroup == null) {
			chooseGroup = new ObjectPropertyBase<ChooseGroup>() {
				private ChooseGroup old;
				@SuppressWarnings("deprecation")
				private ChangeListener<Choose> listener = (o, oV, nV) -> getImpl_traversalEngine().setOverriddenFocusTraversability(nV != null ? isSelected() : null);

				@SuppressWarnings("deprecation")
				@Override
				protected void invalidated() {
					final ChooseGroup tg = get();
					if (tg == null) {
						old.selectedChooseProperty().removeListener(listener);
						setImpl_traversalEngine(null);
					}
					old = tg;
				}

				@Override
				public Object getBean() {
					return ChoosePane.this;
				}

				@Override
				public String getName() {
					return "toggleGroup";
				}
			};
		}
		return chooseGroup;
	}

	private BooleanProperty selected;

	@Override
	public final void setSelected(boolean value) {
		selectedProperty().set(value);
	}

	@Override
	public final boolean isSelected() {
		return selected == null ? false : selected.get();
	}

	@Override
	public final BooleanProperty selectedProperty() {
		if (selected == null) {
			selected = new BooleanPropertyBase() {
				@Override
				protected void invalidated() {
					final boolean selected = get();
					final ChooseGroup cg = getChooseGroup();
					// Note: these changes need to be done before
					// selectToggle/clearSelectedToggle since
					// those operations change properties and can execute user
					// code, possibly modifying selected property again
					pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, selected);
					notifyAccessibleAttributeChanged(AccessibleAttribute.SELECTED);
					if (cg != null) {
						if (selected) {
							cg.selectedChoose(ChoosePane.this);
						} else if (cg.getSelectedChoose() == ChoosePane.this) {
							cg.clearSelectedChoose();
						}
					}
				}

				@Override
				public Object getBean() {
					return ChoosePane.this;
				}

				@Override
				public String getName() {
					return "selected";
				}
			};
		}
		return selected;
	}

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}
}
