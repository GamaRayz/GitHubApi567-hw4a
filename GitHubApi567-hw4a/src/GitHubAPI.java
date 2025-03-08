/**
 * Assignment 4a-c on 2/26/25
 * @author Ray/
 */
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * This class retrieves a user's GitHub from the site and displays their name and commits.
 */
public class GitHubAPI {
    public static void main(String[] args) {
        String userID = "Gamarayz";
        fetchGitHubRepository(userID);
    }
    public static void fetchGitHubRepository(String userID) {
        try {
            String repoURL = "https://api.github.com/users/" + userID + "/repos";
            JSONArray repoFetchArray = fetchJSONArrayFromRepoURL(repoURL);

            // If there is an issue or error, the system will print as such.
            if (repoFetchArray == null) {
                System.out.print("There's been an error fetching the repositories.");
                return;
            }

            for (int i = 0; i < repoFetchArray.length(); i++) {
                String repoFetchName = repoFetchArray.getJSONObject(i).getString("Name");
                int countCommit = getCountCommit(userID, repoFetchName);
                System.out.println("Repository: " + repoFetchName + "Number of user commits: " + countCommit);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static JSONArray fetchJSONArrayFromRepoURL(String urlAPI) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return new JSONArray (response.toString());
        } catch (Exception e) {
            return null;
        }
    }
    private static int getCountCommit(String userID, String repoFetchName) {
        try {
            // Using URI to construct the commit API URL.
            URI countCommitURI = new URI("https", "api.github.com", "/repos/" + userID + "/" + repoFetchName + "/commits", null);
            URL countCommitURL = countCommitURI.toURL();

            JSONArray commitArray = fetchJSONArrayFromRepoURL(countCommitURL);
            return (commitArray != null) ? commitArray.length() : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}