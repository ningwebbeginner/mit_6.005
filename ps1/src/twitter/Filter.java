/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> findList = new ArrayList<>();
        for(Tweet tweet:tweets) {
            if(tweet.getAuthor().toLowerCase().equals(username.toLowerCase())) {
                findList.add(tweet);
            }
        }
        return Collections.unmodifiableList(findList);
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> findList = new ArrayList<>();
        Instant starTime = timespan.getStart();
        Instant endTime = timespan.getEnd();
        for(Tweet tweet:tweets) {
            Instant twtime = tweet.getTimestamp();
            if(twtime.equals(starTime)
               || twtime.equals(endTime)
               || (twtime.isAfter(timespan.getStart()) && twtime.isBefore(timespan.getEnd()))){
                findList.add(tweet);
            }
        }
        return Collections.unmodifiableList(findList);
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> findList = new ArrayList<>();
        for(Tweet tweet:tweets) {
            String textSplitBySpace = tweet.getText();
            String textDeleteEnd = textSplitBySpace.substring(0, textSplitBySpace.length()-1);
            String[] textArray = textDeleteEnd.split(" ");
            for(String wordInTxt:textArray) {
                for(String word:words) {
                    if(wordInTxt.equals(word) && 
                            !findList.contains(tweet)) {
                        findList.add(tweet);
                    } 
                }
            }

            
        }
        return Collections.unmodifiableList(findList);
    }

}
