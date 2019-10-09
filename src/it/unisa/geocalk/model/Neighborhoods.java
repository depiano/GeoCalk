package it.unisa.geocalk.model;

import java.util.ArrayList;

public class Neighborhoods
{
	private String name;
	private GeometryData geometry;
	
	public Neighborhoods()
	{
	}

	public GeometryData getGeoemtry() {
		return geometry;
	}

	public void setGeoemtry(GeometryData geoemtry) {
		this.geometry = geoemtry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Neighborhoods [name=" + name + ", " + geometry.toString() + "]";
	}
	
	
	
}
