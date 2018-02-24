package com.test.oim.fx.view;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.oim.core.business.view.LoginView;
import com.oim.fx.v1.view.LoginViewImpl;
import com.onlyxiahui.app.base.AppContext;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class LoginViewTest extends Application {

	protected AppContext appContext = new AppContext();

	@Override
	public void start(Stage primaryStage) {
		appContext.register(LoginView.class, LoginViewImpl.class);
		LoginView loginView = appContext.getSingleView(LoginView.class);
		loginView.setVisible(true);
	}

	
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
