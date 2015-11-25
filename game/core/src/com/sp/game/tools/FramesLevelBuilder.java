package com.sp.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Troy on 11/25/2015.
 *
 * The frame level builder shows feature data at 44,100 Hz
 *
 */
public class FramesLevelBuilder extends LevelBuilder {

    private StringTokenizer tokens;

    public FramesLevelBuilder(String filepath) {
        super(filepath);
        this.setWritePath("gen/frames_level_out.txt");
        generateLevel();
    }

    @Override
    protected void generateLevel() {
        FileHandle inputFile = Gdx.files.internal(this.getReadPath());
        tokens = new StringTokenizer(inputFile.readString());

        ArrayList<Integer> features = new ArrayList<Integer>();
        int lastFrame = Integer.parseInt(tokens.nextToken());
        while (tokens.hasMoreTokens()) {
            features.add(Integer.parseInt(tokens.nextToken()));
        }

        File file = new File(getWritePath());
        FileOutputStream os;
        BufferedOutputStream out;

        try {
            os = new FileOutputStream(file);

            out = new BufferedOutputStream(os);

            int numBlocks = (int) ((lastFrame / 44100.0) * ( 400 / 64.0));

            for (int i = 0; i < numBlocks; ++i ) {
                out.write("Platform ".getBytes());
                int cur = 64 * i;
                String string = String.valueOf(cur);
                out.write(string.getBytes());
                out.write(" 0\n".getBytes());
            }

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
