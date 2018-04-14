/**
 * Copyright (c) 2014, 2016 ControlsFX
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of ControlsFX, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.oim.ui.fx.classics.component;

import com.only.fx.common.action.ExecuteAction;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.control.cell.ItemListCell;
import javafx.scene.input.MouseButton;

public class ListPopupSkin<T> implements Skin<ListPopup<T>> {

	private final ListPopup<T> control;
	private final ListView<T> suggestionList;
	final int LIST_CELL_HEIGHT = 20;

	public ListPopupSkin(ListPopup<T> control) {
		this.control = control;
		
		ExecuteAction executeAction=new ExecuteAction(){

			@SuppressWarnings("hiding")
			@Override
			public <T, E> E execute(T value) {
				if (value != null) {
					Event.fireEvent(control, new ListPopup.RemoveEvent<>(value));
				}
				return null;
			}
			
		};
		
		suggestionList = new ListView<>(control.getSuggestions());

		suggestionList.prefHeightProperty().bind(Bindings.min(control.visibleRowCountProperty(), Bindings.size(suggestionList.getItems())).multiply(LIST_CELL_HEIGHT).add(18));
		suggestionList.setCellFactory(ItemListCell.forListView(executeAction,control.getConverter()));
		suggestionList.prefWidthProperty().bind(control.prefWidthProperty());
		suggestionList.maxWidthProperty().bind(control.maxWidthProperty());
		suggestionList.minWidthProperty().bind(control.minWidthProperty());
		registerEventListener();
	}

	private void registerEventListener() {
		suggestionList.setOnMouseClicked(me -> {
			if (me.getButton() == MouseButton.PRIMARY) {
				onSuggestionChoosen(suggestionList.getSelectionModel().getSelectedItem());
			}
		});

		suggestionList.setOnKeyPressed(ke -> {
			switch (ke.getCode()) {
			case ENTER:
				onSuggestionChoosen(suggestionList.getSelectionModel().getSelectedItem());
				break;
			case ESCAPE:
				if (control.isHideOnEscape()) {
					control.hide();
				}
				break;
			default:
				break;
			}
		});
	}

	//
	private void onSuggestionChoosen(T suggestion) {
		if (suggestion != null) {
			Event.fireEvent(control, new ListPopup.SuggestionEvent<>(suggestion));
		}
	}
	
//	private void onRemoveEvent(T value) {
//		if (value != null) {
//			Event.fireEvent(control, new ListPopup.RemoveEvent<>(value));
//		}
//	}

	@Override
	public Node getNode() {
		return suggestionList;
	}

	@Override
	public ListPopup<T> getSkinnable() {
		return control;
	}

	@Override
	public void dispose() {
	}
}
