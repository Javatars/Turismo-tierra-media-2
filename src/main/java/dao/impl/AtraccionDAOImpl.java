package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.AtraccionDAO;
import excepciones.SQLExceptionCreated;
import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoAtraccion;

public class AtraccionDAOImpl implements AtraccionDAO {

	@Override
	public List<Atraccion> findAll() {
		try {
			String sql = "SELECT * FROM Atraccion";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			
			while (resultados.next()) {
				atracciones.add(toAtraccion(resultados));
			}
			
			return atracciones;
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private Atraccion toAtraccion(ResultSet resultados) {
		try {
			return new Atraccion(resultados.getString(1), resultados.getInt(2), resultados.getDouble(4),
					resultados.getInt(3), TipoAtraccion.valueOf(resultados.getString(5)));
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public List<Atraccion> findAtraccionesByNombrePromocion(String nombrePromocion){
		try {
			String sql = "SELECT a.* FROM Atraccion a JOIN Lo_Puede_Contener lpc ON a.nombre = lpc.atraccion WHERE lpc.promocion = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombrePromocion);
			ResultSet resultados = statement.executeQuery();
			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			
			while (resultados.next()) {
				atracciones.add(toAtraccion(resultados));
			}
			return atracciones;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public Atraccion findAtraccionByNombre(String nombreAtraccion) {
		try {
			String sql = "SELECT * FROM Atraccion a WHERE a.nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombreAtraccion);
			ResultSet resultados = statement.executeQuery();
			Atraccion atraccion = null;
			if(resultados.next())
				atraccion = toAtraccion(resultados);
			return atraccion;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Atraccion t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Atraccion t) {
		try{
			String sql = "UPDATE Atraccion SET cupo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			//Comenzar transaccion
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, t.getCupo());
			statement.setString(2, t.getNombre());
			int rows = statement.executeUpdate(sql);
			//Fin de la transaccion
			return rows;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public int delete(Atraccion t) {
		// TODO Auto-generated method stub
		return 0;
	}
}
