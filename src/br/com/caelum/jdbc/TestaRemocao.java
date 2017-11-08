package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaRemocao {
	public static void main(String[] args) throws SQLException {
		Connection connection = new ConnectionPool().getConnection();
		
		Statement stmt = connection.createStatement();
		
		stmt.execute("delete from produto where id > 3");
		
		int updateCount = stmt.getUpdateCount();
		
		System.out.println(updateCount + " linhas atualizadas");
		
		stmt.close();
		connection.close();
	}
}
