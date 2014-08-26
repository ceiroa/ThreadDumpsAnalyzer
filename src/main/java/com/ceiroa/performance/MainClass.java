package com.ceiroa.performance;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainClass {

	private static Map<String, ThreadTrace> threadTraces = new HashMap<String, ThreadTrace>();
	
	public static void main(String[] args) throws IOException {
		Analyzer analyzer = new Analyzer();
		
		int fileCount = 1;
		
		List<String> packagesToLookFor = getPackagesToLookForFromUser();
			
		//Loop each file parsing and counting
		for(String filename : args) {
			analyzer.findRunnableThreads(filename, fileCount, threadTraces);
			fileCount++;
		}
		
		//Show the threads that appear in all dumps ("fileCount-1" because we started at 1) 
		appearInAllDumps(fileCount-1);
		haveSameBodyInAllDumps(fileCount-1);
		haveSimilarBodyInAllDumps(fileCount-1);
	}
	
	private static List<String> getPackagesToLookForFromUser() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void appearInAllDumps(int fileCount) {
		
	}
	
	private static void haveSameBodyInAllDumps(int fileCount) {
		Collection<ThreadTrace> values = threadTraces.values();
		
		for(ThreadTrace tt : values) {
			if(tt.getTotal() >= fileCount && tt.hasSameCustomBodies()) {
				System.out.println(tt.toStringOneCustomBody());
			}
		}
	}
	
	private static void haveSimilarBodyInAllDumps(int fileCount) {
		
	}
}
