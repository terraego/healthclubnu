package nu.healthclub.prospect.controller;

import java.awt.Component;
import java.awt.Window;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import nu.healthclub.prospect.LocalProperties;
import nu.healthclub.prospect.view.AboutView;

public class AboutController {

	private JDialog dialog;
	private AboutView view;

	public AboutController() {
		this.view = new AboutView(this);
	}

	public void saveDBConfiguration(String address, int port) throws IOException {
		LocalProperties.getInstance().putProperty(LocalProperties.KEY_SERVER_ADDRESS, address);
		LocalProperties.getInstance().putProperty(LocalProperties.KEY_SERVER_PORT, String.valueOf(port));
		LocalProperties.getInstance().save();
	}

	public String getServerAddress() {
		return LocalProperties.getInstance().getProperty(LocalProperties.KEY_SERVER_ADDRESS);
	}

	public String getServerPort() {
		return LocalProperties.getInstance().getProperty(LocalProperties.KEY_SERVER_PORT);
	}

	public void showView(Component owner) {
		Window window = SwingUtilities.windowForComponent(owner);
		if (dialog == null && window != null) {
			this.dialog = new JDialog(window);
			this.dialog.setModal(true);
			this.dialog.setContentPane(view);
			this.dialog.pack();
			this.dialog.setLocationRelativeTo(window);
			this.dialog.setVisible(true);
		}
	}

	public void disposeView() {
		if (this.dialog != null) {
			this.dialog.dispose();
			this.dialog = null;
		}
	}
}
