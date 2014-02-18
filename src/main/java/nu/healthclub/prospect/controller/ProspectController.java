package nu.healthclub.prospect.controller;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import nl.caliope.framework.dialog.SaveUpdateDialog;
import nl.caliope.framework.dialog.SaveUpdateDialogBuilder;
import nl.caliope.framework.table.TableController;
import nu.healthclub.prospect.ExcelExporter;
import nu.healthclub.prospect.Images;
import nu.healthclub.prospect.MessageUtilities;
import nu.healthclub.prospect.model.Prospect;
import nu.healthclub.prospect.remote.ProspectService;
import nu.healthclub.prospect.view.ProspectDialogView;
import nu.healthclub.prospect.view.ProspectTableView;
import nu.healthclub.prospect.view.ProspectView;

public class ProspectController {

	public static final Dimension MINIMUM_SIZE = new Dimension(525, 600);

	private TableController<Prospect> tableController;
	private ProspectView view;

	private final ProspectService service;

	public ProspectController(ProspectService service) {
		this.service = service;
		this.tableController = new TableController<Prospect>(new ProspectTableView(), null);

		this.view = new ProspectView(this);

		updateView();
	}

	public ProspectView getView() {
		return view;
	}

	public TableController<Prospect> getTableController() {
		return tableController;
	}

	public void updateView() {
		try {
			tableController.clear();
			tableController.addAll(service.list());
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtilities.showException(view, "Kon de lijst met klanten niet ophalen uit de database.", e);
		}
	}

	public void addProspect() {
		SaveUpdateDialog<Prospect> dialog = new SaveUpdateDialogBuilder<Prospect>()
		        .setView(new ProspectDialogView())
		        .setTitle("Voeg een nieuwe klant toe")
		        .setIcon(Images.ICON_PROGRAM)
		        .setOwner(view)
		        .setMinimumSize(MINIMUM_SIZE)
		        .buildDialog();
		dialog.setVisible(true);

		if (!dialog.isCancelled()) {
			try {
				this.service.save(dialog.getModel());
				this.tableController.add(dialog.getModel());
			} catch (Exception e) {
				e.printStackTrace();
				MessageUtilities.showException(view, "Kon de klant niet toevoegen aan de database.", e);
			}
		}
	}

	public void editSelectedProspect() {
		Prospect selected = this.tableController.getSelectedRow();
		if (selected == null) {
			return;
		}
		SaveUpdateDialog<Prospect> dialog = new SaveUpdateDialogBuilder<Prospect>()
		        .setView(new ProspectDialogView())
		        .setModel(selected)
		        .setTitle("Wijzig een bestaande klant")
		        .setIcon(Images.ICON_PROGRAM)
		        .setOwner(view)
		        .setMinimumSize(MINIMUM_SIZE)
		        .buildDialog();
		dialog.setVisible(true);

		if (!dialog.isCancelled()) {
			try {
				this.service.save(dialog.getModel());
				this.tableController.update(dialog.getModel());
			} catch (Exception e) {
				e.printStackTrace();
				MessageUtilities.showException(view, "Kon de klant niet wijzigen in de database.", e);
			}
		}
	}

	public void deleteSelectedProspect() {
		Prospect selected = this.tableController.getSelectedRow();
		if (selected != null) {
			if (!MessageUtilities.confirm(view,
			        "Weet u zeker dat u klant " + selected.getFirstName() + " " + selected.getLastName()
			                + " uit de database wilt verwijderen?")) {
				return;
			}
			try {
				System.out.println("deleting prospect with id " + selected.getId());
				this.service.delete(selected);
				this.tableController.remove(selected);
			} catch (Exception e) {
				e.printStackTrace();
				MessageUtilities.showException(view, "Kon de klant verwijderen uit de database.", e);
			}
		}
	}

	public void exportProspects() {
		ExcelExporter exporter = new ExcelExporter();

		File file = chooseFile();
		if (file == null) {
			return;
		}

		if (file.exists()) {
			if (!MessageUtilities.confirm(view,
			        "Het bestand dat u gekozen heeft bestaat al, wilt u deze overschrijven?")) {
				return;
			}
		}

		try {
			exporter.export(file, service.list());
		} catch (IOException e) {
			e.printStackTrace();
			MessageUtilities.showException(view, "Er is een fout opgetreden tijdens het exporteren.", e);
		}

	}

	private File chooseFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Excel bestand", "xlsx", "XLSX"));
		int result = chooser.showSaveDialog(view);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getName().endsWith("xlsx")) {
				file = new File(file.getParentFile(), file.getName() + ".xlsx");
			}

			return file;
		}

		return null;
	}
}
