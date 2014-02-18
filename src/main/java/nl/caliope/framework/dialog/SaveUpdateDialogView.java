package nl.caliope.framework.dialog;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class SaveUpdateDialogView<T> extends JPanel
{

	private final Class<T> modelClass;

	private Map<String, Object> properties = new HashMap<String, Object>();
	private T model;

	public SaveUpdateDialogView(Class<T> modelClass)
	{
		this.modelClass = modelClass;
	}

	public T getModel()
	{
		return model;
	}

	public void setModel(T model)
	{
		this.model = model;
	}

	public void setProperties(Map<String, Object> properties)
	{
		this.properties = properties;
	}
	
	public boolean hasProperty(String key) {
		return this.properties.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public <P> P getProperty(String key)
	{
		return (P) this.properties.get(key);
	}

	/**
	 * Get the class of the model that is used by this view. This method is used
	 * to create an instance of the model if no model was set.
	 * 
	 * @return returns the class of the model used by this view
	 */
	public Class<T> getModelClass()
	{
		return modelClass;
	}

	/**
	 * Initializes the dialog, after this method has been called, all fields in
	 * this view have been filled with the data contained in the model that was
	 * set
	 */
	public abstract void initialize();

	/**
	 * Commits all entered data from the fields, to the underlying entity. After
	 * this method is called, all user input has been comitted to the model
	 */
	public abstract void commit();

	/**
	 * Checks if the user input is within valid values
	 * 
	 * @return true if the user input is correct
	 */
	public abstract boolean isValidResult();
}
