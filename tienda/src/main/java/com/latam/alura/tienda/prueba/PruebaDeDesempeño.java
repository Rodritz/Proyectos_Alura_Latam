package com.latam.alura.tienda.prueba;

import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.PedidoDao;
import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.utils.JPAUtils;

public class PruebaDeDesempeño {

	public static void main(String[] args) throws  FileNotFoundException {
		LoadRecords.cargarRegistros();
		
		EntityManager em = JPAUtils.getEntityManager();
		
		PedidoDao pedidoDao = new PedidoDao(em);
		
		//la sig es una consulta planeada para evitar la Excepcion LAZY debido a la conexion cerrada
		Pedido pedidoConCliente = pedidoDao.consultarPedidoConCliente(2l);
		
//		Pedido pedido = em.find(Pedido.class, 3l);
		
		em.close();
		
//		System.out.println(pedido.getFecha());
//		System.out.println(pedido.getItems().size());
		System.out.println(pedidoConCliente.getCliente().getNombre());

	}

}
