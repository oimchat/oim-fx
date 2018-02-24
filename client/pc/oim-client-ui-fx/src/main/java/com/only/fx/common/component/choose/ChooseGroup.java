package com.only.fx.common.component.choose;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * @author: XiaHui
 * @date: 2017-12-18 10:54:42
 */
public class ChooseGroup {

	private final ReadOnlyObjectWrapper<Choose> selectedChoose = new ReadOnlyObjectWrapper<Choose>() {

		@Override
		public void set(final Choose newSelectedChoose) {
			if (isBound()) {
				throw new java.lang.RuntimeException("A bound value cannot be set.");
			}
			final Choose old = get();
			if (old == newSelectedChoose) {
				return;
			}
			boolean setSelected = setSelected(newSelectedChoose, true);
			boolean chooseGroupMark = (newSelectedChoose != null && newSelectedChoose.getChooseGroup() == ChooseGroup.this);
			if (setSelected || chooseGroupMark || (newSelectedChoose == null)) {
				if (old == null || old.getChooseGroup() == ChooseGroup.this || !old.isSelected()) {
					setSelected(old, false);
				}
				super.set(newSelectedChoose);
			}
		}
	};

	private boolean setSelected(Choose choose, boolean selected) {
		boolean mark = choose != null && choose.getChooseGroup() == this && !choose.selectedProperty().isBound();
		if (mark) {
			choose.setSelected(selected);
			return true;
		}
		return false;
	}

	public final ReadOnlyObjectProperty<Choose> selectedChooseProperty() {
		return selectedChoose.getReadOnlyProperty();
	}

	public final Choose getSelectedChoose() {
		return selectedChoose.get();
	}

	public final void selectedChoose(Choose value) {
		selectedChoose.set(value);
	}

	final void clearSelectedChoose() {
		if (!selectedChoose.getValue().isSelected()) {
			// for (Toggle toggle: getToggles()) {
			// if (toggle.isSelected()) {
			// return;
			// }
			// }
		}
		selectedChoose.set(null);
	}
}
