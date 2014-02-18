package nl.caliope.framework.dialog;

public interface DialogCommand<T>
{
	/**
	 * Executes a command for a dialog. These commands are used before a dialog
	 * is cancelled, or finished. If the command returns false, the action of
	 * cancelling or finishing the dialog is stopped.
	 * 
	 * @param dialog
	 *            the dialog that wants to execute this command
	 * @return return false if the command did not execute properly
	 */
	public boolean execute(SaveUpdateDialog<T> dialog);
}
