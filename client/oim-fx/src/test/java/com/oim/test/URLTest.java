/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Only
 */
public class URLTest {
    public static void main(String[] args) {
        try {
            File file=new File("Resources/Images/Wallpaper/1.jpg");
            System.out.println(file.getAbsolutePath());
            String urlString=  new URL(file.getAbsolutePath()).toString();
            System.out.println(urlString);
        } catch (MalformedURLException ex) {
            Logger.getLogger(URLTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
