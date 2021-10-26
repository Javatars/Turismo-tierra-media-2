package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.UsuarioDAO;
import excepciones.SQLExceptionCreated;
import jdbc.ConnectionProvider;
import model.TipoAtraccion;
import model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	public List<Usuario> findAll() {
		try{
			String sql = "SELECT * FROM Usuario";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();
			List<Usuario> usuarios= new ArrayList<Usuario>();
			while(resultados.next()) {
				usuarios.add(toUser(resultados));
			}
			return usuarios;
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

	public int update(Usuario t) {
		try{
			String sql = "UPDATE Usuario SET presupuesto = ?, tiempo_disponible = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			//Comenzar transaccion
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, t.getPresupuesto());
			statement.setDouble(2, t.getTiempoDisponible());
			statement.setString(3, t.getNombre());
			int rows = statement.executeUpdate(sql);
			//Fin de la transaccion
			return rows;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	public int delete(Usuario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Usuario toUser(ResultSet resultados) {
		try {
			return new Usuario(resultados.getString(1),resultados.getInt(2),resultados.getDouble(3),TipoAtraccion.valueOf(resultados.getString(4)));
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}
}
