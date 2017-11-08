package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {
	public static void main(String[] args) throws SQLException {
		Connection connection = new ConnectionPool().getConnection();

		Statement statement = connection.createStatement();

		boolean result = statement.execute(
				"insert into produto (nome, descricao) values('Mouse Razer', 'mouse 300000 dpi Naga')",
				Statement.RETURN_GENERATED_KEYS);
		System.out.println("Sempre falso :" + result);
		
		
		ResultSet generatedKeys = statement.getGeneratedKeys();
		
		while(generatedKeys.next()){
			String id = generatedKeys.getString("id");
			System.out.println(id);
		}
		
		statement.close();
		connection.close();

	}
}
