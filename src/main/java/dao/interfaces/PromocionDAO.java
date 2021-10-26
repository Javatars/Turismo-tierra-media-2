package dao.interfaces;

import java.util.List;

import model.Promocion;

public interface PromocionDAO extends GenericDAO<Promocion> {

	public abstract List<Promocion> findPromocionesByNombreUsuario(String nombreUsuario);

}
