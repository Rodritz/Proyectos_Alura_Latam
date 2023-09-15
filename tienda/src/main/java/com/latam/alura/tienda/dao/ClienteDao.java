package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Cliente;

public class ClienteDao {

	//injectamos el em como atributo
	private EntityManager em;

	public ClienteDao(EntityManager em) {		
		this.em = em;
	}

	public void guardar(Cliente cliente) {
		this.em.persist(cliente);
	}
	
	public Cliente consultaPorId(Long id) {
		return em.find(Cliente.class, id);
	}
	
	public List<Cliente> consultarTodos(){
		//debemos hacer la consulta con la Java Persistence Query Language(jpql)
		//el * de TODOS se reemplaza por un token en en jpql equivale a la letra
		//que utilizamos para representar la entidad en las query
		 String jpql="SELECT P FROM Cliente AS P"; 
		 return em.createQuery(jpql, Cliente.class).getResultList();
	}
	
	public List<Cliente> consultaPorNombre(String nombre){
		String jpql = "SELECT P FROM Cliente AS P WHERE P.nombre=:nombre";
		return em.createQuery(jpql,Cliente.class).setParameter("nombre", nombre).getResultList();
		
	}
//	se podria retornar dos resultados y reemplazar el parametro del nombre por un parametro numerico
	public List<Cliente> consultaPorNombreYDescripcion(String nombre, String descripcion){
		String jpql = "SELECT P FROM Cliente AS P WHERE P.nombre=?1 AND P.descripcion=?2";
		return em.createQuery(jpql, Cliente.class)
				.setParameter(1, nombre)
				.setParameter(2, descripcion)
				.getResultList();		
	}
	
	public List<Cliente> consultaPorNombreDeCategoria(String nombre){
		String jpql = "SELECT P FROM Cliente AS P WHERE P.categoria.nombre=:nombre";
		return em.createQuery(jpql, Cliente.class).setParameter("nombre", nombre).getResultList();
	}
	
	public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
		//como solo queremos consultar el precio aclaramos que de la entidad P solo queremos el precio
		String jpql = "SELECT P.precio FROM Cliente AS P WHERE P.nombre=?1";
		return em.createQuery(jpql, BigDecimal.class).setParameter(1, nombre).getSingleResult();
	}
}
