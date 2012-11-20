package com.ccx.radicador;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.ccx.ex.ActorException;
import com.ccx.radicador.svc.DataSource;
import com.ccx.radicador.svc.HTTPDataSource;
import com.ccx.radicador.vo.Scenario;

public class App {
	private static final ResourceBundle BUNDLE = ResourceBundle
			.getBundle("com.ccx.radicador.gui.messages"); //$NON-NLS-1$
	private JFrame frmRadicador;
	private JTextField txtFldKey;
	private JButton btnNewPIN;
	private JButton btnLoad;
	private JButton btnMapSolicitud;
	private JButton btnMapLocalizacion;
	private Font CCXFont = new Font("Tahoma", Font.BOLD, 21);
	private Font CCXFontEx = new Font("Tahoma", Font.BOLD, 17);
	private Color COLOR_EX = Color.RED;
	private WebDriver driver;
	private Wait<WebDriver> wait;
	private DataSource ds = new HTTPDataSource();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmRadicador.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initializeSelenium();
		initialize();
	}

	private void initializeSelenium() {
		try {
			String firefoxProfile = System.getProperty("profile");
			if (firefoxProfile != null) {
				try {
					File profileDir = new File(firefoxProfile);
					FirefoxProfile profile = new FirefoxProfile(profileDir);
					driver = new FirefoxDriver(profile);
				} catch (Exception e) {
					driver = new FirefoxDriver();
				}
			} else {
				driver = new FirefoxDriver();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRadicador = new JFrame();
		frmRadicador.setAlwaysOnTop(true);
		frmRadicador.setTitle(BUNDLE.getString("App.frmRadicador.title")); //$NON-NLS-1$
		frmRadicador.setBounds(100, 100, 499, 130);
		frmRadicador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRadicador.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

		txtFldKey = new JTextField("");
		txtFldKey.setHorizontalAlignment(SwingConstants.CENTER);
		txtFldKey.setFont(CCXFont);
		btnNewPIN = new JButton(BUNDLE.getString("App.btnNewPIN.text")); //$NON-NLS-1$
		btnNewPIN.setFont(CCXFontEx);
		btnNewPIN.setForeground(COLOR_EX);
		btnNewPIN.setEnabled(false);
		btnNewPIN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ds.newPIN(frmRadicador);
			}
		});
		btnMapSolicitud = new JButton(
				BUNDLE.getString("App.btnMapaSoliciutd.text")); //$NON-NLS-1$
		btnMapSolicitud.setFont(CCXFontEx);
		btnMapSolicitud.setForeground(COLOR_EX);
		btnMapSolicitud.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = ds.getFileNameSolicitud();
				StringSelection ss = new StringSelection(filePath);
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(ss, null);
				JOptionPane
						.showMessageDialog(
								frmRadicador,
								"Ruta del archivo de coordenadas de 'Existencia de Explotación Minera' copiado al portapapeles con éxito.",
								"Radicador EBX",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnMapLocalizacion = new JButton(
				BUNDLE.getString("App.btnMapaLocalizacion.text")); //$NON-NLS-1$
		btnMapLocalizacion.setFont(CCXFontEx);
		btnMapLocalizacion.setForeground(COLOR_EX);
		btnMapLocalizacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = ds.getFileNameLocalizacion();
				StringSelection ss = new StringSelection(filePath);
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(ss, null);
				JOptionPane
						.showMessageDialog(
								frmRadicador,
								"Ruta del archivo de coordenadas de 'Datos de Localización' copiado al portapapeles con éxito.",
								"Radicador EBX",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnLoad = new JButton(BUNDLE.getString("App.btnReload.text")); //$NON-NLS-1$
		btnLoad.setFont(CCXFont);
		btnLoad.addActionListener(new LoadAction());
		frmRadicador.getContentPane().add(txtFldKey);
		frmRadicador.getContentPane().add(btnLoad);
		frmRadicador.getContentPane().addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				txtFldKey.requestFocusInWindow();
			}
		});

	}

	@SuppressWarnings("serial")
	private final class LoadAction extends AbstractAction {

		private LoadAction() {
			super();
		}

		public void actionPerformed(ActionEvent e) {
			try {
				String key = txtFldKey.getText().trim();
				if (key != null && !key.isEmpty()) {
					String codVerificacion = JOptionPane
							.showInputDialog(frmRadicador,
									"Digite el código de verificación asignado por favor");
					frmRadicador.getContentPane().removeAll();
					frmRadicador.getContentPane().add(txtFldKey);
					frmRadicador.getContentPane().add(btnLoad);
					ds.init(key, codVerificacion, frmRadicador);
					wait = new FluentWait<WebDriver>(driver)
							.withTimeout(ds.getTimeout(), TimeUnit.SECONDS)
							.pollingEvery(ds.getPollingTime(),
									TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class);
					driver.get(ds.getSiteURL());
					List<Scenario> scenarios = ds.getScenarios();
					frmRadicador.getContentPane().setLayout(
							new GridLayout(0, scenarios.size() + 2, 0, 0));
					frmRadicador.setBounds(100, 100, 800, 130);
					for (final Scenario scnrio : scenarios) {
						JButton buttonScenario = new JButton(scnrio.getId());
						buttonScenario.setFont(CCXFont);
						buttonScenario.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									scnrio.play(driver, wait);
								} catch (ActorException e1) {
									JOptionPane.showMessageDialog(frmRadicador,
											"Ingrese los datos de forma manual. \nContinuar en:\n"
													+ e1.getMessage(),
											"Radicador EBX",
											JOptionPane.ERROR_MESSAGE);
								}
							}
						});
						frmRadicador.getContentPane().add(buttonScenario);
					}
					frmRadicador.getContentPane().add(btnNewPIN);
					btnNewPIN.setEnabled(true);
					if (ds.getFileNameSolicitud() != null) {
						frmRadicador.getContentPane().add(btnMapSolicitud);
					}
					if (ds.getFileNameLocalizacion() != null) {
						frmRadicador.getContentPane().add(btnMapLocalizacion);
					}
					frmRadicador.getContentPane().validate();
				} else {
					JOptionPane.showMessageDialog(frmRadicador, "Llave vacía",
							"Radicador EBX", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}
}
