package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoPreparedStatement {

	public static void main(String[] args) throws SQLException {
		try (Connection connection = new ConnectionPool().getConnection()) {
			connection.setAutoCommit(false);

			String sql = "insert into produto (nome, descricao) values(?,?)";

			try (PreparedStatement prepareStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS)) {

				adiciona("Control Razer' Serval", "Controle para Android", prepareStatement);
				adiciona("PS4", "Sony", prepareStatement);

				connection.close();
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
				connection.rollback();
				System.out.println("Executed rollback");
			}
		}
	}

	private static void adiciona(String prod, String desc, PreparedStatement prepareStatement)
			throws SQLException {
		
		if(prod.equals("PS4"))
			throw new IllegalArgumentException("Produto invalido");
		
		prepareStatement.setString(1, prod);
		prepareStatement.setString(2, desc);

		boolean result = prepareStatement.execute();

		System.out.println("Sempre falso :" + result);

		ResultSet resultSet = prepareStatement.getGeneratedKeys();

		while (resultSet.next()) {
			String id = resultSet.getString("id");
			System.out.println(id);
		}

		resultSet.close();
	}

}
