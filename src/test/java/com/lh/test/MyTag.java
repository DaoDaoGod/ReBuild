package com.lh.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyTag {

	String name() default "yeeku"; 
	int age() default 32;
}
