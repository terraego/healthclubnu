package nu.healthclub.prospect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class LocalProperties {

	public static final String KEY_SERVER_ADDRESS = "server_addr";
	public static final String KEY_SERVER_PORT = "server_port";
	public static final String KEY_SERVER_USER = "server_user";
	public static final String KEY_SERVER_PASS = "server_pass";

	public static final String DEFAULT_VALUE_SERVER_ADDRESS = "176.31.185.109";
	public static final String DEFAULT_VALUE_SERVER_PORT = "27017";
	public static final String DEFAULT_VALUE_SERVER_USER = "user";
	public static final String DEFAULT_VALUE_SERVER_PASS = "pass";

	public static final String ENVIRONMENT_VAR_PROGRAMDATA = "ProgramData";
	public static final String ENVIRONMENT_VAR_ALLUSERS = "ALLUSERSPROFILE";
	public static final String ENVIRONMENT_VAR_APPDATA = "APPDATA";

	public static final String DIRECTORY_NAME = "HealthClubNu";
	public static final String FILE_NAME = "settings.properties";

	private static LocalProperties singleton;

	public static LocalProperties getInstance() {
		if (singleton == null) {
			singleton = new LocalProperties();
		}

		return singleton;
	}

	private Properties properties = new Properties();

	private LocalProperties() {

	}

	public File locatePropertiesFile() throws FileNotFoundException {
		File root = null;

		String programData = System.getenv(ENVIRONMENT_VAR_PROGRAMDATA);
		if (programData != null) {
			root = new File(programData);
		} else {
			String allUsers = System.getenv(ENVIRONMENT_VAR_ALLUSERS);
			String appData = System.getenv(ENVIRONMENT_VAR_APPDATA);
			if (appData != null) {
				appData = appData.substring(appData.lastIndexOf(File.separatorChar));
				root = new File(allUsers, appData);
			}
		}

		File result = null;
		if (root != null) {
			File directory = new File(root, DIRECTORY_NAME);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			result = new File(directory, FILE_NAME);
		}

		return result;
	}

	public void load() throws IOException {
		File file = locatePropertiesFile();
		if (!file.exists()) {
			createDefault();
			return;
		}

		InputStream stream = null;
		try {
			stream = new FileInputStream(locatePropertiesFile());
			this.properties.clear();
			this.properties.load(new FileInputStream(file));
		} catch (IOException e) {
			throw e;
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public void createDefault() throws IOException {
		this.properties.put(KEY_SERVER_ADDRESS, DEFAULT_VALUE_SERVER_ADDRESS);
		this.properties.put(KEY_SERVER_PORT, DEFAULT_VALUE_SERVER_PORT);
		this.properties.put(KEY_SERVER_USER, DEFAULT_VALUE_SERVER_USER);
		this.properties.put(KEY_SERVER_PASS, DEFAULT_VALUE_SERVER_PASS);

		OutputStream stream = null;
		try {
			stream = new FileOutputStream(locatePropertiesFile());
			this.properties.store(stream, "used for the healthclub.nu prospect app");
		} catch (IOException e) {
			throw e;
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public boolean isEmpty() {
		return this.properties.isEmpty();
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
}
