package com.memorycat.notifier.mtp.core.entity.message;

/**
 * 消息重要程度
 * 
 * @author xie
 *
 */
public enum Criticality {

	/**
	 * 
	 * 轻微
	 */
	SLIGHT,
	/**
	 * 普通
	 */
	COMMON,
	/**
	 * 重要
	 */
	IMPORTANT,
	/**
	 * 非常重要
	 */
	VERYIMPORTANT,
	/**
	 * 危急
	 */
	CRITICAL
}
