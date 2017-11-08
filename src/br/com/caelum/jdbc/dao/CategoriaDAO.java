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

public class CategoriaDAO {

	private Connection conn;

	public CategoriaDAO(Connection conn) {
		this.conn = conn;
	}

	public List<Categoria> lista() throws SQLException {
		List<Categoria> categorias = new ArrayList<>();
		String sql = "select * from Categoria";
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			try (ResultSet result = stmt.executeQuery()) {
				while (result.next()) {
					Categoria categoria = new Categoria(result.getString("nome"));
					categoria.setId(result.getInt("id"));
					categorias.add(categoria);
				}
			}
			return categorias;
		}

	}

	public List<Categoria> listaComProdutos() throws SQLException {
		
		List<Categoria> categorias = new ArrayList<>();
		
		String sql = "select c.id as c_id, c.nome as c_nome, p.id  as p_id, p.nome as p_nome, p.descricao "
				+ "as p_descricao from Categoria c join Produto p on c.id = p.categoria_id order by c.id";
		
		Categoria ultimaCategoria = null;
		
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		
			try (ResultSet result = stmt.executeQuery()) {
				
				while (result.next()) {
					
					int idCategoria = result.getInt("id");
					String nomeCategoria = result.getString("nome");
					
					if(ultimaCategoria == null || !ultimaCategoria.getNome().equals(nomeCategoria)){
						ultimaCategoria = new Categoria(nomeCategoria);
						ultimaCategoria.setId(idCategoria);
						categorias.add(ultimaCategoria);
					}
					
					int idProduto = result.getInt("p_id");
					String nomeProduto = result.getString("p_nome");
					String descricaoProduto = result.getString("p_descricao");
					
					Produto produto = new Produto(nomeProduto, descricaoProduto);
					produto.setId(idProduto);
					
					ultimaCategoria.adiciona(produto);
					
				}
				
			}
			return categorias;
		}
	}

}
