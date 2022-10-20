package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(300,200,400,300);
        mainFrame.setVisible(true);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setMainWindowLayout();

        Thread t = new Thread(mainFrame);
        t.start();
    }
}