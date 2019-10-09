package it.unisa.geocalk.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
			Connection con=PostgreSqlConnectionPool.PostgreSqlConnectionPool();
			try {
				
				
				//ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"select * from public.bike;");
						/*ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"SELECT vehicletype, state, make, model, year, color, violation_type, gender, geom " + 
								"FROM example " + 
								"WHERE date_of_stop = '2013-09-05' AND " + 
								"geom <> 'NULL' AND " + 
								"arrest_type = 'Q - Marked Laser' ");
				
								*/
				/*
				ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"SELECT date_of_stop, location, geom, ST_X(geom) as longitude, ST_Y(geom) as latitude "+
				"FROM example "+  
				"WHERE "+  
				"date_of_stop = '2013-09-05' AND "+
				"arrest_type = 'Q - Marked Laser' AND "+
				"location like '%270%'");
				
				while (resultSet.next()) {
					System.out.printf("date_of_stop: "+resultSet.getString("date_of_stop")+" location: "+resultSet.getString("location")+" lat: "+resultSet.getString("latitude")+"long: "+resultSet.getString("longitude")+"\n");
				}
				*/
				
				System.out.println("\n\nNuova query\n\n");
				ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"SELECT name "+
						"FROM station");
						
						while (resultSet.next()) {
							System.out.printf("name: "+resultSet.getString("name")+"\n");
						}
						
				
				/*
				 * Query perfetta
				 */
				/*
				ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"SELECT vehicletype, state, make, model, year, color, violation_type, gender,geom, ST_X(geom) as longitude, ST_Y(geom) as latitude "+
						"FROM example "+
						"WHERE date_of_stop = '2013-09-05' AND " + 
						"geom <> 'NULL' AND " + 
						"arrest_type = 'Q - Marked Laser' ");
				
				
				while (resultSet.next()) {
					System.out.printf("vehicletype: "+resultSet.getString("vehicletype")+"long: "+resultSet.getString("longitude")+"lat: "+resultSet.getString("latitude")+"\n");
				}
				*/
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
