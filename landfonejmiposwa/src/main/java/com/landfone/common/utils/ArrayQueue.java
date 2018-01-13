package com.landfone.common.utils;

public class ArrayQueue<E> implements Queue<E> {

	private  E[] data;//volatile

	// 当前队列中元素的大小
	private  int size;

	private  int front;

	private  int rear;

	public ArrayQueue() {
		data = (E[]) new Object[1024*3];
		size = 0;
		front = 0;
		rear = 0;
	}

	public synchronized void add(E target) {
		if (isFull()) {

			enlarge();
			//数组队列满后，需要扩充，记住扩充后要将front的值归0
			front=0;
		}

		rear = (front + size) % data.length;

		data[rear] = target;

		size++;
	}

	public  boolean isEmpty() {

		return size == 0;
	}

	/**
	 * 判断当前队列是否已满
	 *
	 * @return
	 */
	public  boolean isFull() {

		return size == data.length;

	}

	/**
	 * 将数组容量扩大两倍
	 *
	 */
	public  void enlarge() {
		E[] newData = (E[]) new Object[data.length * 2];
		for (int i = 0; i < data.length; i++) {
			newData[i] = data[i];

		}
		data = newData;
		newData = null;

		System.out.println("!!!!!!!!!!!enlarged!!!!!!!!!!!!!!");

	}

	public synchronized E remove() {
		if (isEmpty()) {

			throw new RuntimeException("队列为空!");
		}

		E tempData = data[front];


		data[front] = null;
		front = (front + 1) % (data.length);

		size--;

		return tempData;
	}

	public  int size() {

		return size;
	}

	public synchronized E front() {
		if (isEmpty()) {

			throw new RuntimeException("队列为空!");
		}
		return data[front];
	}

}
