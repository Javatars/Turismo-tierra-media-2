package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.factory.DAOFactory;
import dao.interfaces.AtraccionDAO;
import dao.interfaces.PromocionDAO;
import excepciones.SQLExceptionCreated;
import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Promocion;
import model.PromocionAbsoluta;
import model.PromocionAxB;
import model.PromocionPorcentual;
import model.TipoAtraccion;

public class PromocionDAOImpl implements PromocionDAO {

	@Override
	public List<Promocion> findAll() {
		try {
			String sql1 = "SELECT pa.*,p.tipo_atraccion FROM Promocion_Absoluta pa JOIN Promocion p ON p.nombre = pa.nombre";
			String sql2 = "SELECT pp.*,p.tipo_atraccion FROM Promocion_Porcentual pp JOIN Promocion p ON p.nombre = pp.nombre";
			String sql3 = "SELECT pab.*,p.tipo_atraccion FROM Promocion_AxB pab JOIN Promocion p ON p.nombre = pab.nombre";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql1);
			ResultSet resultados1 = statement.executeQuery();
			statement = conn.prepareStatement(sql2);
			ResultSet resultados2 = statement.executeQuery();
			statement = conn.prepareStatement(sql3);
			ResultSet resultados3 = statement.executeQuery();

			List<Promocion> promociones = new ArrayList<Promocion>();
			
			while (resultados1.next()) {
				promociones.add(toPromocionAbsoluta(resultados1));
			}
			while (resultados2.next()) {
				promociones.add(toPromocionPorcentual(resultados2));
			}
			while (resultados3.next()) {
				promociones.add(toPromocionAxB(resultados3));
			}
			
			return promociones;
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private Promocion toPromocionAxB(ResultSet promocion) {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		Atraccion atraccionGratis = null;
		try {
			atracciones = atraccionDAO.findAtraccionesByNombrePromocion(promocion.getString(1));
			atraccionGratis = atraccionDAO.findAtraccionByNombre(promocion.getString(2));
			return new PromocionAxB(promocion.getString(1),TipoAtraccion.valueOf(promocion.getString(3)),
					atracciones, atraccionGratis);
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private Promocion toPromocionPorcentual(ResultSet promocion) {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		try {
			atracciones = atraccionDAO.findAtraccionesByNombrePromocion(promocion.getString(1));
			return new PromocionPorcentual(promocion.getString(1),TipoAtraccion.valueOf(promocion.getString(3)),
					atracciones, promocion.getDouble(2));
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private Promocion toPromocionAbsoluta(ResultSet promocion) {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		try {
			atracciones = atraccionDAO.findAtraccionesByNombrePromocion(promocion.getString(1));
			return new PromocionAbsoluta(promocion.getString(1),TipoAtraccion.valueOf(promocion.getString(3)),
					atracciones, promocion.getInt(2));
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public int insert(Promocion t) {
		try {
			String sql = "INSERT INTO Promocion(nombre,tipo_atraccion) VALUES (?,?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
			statement.setString(2, t.getTipo().name());
			int rows = statement.executeUpdate();
			insertTipoPromocion(t);
			insertAtraccionPromocion(t);
			return rows;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private void insertAtraccionPromocion(Promocion t) {
		List<String> nombresAtracciones = new ArrayList<String>();
		for(Atraccion unaAtraccion : t.getAtracciones()) {
			nombresAtracciones.add(unaAtraccion.getNombre());
		}
		try {
			String sql = "INSERT INTO Lo_Puede_Contener(atraccion,promocion) VALUES (?,?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			for(String nombreAtaccion : nombresAtracciones) {
				statement.setString(1, nombreAtaccion);
				statement.setString(2, t.getNombre());
				statement.executeUpdate();
			}
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private void insertTipoPromocion(Promocion p) {
		try {
			String sql = "";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = null;
			if(p instanceof PromocionAbsoluta) {
				PromocionAbsoluta pa = (PromocionAbsoluta) p;
				sql = "INSERT INTO PromocionAbsoluta(nombre,costo_final) VALUES (?,?)";
				statement = conn.prepareStatement(sql);
				statement.setString(1, pa.getNombre());
				statement.setInt(2, pa.costoTotal());
				statement.executeUpdate();
			} if(p instanceof PromocionPorcentual) {
				PromocionPorcentual pp = (PromocionPorcentual) p;
				sql = "INSERT INTO PromocionPorcentual(nombre,porcentaje_descuento) VALUES (?,?)";
				statement = conn.prepareStatement(sql);
				statement.setString(1, pp.getNombre());
				statement.setDouble(2, pp.getPorcentajeDescuento());
				statement.executeUpdate();
			}if(p instanceof PromocionAxB) {
				PromocionAxB pab = (PromocionAxB) p;
				sql = "INSERT INTO PromocionAxB(nombre,atraccion_gratis) VALUES (?,?)";
				statement = conn.prepareStatement(sql);
				statement.setString(1, pab.getNombre());
				String nombreAtraccionGratis = pab.getAtraccionGratis().getNombre();
				statement.setString(2, nombreAtraccionGratis);
				statement.executeUpdate();
			}
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public int update(Promocion t) {
		try {
			String sql = "UPDATE Promocion SET tipo_atraccion = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getTipo().name());
			int rows = statement.executeUpdate();
			return rows;
		}catch(Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public int delete(Promocion t) {
		try {
			deleteTipoPromocion(t);
			String sql = "DELETE FROM Promocion WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private void deleteTipoPromocion(Promocion t) {
		try {
			String sql = "";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = null;
			if(t instanceof PromocionAbsoluta) {
				sql = "DELETE FROM PromocionAbsoluta WHERE nombre = ?";
				conn = ConnectionProvider.getConnection();
				statement = conn.prepareStatement(sql);
				statement.setString(1, t.getNombre());
				statement.executeUpdate();
			}if(t instanceof PromocionPorcentual) {
				sql = "DELETE FROM PromocionPorcentual WHERE nombre = ?";
				conn = ConnectionProvider.getConnection();
				statement = conn.prepareStatement(sql);
				statement.setString(1, t.getNombre());
				statement.executeUpdate();
			}if(t instanceof PromocionAxB) {
				sql = "DELETE FROM PromocionAxB WHERE nombre = ?";
				conn = ConnectionProvider.getConnection();
				statement = conn.prepareStatement(sql);
				statement.setString(1, t.getNombre());
				statement.executeUpdate();
			}
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	@Override
	public List<Promocion> findPromocionesByNombreUsuario(String nombreUsuario) {
		try {
			String sql = "SELECT p.* FROM Tiene_Promociones tp JOIN Promocion p ON tp.promocion = p.nombre WHERE tp.usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombreUsuario);
			ResultSet resultados = statement.executeQuery();
			List<Promocion> promociones = new ArrayList<Promocion>();
			while (resultados.next()) {
				promociones.add(toPromocion(resultados));
			}
			return promociones;
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

	private Promocion toPromocion(ResultSet resultados) {
		try {
			String sql1 = "SELECT pa.*,p.tipo_atraccion FROM Promocion_Absoluta pa JOIN Promocion p ON pa.nombre = ?";
			String sql2 = "SELECT pp.*,p.tipo_atraccion FROM Promocion_Porcentual pp JOIN Promocion p ON pp.nombre = ?";
			String sql3 = "SELECT pab.*,p.tipo_atraccion FROM Promocion_AxB pab JOIN Promocion p ON pab.nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql1);
			statement.setString(1, resultados.getString(1));
			ResultSet resultados1 = statement.executeQuery();
			statement = conn.prepareStatement(sql2);
			statement.setString(1, resultados.getString(1));
			ResultSet resultados2 = statement.executeQuery();
			statement = conn.prepareStatement(sql3);
			statement.setString(1, resultados.getString(1));
			ResultSet resultados3 = statement.executeQuery();
			Promocion promocion = null;
			if (resultados1.next()) 
				promocion = toPromocionAbsoluta(resultados1);
			if (resultados2.next()) 
				promocion = toPromocionPorcentual(resultados2);
			if (resultados3.next()) 
				promocion = toPromocionAxB(resultados3);
			return promocion;
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}

}
