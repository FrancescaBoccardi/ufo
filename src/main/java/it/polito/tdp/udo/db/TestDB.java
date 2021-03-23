package it.polito.tdp.udo.db;

import java.util.List;

public class TestDB {

	public static void main(String[] args) {
		
		SightingDAO dao = new SightingDAO();
		
		List<String> formeUFO = dao.readShapes();
		
		for(String forma : formeUFO) {
			int count = dao.countByShape(forma);
			System.out.println("Ufo di forma "+forma+" sono: "+count);
		}
		
	}		
}
