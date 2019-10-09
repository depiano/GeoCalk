package it.unisa.geocalk.model;

public class Dot {

	private double longitude;
	private double latitude;
	
	public Dot(double longitude, double latitude)
	{
		this.longitude=longitude;
		this.latitude=latitude;
	}
	
	public Dot()
	{
		
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return "Point [longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	
}
