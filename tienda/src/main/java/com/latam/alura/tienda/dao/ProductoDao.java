package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.latam.alura.tienda.modelo.Producto;

public class ProductoDao {

	//injectamos el em como atributo
	private EntityManager em;

	public ProductoDao(EntityManager em) {		
		this.em = em;
	}

	public void guardar(Producto producto) {
		this.em.persist(producto);
	}
	
	public Producto consultaPorId(Long id) {
		return em.find(Producto.class, id);
	}
	
	public List<Producto> consultarTodos(){
		//debemos hacer la consulta con la Java Persistence Query Language(jpql)
		//el * de TODOS se reemplaza por un token que en jpql equivale a la letra
		//que utilizamos para representar la entidad en las query
		 String jpql="SELECT P FROM Producto AS P"; 
		 return em.createQuery(jpql, Producto.class).getResultList();
	}
	
	public List<Producto> consultaPorNombre(String nombre){
		String jpql = "SELECT P FROM Producto AS P WHERE P.nombre=:nombre";
		return em.createQuery(jpql,Producto.class).setParameter("nombre", nombre).getResultList();
		
	}
//	se podria retornar dos resultados y reemplazar el parametro del nombre por un parametro numerico
	public List<Producto> consultaPorNombreYDescripcion(String nombre, String descripcion){
		String jpql = "SELECT P FROM Producto AS P WHERE P.nombre=?1 AND P.descripcion=?2";
		return em.createQuery(jpql, Producto.class)
				.setParameter(1, nombre)
				.setParameter(2, descripcion)
				.getResultList();		
	}
	
	public List<Producto> consultaPorNombreDeCategoria(String nombre){
		String jpql = "SELECT P FROM Producto AS P WHERE P.categoria.categoriaId.nombre=:nombre";
		return em.createQuery(jpql, Producto.class).setParameter("nombre", nombre).getResultList();
	}
	
	public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
		return em.createNamedQuery("Producto.consultaDePrecio", BigDecimal.class).setParameter(1, nombre).getSingleResult();
	}
	
	public List<Producto> consultaPorParametros(String nombre, BigDecimal precio, LocalDate fecha){
		StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p  WHERE 1=1 "); //al poner 1=1 va evaluando los if y suma los que no son nulos a la respuesta
			if(nombre !=null && !nombre.trim().isEmpty()) {
				jpql.append("AND p.nombre =:nombre");
			}
			if(precio !=null && !precio.equals(new BigDecimal(0))) {
				jpql.append("AND p.precio =:precio");
			}
			if(fecha !=null) {
				jpql.append("AND p.fechaDeRegistro=:fecha");
			}
			
			TypedQuery<Producto> query = em.createQuery(jpql.toString(),Producto.class);	
			if(nombre !=null && !nombre.trim().isEmpty()) {
				query.setParameter("nombre", nombre);
			}
			if(precio !=null && !precio.equals(new BigDecimal(0))) {
				query.setParameter("precio", precio);
			}
			if(fecha !=null) {
				query.setParameter("fechaDeRegistro", fecha);
			}
			
			 return query.getResultList();
	}
	
	public List<Producto> consultaPorParametrosConAPICriteria(String nombre, BigDecimal precio, LocalDate fecha){
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Producto> query = builder.createQuery(Producto.class);
			Root<Producto> from = query.from(Producto.class);
			
			Predicate filtro = builder.and();
			if(nombre !=null && !nombre.trim().isEmpty()) {
				filtro = builder.and(filtro,builder.equal(from.get("nombre"), nombre));
			}
			if(precio !=null && !precio.equals(new BigDecimal(0))) {
				filtro = builder.and(filtro,builder.equal(from.get("precio"), precio));
			}
			if(fecha !=null) {
				filtro = builder.and(filtro,builder.equal(from.get("fechaDeRegistro"), fecha));
			}
			
			query = query.where(filtro);
			return em.createQuery(query).getResultList();
			
	}
}
