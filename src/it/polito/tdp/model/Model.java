package it.polito.tdp.model;

import java.time.Year;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	
	
	
	EventsDao dao;
	Graph<Integer, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao=new EventsDao();
	}
	

	public List<Integer> getAnni() {
		return dao.listAnni();
	}
	
	
	
	public void creaGrafo(int anno) {
		
		//vertici
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<Integer> distretti=dao.listAllDistricts();
		
		Graphs.addAllVertices(grafo, distretti);
		
		
		
		//archi
		LatLng coord1;		
		
		for(int d1: distretti) {
			
			coord1=dao.calcolaCoordinate(anno, d1);
			
			for(int d2: distretti) {
				
				if(d1!=d2 && grafo.getAllEdges(d1, d2)==null) {
				
				LatLng coord2=dao.calcolaCoordinate(anno, d2);		//nuova libreria
				
				System.out.println(coord1+" "+coord2);
				
				double distanza=LatLngTool.distance(coord1, coord2, LengthUnit.KILOMETER);			//calcola la distanza in kilometres
				
				System.out.println(distanza);
				
				Graphs.addEdge(grafo, d1,d2, distanza);			//la distanza è il peso
				}
				
			}

		}
		
		
	}


	public Graph<Integer, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}


	public void setGrafo(Graph<Integer, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	
	
	public List<Integer> adiacenti(int distretto){
		
		List<Integer> vicini=Graphs.neighborListOf(grafo, distretto);
		
		
		
		return vicini;
		
	}
	

	
	
	
}
