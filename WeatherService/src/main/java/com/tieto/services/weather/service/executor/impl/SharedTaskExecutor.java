package com.tieto.services.weather.service.executor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.tieto.services.weather.service.executor.ISharedTaskExecutor;

public class SharedTaskExecutor<K, E> implements ISharedTaskExecutor<K, E> {

	private final ExecutorService executor = Executors.newCachedThreadPool();

	private final Map<K, ISharedTask<E>> runningTasks = new HashMap<>();

	@Override
	public <C extends Callable<E>> ISharedTask<E> runTask(K key,
			ISharedTaskFactory<C> factory) {
		// check running tasks first
		// if task with requested key is not running
		// then execute new task
		synchronized (runningTasks) {
			ISharedTask<E> task = runningTasks.get(key);
			if (task == null) {
				task = new SharedTask(key, executor.submit(factory.create()));
				// add new task to the list of running tasks
				runningTasks.put(key, task);
			}
			return task;
		}
	}

	private class SharedTask implements ISharedTask<E> {

		private final K key;
		private final Future<E> delegate;

		private SharedTask(K key, Future<E> delegate) {
			this.key = key;
			this.delegate = delegate;
		}

		public E waitForResult() throws ExecutionException {
			try {
				for (;;) {
					try {
						return delegate.get();
					} catch (InterruptedException e) {
						// swallow
						if (delegate.isCancelled()) {
							break;
						}
					}
				}
			} finally {
				// remove completed task from list of running tasks
				synchronized (runningTasks) {
					if (runningTasks.containsKey(key)) {
						runningTasks.remove(key);
					}
				}
			}
            return null;
		}

		public boolean cancel(boolean mayInterruptIfRunning) {
			return delegate.cancel(mayInterruptIfRunning);
		}

		public boolean isCancelled() {
			return delegate.isCancelled();
		}

		public boolean isDone() {
			return delegate.isDone();
		}

	}
}
