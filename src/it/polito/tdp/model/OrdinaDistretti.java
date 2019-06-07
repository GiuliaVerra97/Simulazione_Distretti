package it.polito.tdp.model;

import java.util.Comparator;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class OrdinaDistretti implements Comparator<Integer>{

	
	Graph<Integer, DefaultWeightedEdge> grafo;
	Integer d;
	
	
	
	public OrdinaDistretti(Integer d, Graph<Integer, DefaultWeightedEdge> grafo) {
		this.grafo=grafo;
		this.d=d;
	}
	
	
	
	
	@Override
	public int compare(Integer distretto1, Integer distretto2) {				
		return (int) ( grafo.getEdgeWeight(grafo.getEdge(distretto1, d))-grafo.getEdgeWeight(grafo.getEdge(distretto2, d)));
	}

}
