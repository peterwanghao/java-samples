package com.peterwanghao.samples.java.utils.uniqueId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @ClassName: GenerateId
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date: 2019年12月17日 下午4:07:35
 * @version V1.0
 * 
 */
public class GenerateId {
	public static void main(String... arguments) {
		try {
			// Initialize SecureRandom
			// This is a lengthy operation, to be done only upon
			// initialization of the application
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

			// generate a random number
			String randomNum = Integer.valueOf(prng.nextInt()).toString();

			// get its digest
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] result = sha.digest(randomNum.getBytes());

			System.out.println("Random number: " + randomNum);
			System.out.println("Message digest: " + hexEncode(result));
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}
	}

	/**
	 * The byte[] returned by MessageDigest does not have a nice textual
	 * representation, so some form of encoding is usually performed.
	 *
	 * This implementation follows the example of David Flanagan's book "Java In
	 * A Nutshell", and converts a byte array into a String of hex characters.
	 *
	 * Another popular alternative is to use a "Base64" encoding.
	 */
	static private String hexEncode(byte[] input) {
		StringBuilder result = new StringBuilder();
		char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f'};
		for (int idx = 0; idx < input.length; ++idx) {
			byte b = input[idx];
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}
}
