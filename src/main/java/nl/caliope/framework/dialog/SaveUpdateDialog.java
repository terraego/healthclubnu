package nl.caliope.framework.dialog;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class SaveUpdateDialog<T> extends JDialog
{

	private final SaveUpdateDialogView<T> view;
	private boolean cancelled;

	public SaveUpdateDialog(SaveUpdateDialogView<T> view)
	{
		this.view = view;
	}

	public SaveUpdateDialogView<T> getView()
	{
		return view;
	}

	public void cancel()
	{
		this.cancelled = true;
		dispose();
	}

	public boolean isCancelled()
	{
		return cancelled;
	}

	public void approve()
	{
		this.cancelled = false;
		dispose();
	}

	/**
	 * utility method. gets the model, from the underlying view
	 * 
	 * @return
	 */
	public T getModel()
	{
		return view.getModel();
	}

	/**
	 * utility method. sets the model, for the underlying view
	 * 
	 * @return
	 */
	public void setModel(T model)
	{
		this.view.setModel(model);
	}

}
