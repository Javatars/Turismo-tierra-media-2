package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ItinerarioDAO;
import excepciones.SQLExceptionCreated;
import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Itinerario;
import model.Sugerible;
import model.TipoAtraccion;

public class ItinerarioDAOImpl implements ItinerarioDAO {

	public List<Itinerario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(Itinerario t) {
		try{
			String sql = "INSERT INTO Itinerario(usuario) VALUES (?)";
			Connection conn = ConnectionProvider.getConnection();
			int rows = 0;
			//Comenzar transaccion
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getUsuario().getNombre());
			rows = statement.executeUpdate(sql);
			List<String> nombresAtracciones= new ArrayList<String>();
			List<String> nombresPromociones= new ArrayList<String>();
			for(Sugerible unaSugerencia : t.getSugerenciasAceptadas()) {
				if(unaSugerencia.esPromocion()) nombresPromociones.add(unaSugerencia.getNombre());
				else nombresAtracciones.add(unaSugerencia.getNombre());
			}
			sql = "SELECT * FROM Itinerario WHERE usuario = ?";
			statement.setString(1, t.getUsuario().getNombre());
			ResultSet resultado = statement.executeQuery(sql);
			sql = "INSERT INTO Tiene_Atracciones(id_itinerario,atraccion) VALUES (?,?)";
			for(String atraccion : nombresAtracciones) {
				statement.setInt(1, 0);
				statement.setString(1, atraccion);
			}
			//Fin de la transaccion
			return rows;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	public int update(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Itinerario findItinerarioByNombreUsuario(String nombreUsuario) {
		try {
			String sql = "SELECT * FROM Itinerario WHERE usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombreUsuario);
			ResultSet resultado = statement.executeQuery(sql);
			Itinerario itinerario = null;
			if(resultado.next())
				itinerario = toItinerario(resultado);
		}catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private Itinerario toItinerario(ResultSet resultado) {
		try {
			return new Itinerario());
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

}
