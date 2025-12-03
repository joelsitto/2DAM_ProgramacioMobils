package com.example.act09_listview;

import java.io.Serializable;

public class Titular implements Serializable {
	private String nombre;
	private String apellido;
	private String telefono;
	private String email;
	private String direccion;
	private String fechaNacimiento;
	private String notas;

	public Titular() {
		this.nombre = "";
		this.apellido = "";
		this.telefono = "";
		this.email = "";
		this.direccion = "";
		this.fechaNacimiento = "";
		this.notas = "";
	}

	public Titular(String nombre, String apellido, String telefono, String email,
	               String direccion, String fechaNacimiento, String notas) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
		this.fechaNacimiento = fechaNacimiento;
		this.notas = notas;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getNotas() {
		return notas;
	}


	public String getNombreCompleto() {
		String nombreCompleto = nombre;
		if (apellido != null && !apellido.isEmpty()) {
			nombreCompleto += " " + apellido;
		}
		return nombreCompleto;
	}

	public String getInfoResumida() {
		StringBuilder info = new StringBuilder();
		if (telefono != null && !telefono.isEmpty()) {
			info.append("Tel: ").append(telefono);
		}
		if (email != null && !email.isEmpty()) {
			if (info.length() > 0) info.append(" | ");
			info.append(email);
		}
		return info.toString();
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}
}