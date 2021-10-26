package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.factory.DAOFactory;
import dao.interfaces.AtraccionDAO;
import dao.interfaces.PromocionDAO;
import dao.interfaces.UsuarioDAO;
import excepciones.SQLExceptionCreated;
import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Itinerario;
import model.Promocion;
import model.Sugerible;
import model.TipoAtraccion;
import model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	@Override
	public List<Usuario> findAll() {
		try{
			String sql = "SELECT * FROM Usuario";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();
			List<Usuario> usuarios= new ArrayList<Usuario>();
			while(resultados.next()) {
				usuarios.add(toUsuario(resultados));
			}
			return usuarios;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public Usuario findUsuarioByNombre(String nombre) {
		try{
			String sql = "SELECT * FROM Usuario WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();
			Usuario usuario = null;
			if(resultados.next())
				usuario = toUsuario(resultados);
			return usuario;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(Usuario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Usuario t) {
			try {
				String sql = "UPDATE Usuario SET presupuesto = ?, tiempo_disponible = ? WHERE nombre = ?";
				Connection conn = ConnectionProvider.getConnection();
			//Comenzar transaccion
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, t.getPresupuesto());
				statement.setDouble(2, t.getTiempoDisponible());
				statement.setString(3, t.getNombre());
				int rows = statement.executeUpdate();
			
				actualizarRelacionesConSugerencias(t.getNombre(),t.getItinerario());
			
			//Fin de la transaccion
				return rows;
			} catch (Exception e) {
				throw new SQLExceptionCreated(e);
			}
	}

	private void actualizarRelacionesConSugerencias(String nombreUsuario,Itinerario itinerario) {
		List<String> nombresAtracciones= new ArrayList<String>();
		List<String> nombresPromociones= new ArrayList<String>();
		for(Sugerible unaSugerencia : itinerario.getSugerenciasAceptadas()) {
			if(unaSugerencia.esPromocion()) nombresPromociones.add(unaSugerencia.getNombre());
			else nombresAtracciones.add(unaSugerencia.getNombre());
		}
		try {
			String sql = "INSERT INTO Tiene_Atracciones(usuario,atraccion) VALUES (?,?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			for(String nombreAtraccion : nombresAtracciones) {
				if(!atraccionYaIncluida(nombreAtraccion,nombreUsuario)){
					statement.setString(1, nombreUsuario);
					statement.setString(2, nombreAtraccion);
					statement.executeUpdate();
				}
				
			}
			sql = "INSERT INTO Tiene_Promociones(usuario,promocion) VALUES (?,?)";
			statement = conn.prepareStatement(sql);
			for(String nombrePromocion : nombresPromociones) {
				if(!promocionYaIncluida(nombrePromocion,nombreUsuario)) {
					statement.setString(1, nombreUsuario);
					statement.setString(2, nombrePromocion);
					statement.executeUpdate();
				}
			}
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private boolean promocionYaIncluida(String nombrePromocion, String nombreUsuario) {
		boolean estaIncluida = false;
		try {
			String sql = "SELECT * FROM Tiene_Promociones WHERE usuario = ? and promocion = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombreUsuario);
			statement.setString(2, nombrePromocion);
			ResultSet resultados = statement.executeQuery();
			if(resultados.next())
				estaIncluida = true;
			return estaIncluida;
		}catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private boolean atraccionYaIncluida(String nombreAtraccion,String nombreUsuario) {
		boolean estaIncluida = false;
		try {
			String sql = "SELECT * FROM Tiene_Atracciones WHERE usuario = ? and atraccion = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombreUsuario);
			statement.setString(2, nombreAtraccion);
			ResultSet resultados = statement.executeQuery();
			if(resultados.next())
				estaIncluida = true;
			return estaIncluida;
		}catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	public int delete(Usuario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Usuario toUsuario(ResultSet resultados) {
		try {
			AtraccionDAO atraccionDao = DAOFactory.getAtraccionDAO();
			PromocionDAO promocionDao = DAOFactory.getPromocionDAO();
			List<Sugerible> sugerenciasAceptadas = new ArrayList<Sugerible>();
			List<Atraccion> atracciones = atraccionDao.findAtraccionesByNombreUsuario(resultados.getString(1));
			sugerenciasAceptadas.addAll(atracciones);
			List<Promocion> promociones = promocionDao.findPromocionesByNombreUsuario(resultados.getString(1));
			sugerenciasAceptadas.addAll(promociones);
			return new Usuario(resultados.getString(1),resultados.getInt(2),resultados.getDouble(3),TipoAtraccion.valueOf(resultados.getString(4)),sugerenciasAceptadas);
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}
}
