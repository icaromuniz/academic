package br.com.juris.academico.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Util{

	@SuppressWarnings("unchecked")
	public static <T extends Object> T getDao(@SuppressWarnings("rawtypes") Class classeDao){
		
		try {
			return (T) InitialContext.doLookup("java:module/" + classeDao.getSimpleName());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
