package it.unisa.geocalk.model;

public class Geometry {
    GeometryData geometry;
    
    public Geometry(GeometryData geometry)
    {
    	this.geometry=geometry;
    }
    
    public Geometry()
    {
    	
    }
    
    public GeometryData getGeometry() {
		return geometry;
	}

	public void setGeometry(GeometryData geometry) {
		this.geometry = geometry;
	}

	@Override
    public String toString() {
        return "Geometry [geometry=" + geometry+ "]";
       
    }
}