package nl.caliope.framework.table;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;
import javax.swing.SwingWorker;

/**
 * 
 * @author mblokker
 */
public abstract class AbstractTableDatasource<T>
{

	private TableController<T> controller;
	private SwingWorker<Void, T> worker;

	public AbstractTableDatasource(TableController<T> controller)
	{
		this.controller = controller;
	}

	public final void cancel()
	{
		if (this.worker == null
				|| this.worker.isCancelled()
				|| this.worker.isDone()) {
			return;
		}

		this.worker.cancel(true);
	}

	public abstract Iterator<T> getIterator();

	public final void update()
	{
		if (this.worker != null) {
			cancel();
		}

		this.controller.clear();

		this.worker = new SwingWorker<Void, T>()
		{

			@Override
			protected Void doInBackground() throws Exception
			{
				Iterator<T> iterator = getIterator();
				try {
					while (iterator.hasNext()) {
						publish(iterator.next());
					}
					return null;
				} finally {
					if (iterator instanceof Closeable) {
						((Closeable) iterator).close();
					}
				}
			}

			@Override
			protected void process(List<T> chunks)
			{
				if (!isCancelled()) {
					controller.addAll(chunks);
				}
			}
		};
		this.worker.execute();
	}
}
