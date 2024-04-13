package org.example.time;

public enum MomentOfTheDay {
    MORNING(6), AFTERNOON(12), EVENING(18), NIGHT(24);
 
    /**
     * Gets the moment of the day corresponding to the hour.
     * 
     * @param hour
     *            the given hour
     * @return the corresponding moment of the day
     */
    MomentOfTheDay getCorrespondingMoment(int hour) {
        assert ((0 <= startHour) && (startHour <= 24));
        // TODO : if (hour < //..
        return MORNING;
    }
 
    /**
     * The hour when the moment start.
     */
    private final int startHour;
 
    /**
     * Build a new moment of the day :
     * 
     * @param startHour
     *            when the moment start.
     */
    MomentOfTheDay(int startHour) {
        assert ((0 <= startHour) && (startHour <= 24));
        this.startHour = startHour;
    }
}