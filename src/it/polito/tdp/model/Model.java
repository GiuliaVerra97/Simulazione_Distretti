package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	
	
	
	EventsDao dao;
	Graph<Integer, DefaultWeightedEdge> grafo;
	List<Integer> distretti;
	
	public Model() {
		dao=new EventsDao();
		distretti=new ArrayList<Integer>();
	}
	

	public List<Integer> getAnni() {
		return dao.listAnni();
	}
	
	
	
	public void creaGrafo(int anno) {
		
		//vertici
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		distretti=dao.listAllDistricts();
		
		Graphs.addAllVertices(grafo, distretti);
		
		
		
		//archi
		LatLng coord1;		
		
		for(Integer d1: distretti) {
			
			coord1=dao.calcolaCoordinate(anno, d1);
			
			for(Integer d2: distretti) {
				
				if(d1!=d2 && !grafo.containsEdge(d1, d2)) {
				
				LatLng coord2=dao.calcolaCoordinate(anno, d2);		//nuova libreria
								
				double distanza=LatLngTool.distance(coord1, coord2, LengthUnit.KILOMETER);			//calcola la distanza in kilometres
				
				/*System.out.println("coordinata1 "+coord1);
				System.out.println("coordinata2 "+coord2);
				System.out.println(distanza);*/
				
				Graphs.addEdge(grafo, d1,d2, distanza);			//la distanza è il peso
				
				}
				
			}

		}
		
		
	}


	
	
	/**
	 * Metodo che ordina i distretti vicini al vertice passato come parametro in ordine crescente di distanza
	 */
	public String getAdiacenti() {
		
		String s="";
		for(Integer d: distretti) {
			
			List<Integer> adiacenti=this.ordinaAdiacenti(d);
			
			s="\n\n\n"+s+"I distretti vicini a "+d+" sono\n";
			
			for(Integer a: adiacenti) {
				s=s+a+" con distanza "+grafo.getEdgeWeight(grafo.getEdge(d, a))+"\n";
			}
			
		}
		
		return s;
		
		
	}
	
	
	
	
	/**
	 * Ordina i vicini in base al peso dell'arco (distanza tra un distretto e l'altro). Per farlo creo una nuova classe compara le distanza
	 * @param distretto
	 * @return lista di distretti
	 */
	public List<Integer> ordinaAdiacenti(int distretto){
		
		List<Integer> vicini=Graphs.neighborListOf(grafo, distretto);
		Collections.sort(vicini, new OrdinaDistretti(distretto, this.grafo));
		
		return vicini;
		
	}
	
	
	
	
	
	//get
	public Graph<Integer, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}


	public void setGrafo(Graph<Integer, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	
	

	

	
	
	
}
