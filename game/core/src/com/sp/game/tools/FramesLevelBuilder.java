package com.sp.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.sp.game.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Troy on 11/25/2015.
 *
 * The frame level builder shows feature data at 44,100 Hz
 *
 */
public class FramesLevelBuilder extends LevelBuilder {

    private StringTokenizer tokens;
    private ArrayList<Integer> features;
    private ArrayList<Integer> densities;
    private int lastFrame;
    private static final int BLOCK_BUFFER_NUM = 60;
    private static final int BLOCK_BUFFER_PIXELS = BLOCK_BUFFER_NUM * 64;

    public FramesLevelBuilder(String filepath) {
        super(filepath);
        this.setWritePath("levels/frames_level_out.txt");
        FileHandle inputFile = Gdx.files.internal(this.getReadPath());
        tokens = new StringTokenizer(inputFile.readString());

        features = new ArrayList<Integer>();
        lastFrame = Integer.parseInt(tokens.nextToken());
        while (tokens.hasMoreTokens()) {
            features.add(Integer.parseInt(tokens.nextToken()));
        }
        generateLevel();
    }
    
    public FramesLevelBuilder(MusicOperator mo, double[] frames, double[] densities, double numFrames, SongCacheUtil cache) {
    	super("levels/"+mo.getSong()+".txt");
    	if((cache.hasFrameBuiltLevel(mo.getSong()))) {
    		this.setWritePath("levels/"+mo.getSong()+".txt");
    		return;
    	}
    	
    	Integer[] int_features = new Integer[frames.length];
    	for (int i=0; i < frames.length; ++i) {
    		int_features[i] = (int)frames[i];
    	}
    	features = new ArrayList<Integer>(Arrays.asList(int_features));
    	
    	Integer[] int_densities = new Integer[frames.length];
    	for (int i=0; i < densities.length; ++i) {
    		int_densities[i] = (int)densities[i];
    	}
    	this.densities = new ArrayList<Integer>(Arrays.asList(int_densities));
    	
    	this.setWritePath("levels/"+mo.getSong()+".txt");
    	lastFrame = (int)numFrames;
    	generateLevel();
    }

    @Override
    protected void generateLevel() {
    

        File file = new File(getWritePath());
        FileOutputStream os;
        BufferedOutputStream out;

        try {
            os = new FileOutputStream(file);

            out = new BufferedOutputStream(os);

            int numBlocks = (int) ((lastFrame / 44100.0) * ( 400 / 64.0));

            for (int i = 0; i < (numBlocks + BLOCK_BUFFER_NUM); ++i ) {
                out.write("Platform ".getBytes());
                int cur = 64 * i;
                String string = String.valueOf(cur);
                out.write(string.getBytes());
                out.write(" 0\n".getBytes());
                if (i % 4 == 0) {
                    out.write("Wave ".getBytes());
                    out.write(string.getBytes());
                    out.write(" 48\n".getBytes());
                }
                if (i == (numBlocks + BLOCK_BUFFER_NUM - 20)) {
                    out.write("Flag ".getBytes());
                    out.write(string.getBytes());
                    out.write(" 64\n".getBytes());
                }
            }


            int prevLocEnemy = Integer.MIN_VALUE;
            int prevLocCollectible = Integer.MIN_VALUE;
            for(int i = 0; i < features.size(); ++i) {

                double rand = Math.random();
                int cur = (int) ((features.get(i) * 400.0) / (44100.0));
                cur += BLOCK_BUFFER_PIXELS / 2;
                String string = String.valueOf(cur);
                if (Math.random() < DifficultyUtility.ENEMY_MEDIUM) {
                    //if (Math.abs(cur - prevLocEnemy) > 100) {
                        //System.out.println(densities.get(i));
                    	if(densities.get(i) > 250) {
                    		out.write("LargeEnemy ".getBytes());
                    	}
                    	else if(densities.get(i) > 100) {
                    		out.write("MediumEnemy ".getBytes());
                    	}
                    	else {
                    		out.write("SmallEnemy ".getBytes());
                    	}
                        out.write(string.getBytes());
                        out.write(" 64\n".getBytes());
                        prevLocEnemy = cur;
                    //}
                }
                else if(Math.random() < DifficultyUtility.COLLECTIBLE_EASY) {
                    if (Math.abs(cur - prevLocCollectible) > 100) {
                        out.write("Collectible ".getBytes());
                        out.write(string.getBytes());
                        out.write(" 256\n".getBytes());
                        prevLocCollectible = cur;
                    }
                }
            }

            int bgNum = (int) (((lastFrame / 44100.0) / (400 / 600.0)) / 2) + 1;
            for (int j=0; j < bgNum; ++j) {
                int bgCur = 600 * j;
                String string = String.valueOf(bgCur);
                out.write("VolumeBar ".getBytes());
                out.write(string.getBytes());
                out.write(" -200 2\n".getBytes());
            }

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
