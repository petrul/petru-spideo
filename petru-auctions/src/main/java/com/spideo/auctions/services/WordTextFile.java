package com.spideo.auctions.services;

import java.io.*;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class WordTextFile {

    int nrLines;
    String resourceName;

    public WordTextFile(String resourceName) {
        this.resourceName = resourceName;


        URL resource = this.getClass().getClassLoader().getResource(resourceName);
        if (resource == null)
            throw new IllegalArgumentException(String.format("cannot access %s", resourceName));

        this.nrLines = this.countLines();
    }

    /**
     * first line is number 0; the second is number 1; the last one is this.countLines() - 1
     * @param n
     * @return
     */
    public String getLineNr(int n) throws IOException {

        if (n >= this.nrLines)
            throw new IllegalArgumentException(String.format("given line %d depasses total number of lines of file %s : %d", n, resourceName, this.nrLines));

        int crtLineNr = 0;
        String crtLine = null;
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        LineNumberReader lnreader = new LineNumberReader(new BufferedReader(new InputStreamReader(resourceAsStream)));

        while (crtLineNr++ <= n) {
            crtLine = lnreader.readLine();
        }

        lnreader.close();

        return  crtLine;
    }


    String getRandomLine() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, this.nrLines);

        try {
            String line = this.getLineNr(randomNum);
            return line;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private int countLines() {
        int lineCount = 0;

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        LineNumberReader lnreader = new LineNumberReader(new BufferedReader(new InputStreamReader(resourceAsStream)));

        try {
            while (lnreader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                lnreader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return lineCount;
    }

}
