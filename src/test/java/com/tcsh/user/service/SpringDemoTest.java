package com.tcsh.user.service;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class SpringDemoTest {

	@Test
	public void testPropertyPlaceholderConfigurer(){
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ApplicationContext app = new ClassPathXmlApplicationContext();
	}
	
	@Test
	public void testResource(){
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	    try {
	      Resource[] metaInfResources = resourcePatternResolver
	          .getResources("classpath*:**/*DAO.class");
	      for(Resource r : metaInfResources){
	        System.out.println("URL:" + r.getURL());
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
}
