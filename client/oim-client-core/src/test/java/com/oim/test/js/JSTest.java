package com.oim.test.js;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSTest {

	public static void main(String[] args) {
		InputStream in = JSTest.class.getResourceAsStream("/com/oim/test/js/test.js");
		Reader inreader = new InputStreamReader(in);
		ScriptEngineManager m = new ScriptEngineManager();
		ScriptEngine se = m.getEngineByName("javascript");
		try {
			se.eval(inreader);
			Object t = se.eval("get(\"" + 123 + "\");");
			
			System.out.println(t);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
