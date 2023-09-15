package com.latam.alura.tienda.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
	/*
	 * organizamos el código delegando la responsabilidad de instanciar el
	 * EntityManager a una clase utilitaria cuya única función será instanciarlo
	 * El em nos conecta a la DB
	 */

	private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("tienda");

	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}
}
