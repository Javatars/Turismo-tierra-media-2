package dao.factory;

import dao.impl.AtraccionDAOImpl;
import dao.impl.ItinerarioDAOImpl;
import dao.impl.PromocionDAOImpl;
import dao.impl.UsuarioDAOImpl;
import dao.interfaces.AtraccionDAO;
import dao.interfaces.ItinerarioDAO;
import dao.interfaces.PromocionDAO;
import dao.interfaces.UsuarioDAO;

public class DAOFactory {
	
	public static AtraccionDAO getAtraccionDAO() {
		return new AtraccionDAOImpl();
	}
	public static ItinerarioDAO getItinerarioDAO() {
		return new ItinerarioDAOImpl();
	}
	public static PromocionDAO getPromocionDAO() {
		return new PromocionDAOImpl();
	}

	public static UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAOImpl();
	}
}
