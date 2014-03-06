package nu.healthclub.prospect.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;
import nl.caliope.framework.dialog.SaveUpdateDialogView;
import nu.healthclub.prospect.model.Prospect;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class ProspectDialogView extends SaveUpdateDialogView<Prospect> implements ActionListener {

	// demographics
	private JTextField txtAdvisorName;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtAddress;
	private JTextField txtPostalCode;
	private JTextField txtCity;
	private JTextField txtEmail;
	private JTextField txtPhoneHome;
	private JTextField txtPhoneMobile;
	private JTextField txtPhoneWork;
	private JTextField txtCurrentSport;
	private JTextField txtPastSport;

	// reference
	private JRadioButton rdbtnViaVia;
	private JRadioButton rdbtnInternet;
	private JRadioButton rdbtnClublid;
	private JRadioButton rdbtnFolder;
	private JRadioButton rdbtnAdvertentie;
	private JRadioButton rdbtnAnders;

	// general
	private JRadioButton rdbtnCurrentlySportingYes;
	private JRadioButton rdbtnCurrentlySportingNo;
	private JRadioButton rdbtnPastSportingYes;
	private JRadioButton rdbtnPastSportingNo;

	//
	private JTextPane txtMotivation;
	private JTextPane txtConversionMotivation;

	private JDateChooser txtDateEntry;
	private JDateChooser txtDateBirth;
	private JRadioButton rdbtnMemberYes;
	private JRadioButton rdbtnMemberNo;
	private JRadioButton rdbtnReceiveNewsletterYes;
	private JRadioButton rdbtnReceiveNewsletterNo;

	/**
	 * Create the panel.
	 */
	public ProspectDialogView() {
		super(Prospect.class);

		initComponents();
		initPanel();
	}

	private void initComponents() {
		this.txtAdvisorName = new JTextField();
		this.txtFirstName = new JTextField();
		this.txtLastName = new JTextField();
		this.txtEmail = new JTextField();
		this.txtAddress = new JTextField();
		this.txtPhoneHome = new JTextField();
		this.txtPostalCode = new JTextField();
		this.txtPhoneMobile = new JTextField();
		this.txtCity = new JTextField();
		this.txtPhoneWork = new JTextField();
		this.txtCurrentSport = new JTextField();
		this.txtPastSport = new JTextField();

		this.txtMotivation = new JTextPane();
		this.txtConversionMotivation = new JTextPane();

		this.txtDateEntry = new JDateChooser();
		this.txtDateEntry.setDateFormatString("dd-MM-yyyy");
		this.txtDateBirth = new JDateChooser();
		this.txtDateBirth.setDateFormatString("dd-MM-yyyy");

		ButtonGroup btnGrpReference = new ButtonGroup();
		btnGrpReference.add(this.rdbtnViaVia = new JRadioButton("Via via"));
		btnGrpReference.add(this.rdbtnInternet = new JRadioButton("Internet"));
		btnGrpReference.add(this.rdbtnClublid = new JRadioButton("Clublid"));
		btnGrpReference.add(this.rdbtnFolder = new JRadioButton("Folder"));
		btnGrpReference.add(this.rdbtnAdvertentie = new JRadioButton("Advertentie"));
		btnGrpReference.add(this.rdbtnAnders = new JRadioButton("Anders"));

		ButtonGroup btnGrpCurrentlySporting = new ButtonGroup();
		btnGrpCurrentlySporting.add(this.rdbtnCurrentlySportingYes = new JRadioButton("Ja"));
		btnGrpCurrentlySporting.add(this.rdbtnCurrentlySportingNo = new JRadioButton("Nee"));

		ButtonGroup btnGrpPastSporting = new ButtonGroup();
		btnGrpPastSporting.add(this.rdbtnPastSportingYes = new JRadioButton("Ja"));
		btnGrpPastSporting.add(this.rdbtnPastSportingNo = new JRadioButton("Nee"));

		ButtonGroup btnGrpMember = new ButtonGroup();
		btnGrpMember.add(this.rdbtnMemberYes = new JRadioButton("Ja"));
		btnGrpMember.add(this.rdbtnMemberNo = new JRadioButton("Nee"));

		ButtonGroup btnGrpReceiveNewsLetter = new ButtonGroup();
		btnGrpReceiveNewsLetter.add(this.rdbtnReceiveNewsletterYes = new JRadioButton("Ja"));
		btnGrpReceiveNewsLetter.add(this.rdbtnReceiveNewsletterNo = new JRadioButton("Nee"));

		JPanel panelHeader = new JPanel(new MigLayout("", "[][][][grow]", "[]"));
		panelHeader.add(new JLabel("Datum:"), "cell 0 0");
		panelHeader.add(txtDateEntry, "cell 1 0");
		panelHeader.add(new JLabel("Naam sport adviseur"), "cell 2 0,alignx trailing");
		panelHeader.add(txtAdvisorName, "cell 3 0,growx");

		JPanel panelGeneral = new JPanel();
		panelGeneral.setFocusCycleRoot(true);
		panelGeneral.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
		        txtFirstName, txtLastName, txtAddress, txtPostalCode, txtCity, txtDateBirth,
		        txtDateBirth.getCalendarButton(), txtEmail, txtPhoneHome, txtPhoneMobile, txtPhoneWork }));
		panelGeneral.setBorder(BorderFactory.createTitledBorder("Algemeen"));
		panelGeneral.setLayout(new MigLayout("", "[][100px:n,grow][][100px:n,grow]", "[][][][][]"));
		panelGeneral.add(new JLabel("Voornaam:"), "cell 0 0,alignx trailing");
		panelGeneral.add(txtFirstName, "cell 1 0,growx");
		panelGeneral.add(new JLabel("Geboortedatum:"), "cell 2 0");
		panelGeneral.add(txtDateBirth, "cell 3 0, growx");
		panelGeneral.add(new JLabel("Achternaam:"), "cell 0 1,alignx trailing");
		panelGeneral.add(txtLastName, "cell 1 1,growx");
		panelGeneral.add(new JLabel("Email adres:"), "cell 2 1,alignx trailing");
		panelGeneral.add(txtEmail, "cell 3 1,growx,aligny top");
		panelGeneral.add(new JLabel("Adres:"), "cell 0 2,alignx trailing");
		panelGeneral.add(txtAddress, "cell 1 2,growx");
		panelGeneral.add(new JLabel("Telefoon thuis:"), "cell 2 2,alignx trailing");
		panelGeneral.add(txtPhoneHome, "cell 3 2,growx");
		panelGeneral.add(new JLabel("Postcode:"), "cell 0 3,alignx trailing");
		panelGeneral.add(txtPostalCode, "cell 1 3,growx");
		panelGeneral.add(new JLabel("Telefoon mobiel:"), "cell 2 3,alignx trailing");
		panelGeneral.add(txtPhoneMobile, "cell 3 3,growx");
		panelGeneral.add(new JLabel("Plaats:"), "cell 0 4,alignx trailing");
		panelGeneral.add(txtCity, "cell 1 4,growx");
		panelGeneral.add(new JLabel("Telefoon werk:"), "cell 2 4,alignx trailing");
		panelGeneral.add(txtPhoneWork, "cell 3 4,growx");

		JPanel panelMotivation = new JPanel(new MigLayout("", "[][grow]", "[][][70px:n,grow][][]"));
		panelMotivation.setBorder(BorderFactory.createTitledBorder("Motivatie"));
		panelMotivation.add(new JLabel("Hoe heeft u over ons gehoord:"), "cell 0 0");
		panelMotivation.add(rdbtnViaVia, "flowx,cell 1 0");
		panelMotivation.add(rdbtnInternet, "flowx,cell 1 1");
		panelMotivation.add(rdbtnClublid, "cell 1 0");
		panelMotivation.add(rdbtnFolder, "cell 1 0");
		panelMotivation.add(rdbtnAdvertentie, "cell 1 0");
		panelMotivation.add(rdbtnAnders, "cell 1 1");
		panelMotivation.add(new JLabel("Wat motiveerde u om naar de club te komen:"), "cell 0 2");
		panelMotivation.add(new JScrollPane(txtMotivation), "cell 1 2,grow");
		panelMotivation.add(new JLabel("Sport u op dit moment:"), "cell 0 3");
		panelMotivation.add(rdbtnCurrentlySportingYes, "flowx,cell 1 3");
		panelMotivation.add(rdbtnCurrentlySportingNo, "cell 1 3");
		panelMotivation.add(new JLabel("Heeft u in het verleden gesport"), "cell 0 4");
		panelMotivation.add(rdbtnPastSportingYes, "flowx,cell 1 4");
		panelMotivation.add(rdbtnPastSportingNo, "cell 1 4");
		panelMotivation.add(txtCurrentSport, "cell 1 3,growx");
		panelMotivation.add(txtPastSport, "cell 1 4,growx");

		JPanel panelConversion = new JPanel(new MigLayout("", "[][grow]", "[][][70px:n,grow]"));
		panelConversion.setBorder(BorderFactory.createTitledBorder("Overig"));
		panelConversion.add(new JLabel("Lid geworden:"), "cell 0 0");
		panelConversion.add(rdbtnMemberYes, "flowx,cell 1 0");
		panelConversion.add(new JLabel("Wilt nieuwsbrief ontvangen:"), "cell 0 1");
		panelConversion.add(rdbtnReceiveNewsletterYes, "flowx,cell 1 1");
		panelConversion.add(new JLabel("Opmerkingen (waarom wel of geen lid):"), "cell 0 2");
		panelConversion.add(new JScrollPane(txtConversionMotivation), "cell 1 2,grow");
		panelConversion.add(rdbtnMemberNo, "cell 1 0");
		panelConversion.add(rdbtnReceiveNewsletterNo, "cell 1 1");

		setLayout(new MigLayout("", "[grow]", "[][][][grow][]"));
		add(panelHeader, "cell 0 0,grow");
		add(panelGeneral, "cell 0 1,grow");
		add(panelMotivation, "cell 0 2,grow");
		add(panelConversion, "cell 0 3,grow");

	}

	private void initPanel() {
		this.rdbtnCurrentlySportingYes.addActionListener(this);
		this.rdbtnCurrentlySportingNo.addActionListener(this);
		this.txtCurrentSport.setEnabled(false);

		this.rdbtnPastSportingYes.addActionListener(this);
		this.rdbtnPastSportingNo.addActionListener(this);
		this.txtPastSport.setEnabled(false);
	}

	@Override
	public void initialize() {
		this.txtAdvisorName.setText(getModel().getAdvisor());
		this.txtFirstName.setText(getModel().getFirstName());
		this.txtLastName.setText(getModel().getLastName());
		this.txtAddress.setText(getModel().getAddress());
		this.txtPostalCode.setText(getModel().getPostalCode());
		this.txtCity.setText(getModel().getCity());
		this.txtEmail.setText(getModel().getEmail());
		this.txtPhoneHome.setText(getModel().getPhoneHome());
		this.txtPhoneMobile.setText(getModel().getPhoneMobile());
		this.txtPhoneWork.setText(getModel().getPhoneWork());

		String currentSport = getModel().getCurrentSport();
		if (currentSport == null) {
			this.rdbtnCurrentlySportingNo.setSelected(true);
		} else {
			this.rdbtnCurrentlySportingYes.setSelected(true);
			this.txtCurrentSport.setEnabled(true);
			this.txtCurrentSport.setText(currentSport);
		}

		String pastSport = getModel().getPastSport();
		if (pastSport == null) {
			this.rdbtnPastSportingNo.setSelected(true);
		} else {
			this.rdbtnPastSportingYes.setSelected(true);
			this.txtPastSport.setEnabled(true);
			this.txtPastSport.setText(pastSport);
		}

		if (getModel().isMember()) {
			this.rdbtnMemberYes.setSelected(true);
			this.rdbtnMemberNo.setSelected(false);
		} else {
			this.rdbtnMemberYes.setSelected(false);
			this.rdbtnMemberNo.setSelected(true);
		}

		if (getModel().isReceivingNewsLetter()) {
			this.rdbtnReceiveNewsletterYes.setSelected(true);
			this.rdbtnReceiveNewsletterNo.setSelected(false);
		} else {
			this.rdbtnReceiveNewsletterYes.setSelected(false);
			this.rdbtnReceiveNewsletterNo.setSelected(true);
		}

		String ref = getModel().getReference();
		if ("viavia".equals(ref)) {
			this.rdbtnViaVia.setSelected(true);
		} else if ("internet".equals(ref)) {
			this.rdbtnInternet.setSelected(true);
		} else if ("clublid".equals(ref)) {
			this.rdbtnClublid.setSelected(true);
		} else if ("folder".equals(ref)) {
			this.rdbtnFolder.setSelected(true);
		} else if ("advertentie".equals(ref)) {
			this.rdbtnAdvertentie.setSelected(true);
		} else if ("anders".equals(ref)) {
			this.rdbtnAnders.setSelected(true);
		}

		this.txtMotivation.setText(getModel().getMotivation());
		this.txtConversionMotivation.setText(getModel().getRemark());

		this.txtDateEntry.setDate(getModel().getEntryDate());
		this.txtDateBirth.setDate(getModel().getBirthDate());
	}

	@Override
	public void commit() {
		getModel().setAdvisor(txtAdvisorName.getText());
		getModel().setFirstName(txtFirstName.getText());
		getModel().setLastName(txtLastName.getText());
		getModel().setAddress(txtAddress.getText());
		getModel().setPostalCode(txtPostalCode.getText());

		getModel().setCity(txtCity.getText());
		getModel().setEmail(txtEmail.getText());
		getModel().setPhoneHome(txtPhoneHome.getText());
		getModel().setPhoneWork(txtPhoneWork.getText());
		getModel().setPhoneMobile(txtPhoneMobile.getText());

		if (rdbtnCurrentlySportingNo.isSelected()) {
			getModel().setCurrentSport(null);
		} else {
			getModel().setCurrentSport(txtCurrentSport.getText());
		}

		if (rdbtnPastSportingNo.isSelected()) {
			getModel().setPastSport(null);
		} else {
			getModel().setPastSport(txtPastSport.getText());
		}

		if (rdbtnViaVia.isSelected()) {
			getModel().setReference("viavia");
		} else if (rdbtnInternet.isSelected()) {
			getModel().setReference("internet");
		} else if (rdbtnClublid.isSelected()) {
			getModel().setReference("clublid");
		} else if (rdbtnFolder.isSelected()) {
			getModel().setReference("folder");
		} else if (rdbtnAdvertentie.isSelected()) {
			getModel().setReference("advertentie");
		} else if (rdbtnAnders.isSelected()) {
			getModel().setReference("anders");
		}

		getModel().setMotivation(txtMotivation.getText());
		getModel().setRemark(txtConversionMotivation.getText());
		getModel().setMember(rdbtnMemberYes.isSelected());
		getModel().setReceivingNewsLetter(rdbtnReceiveNewsletterYes.isSelected());
		getModel().setEntryDate(txtDateEntry.getDate());
		getModel().setBirthDate(txtDateBirth.getDate());
	}

	@Override
	public boolean isValidResult() {
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		this.txtCurrentSport.setEnabled(rdbtnCurrentlySportingYes.isSelected());
		this.txtPastSport.setEnabled(rdbtnPastSportingYes.isSelected());
	}

	protected class FocusTraversalOnArray extends FocusTraversalPolicy {

		private final Component m_Components[];

		public FocusTraversalOnArray(Component components[]) {
			m_Components = components;
		}

		private int indexCycle(int index, int delta) {
			int size = m_Components.length;
			int next = (index + delta + size) % size;
			return next;
		}

		private Component cycle(Component currentComponent, int delta) {
			int index = -1;
			loop: for (int i = 0; i < m_Components.length; i++) {
				Component component = m_Components[i];
				for (Component c = currentComponent; c != null; c = c.getParent()) {
					if (component == c) {
						index = i;
						break loop;
					}
				}
			}

			// try to find enabled component in "delta" direction
			int initialIndex = index;
			while (true) {
				int newIndex = indexCycle(index, delta);
				if (newIndex == initialIndex) {
					break;
				}
				index = newIndex;
				//
				Component component = m_Components[newIndex];
				if (component.isEnabled() && component.isVisible() && component.isFocusable()) {
					return component;
				}
			}
			// not found
			return currentComponent;
		}

		public Component getComponentAfter(Container container, Component component) {
			return cycle(component, 1);
		}

		public Component getComponentBefore(Container container, Component component) {
			return cycle(component, -1);
		}

		public Component getFirstComponent(Container container) {
			return m_Components[0];
		}

		public Component getLastComponent(Container container) {
			return m_Components[m_Components.length - 1];
		}

		public Component getDefaultComponent(Container container) {
			return getFirstComponent(container);
		}
	}

}
