package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoAtraccion;

public class AtraccionDAOImpl implements AtraccionDAO {

	public List<Atraccion> findAll() {

		try {
			String sql = "SELECT * FROM atracciones";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			
			while (resultados.next()) {
				atracciones.add(toAtraccion(resultados));
			}
			
			return atracciones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Atraccion toAtraccion(ResultSet resultados) {
		try {
			return new Atraccion(resultados.getString(1), resultados.getInt(2), resultados.getDouble(3),
					resultados.getInt(4), TipoAtraccion.valueOf(resultados.getString(5)));
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int countAll() {

		try {
			String sql = "SELECT COUNT(1) FROM atracciones";
			int resultado = 0;
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();
			
			if (resultados.next())
				resultado = resultados.getInt(1);
			
			return resultado;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int insert(Atraccion atraccion) {

		try {
			String sql = "INSERT INTO atracciones (nombre, costo, tiempo, tipo_atraccion) VALUES (?,?,?,?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, atraccion.getNombre());
			statement.setInt(2, atraccion.costoTotal());
			statement.setDouble(3, atraccion.tiempoTotal());
			statement.setString(4, atraccion.getTipo().name());

			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(Atraccion atraccion) {

		try {
			String sql = "UPDATE atracciones SET costo=?, tiempo=?, tipo_atraccion=? WHERE nombre=?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, atraccion.costoTotal());
			statement.setDouble(2, atraccion.tiempoTotal());
			statement.setString(3, atraccion.getTipo().name());
			statement.setString(4, atraccion.getNombre());

			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int delete(Atraccion atraccion) {

		try {
			String sql = "DELETE FROM atracciones WHERE nombre=?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, atraccion.getNombre());

			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

}
