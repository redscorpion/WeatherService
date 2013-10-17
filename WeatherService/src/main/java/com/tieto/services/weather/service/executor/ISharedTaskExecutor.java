package com.tieto.services.weather.service.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public interface ISharedTaskExecutor<K, E> {

	public <C extends Callable<E>> ISharedTask<E> runTask(K key,
			ISharedTaskFactory<C> factory);

	public interface ISharedTask<E> {

		public E waitForResult() throws ExecutionException;

		public boolean cancel(boolean mayInterruptIfRunning);

		public boolean isCancelled();

		public boolean isDone();
	}

	public interface ISharedTaskFactory<C> {
		public C create();
	}
}