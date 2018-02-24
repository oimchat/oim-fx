package com.only.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
/**
 * 
 * @date 2013年12月28日 上午11:04:12
 * version 0.0.1
 */
public class LineLayout implements LayoutManager2, Serializable {

    private static final long serialVersionUID = -2870834002278815219L;
    public static final String START = "Start";
    public static final String END = "End";
    public static final String MIDDLE = "Middle";
    public static final String START_FILL = "StartFill";
    public static final String END_FILL = "EedFill";
    public static final String MIDDLE_FILL = "MiddleFill";
    public static final int HORIZONTAL = SwingConstants.HORIZONTAL;
    public static final int VERTICAL = SwingConstants.VERTICAL;
    public static final int LEADING = SwingConstants.LEADING;
    public static final int TRAILING = SwingConstants.TRAILING;
    public static final int CENTER = SwingConstants.CENTER;
    private int orientation;
    private int gap;
    private int leftGap;
    private int rightGap;
    private int topGap;
    private int bottomGap;
    private int alignment;
    private int position;
    private Component middleComponent;
    private List<Component> startComponents;
    private List<Component> endComponents;
    private List<Component> fillComponents;

    public LineLayout() {
        this(HORIZONTAL);
    }

    public LineLayout(int orientation) {
        this(5, CENTER, LEADING, orientation);
    }

    public LineLayout(int gap, int alignment, int position, int orientation) {
        this(gap, gap, gap, gap, gap, alignment, position, orientation);
    }

    public LineLayout(int gap, int leftGap, int rightGap, int topGap, int bottomGap, int alignment, int position, int orientation) {
        this.gap = gap;
        this.leftGap = leftGap;
        this.rightGap = rightGap;
        this.topGap = topGap;
        this.bottomGap = bottomGap;
        this.alignment = alignment;
        this.position = position;
        this.orientation = orientation;
        this.startComponents = new ArrayList<Component>();
        this.endComponents = new ArrayList<Component>();
        this.fillComponents = new ArrayList<Component>();
    }

    public void addLayoutComponent(Component comp, Object constraints) {
        synchronized (comp.getTreeLock()) {
            if ((constraints == null) || (constraints instanceof String)) {
                addLayoutComponent((String) constraints, comp);
            } else {
                throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
            }
        }
    }

    @Deprecated
    @Override
    public void addLayoutComponent(String name, Component comp) {
        synchronized (comp.getTreeLock()) {
            if ((name == null || START.equals(name)) && !startComponents.contains(comp)) {
                startComponents.add(comp);
            } else if (START_FILL.equals(name) && !startComponents.contains(comp)) {
                startComponents.add(comp);
                fillComponents.add(comp);
            } else if (END.equals(name) && !endComponents.contains(comp)) {
                endComponents.add(comp);
            } else if (END_FILL.equals(name) && !endComponents.contains(comp)) {
                endComponents.add(comp);
                fillComponents.add(comp);
            } else if (MIDDLE.equals(name) && middleComponent != comp) {
                middleComponent = comp;
            } else if (MIDDLE_FILL.equals(name) && middleComponent != comp) {
                middleComponent = comp;
                fillComponents.add(comp);
            }
        }
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        synchronized (comp.getTreeLock()) {
            if (middleComponent == comp) {
                middleComponent = null;
            } else {
                startComponents.remove(comp);
                endComponents.remove(comp);
            }

            fillComponents.remove(comp);
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            int width = 0;
            int height = 0;
            int count = 0;
            Dimension size;
            Insets insets = target.getInsets();

            for (Component component : target.getComponents()) {
                if (component.isVisible()) {
                    count++;
                    size = component.getPreferredSize();

                    if (orientation == VERTICAL) {
                        height += size.height;

                        if (size.width > width) {
                            width = size.width;
                        }
                    } else {
                        width += size.width;

                        if (size.height > height) {
                            height = size.height;
                        }
                    }
                }
            }

            if (orientation == VERTICAL) {
                width += leftGap + rightGap + insets.left + insets.right;
                height += topGap + bottomGap + insets.top + insets.bottom + (count > 1 ? (count - 1) * gap : 0);
            } else {
                height += topGap + bottomGap + insets.top + insets.bottom;
                width += leftGap + rightGap + insets.left + insets.right + (count > 1 ? (count - 1) * gap : 0);
            }

            return new Dimension(width, height);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            int width = 0;
            int height = 0;
            int count = 0;
            Dimension size;
            Insets insets = target.getInsets();

            for (Component component : target.getComponents()) {
                if (component.isVisible()) {
                    count++;
                    size = component.getMinimumSize();

                    if (orientation == VERTICAL) {
                        height += size.height;

                        if (size.width > width) {
                            width = size.width;
                        }
                    } else {
                        width += size.width;

                        if (size.height > height) {
                            height = size.height;
                        }
                    }
                }
            }

            if (orientation == VERTICAL) {
                width += leftGap + rightGap + insets.left + insets.right;
                height += topGap + bottomGap + insets.top + insets.bottom + (count > 1 ? (count - 1) * gap : 0);
            } else {
                height += topGap + bottomGap + insets.top + insets.bottom;
                width += leftGap + rightGap + insets.left + insets.right + (count > 1 ? (count - 1) * gap : 0);
            }

            return new Dimension(width, height);
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public void layoutContainer(Container target) {
        if (orientation == VERTICAL) {
            layoutContainerV(target);
        } else {
            layoutContainerH(target);
        }
    }

    private void layoutContainerV(Container target) {
        int maxWidth = 0;
        Dimension size;
        int width;
        Insets insets = target.getInsets();

        for (Component component : target.getComponents()) {
            if (component.isVisible()) {
                size = component.getPreferredSize();

                if (fillComponents.contains(component)) {
                    width = target.getWidth() - insets.left - insets.right - leftGap - rightGap;
                } else {
                    width = size.width;

                    if (width > maxWidth) {
                        maxWidth = width;
                    }
                }

                component.setSize(width, size.height);
            }
        }

        moveComponentsV(target, maxWidth);
    }

    private void layoutContainerH(Container target) {
        int maxHeight = 0;
        Dimension size;
        int height;
        Insets insets = target.getInsets();

        for (Component component : target.getComponents()) {
            if (component.isVisible()) {
                size = component.getPreferredSize();

                if (fillComponents.contains(component)) {
                    height = target.getHeight() - insets.top - insets.bottom - topGap - bottomGap;
                } else {
                    height = size.height;

                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                }

                component.setSize(size.width, height);
            }
        }

        moveComponentsH(target, maxHeight);
    }

    private void moveComponentsV(Container target, int maxWidth) {
        synchronized (target.getTreeLock()) {
            Insets insets = target.getInsets();
            int topY = insets.top + topGap;
            int bottomY = target.getHeight() - insets.bottom - bottomGap;

            for (Component component : startComponents) {
                if (component.isVisible()) {
                    component.setLocation(getX(component, maxWidth, insets, target.getWidth()), topY);
                    topY += component.getHeight() + gap;
                }
            }

            Component component;

            for (int i = endComponents.size() - 1; i >= 0; i--) {
                component = endComponents.get(i);

                if (component.isVisible()) {
                    bottomY -= component.getHeight();
                    component.setLocation(getX(component, maxWidth, insets, target.getWidth()), bottomY);
                    bottomY -= gap;
                }
            }

            if (middleComponent != null && middleComponent.isVisible()) {
                middleComponent.setSize(middleComponent.getWidth(), bottomY > topY ? bottomY - topY : 0);
                middleComponent.setLocation(getX(middleComponent, maxWidth, insets, target.getWidth()), topY);
            }
        }
    }

    private void moveComponentsH(Container target, int maxHeight) {
        synchronized (target.getTreeLock()) {
            Insets insets = target.getInsets();
            int leftX = insets.left + leftGap;
            int rightX = target.getWidth() - insets.right - rightGap;

            for (Component component : startComponents) {
                if (component.isVisible()) {
                    component.setLocation(leftX, getY(component, maxHeight, insets, target.getHeight()));
                    leftX += component.getWidth() + gap;
                }
            }

            Component component;

            for (int i = endComponents.size() - 1; i >= 0; i--) {
                component = endComponents.get(i);

                if (component.isVisible()) {
                    rightX -= component.getWidth();
                    component.setLocation(rightX, getY(component, maxHeight, insets, target.getHeight()));
                    rightX -= gap;
                }
            }

            if (middleComponent != null && middleComponent.isVisible()) {
                middleComponent.setSize(rightX > leftX ? rightX - leftX : 0, middleComponent.getHeight());
                middleComponent.setLocation(leftX, getY(middleComponent, maxHeight, insets, target.getHeight()));
            }
        }
    }

    private int getX(Component component, int maxWidth, Insets targetInsets, int targetWidth) {
        int x = targetInsets.left + leftGap;

        if (!fillComponents.contains(component)) {
            switch (alignment) {
                case LEADING: {
                    break;
                }
                case TRAILING: {
                    x += maxWidth - component.getWidth();
                    break;
                }
                default: {
                    x += (maxWidth - component.getWidth()) / 2;
                }
            }

            switch (position) {
                case TRAILING: {
                    x += (targetWidth - targetInsets.left - targetInsets.right - leftGap - rightGap - maxWidth);
                    break;
                }
                case CENTER: {
                    x += (targetWidth - targetInsets.left - targetInsets.right - leftGap - rightGap - maxWidth) / 2;
                    break;
                }
            }
        }

        return x;
    }

    private int getY(Component component, int maxHeight, Insets targetInsets, int targetHeight) {
        int y = targetInsets.top + topGap;

        if (!fillComponents.contains(component)) {
            switch (alignment) {
                case LEADING: {
                    break;
                }
                case TRAILING: {
                    y += maxHeight - component.getHeight();
                    break;
                }
                default: {
                    y += (maxHeight - component.getHeight()) / 2;
                }
            }

            switch (position) {
                case TRAILING: {
                    y += (targetHeight - targetInsets.top - targetInsets.bottom - topGap - bottomGap - maxHeight);
                    break;
                }
                case CENTER: {
                    y += (targetHeight - targetInsets.top - targetInsets.bottom - topGap - bottomGap - maxHeight) / 2;
                    break;
                }
            }
        }

        return y;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getLeftGap() {
        return leftGap;
    }

    public void setLeftGap(int leftGap) {
        this.leftGap = leftGap;
    }

    public int getRightGap() {
        return rightGap;
    }

    public void setRightGap(int rightGap) {
        this.rightGap = rightGap;
    }

    public int getTopGap() {
        return topGap;
    }

    public void setTopGap(int topGap) {
        this.topGap = topGap;
    }

    public int getBottomGap() {
        return bottomGap;
    }

    public void setBottomGap(int bottomGap) {
        this.bottomGap = bottomGap;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public float getLayoutAlignmentX(Container target) {
        float alignmentX = 0.5f;

        if (orientation == HORIZONTAL) {
            switch (alignment) {
                case LEADING: {
                    alignmentX = 0;
                    break;
                }
                case TRAILING: {
                    alignmentX = 1;
                    break;
                }
            }
        }

        return alignmentX;
    }

    public float getLayoutAlignmentY(Container target) {
        float alignmentY = 0.5f;

        if (orientation == VERTICAL) {
            switch (alignment) {
                case LEADING: {
                    alignmentY = 0;
                    break;
                }
                case TRAILING: {
                    alignmentY = 1;
                    break;
                }
            }
        }

        return alignmentY;
    }

    public String toString() {
        return getClass().getName() + "[gap=" + gap + ",leftGap=" + leftGap + ",rightGap=" + rightGap + ",topGap=" + topGap + ",bottomGap=" + bottomGap + ",alignment=" + alignment
                + ",position=" + position + ",orientation=" + orientation + "]";
    }
}