package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(int anno, int distretto){
		String sql = "SELECT AVG(geo_lon), AVG(geo_lat) " + 
				"FROM events " + 
				"WHERE Year(reported_date)=2015 AND district_id=6" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			st.setInt(2, distretto);		
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	
	
	
	
	
	
	
	
	
	public List<Integer> listAnni(){
		String sql = "SELECT DISTINCT YEAR(reported_date) as anno FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("anno"));	//lo converto in intero
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	public List<Integer> listAllDistricts(){
		String sql = "SELECT distinct district_id " + 
					"from events " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("district_id"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	
	}
	
	
	
	/**
	 * Calcola il centro di ogni distretto
	 * @param anno
	 * @param distretto
	 * @return
	 */
		public LatLng calcolaCoordinate(int anno, int distretto){
			String sql = "SELECT AVG(geo_lon) as lon, AVG(geo_lat) as lat " + 
					"FROM events " + 
					"WHERE Year(reported_date)=? AND district_id=? " ;
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				
				st.setInt(1, anno);
				st.setInt(2, distretto);		
				
				LatLng coordinata=null;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
					
						coordinata=new LatLng(res.getDouble("lat"), res.getDouble("lon"));
						
					} catch (Throwable t) {
						t.printStackTrace();
						System.out.println(res.getInt("id"));
					}
				}
				
				conn.close();
				return coordinata ;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		}









		/**
		 * Metodo che restituisce tutti gli eventi(crimini) avvenuti in un specifico giorno dell'anno
		 * @param giorno
		 * @param m
		 * @param anno
		 * @return lista di Event
		 */
		public List<Event> getCrimini(int giorno, int m, int anno) {
			String sql = "SELECT * " + 
					"FROM `events` " + 
					"WHERE YEAR(reported_date)=? AND MONTH(reported_date)=? AND DAY(reported_date)=?" ;
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				
				st.setInt(1, anno);
				st.setInt(2, m);		
				st.setInt(3, giorno);
				
				List<Event> list = new ArrayList<>() ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
						list.add(new Event(res.getLong("incident_id"),
								res.getInt("offense_code"),
								res.getInt("offense_code_extension"), 
								res.getString("offense_type_id"), 
								res.getString("offense_category_id"),
								res.getTimestamp("reported_date").toLocalDateTime(),
								res.getString("incident_address"),
								res.getDouble("geo_lon"),
								res.getDouble("geo_lat"),
								res.getInt("district_id"),
								res.getInt("precinct_id"), 
								res.getString("neighborhood_id"),
								res.getInt("is_crime"),
								res.getInt("is_traffic")));
					} catch (Throwable t) {
						t.printStackTrace();
						System.out.println(res.getInt("id"));
					}
				}
				
				conn.close();
				return list ;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		}
	
	
	
	
	
		
		
		
		
		
		/**
		 * Conto quanti crimini avvengono all'anno in un posto
		 */
		public int getNumCrimini(int anno, int distretto) {
			String sql = "SELECT count(incident_id) as cont " + 
					"FROM `events` " + 
					"WHERE YEAR(reported_date)=? AND district_id=?" ;
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				
				st.setInt(1, anno);
				st.setInt(2, distretto);
								
				ResultSet res = st.executeQuery() ;
				
				int numCrimini=0;
				
				if(res.next()) {
					try {
						
						numCrimini=res.getInt("cont");
						
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
				
				conn.close();
				return numCrimini;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			
		
			
		}
		
		
		
		
	
	
	
	
	
	
	
	
}
