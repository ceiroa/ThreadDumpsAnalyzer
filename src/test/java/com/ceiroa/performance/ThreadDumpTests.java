package com.ceiroa.performance;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ceiroa.performance.Analyzer;
import com.ceiroa.performance.ThreadTrace;

public class ThreadDumpTests {

	static String slash = System.getProperty("file.separator");
	static String resourcesPath = "."+slash+"src"+slash+"test"+slash+"resources"+slash;
	
	@Test
	public void findsAllRunnableIgnoresOthers() throws IOException {
		Analyzer analyzer = new Analyzer();
		Map<String, ThreadTrace> threadTraces = new HashMap<String, ThreadTrace>();
		analyzer.findRunnableThreads(resourcesPath + "TestThreadDump1.log", 1, threadTraces);
		assertEquals(3, threadTraces.size());
	}
	
	@Test
	public void findsNone() throws IOException {
		Analyzer analyzer = new Analyzer();
		Map<String, ThreadTrace> threadTraces = new HashMap<String, ThreadTrace>();
		analyzer.findRunnableThreads(resourcesPath + "TestThreadDump2.log", 1, threadTraces);
		assertEquals(0, threadTraces.size());
	}
	
	@Test
	public void doesNotBreak() throws IOException {
		Analyzer analyzer = new Analyzer();
		Map<String, ThreadTrace> threadTraces = new HashMap<String, ThreadTrace>();
		analyzer.findRunnableThreads(resourcesPath + "TestThreadDump3.log", 1, threadTraces);
		assertEquals(0, threadTraces.size());
	}
	
	@Test
	public void findsAllCountsTotal() throws IOException {
		Analyzer analyzer = new Analyzer();
		Map<String, ThreadTrace> threadTraces = new HashMap<String, ThreadTrace>();
		analyzer.findRunnableThreads(resourcesPath + "TestThreadDump4.log", 1, threadTraces);
		ThreadTrace entry = threadTraces.get("http-0.0.0.0-1080-1");
		assertEquals(7, entry.getTotal());
	}
}
