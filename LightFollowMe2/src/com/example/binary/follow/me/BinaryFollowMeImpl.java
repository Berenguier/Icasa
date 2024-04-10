package com.example.binary.follow.me;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.light.DimmerLight;
import fr.liglab.adele.icasa.device.light.Photometer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class BinaryFollowMeImpl implements DeviceListener<GenericDevice> {

	/**
	 * The name of the LOCATION property
	 */
	public static final String LOCATION_PROPERTY_NAME = "Location";

	/**
	 * The name of the location for unknown value
	 */
	public static final String LOCATION_UNKNOWN = "unknown";
	/** Field for presenceSensors dependency */
	private Photometer[] PhotoMeters;
	/** Field for binaryLights dependency */
	private DimmerLight[] dimmerLights;

	/** Bind Method for binaryLights dependency */
	public void bindBinaryLight(DimmerLight dimmerLight, Map<Object, Object> properties) {
		System.out.println("bind binary light " + dimmerLight.getSerialNumber());
	}

	/** Unbind Method for binaryLights dependency */
	public void unbindBinaryLight(DimmerLight dimmerLight, Map<Object, Object> properties) {
		System.out.println("unbind binary light " + dimmerLight.getSerialNumber());
	}

	/**
	 * Bind Method for PresenceSensors dependency.
	 * This method is used to manage device listener.
	 */
	public synchronized void bindPresenceSensor(Photometer photoMeter, Map<Object, Object> properties) {
		// Add the listener to the presence sensor
		photoMeter.addListener(this); //..
	}

	/**
	 * Unbind Method for PresenceSensors dependency.
	 * This method is used to manage device listener.
	 */
	public synchronized void unbindPresenceSensor(Photometer photoMeter, Map<Object, Object> properties) {
		// Remove the listener from the presence sensor
		photoMeter.removeListener(this); //..
	}

	/** Component Lifecycle Method */
	public synchronized void stop() {
		for (Photometer sensor : PhotoMeters) {
			sensor.removeListener(this);
		}
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Component is starting...");
	}

	@Override
	public void deviceAdded(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceEvent(GenericDevice arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void devicePropertyAdded(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method is part of the DeviceListener interface and is called when a
	 * subscribed device property is modified.
	 * 
	 * @param device
	 *            is the device whose property has been modified.
	 * @param propertyName
	 *            is the name of the modified property.
	 * @param oldValue
	 *            is the old value of the property
	 * @param newValue
	 *            is the new value of the property
	 */
	public void devicePropertyModified(GenericDevice device, String propertyName, Object oldValue, Object newValue) {
		Photometer changingSensor = (Photometer) device;
		// check the change is related to presence sensing
		if (propertyName.equals(Photometer.PHOTOMETER_CURRENT_ILLUMINANCE)) {
			System.out.println("ici");
			// get the location where the sensor is:
			String detectorLocation = (String) changingSensor.getPropertyValue(LOCATION_PROPERTY_NAME);
			System.out.println(detectorLocation);
			// if the location is known :
			if (!detectorLocation.equals(LOCATION_UNKNOWN)) {
				System.out.println("ici 2");
				// get the related binary lights
				List<DimmerLight> sameLocationLigths = getBinaryLightFromLocation(detectorLocation);
				for (DimmerLight dimmerlight : sameLocationLigths) {
					System.out.println("ici boucle");
					// and switch them on/off depending on the sensed presence
					double illuminance = changingSensor.getIlluminance();
	                System.out.println(illuminance);
	                int caseValue = (int) (illuminance / 2000); // Determine which case to execute
	                while (illuminance < 10000) {
	                	double result = dimmerlight.getPowerLevel();
	                	result = result + 0.01;
	                	if (result < 1.0) {
	                		dimmerlight.setPowerLevel(result);
		                	illuminance = changingSensor.getIlluminance();
	                	}
	                	else {
	                		break;
	                	}
	                }
	                while (illuminance > 12000) {
	                	double result = dimmerlight.getPowerLevel();
	                	result = result - 0.01;
	                	if (result < 1.0) {
	                		dimmerlight.setPowerLevel(result);
		                	illuminance = changingSensor.getIlluminance();
	                	}
	                	else {
	                		break;
	                	}
	                } 
				}
			}
		}
	}

	@Override
	public void devicePropertyRemoved(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceRemoved(GenericDevice arg0) {
		// TODO Auto-generated method st

	}

	/**
	 * Return all BinaryLight from the given location
	 * 
	 * @param location
	 *            : the given location
	 * @return the list of matching BinaryLights
	 */
	private synchronized List<DimmerLight> getBinaryLightFromLocation(String location) {
		List<DimmerLight> dimmerLightsLocation = new ArrayList<DimmerLight>();
		for (DimmerLight dimLight : dimmerLights) {
			if (dimLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				dimmerLightsLocation.add(dimLight);
			}
		}
		return dimmerLightsLocation;
	}

	public void setLightIntensity(String serialNumber, double intensity) {
		System.out.println("ok fdp");
		for (DimmerLight light : dimmerLights) {
			if (light.getSerialNumber().equals(serialNumber)) {
				light.setPowerLevel(intensity);
				System.out.println("Intensité lumineuse de la lumière " + serialNumber + " définie à " + intensity);
				return;
			}
		}
		System.out.println("Aucune lumière trouvée avec le numéro de série : " + serialNumber);
	}

}
