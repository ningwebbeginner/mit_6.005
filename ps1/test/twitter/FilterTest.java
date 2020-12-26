/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

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
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    @Test
    public void testWrittenBy() {
        // test one Tweet no result
        List<Tweet> writtenByNo = Filter.writtenBy(Arrays.asList(tweet1), "nnfe");

        assertEquals("expected singleton list", 0, writtenByNo.size());
        assertTrue("expected list to contain tweet", writtenByNo.isEmpty());

        // test one Tweet no result
        List<Tweet> writtenByOne = Filter.writtenBy(Arrays.asList(tweet1), "alyssa");

        assertEquals("expected singleton list", 1, writtenByOne.size());
        assertTrue("expected list to contain tweet", writtenByOne.contains(tweet1));

        // test MultipleTweets no result
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "nnfe");

        assertEquals("expected singleton list", 0, writtenBy.size());
        assertFalse("expected list to contain tweet", writtenBy.contains(tweet1));
        assertFalse("expected list to contain tweet", writtenBy.contains(tweet2));

        // test MultipleTweets multiple results with diff case names
        Tweet tweet3 = new Tweet(3, "trumbb", "res #hype", d2);
        Tweet tweet4 = new Tweet(4, "Trumbb", "res1 #hype", d1);
        List<Tweet> writtenByTr = Filter.writtenBy(Arrays.asList(tweet1, tweet3, tweet2, tweet4), "trumbb");

        assertEquals("expected singleton list", 2, writtenByTr.size());
        assertEquals("expected singleton list", 0, writtenByTr.indexOf(tweet3));
        assertEquals("expected singleton list", 1, writtenByTr.indexOf(tweet4));
        assertTrue("expected list to contain tweet", writtenByTr.contains(tweet3));
        assertTrue("expected list to contain tweet", writtenByTr.contains(tweet4));
    }

    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));

        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    @Test
    public void testInTimespan() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        Instant d3 = Instant.parse("2016-02-17T08:00:00Z");
        Instant d4 = Instant.parse("2016-02-17T09:30:00Z");
        Instant d5 = Instant.parse("2016-02-17T12:30:00Z");

        // test Multiple Tweets no Result.

        // all at left
        Tweet tweet3 = new Tweet(3, "alyssa", "so much?", d3);
        Tweet tweet4 = new Tweet(4, "bbitdiddle", "q #hype", d3);

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet3, tweet4), new Timespan(testStart, testEnd));

        assertTrue("expected empty list", inTimespan.isEmpty());

        // one at the start
        Instant d6 = Instant.parse("2016-02-17T09:00:00Z");
        Tweet tweet5 = new Tweet(5, "bbitdiddle", "p #hype", d6);
        List<Tweet> inTimespanAtStart = Filter.inTimespan(Arrays.asList(tweet3, tweet4, tweet5),
                new Timespan(testStart, testEnd));

        assertFalse("expected non-empty list", inTimespanAtStart.isEmpty());
        assertTrue("expected list to contain tweets", inTimespanAtStart.contains(tweet5));
        assertEquals("expected same order", 0, inTimespanAtStart.indexOf(tweet5));

        // test Multiple Tweets one Result.
        // one at end
        Tweet tweet6 = new Tweet(6, "alyssa", "so much?", d4);

        List<Tweet> inTimespan1 = Filter.inTimespan(Arrays.asList(tweet3, tweet4, tweet6),
                new Timespan(testStart, testEnd));

        assertFalse("expected no empty list", inTimespan1.isEmpty());
        assertTrue("expected list to contain a tweet", inTimespan1.contains(tweet6));
        assertEquals("expected same order", 0, inTimespan1.indexOf(tweet6));

        // one at mid
        Tweet tweet7 = new Tweet(7, "alyssa", "so much?", d5);

        List<Tweet> inTimespan2 = Filter.inTimespan(Arrays.asList(tweet3, tweet6, tweet7),
                new Timespan(testStart, testEnd));

        assertFalse("expected no empty list", inTimespan2.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan2.containsAll(Arrays.asList(tweet6)));
        assertEquals("expected same order", 0, inTimespan2.indexOf(tweet6));

    }

    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));

        // test no result
        List<Tweet> containingNoResult = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("ti"));
        assertTrue("expected empty list", containingNoResult.isEmpty());

        // test one word appears two times in a tweet
        Tweet tweet3 = new Tweet(3, "alyssa", "is it talk reasonable to talk about rivest so much?", d1);

        List<Tweet> containingTwoTimes = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3),
                Arrays.asList("talk"));

        assertEquals("expected a 3 list", 3, containingTwoTimes.size());
        assertTrue("expected list to contain tweets",
                containingTwoTimes.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
        assertEquals("expected same order", 0, containingTwoTimes.indexOf(tweet1));
        assertEquals("expected same order", 1, containingTwoTimes.indexOf(tweet2));
        assertEquals("expected same order", 2, containingTwoTimes.indexOf(tweet3));

        // test word the end of string
        List<Tweet> containingEndOfString = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3),
                Arrays.asList("much"));
        assertEquals("expected a 2 list", 2, containingEndOfString.size());
        assertTrue("expected list to contain tweets", containingEndOfString.containsAll(Arrays.asList(tweet1, tweet3)));
        assertEquals("expected same order", 0, containingEndOfString.indexOf(tweet1));

        // test two words
        List<Tweet> containingTwoWord = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3),
                Arrays.asList("talk", "much"));
        assertEquals("expected a 3 list", 3, containingTwoWord.size());
        assertTrue("expected list to contain tweets",
                containingTwoTimes.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
        assertEquals("expected same order", 0, containingTwoWord.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting (temporarily)
     * your version of Filter with the staff's version. DO NOT strengthen the spec
     * of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own that
     * you have put in Filter, because that means you're testing a stronger spec
     * than Filter says. If you need such helper methods, define them in a different
     * class. If you only need them in this test class, then keep them in this test
     * class.
     */

}
