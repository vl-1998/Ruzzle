package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ruzzle.db.DizionarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
	private final int SIZE = 4;
	private Board board ; //riferimento alla scacchiera
	private List<String> dizionario ; //riferimento al dizionario per controllare le parole
	private StringProperty statusText ; //string property relativa ai messaggi di log subito sopra la casella di testo dei risultati in FXML

	//metodo che richiama la ricorsione
	public List <Pos> trovaParola (String parola){
		Ricerca ricerca = new Ricerca ();
		return ricerca.trovaParola(parola, board);	
	}
	
	
	//CONTROLLORE
	public Model() {
		this.statusText = new SimpleStringProperty() ;
		
		this.board = new Board(SIZE);
		DizionarioDAO dao = new DizionarioDAO() ;
		this.dizionario = dao.listParola() ;
		statusText.set(String.format("%d parole lette", this.dizionario.size())) ; //Viene automaticamente rifelssa nell'interfaccia grafica
	
	}
	
	//metodo che chiama il reset della log
	public void reset() {
		this.board.reset() ;
		this.statusText.set("Board Reset");
	}

	public Board getBoard() {
		return this.board;
	}

	public final StringProperty statusTextProperty() {
		return this.statusText;
	}
	

	public final String getStatusText() {
		return this.statusTextProperty().get();
	}
	

	public final void setStatusText(final String statusText) {
		this.statusTextProperty().set(statusText);
	}

	//METODO che scorre tutte le parole del dizinario e per ogni parola va a vedere se è contenuta nella matrice
	public List<String> trovaTutte() {
		List <String> tutte = new ArrayList <String>();
		for (String d: dizionario) {
				if (d.length()>1) { //testo solo se ho piu di una lettera
					d= d.toUpperCase();
					if (this.trovaParola(d)!=null) { //perche restituisce il percorso
						tutte.add(d);
					}
				}
			}
		return tutte;
	}
	

}
