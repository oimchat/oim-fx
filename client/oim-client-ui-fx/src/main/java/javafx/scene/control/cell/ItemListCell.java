package javafx.scene.control.cell;

import com.only.fx.common.action.ExecuteAction;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @author: XiaHui
 * @date: 2017年4月10日 下午5:34:59
 */
public class ItemListCell<T> extends ListCell<T> {

    public static <T> Callback<ListView<T>, ListCell<T>> forListView(ExecuteAction executeAction,
            final StringConverter<T> converter) {
        return list -> new ItemListCell<T>(executeAction, converter);
    }

    private final Button button;
    ExecuteAction executeAction;

    public ItemListCell(ExecuteAction executeAction) {
        this(executeAction, CellUtils.<T>defaultStringConverter());
    }

    public ItemListCell(ExecuteAction executeAction, final StringConverter<T> converter) {
        setConverter(converter);
        this.executeAction = executeAction;
        this.button = new Button();
        button.getStyleClass().clear();
        button.setMinHeight(20);
        button.setMinWidth(20);

        //Image image = ImageBox.getImageClassPath("/resources/common/imagse/close/1_cancel_button_hover.png");
        //button.setGraphic(new ImageView(image));
        
        button.setText("X");
        setAlignment(Pos.CENTER_LEFT);
        setContentDisplay(ContentDisplay.LEFT);
        setGraphic(null);
    }

    private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<StringConverter<T>>(this,
            "converter");

    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }

    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (null != executeAction) {
                        executeAction.execute(item);
                    }
                }
            });
            StringConverter<T> c = getConverter();
            setGraphic(button);
            setText(c != null ? c.toString(item) : (item == null ? "" : item.toString()));
        } else {
            setGraphic(null);
            setText(null);
        }
    }
}
