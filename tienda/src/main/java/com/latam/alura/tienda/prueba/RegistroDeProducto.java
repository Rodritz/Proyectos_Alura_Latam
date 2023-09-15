package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.CategoriaId;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {
		registrarProducto();
		
		// iniciamos la conexion
		EntityManager em = JPAUtils.getEntityManager();

		ProductoDao productoDao = new ProductoDao(em);
		Producto producto = productoDao.consultaPorId(1L);
		System.out.println(producto.getNombre());
		
		List<Producto> productos = productoDao.consultarTodos();
		productos.forEach(prod->System.out.println(prod.getDescripcion()));
		
		List<Producto> productoPorNombre = productoDao.consultaPorNombre("Samsung");
		productoPorNombre.forEach(prod->System.out.println(prod.getDescripcion()));
		
		List<Producto> productoPorNombreYDesc = productoDao.consultaPorNombreYDescripcion("Samsung","telefono usado");
		productoPorNombreYDesc.forEach(prod->System.out.println(prod.getPrecio()));
		
		List<Producto> productoPorNombreDeCategoria = productoDao.consultaPorNombreDeCategoria("CELULARES");
		productoPorNombreDeCategoria.forEach(prod->System.out.println(prod.getDescripcion()));
		
		BigDecimal precio = productoDao.consultarPrecioPorNombreDeProducto("Samsung");
		System.out.println(precio);
		
		Categoria find = em.find(Categoria.class, new CategoriaId("CELULARES","456"));
	    System.out.println(find.getNombre());
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
