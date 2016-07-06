package com.alec.ync.interfaces;
/**
 * 上、下拉接口
 * @author longhf
 * @version 1.0
 */
public interface Pullable {
	/**
	 * 刷新
	 * @return true false
	 */
	boolean canPullDown();

	/**
	 * 
	 * 加载
	 * @return true false
	 */
	boolean canPullUp();

}

