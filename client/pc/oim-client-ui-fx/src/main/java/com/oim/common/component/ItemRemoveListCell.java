package com.oim.common.component;

import com.only.fx.common.action.ValueAction;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

/**
 * @author XiaHui
 * @date 2017-12-18 09:15:01
 */
public class ItemRemoveListCell<T> extends ListCell<T> {

	private final Label removeButton;
	private ValueAction<T> removeValueAction;
	private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<StringConverter<T>>(this, "converter");

	public ItemRemoveListCell(ValueAction<T> valueAction) {
		this(valueAction, new StringConverter<T>() {
			@Override
			public String toString(Object t) {
				return t == null ? null : t.toString();
			}

			@SuppressWarnings("unchecked")
			@Override
			public T fromString(String string) {
				return (T) string;
			}
		});
	}

	public ItemRemoveListCell(ValueAction<T> valueAction, final StringConverter<T> converter) {
		this.setRemoveValueAction(valueAction);
		this.setConverter(converter);
		removeButton = new Label("X");
		//removeButton.getStyleClass().clear();
		removeButton.setMinHeight(20);
		removeButton.setMinWidth(20);
		setAlignment(Pos.CENTER_LEFT);
		setContentDisplay(ContentDisplay.LEFT);
		setGraphic(removeButton);
		this.setOnMouseClicked(m->{
			System.out.println(111);
		});
		
		removeButton.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(444);
            }
        });
		removeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(444);
            }
        });
		removeButton.setOnMouseEntered(a->{
			System.out.println(777);
		});
		removeButton.setOnMouseClicked(m->{
			System.out.println(222);
		});
	}

	public final void setConverter(StringConverter<T> value) {
		converterProperty().set(value);
	}

	public final StringConverter<T> getConverter() {
		return converterProperty().get();
	}

	public final ObjectProperty<StringConverter<T>> converterProperty() {
		return converter;
	}

	

	public ValueAction<T> getRemoveValueAction() {
		return removeValueAction;
	}

	public void setRemoveValueAction(ValueAction<T> removeValueAction) {
		this.removeValueAction = removeValueAction;
	}

	@Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
//        	removeButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    if (null != removeValueAction) {
//                    	removeValueAction.value(getItem());;
//                    }
//                }
//            });
        	Button b=new Button("(x)");
        	b.setOnAction(a->{
        		System.out.println(888);
        	});
            StringConverter<T> c = getConverter();
            //setGraphic(b);
            setText(c != null ? c.toString(item) : (item == null ? "" : item.toString()));
        } else {
            //setGraphic(null);
            setText(null);
        }
    }
}
