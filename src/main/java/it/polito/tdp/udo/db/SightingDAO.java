package it.polito.tdp.udo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SightingDAO {

	public List<String> readShapes(){
		
		try {
			Connection conn = DBConnect.getConnection();
		
			String sql = "SELECT DISTINCT shape "
					+ "FROM sighting "
					+ "WHERE shape <>'' "
					+ "ORDER BY shape ASC";
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			
			List<String> formeUfo = new ArrayList<String>();
			while(res.next()) { //posiziona ad ogni ciclo il cursore sulla riga successiva, partendo da quella prima della prima riga effettiva. Ritorna false quando si trova dopo l'ultima riga effettiva
				String forma = res.getString("shape"); //estrae la colonna "shape"
				formeUfo.add(forma);
			}
			st.close();
			conn.close();
		
			return formeUfo;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error in readShapes", e);
		}
		
	}
	
	public int countByShape(String shape) {
		
		try {
			Connection conn = DBConnect.getConnection();
			
			String sql2 = "SELECT COUNT(*) AS cnt FROM sighting WHERE shape = ? "; // ? da sostituire con un parametro inserito in seguito (tipicamente dall'utente)
			
			PreparedStatement st2 = conn.prepareStatement(sql2); //preparo lo statement passando la stringa di query con i parametri non settati (?)
			
			st2.setString(1, shape); //imposto il parametro al posto del primo ? (inserisco 1)
			ResultSet res2 = st2.executeQuery(); //esegue la query mettendo insieme stringa sql + parametro settato
			
			res2.first(); //posiziona il cursore sulla prima riga (perché so già che il risultato sarà su una sola riga)
			int count = res2.getInt("cnt");//estrae la colonna cnt (che sappiamo contenere int)
			st2.close(); //chiudere statement
			conn.close(); //chiudere sempre la connessione
			
			return count;
			
		}catch (SQLException e) {
			throw new RuntimeException("Database error in countByShape", e);
		}
	}
}
