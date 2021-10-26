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

	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(Promocion t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int update(Promocion t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete(Promocion t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
