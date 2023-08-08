package org.wildstang.wildrank.androidv2;

import android.content.Context;
import android.preference.PreferenceManager;

import com.couchbase.lite.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utilities {

    public static Object[] getRedTeamsFromMatchDocument(Document matchDocument) {
        Map<String, Object> properties = matchDocument.getProperties();
        Map<String, Object> alliances = (Map<String, Object>) properties.get("alliances");
        Map<String, Object> redAlliance = (Map<String, Object>) alliances.get("red");
        try {
            //Map<String, Object> redTeams = (Map<String, Object>) redAlliance.get("teams");
            //System.out.println("Red Teams: " + redTeams);
            return ((ArrayList<Object>) redAlliance.get("teams")).toArray();
        }catch (Exception e) {
            System.out.println("Something went wrong parsing json team keys when evaluating schedule, key is probably wrong: " + e);
        } finally {
            System.out.println("Resorting to new format.");
            return ((ArrayList<Object>) redAlliance.get("team_keys")).toArray();
        }
        //System.out.println(((ArrayList<Object>) redAlliance.get("team_keys")).toArray());

        //return ((ArrayList<Object>) redAlliance.get("team_keys")).toArray();
    }

    public static Object[] getBlueTeamsFromMatchDocument(Document matchDocument) {
        Map<String, Object> properties = matchDocument.getProperties();
        Map<String, Object> alliances = (Map<String, Object>) properties.get("alliances");
        Map<String, Object> blueAlliance = (Map<String, Object>) alliances.get("blue");
        try {
            //Map<String, Object> redTeams = (Map<String, Object>) blueAlliance.get("teams");
            //System.out.println("Blue Teams: " + redTeams);
            return ((ArrayList<Object>) blueAlliance.get("teams")).toArray();
        }catch (Exception e) {
            System.out.println("Something went wrong parsing json team keys when evaluating schedule,  key is probably wrong " + e);
        } finally {
            System.out.println("Resorting to new format.");
            return ((ArrayList<Object>) blueAlliance.get("team_keys")).toArray();
        }
    }

    public static Object[] getTeamsFromMatchDocument(Document matchDocument) {
        Map<String, Object> properties = matchDocument.getProperties();
        Map<String, Object> alliances = (Map<String, Object>) properties.get("alliances");
        Map<String, Object> blueAlliance = (Map<String, Object>) alliances.get("blue");
        Map<String, Object> redAlliance = (Map<String, Object>) alliances.get("red");
        Object[] blueTeams = ((ArrayList<Object>) blueAlliance.get("team_keys")).toArray();
        Object[] redTeams = ((ArrayList<Object>) redAlliance.get("team_keys")).toArray();
        List<Object> teams = new ArrayList<>();
        for (int i = 0; i < blueTeams.length; i++) {
            teams.add(blueTeams[i]);
        }
        for (int i = 0; i < redTeams.length; i++) {
            teams.add(redTeams[i]);
        }
        return teams.toArray();
    }

    public static int matchNumberFromMatchKey(String matchKey) {
        return Integer.parseInt(matchKey.substring(matchKey.lastIndexOf('m') + 1));
    }

    public static int teamNumberFromTeamKey(String teamKey) {
        return Integer.parseInt(teamKey.replace("frc", ""));
    }

    public static String getAssignedTeam(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("assignedTeam", "red_1");
    }

    public static String getAssignedTeamKeyFromMatchDocument(Context context, Document matchDocument) {
        String assignedTeamType = PreferenceManager.getDefaultSharedPreferences(context).getString("assignedTeam", "red_1");
        System.out.println((Map<String, Object>)  matchDocument.getProperties().get("alliances"));
        switch (assignedTeamType) {
            case "red_1":
                return Utilities.getRedTeamsFromMatchDocument(matchDocument)[0].toString();
            case "red_2":
                return Utilities.getRedTeamsFromMatchDocument(matchDocument)[1].toString();
            case "red_3":
                return Utilities.getRedTeamsFromMatchDocument(matchDocument)[2].toString();
            case "blue_1":
                return Utilities.getBlueTeamsFromMatchDocument(matchDocument)[0].toString();
            case "blue_2":
                return Utilities.getBlueTeamsFromMatchDocument(matchDocument)[1].toString();
            case "blue_3":
                return Utilities.getBlueTeamsFromMatchDocument(matchDocument)[2].toString();
            default:
                return "";
        }
    }
}
