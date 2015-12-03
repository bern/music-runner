package com.sp.game.tools;
import java.util.ArrayList;

import com.sp.game.Game;

import java.io.*;

public class MusicOperator {

	private double[] frames;
	private double numFrames = 0;
	private boolean doneProcessing = false;
	private String song;
	private SongCacheUtil cache;
	
	public MusicOperator(SongCacheUtil cache) {
		this.cache = cache;
	}
	
	public void initSelect()
	{
		if(!(cache.hasFrameBuiltLevel(song))) {
			String s = null;
			numFrames = -1;		
			ArrayList<Double> frameData = new ArrayList<Double>();
			ArrayList<Integer> frameDensity = new ArrayList<Integer>();
			
			try {
				Process p = Runtime.getRuntime().exec("python MusicAnalysis.py "+song);
				BufferedReader stdInput = new BufferedReader(new
	                 InputStreamReader(p.getInputStream()));
	 
	            BufferedReader stdError = new BufferedReader(new
	                 InputStreamReader(p.getErrorStream()));
	 
	            // read the output from the command
	            while ((s = stdInput.readLine()) != null) {
	            	if(numFrames == -1) {
	            		numFrames = Double.parseDouble(s);
	            	}
	            	else if(frameData.size() == frameDensity.size()) {
	                	frameData.add(Double.parseDouble(s));
	                }
	                else {
	                	frameDensity.add(Integer.parseInt(s));
	                }
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			frames = new double[frameData.size()];
			for(int i = 0; i < frameData.size(); i++) {
				frames[i] = frameData.get(i);
			}
			
			doneProcessing = true;
		}
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
	
	public String getSong() {
		return song;
	}
	
	public void setSong(String song) {
        if (!song.contains(".wav"))
            song = song + ".wav";
        this.song = song;
	}
}
