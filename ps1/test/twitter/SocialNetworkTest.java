/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import java.util.stream.Collectors;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T13:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "peter", "@mark @peter", d1);
    private static final Tweet tweet2 = new Tweet(2, "mark", "Giao Giao", d2);
    private static final Tweet tweet3 = new Tweet(3, "peter", "@JJ @mike", d2);
    private static final Tweet tweet4 = new Tweet(4, "JJ", "@mike @mike", d2);
    private static final Tweet tweet5 = new Tweet(5, "JJ", "@sam", d2);
    private static final Tweet tweet6 = new Tweet(6, "stussy", "@MIKE @NING", d2);
    private static final Tweet tweet7 = new Tweet(7, "peter", "@mike @mark", d2);
    private static final Tweet tweet8 = new Tweet(8, "mark", "@jake", d2);
    private static final Tweet tweet9 = new Tweet(9, "peter", "jacky", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // No tweets
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    // 1. One user one tweet and zero @
    @Test
    public void testGuessFollowsGraphOneUserOneTweetZeroAt() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        Map<String, Set<String>> followsGraphLowerCase = followsGraph.keySet().stream()
                .collect(Collectors.toMap(key -> key.toLowerCase(),
                        key -> followsGraph.get(key).stream().map(String::toLowerCase).collect(Collectors.toSet())));

        assertTrue("expected empty graph", followsGraphLowerCase.isEmpty() || followsGraphLowerCase.get(tweet2.getAuthor()).isEmpty());
    }
    
    // 2. One user one tweet and one @
    @Test
    public void testGuessFollowsGraphOneUserOneTweetOneAt() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        Map<String, Set<String>> followsGraphLowerCase = followsGraph.keySet().stream()
                .collect(Collectors.toMap(key -> key.toLowerCase(),
                        key -> followsGraph.get(key).stream().map(String::toLowerCase).collect(Collectors.toSet())));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertTrue("expected graph containing keys", followsGraphLowerCase.keySet().containsAll(Arrays.asList("peter")));
        assertTrue("expected Map[peter] to contain tweets", followsGraph.get("peter").containsAll(Arrays.asList("mark")));
        assertFalse("expected not include self", followsGraph.get("peter").contains("peter"));
    }
       
    // 3. One user one tweet and multiple different @
    @Test
    public void testGuessFollowsGraphOneUserOneTweetMultipleDifferentAt() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
        Map<String, Set<String>> followsGraphLowerCase = followsGraph.keySet().stream()
                .collect(Collectors.toMap(key -> key.toLowerCase(),
                        key -> followsGraph.get(key).stream().map(String::toLowerCase).collect(Collectors.toSet())));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertTrue("expected graph containing keys", followsGraphLowerCase.keySet().containsAll(Arrays.asList("peter")));
        assertTrue("expected Map[peter] to contain tweets", followsGraph.get("peter").containsAll(Arrays.asList("jj", "mike")));
        assertFalse("expected not include self", followsGraph.get("peter").contains("peter"));
    }
    
    // 4. One user one tweet and multiple same @
    @Test
    public void testGuessFollowsGraphOneUserOneTweetMultipleSameAt() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));
        Map<String, Set<String>> followsGraphLowerCase = followsGraph.keySet().stream()
                .collect(Collectors.toMap(key -> key.toLowerCase(),
                        key -> followsGraph.get(key).stream().map(String::toLowerCase).collect(Collectors.toSet())));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertTrue("expected graph containing keys", followsGraphLowerCase.keySet().containsAll(Arrays.asList("jj")));
        assertTrue("expected Map[JJ] to contain tweets", followsGraph.get("jj").containsAll(Arrays.asList("mike")));
        assertFalse("expected not include self", followsGraph.get("jj").contains("jj"));
    }
    
    // 5. One user multiple tweet some has @ some don't
    @Test
    public void testGuessFollowsGraphOneUserMultipleTweetSomeAt() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet8));
        Map<String, Set<String>> followsGraphLowerCase = followsGraph.keySet().stream()
                .collect(Collectors.toMap(key -> key.toLowerCase(),
                        key -> followsGraph.get(key).stream().map(String::toLowerCase).collect(Collectors.toSet())));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertTrue("expected graph containing keys", followsGraphLowerCase.keySet().containsAll(Arrays.asList("mark")));
        assertTrue("expected Map[mark] to contain tweets", followsGraph.get("mark").containsAll(Arrays.asList("jake")));
        assertFalse("expected Map[mark] not include self", followsGraph.get("mark").contains("mark"));
    }
    
    // 6. One user multiple tweet multiple @ for each tweet(one in both)
    @Test
    public void testGuessFollowsGraphOneUserMultipleTweetOneinbothAt() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet7));
        Map<String, Set<String>> followsGraphLowerCase = followsGraph.keySet().stream()
                .collect(Collectors.toMap(key -> key.toLowerCase(),
                        key -> followsGraph.get(key).stream().map(String::toLowerCase).collect(Collectors.toSet())));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertTrue("expected graph containing keys", followsGraphLowerCase.keySet().containsAll(Arrays.asList("peter")));
        assertTrue("expected Map[peter] to contain tweets", followsGraph.get("peter").containsAll(Arrays.asList("mark", "mike")));
        assertFalse("expected Map[peter] not include self", followsGraph.get("peter").contains("peter"));
    }
    
    // 7. multiple user multiple tweet no @
    @Test
    public void testGuessFollowsGraphMultipleUserMultipleTweetNoAt() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet9));

        assertTrue("expected empty graph", followsGraph.isEmpty() 
                                        || followsGraph.get(tweet2.getAuthor()).isEmpty()
                                        || followsGraph.get(tweet9.getAuthor()).isEmpty());
    }
    
    // 8. multiple user multiple tweet some has @ some don't
    @Test
    public void testGuessFollowsGraphMultipleUserMultipleTweetSomeAt() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet3));
        Map<String, Set<String>> followsGraphLowerCase = followsGraph.keySet().stream()
                .collect(Collectors.toMap(key -> key.toLowerCase(),
                        key -> followsGraph.get(key).stream().map(String::toLowerCase).collect(Collectors.toSet())));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertTrue("expected graph containing keys", followsGraphLowerCase.keySet().containsAll(Arrays.asList("mark", "peter")));
        assertTrue("expected Map[mark] to contain tweets", followsGraph.get("mark").isEmpty());
        assertTrue("expected Map[peter] to contain tweets", followsGraph.get("peter").containsAll(Arrays.asList("jj", "mike")));
        assertFalse("expected Map[mark] not include self", followsGraph.get("mark").contains("mark"));
        assertFalse("expected Map[peter] not include self", followsGraph.get("peter").contains("peter"));
    }
    
    // 9. Mixed case
    @Test
    public void testGuessFollowsGraphMixedCase() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(
                tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8));
        Map<String, Set<String>> followsGraphLowerCase = followsGraph.keySet().stream()
                .collect(Collectors.toMap(key -> key.toLowerCase(),
                        key -> followsGraph.get(key).stream().map(String::toLowerCase).collect(Collectors.toSet())));
        
        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertTrue("expected graph containing keys", followsGraphLowerCase.keySet().containsAll(Arrays.asList("peter", "mark", "jj", "stussy")));
        assertTrue("expected Map[peter] to contain tweets", followsGraph.get("peter").containsAll(Arrays.asList("mark", "jj", "mike")));
        assertTrue("expected Map[mark] to contain tweets", followsGraph.get("mark").containsAll(Arrays.asList("jake")));
        assertTrue("expected Map[JJ] to contain tweets", followsGraph.get("jj").containsAll(Arrays.asList("mike", "sam")));
        assertTrue("expected Map[stussy] to contain tweets", followsGraph.get("stussy").containsAll(Arrays.asList("mike", "ning")));
        
        assertFalse("expected Map[peter] not include self", followsGraph.get("peter").contains("peter"));
        assertFalse("expected Map[mark] not include self", followsGraph.get("mark").contains("mark"));
        assertFalse("expected Map[JJ] not include self", followsGraph.get("jj").contains("jj"));
        assertFalse("expected Map[stussy] not include self", followsGraph.get("stussy").contains("stussy"));
    }
    
 
    private static final Set<String> stussy = new HashSet<>(Arrays.asList("peter", "mike", "Jake"));
    private static final Set<String> james = new HashSet<>();
    private static final Set<String> mike = new HashSet<>(Arrays.asList("peter", "james", "anderson"));
    private static final Set<String> jake = new HashSet<>(Arrays.asList("peter", "james", "mike"));
    private static final Set<String> peter = new HashSet<>(Arrays.asList("stussy"));
    
    // Empty Case 
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    // one user multiple followers
    @Test
    public void testInfluencersOneUserMultipleFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("stussy", stussy);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> influencersLower = new ArrayList<>();
        
        for (int i = 0; i < influencers.size(); i++) {
            influencersLower.add(influencers.get(i).toLowerCase());
        }

        assertFalse("expected non-empty list", influencersLower.isEmpty());
        assertTrue("expect first", influencersLower.subList(0, 3).containsAll(Arrays.asList("peter", "mike", "jake")));
    }
    
    // two user only one of them has followers
    @Test
    public void testInfluencerstwoUserOneHasFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("stussy", stussy);
        followsGraph.put("james", james);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> influencersLower = new ArrayList<>();
        
        for (int i = 0; i < influencers.size(); i++) {
            influencersLower.add(influencers.get(i).toLowerCase());
        }
        
        assertFalse("expected non-empty list", influencersLower.isEmpty());
        assertTrue("expect first", influencersLower.subList(0, 3).containsAll(Arrays.asList("peter", "mike", "jake")));
    }
    
    // two user only both has some same followers
    @Test
    public void testInfluencerstwoUserSomeSameFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("stussy", stussy);
        followsGraph.put("jake", jake);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> influencersLower = new ArrayList<>();
        
        for (int i = 0; i < influencers.size(); i++) {
            influencersLower.add(influencers.get(i).toLowerCase());
        }
        
        assertFalse("expected non-empty list", influencersLower.isEmpty());
        assertTrue("expect first", influencersLower.subList(0, 2).containsAll(Arrays.asList("peter", "mike")));
        assertTrue("expect second", influencersLower.subList(2, 4).containsAll(Arrays.asList("james", "jake")));
    }
    
    // Mixed case
    @Test
    public void testInfluencersMixedCase() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("stussy", stussy);
        followsGraph.put("james", james);
        followsGraph.put("mike", mike);
        followsGraph.put("jake", jake);
        followsGraph.put("peter", peter);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> influencersLower = new ArrayList<>();
        
        for (int i = 0; i < influencers.size(); i++) {
            influencersLower.add(influencers.get(i).toLowerCase());
        }
        
        assertFalse("expected non-empty list", influencersLower.isEmpty());
        assertTrue("expect first", influencersLower.subList(0, 1).containsAll(Arrays.asList("peter")));
        assertTrue("expect second", influencersLower.subList(1, 3).containsAll(Arrays.asList("james", "mike")));
        assertTrue("expect third", influencersLower.subList(3, 6).containsAll(Arrays.asList("jake", "anderson", "stussy")));
    }
    
    /*
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    */
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
