package org.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CommonLogger {
	private static CommonLogger commonLoger;
	private Logger loger;
	private CommonLogger(){
		
	}
	
	public static CommonLogger getCommon() {
		if(commonLoger == null) commonLoger = new CommonLogger();
		return commonLoger;
	}
	
	public <T> Logger getLogger(Class <T>cls) {
		loger = Logger.getLogger(cls);
<<<<<<< HEAD
		PropertyConfigurator.configure("/home/sun-zstk328/.logs/JobVista.properties");
=======
		PropertyConfigurator.configure("/home/abi-zstk360/JobVista.log");
>>>>>>> 14f5cfc (today Commit)
		return loger;
	}
		
}