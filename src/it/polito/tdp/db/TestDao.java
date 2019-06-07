package it.polito.tdp.db;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.model.Event;

public class TestDao {

	public static void main(String[] args) {
	

		EventsDao dao=new EventsDao();
		int anno=2015;
		int distretto=6;
		LatLng coordinata=dao.calcolaCoordinate(anno, distretto);
		System.out.println("coordinata "+coordinata);
		
		
	} 
}