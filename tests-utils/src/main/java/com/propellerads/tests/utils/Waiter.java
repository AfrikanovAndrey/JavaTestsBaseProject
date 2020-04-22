package com.propellerads.tests.utils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Waiter {

	public static void wait(Duration duration){
		try {
			TimeUnit.MILLISECONDS.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
