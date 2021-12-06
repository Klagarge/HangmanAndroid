package com.klagarge.hangman;

import android.text.Html;
import android.util.Log;

import java.text.Normalizer;
import java.io.*;
import java.util.ArrayList;
import java.net.URL;
import java.nio.charset.StandardCharsets;
// @Klagarge
/**
 * Class pour la gestion de mots
 * @author Rémi Heredero
 * @version 1.0.0
 */

public class WordManager {
    public String secretWord = "";
    public String userWord = "";

    void askSecretWord(String path){
        secretWord = randomWord(path);
        secretWord = stripAccents(secretWord);
        secretWord = secretWord.toLowerCase();
        userWord = "";

        for (int i = 0; i < secretWord.length(); i++) {
            userWord += '*';
        }
        Log.i("SecretWord", secretWord);
    }
    /**
     * Check si la lettre en paramètre est dans le secretWord
     * Modifie le userWord pour afficher la lettre trouvé à tous les emplacements bon
     * @param letterToCheck lettre à vérifier
     * @return Retourne true si la lettre est dans le mot
     */
    boolean checkLetter(char letterToCheck){
        
        boolean letterPresent = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if(letterToCheck == secretWord.charAt(i)){
                letterPresent = true;
                userWord = userWord.substring(0, i) + letterToCheck + userWord.substring(i+1);
            }
        }
        return letterPresent;
    }

    public String correctWord(){
        String s = "";
        for (int i = 0; i < secretWord.length(); i++){
            if('*' == secretWord.charAt(i)){
                String first = userWord.substring(0, i);
                String letter = "<font color='#EE0000'>" + secretWord.charAt(i) + "</font>";
                String last = userWord.substring(i+1);
                s = first + letter + last;
                Log.i("debug", s);
            }
        }
        return s;
    }
    /**
     * Check si le userWord = le secretWord
     * @return true si les userWord et le secretWord sont strictement égaux
     */
    boolean isWordComplete(){
        boolean complete = false;
        if (secretWord.equals(userWord)) {
            complete = true;
            // Victory
        }
        return complete;


    }

    /**
     * Enlève les accent d'une chaîne de caractère.
     * @author Mudry Pierre-André
     * @param s Chaîne de caractère à transmettre avec (ou sans) accents.
     * @return Chaîne sans accent
     */
    public static String stripAccents(String s){
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    /**
     * Génère un mot aléatoire en fonction d'une difficulté choisie.
     * <p>Créé un boîte de dialogue pour inviter l'utilisateur à indiquer le niveau de difficulté désiré
     * Choisit dans une liste de mot pré-établi selon le niveau: 
     * <ul><li> beginner:   liste de 19 mots extrêmement courant
     * <li> easy:       liste de 579 mots très courant
     * <li> medium:     liste de 4'872 mots courant
     * <li> difficult:  liste de 23'371 mots rare
     * <li> hardcore:   liste de 108'034 mots très rare </ul>
     * <p>Si pas de niveau choisit, choisit un mot aléatoirement parmi une liste de 331'782 mots
     * @return Retourne le mot généré aléatoirement selon la difficulté choisie
     */
    public String randomWord(String level) {
        String s = "";
        String path = level.toLowerCase();
        //Log.i("debug", path);
        String[] word = loadList("assets/mots.csv"); // 331'782 mots

        if (path.length() != 0){
            String chemin = "assets/mots_" + path + ".csv";
            word = loadList(chemin);
            //Log.i("debug", chemin);
        }

        for (int i = 0; i < word.length; i++) {
            //Log.i("debug", word[i]);
        }

        s = word[(int)(Math.random()*word.length)];
        return s;
    }

    /**
     * Charge un fichier (csv ou txt) et met les ligne dans un tableau de String
     * ! le fichier doit être en UTF-8
     * @author Mudry Pierre-André
     * @param filePath chemin d'accès au fichier doit se trouver dans le src
     * @return Tableau de String avec toutes les lignes du fichier d'entrée
     */
    public String[] loadList(String filePath) {
        String[] wordList;
        try {
            URL url = this.getClass().getClassLoader().getResource(filePath);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)); // ! important de préciser le format de fichier
            ArrayList < String > al = new ArrayList < String > ();
            while (bf.ready()) {
                String[] letterToCheck = bf.readLine().split(";");
                al.add(letterToCheck[0]);
            }
            wordList = al.stream().toArray(String[]::new);
            bf.close();
            return wordList;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}