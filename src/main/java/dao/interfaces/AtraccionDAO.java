package dao.interfaces;

import java.util.List;

import model.Atraccion;

public interface AtraccionDAO extends GenericDAO<Atraccion> {	
	public abstract List<Atraccion> findAtraccionesByNombrePromocion(String nombrePromocion);
	public abstract Atraccion findAtraccionByNombre(String nombreAtraccion);
	public abstract List<Atraccion> findAtraccionesByNombreUsuario(String nombreUsuario);
}
