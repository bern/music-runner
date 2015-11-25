package com.sp.game.tools;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.LineEvent.Type;
import javax.swing.JFileChooser;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;

import matlabcontrol.*;

public class MusicOperator {

	private double[] frames;
	private double numFrames = 0;
	private boolean doneProcessing = false;
	
	private static MatlabProxyFactory factory = null;
	
	public MusicOperator() {}
	
	public void initSelect(String song) 
		throws MatlabConnectionException, MatlabInvocationException
	{		
		String file = "../../../java-matlab-test/src/"+song;
		String difficulty = "nightmare";
		//JFileChooser temp = new JFileChooser("../../../../../../java-matlab-test/src");
		
	    //Create a proxy, which we will use to control MATLAB
	    if(factory == null)
	    	factory = new MatlabProxyFactory();
	    
	    MatlabProxy proxy = factory.getProxy();
	    
	    //Set a variable, add to it, retrieve it, and print the result
	    proxy.setVariable("a", 3);
	    
	    proxy.setVariable("var1", -1);
	    proxy.setVariable("var2", -1);
	    proxy.setVariable("var3", -1);
	    proxy.setVariable("var4", -1);
	    
	    System.out.println(file);
	    
	    StringBuilder filename = new StringBuilder();
	    filename.append("'");
	    filename.append(file);
	    filename.append("'");
	    
	    proxy.eval("NOISE_CANCELLATION_OFFSET = 100;\n"
	    		+ "n = 3;\n"
	    		+ "[wav,fs] = audioread("+filename.toString()+");\n"
	    		+ "num_frames = length(wav);"
	    		+ "left_channel = wav(:,1);\n"
	    		+ "len = size(left_channel);\n"
	    		+ "for i=1:len\n"
	    		+ "\tleft_channel(i) = abs(left_channel(i));\n"
	    		+ "end\n"
	    		+ "standard_deviation = std(left_channel);\n"
	    		+ "avg = mean(left_channel);\n"
	    		+ "threshold = avg+(standard_deviation*n);\n"
	    		+ "[peaks, loc] = findpeaks(left_channel, 'MinPeakHeight', threshold);\n"
	    		+ "findpeaks(left_channel, 'MinPeakHeight', threshold);\n"
	    		+ "[h,w] = size(loc);\n"
	    		+ "loc_cancelled = zeros(h,1);\n"
	    		+ "for i=1:size(loc)\n"
	    		+ "\tloc_cancelled(i) = -1;\n"
	    		+ "end\n"
	    		+ "for i=1:size(loc)\n"
	    		+ "\tcurr_peak = loc(i);\n"
	    		+ "\tcan_add = true;\n"
	    		+ "\tstore_index = -1;\n"
	    		+ "\tfor j=1:size(loc_cancelled)\n"
	    		+ "\t\tif curr_peak < (loc_cancelled(j) + NOISE_CANCELLATION_OFFSET)\n"
	    		+ "\t\t\tcan_add = false;\n"
	    		+ "\t\tend\n"
	    		+ "\t\tif loc_cancelled(j) == -1\n"
	    		+ "\t\t\tstore_index = j;\n"
	    		+ "\t\t\tbreak;\n"
	    		+ "\tend\n"
	    		+ "\tend\n"
	    		+ "\tif can_add == true\n"
	    		+ "\t\tloc_cancelled(store_index) = curr_peak;\n"
	    		+ "\tend\n"
	    		+ "end\n"
	    		+ "loc_cancelled_trimmed = zeros(store_index-1,1);\n"
	    		+ "for i=1:store_index-1\n"
	    		+ "\tloc_cancelled_trimmed(i) = loc_cancelled(i);\n"
	    		+ "end\n"
	    		+ "[h,w] = size(loc_cancelled_trimmed);\n"
	    		+ "loc_sec_trimmed = zeros(h,1);\n"
	    		+ "for i=1:size(loc_cancelled_trimmed)\n"
	    		+ "\tloc_sec_trimmed(i) = loc_cancelled(i)/fs;\n"
	    		+ "end");
	    
	    frames = ((double[]) proxy.getVariable("loc_cancelled_trimmed"));
	    double[] temp_num_frames_arr = ((double[]) proxy.getVariable("num_frames"));
	    numFrames = temp_num_frames_arr[0];

	    //Disconnect the proxy from MATLAB
	    proxy.disconnect();
	    doneProcessing = true;
	}
	
	public boolean getDoneProcessing() {
		return doneProcessing;
	}
	
	public double[] getFrames() {
		return frames;
	}
	
	public double getNumFrames() {
		return numFrames;
	}
}