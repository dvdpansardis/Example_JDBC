package br.com.caelum.jdbc.model;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.caelum.jdbc.ConnectionPool;
import br.com.caelum.jdbc.dao.ProdutoDAO;

public class TestaDAODeProduto {

	public static void main(String[] args) throws SQLException {
		Produto noteBookGamer = new Produto("Notebook Asus", "Notebook para gamers");

		try (Connection conn = new ConnectionPool().getConnection()) {

			ProdutoDAO produtoDao = new ProdutoDAO(conn);
			produtoDao.salva(noteBookGamer);

			for (Produto prod : produtoDao.lista()) {
				System.out.println(prod);
			}
		}
	}
}
