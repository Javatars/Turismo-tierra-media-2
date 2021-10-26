package dao.interfaces;

import model.Itinerario;

public interface ItinerarioDAO extends GenericDAO<Itinerario> {
	public abstract Itinerario findItinerarioByNombreUsuario(String nombreUsuario);
}
