package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DomainRepository {


	private int id = -1;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	

	

	/**
	 * Lädt einen Datensatz aus der Datenbank
	 * @param id ID des zu ladenden Objects
	 * @return Daten
	 */
	public static ResultSet load(String table,int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM "+table+" WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			pstmt.close();
			return rs;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Speichert einen Datensatz in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public int save(String table, int id, Map<String,Object> keysVals ) {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (id == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String keys = keysVals.keySet().toString().replaceAll("\\[|\\]","");
				String questionMarks = "?";
				for (int i = 1; i < keysVals.size(); i++) {
					questionMarks += ",?";
				}
				String insertSQL = "INSERT INTO "+table+"("+keys+") VALUES ("+questionMarks+")";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);
				// Setze Anfrageparameter und fC<hre Anfrage aus
				System.out.println(insertSQL);
				int i = 1;
				for (Map.Entry<String, Object> entry : keysVals.entrySet()) {
					if (entry.getValue() instanceof String) {
						pstmt.setString(i,(String) entry.getValue() );
					} else if (entry.getValue() instanceof Integer) {
						pstmt.setInt(i,(Integer) entry.getValue());
					}
					i++;
				}
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}

				rs.close();
				pstmt.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String keysWithMarks = "";
				for (Map.Entry<String, Object> entry : keysVals.entrySet()) {
					keysWithMarks += entry.getKey() + " = ?,";
				}
				keysWithMarks = keysWithMarks.substring(0,keysWithMarks.length() -1);
				String updateSQL = "UPDATE "+table+" SET "+keysWithMarks+" WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				int i = 1;
				for (Map.Entry<String, Object> entry : keysVals.entrySet()) {
					if (entry.getValue() instanceof String) {
						pstmt.setString(i,(String) entry.getValue() );
					} else if (entry.getValue() instanceof Integer) {
						pstmt.setInt(i,(Integer) entry.getValue());
					}
					i++;
				}
				pstmt.setInt(i, id);
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
