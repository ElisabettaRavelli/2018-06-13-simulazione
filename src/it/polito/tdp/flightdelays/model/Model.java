package it.polito.tdp.flightdelays.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

public class Model {
	
	private FlightDelaysDAO dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<ArcoPeso> arcopeso;
	
	public Model() {
		this.dao = new FlightDelaysDAO();
		this.arcopeso = new ArrayList<>();
	}
	
	public List<Airline> allAirline(){
		return this.dao.loadAllAirlines();
	}
	
	public void creaGrafo(String airline) {
		this.grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		for(Arco a: this.dao.getConnessioni(airline)) {
			String id1 = a.getId1();
			LatLng latlng1 = this.dao.getLatLon(id1);
			
			String id2 = a.getId2();
			LatLng latlng2 = this.dao.getLatLon(id2);
			
			double distanza = LatLngTool.distance(latlng1, latlng2, LengthUnit.KILOMETER);
			double peso = a.getRitardo()/distanza;
			Graphs.addEdgeWithVertices(this.grafo, a.getId1(), a.getId2(), peso);
			this.arcopeso.add(new ArcoPeso(a.getId1(), a.getId2(), peso));
			System.out.println("Arco aggiunto: "+ a.getId1()+ " -> "+ a.getId2());
			
			
		}
	}
	
	public int getVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<ArcoPeso> getPeggioriRotte(){
		Collections.sort(this.arcopeso);
		return this.arcopeso;
	}

}
