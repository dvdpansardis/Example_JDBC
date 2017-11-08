package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.model.Categoria;
import br.com.caelum.jdbc.model.Produto;

public class ProdutoDAO {

	private Connection conn;

	public ProdutoDAO(Connection conn) {
		this.conn = conn;
	}

	public void salva(Produto produto) throws SQLException {
		String sql = "insert into Produto (nome, descricao) values (?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getDescricao());

			stmt.execute();

			try (ResultSet result = stmt.getGeneratedKeys()) {
				if (result.next()) {
					int id = result.getInt("id");
					produto.setId(id);
				}
			}

		}
	}

	public List<Produto> lista() throws SQLException {

		List<Produto> produtos = new ArrayList<>();

		String sql = "select * from Produto";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {

			transformeResultadoEmProdutos(produtos, stmt);

			return produtos;
		}
	}

	private void transformeResultadoEmProdutos(List<Produto> produtos, PreparedStatement stmt) throws SQLException {
		try (ResultSet result = stmt.executeQuery()) {
			while (result.next()) {
				Produto produto = new Produto(result.getString("nome"), result.getString("descricao"));
				produto.setId(result.getInt("id"));

				produtos.add(produto);
			}
		}
	}

	public List<Produto> busca(Categoria categoria) throws SQLException {
		List<Produto> produtos = new ArrayList<>();

		String sql = "select * from Produto where categoria_id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, categoria.getId());

			transformeResultadoEmProdutos(produtos, stmt);

			return produtos;
		}
	}

}
