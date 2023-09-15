package com.latam.alura.tienda.vo;

import java.time.LocalDate;

import net.bytebuddy.implementation.ToStringMethod;

public class RelatorioDeVenta {
	
	private String nombreDelProducto;
	private Long cantidadDeProducto;
	private LocalDate fechaDeUltimaVenta;
	
	public RelatorioDeVenta(String nombreDelProducto, Long cantidadDeProducto, LocalDate fechaDeUltimaVenta) {
		this.nombreDelProducto = nombreDelProducto;
		this.cantidadDeProducto = cantidadDeProducto;
		this.fechaDeUltimaVenta = fechaDeUltimaVenta;
	}

	public String getNombreDelProducto() {
		return nombreDelProducto;
	}

	public void setNombreDelProducto(String nombreDelProducto) {
		this.nombreDelProducto = nombreDelProducto;
	}

	public Long getCantidadDeProducto() {
		return cantidadDeProducto;
	}

	public void setCantidadDeProducto(Long cantidadDeProducto) {
		cantidadDeProducto = cantidadDeProducto;
	}

	public LocalDate getFechaDeUltimaVenta() {
		return fechaDeUltimaVenta;
	}

	public void setFechaDeUltimaVenta(LocalDate fechaDeUltimaVenta) {
		this.fechaDeUltimaVenta = fechaDeUltimaVenta;
	}

	@Override
	public String toString() {
		return "RelatorioDeVenta [nombreDelProducto=" + nombreDelProducto + ", cantidadDeProducto=" + cantidadDeProducto
				+ ", fechaDeUltimaVenta=" + fechaDeUltimaVenta + "]";
	}
}
