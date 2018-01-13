package com.landfone.common.utils;

/**
 * 队列FIFO的接口
 *
 * @author 鼎鼎
 *
 * @param <E>
 */
public interface Queue<E> {

	/**
	 * 入队: 从队尾加入一元素
	 *
	 * @param target
	 */
	public void add(E target);

	/**
	 * 出队: 移走队头元素
	 *
	 * @param target
	 * @return 当前队头元素
	 */
	public E remove();

	/**
	 * 当前队列中的元素个数
	 */
	public int size();

	/**
	 * 判断当前队列是否为空
	 *
	 * @return
	 */

	public boolean isEmpty();
	/**
	 * 只是返回队头元素
	 * @return
	 */
	public E front();

}
