/*
*
* @Author : Yossep BINYOUM
* @Brief  : Based on Mark Watson - Practical AI with Java.
* Cet exemple est très similaire au programme de l'API OpenAI présentés dans le chapitre précédent. 
* Nous nous appuyons sur la documentation de Google Developer : https://ai.google.dev/gemini-api/docs. 
* Nous implémentons des méthodes pour les suggestions et les complétions en utilisant également l'outil 
* de recherche de Google.
* 
* CLASS: org.example.GeminiCompletions
* 
* Fournit un ensemble de méthodes statiques permettant d'interagir avec l'API Google Gemini et d'effectuer 
* des opérations utilitaires liées aux chaînes de caractères et aux fichiers.
* 
*
* ******************************************
* * FUNCTION: getCompletion(String prompt) *
* ******************************************
*
*   Sends a text prompt to the Gemini API and returns the generated text completion.
*
*   INPUT       :  A String ‘prompt’.
*   REQUIRES    : The ‘GEMINI_API_KEY’ environment variable must be set.
*   ACTION:
*       * Constructs an HTTP POST request to the ‘gemini-3.5-flash’ model endpoint.
*       * Packages the prompt into the required JSON body.
*   
*   ON SUCCESS  : Returns the ‘text’ field from the API’s JSON response as a String.
*   ON FAILURE  : Throws an IOException if the API key is not found.
*
* 
* ****************************************************
* * FUNCTION: getCompletionWithSearch(String prompt) *
* ****************************************************
*
*   Sends a text prompt to the Gemini API using the search tool and returns the generated text 
*   completion.
*
*   INPUT       : A String ‘prompt’.
*   REQUIRES    : The ‘GEMINI_API_KEY’ environment variable must be set.
*   ACTION:
*       * Constructs an HTTP POST request to the ‘gemini-2.5-flash’ model endpoint.
*       * Packages the prompt into the required JSON body.
*
*   ON SUCCESS  : Returns the ‘text’ field from the API’s JSON response as a String.
*   ON FAILURE  : Throws an IOException if the API key is not found.
*
*
* **********************************************  
* * FUNCTION: readFileToString(String filePath)*
* **********************************************
*
*   Reads the entire contents of a specified file into a single string.
*
*   INPUT       :A String ‘filePath’ pointing to a file.
*   ACTION:
*
*       * Reads all bytes from the file.
*       * Escapes all double-quote (“) characters found in the content.
*   
*   ON SUCCESS  : Returns the file’s contents as a String.
*   ON FAILURE  :Throws an IOException if the file cannot be found or read.
*
*
* *********************************
* * FUNCTION: replaceSubstring(…) *
* *********************************
*
*   A utility function that replaces all occurrences of a substring within a string.
*
*   INPUT       : An original string. The substring to replace. The replacement string.
*   OUTPUT      : Returns the new string with replacements made
*
* **************************
* * FUNCTION: promptVar(…) *
* **************************
*
*   A simple templating utility for substituting a placeholder in a string.
*
*   INPUT       : A template string. A placeholder name to find. The value to substitute.
*   OUTPUT      :Returns the new string with the placeholder replaced by the value.
*
*/
package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;


public class GeminiCompletions {

    private static final String DEFAULT_MODEL = "gemini-3.5-flash";
    public static String model = DEFAULT_MODEL;


    public static void main(String[] args) throws Exception 
    {
        var prompt = "How do you say 'Hello Good morning' in french ?";
        String completion = getCompletion (prompt);

        System.out.println("Completion : " + completion);
    }


    public static String getCompletion (String prompt) throws Exception
    {
        var jsonBody = buildRequestBody(prompt);
        return executeRequest(jsonBody);
    }

    public static String getCompletionWithSearch(String prompt) throws Exception
    {
        var textPart    = new JSONObject().put("text", prompt);
        var parts       = new JSONArray().put(textPart);
        var content     = new JSONObject().put("parts", parts);
        var contents    = new JSONArray().put(content);

        var googleSearchTool    = new JSONObject().put("google_search", new JSONObject());
        var tools               = new JSONArray().put(googleSearchTool);

        var body = new JSONObject()
                .put("contents", contents)
                .put("tools", tools);

        return executeRequest(body.toString());
    }

    private static String buildRequestBody(String prompt) 
    {
        var textPart = new JSONObject().put("text", prompt);
        var parts = new JSONArray().put(textPart);
    
        var content = new JSONObject().put("parts", parts);
        var contents = new JSONArray().put(content);
    
        return new JSONObject().put("contents", contents).toString();
    }

    private static String executeRequest(String jsonBody) throws Exception 
    {
        String API_KEY = System.getenv("GEMINI_API_KEY");
        
        if (API_KEY == null || API_KEY.isEmpty())
        {
            throw new IOException 
                    ("GEMINI_API_KEY environment variable is not set properly");
        } 

        var uri = URI.create ("https://generativelanguage.googleapis.com/v1beta/models/" + 
                    model + ":generateContent?key=" + API_KEY);  

        var request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response;
        try (var client = HttpClient.newHttpClient())
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        
        if (response.statusCode() != 200) 
        {
            throw new IOException("Gemini API request failed (HTTP %d): %s"
                    .formatted(response.statusCode(), response.body()));
        }

        var jsonObject = new JSONObject(response.body());
        return jsonObject.getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0).
                        getString("text");
    }

    public String getGreeting() {
        return "Hello World!";
    }

     // read the contents of a file path into a Java string
    public static String readFileToString(String filePath) throws IOException {
        return Files.readString(Path.of(filePath)).replace("\"", "\\\"");
    }

    public static String replaceSubstring(String originalString, String substringToReplace, String replacementString) {
        return originalString.replace(substringToReplace, replacementString);
    }

    public static String promptVar(String prompt0, String varName, String varValue) {
        String prompt = replaceSubstring(prompt0, varName, varValue);
        return replaceSubstring(prompt, varName, varValue);
    }
}
