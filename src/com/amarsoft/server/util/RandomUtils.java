package com.amarsoft.server.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: ����������Ĺ����ࡣ
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 
 * @version 1.0
 */

public class RandomUtils {
	private static final Random random = new Random();

	private RandomUtils() {
		// random = new Random();
	}

	/**
	 * ����num������Ϊlength���ַ������ַ���������ͬ��,�ַ���ֻ������ĸ
	 * @param length
	 *            �ַ����ĳ���
	 * @param num
	 *            �ַ����ĸ���
	 * @return
	 */
	public static String[] random(final int length, final int num) {
		return new RandomUtils().buildRandom(length, num);
	}

	/**
	 * ���ɳ���Ϊlength���ַ���,�ַ���ֻ��������
	 * 
	 * @param length
	 *            �ַ����ĳ���
	 * @return
	 */
	public static String random(final int length) {
		return new RandomUtils().buildRandom(length);
	}

	/**
	 * ����num������Ϊlength���ַ���������� 123-123-123 ��ʽ(ֻ��������)
	 * 
	 * @param length
	 * @param num
	 * @return
	 * @throws BaseException
	 * 
	 * @author �ź���
	 */
	public static String randombunch(int length, int num) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < num; i++) {
			str.append(RandomUtils.random(length));
			if (i != num - 1)
				str.append("-");
		}
		return str.toString();
	}

	/**
	 * ����num������Ϊlength���ַ������ַ���������ͬ��,�ַ���ֻ������ĸ
	 * 
	 * @param length
	 *            �ַ����ĳ���
	 * @param num
	 *            �ַ����ĸ���
	 * @return
	 */
	private String[] buildRandom(final int length, final int num) {
		if (num < 1 || length < 1) {
			return null;
		}
		Set<String> tempRets = new HashSet<String>(num); // �����ʱ������Ա����ظ�ֵ�ķ���

		// ����num������ͬ���ַ���
		while (tempRets.size() < num) {
			tempRets.add(buildRandom(length));
		}

		String[] rets = new String[num];
		rets = tempRets.toArray(rets);
		return rets;
	}

	/**
	 * ����ָ��λ��������
	 * 
	 * @param length
	 * @return
	 * 
	 * @author �ź���
	 */
	public static int buildIntRandom(final int length) {
		String maxStr = StringUtils.rightPad("1", length + 1, '0');
		long max = Long.parseLong(maxStr);
		long i = Math.abs(random.nextLong()) % max;
		String rand = String.valueOf(i);
		return Integer.parseInt(rand);
	}

	/**
	 * ȡС��ָ����Χ�ڵ�����
	 * 
	 * @param length
	 * @return
	 * 
	 * @author �ź���
	 */
	public static int buildIntRandomBy(final int length) {
		return (int) (Math.random() * length);
	}

	/**
	 * ���ɳ���Ϊlength���ַ���,�ַ���ֻ��������
	 * 
	 * @param length
	 *            �ַ����ĳ���
	 * @return
	 */
	private String buildRandom(final int length) {
		// ����Ϊlength���������
		String maxStr = StringUtils.rightPad("1", length + 1, '0');
		// System.out.println("maxStr=" + maxStr);
		long max = Long.parseLong(maxStr);
		long i = random.nextLong(); // ȡ�������
		// System.out.println("befor i=" + i);
		i = Math.abs(i) % max; // ȡ�������������䳤��
		// System.out.println("after i=" + i);
		String value = StringUtils.leftPad(String.valueOf(i), length, '0');
		// System.out.println("length=" + length);
		// System.out.println("value=" + value);
		return value;
	}
	
	public static void main(String[] args){
		for(int i = 0; i < 10; i++)
			System.out.println(buildIntRandomBy(23));
	}
}
