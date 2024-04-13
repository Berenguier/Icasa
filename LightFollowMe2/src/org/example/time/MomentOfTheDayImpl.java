package org.example.time;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.liglab.adele.icasa.device.light.DimmerLight;
import fr.liglab.adele.icasa.device.light.Photometer;
import fr.liglab.adele.icasa.service.scheduler.PeriodicRunnable;
import com.example.binary.follow.me.BinaryFollowMeImpl;

public class MomentOfTheDayImpl implements MomentOfTheDayService, PeriodicRunnable {
	public static final String LOCATION_PROPERTY_NAME = "Location";

	/**
	 * The name of the location for unknown value
	 */
	public static final String LOCATION_UNKNOWN = "unknown";
	/** Field for binaryLights dependency */
	private DimmerLight[] dimmerLights;
	private Photometer[] PhotoMeters;
	LocalDateTime currentTime = null;
	int currentSeconds = 0;
	/**
	* The current moment of the day :
	**/
	MomentOfTheDay currentMomentOfTheDay;
	private List<MomentOfTheDayListener> listeners = new ArrayList<>();

	// Autres membres de la classe

	// Méthode pour notifier les auditeurs du changement du moment de la journée
	private void notifyMomentOfTheDayChange(boolean jour) {
		for (MomentOfTheDayListener listener : listeners) {
			listener.momentOfTheDayHasChanged(jour);
		}
	}

	// Implementation of the MomentOfTheDayService ....

	public MomentOfTheDay getMomentOfTheDay() {
		return currentMomentOfTheDay;
	}

	// Implementation ot the PeriodicRunnable ...
	// The service will be periodically called every hour.

	@Override
	public long getPeriod() {
		return 1;
	}

	@Override
	public TimeUnit getUnit() {
		return TimeUnit.HOURS;
	}

	/**
	 * Register a listener that will be notified each time the current moment of the day
	 * changed.
	 * 
	 * @param listener
	 *            the listener
	 */
	void register(MomentOfTheDayListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Unregister a moment of the day listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	void unregister(MomentOfTheDayListener listener) {
		listeners.remove(listener);
	}

	public synchronized List<DimmerLight> getBinaryLightFromLocation(String location) {
		List<DimmerLight> dimmerLightsLocation = new ArrayList<DimmerLight>();
		for (DimmerLight dimLight : dimmerLights) {
			if (dimLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				dimmerLightsLocation.add(dimLight);
			}
		}
		return dimmerLightsLocation;
	}

	@Override
	public void run() {
		/*System.out.println("YOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO444444444444");
		System.out.println("CUUUUUUUUUUUUUUUUUUREEEEEEEEEEEEENT " + currentTime);
		if (currentTime == null) {
			System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
			currentTime = LocalDateTime.now();
			currentSeconds = currentTime.getHour() * 3600 + currentTime.getMinute() * 60 + currentTime.getSecond();
		} else {
			currentSeconds += 3600;
			if (currentSeconds >= 86400) {
				currentSeconds -= 86400;
			}
			System.out.println("SECOOOOOOOOOOOOOOOOOOOOOOONDS " + currentSeconds);
		}
		
		if (currentSeconds > 75600 || currentSeconds < 21600) {
			System.out.println("NUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT");
			notifyMomentOfTheDayChange(false);
			System.out.println(dimmerLights);
			for (DimmerLight dimLight : dimmerLights) {
				System.out.println("LIGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGHHHHHHHT");
				dimLight.setPowerLevel(0.0);
				System.out.println("000000000000000000000000000000000000000000000");
			}
		} else {
			notifyMomentOfTheDayChange(false);
		}*/
	}

}