package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


//CLASSE che utilizzo per meorizzare le mie variabili di gioco
/**
 * Memorizza le lettere presenti nella scacchiera Ruzzle.
 * @author Fulvio
 *
 */
public class Board {
	private List<Pos> positions; //lista di posizioni disponibili in questa madice
	private Map<Pos, StringProperty> cells; //maprice vera e propria modellata come una mappa di celle con 
											//chiave una posizione come valore una stringa, ma StringProperty
											//utilizzando queste stringhe possiamo fare in modo che il bottone sia collegato
											//direttamente ai valori salvati in queste Stringproperty tramite un binding
											//Ogni volta che un carattere in una string property cambia, questo cambiamento
											//si riflette in modo automatico nell'interfaccia grafica
											//con questa classe si usano i metodi SET e GET per leggere e impostare i valori

	private int size; //grandezza della matrice

	/**
	 * Crea una nuova scacchiera della dimensione specificata
	 * @param size
	 */
	public Board(int size) {
		this.size = size; //imposto la size

		//Definisco le "caselle" del gioco (e la forma del piano di gioco) (CREO LE POSIZIONI)
		this.positions = new ArrayList<>();
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++) {
				this.positions.add(new Pos(row, col)); //le metto nella lista position
			}
		}

		//Definisco il contenuto delle caselle (CREO LA SCACCHIERA)
		this.cells = new HashMap<>(); 

		//Ogni casella conterrà una String Property, inizialmente vuota, per contenere il proprio carattere  
		for (Pos p : this.positions) {
			this.cells.put(p, new SimpleStringProperty()); //metto una new di una string property e non una stringa vuota
		}
	}
	
	/**
	 * Fornisce la {@link StringProperty} corrispondente alla {@link Pos} specificata. <p>
	 * 
	 * Può essere usata per sapere che lettera è presente
	 * (es. {@code getCellValueProperty(p).get()}) oppure per fare un binding della proprietà stessa sulla mappa visuale.
	 * @param p
	 * @return
	 */
	public StringProperty getCellValueProperty(Pos p) {
		return this.cells.get(p) ;
	}

	/**
	 * Restituisce la lista di oggetti {@link  Pos} che corrispondono alle posizioni lecite sulla scacchiera. Gli elementi sono ordinati per righe.
	 * @return
	 */
	public List<Pos> getPositions() {
		return positions;
	}

	/**
	 * Crea una nuova scacchiera generando tutte lettere casuali
	 */
	public void reset() {
		for(Pos p: this.positions) {
			int random = (int)(Math.random()*26) ;
			String letter = Character.toString((char)('A'+random)) ;
			this.cells.get(p).set(letter); //assegna il carattere casuale, questa set grazie al BINDING nell'interfaccia
											// grafica, setta un valore della classe del modello riflettendosi automaticamente
											//sul bottone
		}
	}
	
	//UTILE nel processo ricorsivo che andremo a generare
	/**
	 * Data una posizione, restituisce tutte le posizioni adiacenti
	 * @param p
	 * @return
	 */
	public List<Pos> getAdjacencies(Pos p) {
		List<Pos> result = new ArrayList<>() ;
		
		for(int r = -1; r<=1; r++) {
			for(int c = -1; c<=1; c++) {
				// tutte le 9 posizioni nell'intorno della cella				
				if(r!=0 || c!=0) { // escludo la cella stessa (offset 0,0)
					Pos adj = new Pos(p.getRow()+r, p.getCol()+c) ;
					//controllo che gli indici non siano fuori dalla griglia
					if(positions.contains(adj)) {
						result.add(adj) ;
					}
				}
			}
		}
		
		return result ;
	}


	
}
