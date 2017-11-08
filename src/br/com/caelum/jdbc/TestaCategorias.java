package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.caelum.jdbc.dao.CategoriaDAO;
import br.com.caelum.jdbc.dao.ProdutoDAO;
import br.com.caelum.jdbc.model.Categoria;
import br.com.caelum.jdbc.model.Produto;

public class TestaCategorias {

	public static void main(String[] args) throws SQLException {
		try(Connection conn = new ConnectionPool().getConnection()){
			List<Categoria> categorias = new CategoriaDAO(conn).lista();
			for (Categoria categoria : categorias) {
				//System.out.println(categoria);
				
				List<Produto> produtos = new ProdutoDAO(conn).busca(categoria);
				for (Produto produto : produtos) {
					System.out.println(categoria.getNome() + " - " + produto.getNome());
				}
			}
		}

	}

}
