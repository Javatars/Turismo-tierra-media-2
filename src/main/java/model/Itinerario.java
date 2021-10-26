package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Itinerario {
	private List<Sugerible> sugerenciasAceptadas;	
	private Usuario usuario;
	private int id;

	public Itinerario(Usuario usuario) {
		this.usuario = usuario;
		this.sugerenciasAceptadas = new ArrayList<Sugerible>();
	}

	public Itinerario(int id, Usuario usuario) {
		this.id = id;
		this.usuario = usuario;
		this.sugerenciasAceptadas = new ArrayList<Sugerible>();
	}

	public int getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public List<Sugerible> getSugerenciasAceptadas(){
		return sugerenciasAceptadas;
	}

	public double horasNecesarias() {
		double tiempo = 0;
		for (Sugerible unaSugerencia : this.sugerenciasAceptadas)
			tiempo += unaSugerencia.tiempoTotal();
		return tiempo;
	}

	public int costoTotal() {
		int costo = 0;
		for (Sugerible unaSugerencia : this.sugerenciasAceptadas)
			costo += unaSugerencia.costoTotal();
		return costo;
	}

	public void agregarSugerencia(Sugerible sugerencia) {
		this.sugerenciasAceptadas.add(sugerencia);
	}

	public boolean incluyeAtraccion(Sugerible sugerencia) {
		boolean incluye = false;
		for(Sugerible unaSugerenciaAceptada : this.sugerenciasAceptadas) {
			if(unaSugerenciaAceptada.esOcontiene(sugerencia) || sugerencia.esOcontiene(unaSugerenciaAceptada)) {
				incluye = true;
				break;
			}
		}
		return incluye;
	}

	public String resumen() {
		DecimalFormat formato = new DecimalFormat();
		formato.setMaximumFractionDigits(2);
		String compras = "";
		for (int i = 0; i < this.sugerenciasAceptadas.size(); i++) {
			if(this.sugerenciasAceptadas.get(i) instanceof Atraccion)
				compras += this.sugerenciasAceptadas.get(i).resumen() + "\n";
			else compras += this.sugerenciasAceptadas.get(i).resumen();
		}	
		return "Compras realizadas: " + "\n" + compras + "Total a pagar: " + this.costoTotal() + " monedas"
				+ "\n" + "Tiempo necesario para la salida: " + formato.format(this.horasNecesarias()) + " horas";
	}
}
