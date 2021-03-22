package it.polito.tdp.udo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDB {

	public static void main(String[] args) {
		
		String jdbcURL = "jdbc:mysql://localhost/ufo_sightings?user=root&password=ReplayMariaDB";
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
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
			
			System.out.println(formeUfo);
			
			String sql2 = "SELECT COUNT(*) AS cnt FROM sighting WHERE shape = ? "; // ? da sostituire con un parametro inserito in seguito (tipicamente dall'utente)
			String shapeScelta = "circle"; //parametro inserito dall'utente
			
			PreparedStatement st2 = conn.prepareStatement(sql2); //preparo lo statement passando la stringa di query con i parametri non settati (?)
			
			st2.setString(1, shapeScelta); //imposto il parametro al posto del primo ? (inserisco 1)
			ResultSet res2 = st2.executeQuery(); //esegue la query mettendo insieme stringa sql + parametro settato
			
			res2.first(); //posiziona il cursore sulla prima riga (perché so già che il risultato sarà su una sola riga)
			int count = res2.getInt("cnt");//estrae la colonna cnt (che sappiamo contenere int)
			st2.close(); //chiudere statement
			
			System.out.println("UFO di forma "+shapeScelta+" sono: "+count);
			
			conn.close(); //chiudere sempre connessione
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
