/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.webview;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSException;

public class HTMLEditorCustomizationSample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //int to string
    private static String hex(int i) {
        return Integer.toHexString(i);
    }

    //a method to convert to a javas/js style string 
    //https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/src-html/org/apache/commons/lang/StringEscapeUtils.html
    private static String escapeJavaStyleString(String str,
            boolean escapeSingleQuote, boolean escapeForwardSlash) {
        StringBuilder out = new StringBuilder("");
        if (str == null) {
            return null;
        }
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                out.append("\\u").append(hex(ch));
            } else if (ch > 0xff) {
                out.append("\\u0").append(hex(ch));
            } else if (ch > 0x7f) {
                out.append("\\u00").append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;
                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;
                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;
                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;
                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            out.append("\\u00").append(hex(ch));
                        } else {
                            out.append("\\u000").append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        if (escapeSingleQuote) {
                            out.append('\\');
                        }
                        out.append('\'');
                        break;
                    case '"':
                        out.append('\\');
                        out.append('"');
                        break;
                    case '\\':
                        out.append('\\');
                        out.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            out.append('\\');
                        }
                        out.append('/');
                        break;
                    default:
                        out.append(ch);
                        break;
                }
            }
        }
        return out.toString();
    }

    @Override
    public void start(Stage stage) {
// create a new html editor and show it before we start modifying it.
        final HTMLEditor htmlEditor = new HTMLEditor();
        stage.setScene(new Scene(htmlEditor));
        stage.show();

// add a custom button to the top toolbar.
        Node toolNode = htmlEditor.lookup(".top-toolbar");
        Node webNode = htmlEditor.lookup(".web-view");
        if (toolNode instanceof ToolBar && webNode instanceof WebView) {
            ToolBar bar = (ToolBar) toolNode;
            WebView webView = (WebView) webNode;
            WebEngine engine = webView.getEngine();

            Button btnCaretAddImage = new Button("add an image");
            btnCaretAddImage.setMinSize(100.0, 24.0);
            btnCaretAddImage.setMaxSize(100.0, 24.0);
            
            Button show = new Button("getHtmlText");
            show.setMinSize(100.0, 24.0);
            show.setMaxSize(100.0, 24.0);

            show.setOnAction((ActionEvent event) -> {
              
                System.out.println( htmlEditor.getHtmlText());
            });
            
            bar.getItems().addAll(btnCaretAddImage);
            bar.getItems().addAll(show);
            htmlEditor.setHtmlText("Hello World");
            //data uri image
            //
           String img = "<img alt=\"Embedded Image\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAEeSURBVDhPbZFba8JAEIXz//9BoeCD4GNQAqVQBUVKm97UeMFaUmvF1mqUEJW0NXV6mtlL3PRwILuz52NnJxax5iGdt8l2TaP4shCZVBI4ezCjypVbkUklASN07NftlzMNh2GMYA6Iv0UFksWiH5w8Lk5HS9QkgHv5GAtuGq+SAKIAYJQl4E3UMTyYBLFzp7at1Q7p5scGQQlAszVdeJwYV7sqTa2xCKTKAET+dK1zysfSAEZReFqaadv1GsNi/53fAFv8KT2vkMYiKd8YADty7ptXvgaU3cuREc26Xx+YQPUtQnud3kznapkB2K4JJIcDgOBzr3uDkh+6Fjdb9XmUBf6OU3Fv6EHsIcy91hVTygP5CksA6uejGa78DxD9ApzMoGHun6uuAAAAAElFTkSuQmCC\" />";
          
            //http://stackoverflow.com/questions/2213376/how-to-find-cursor-position-in-a-contenteditable-div
            String jsCodeInsertHtml = "function insertHtmlAtCursor(html) {\n"
                    + "    var range, node;\n"
                    + "    if (window.getSelection && window.getSelection().getRangeAt) {\n"
                    + "        range = window.getSelection().getRangeAt(0);\n"
                    + "        node = range.createContextualFragment(html);\n"
                    + "        range.insertNode(node);\n"
                    + "    } else if (document.selection && document.selection.createRange) {\n"
                    + "        document.selection.createRange().pasteHTML(html);\n"
                    + "    }\n"
                    + "}insertHtmlAtCursor('####html####')";
            btnCaretAddImage.setOnAction((ActionEvent event) -> {
                try {
                    engine.executeScript(jsCodeInsertHtml. replace("####html####",escapeJavaStyleString(img, true, true)));
                } catch (JSException e) {
                    // A JavaScript Exception Occured
                }
            });
        }
    }
}
