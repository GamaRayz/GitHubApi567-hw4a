import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class GitHubUserFetcher {

    // Function used to fetch repository names and counts their commits.
    public static void fetchGitHubRespository(String userID) {
        // Allows parsing an invalid JSON response to handle errors rather than system crash silently.
        try {
            String repoURL = "https://api.github.com/users/" + userID + "/repos";
            JSONArray repoFetchArray = fetchJSONArrayFromRepoURL(repoURL);

            // If there is an issue or error, the system will print as such.
            if (repoFetchArray == null) {
                System.out.print("There's been an error fetching the repositories.");
                return;
            }
            // Looking for the length of returned list.
            for (int i = 0; i < repoFetchArray.length(); i++) {
                String repoFetchName = repoFetchArray.getJSONObject(i).getString("Name");
                int countCommit = getCountCommit(userID, repoFetchName);
                System.out.println("Repository: " + repoFetchName + "Number of user commits: " + countCommit);
            }
        // Used to catch exceptions from network errors and help prevent crashing.
        } catch (Exception e) { //Throws exception.
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Function helper to fetch the JSON array from any URL. Easily extract data.
    private static JSONArray fetchJSONArrayFromRepoURL(String urlAPI) {
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
            // Converting JSON string into JSONArray with raw JSON response string to an object in JSONArray.
            return new JSONArray (response.toString()); // Converts StringBuilder (API response) to a string.
        } catch (Exception e) { // Throws exception.
            return null; // Return null if error.
        }
    }
    // Function to gather repo count of commits.
    private static int getCountCommit(String userID, String repoFetchName) {
        try {
            // Using URI to construct the commit API URL.
            URI countCommitURI = new URI("https", "api.github.com", "/repos/" + userID + "/" + repoFetchName + "/commits", null);
            URI countCommitURL = countCommitURI.toURL();
            // Fetch list of user commits as a JSON Array.
            JSONArray commitArray = fetchJSONArrayFromRepoURL(countCommitURL);
            return (commitArray != null) ? commitArray.length() : 0;
        } catch (Exception e) { // Throws exception.
            return 0;
        }
    }
}

public static void main(String[] args) {
    String userID = "Gamarayz"; // Provided user example.
    fetchGitHubRepository(userID); // Fetch and display repo name with commit count.
}