package com.bosch.validation.fw.utils;

import java.util.Random;

public class NumberUtils {
	public static int randomNum(int start, int end) {
		Random randomGenerator = new Random();

		long range = (long) end - (long) start + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * randomGenerator.nextDouble());
		int randomNumber = (int) (fraction + start);

		return randomNumber;
	}
}
