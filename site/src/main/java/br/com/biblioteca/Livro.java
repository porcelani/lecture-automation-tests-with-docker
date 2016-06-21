package br.com.biblioteca;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_livro")
public class Livro implements Serializable {

	private static final long serialVersionUID = 797662145851177903L;

	@Id
	private String isbn;
	
	private String titulo;
	
	private String autor;
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.PERSIST,
						CascadeType.MERGE}, fetch=FetchType.EAGER)
	@JoinColumn (name="idCategoria")
	private Categoria categoria;

	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.PERSIST,
						CascadeType.MERGE}, fetch=FetchType.EAGER)
	@JoinColumn (name="idEditora")
	private Editora editora;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER,
			   mappedBy="livro")
	private List<Estoque> estoqueItems = new ArrayList<Estoque>();
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,
			   mappedBy="emprestimoID.livro")
	private List<Emprestimo> emprestimoItems = new ArrayList<Emprestimo>();
	
	public Livro() { }
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Editora getEditora() {
		return editora;
	}
	
	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	
	public List<Estoque> getEstoqueItems() {
		return estoqueItems;
	}
	
	public void setEstoqueItems(List<Estoque> estoqueItems) {
		this.estoqueItems = estoqueItems;
	}

	public List<Emprestimo> getEmprestimoItems() {
		return emprestimoItems;
	}

	public void setEmprestimoItems(List<Emprestimo> emprestimoItems) {
		this.emprestimoItems = emprestimoItems;
	}
}
