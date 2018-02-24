package com.oim.fx.ui.chat;

import com.sun.javafx.webkit.theme.ContextMenuImpl;
import com.sun.javafx.webkit.theme.PopupMenuImpl;
import com.sun.webkit.ContextMenu;
import com.sun.webkit.Pasteboard;
import com.sun.webkit.PopupMenu;
import com.sun.webkit.Utilities;

/**
 * @author: XiaHui
 * @date: 2017年7月31日 下午1:58:05
 */
public class UtilitiesImpl extends Utilities {

	@Override
	protected Pasteboard createPasteboard() {
		return new PasteboardImpl();
	}

	@Override
	protected PopupMenu createPopupMenu() {
		return new PopupMenuImpl();
	}

	@Override
	protected ContextMenu createContextMenu() {
		return new ContextMenuImpl();
	}
}