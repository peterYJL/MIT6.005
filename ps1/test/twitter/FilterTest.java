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
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T09:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "sam", "Giao @peter GiaoGiao", d3);
    private static final Tweet tweet4 = new Tweet(4, "alyssa", "Yi Giao wuli GiaoGiao", d3);
    private static final Tweet tweet5 = new Tweet(5, "alyssa", "Wuli kunkun Ji ni tai mei", d4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // One tweet and zero result
    @Test
    public void testWrittenByOneTweetsZeroResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "peter");
        
        assertEquals("expected list size", 0, writtenBy.size());
    }
    
    // One tweet and one result
    @Test
    public void testWrittenByOneTweetsOneResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "alyssa");
        
        assertEquals("expected list size", 1, writtenBy.size());
        assertTrue("expected first", tweet1.equals(writtenBy.get(0)));
    }
    
    // Multiple tweet and zero result
    @Test
    public void testWrittenByMultipleTweetsZeroResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "peter");
        
        assertEquals("expected list size", 0, writtenBy.size());
    }
    
    // Multiple tweet and one result
    @Test
    public void testWrittenByMultipleTweetsOneResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");
        
        assertEquals("expected list size", 1, writtenBy.size());
        assertTrue("expected first", tweet1.equals(writtenBy.get(0)));
    }
    
    // Multiple tweet and Multiple result
    @Test
    public void testWrittenByMultipleTweetsMultipleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "alyssa");
        
        assertEquals("expected list size", 3, writtenBy.size());
        assertTrue("expected first", tweet1.equals(writtenBy.get(0)));
        assertTrue("expected second", tweet4.equals(writtenBy.get(1)));
        assertTrue("expected third", tweet5.equals(writtenBy.get(2)));
    }
    
    // Upper case test
    @Test
    public void testWrittenByUpperMultipleTweetsOneResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "ALYSSA");

        assertEquals("expected list size", 1, writtenBy.size());
        assertTrue("expected first", tweet1.equals(writtenBy.get(0)));
    }
    
    // Lower case test
    @Test
    public void testWrittenByLowerMultipleTweetsOneResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet2, tweet3, tweet5), "alyssa");
        
        assertEquals("expected list size", 1, writtenBy.size());
        assertTrue("expected first", tweet5.equals(writtenBy.get(0)));
    }
    
     
    
    
    
    
    
    // One tweet and zero result
    @Test
    public void testInTimespanOneTweetsZeroResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet3), new Timespan(testStart, testEnd));
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    // One tweet and one result
    @Test
    public void testInTimespanOneTweetsOneResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet2));
    }
    
    // Multiple tweets and zero result
    @Test
    public void testInTimespanMultipleTweetsZeroResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet3, tweet4), new Timespan(testStart, testEnd));
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    // Multiple tweets and one result
    @Test
    public void testInTimespanMultipleTweetsOneResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet3), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    // Multiple tweets and Multiple result
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet5), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2, tweet5)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
        assertEquals("expected same order", 1, inTimespan.indexOf(tweet2));
        assertEquals("expected same order", 2, inTimespan.indexOf(tweet5));
    }
    
    
    
    
    
    // One tweet one word zero result
    @Test
    public void testContainingOneTweetsOneWordsZeroResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("right"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    // One tweet one word one result
    @Test
    public void testContainingOneTweetsOneWordsOneResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expect list to contain tweets", containing.containsAll(Arrays.asList(tweet1)));
        assertEquals("expect same order", 0, containing.indexOf(tweet1));
    }
    
    // Multiple tweets one word zero result
    @Test
    public void testContainingMultipleTweetsOneWordsZeroResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet3, tweet4), Arrays.asList("sun"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    // Multiple tweets Multiple word zero result
    @Test
    public void testContainingMultipleTweetsMultipleWordsZeroResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet3, tweet4), Arrays.asList("sun", "rice", "chair"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    // Multiple tweets one word one result
    @Test
    public void testContainingMultipleTweetsOneWordsOneResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet3, tweet4), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expect list to contain tweets", containing.containsAll(Arrays.asList(tweet1)));
        assertEquals("expect same order", 0, containing.indexOf(tweet1));
    }
    
    // Multiple tweets Multiple word one result
    @Test
    public void testContainingMultipleTweetsMultipleWordsOneResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet3, tweet4), Arrays.asList("talk", "about", "much"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expect list to contain tweets", containing.containsAll(Arrays.asList(tweet1)));
        assertEquals("expect same order", 0, containing.indexOf(tweet1));
    }
    
    // Multiple tweets one word Multiple result
    @Test
    public void testContainingMultipleTweetsOneWordsMultipleResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet3, tweet4), Arrays.asList("Giao"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expect list to contain tweets", containing.containsAll(Arrays.asList(tweet3, tweet4)));
        assertEquals("expect same order", 0, containing.indexOf(tweet3));
        assertEquals("expect same order", 1, containing.indexOf(tweet4));
    }
    
    // Multiple tweets Multiple word Multiple result
    @Test
    public void testContainingMultipleTweetsMultipleWordsMultipleResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet3, tweet4), Arrays.asList("Giao", "talk", "much"));
        for (Tweet tweet : containing) {
            System.out.println(tweet);
        }
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expect list to contain tweets", containing.containsAll(Arrays.asList(tweet3, tweet4)));
        assertEquals("expect same order", 0, containing.indexOf(tweet1));
        assertEquals("expect same order", 1, containing.indexOf(tweet3));
        assertEquals("expect same order", 2, containing.indexOf(tweet4));
    }
    
    
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
