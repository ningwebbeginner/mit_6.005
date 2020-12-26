/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here. See the
     * ic03-testing exercise for examples of what a testing strategy comment looks
     * like. Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());

    }

    @Test
    public void testGetTimespan() {
        
        // split different case to different functions
        // test one element
        Timespan timespanOneElement = Extract.getTimespan(Arrays.asList(tweet1));
        assertEquals("expected start", d1, timespanOneElement.getStart());
        assertEquals("expected end", d1, timespanOneElement.getEnd());

        // test unsorted elements
        Instant sampled3 = Instant.parse("2016-02-17T10:30:00Z");
        Tweet tweet3 = new Tweet(3, "Niiwe", "rn #hype", sampled3);
        Timespan timespanMoreThan2 = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        assertEquals("expected start", d1, timespanMoreThan2.getStart());
        assertEquals("expected end", d2, timespanMoreThan2.getEnd());

        // test the same time
        Instant sampled4 = Instant.parse("2016-02-17T10:30:00Z");
        Tweet tweet4 = new Tweet(4, "Niee", "ohhh #hype", sampled4);
        Timespan timespanThesameTime = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
        assertEquals("expected start", d1, timespanThesameTime.getStart());
        assertEquals("expected end", d2, timespanThesameTime.getEnd());

    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));

        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsers() {
        Tweet tweet3 = new Tweet(3, "niew", "@Xx it?", d1);
        Tweet tweet4 = new Tweet(4, "nnn", "No @xx it?", d1);
        Tweet tweet5 = new Tweet(5, "nixew", "when @xx?", d1);
        Tweet tweet6 = new Tweet(6, "niewt", "@din no @xx it?", d1);
        Tweet tweet7 = new Tweet(7, "niewj", "@din what @xx?", d1);
        Tweet tweet8 = new Tweet(8, "niewk", "@@xx it?", d1);
        Tweet tweet9 = new Tweet(9, "niewk", "ef@mit.edu it?", d1);
        Tweet tweet10 = new Tweet(10, "niewk", "ef @.mit it?", d1);

        // test a mentioned user at begin in upper case
        Set<String> mentionedUsers2 = Extract.getMentionedUsers(Arrays.asList(tweet3));
        assertTrue("expected get a set", mentionedUsers2.stream().anyMatch("xx"::equalsIgnoreCase));

        // test a mentioned users at mid
        Set<String> mentionedUsers3 = Extract.getMentionedUsers(Arrays.asList(tweet4));
        assertTrue("expected get a set", mentionedUsers3.stream().anyMatch("xx"::equalsIgnoreCase));

        // test a mentioned user at end
        Set<String> mentionedUsers4 = Extract.getMentionedUsers(Arrays.asList(tweet5));
        assertTrue("expected get a set", mentionedUsers4.stream().anyMatch("xx"::equalsIgnoreCase));

        // test two mentioned users at begin and at mid
        Set<String> mentionedUsers5 = Extract.getMentionedUsers(Arrays.asList(tweet6));
        assertTrue("expected get a set", mentionedUsers5.stream().anyMatch("xx"::equalsIgnoreCase));
        assertTrue("expected get a set", mentionedUsers5.stream().anyMatch("din"::equalsIgnoreCase));

        // test two mentioned users at begin and at end
        Set<String> mentionedUsers6 = Extract.getMentionedUsers(Arrays.asList(tweet7));
        assertTrue("expected get a set", mentionedUsers6.stream().anyMatch("xx"::equalsIgnoreCase));
        assertTrue("expected get a set", mentionedUsers6.stream().anyMatch("xx"::equalsIgnoreCase));

        // test an illegal mentioned user followed by @
        Set<String> mentionedUsers7 = Extract.getMentionedUsers(Arrays.asList(tweet8));
        assertTrue("expected get a set", mentionedUsers7.isEmpty());

        // test an illegal mentioned users e-mail format
        Set<String> mentionedUsers8 = Extract.getMentionedUsers(Arrays.asList(tweet9));
        assertTrue("expected get a set", mentionedUsers8.isEmpty());

        // test an illegal mentioned users followed by .
        Set<String> mentionedUsers9 = Extract.getMentionedUsers(Arrays.asList(tweet10));
        assertTrue("expected get a set", mentionedUsers9.isEmpty());
    }
    /*
     * Warning: all the tests you write here must be runnable against any Extract
     * class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting (temporarily)
     * your version of Extract with the staff's version. DO NOT strengthen the spec
     * of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own that
     * you have put in Extract, because that means you're testing a stronger spec
     * than Extract says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them in
     * this test class.
     */

}
