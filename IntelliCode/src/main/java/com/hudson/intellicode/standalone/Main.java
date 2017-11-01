package com.hudson.intellicode.standalone;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	private static JFrame createWindow() {
		JFrame window = new JFrame("IntelliCode - Hudson, Inc.");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(500, 800);

		JComponent contentPane = new ChatPanel();
		contentPane.setOpaque(true);
		window.setContentPane(contentPane);
		window.setLocationRelativeTo(null);
		
		return window;

	}

	public static void main(String[] args) throws Exception {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				JFrame window = createWindow();
				window.setVisible(true);
			}
		});
	}
}
