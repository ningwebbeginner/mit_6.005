/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here. See the
     * ic03-testing exercise for examples of what a testing strategy comment looks
     * like. Make sure you have partitions.
     */

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraph() {
        Instant d3 = Instant.parse("2016-02-17T08:00:00Z");

        Tweet tweet1 = new Tweet(1, "alyssa", "ff gg?", d3);
        Tweet tweet2 = new Tweet(2, "bbitdiddle", "@fi gg?", d3);
        Tweet tweet3 = new Tweet(3, "ttbb", "@fi @if gg?", d3);
        // Tweet tweet4 = new Tweet(4, "alyssa", "ff gg kk?", d3);
        Tweet tweet5 = new Tweet(5, "alyssa", "ff @alyssa gg @san?", d3);
        Tweet tweet6 = new Tweet(6, "alyssa", "ff @Bbitdiddle?", d3);
        Tweet tweet7 = new Tweet(7, "alyssa", "ff gg @sayhr?", d3);
        Tweet tweet8 = new Tweet(8, "alyssa", "ff @san @sayhr?", d3);
        Tweet tweet9 = new Tweet(9, "alyssa", "ff gg @sayhr @sayst?", d3);
        // Tweet tweet10 = new Tweet(10, "alyssa", "ff @san @sayhr?", d3);
        // Tweet tweet11 = new Tweet(11, "alyssa", "ff gg @sayhr @sayst?", d3);

        // one user tweet one txt @ none
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        Map<String, Set<String>> followsGraphLowercase = transformLowerCase(followsGraph);
        assertTrue("expected empty graph",
                followsGraphLowercase.isEmpty() || followsGraphLowercase.get(tweet1.getAuthor()).isEmpty());

        // one user tweet one txt @ another one
        Map<String, Set<String>> followsGraph1 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        Map<String, Set<String>> followsGraph1Lowercase = transformLowerCase(followsGraph1);
        Set<String> keySet1 = followsGraph1Lowercase.keySet();
        assertTrue("expected graph contain", keySet1.contains(tweet2.getAuthor()));
        assertTrue("expected graph contain", !keySet1.contains("fi") || followsGraph1Lowercase.get("fi").isEmpty());

        // one user tweet one txt @ another two
        Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet3));
        Map<String, Set<String>> followsGraph2Lowercase = transformLowerCase(followsGraph2);
        Set<String> keySet2 = followsGraph2Lowercase.keySet();
        assertTrue("expected graph contain", keySet2.contains(tweet3.getAuthor()));
        assertTrue("expected graph contain", !keySet2.contains("fi") || followsGraph1Lowercase.get("fi").isEmpty());
        assertTrue("expected graph contain", !keySet2.contains("if") || followsGraph1Lowercase.get("if").isEmpty());

        // two users tweet two txts @ another two and oneself
        Map<String, Set<String>> followsGraph3 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet5));
        Map<String, Set<String>> followsGraph3Lowercase = transformLowerCase(followsGraph3);
        Set<String> keySet3 = followsGraph3Lowercase.keySet();
        assertFalse("expected graph don't containhimeself",
                followsGraph3Lowercase.get(tweet5.getAuthor()).contains(tweet5.getAuthor()));
        assertTrue("expected graph contain", !keySet3.contains("fi") || followsGraph3Lowercase.get("fi").isEmpty());
        assertTrue("expected graph contain", !keySet3.contains("san") || followsGraph3Lowercase.get("san").isEmpty());

        // two users tweet two txts @ two
        Map<String, Set<String>> followsGraph4 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet6));
        Map<String, Set<String>> followsGraph4Lowercase = transformLowerCase(followsGraph4);
        Set<String> keySet4 = followsGraph4Lowercase.keySet();
        assertTrue("expected graph", keySet4.contains(tweet6.getAuthor()));
        assertTrue("expected graph", followsGraph4Lowercase.get(tweet6.getAuthor()).contains(tweet2.getAuthor()));

    }

    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencers() {

        Instant d3 = Instant.parse("2016-02-17T08:00:00Z");

        Tweet tweet1 = new Tweet(1, "alyssa", "ff @om @bbitdiddle gg?", d3);
        Tweet tweet2 = new Tweet(2, "bbitdiddle", "fi gg?", d3);
        Tweet tweet3 = new Tweet(3, "ttbb", "@bbitdiddle @if gg?", d3);
        Tweet tweet4 = new Tweet(4, "ttbb", "@om @ddr gg?", d3);

        // one author two follows
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        Map<String, Set<String>> followsGraphLowercase = transformLowerCase(followsGraph);

        List<String> influencers = SocialNetwork.influencers(followsGraphLowercase);

        assertEquals("expected a list with two", 2, influencers.size());
        assertTrue("expected a list with two", influencers.containsAll(Arrays.asList("bbitdiddle", "om")));

        // empty list
        Map<String, Set<String>> followsGraph1 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        Map<String, Set<String>> followsGraph1Lowercase = transformLowerCase(followsGraph1);

        List<String> influencers1 = SocialNetwork.influencers(followsGraph1Lowercase);

        assertTrue("expected empty list", influencers1.isEmpty());

        // two authors two follows
        Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet3));
        Map<String, Set<String>> followsGraph2Lowercase = transformLowerCase(followsGraph2);

        List<String> influencers2 = SocialNetwork.influencers(followsGraph2Lowercase);

        assertEquals("expected two authors two follows", 3, influencers2.size());
        assertEquals("expected first", "bbitdiddle", influencers2.get(0));
        assertTrue("expected the other two", influencers2.subList(1, 3).containsAll(Arrays.asList("if", "om")));

        // three authors multiple follows
        Map<String, Set<String>> followsGraph3 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet3, tweet4));
        Map<String, Set<String>> followsGraph3Lowercase = transformLowerCase(followsGraph3);

        List<String> influencers3 = SocialNetwork.influencers(followsGraph3Lowercase);

        assertEquals("expected a list 4 elements", 4, influencers3.size());
        assertTrue("expected top 2", influencers3.subList(0, 2).containsAll(Arrays.asList("bbitdiddle", "om")));
        assertTrue("expected the other 2", influencers3.subList(2, 4).containsAll(Arrays.asList("if", "ddr")));

    }

    public static Map<String, Set<String>> transformLowerCase(Map<String, Set<String>> orgMap) {
        Map<String, Set<String>> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toLowerCase();
            resultMap.put(newKey, orgMap.get(key));
        }

        Map<String, Set<String>> resultMapLowString = new HashMap<>();

        Set<String> keySetLower = resultMap.keySet();
        for (String key : keySetLower) {
            Set<String> newValue = resultMap.get(key);
            for (String value : newValue) {
                value = value.toLowerCase();
            }
            resultMapLowString.put(key, newValue);
        }

        return resultMapLowString;
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version. DO NOT
     * strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own that
     * you have put in SocialNetwork, because that means you're testing a stronger
     * spec than SocialNetwork says. If you need such helper methods, define them in
     * a different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
