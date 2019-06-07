package it.polito.tdp.model;

public class TestModel {

	public static void main(String[] args) {
		
		
		
		int anno=2015;
		Model model=new Model();
		model.creaGrafo(anno);
		System.out.println("Vertici "+model.getGrafo().vertexSet().size()+" archi "+model.getGrafo().edgeSet().size());
		
		System.out.println(model.getAdiacenti());
		
		int giorno=6;
		int mese=2;
		int numAgenti=5;
		model.simula(giorno, mese, anno, numAgenti);
		
		
	}

}
