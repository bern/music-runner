import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;

import matlabcontrol.*;

public class MatlabTest {
	
	public static double[] frames;
	public static int counter;
	public static double prob = 0;
	
	private static MatlabProxyFactory factory = null;
	
	public static void init(String file, String difficulty) 
		throws MatlabConnectionException, MatlabInvocationException
	{
	    //Create a proxy, which we will use to control MATLAB
	    if(factory == null)
	    	factory = new MatlabProxyFactory();
	    
	    MatlabProxy proxy = factory.getProxy();

	    counter = 0;
	    
	    //Set a variable, add to it, retrieve it, and print the result
	    proxy.setVariable("a", 3);
	    
	    proxy.setVariable("var1", -1);
	    proxy.setVariable("var2", -1);
	    proxy.setVariable("var3", -1);
	    proxy.setVariable("var4", -1);
	    
	    proxy.eval("cd C:\\Users\\bern\\Documents\\Code\\SeniorProject");
	    
	    if(!(file.substring(file.length()-4,file.length()).equals(".wav"))) {
	    	file = file+".wav";
	    }
	    
	    difficulty = difficulty.toLowerCase();
	    
	    if(difficulty.equals("easy")) {
	    	prob = .05;
	    }
	    else if(difficulty.equals("medium")) {
	    	prob = .15;
	    }
	    else if(difficulty.equals("hard")) {
	    	prob = .3;
	    }
	    else if(difficulty.equals("nightmare")) {
	    	prob = .6;
	    }
	    else if(difficulty.equals("dave")) {
	    	prob = 1;
	    }
	    
	    StringBuilder filename = new StringBuilder();
	    filename.append("'");
	    filename.append(file);
	    filename.append("'");
	    
	    proxy.eval("[var1, var2, var3, var4] = musicalfeatures("+filename.toString()+", a);");
	    frames = ((double[]) proxy.getVariable("var3"));

	    //Disconnect the proxy from MATLAB
	    proxy.disconnect();
	    
	    try {
	    	playClip(file);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	private static synchronized void playClip(String url) {
	  new Thread(new Runnable() {
		public void run(){
			class AudioListener implements LineListener {
			    private boolean done = false;
			    @Override public synchronized void update(LineEvent event) {
			      Type eventType = event.getType();
			      if (eventType == Type.STOP || eventType == Type.CLOSE) {
			        done = true;
			        System.out.println("ending");
			        MatlabTestGUI.play = false;
			        notifyAll();
			      }
			    }
			    public synchronized void waitUntilDone() throws InterruptedException {
			      while (!done) { }
			    }
			    public synchronized void waitUntilDone(Clip clip) {
			    	while(!done) {
			    		if(counter < frames.length && clip.getLongFramePosition() > frames[counter]) {
			    			System.out.println("FEATURE"+counter);
			    			counter++;
			    			double p = Math.random();
			    			if(p < prob)
			    				MatlabTestPanel.addEnemy();
			    		}
			    	}
			    }
			  }
			  AudioListener listener = new AudioListener();
			  AudioInputStream audioInputStream = null;
			  try {
				  audioInputStream = AudioSystem.getAudioInputStream(
			        MatlabTest.class.getResourceAsStream(url));
			  } catch(Exception e) {
				  e.printStackTrace();
			  }
			  try {
				Clip clip = null;
				try {
				    clip = AudioSystem.getClip();
				    clip.addLineListener(listener);
				    clip.open(audioInputStream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			    try {
			      MatlabTestGUI.play = true;
			      clip.start();
			      listener.waitUntilDone(clip);
			    } finally {
			      System.out.println("ending");
			      MatlabTestGUI.play = false;
			      clip.close();
			    }
			  } finally {
				try {
					audioInputStream.close();
					System.out.println("ending");
					MatlabTestGUI.play = false;
				} catch(Exception e) {
					e.printStackTrace();
				}
			  }
		  }
	  }).start();
	}
}
