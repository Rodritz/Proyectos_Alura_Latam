package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.vo.RelatorioDeVenta;

public class PedidoDao {

	//injectamos el em como atributo
	private EntityManager em;

	public PedidoDao(EntityManager em) {		
		this.em = em;
	}

	public void guardar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public Pedido consultaPorId(Long id) {
		return em.find(Pedido.class, id);
	}
	
	public List<Pedido> consultarTodos(){
		//debemos hacer la consulta con la Java Persistence Query Language(jpql)
		//el * de TODOS se reemplaza por un token en en jpql equivale a la letra
		//que utilizamos para representar la entidad en las query
		 String jpql="SELECT P FROM Pedido AS P"; 
		 return em.createQuery(jpql, Pedido.class).getResultList();
	}
	
	public BigDecimal valorTotalVendido () {
		String jpql ="SELECT SUM(P.valorTotal) FROM Pedido P";
		return em.createQuery(jpql,BigDecimal.class).getSingleResult();
	}
	
	//si tenemos varias ventas registradas podemos utilizar esta query
	public BigDecimal valorMaximoVendido () {
		String jpql ="SELECT MAX(P.valorTotal) FROM Pedido P";
		return em.createQuery(jpql,BigDecimal.class).getSingleResult();
	}
	
	public double valorPromedioVendido() {
		String jpql = "SELECT AVG(P.valorTotal) FROM Pedido P";
		return em.createQuery(jpql,Double.class).getSingleResult();
	}
	
	public List<Object[]> relatorioDeVentas(){
		String jpql = "SELECT producto.nombre,"
				+ "SUM(item.cantidad),"
				+ "MAX(pedido.fecha) "
				+ "FROM Pedido pedido "
				+ "JOIN pedido.items item "
				+ "JOIN item.producto producto "
				+ "GROUP BY producto.nombre "
				+ "ORDER BY item.cantidad DESC";
		return em.createQuery(jpql,Object[].class).getResultList();
	}
	
	//VO significa Value Object
	public List<RelatorioDeVenta> relatorioDeVentasVO(){
		String jpql = "SELECT new com.latam.alura.tienda.vo.RelatorioDeVenta(producto.nombre,"
				+ "SUM(item.cantidad),"
				+ "MAX(pedido.fecha)) "
				+ "FROM Pedido pedido "
				+ "JOIN pedido.items item "
				+ "JOIN item.producto producto "
				+ "GROUP BY producto.nombre "
				+ "ORDER BY item.cantidad DESC";
		return em.createQuery(jpql,RelatorioDeVenta.class).getResultList();
	}
	
	public Pedido consultarPedidoConCliente(Long id) {
		String jpql="SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id=:id";
		return em.createQuery(jpql,Pedido.class).setParameter("id", id).getSingleResult();
	}
	
	
	
	
	public List<Pedido> consultaPorNombre(String nombre){
		String jpql = "SELECT P FROM Pedido AS P WHERE P.nombre=:nombre";
		return em.createQuery(jpql,Pedido.class).setParameter("nombre", nombre).getResultList();
		
	}
	
//	se podria retornar dos resultados y reemplazar el parametro del nombre por un parametro numerico
	public List<Pedido> consultaPorNombreYDescripcion(String nombre, String descripcion){
		String jpql = "SELECT P FROM Pedido AS P WHERE P.nombre=?1 AND P.descripcion=?2";
		return em.createQuery(jpql, Pedido.class)
				.setParameter(1, nombre)
				.setParameter(2, descripcion)
				.getResultList();		
	}
	
	public List<Pedido> consultaPorNombreDeCategoria(String nombre){
		String jpql = "SELECT P FROM Pedido AS P WHERE P.categoria.nombre=:nombre";
		return em.createQuery(jpql, Pedido.class).setParameter("nombre", nombre).getResultList();
	}
	
	public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
		//como solo queremos consultar el precio aclaramos que de la entidad P solo queremos el precio
		String jpql = "SELECT P.precio FROM Pedido AS P WHERE P.nombre=?1";
		return em.createQuery(jpql, BigDecimal.class).setParameter(1, nombre).getSingleResult();
	}
}
