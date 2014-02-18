package nu.healthclub.prospect.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import nu.healthclub.prospect.MessageUtilities;
import nu.healthclub.prospect.controller.AboutController;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class AboutView extends JPanel implements ActionListener {

	private final AboutController controller;
	private JTextField txtAddress;
	private JTextField txtPort;
	private JButton btnCancel;
	private JButton btnSave;

	/**
	 * Create the panel.
	 */
	public AboutView(AboutController controller) {
		this.controller = controller;

		initComponents();
		initPanel();
	}

	private void initComponents() {
		this.txtAddress = new JTextField();
		this.txtPort = new JTextField();
		this.btnCancel = new JButton("Annuleren");
		this.btnCancel.addActionListener(this);
		this.btnSave = new JButton("Opslaan");
		this.btnSave.addActionListener(this);

		setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		add(new JLabel("Deze applicatie is ontworpen door Maarten Blokker"), "cell 0 0 2 1");
		add(new JLabel("0651542113"), "cell 0 1");
		add(new JLabel("Terraego@gmail.com"), "cell 1 1");
		add(new JLabel("Database adres:"), "cell 0 2");
		add(txtAddress, "cell 1 2,growx");
		add(new JLabel("Database port:"), "cell 0 3");
		add(txtPort, "cell 1 3,growx");
		add(btnSave, "cell 1 4,alignx trailing");
		add(btnCancel, "flowx,cell 1 4,alignx trailing");
	}

	private void initPanel() {
		this.txtAddress.setText(controller.getServerAddress());
		this.txtPort.setText(controller.getServerPort());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			this.controller.disposeView();
		} else if (e.getSource() == btnSave) {
			String address = this.txtAddress.getText().trim();
			int port;
			try {
				port = Integer.parseInt(this.txtPort.getText().trim());
			} catch (NumberFormatException ex) {
				MessageUtilities.showException(this,
				        "Voer alstjeblieft een nummer in bij het port veld", ex);
				return;
			}

			try {
				controller.saveDBConfiguration(address, port);
				MessageUtilities.showMessage(this,
				        "Succes!", "Database gegevens zijn opgeslagen.\n" +
				                "De nieuwe instellingen zijn van kracht als de applicatie opnieuw opgestart is");
				controller.disposeView();
			} catch (IOException ex) {
				MessageUtilities.showException(this, "Kon database gegevens niet opslaan", ex);
			}
		}
	}
}
