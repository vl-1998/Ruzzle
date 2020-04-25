package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

//CLASSE in cui imposto la ricorsione
public class Ricerca {
	
	//Mi dice se la parola che passo alla ricorsione è presente o no nella matrice
	//riceve la parola e la matrice su cui cercarla
	public List <Pos> trovaParola (String parola, Board board) {
		//scorro tutte le posizioni della board per trovare da dove partire
		for (Pos p: board.getPositions()) { //mi chiedo se il carattere corrispondente a quella posizione
											// è uguale al primo carattere della mia parola
			//SE questa if restituisce vero almeno la prima lettera della parola è presente nella matrice
			if (board.getCellValueProperty(p).get().charAt(0) == parola.charAt(0)) { //fornisce la String Property corrispondente alla posizione 
												 									 //specificata 
				//inizio potenziale della parola, faccio la RICORSIONE
				//IO QUI PARLO DI POSIZIONI, SE VOGLIO LA LETTERA VADO ALLA BOARD
				List <Pos> percorso = new ArrayList <Pos>(); //inizializzo la lista che salva il percorso
				percorso.add(p);
				
				//Se ho trovato la parola posso restituire il percorso sulla matrice
				if (cerca(parola, 1, percorso, board)) { 	// richiamo la ricorsione passandole la parola, il livello che è già
															// 1 perchè la prima lettera l'ho già cercata sopra, il percorso
															//che andrò a compiere e la board per prendere i caratteri e non 
															// solo la posizione
					return percorso;
					}
				}
			}
			return null; //non ho trovato nessun percorso sulla matrice
			
		}
	

	//IMPOSTIAMO LA RICORSIONE
	private boolean cerca(String parola, int livello, List<Pos> percorso, Board board) {
		//caso terminale
		//LIVELLO = LUNGHEZZA PAROLA
		if (livello == parola.length()) {
			return true;
		}
		
		Pos ultima = percorso.get(percorso.size()-1); //ultima cosa inserita nella soluzione parziale
		List <Pos> adiacenti = board.getAdjacencies(ultima); //mi prendo gli adiacenti da scorrere e filtrare secondo i miei criteri
		
		//Devo ricercare le lettere giuste non ancora usate
		for (Pos p: adiacenti) {
			// !percorso.contains(p) Se percorso non contiene la lettera (perche non posso riusare le stesse e sto controllando le adiacenti) 
			//parola.charAt(livello)==board.getCellValueProperty(p).get().charAt(0)
			// mi chiedo se la lettera che sto controllando corrisponde alla lettera nella posizione della parola in cui sono
			if (!percorso.contains(p) && parola.charAt(livello)==board.getCellValueProperty(p).get().charAt(0)) {
				//faccio ricorsione
				percorso.add(p);
				if (cerca (parola, livello+1, percorso, board) ){
					return true; //uscita rapida in caso di soluzione
				}
				percorso.remove(percorso.size()-1);	
			}
		}
		return false;
		
	}

}
