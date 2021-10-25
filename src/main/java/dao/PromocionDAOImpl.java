package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Promocion;
import model.TipoAtraccion;

public class PromocionDAOImpl implements PromocionDAO {

	public List<Promocion> findAll() {

		try {
			String sql = "SELECT * FROM promociones";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Promocion> promociones = new LinkedList<Promocion>();
			
			while (resultados.next()) {
				promociones.add(toPromocion(resultados));
			}
			
			return promociones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Promocion toPromocion(ResultSet resultados) {
		
		AtraccionDAOImpl atraccionDAOImpl = new AtraccionDAOImpl();
		ArrayList<Atraccion> atracciones = null;		
		atracciones.addAll(atraccionDAOImpl.findAll());
		try {
		return new Promocion(resultados.getString(1), TipoAtraccion.valueOf(resultados.getString(2)), atracciones);
		} catch (Exception e) {
			throw new MissingDataException(e);
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
