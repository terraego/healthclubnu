package nl.caliope.framework.table.renderers;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ImageIconRenderer extends DefaultTableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column)
	{
		JLabel label = (JLabel) super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column);

		ImageIcon icon = null;
		if (value instanceof ImageIcon) {
			icon = (ImageIcon) value;
		}

		label.setText("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(icon);

		return label;
	}
}
