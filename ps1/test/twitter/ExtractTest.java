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
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T14:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "sam", "Giao @peter GiaoGiao", d1);
    private static final Tweet tweet4 = new Tweet(4, "mike", "@peter Yo Yo check it", d1);
    private static final Tweet tweet5 = new Tweet(5, "peter", "peter parker @Xuning", d3);
    
    private static final Tweet tweet6 = new Tweet(6, "jake", "peter @Xuning parker @siyuan spider", d1);
    private static final Tweet tweet7 = new Tweet(7, "anderson", "@Xuning peter parker @Xuning", d1);
    private static final Tweet tweet8 = new Tweet(8, "kobe", "peter parker@mit.com", d1);
    private static final Tweet tweet9 = new Tweet(9, "franklin", "peter parker @ning@ning spider man", d1);
    private static final Tweet tweet10 = new Tweet(10, "curry", "peter parker @. spider man", d1);
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // This covers case size == 1 and number of Timestamp is 1
    @Test
    public void testGetTimespanUnsortedOneTweetsOneTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expect start", d1, timespan.getStart());
        assertEquals("expect end", d1, timespan.getEnd());
    }
    
    // This covers case size == 2 and number of Timestamp is 1
    @Test
    public void testGetTimespanUnsortedTwoTweetsOneTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    // This covers case size == 2 and number of Timestamp is 2
    @Test
    public void testGetTimespanUnsortedTwoTweetsTwoTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    // This covers case size more than two and number of Timestamp is 1
    @Test
    public void testGetTimespanUnsortedMultipleTweetsOneTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet4, tweet3, tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }

    // This covers case size more than two and number of Timestamp is 2
    @Test
    public void testGetTimespanUnsortedMultipleTweetsTwoTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3, tweet2, tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    // This covers case size more than two and number of Timestamp more than 2
    @Test
    public void testGetTimespanUnsortedMultipleTweetsMultipleTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet5, tweet2, tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    // No person mentioned case
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    // One person mentioned in middle
    @Test
    public void testGetMentionedUsersOneMiddleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
          
        assertEquals("expected set size", 1, mentionedUsers.size());
        assertTrue("expected element", mentionedUsers.stream().anyMatch("peter"::equalsIgnoreCase));
    }
    
    // One person mentioned at the beginning
    @Test
    public void testGetMentionedUsersOneStartMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        //System.out.println(mentionedUsers);
        assertEquals("expected set size", 1, mentionedUsers.size());
        assertTrue("expected element", mentionedUsers.stream().anyMatch("peter"::equalsIgnoreCase));
    }
    
    // One person mentioned at last
    @Test
    public void testGetMentionedUsersOneLastMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
              
        assertEquals("expected set size", 1, mentionedUsers.size());
        assertTrue("expected element", mentionedUsers.stream().anyMatch("Xuning"::equalsIgnoreCase));
    }
    
    // Two different person mentioned in middle
    @Test
    public void testGetMentionedUsersTwoDifferentMiddleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
              
        assertEquals("expected set size", 2, mentionedUsers.size());
        assertTrue("expected element", mentionedUsers.stream().anyMatch("Xuning"::equalsIgnoreCase));
        assertTrue("expected element", mentionedUsers.stream().anyMatch("siyuan"::equalsIgnoreCase));
    }
    
    // Two same person mentioned in middle
    @Test
    public void testGetMentionedUsersTwoSameMiddleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7));
              
        assertEquals("expected set size", 1, mentionedUsers.size());
        assertTrue("expected element", mentionedUsers.stream().anyMatch("Xuning"::equalsIgnoreCase));
    }
    
    // Mentioned in email form
    @Test
    public void testGetMentionedUsersEmail() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet8));
              
        assertEquals("expected set size", 0, mentionedUsers.size());
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    // Consecutive @ form
    @Test
    public void testGetMentionedDoubleAt() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet9));
              
        assertEquals("expected set size", 0, mentionedUsers.size());
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    // @ followed by . 
    @Test
    public void testGetMentionedDoubleAtDot() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet10));
              
        assertEquals("expected set size", 0, mentionedUsers.size());
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    // Mixed Case
    @Test
    public void testGetMentionedDoubleMixCase() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(
                tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8, tweet9, tweet10));
              
        assertEquals("expected set size", 3, mentionedUsers.size());
        assertTrue("expected element", mentionedUsers.stream().anyMatch("Xuning"::equalsIgnoreCase));
        assertTrue("expected element", mentionedUsers.stream().anyMatch("siyuan"::equalsIgnoreCase));
        assertTrue("expected element", mentionedUsers.stream().anyMatch("peter"::equalsIgnoreCase));
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
