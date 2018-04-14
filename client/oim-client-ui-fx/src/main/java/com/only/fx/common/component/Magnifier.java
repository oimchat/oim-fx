/**
 * Magnifier.java
 *
 * Copyright (c) 2011-2015, JFXtras
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the organization nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.only.fx.common.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;

/**
 * <p>
 * A control which allows the subsection of its content to be magnified when the mouse hovers over it.
 * </p>
 * 
 * <p>
 * Note : When the magnifier is activated mode, the contents inside this control are not accessible.
 * </p>
 * 
 * <p>
 * Example :
 * <pre>
 * <code>Magnifier magnifier = new Magnifier();
 * magnifier.setContent(someNode);</code>
 * or
 * <code>Magnifier magnifier = new Magnifier(someNode);</code>
 * </pre>
 * 
 * @author SaiPradeepDandem
 */
public class Magnifier extends Control {
	// Configurable properties
	private DoubleProperty radius;
	private DoubleProperty frameWidth;
	private DoubleProperty scaleFactor;
	private DoubleProperty scopeLineWidth;
	private BooleanProperty scopeLinesVisible;
	private ObjectProperty<Node> content;
	private BooleanProperty active;
	private BooleanProperty scalableOnScroll;
	private BooleanProperty resizableOnScroll;

	// Default values
	private final double DEFAULT_RADIUS = 86.0D;
	private final double DEFAULT_FRAME_WIDTH = 5.5D;
	private final double DEFAULT_SCALE_FACTOR = 3.0D;
	private final double DEFAULT_SCOPELINE_WIDTH = 1.5D;
	private final boolean DEFAULT_SCOPELINE_VISIBLE = false;
	private final boolean DEFAULT_ACTIVE = true;
	private final boolean DEFAULT_SCALABLE_ONSCROLL = false;
	private final boolean DEFAULT_RESIZABLE_ONSCROLL = false;
	
	private final String DEFAULT_STYLE_CLASS = "magnifier";
	
	/**
	 * Creates a magnifier pane with empty content.
	 */
	public Magnifier() {
		this(null);
	}

	/**
	 * Creates a magnifier pane with the provided content.
	 */
	public Magnifier(Node node) {
		getStyleClass().setAll(new String[] { DEFAULT_STYLE_CLASS });
		if (node != null) {
			setContent(node);
		}
	}

	/**
	 * Return the path to the CSS file so things are setup right
	 */
	@Override
	public String getUserAgentStylesheet() {
		return this.getClass().getResource(this.getClass().getSimpleName() + ".css").toString();
	}

	/**
	 * Property for setting the radius of the circular viewer. The default value is 86.0D.
	 * 
	 * @see #setRadius(double)
	 * @see #getRadius()
	 */
	public final DoubleProperty radiusProperty() {
		if (this.radius == null) {
			this.radius = new DoublePropertyBase(DEFAULT_RADIUS) {
				@Override
				public String getName() {
					return "radius";
				}

				@Override
				public Object getBean() {
					return Magnifier.this;
				}
			};
		}
		return this.radius;
	}

	/**
	 * Sets the value of the property radius.
	 */
	public final void setRadius(double paramRadius) {
		radiusProperty().setValue(paramRadius);
	}

	/**
	 * Gets the value of the property radius.
	 */
	public final double getRadius() {
		return ((this.radius == null) ? DEFAULT_RADIUS : this.radius.getValue());
	}

	/**
	 * Property for setting the frame width of the circular viewer. The default value is 5.5D.
	 * 
	 * @see #setFrameWidth(double)
	 * @see #getFrameWidth()
	 */
	public final DoubleProperty frameWidthProperty() {
		if (this.frameWidth == null) {
			this.frameWidth = new DoublePropertyBase(DEFAULT_FRAME_WIDTH) {
				@Override
				public String getName() {
					return "frameWidth";
				}

				@Override
				public Object getBean() {
					return Magnifier.this;
				}
			};
		}
		return this.frameWidth;
	}

	/**
	 * Sets the value of the property frameWidth.
	 */
	public final void setFrameWidth(double paramFrameWidth) {
		frameWidthProperty().setValue(paramFrameWidth);
	}

	/**
	 * Gets the value of the property frameWidth.
	 */
	public final double getFrameWidth() {
		return ((this.frameWidth == null) ? DEFAULT_FRAME_WIDTH : this.frameWidth.getValue());
	}

	/**
	 * Property for setting the scale factor to which the content need to be magnified. The default value is 3.0D.
	 * 
	 * @see #setScaleFactor(double)
	 * @see #getScaleFactor()
	 */
	public final DoubleProperty scaleFactorProperty() {
		if (this.scaleFactor == null) {
			this.scaleFactor = new DoublePropertyBase(DEFAULT_SCALE_FACTOR) {
				@Override
				public String getName() {
					return "scaleFactor";
				}

				@Override
				public Object getBean() {
					return Magnifier.this;
				}
			};
		}
		return this.scaleFactor;
	}

	/**
	 * Sets the value of the property scaleFactor.
	 */
	public final void setScaleFactor(double paramScaleFactor) {
		scaleFactorProperty().setValue(paramScaleFactor);
	}

	/**
	 * Gets the value of the property scaleFactor.
	 */
	public final double getScaleFactor() {
		return ((this.scaleFactor == null) ? DEFAULT_SCALE_FACTOR : this.scaleFactor.getValue());
	}

	/**
	 * Property for setting the width of the scope lines that are visible in the circular viewer. The default value is 1.5px.
	 * 
	 * @see #setScopeLineWidth(double)
	 * @see #getScopeLineWidth()
	 */
	public final DoubleProperty scopeLineWidthProperty() {
		if (this.scopeLineWidth == null) {
			this.scopeLineWidth = new DoublePropertyBase(DEFAULT_SCOPELINE_WIDTH) {
				@Override
				public String getName() {
					return "scopeLineWidth";
				}

				@Override
				public Object getBean() {
					return Magnifier.this;
				}
			};
		}
		return this.scopeLineWidth;
	}

	/**
	 * Sets the value of the property scopeLineWidth.
	 */
	public final void setScopeLineWidth(double paramScopeLineWidth) {
		scopeLineWidthProperty().setValue(paramScopeLineWidth);
	}

	/**
	 * Gets the value of the property scopeLineWidth.
	 */
	public final double getScopeLineWidth() {
		return ((this.scopeLineWidth == null) ? DEFAULT_SCOPELINE_WIDTH : this.scopeLineWidth.getValue());
	}

	/**
	 * Controls whether lines are displayed to show in the magnifier viewer. Default is {@code false}.
	 * 
	 * @see #setScopeLinesVisible(boolean)
	 * @see #isScopeLinesVisible()
	 */
	public final BooleanProperty scopeLinesVisibleProperty() {
		if (this.scopeLinesVisible == null) {
			this.scopeLinesVisible = new BooleanPropertyBase(DEFAULT_SCOPELINE_VISIBLE) {

				@Override
				public String getName() {
					return "scopeLinesVisible";
				}

				@Override
				public Object getBean() {
					return Magnifier.this;
				}
			};
		}
		return this.scopeLinesVisible;
	}

	/**
	 * Sets the value of the property scopeLinesVisible.
	 */
	public final void setScopeLinesVisible(boolean paramScopeLinesVisible) {
		scopeLinesVisibleProperty().setValue(paramScopeLinesVisible);
	}

	/**
	 * Gets the value of the property scopeLinesVisible.
	 */
	public final boolean isScopeLinesVisible() {
		return ((this.scopeLinesVisible == null) ? DEFAULT_SCOPELINE_VISIBLE : this.scopeLinesVisible.getValue());
	}

	/**
	 * <p>
	 * The content associated with the magnifier.
	 * </p>
	 * 
	 * @see #setContent(javafx.scene.Node)
	 * @see #getContent()
	 */
	public final ObjectProperty<Node> contentProperty() {
		if (content == null) {
			content = new SimpleObjectProperty<Node>(this, "content");
		}
		return content;
	}

	/**
	 * Sets the content of the magnifier.
	 */
	public final void setContent(Node value) {
		contentProperty().set(value);
	}

	/**
	 * Gets the content of the magnifier.
	 */
	public final Node getContent() {
		return content == null ? null : content.get();
	}
	
	/**
	 * Controls the magnifier whether to activate or not.
	 * <ul>
	 * <li> {@code true} Shows the magnified viewer on mouse over and does not allow to access the content inside the control.</li>
	 * <li> {@code false} Does not show the magnified viewer on mouse over and can access the content inside the control.</li>
	 * </ul>
	 * <p>Default value is {@code true}</p>.
	 * 
	 * @see #setActive(boolean)
	 * @see #isActive()
	 */
	public final BooleanProperty activeProperty() {
		if (this.active == null) {
			this.active = new BooleanPropertyBase(DEFAULT_ACTIVE) {

				@Override
				public String getName() {
					return "active";
				}

				@Override
				public Object getBean() {
					return Magnifier.this;
				}
			};
		}
		return this.active;
	}

	/**
	 * Sets the value of the property active.
	 */
	public final void setActive(boolean paramActivate) {
		activeProperty().setValue(paramActivate);
	}

	/**
	 * Gets the value of the property active.
	 */
	public final boolean isActive() {
		return ((this.active == null) ? DEFAULT_ACTIVE : this.active.getValue());
	}
	
	/**
	 * Controls the magnifier whether to scale the content on mouse scroll or not. Content is scaled only when the mouse is scrolled in
	 * combination with CTRL key press.
	 * <ul>
	 * <li> {@code true} Allows the content to scale when mouse is scrolled in combination with CTRL key press.</li>
	 * <li> {@code false} Does not allow the content to scale when mouse is scrolled.</li>
	 * </ul>
	 * <p>Default value is {@code false}</p>.
	 * 
	 * @see #setScalableOnScroll(boolean)
	 * @see #isScalableOnScroll()
	 */
	public final BooleanProperty scalableOnScrollProperty() {
		if (this.scalableOnScroll == null) {
			this.scalableOnScroll = new BooleanPropertyBase(DEFAULT_SCALABLE_ONSCROLL) {

				@Override
				public String getName() {
					return "scalableOnScroll";
				}

				@Override
				public Object getBean() {
					return Magnifier.this;
				}
			};
		}
		return this.scalableOnScroll;
	}

	/**
	 * Sets the value of the property scalableOnScroll.
	 */
	public final void setScalableOnScroll(boolean paramScalableOnScroll) {
		scalableOnScrollProperty().setValue(paramScalableOnScroll);
	}

	/**
	 * Gets the value of the property scalableOnScroll.
	 */
	public final boolean isScalableOnScroll() {
		return ((this.scalableOnScroll == null) ? DEFAULT_SCALABLE_ONSCROLL : this.scalableOnScroll.getValue());
	}
	
	/**
	 * Controls the magnifier whether to resize the viewer on mouse scroll or not. The viewer is resized only when the mouse is scrolled in
	 * combination with ALT key press.
	 * <ul>
	 * <li> {@code true} Allows the viewer to resize when mouse is scrolled in combination with ALT key press.</li>
	 * <li> {@code false} Does not allow the viewer to resize when mouse is scrolled.</li>
	 * </ul>
	 * <p>Default value is {@code false}</p>.
	 * 
	 * @see #setResizableOnScroll(boolean)
	 * @see #isResizableOnScroll()
	 */
	public final BooleanProperty resizableOnScrollProperty() {
		if (this.resizableOnScroll == null) {
			this.resizableOnScroll = new BooleanPropertyBase(DEFAULT_RESIZABLE_ONSCROLL) {

				@Override
				public String getName() {
					return "resizableOnScroll";
				}

				@Override
				public Object getBean() {
					return Magnifier.this;
				}
			};
		}
		return this.resizableOnScroll;
	}

	/**
	 * Sets the value of the property resizableOnScroll.
	 */
	public final void setResizableOnScroll(boolean paramResizableOnScroll) {
		resizableOnScrollProperty().setValue(paramResizableOnScroll);
	}

	/**
	 * Gets the value of the property resizableOnScroll.
	 */
	public final boolean isResizableOnScroll() {
		return ((this.resizableOnScroll == null) ? DEFAULT_RESIZABLE_ONSCROLL : this.resizableOnScroll.getValue());
	}
}
