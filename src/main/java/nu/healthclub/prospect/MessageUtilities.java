package nu.healthclub.prospect;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MessageUtilities {

	public static void showMessage(Component owner, String title, String message) {
		JOptionPane.showMessageDialog(owner, message, title, JOptionPane.PLAIN_MESSAGE);
	}

	public static void showException(Component owner, String message, Throwable ball) {
		String content = message + "\n" + ball.getLocalizedMessage();
		JOptionPane.showMessageDialog(owner, content, "Foutmelding!", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean confirm(Component owner, String message) {
		return JOptionPane.showConfirmDialog(owner, message, "Bevestig aub", JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION;
	}
}
