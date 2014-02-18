package nl.caliope.framework.table;

import java.util.ArrayList;
import java.util.List;

public class CompositeTableRowFilter<T> implements TableRowFilter<T>
{

	private final List<TableRowFilter<T>> filters;

	public CompositeTableRowFilter()
	{
		this.filters = new ArrayList<TableRowFilter<T>>();
	}

	public boolean accept(T row)
	{
		for (TableRowFilter<T> filter : new ArrayList<TableRowFilter<T>>(this.filters)) {
			if (!filter.accept(row)) {
				return false;
			}
		}

		return true;
	}

	public boolean addFilter(TableRowFilter<T> filter)
	{
		return this.filters.add(filter);
	}

	public boolean hasFilters()
	{
		return !this.filters.isEmpty();
	}

	public boolean removeFilter(TableRowFilter<T> filter)
	{
		return this.filters.remove(filter);
	}

}
