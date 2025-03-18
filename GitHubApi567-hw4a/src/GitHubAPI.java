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
        JSONArray repoFetchArray = fetchGitHubRepository(userID);

        if (repoFetchArray == null) {
            System.out.print("There's been an error fetching the repositories.");
            return;
        }

        for (int i = 0; i < repoFetchArray.length(); i++) {
            String repoFetchName = repoFetchArray.getJSONObject(i).getString("name");
            int countCommit = getCountCommit(userID, repoFetchName);
            System.out.println("Repository: " + repoFetchName + "Number of user commits: " + countCommit);
        }
    }

    public static JSONArray fetchGitHubRepository(String userID) {
        try {
            String repoURL = "https://api.github.com/users/" + userID + "/repos";
            return fetchJSONArrayFromRepoURL(repoURL);
            } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
            }
        }

    private static JSONArray fetchJSONArrayFromRepoURL(String urlAPI) {
        try {
            URL url = new URL(urlAPI);
            return fetchJSONArrayFromRepoURL(url);
        } catch (Exception e) {
            return null;
        }
    }

    private static JSONArray fetchJSONArrayFromRepoURL(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return new JSONArray(response.toString());
        } catch (Exception e) {
            return null;
        }
    }
    public static int getCountCommit(String userID, String repoFetchName) {
        try {
            URI countCommitURI = new URI("https", "api.github.com", "/repos/" + userID + "/" + repoFetchName + "/commits", null);
            URL countCommitURL = countCommitURI.toURL();

            JSONArray commitArray = fetchJSONArrayFromRepoURL(countCommitURL);
            return (commitArray != null) ? commitArray.length() : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}