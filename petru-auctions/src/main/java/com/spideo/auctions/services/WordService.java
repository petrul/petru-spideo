package com.spideo.auctions.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * manages the two files, nouns and adjective,
 */
@Service
public class WordService {

    static String nounsPath = "words/nouns.txt";
    static String adjPath = "words/adjectives.txt";

    WordTextFile nounsTextFile;
    WordTextFile adjTextFile;

    private static URI nounsUri = null;
    private static URI adjUri = null;
    private static Map<String,URI> pathToUriMap = new HashMap();

    public WordService() {

        this.nounsTextFile = new WordTextFile(nounsPath);
        this.adjTextFile = new WordTextFile(adjPath);

        LOG.info(String.format("%d nouns, %d adjectives", this.nounsTextFile.nrLines, this.adjTextFile.nrLines));
    }

    public String randomNoun() {
        return this.nounsTextFile.getRandomLine();
    }

    public String randomAdjective() {
        return this.adjTextFile.getRandomLine();
    }


    public List<String> randomAdjectiveAndNoun() {

        ArrayList<String> result = new ArrayList<>(2);

        String adj = this.randomAdjective();
        String noun = this.randomNoun();

        result.add(adj);
        result.add(noun);

        return result;

    }
    
    /**
     * the username concatenates the pieces in lowercase with underscores
     */
    protected static String randomUsernameFor(List<String> words) {
        if (words.size() < 1)
            throw new IllegalArgumentException();

        StringBuilder builder = new StringBuilder();
        for (String s : words) {
            builder.append(s
                    .replace("\'", "")
                    .replace("-", "")
                    .toLowerCase());
            builder.append("_");
        }
        builder.deleteCharAt(builder.length() - 1); // delete last underscore
        return builder.toString();
    }

    protected String randomUsername() {
         return randomUsernameFor(this.randomAdjectiveAndNoun());
    }

    protected String randomDisplayUsername() {
        return randomDisplayUsernameFor(this.randomAdjectiveAndNoun());
    }

    /**
     * the display username may contain spaces and will be capitalized
     */
    protected static String randomDisplayUsernameFor(List<String> words) {
        if (words.size() < 1)
            throw new IllegalArgumentException();

        StringBuilder builder = new StringBuilder();
        for (String s : words) {
            builder.append( capitalize(s));
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length() - 1); // delete last space
        return builder.toString();
    }

    private static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    static Logger LOG = LoggerFactory.getLogger(WordService.class);
}
