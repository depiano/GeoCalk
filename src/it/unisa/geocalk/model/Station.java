package it.unisa.geocalk.model;

public class Station {

	private double longitude;
	private double latitude;
	private String name;
	private String id;
	@Override
	public String toString() {
		return "Station [longitude=" + longitude + ", latitude=" + latitude + ", name=" + name + ", id=" + id + "]";
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
