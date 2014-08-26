package com.ceiroa.performance;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
	
	public void findRunnableThreads(String filename, int fileCount, Map<String, ThreadTrace> threadTraces) 
			throws IOException {
		//Open file
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		//Read file line by line
		String lineBefore;
		while ((lineBefore = br.readLine()) != null) {
			lineBefore = lineBefore.trim();
			if(!lineBefore.isEmpty()) {
				try {
					String threadStatus = br.readLine().trim();
				
					//If it's a runnable thread
					if(threadStatus.equals("java.lang.Thread.State: RUNNABLE")) {
						String body = getRunnableThreadBody(br);
						storeRunnableThreadInfo(lineBefore, body, fileCount, threadTraces);
					}
				} catch(NullPointerException e) {
					//System.out.println("****");
					//System.out.println("(Reached EOF " + fileCount + ")");
					//System.out.println("****");
				}
			}
		}
		//Close file
		in.close();
	}
	
	private void storeRunnableThreadInfo(String lineBefore, String body, int fileCount, 
			Map<String, ThreadTrace> threadTraces) {	
		String threadName = getThreadName(lineBefore);
		
		//Add entry containing information on all runnable threads
		ThreadTrace existingEntry = threadTraces.get(threadName);
		
		if(existingEntry == null) {			
			ThreadTrace entry = new ThreadTrace(threadName);
			entry.addBody(body.toString(), fileCount);
			threadTraces.put(threadName, entry);
			increaseCount(entry, fileCount);	
		} else {
			existingEntry.addBody(body.toString(), fileCount);
			increaseCount(existingEntry, fileCount);
		}
	}
	
	private static void increaseCount(ThreadTrace entry, int fileCount) {
		if(fileCount == 1)
			entry.increaseCount1();
		else if(fileCount == 2)
			entry.increaseCount2();
		else
			entry.increaseCount3();
	}
	
	private String getThreadName(String lineBefore) {
		String regex = "\"(.*?)\"(.*?)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(lineBefore);
		if(m.matches()) {
			return m.group(1);
		} else {
			return lineBefore;
		}
	}
	
	private static String getRunnableThreadBody(BufferedReader br) throws IOException {
		StringBuffer body = new StringBuffer();
		
		//Read body = rest of lines until blank line
		String temp = "";
		while((temp = br.readLine()) != null) {
			temp = temp.trim();
			//If we reach the end of the body
			if (temp.isEmpty() || temp.equals("\n")) {
				break;
			} else {
				body.append(temp).append("\n");
			}
		}
		
		return body.toString();
	}
}
