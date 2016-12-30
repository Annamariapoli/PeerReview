package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import bean.Article;
import bean.Creator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController {
	
	private Model m = new Model();
	
	public void setModel(Model m){
		this.m = m;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnCercaarticoli;

    @FXML
    private TextField txtArticolo;

    @FXML
    private Button btnCercaRevisori;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCercaArticoli(ActionEvent event) throws SQLException {    //OK
    	txtResult.clear();
    	try{
    	int codiceAutore= Integer.parseInt(txtMatricola.getText());
    	if(!m.isPres(codiceAutore)){
    		txtResult.appendText("La matricola dell'autore non è presente nel db!\n");
    		return;
    	}
    	List<Article> articoli= m.getArticoli(codiceAutore);
    	if(articoli.size()==0){
    		txtResult.appendText("Al momento l'autore non ha scritto nessun articolo !\n");
    		return;
    	}
    	for(Article a : articoli){
    		txtResult.appendText("Codice: "+ a.getEprintid()+"  Anno: "+a.getYear()+ "  Titolo: "+a.getTitle()+ "\n");
    	}
    	
    	
    	}catch(Exception e ){
    		txtResult.appendText("Il formato non è corretto");
    		return;
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	txtResult.clear();
//    	try{
//    		int id = Integer.parseInt(txtMatricola.getText());
//    		if(id==0){
//    			txtResult.appendText("Non esiste nessun autore con questo codice!\n");
//    			return;
//    		}
//    		if(!m.isPres(id)){
//    			txtResult.appendText("Il codice non esiste! \n ");
//    			return;
//    		}
//    		List<String> articoli = m.getArticoli(id);
//    		if(articoli.size()==0){
//    			txtResult.appendText("L'autore non ha scritto articoli\n");
//    			return;
//    		}
//    		for(String s : articoli){
//    			txtResult.appendText(s+ " ");
//    		}
//    		
//    		
//    	}catch(Exception e){
//    		txtResult.appendText("Il formato non è valido\n");
//    		return;
//    	}

    }

    @FXML
    void doCercaRevisori(ActionEvent event) throws SQLException {
    	txtResult.clear();
    	try{
    	long codiceArticolo = Long.parseLong(txtArticolo.getText());
    	if(!m.isArticoloPres(codiceArticolo)){
    		txtResult.appendText("Il codice di quest'articolo non esiste nel db!\n");
    		return;
    	}
    	UndirectedGraph<Creator, DefaultEdge> grafo = m.buildGraph();  //creo grafo che ha per vertici gli autori e gli archi collegano 2 autori che hanno collaborato
    	
    	List<Creator> possibiliRevisori= new LinkedList<Creator>();
      	possibiliRevisori= m.getAll();                                                 //tutti autori
      	
      	List<Creator> autoriArticolo= m.getAutori(codiceArticolo);         
      	possibiliRevisori.remove(autoriArticolo);                                      //tolgo autori dell articolo
                    
	    List<Creator> collaboratoConAutori = m.getColl(autoriArticolo, grafo)  ;   //tolgo le persone che hanno collaborato con gli autori (vicini di autori)
	    possibiliRevisori.remove(collaboratoConAutori);
    	
    	List<Creator> collaboratoConAutori2 = m.getColl(collaboratoConAutori, grafo) ;
    	possibiliRevisori.remove(collaboratoConAutori2);
    	
    	for(Creator c : possibiliRevisori){
    		txtResult.appendText(c+" \n");
    	}
    	
    	
    	
    	
    	
    	}catch(Exception e ){
    		txtResult.appendText("Errore nel formato!");
    		return;
    	}
    	
    	
    	
//    	txtResult.clear();
//    	try{
//    		int idArticolo = Integer.parseInt(txtArticolo.getText());
//    		
//    	}catch(Exception e){
//    		txtResult.appendText("Il formato non è valido\n");
//    		return;
//    	}

    }

    @FXML
    void initialize() {
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnCercaarticoli != null : "fx:id=\"btnCercaarticoli\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtArticolo != null : "fx:id=\"txtArticolo\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnCercaRevisori != null : "fx:id=\"btnCercaRevisori\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";

    }
}
