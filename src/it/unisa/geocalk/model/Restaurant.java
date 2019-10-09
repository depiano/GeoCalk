package it.unisa.geocalk.model;

public class Restaurant {

	private Dot coordinates;
	private String name;
	@Override
	public String toString() {
		return "Restaurant [coordinates=" + coordinates + ", name=" + name + "]";
	}
	public Dot getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Dot coordinates) {
		this.coordinates = coordinates;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
