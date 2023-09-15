package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.internal.build.AllowSysOut;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ClienteDao;
import com.latam.alura.tienda.dao.PedidoDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Cliente;
import com.latam.alura.tienda.modelo.ItemsPedido;
import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;
import com.latam.alura.tienda.vo.RelatorioDeVenta;

public class RegistroDePedido {

	public static void main(String[] args) {
		registrarProducto();
		
		EntityManager em = JPAUtils.getEntityManager();
		
		ProductoDao productoDao = new ProductoDao(em);
		Producto producto = productoDao.consultaPorId(1l);
		
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);	
		
		Cliente cliente = new Cliente("Juan","k6757kjb");
		Pedido pedido = new Pedido(cliente);
		pedido.agregarItems(new ItemsPedido(5,producto,pedido));
		
		em.getTransaction().begin();

		clienteDao.guardar(cliente);
		pedidoDao.guardar(pedido);
		
		em.getTransaction().commit();
		
		BigDecimal valorTotal = pedidoDao.valorTotalVendido();
		System.out.println("Valor total: "+ valorTotal);
		
		BigDecimal valorMaximo = pedidoDao.valorMaximoVendido();
		System.out.println("Valor maximo: "+ valorMaximo);
		
		double valorPromedio = pedidoDao.valorPromedioVendido();
		System.out.println("Valor promedio: "+ valorPromedio);
		
		List<Object[]> relatorio = pedidoDao.relatorioDeVentas();
		for(Object[] obj:relatorio) {
			System.out.println(obj[0]);
			System.out.println(obj[1]);
			System.out.println(obj[2]);
		}
		
		List<RelatorioDeVenta> relatorioVO = pedidoDao.relatorioDeVentasVO();
		relatorioVO.forEach(System.out::println);
		
	}

	private static void registrarProducto() {
		Categoria celulares = new Categoria("CELULARES");
		Producto celular = new Producto("Samsung", "telefono usado", new BigDecimal("1000"), celulares);

		// iniciamos la conexion
		EntityManager em = JPAUtils.getEntityManager();

		ProductoDao productoDao = new ProductoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);

		em.getTransaction().begin();

		categoriaDao.guardar(celulares);
		productoDao.guardar(celular);

		em.getTransaction().commit();
		em.close();
	}
}

//		Explicacion del ciclo de vida

//		/* cuando instanciamos una entidad la misma se encuentra en estado TRANSIENTE */
//		Categoria celulares = new Categoria("CELULARES");
//
//		// iniciamos la conexion
//		EntityManager em = JPAUtils.getEntityManager();
//
//		em.getTransaction().begin();
//
//		/*
//		 * cuando hacemos un persist la entidad para a estar en estado MANAGED y recien
//		 * ahora es considerada para ser guardada en una BD
//		 */
//		em.persist(celulares);
//
//		celulares.setNombre("LIBROS");
//
//		/*
//		 * con un commit o un flush esos datos son enviados/sincronizados con la DB
//		 * donde se crea un ID y un nuevo registro
//		 */
//		// em.getTransaction().commit();
//		em.flush();
//
//		/*
//		 * cuando se realiza un close o un clear la entidad pasa a estar en estado
//		 * DETACHED que es un estado similar al estado TRANSIENTE y no puede ser
//		 * considerada para guardar cambios
//		 */
//		// em.close();
//		em.clear();
//
//		celulares = em.merge(celulares);
//		celulares.setNombre("SOFWARES");
//
//		em.flush();
//		em.clear();
//		
//		celulares = em.merge(celulares);
//		em.remove(celulares);
//		em.flush();
//		
//		
//
//		/*
//		 * El método commit() sincroniza los datos con la base de datos de forma
//		 * definitiva.
//		 */
//		/*
//		  El método flush sincroniza los datos con la base de datos y permite realizar
//		  un roll back en caso de errores.
//		 */
//		/*
//		  El método close cierra la conexión con la base de datos. Es decir que cierro
//		  EntityManager y debere volver a instanciarlo para volver a hacer
//		  transacciones
//		 */
//		/*
//		  El método clear coloca todas las entidades en estado detached para ahorrar
//		  espacio de memoria en el proyecto
//		 */
//		/*
//		  El método merge trae los registros deseados y los pasa a estado managed ,pero en una
//		  diferente ubicación en memoria, por lo que tenemos que reasignar la variable
//		  que se encuentra como detached y luego para actualizarlos ejecutar un flush() 
//		 */
//	}
