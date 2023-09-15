package com.latam.alura.tienda.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="categorias")
public class Categoria {
	
	//creamos una llave compuesta que reemplaza los atributos comentados
	@EmbeddedId //definimos categoriaId como llave primaria
	private CategoriaId categoriaId;
	
	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nombre;*/
	
	public Categoria() {}

	public Categoria(String nombre) {
		this.categoriaId=new CategoriaId(nombre,"456");
	}
	public String getNombre() {
		return categoriaId.getNombre();
	}

	public void setNombre(String nombre) {
		this.categoriaId.setNombre(nombre);
	}
}
