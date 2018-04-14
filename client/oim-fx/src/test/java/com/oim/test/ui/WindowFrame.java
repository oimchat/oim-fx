/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import javafx.stage.PopupWindow;

/**
 *
 * @author Only
 */
public class WindowFrame extends PopupWindow {


    public WindowFrame() {
        init();
    }

    private void init() {
        this.setWidth(740);
        this.setHeight(260);
        this.show();
    }
}
