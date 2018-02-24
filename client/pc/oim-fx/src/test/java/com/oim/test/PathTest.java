package com.oim.test;

import java.io.File;

/**
 * Created by Here on 2017/4/3.
 */
public class PathTest {
    public static void main(String[] agr) {

        System.out.println(PathTest.class.getProtectionDomain().getCodeSource().getLocation());

        File file = new File("log.txt");
        System.out.println(file.getAbsolutePath());
    }
}
