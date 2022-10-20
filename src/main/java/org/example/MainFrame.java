package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame implements Runnable {
    private JTextField srcContentTextField;//记录剪切板的内容
    private JTextArea resContentTextField;//记录翻译的内容
    private JCheckBox translateFlag;//标记单词的获取来源

    private Container topContainer;

    public MainFrame(){
        srcContentTextField = new JTextField(10);
        resContentTextField = new JTextArea();
        translateFlag = new JCheckBox();
        topContainer = new Container();
    }

    public void setMainWindowLayout(){
        resContentTextField.setBorder(new LineBorder(new java.awt.Color(127,157,185),1,false));
        this.setLayout(new BorderLayout());
        this.add(this.resContentTextField);
        translateFlag.setToolTipText("手动输入取词");
        topContainer.setLayout(new BorderLayout());
        topContainer.add(srcContentTextField,BorderLayout.CENTER);
        topContainer.add(translateFlag,BorderLayout.EAST);
        this.add(this.topContainer,BorderLayout.NORTH);
        this.setResizable(false);

        translateFlag.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(translateFlag.isSelected()){
                    translateFlag.setToolTipText("自动复制取词");
                }else{
                    translateFlag.setToolTipText("手动输入取词");
                }
            }
        });

        srcContentTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                //调用接口获取翻译结果
                try{
                    String result = GetHtmlConentUtils.getTranslateResult(String.valueOf(srcContentTextField.getText()));
                    if(result == "") {
                        result = "不好意思，未找到该词";
                    }
                    else {
                        resContentTextField.setText(result);
                    }
                } catch (Exception e) {
                    resContentTextField.setText("系统故障，请稍等");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }
        });
        this.validate();
    }

    @Override
    public void run() {
        while (true){
            if(!translateFlag.isSelected()){//如果JcheckBox没有被选中，则从剪切板获取单词
                try{
                    String content = ClipboardUtils.getClipboardText();
                    srcContentTextField.setText(getSimpleWord(content));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    private String getSimpleWord(String content) {//去掉剪切板里的特殊字符
        return content.replace(".","").replace(",","").replace("","").replace(":","").replace(";","").trim();
    }
}
