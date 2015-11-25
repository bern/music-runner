package com.sp.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Troy on 11/25/2015.
 *
 * The seconds level builder shows feature data at 60 Hz (seconds on a timeline)
 *
 */
public class SecondsLevelBuilder extends LevelBuilder {

    private StringTokenizer tokens;

    public SecondsLevelBuilder(String filepath) {
        super(filepath);
    }

    @Override
    protected void generateLevel() {

        FileHandle inputFile = Gdx.files.internal(this.getReadPath());
        tokens = new StringTokenizer(inputFile.readString());

        ArrayList<Double> features = new ArrayList<Double>();
        while (tokens.hasMoreTokens()) {
            features.add(Double.parseDouble(tokens.nextToken()));
        }

        File file = new File("levels/level2.txt");
        FileOutputStream os;
        BufferedOutputStream out;
        try {
            os = new FileOutputStream(file);

            out = new BufferedOutputStream(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
