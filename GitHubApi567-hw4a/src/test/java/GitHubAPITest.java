/**
 * Assignment 4a-c on 2/26/25
 *
 * @author Ray/
 */

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Class provides test cases for GitHubAPI.java.
class GitHubAPITest {
    // Test Case for checking for throw and return for valid JSONArray.
    @Test
    void testfetchGitHubRepository() {
        JSONArray result = GitHubAPI.fetchGitHubRepository("Gamarayz");
        assertNotNull(result, "Fetching repos should return a null JSONArray.");
    }

    // Test Case to validate existing repo.
    @Test
    void testGetCountCommit_ValidRepo(){
        int commitCount = GitHubAPI.getCountCommit("Gamarayz", "helloworld");
        assertEquals(0, commitCount, "Commit count should be positive for validated repo.");
    }

    // Test Case to ensure repo fetch for any repos that do not exist returns a 0.
    @Test
    void testGetCountCommit_InvalidRepo() {
        int commitCount = GitHubAPI.getCountCommit("Gamarayz", "NoRepo");
        assertEquals(0, commitCount, "Commit count is 0 when there is no repo(s).");
    }

    // Test Case attempting to fetch from an invalid URL returns null.
    @Test
    void testFetchJSONArrayFromRepoURL_Invalid() {
        JSONArray result = GitHubAPI.fetchGitHubRepository("https://api.github.com/invalid-url");
        assertNull(result, "Fetching from invalid url returns a null.");
    }
}