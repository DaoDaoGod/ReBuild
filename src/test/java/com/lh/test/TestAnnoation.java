package com.lh.test;

import java.lang.annotation.Annotation;

import javax.swing.text.html.HTML.Tag;
public class TestAnnoation {
   public static void main(String[] args)
   {
	   
	   Test t1=new Test();
	   t1.info();
	   t1.jj();
	   
	   try {
		Annotation[] array=Class.forName("com.lh.test.Test").getMethod("jj").getAnnotations();
		for(Annotation tag:array)
		{
			if(tag instanceof MyTag)
			{
				System.out.println("Tag is: "+tag);
				System.out.println(((MyTag)tag).age());
			//	System.out.println();
				
				
			}
		}
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
   }
}
