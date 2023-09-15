package com.latam.alura.tienda.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;//importamos la especificacion(JPA)
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//@SuppressWarnings("all") //se utiliza para indicar al compilador que ignore ciertos tipos de advertencias generadas por el compilador.
@Entity
@Table(name="productos")
@NamedQuery(name="Producto.consultaDePrecio", query="SELECT P.precio FROM Producto AS P WHERE P.nombre=?1")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE) //SINGLE_TABLE crea una unica tabla
@Inheritance(strategy=InheritanceType.JOINED)//JOINED crea tabblas por separado
public class Producto {
	//Una entidad JPA funciona como un espejo de una tabla en el banco de datos.
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	private String nombre;
	private String descripcion;
	private BigDecimal precio;
	private LocalDate fechaDeRegistro = LocalDate.now();
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Categoria categoria;	
	
	
	
	public Producto() {
	}
	
	public Producto(String nombre, String descripcion, BigDecimal precio, Categoria categoria) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.categoria = categoria;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
	
}
