package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.model.Evento.TipoEvento;

public class Simula {
	
	
	PriorityQueue<Evento> queue=new PriorityQueue<Evento>();
	
	//parametri passati
	Graph<Integer, DefaultWeightedEdge> grafo=new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	int centrale;
	List<Event> crimini=new ArrayList<Event>();
	Map<Integer, Agente> agenti=new HashMap<Integer, Agente>();
	
	//variabili
	List<Duration> durataSoccorsoTutti;		//può essere 1 o 2 ore
    Duration tempoSoccorsoAltri;		//è solo 2 ore
	
    //variabili interne
    Random rand=new Random();
    
	//output
	int numMalGestiti;
	
	
	
	
	public void init(int numAgenti, int centrale, List<Event> crimini, Graph<Integer, DefaultWeightedEdge> grafo) {
		
		this.grafo=grafo;
		this.crimini=crimini;
		this.centrale=centrale;
		
		durataSoccorsoTutti=new ArrayList<Duration>();
		durataSoccorsoTutti.add(Duration.ofHours(1));
		durataSoccorsoTutti.add(Duration.ofHours(2));
		
		tempoSoccorsoAltri=Duration.ofHours(2);
		
		for(int i=1; i<numAgenti; i++) {			//riempo la lista di agenti
			agenti.put(i,new Agente(i,centrale));
		}
		
		for(Event e: crimini) {
			queue.add(new Evento(e.getReported_date(), TipoEvento.CRIMINE, null, e));
		}
		
		
	}
	
	
	
	

	
	
	
	public void run() {
		
		while(!queue.isEmpty()) {
			
			Evento ev=queue.poll();
			
			switch(ev.getTipo()) {
			
				case CRIMINE:
					
					LocalDateTime tempo=null;
					Event crimine=ev.getCrimine();
					
					int distrettoCrimine=ev.getCrimine().getDistrict_id();
					int distMin=Integer.MAX_VALUE;
					Agente agente=null;
					
					System.out.println("Distretto crimine: "+distrettoCrimine);
					
					for(Agente a: agenti.values()) {
						System.out.println("Distretto agente: "+a.getLuogo());

						if(this.grafo.containsEdge(a.getLuogo(), distrettoCrimine)) {
							int distanza=(int)(grafo.getEdgeWeight(grafo.getEdge(distrettoCrimine, a.getLuogo())));		//ditanza tra l'agente e il crimine
						
							if(a.isOccupato()==false && distMin>distanza) {
								distMin=distanza;
								agente=a;
								System.out.println(agente);
								System.out.println("distanza"+distMin);
							}
						}
					}
					
					double tempoArrivo=(double)(distMin/60);		//l'agente arriva con una velocità di 60km/h quindi ci impiega
					

					if(tempoArrivo>0.25){			//se ci impiega più di 15 minuti ad arrivare
						numMalGestiti++;
					}
					
					
					if(crimine.getOffense_category_id().equals("all_other_crimes")) {
						int i=rand.nextInt(durataSoccorsoTutti.size());
						tempo=ev.getTempo().plus(durataSoccorsoTutti.get(i));
					}else {
						tempo=ev.getTempo().plus(tempoSoccorsoAltri);
					}
					
					tempo=tempo.plusHours(distMin/60);
					
					
					if(agente==null) {
						numMalGestiti++;
					}else {
						
					agenti.get(agente.getCodice()).setLuogo(distrettoCrimine);
					agenti.get(agente.getCodice()).setOccupato(true);
					queue.add(new Evento(tempo, TipoEvento.LIBERO, agente, crimine));
					}
					
					break;
					
				case LIBERO:
					
					Agente a=ev.getA();
					agenti.get(a.getCodice()).setOccupato(false);
						
					break;
			
			
			}
		}
		
		
	}







	//get
	public int getNumMalGestiti() {
		return numMalGestiti;
	}


	
	
	
	

}
