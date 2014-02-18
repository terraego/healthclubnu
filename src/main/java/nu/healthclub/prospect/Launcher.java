package nu.healthclub.prospect;

import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import nu.healthclub.prospect.Images;
import nu.healthclub.prospect.LocalProperties;
import nu.healthclub.prospect.MessageUtilities;
import nu.healthclub.prospect.controller.ProspectController;
import nu.healthclub.prospect.remote.ProspectService;

public class Launcher {

	static void loadProperties() {
		LocalProperties props = LocalProperties.getInstance();
		try {
			props.load();
		} catch (IOException e) {
			MessageUtilities.showException(null,
			        "Kon de benodigde gegevens om te verbinden met de database niet vinden.", e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	static ProspectService createService() {
		LocalProperties props = LocalProperties.getInstance();
		String serverAddr = props.getProperty(LocalProperties.KEY_SERVER_ADDRESS);
		int port = Integer.parseInt(props.getProperty(LocalProperties.KEY_SERVER_PORT));
		String serverUser = props.getProperty(LocalProperties.KEY_SERVER_USER);
		String serverPass = props.getProperty(LocalProperties.KEY_SERVER_PASS);

		ProspectService service = new ProspectService();
		service.setIp(serverAddr);
		service.setPort(port);
		service.setUser(serverUser);
		service.setPassword(serverPass);

		try {
			service.connect();
		} catch (IOException e) {
			MessageUtilities.showException(null, "Kon niet verbinden met de database", e);
			System.exit(2);
		}

		return service;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// load properties
		loadProperties();

		// create service
		ProspectService service = createService();

		// create application
		ProspectController controller = new ProspectController(service);

		// show application
		JFrame frame = new JFrame("Healthclub nu");
		frame.setIconImages(Arrays.asList(
		        Images.IMAGE_LOGO_SMALL.getImage(),
		        Images.ICON_PROGRAM.getImage()));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(controller.getView());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
