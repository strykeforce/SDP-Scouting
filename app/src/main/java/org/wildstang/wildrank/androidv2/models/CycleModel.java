package org.wildstang.wildrank.androidv2.models;

import java.util.HashMap;
import java.util.Map;

public class CycleModel {

    //public static final String GAMEPIECE_NOT_SCORED_KEY = "not_scored";
    public static final String HUB_UPPER_SCORE_KEY = "hub_upper_score";
    public static final String HUB_LOWER_SCORE_KEY = "hub_lower_score";
    public static final String HUB_UPPER_MISS_KEY = "hub_upper_miss";
    public static final String HUB_LOWER_MISS_KEY = "hub_lower_miss";
    public static final String LOCATION_1_KEY = "location_1";
    public static final String LOCATION_2_KEY = "location_2";
    public static final String LOCATION_3_KEY = "location_3";
    public static final String LOCATION_4_KEY = "location_4";
    public static final String LOCATION_5_KEY = "location_5";
    public static final String LOCATION_6_KEY = "location_6";
    public static final String LOCATION_7_KEY = "location_7";
    public static final String LOCATION_8_KEY = "location_8";


    //public boolean isNotScored;
    public boolean isHubUpperScored;
    public boolean isHubLowerScored;
    public boolean isHubUpperMissed;
    public boolean isHubLowerMissed;
    public boolean isLocation1;
    public boolean isLocation2;
    public boolean isLocation3;
    public boolean isLocation4;
    public boolean isLocation5;
    public boolean isLocation6;
    public boolean isLocation7;
    public boolean isLocation8;


    public CycleModel() {
        // Initialize everything to zero/false, you can set default values here (i.e. preexistingToteCount = 1;)
        //isNotScored = false;
        isHubUpperScored = false;
        isHubLowerScored = false;
        isHubUpperMissed = false;
        isHubLowerMissed = false;
        isLocation1 = false;
        isLocation2 = false;
        isLocation3 = false;
        isLocation4 = false;
        isLocation5 = false;
        isLocation6 = false;
        isLocation7 = false;
        isLocation8 = false;
    }

    public static CycleModel fromMap(Map<String, Object> map) {
        CycleModel cycle = new CycleModel();
        //cycle.isNotScored = (Boolean) map.get(GAMEPIECE_NOT_SCORED_KEY);
        cycle.isHubUpperScored = (Boolean) map.get(HUB_UPPER_SCORE_KEY);
        cycle.isHubLowerScored = (Boolean) map.get(HUB_UPPER_SCORE_KEY);
        cycle.isHubUpperMissed = (Boolean) map.get(HUB_UPPER_SCORE_KEY);
        cycle.isHubLowerMissed = (Boolean) map.get(HUB_UPPER_SCORE_KEY);
        cycle.isLocation1 = (Boolean) map.get(LOCATION_1_KEY);
        cycle.isLocation2 = (Boolean) map.get(LOCATION_2_KEY);
        cycle.isLocation3 = (Boolean) map.get(LOCATION_3_KEY);
        cycle.isLocation4 = (Boolean) map.get(LOCATION_4_KEY);
        cycle.isLocation5 = (Boolean) map.get(LOCATION_5_KEY);
        cycle.isLocation6 = (Boolean) map.get(LOCATION_6_KEY);
        cycle.isLocation7 = (Boolean) map.get(LOCATION_7_KEY);
        cycle.isLocation8 = (Boolean) map.get(LOCATION_8_KEY);

        /* we can use this code to nullify a cycle with a single "not scored" button in pick and place games
        if (map.containsKey(GAMEPIECE_NOT_SCORED_KEY)) {
            cycle.isNotScored = (Boolean) map.get(GAMEPIECE_NOT_SCORED_KEY);
        } else {
            cycle.isNotScored = false;
        }
        */
        return cycle;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        //map.put(CycleModel.GAMEPIECE_NOT_SCORED_KEY, this.isNotScored);
        map.put(CycleModel.HUB_UPPER_SCORE_KEY, this.isHubUpperScored);
        map.put(CycleModel.HUB_LOWER_SCORE_KEY, this.isHubLowerScored);
        map.put(CycleModel.HUB_UPPER_MISS_KEY, this.isHubUpperMissed);
        map.put(CycleModel.HUB_LOWER_MISS_KEY, this.isHubLowerMissed);
        map.put(CycleModel.LOCATION_1_KEY, this.isLocation1);
        map.put(CycleModel.LOCATION_2_KEY, this.isLocation2);
        map.put(CycleModel.LOCATION_3_KEY, this.isLocation3);
        map.put(CycleModel.LOCATION_4_KEY, this.isLocation4);
        map.put(CycleModel.LOCATION_5_KEY, this.isLocation5);
        map.put(CycleModel.LOCATION_6_KEY, this.isLocation6);
        map.put(CycleModel.LOCATION_7_KEY, this.isLocation7);
        map.put(CycleModel.LOCATION_8_KEY, this.isLocation8);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CycleModel)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        CycleModel comparing = (CycleModel) o;
        boolean equals = true;
        //equals &= (comparing.isNotScored == this.isNotScored);
        equals &= (comparing.isHubUpperScored == this.isHubUpperScored);
        equals &= (comparing.isHubLowerScored == this.isHubLowerScored);
        equals &= (comparing.isHubUpperMissed == this.isHubUpperMissed);
        equals &= (comparing.isHubLowerMissed == this.isHubLowerMissed);
        equals &= (comparing.isLocation1 == this.isLocation1);
        equals &= (comparing.isLocation2 == this.isLocation2);
        equals &= (comparing.isLocation3 == this.isLocation3);
        equals &= (comparing.isLocation4 == this.isLocation4);
        equals &= (comparing.isLocation5 == this.isLocation5);
        equals &= (comparing.isLocation6 == this.isLocation6);
        equals &= (comparing.isLocation7 == this.isLocation7);
        equals &= (comparing.isLocation8 == this.isLocation8);

        return equals;
    }

    /**
     * A meaningful cycle is defined as one that indicates something actually happened.
     * <p>
     * We will define a meaningful cycle as one that has scored game pieces or robot actions
     * resulting in attempt at scoring.
     *
     * @return true if it is meaningful cycle, false if otherwise
     */
    public boolean isMeaningfulCycle() {
        if (isHubUpperScored == true || isHubLowerScored == true || isHubUpperMissed == true || isHubLowerMissed == true) {
            return true;
        }
        return false;
    }

    public boolean isScoringCycle() {
        if (isHubUpperScored == true || isHubLowerScored == true) {
            return true;
        }
        return false;
    }

    public boolean isLocatedCycle() {
        if (isLocation1 == true || isLocation2 == true || isLocation3 == true || isLocation4 == true ||
                isLocation5 == true || isLocation6 == true || isLocation7 == true || isLocation8 == true) {
            return true;
        }
        return false;
    }
}
