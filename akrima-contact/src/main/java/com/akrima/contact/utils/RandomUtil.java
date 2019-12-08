package com.akrima.contact.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class RandomUtil {

	public static Date getRandomDate() {
		Random rnd;
		long ms;

		// Get a new random instance, seeded from the clock
		rnd = new Random();

		// Get an Epoch value roughly between 1940 and 2010
		// -946771200000L = January 1, 1940
		// Add up to 70 years to it (using modulus on the next long)
		ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));

		// Construct and return a date
		return new Date(ms);
	}

	public static String getRandomName() {
		// class variable
		final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

		final java.util.Random rand = new java.util.Random();

		// consider using a Map<String,Boolean> to say whether the identifier is being
		// used or not
		final Set<String> identifiers = new HashSet<String>();

		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(5) + 5;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}
	
	public static Double getRandomSalary() {
		Random r = new Random();
		double randomValue = 1000 + (3000 - 1000) * r.nextDouble();
		return Math.ceil(randomValue);
	}
}
