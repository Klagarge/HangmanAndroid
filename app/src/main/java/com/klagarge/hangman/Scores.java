package com.klagarge.hangman;

public class Scores extends MainActivity {
    private static int[]pts;
    private static int[]party;
    private static int length;

    Scores(int language){
        pts = new int[language];
        party = new int[language];
        length = language;
    }

    private void getFromMemorize(){
        for (int i = 0; i < length; i++) {
            pts[i]= MainActivity.settings.getInt("pts" + i, 0);
            party[i]= MainActivity.settings.getInt("party" + i, 0);
        }
    }
    private void writeOnMemorize(){
        for (int i = 0; i < length; i++) {
            MainActivity.editor.putInt("pts" + i, pts[i]);
            MainActivity.editor.putInt("party" + i, party[i]);
        }
            MainActivity.editor.commit();
    }

    private double calculate(int l){
        getFromMemorize();
        double ratio = (pts[l]*1.0)/(party[l]*(MainActivity.MAX_STEPS/4.0))+0.0;
        return ((int)(ratio*100))/100.0;
    }

    public void addPts(String language, String level){
        int step = MainActivity.drawingView.state;
        double points = (MainActivity.MAX_STEPS - step)/2.0;
        getFromMemorize();
        pts[convertLanguage(language)] += scoreForLevel(level)*10 * points;
        writeOnMemorize();
    }

    public void addParty(String language){
        getFromMemorize();
        party[convertLanguage(language)]++;
        writeOnMemorize();
    }

    public String toString(String lg){
        int language = convertLanguage(lg);
        String s = "Score: ";
        s += calculate(language);
        s += "%";
        return s;
    }

    private int convertLanguage(String language){
        String lg = WordManager.stripAccents(language);
        if (lg.equals("francais")){
            return 0;
        } else if (lg.equals("english")){
            return 1;
        } else {
            return 2;
        }
    }

    private int scoreForLevel(String level){
        switch (level){
            case "beginner":
                return 1;
            case "easy":
                return 2;
            case "medium":
                return 3;
            case "difficult":
                return 4;
            default:
                return 5;
        }
    }

    public void resetScores(){
        for (int i = 0; i < length; i++) {
            MainActivity.editor.putInt("pts" + i, 0);
            MainActivity.editor.putInt("party" + i, 0);
        }
        MainActivity.editor.commit();
    }




}
