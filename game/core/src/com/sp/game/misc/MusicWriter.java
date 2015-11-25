package com.sp.game.misc;

import java.io.*;

/**
 * Created by Troy on 11/18/2015.
 */
public class MusicWriter {
    public static void main(String[] args) {
        File outFile = new File("out.txt");
        File inFile = new File("in.txt");

        FileOutputStream os;
        BufferedOutputStream out;

        FileInputStream is;
        BufferedInputStream in;


        try {
            is = new FileInputStream(inFile);
            os = new FileOutputStream(outFile);

            in = new BufferedInputStream(is);
            out = new BufferedOutputStream(os);

            BufferedReader br = new BufferedReader(new FileReader("in.txt"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] segs = line.split(" ");
                out.write("PLAY_NOTE_DUR(\"".getBytes());
                out.write(segs[0].getBytes());
                out.write("\", ".getBytes());
                switch(segs[1].charAt(0)) {
                    case 'T':
                        out.write("THIRTYSECOND_NOTE".getBytes());
                        break;
                    case 'S':
                        out.write("SIXTEENTH_NOTE".getBytes());
                        break;
                    case 'E':
                        out.write("EIGHTH_NOTE".getBytes());
                        break;
                    case 'Q':
                        out.write("QUARTER_NOTE".getBytes());
                        break;
                    case 'H':
                        out.write("HALF_NOTE".getBytes());
                        break;
                    case 'W':
                        out.write("WHOLE_NOTE".getBytes());
                        break;
                    case 'R':
                        out.write("R".getBytes());
                }
                out.write(");\n".getBytes());
            }
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
