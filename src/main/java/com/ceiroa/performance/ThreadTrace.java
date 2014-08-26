package com.ceiroa.performance;

import java.util.ArrayList;
import java.util.List;

public class ThreadTrace {

	private final String threadName;
	private final List<String> bodies;
	private final List<String> customBodies;
	
	//TODO we need to be have as many counts as threaddumps we are comparing
	private int count1 = 0, count2 = 0, count3 = 0, total = 0;
	
	public ThreadTrace(String threadName) {
		this.threadName = threadName;
		bodies = new ArrayList<String>();
		customBodies = new ArrayList<String>();
	}
	
	public String getThreadName() 			{ return threadName; }
	public List<String> getBodies() 		{ return bodies; }
	public List<String> getCustomBodies() 	{ return customBodies; }
	public void increaseCount1() 			{ count1++; total++; }
	public void increaseCount2() 			{ count2++; total++; }
	public void increaseCount3() 			{ count3++; total++; }
	public int getTotal() 					{ return total; }
	
	public void addBody(String body, int fileCount) {
		bodies.add(body);
		
		String[] lines = body.split("\n");
		StringBuffer customLines = new StringBuffer();
		for(String line: lines) {
			//Save those lines that refer to classes created by us
			
			//TODO change this from harcoded to customizable - we can pass it as params
			//or read it from the console
			if(line.contains("com.ceiroa") || line.contains("com.carlos")) {
				customLines.append(line).append("\n");
			}
		}
		
		customBodies.add(customLines.toString());
	}
	
	public boolean isAnyCustomBodyEmpty() {
		boolean isCustomBodyEmpty = false;
		for(String customBody : customBodies) {
			if(customBody.length() == 0) {
				isCustomBodyEmpty = true;
				break;
			}
		}
		return isCustomBodyEmpty;
	}
	
	public boolean hasSameCustomBodies() {
		//Check if the custom  stack traces (the ones for the 
		//specified packages) are exactly the same
		boolean sameCustomBodies = true;
		if(!isAnyCustomBodyEmpty()) {
			for(int i=0; i<(customBodies.size()-1); i++){
				String a = customBodies.get(i);
				String b = customBodies.get(i+1);
				if(!a.equals(b)){
					sameCustomBodies = false;
					break;
				}
			}
		} else {
			sameCustomBodies = false;
		}
		return sameCustomBodies;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(threadName).append("\n");
		sb.append(" Count1: ").append(count1);
		sb.append(" - Count2: ").append(count2);
		sb.append(" - Count3: ").append(count3).append("\n");
		sb.append(" Total Count: ").append(total).append("\n");
		if(customBodies.size()>0) {
			for(String customBody : customBodies) {
				sb.append("\n");
				String[] customBodyLines = customBody.split("\n");
				for(String line : customBodyLines) {
					sb.append(line).append("\n");
				}
			}
		}
		sb.append("=======================");
		return sb.toString();
	}

	public String toStringOneCustomBody() {
		StringBuffer sb = new StringBuffer();
		sb.append(threadName).append("\n");
		sb.append(" Count1: ").append(count1);
		sb.append(" - Count2: ").append(count2);
		sb.append(" - Count3: ").append(count3).append("\n");
		sb.append(" Total Count: ").append(total).append("\n");
		if(customBodies.size()>0) {
			String customBody = customBodies.get(0);
			sb.append("\n");
			String[] customBodyLines = customBody.split("\n");
			for(String line : customBodyLines) {
				sb.append(line).append("\n");
			}
		}
		sb.append("===================");
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return threadName.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ThreadTrace) {
			ThreadTrace otherEntry = (ThreadTrace)obj;
			return threadName.equals(otherEntry.threadName);
		}
		return false;
	}
}
