package br.com.caelum.jdbc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Categoria {

	private Integer id;

	private String nome;

	private List<Produto> produtos = new ArrayList<>();
	
	public Categoria(String nome) {
		this.nome = nome;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return String.format("[Categoria %d %s]", id, nome);
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void adiciona(Produto produto) {
		produtos.add(produto);
	}

	public List<Produto> retornaProdutos() {
		return Collections.unmodifiableList(produtos);
	}

}
