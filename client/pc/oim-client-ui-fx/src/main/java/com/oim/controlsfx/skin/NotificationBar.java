package com.oim.controlsfx.skin;
/**
 * @author: XiaHui
 * @date: 2017年10月13日 下午12:00:51
 */

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionUtils;

public abstract class NotificationBar extends Region {

    private static final double MIN_HEIGHT = 40;

    final Label label;
    Label title;
    ButtonBar actionsBar;
    Button closeBtn;

    private final GridPane pane;
    
    public DoubleProperty transition = new SimpleDoubleProperty() {
        @Override protected void invalidated() {
            requestContainerLayout();
        }
    };
    
    
    public void requestContainerLayout() {
        layoutChildren();
    }
    
    public String getTitle() {
        return ""; //$NON-NLS-1$
    }
    
    public boolean isCloseButtonVisible() {
        return true;
    }
    
    public abstract String getText();
    public abstract String getTextColor();
    public abstract Node getGraphic();
    public abstract ObservableList<Action> getActions();
    public abstract void hide();
    public abstract boolean isShowing();
    public abstract boolean isShowFromTop();
    
    public abstract double getContainerHeight();
    public abstract void relocateInParent(double x, double y);

	public NotificationBar() {
        getStyleClass().add("notification-bar"); //$NON-NLS-1$
        
        setVisible(isShowing());
        
        pane = new GridPane();
        pane.getStyleClass().add("pane"); //$NON-NLS-1$
        pane.setAlignment(Pos.BASELINE_LEFT);
        getChildren().setAll(pane);
        
        // initialise title area, if one is set
        String titleStr = getTitle();
        if (titleStr != null && ! titleStr.isEmpty()) {
            title = new Label();
            title.getStyleClass().add("title"); //$NON-NLS-1$
            title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            GridPane.setHgrow(title, Priority.ALWAYS);

            title.setText(titleStr);
            title.opacityProperty().bind(transition);
        }
        
        // initialise label area
        label = new Label();
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setVgrow(label, Priority.ALWAYS);
        GridPane.setHgrow(label, Priority.ALWAYS);

        label.setText(getText());
        label.setGraphic(getGraphic());
        label.opacityProperty().bind(transition);

        String textColor = getTextColor();
        if(null!=textColor){
        	textColor=textColor.replace("#", "");
        	label.setStyle("-fx-text-fill:#"+textColor);
        }
        // initialise actions area
        getActions().addListener(new InvalidationListener() {
            @Override public void invalidated(Observable arg0) {
                updatePane();
            }
        });

        // initialise close button area
        closeBtn = new Button();
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
                hide();
            }
        });
        closeBtn.getStyleClass().setAll("close-button"); //$NON-NLS-1$
        StackPane graphic = new StackPane();
        graphic.getStyleClass().setAll("graphic"); //$NON-NLS-1$
        closeBtn.setGraphic(graphic);
        closeBtn.setMinSize(17, 17);
        closeBtn.setPrefSize(17, 17);
        closeBtn.setFocusTraversable(false);
        closeBtn.opacityProperty().bind(transition);
        GridPane.setMargin(closeBtn, new Insets(0, 0, 0, 8));
        
        // position the close button in the best place, depending on the height
        double minHeight = minHeight(-1);
        GridPane.setValignment(closeBtn, minHeight == MIN_HEIGHT ? VPos.CENTER : VPos.TOP);
        
        // put it all together
        updatePane();
    }

    void updatePane() {
        actionsBar = ActionUtils.createButtonBar(getActions());
        actionsBar.opacityProperty().bind(transition);
        GridPane.setHgrow(actionsBar, Priority.SOMETIMES);
        pane.getChildren().clear();
        
        int row = 0;
        
        if (title != null) {
            pane.add(title, 0, row++);
        }
        
        pane.add(label, 0, row);
        pane.add(actionsBar, 1, row);
        
        if (isCloseButtonVisible()) {
            pane.add(closeBtn, 2, 0, 1, row+1);
        }
    }

    @Override protected void layoutChildren() {
        final double w = getWidth();
        final double h = computePrefHeight(-1);
        
        final double notificationBarHeight = prefHeight(w);
        final double notificationMinHeight = minHeight(w);
        
        if (isShowFromTop()) {
            // place at top of area
            pane.resize(w, h);
            relocateInParent(0, (transition.get() - 1) * notificationMinHeight);
        } else {
            // place at bottom of area
            pane.resize(w, notificationBarHeight);
            relocateInParent(0, getContainerHeight() - notificationBarHeight);
        }
    }

    @Override protected double computeMinHeight(double width) {
        return Math.max(super.computePrefHeight(width), MIN_HEIGHT);
    }

    @Override protected double computePrefHeight(double width) {
        return Math.max(pane.prefHeight(width), minHeight(width)) * transition.get();
    }

    public void doShow() {
        transitionStartValue = 0;
        doAnimationTransition();
    }

    public void doHide() {
        transitionStartValue = 1;
        doAnimationTransition();
    }



    // --- animation timeline code
    private final Duration TRANSITION_DURATION = new Duration(350.0);
    private Timeline timeline;
    private double transitionStartValue;
    private void doAnimationTransition() {
        Duration duration;

        if (timeline != null && (timeline.getStatus() != Status.STOPPED)) {
            duration = timeline.getCurrentTime();

            // fix for #70 - the notification pane freezes up as it has zero
            // duration to expand / contract
            duration = duration == Duration.ZERO ? TRANSITION_DURATION : duration;
            transitionStartValue = transition.get();
            // --- end of fix

            timeline.stop();
        } else {
            duration = TRANSITION_DURATION;
        }

        timeline = new Timeline();
        timeline.setCycleCount(1);

        KeyFrame k1, k2;

        if (isShowing()) {
            k1 = new KeyFrame(
                    Duration.ZERO,
                    new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent event) {
                            // start expand
                            setCache(true);
                            setVisible(true);

                            pane.fireEvent(new Event(NotificationPane.ON_SHOWING));
                        }
                    },
                    new KeyValue(transition, transitionStartValue)
                    );

            k2 = new KeyFrame(
                    duration,
                    new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent event) {
                            // end expand
                            pane.setCache(false);

                            pane.fireEvent(new Event(NotificationPane.ON_SHOWN));
                        }
                    },
                    new KeyValue(transition, 1, Interpolator.EASE_OUT)

                    );
        } else {
            k1 = new KeyFrame(
                    Duration.ZERO,
                    new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent event) {
                            // Start collapse
                            pane.setCache(true);

                            pane.fireEvent(new Event(NotificationPane.ON_HIDING));
                        }
                    },
                    new KeyValue(transition, transitionStartValue)
                    );

            k2 = new KeyFrame(
                    duration,
                    new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent event) {
                            // end collapse
                            setCache(false);
                            setVisible(false);

                            pane.fireEvent(new Event(NotificationPane.ON_HIDDEN));
                        }
                    },
                    new KeyValue(transition, 0, Interpolator.EASE_IN)
                    );
        }

        timeline.getKeyFrames().setAll(k1, k2);
        timeline.play();
    }
}

