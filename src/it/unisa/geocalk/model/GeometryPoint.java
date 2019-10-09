package it.unisa.geocalk.model;


import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

public class GeometryPoint {
	 	String type;
	    double[] coordinates;
	    Dot point=new Dot();

	    @Override
	    public String toString() {
	    	 String result="";
	    	
	    	 result=result+"GeometryPoint [type=" + type +" ]\n";

	    	 return result;
	    }

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
		public double[] getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(double[] coordinates) {
			this.coordinates = coordinates;
		}
		
		public Dot getPoint()
		{
			
			double longitude=Double.parseDouble(ArrayUtils.toString(coordinates[0]));
			double latitude=Double.parseDouble(ArrayUtils.toString(coordinates[1]));
				
			Dot pp=new Dot(longitude, latitude);
	    	
			return pp;
		}
		
		public void printPoint(Dot pp)
		{
			System.out.println(pp.toString());
		}

}

