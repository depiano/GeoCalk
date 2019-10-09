package it.unisa.geocalk.model.dao.operations;

import java.util.Collection;

import it.unisa.geocalk.model.Neighborhoods;

public interface NeighborhoodsDaoInterface {
	
	public void insertNeighborhoodsIntoPostgreSQL(Neighborhoods district);
	
}
