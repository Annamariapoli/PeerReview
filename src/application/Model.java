package application;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import bean.Article;
import bean.Creator;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao();
	private UndirectedGraph<Creator, DefaultEdge> grafo = null;
	
	public boolean isPres(int codice) throws SQLException{
		return dao.isPresente(codice);
	}
	
	public List<Article> getArticoli(int matrico) throws SQLException{
		List<Article> art = dao.articoliScrittiDaLui(matrico);
		return art;
	}
	
	public boolean isArticoloPres(long codiceA) throws SQLException{
		return dao.isPresenteArticolo(codiceA);
	}
	
	
	public List<Creator> getAll() throws SQLException{
		List<Creator> autori = dao.getAllAutori();
		return autori;
	}
	
	public Map<Integer, Integer> getMappa() throws SQLException{
		Map<Integer, Integer> coppie = dao.coppieDiAutoriCheHannoScrittoAlmenoUnArticoloInsieme();
		return coppie;
	}
	
	public List<Creator> getAutoriCheHannoColl(int codice) throws SQLException{
		List<Creator> aut = dao.autoriCheHannoCollaboratoConLui(codice);
		return aut;
		
	}
	
	
	public List<Creator > getAutori(long codiceArticolo) throws SQLException{
		List<Creator > aut = dao.cercoAutoriCheHannoScrittoArticolo(codiceArticolo);
		return aut;
	}
	
	
	
	public UndirectedGraph<Creator, DefaultEdge> buildGraph() throws SQLException{             //OK
		grafo = new SimpleGraph<Creator, DefaultEdge>(DefaultEdge.class);
		List<Creator> vertici = getAll();
		for(Creator c : vertici){              //tutti autori come vertici
    		grafo.addVertex(c);
	     }
		for(Creator c : grafo.vertexSet()){
			List<Creator> collaboratori = getAutoriCheHannoColl(c.getId_creator());
			for(Creator c2 : collaboratori){
				grafo.addEdge(c, c2);
			}
		}
		System.out.println(grafo.toString());
		return grafo;
	}
	
	public List<Creator> getColl(List<Creator> autori, UndirectedGraph<Creator, DefaultEdge> grafo){
		List<Creator> vicini = new LinkedList<Creator>();
		for(Creator c : autori){
		 vicini = Graphs.neighborListOf(grafo, c);
		}
		return vicini;
		
	}
	
	
	
	public List<Creator > trovoViciniDiOgniVertice(UndirectedGraph<Creator, DefaultEdge> grafo){
		List<Creator > vicini = new LinkedList<Creator>();
		for(Creator c : grafo.vertexSet()){
			vicini = Graphs.neighborListOf(grafo, c);
		}
		return vicini;
		
	}

	
//	//CHIEDO
//	public void buildGraoh() throws SQLException{
//		grafo = new SimpleGraph<Creator, DefaultEdge>(DefaultEdge.class);
//		List<Creator> vertici = getAll();
//		for(Creator c : vertici){           //tutti autori come vertici
//			grafo.addVertex(c);
//		}
//		Map<Integer, Integer> coppie = getMappa();   //ho le coppie di autori che hanno scritto almeno un articolo insieme, posso mettere gli archi tra queste?
//		for(Integer i : coppie.values()){              //x tutti i valori della mappa
//			Integer itemp = coppie.get(key);              //
//			
//		}
//	}
//	
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private SimpleGraph<Creator, DefaultEdge> grafo=null;
//	
//	public List<String> getArticoli(int idAutore) throws SQLException{
//		List<String> que = dao.getArticoli(idAutore);
//		return que;
//	}
//
//	public boolean isPres(int id_creator) throws SQLException{
//		return dao.isCodiceAutorePresente(id_creator);
//	}
//	
//	public List<Creator> dammiAutoriDiArticolo(int idArticolo) throws SQLException{
//		List<Creator> que = dao.getDammiAutoriDiArticolo(idArticolo);
//		return que;
//	}
//	
//	
//	public void buildGraph(int idArticolo) throws SQLException{
//		grafo = new SimpleGraph<Creator, DefaultEdge>(DefaultEdge.class);
//		Graphs.addAllVertices(grafo, dammiAutoriDiArticolo(idArticolo));         //ma se l atricolo è stato scritto da una sola persona? ho un grafo con un solo vertice?
//		//collego autori che hanno almeno un articolo in comune (=coautori)
//		
//	}
}
