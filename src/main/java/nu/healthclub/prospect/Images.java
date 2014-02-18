package nu.healthclub.prospect;

import java.net.URL;

import javax.swing.ImageIcon;

public class Images {

	public static final ImageIcon ICON_APPROVE_SMALL = safeLoad("icons/16x16/Approve.png");
	public static final ImageIcon ICON_CANCEL_SMALL = safeLoad("icons/16x16/Cancel.png");
	public static final ImageIcon ICON_PROGRAM = safeLoad("icons/16x16/Icon.png");

	public static final ImageIcon IMAGE_LOGO_SMALL = safeLoad("images/logo_small.png");
	public static final ImageIcon IMAGE_LOGO = safeLoad("images/logo.png");

	public static ImageIcon safeLoad(String resourcename)
	{
		try {
			ClassLoader loader = Images.class.getClassLoader();
			URL resource = loader.getResource(resourcename);
			return new ImageIcon(resource);
		} catch (Throwable ball) {
			System.err.println("Failed to load image " + resourcename);
			return null;
		}
	}
}
