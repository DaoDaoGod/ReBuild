package com.lh.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@MyTag(name="xx",age=6)
public class Test {
	
	@MyTag(name="xx",age=6)
	public void info()
	{
		System.out.println("Hello,World");
	}
	@MyTag
	public void jj()
	{
		System.out.println("xxxxx");
	}
}
