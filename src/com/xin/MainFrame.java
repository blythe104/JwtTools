package com.xin;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * 定义解密面板样式
 * TODO
 * @author 002619
 * @date 2017年7月28日上午10:54:41
 */

public class MainFrame extends JFrame implements ActionListener {
	public MainFrame() {
	}

	JFrame mainframe;
	JPanel panel;
	// 创建相关的Label标签
	JLabel secretkey_label = new JLabel("JWT加密密钥");
	JLabel outfilepath_label = new JLabel("jwt解密内容:");
	JLabel reslut_lable = new JLabel("解密结果:");
	// 创建相关的文本域
	JTextField secretkey_textfield = new JTextField(400);
	JTextField textfile_jwtjson = new JTextField(400);
	// 创建滚动条以及输出文本域
	JScrollPane jscrollPane;
	JTextArea outtext_textarea = new JTextArea();
	// 创建按钮
	JButton start_button = new JButton("开始解密");

	public void show() {
		mainframe = new JFrame("JWT解密工具");
		// Setting the width and height of frame
		mainframe.setSize(575, 550);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setResizable(false);// 固定窗体大小

		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = mainframe.getHeight(); // 获取窗口高度
		int width = mainframe.getWidth(); // 获取窗口宽度
		mainframe.setLocation(screenWidth - width / 2, screenHeight - height / 2);// 将窗口设置到屏幕的中部
		// 窗体居中，c是Component类的父窗口
		// mainframe.setLocationRelativeTo(c);
		initPanel();// 初始化面板
		mainframe.getContentPane().add(panel);
		mainframe.setVisible(true);
	}

	/*
	 * 创建面板，这个类似于 HTML 的 div 标签 我们可以创建多个面板并在 JFrame 中指定位置 面板中我们可以添加文本字段，按钮及其他组件。
	 */
	public void initPanel() {
		this.panel = new JPanel();
		panel.setLayout(null);
		// this.panel = new JPanel(new GridLayout(3,2)); //创建3行3列的容器
		/*
		 * 这个方法定义了组件的位置。 setBounds(x, y, width, height) x 和 y 指定左上角的新位置，由 width
		 * 和 height 指定新的大小。
		 */
		secretkey_label.setBounds(10, 20, 120, 25);
		secretkey_textfield.setBounds(120, 20, 400, 25);
		this.panel.add(secretkey_label);
		this.panel.add(secretkey_textfield);

		outfilepath_label.setBounds(10, 50, 120, 25);
		textfile_jwtjson.setBounds(120, 50, 400, 65);
		this.panel.add(outfilepath_label);
		this.panel.add(textfile_jwtjson);

		reslut_lable.setBounds(10, 110, 120, 25);
		this.panel.add(reslut_lable);

		start_button.setBounds(10, 140, 80, 25);
		this.panel.add(start_button);

		outtext_textarea.setEditable(false);
		outtext_textarea.setFont(new Font("标楷体", Font.BOLD, 16));
		jscrollPane = new JScrollPane(outtext_textarea);
		jscrollPane.setBounds(10, 170, 550, 330);
		this.panel.add(jscrollPane);
		// 增加动作监听
		start_button.addActionListener(this);
	}

	/**
	 * 单击动作触发方法
	 * 
	 * @param event
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		System.out.println(event.getActionCommand());
		if (event.getSource() == start_button) {
			// 确认对话框弹出
			int result = JOptionPane.showConfirmDialog(null, "是否开始解密?", "确认", 0);// YES_NO_OPTION
			if (result == 0) {// 是：0，否：1，取消：2

				if (secretkey_textfield.getText().equals("") || textfile_jwtjson.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "jwt密钥或者jwt需要解密的字段不能为空！", "提示", 2);// 弹出提示对话框，warning
					return;
				} else {

					// 开始解密
					String key = secretkey_textfield.getText().toString().trim();
					// 设置加密密钥
					JwtUtil.setSecretKey(key);
					// 获取jwt需解密的字段
					String jwtJson = textfile_jwtjson.getText().toString().trim();
					try {
						String jwt_result = JwtUtil.parseJWT(jwtJson).toString();
						outtext_textarea.setText(jwt_result);
						System.out.println("--jwt result---" + jwt_result);
					} catch (Exception e) {
						e.printStackTrace();
						outtext_textarea.setText(e.getMessage().toString());
					}

				}
				System.out.println(secretkey_textfield.getText());
			}else{
				return;
			}

		}
	}


	public void windowClosed(WindowEvent arg0) {
		System.exit(0);
	}

	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}

}
