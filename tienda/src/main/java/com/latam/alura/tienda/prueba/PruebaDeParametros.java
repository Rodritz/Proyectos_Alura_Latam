package com.latam.alura.tienda.prueba;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;

public class PruebaDeParametros {

	public static void main(String[] args) throws FileNotFoundException {
		LoadRecords.cargarRegistros();
		
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDao productoDao = new ProductoDao(em);
		
		List<Producto> resultado = productoDao.consultaPorParametros("iphone", null, null);
		
		System.out.println(resultado.get(0).getDescripcion());
		
		
		List<Producto> resultado1 = productoDao.consultaPorParametrosConAPICriteria("Samsung", new BigDecimal(750), null);
		
		System.out.println(resultado1.get(0).getDescripcion());
	}

}
