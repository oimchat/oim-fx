/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.common.component.common;

import java.awt.event.ActionEvent;

/**
 *
 * @author XiaHui
 */
public interface Execute {

    void execute();

    void execute(Object object);

    void execute(ActionEvent event, Object value);

    Object action();

    Object action(Object key);

    Object action(Object event, Object value);
}
