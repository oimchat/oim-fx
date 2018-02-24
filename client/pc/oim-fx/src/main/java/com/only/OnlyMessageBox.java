package com.only;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.only.layout.LineLayout;



public class OnlyMessageBox extends JDialog implements ActionListener {

    private static final long serialVersionUID = 3983953036367048891L;
    public static final int CLOSE_OPTION = 0;
    public static final int OK_OPTION = 1;
    public static final int CANCEL_OPTION = 1 << 1;
    public static final int YES_OPTION = 1 << 2;
    public static final int NO_OPTION = 1 << 3;

    public static enum MessageType {

        ERROR, INFORMATION, WARNING, QUESTION
    }
    private JButton btnOK, btnCancel, btnYes, btnNo;
    private JLabel lbMessage;
    private Map<OnlyMessageBox.MessageType, Icon> iconMap;
    private int option;

    private OnlyMessageBox(Window owner, String title, String message, OnlyMessageBox.MessageType messageType, int option) {
        super(owner, title, Dialog.ModalityType.DOCUMENT_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init(message, messageType, option);
    }

    public static OnlyMessageBox createMessageBox(Window parent, String title, String message, OnlyMessageBox.MessageType messageType, int option) {
        return new OnlyMessageBox(parent, title, message, messageType, option);
    }

    public static OnlyMessageBox createErrorMessageBox(Window parent, String title, String message) {
        return createErrorMessageBox(parent, title, message, OK_OPTION);
    }

    public static OnlyMessageBox createErrorMessageBox(Window parent, String title, String message, int option) {
        return createMessageBox(parent, title, message, OnlyMessageBox.MessageType.ERROR, option);
    }

    public static OnlyMessageBox createInformationMessageBox(Window parent, String title, String message) {
        return createInformationMessageBox(parent, title, message, OK_OPTION);
    }

    public static OnlyMessageBox createInformationMessageBox(Window parent, String title, String message, int option) {
        return createMessageBox(parent, title, message, OnlyMessageBox.MessageType.INFORMATION, option);
    }

    public static OnlyMessageBox createWarningMessageBox(Window parent, String title, String message) {
        return createWarningMessageBox(parent, title, message, OK_OPTION | CANCEL_OPTION);
    }

    public static OnlyMessageBox createWarningMessageBox(Window parent, String title, String message, int option) {
        return createMessageBox(parent, title, message, OnlyMessageBox.MessageType.WARNING, option);
    }

    public static OnlyMessageBox createQuestionMessageBox(Window parent, String title, String message) {
        return createQuestionMessageBox(parent, title, message, YES_OPTION | NO_OPTION);
    }

    public static OnlyMessageBox createQuestionMessageBox(Window parent, String title, String message, int option) {
        return createMessageBox(parent, title, message, OnlyMessageBox.MessageType.QUESTION, option);
    }

    private void init(String message, OnlyMessageBox.MessageType messageType, int option) {
  
        iconMap = new HashMap<OnlyMessageBox.MessageType, Icon>();
        iconMap.put(OnlyMessageBox.MessageType.ERROR, new ImageIcon());
        iconMap.put(OnlyMessageBox.MessageType.INFORMATION, new ImageIcon());
        iconMap.put(OnlyMessageBox.MessageType.QUESTION, new ImageIcon());
        iconMap.put(OnlyMessageBox.MessageType.WARNING, new ImageIcon());

        JPanel buttonPane = new JPanel();
        lbMessage = new JLabel(message, iconMap.get(messageType), JLabel.LEFT);
        btnOK = new JButton("确定");
        btnCancel = new JButton("取消");
        btnYes = new JButton("是");
        btnNo = new JButton("否");
        JButton[] buttons = {btnOK, btnYes, btnNo, btnCancel};
        int[] options = {OK_OPTION, YES_OPTION, NO_OPTION, CANCEL_OPTION};
        final Dimension buttonSize = new Dimension(69, 21);
        int index = 0;
        boolean hasDefaultButton = false;

       
        lbMessage.setIconTextGap(16);
        lbMessage.setHorizontalAlignment(JLabel.LEFT);
        lbMessage.setVerticalAlignment(JLabel.TOP);
        lbMessage.setVerticalTextPosition(JLabel.TOP);
        lbMessage.setBorder(new EmptyBorder(15, 25, 15, 25));
        buttonPane.setLayout(new LineLayout(6, 0, 0, 0, 0, LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
        buttonPane.setPreferredSize(new Dimension(-1, 33));
        buttonPane.setBorder(new EmptyBorder(5, 9, 0, 9));
        buttonPane.setBackground(new Color(255, 255, 255, 170));

        for (JButton button : buttons) {
            button.setActionCommand(String.valueOf(options[index]));
            button.setPreferredSize(buttonSize);
            button.setVisible((option & options[index]) != 0);
            button.addActionListener(this);
            buttonPane.add(button, LineLayout.END);
            index++;

            if (!hasDefaultButton && button.isVisible()) {
                getRootPane().setDefaultButton(button);
                hasDefaultButton = true;
            }
        }

        getContentPane().setLayout(new LineLayout(0, 1, 1, 3, 1, LineLayout.LEADING, LineLayout.LEADING, LineLayout.VERTICAL));
        getContentPane().add(lbMessage, LineLayout.MIDDLE_FILL);
        getContentPane().add(buttonPane, LineLayout.END_FILL);
    }

    public int open() {
        option = CLOSE_OPTION;
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(this.getParent());
        this.setResizable(false);
        this.setVisible(true);
        return option;
    }

    @Override
    public Dimension getPreferredSize() {
        int width = Math.max(315, lbMessage.getPreferredSize().width) + 15;
        int height = Math.max(150, lbMessage.getPreferredSize().height + 15 + 28 + 34);
        return new Dimension(width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        option = Integer.parseInt(button.getActionCommand());
        this.dispose();
    }
}