package nu.healthclub.prospect.remote;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import nu.healthclub.prospect.model.Prospect;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class ProspectService {

	public static final String DB_NAME = "healthclubnu";
	public static final String COLLECTION_NAME = "prospects";

	private MongoClient client;
	private Datastore store;

	private String ip;
	private int port;
	private String user;
	private String password;

	public ProspectService() {

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void connect() throws IOException {
		this.client = new MongoClient(new ServerAddress(ip, port));
		this.store = new Morphia().createDatastore(client, DB_NAME);
	}

	public void save(Prospect prospect) {
		if (prospect.getId() != null) {
			delete(prospect);
		}
		this.store.save(prospect);
	}

	public void delete(Prospect prospect) {
		DBCollection collection = client.getDB(DB_NAME).getCollection(Prospect.class.getSimpleName());
		DBObject doc = collection.findOne(new ObjectId(prospect.getId()));
		if (doc != null) {
			collection.remove(doc);
		}
		prospect.setId(null);
	}

	public List<Prospect> list() {
		return this.store.createQuery(Prospect.class).asList();
	}
}
