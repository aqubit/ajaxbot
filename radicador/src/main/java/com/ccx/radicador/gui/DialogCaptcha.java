package com.ccx.radicador.gui;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogCaptcha extends Dialog {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("com.ccx.radicador.gui.messages"); //$NON-NLS-1$

	/**
	 * 
	 */
	private static final long serialVersionUID = 1702567236796416102L;
	/**
	 * @wbp.nonvisual location=40,59
	 */
	private final JPanel _mainPanel = new JPanel();
	private JTextField _txtFldCaptcha;
	private ImageIcon _icon;
	private JButton _btnImage = new JButton();
	
	public DialogCaptcha(Dialog owner) {
		super(owner);
		setModal(true);
		setSize(320, 160);
		setResizable(false);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{320, 0};
		gbl_panel.rowHeights = new int[]{60, 30, 30, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		_mainPanel.setLayout(gbl_panel);
		GridBagConstraints gbc_panelImage = new GridBagConstraints();
		gbc_panelImage.fill = GridBagConstraints.BOTH;
		gbc_panelImage.insets = new Insets(0, 0, 5, 0);
		gbc_panelImage.gridx = 0;
		gbc_panelImage.gridy = 0;
		_mainPanel.add(_btnImage, gbc_panelImage);
		_txtFldCaptcha = new JTextField();
		GridBagConstraints gbc_txtFldCaptcha = new GridBagConstraints();
		gbc_txtFldCaptcha.anchor = GridBagConstraints.NORTH;
		gbc_txtFldCaptcha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFldCaptcha.insets = new Insets(0, 0, 5, 0);
		gbc_txtFldCaptcha.gridx = 0;
		gbc_txtFldCaptcha.gridy = 1;
		_mainPanel.add(_txtFldCaptcha, gbc_txtFldCaptcha);
		_txtFldCaptcha.setColumns(10);
		setTitle(BUNDLE.getString("DialogCaptcha.this.title")); //$NON-NLS-1$
		add(_mainPanel);
		JButton btnAceptar = new JButton(BUNDLE.getString("DialogCaptcha.btnAceptar.text")); //$NON-NLS-1$
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.anchor = GridBagConstraints.NORTH;
		gbc_btnAceptar.gridx = 0;
		gbc_btnAceptar.gridy = 2;
		_mainPanel.add(btnAceptar, gbc_btnAceptar);
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				_txtFldCaptcha.requestFocus();
			}
		});		
		CCXUtils.locateOnScreenCenter(this);
	}
	
	public String getCaptcha(){
		return _txtFldCaptcha.getText();
	}
	
	public void setSource(String src){
		try {
			_icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(new URL(src)));
		} catch (MalformedURLException e1) {
			_icon = null;
			e1.printStackTrace();
		}
		_btnImage.setIcon(_icon);
		_txtFldCaptcha.setText("");
	}
}
