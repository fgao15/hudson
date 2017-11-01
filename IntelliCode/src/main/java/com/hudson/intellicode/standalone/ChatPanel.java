package com.hudson.intellicode.standalone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.apache.commons.lang3.StringUtils;

import com.hudson.intellicode.config.Configuration;
import com.hudson.intellicode.core.ChatControllerFactory;
import com.hudson.intellicode.spi.IChatController;

public class ChatPanel extends JPanel {

	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == inputTextField) {
				send();
			}
		}
	}

	private JPanel mainPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane(mainPanel);
	private IChatController chatController = ChatControllerFactory.getChatController(Configuration.defaultConfiguration());
	private JTextField inputTextField = new JTextField();
	private JButton sendButton = new JButton("Send");
	private int currentMessageIndex = 0;
	
	private EventListener eventListener = new EventListener();

	ChatPanel() {
		super();
		initGui();
	}

	private void initBottomPanel() {

		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 2, 2, 2);
		bottomPanel.add(inputTextField, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		bottomPanel.add(sendButton, gbc);

		inputTextField.addActionListener(eventListener);
		sendButton.addActionListener(eventListener);
	}

	private void initMainPanel()
	{
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(new GridBagLayout());
	}
	
	private void initGui() {
		setBackground(Color.WHITE);

		initMainPanel();
		initBottomPanel();

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private void send() {
		String inputText = inputTextField.getText();
		if (StringUtils.isNotBlank(inputText)) {
			addTextToMainPanel(inputText, true);
			String response = chatController.send(inputText);
			addTextToMainPanel(response, false);
			inputTextField.setText(null);
		}
	}

	private void addTextToMainPanel(String inputText, boolean bUserInput) {
		JComponent label = createLabel(inputText, bUserInput);
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(480, 25));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = bUserInput ? 0 : 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = inputText.length() < 55 ? 0.05 : 2;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(2, 4, 2, 4);
		gbc.anchor = bUserInput ? GridBagConstraints.LINE_START : GridBagConstraints.LINE_END;
		panel.add(label, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = bUserInput ? 1 : 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(Box.createHorizontalStrut(5), gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = currentMessageIndex;
		gbc.ipadx = 2;
		gbc.ipady = 15 * (int)(Math.ceil(inputText.length() * 1.0/55));
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(panel, gbc);
		
		updateUI();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
				verticalBar.setValue(verticalBar.getMaximum());
			}
		});
		++currentMessageIndex;
	}

	private JComponent createLabel(String inputText, boolean bUserInput) {
		JLabel label = new JLabel("<html><p>" + inputText + "</p></html>");
		label.setHorizontalAlignment(bUserInput ? SwingConstants.LEFT : SwingConstants.RIGHT);
		
		label.setOpaque(true);
		label.setBackground(bUserInput ? new Color(239,240,241) : Color.GREEN.darker());
		
		Border border = new LineBorder(new Color(127, 157,185), 1, true);
		label.setBorder(border);
		
		if (!bUserInput)
		{
			label.setForeground(Color.WHITE);
			Font font = label.getFont();
			label.setFont(font.deriveFont(Font.BOLD));
		}
		return label;
	}

}
