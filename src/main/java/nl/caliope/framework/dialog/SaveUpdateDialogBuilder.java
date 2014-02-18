package nl.caliope.framework.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import nu.healthclub.prospect.Images;

public class SaveUpdateDialogBuilder<T>
{
	private String title;
	private ImageIcon icon;
	private T model;
	private Component owner;
	private Dimension minimumSize;
	private boolean modal = true;
	private SaveUpdateDialogView<T> view;
	private Map<String, Object> properties = new HashMap<String, Object>();
	private List<DialogCommand<T>> finishCommands = new ArrayList<DialogCommand<T>>();
	private List<DialogCommand<T>> cancelCommands = new ArrayList<DialogCommand<T>>();

	public SaveUpdateDialogBuilder<T> setTitle(String title)
	{
		this.title = title;
		return this;
	}

	public SaveUpdateDialogBuilder<T> setModel(T model)
	{
		this.model = model;
		return this;
	}

	public SaveUpdateDialogBuilder<T> setIcon(ImageIcon icon)
	{
		this.icon = icon;
		return this;
	}

	public SaveUpdateDialogBuilder<T> setModal(boolean modal)
	{
		this.modal = modal;
		return this;
	}

	public SaveUpdateDialogBuilder<T> setOwner(Component owner)
	{
		this.owner = owner;
		return this;
	}

	public SaveUpdateDialogBuilder<T> setMinimumSize(Dimension minimumSize)
	{
		this.minimumSize = minimumSize;
		return this;
	}

	public SaveUpdateDialogBuilder<T> setView(SaveUpdateDialogView<T> view)
	{
		this.view = view;
		return this;
	}

	public SaveUpdateDialogBuilder<T> setProperty(String property, Object value)
	{
		this.properties.put(property, value);
		return this;
	}

	public SaveUpdateDialogBuilder<T> addFinishCommand(DialogCommand<T> command)
	{
		this.finishCommands.add(command);
		return this;
	}

	public SaveUpdateDialogBuilder<T> addCancelCommand(DialogCommand<T> command)
	{
		this.cancelCommands.add(command);
		return this;
	}

	public SaveUpdateDialog<T> buildDialog()
	{
		prepareView();

		SaveUpdateDialog<T> dialog = new SaveUpdateDialog<T>(view);
		initKeyBindings(dialog);
		initContentPane(dialog);

		if (title != null)
			dialog.setTitle(title);
		if (icon != null)
			dialog.setIconImage(icon.getImage());

		if (minimumSize != null) {
			dialog.setMinimumSize(minimumSize);
		} else {
			dialog.pack();
		}

		dialog.setModal(modal);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new CloseListener(dialog));
		dialog.setLocationRelativeTo(owner);

		return dialog;
	}

	private void prepareView()
	{
		if (this.view == null) {
			throw new RuntimeException("View is not set!");
		}

		Class<T> modelClass = this.view.getModelClass();
		T model = this.model;
		if (model == null) {
			try {
				model = modelClass.newInstance();
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException("failed to instantiate model", e);
			}
		}

		this.view.setModel(model);
		this.view.setProperties(properties);
		this.view.initialize();
	}

	private void initKeyBindings(SaveUpdateDialog<T> dialog)
	{
		JRootPane rootPane = dialog.getRootPane();
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap actionMap = rootPane.getActionMap();

		// and click on the okay button when Enter is pressed
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ok");

		// Close the dialog when Esc is pressed
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");

		actionMap.put("ok", new OkayAction(dialog));
		actionMap.put("cancel", new CancelAction(dialog));
	}

	private void initContentPane(SaveUpdateDialog<T> dialog)
	{
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.TRAILING));
		buttonPane.add(new JButton(new OkayAction(dialog)));
		buttonPane.add(new JButton(new CancelAction(dialog)));

		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.add(view, BorderLayout.CENTER);
		contentPanel.add(buttonPane, BorderLayout.SOUTH);

		dialog.setContentPane(contentPanel);
	}

	private boolean executeFinishCommands(SaveUpdateDialog<T> dialog)
	{
		for (DialogCommand<T> command : this.finishCommands) {
			if (!command.execute(dialog)) {
				return false;
			}
		}

		return true;
	}

	private boolean executeCancelCommands(SaveUpdateDialog<T> dialog)
	{
		for (DialogCommand<T> command : this.cancelCommands) {
			if (!command.execute(dialog)) {
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("serial")
	protected class OkayAction extends AbstractAction
	{

		private SaveUpdateDialog<T> dialog;

		public OkayAction(SaveUpdateDialog<T> dialog)
		{
			super("Ok", Images.ICON_APPROVE_SMALL);
			this.dialog = dialog;
		}

		public void actionPerformed(ActionEvent e)
		{
			SaveUpdateDialogView<T> view = this.dialog.getView();
			view.commit();
			if (!view.isValidResult() || !executeFinishCommands(dialog)) {
				return;
			}

			this.dialog.approve();
		}
	}

	@SuppressWarnings("serial")
	protected class CancelAction extends AbstractAction
	{

		private SaveUpdateDialog<T> dialog;

		public CancelAction(SaveUpdateDialog<T> dialog)
		{
			super("Cancel", Images.ICON_CANCEL_SMALL);
			this.dialog = dialog;
		}

		public void actionPerformed(ActionEvent e)
		{
			if (!executeCancelCommands(dialog)) {
				return;
			}
			this.dialog.cancel();
		}
	}

	protected class CloseListener extends WindowAdapter
	{

		private SaveUpdateDialog<T> dialog;

		public CloseListener(SaveUpdateDialog<T> dialog)
		{
			this.dialog = dialog;
		}

		@Override
		public void windowClosing(WindowEvent e)
		{
			this.dialog.cancel();
		}
	}
}
