package com.alec.ync.interfaces;
/**
 * �ϡ������ӿ�
 * @author longhf
 * @version 1.0
 */
public interface Pullable {
	/**
	 * ˢ��
	 * @return true false
	 */
	boolean canPullDown();

	/**
	 * 
	 * ����
	 * @return true false
	 */
	boolean canPullUp();

}

