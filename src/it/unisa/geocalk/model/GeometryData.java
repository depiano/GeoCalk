package it.unisa.geocalk.model;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

public class GeometryData {
    String type;
    double[][][] coordinates;
    ArrayList<Dot> point=new ArrayList<Dot>();

    @Override
    public String toString() {
    	 String result="";
    	 
    	for(int i=0;i<coordinates[0].length;i++)
    	{
	    	result=result+"GeometryData [type=" + type +" coordinates=" + ArrayUtils.toString(coordinates[0][i][0]) + ", "+ArrayUtils.toString(coordinates[0][i][1])+"]\n";

    	}
       return result;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public double[][][] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[][][] coordinates) {
		this.coordinates = coordinates;
	}
	
	public ArrayList<Dot> getPoints()
	{
		ArrayList<Dot> list=new ArrayList<Dot>();
		
		for(int i=0;i<coordinates[0].length;i++)
    	{
			double longitude=coordinates[0][i][0];
			double latitude=coordinates[0][i][1];
			
			list.add(new Dot(longitude, latitude));
    	}
		return list;
	}
	
	public void printPoint(ArrayList<Dot> list)
	{
		for(int i=0;i<list.size();i++)
		{
			System.out.println(list.get(i).toString());
		}
	}
	
}