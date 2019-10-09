package it.unisa.geocalk.model;

import java.util.ArrayList;

public class Polygon {

	private String name;
	private ArrayList<Dot> pointList;
	
	@Override
	public String toString() {
		return "Polygon [name=" + name + ", pointList=" + pointList + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Dot> getPointList() {
		return pointList;
	}
	public void setPointList(ArrayList<Dot> pointList) {
		this.pointList = pointList;
	}
	
}
