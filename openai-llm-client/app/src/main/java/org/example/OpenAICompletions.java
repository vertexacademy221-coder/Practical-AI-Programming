/*
*   @Author : Yossep BINYOUM
*   @Date   : 11/07/2026
*   @Brief : 
*   
*   Based on Mark Watson book (Practical AI).
*   
*   Dans le développement de systèmes d’IA concrets, les modèles de langage à grande échelle (LLM), 
*   tels que ceux proposés par OpenAI, Anthropic et Hugging Face, se sont imposés comme des outils essentiels 
*   pour de nombreuses applications, notamment le traitement, la génération et la compréhension du langage naturel. 
*   Ces modèles, basés sur des architectures d’apprentissage profond, renferment une mine de connaissances et de capacités de calcul. 
*   
*   Nous allons ici aborder les bases qui te permettront, cher lecteur, de te lancer dans l’utilisation des APIs OpenAI 
*   pour des tâches de complétion de texte en code Java. 
*   
*   La librairie OpenAI-llm-client est concu pour interagir avec l'API d'OpenAI afin d'accepter un prompt sous forme de 
*   chaine de caractere et de retourne la reponse du modele.
*   
*   METHODES : 
*   
*   OpenAICompletions::getCompletion, effectue les etapes suivantes :
* 
*       1. Initialisation du client : Nous utilisons une seule instance d'OpenAIClient initialisee via **OpenAIOkHttpClient.fromEnv()** 
*           qui recupere automatiquement votre cle API depuis les variables d'environnement de votre machine.
*
*       2. Structure des parametres de la requete : Construit un objet de la classe ChatCompletionCreateParams en utilisant le builder()
*           , specifiant le message de l'utilisateur passe en parametre de la methode getCompletion et enfin en specifiant le modele 
*           ici "GPT_5_MINI". La liste des modeles est disponibles ic : https://developers.openai.com/api/docs/models/all
*
*       3. Envoie de la requete : Fait appel a la methode, client.chat().completions().create(params) de l'instance qui envoie de maniere 
*       synchrone la requete via OKHttp et attend pour recevoir la reponse sous forme d'ojet ChatCompletion (en passant en un objet au format JSON)
*
*       4. Parser la reponse : On recupere le premier choix parmi les messages et ensuite extrait le contenu (chaine de caracteres).
*  
*   Dependances :
*   
*       java.io.IOException
*       java.nio.file.Files
*       java.nio.file.Path
*       com.openai.client.OpenAIClient
*       com.openai.client.okhttp.OKHttpClient
*       com.openai.models.ChatModel;
*       com.openai.models.chat.completions.ChatCompletion;
*       com.openai.models.chat.completions.ChatCompletionCreateParams;
*   
*   ERRORS : 
*   
*   Lors de l'execution du programme principal nous avons l'erreur suivante :
*   "Exception in thread "main" com.openai.errors.RateLimitException: 
*   429: You exceeded your current quota, please check your plan and billing details. 
*   For more information on this error, 
*   
*   
*   read the docs: https://platform.openai.com/docs/guides/error-codes/api-errors.
*   WebSocket mode errors
*   
*   Le code d'erreur 429 : 
*   
*   Ce message d'erreur indique que vous avez atteint votre limite d'utilisation mensuelle pour l'API ou, pour les clients disposant de crédits prépayés, 
*   que vous avez épuisé tous vos crédits. Vous pouvez consulter votre limite d'utilisation maximale sur la page des limites. 
*   Cela peut se produire pour plusieurs raisons, notamment :

*       1. Vous utilisez un service à fort volume ou complexe qui consomme beaucoup de crédits ou de jetons.
*       2. Votre budget mensuel est trop faible par rapport à l'utilisation de votre organisation.
*       3. Votre budget mensuel est trop faible par rapport à l'utilisation de votre projet.
*   
*/

package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;


public class OpenAICompletions 
{

    // Reuse a single client instance (connection pool + thread pool efficiency)
    private static final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public static void main(String[] args) 
    {

        var prompt = "Translate the following English text to French: 'Hello, how are you?'";
        var completion = getCompletion(prompt);

        System.out.println("Completion :" + completion);
    }

    public static String getCompletion (String prompt)
    {
        var params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)
                .model(ChatModel.GPT_5_MINI)
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(params);
        System.out.println(chatCompletion);

        var content = chatCompletion.choices().get(0).message().content().orElse("");
        System.out.println(content);
        return content;   
    }

    /*
     * Utilities for using the OpenAI API
    */
    // Read the contents of a file into a java String
    public static String readFileToString(String filePath) throws IOException
    {
        return Files.readString(Path.of(filePath));
    }

    public static String replaceSubString(String originalString, 
                                    String substringToReplace, 
                                    String replacementString)
    {
        return originalString.replace(substringToReplace, replacementString);
    }

    public static String promptVar(String prompt0, String varName, String varValue)
    {
        String prompt = replaceSubString(prompt0, varName, varValue);
        return replaceSubString(prompt, varName, varValue);
    }

    public String getGreeting() 
    {
        return "Hello World!";
    }

}
