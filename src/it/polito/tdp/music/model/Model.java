package it.polito.tdp.music.model;

import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.music.db.MusicDAO;

public class Model {
	
	MusicDAO dao ;
	SimpleWeightedGraph<Country, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class) ;
	
	public Model() {
		dao = new MusicDAO() ;
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class) ;
	}

	public List<Month> getMesi() {
		return dao.getAllMesi() ;
	}

	public List<ArtistNumber> getTop20Artisti(Integer mese) {
		return dao.getTop20Artisti(mese);
	}

	public void creaGrafo(int mese) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class) ;
		
		//aggiungo tutti i vertici
		Set<Country> paesi = new HashSet<>() ;
		List<ArtistNumber> top20artisti = this.getTop20Artisti(mese) ;
		for (ArtistNumber a : top20artisti) {
			List<Country> paesiA = dao.getPaesi(a.getA(), mese) ;
			paesi.addAll(paesiA) ;
		}
		Graphs.addAllVertices(grafo, paesi) ;
		
		//aggiungo tutti gli archi
		List<MieiArchi> archi = dao.getMieiArchi(mese) ;
		for (MieiArchi a : archi) {
			Country c1 = a.getC1() ;
			Country c2 = a.getC2() ;
			if (grafo.containsVertex(c1) && grafo.containsVertex(c2)) {
				Graphs.addEdge(grafo, a.getC1(), a.getC2(), a.getPeso()) ;
			}
		}
		
		
	}

}
