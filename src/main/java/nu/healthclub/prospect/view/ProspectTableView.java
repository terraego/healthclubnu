package nu.healthclub.prospect.view;

import java.util.Date;

import nl.caliope.framework.table.Column;
import nl.caliope.framework.table.TableViewDefinition;
import nu.healthclub.prospect.model.Prospect;

public class ProspectTableView extends TableViewDefinition {

	@Column(key = "firstname", title = "Voornaam", order = 1)
	public String getFirstName(Prospect prospect) {
		return prospect.getFirstName();
	}

	@Column(key = "lastname", title = "Achternaam", order = 2)
	public String getLastName(Prospect prospect) {
		return prospect.getLastName();
	}

	@Column(key = "birthdate", title = "Geboortedatum", order = 3)
	public Date getBirthDate(Prospect prospect) {
		return prospect.getBirthDate();
	}

	@Column(key = "entrydate", title = "Datum langsgeweest", order = 4)
	public Date getEntryDate(Prospect prospect) {
		return prospect.getEntryDate();
	}
}
