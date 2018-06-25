package it.polito.tdp.music;

import java.net.URL;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import it.polito.tdp.music.model.ArtistNumber;
import it.polito.tdp.music.model.Model;

public class MusicController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Month> boxMese;

    @FXML
    private Button btnArtisti;

    @FXML
    private Button btnNazioni;

    @FXML
    private TextArea txtResult;
    
    private Model model ;

    @FXML
    void initialize() {
    	 assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'MusicA.fxml'.";
         assert btnArtisti != null : "fx:id=\"btnArtisti\" was not injected: check your FXML file 'MusicA.fxml'.";
         assert btnNazioni != null : "fx:id=\"btnNazioni\" was not injected: check your FXML file 'MusicA.fxml'.";
         assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MusicA.fxml'.";
    }
    
    @FXML
    void doDistanzaMaxNazioni(ActionEvent event) {
    	txtResult.clear() ;
    	Month meseM = boxMese.getValue() ;
    	if (meseM == null) {
    		txtResult.setText("Selezionare un mese") ;
    		return ; 
    	}
    	int mese = meseM.getValue() ;
    	model.creaGrafo(mese) ;
    	
    	txtResult.setText("Distanza massima tra brasile e russia:\n");
    	//TODO elaborare i risultati
    }

    @FXML
    void doElencoArtisti(ActionEvent event) {
    	txtResult.clear() ;
    	Month meseM = boxMese.getValue() ;
    	if (meseM == null) {
    		txtResult.setText("Selezionare un mese") ;
    		return ; 
    	}
    	int mese = meseM.getValue() ;
    	List<ArtistNumber> artisti = model.getTop20Artisti(mese) ;
    	txtResult.setText("Ecco i 20 artisti più ascoltati del mese:\n");
    	int i = 1 ;
    	for (ArtistNumber a : artisti) {
    		txtResult.appendText("Numero "+i+": "+a.getA()+" (ascolti: "+a.getNumber()+")\n");
    		i++ ;
    	}
    }

	public void setModel(Model model) {
		this.model = model ;
		List<Month> mesi = model.getMesi() ;
		boxMese.getItems().addAll(mesi) ;		
	}
}
