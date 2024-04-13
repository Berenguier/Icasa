package org.example.time;

/**
 * The MomentOfTheDay service is used to retrieve the moment of the day.
 * It also supports listeners that are notified when the moment of the day
 * change.
 */
public interface MomentOfTheDayService {
 
    /**
     * Gets the moment of the day.
     * 
     * @return the moment of the day
     */
    MomentOfTheDay getMomentOfTheDay();
 
 
    //...
}