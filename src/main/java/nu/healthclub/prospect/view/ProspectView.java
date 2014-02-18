package nu.healthclub.prospect.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import nu.healthclub.prospect.Images;
import nu.healthclub.prospect.controller.ProspectController;

@SuppressWarnings("serial")
public class ProspectView extends JPanel implements ActionListener {

	private ProspectController controller;

	private JTable table;
	private JButton btnAddProspect;
	private JButton btnEditProspect;
	private JButton btnDeleteProspect;
	private JButton btnExport;

	public ProspectView(ProspectController controller) {
		this.controller = controller;
		initComponents();
	}

	private void initComponents() {
		this.table = new JTable();
		this.table.setAutoCreateRowSorter(true);
		this.controller.getTableController().setup(table);
		this.btnAddProspect = new JButton("Voeg klant toe");
		this.btnAddProspect.addActionListener(this);
		this.btnEditProspect = new JButton("Wijzig klant");
		this.btnEditProspect.addActionListener(this);
		this.btnDeleteProspect = new JButton("Verwijder klant");
		this.btnDeleteProspect.addActionListener(this);
		this.btnExport = new JButton("Exporteer gegevens");
		this.btnExport.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new MigLayout("", "[][][][grow][]", "[23px]"));
		buttonPanel.add(btnAddProspect, "cell 0 0,alignx left,aligny top");
		buttonPanel.add(btnEditProspect, "cell 1 0");
		buttonPanel.add(btnDeleteProspect, "cell 2 0");
		buttonPanel.add(btnExport, "cell 4 0");

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(Color.BLACK);
		panelHeader.add(new JLabel(Images.IMAGE_LOGO));

		JPanel panelCenter = new JPanel(new BorderLayout(0, 0));
		panelCenter.add(buttonPanel, BorderLayout.NORTH);
		panelCenter.add(new JScrollPane(table), BorderLayout.CENTER);

		setLayout(new BorderLayout(0, 0));
		add(panelHeader, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddProspect) {
			this.controller.addProspect();
		} else if (e.getSource() == btnEditProspect) {
			this.controller.editSelectedProspect();
		} else if (e.getSource() == btnDeleteProspect) {
			this.controller.deleteSelectedProspect();
		} else if (e.getSource() == btnExport) {
			this.controller.exportProspects();
		}
	}
}
