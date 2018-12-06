package com.mycompany.flightapp.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CentralizedPointCutAspect {
	@Pointcut("bean(*Service) || bean(*Controller)")
	private void allServices(){
		
	}
	
	@Pointcut("bean(*Controller)")
	private void controllers(){
		
	}
	
	@Pointcut("within(com.yilu.flightapp..*)")
	private void packagesToInclude() {
	}
	

	
	@Pointcut("packagesToInclude() && (allServices() || controllers()) ")
	public void serviceClassesToLog() {
	}
}