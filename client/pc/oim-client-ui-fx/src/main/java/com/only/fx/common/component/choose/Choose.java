package com.only.fx.common.component.choose;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;

/**
 * @author XiaHui
 * @date 2017-12-12 10:16:35
 */
public interface Choose {

	void setChooseGroup(ChooseGroup chooseGroup);
	
	ChooseGroup getChooseGroup();

	ObjectProperty<ChooseGroup> chooseGroupProperty();
	
	boolean isSelected();

	void setSelected(boolean selected);
	
	BooleanProperty selectedProperty();
}
