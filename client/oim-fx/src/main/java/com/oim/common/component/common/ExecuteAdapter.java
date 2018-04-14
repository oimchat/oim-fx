/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.common.component.common;

import java.awt.event.ActionEvent;

/**
 * 2013-8-29 16:41:31
 *
 * @author XiaHui
 */
public abstract class ExecuteAdapter implements Execute {

    @Override
    public void execute() {
    }

    @Override
    public void execute(Object object) {
    }

    @Override
    public void execute(ActionEvent event, Object value) {
    }

    @Override
    public Object action() {
        return null;
    }

    @Override
    public Object action(Object key) {
        return null;
    }

    @Override
    public Object action(Object event, Object value) {
        return null;
    }
}
