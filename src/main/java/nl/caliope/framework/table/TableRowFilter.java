package nl.caliope.framework.table;

public interface TableRowFilter<T>
{
	public boolean accept(T row);
}
