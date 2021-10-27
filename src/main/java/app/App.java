package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import model.Atraccion;
import model.ComparadorDeSugerencias;
import model.Promocion;
import model.Sugerible;
import model.Usuario;
import dao.factory.DAOFactory;
import dao.interfaces.AtraccionDAO;
import dao.interfaces.PromocionDAO;
import dao.interfaces.UsuarioDAO;
import jdbc.ConnectionProvider;

public class App {
	private static List<Usuario> usuarios = new ArrayList<Usuario>();
	private static List<Sugerible> sugerencias = new ArrayList<Sugerible>();
	private static List<Atraccion> atracciones = new ArrayList<Atraccion>();
	private static List<Promocion> promociones = new ArrayList<Promocion>();

	public static void main(String[] args) {
		System.out.println("Sistema de Turismo en la Tierra Media");

		UsuarioDAO usuarioDao = DAOFactory.getUsuarioDAO();
		AtraccionDAO atraccionDao = DAOFactory.getAtraccionDAO();
		PromocionDAO promocionDao = DAOFactory.getPromocionDAO();

		usuarios = usuarioDao.findAll();
		atracciones = atraccionDao.findAll();
		sugerencias.addAll(atracciones);
		promociones = promocionDao.findAll();
		sugerencias.addAll(promociones);

		ejecutar();
		cerrarConexion();
	}

	private static void cerrarConexion() {
		ConnectionProvider.closeConnection();
	}

	private static void ejecutar() {
		UsuarioDAO usuarioDao = DAOFactory.getUsuarioDAO(); 
		DecimalFormat formato = new DecimalFormat();
		formato.setMaximumFractionDigits(2);
		for(Usuario unUsuario : usuarios) {
			sugerencias.sort(new ComparadorDeSugerencias(unUsuario.getTipoAtraccionPreferida()));
			System.out.println("-----------------------");
			System.out.println("Usuario " + unUsuario.getNombre() + " - tiene " + unUsuario.getPresupuesto() + " monedas y " + 
					formato.format(unUsuario.getTiempoDisponible()) + " horas disponibles.");
			for(Sugerible unaSugerencia : sugerencias) {
				if(unUsuario.puedeComprar(unaSugerencia)) {
					System.out.println("Le quedan " + unUsuario.getPresupuesto() + " monedas y " + formato.format(unUsuario.getTiempoDisponible()) + " horas disponibles.");
					sugerir(unaSugerencia, unUsuario);
				}		
			}
			System.out.println("-----------------------");
		}
		System.out.println("Se actualizan los datos en la DB");
		usuarioDao.updateUsuariosItinerariosAtracciones(usuarios, atracciones);
		System.out.println("Fin del programa");
	}

	private static void sugerir(Sugerible sugerencia, Usuario unUsuario) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(sugerencia.toString());
		System.out.print("¿Desea comprarlo?(si/no): ");
		String decisionUsuario = "";
		try {
			decisionUsuario = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(decisionUsuario.equals("si"))
			unUsuario.comprar(sugerencia);
		else if(!decisionUsuario.equals("si") && !decisionUsuario.equals("no")) {
			while(!decisionUsuario.equals("si") && !decisionUsuario.equals("no")) {
				System.out.print("Debe ingresar si o no: ");
				try {
					decisionUsuario = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(decisionUsuario.equals("si"))
				unUsuario.comprar(sugerencia);
		}
	}
}