package dao;

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
	public static PromocionAbsolutaDAO getPromocionAbsolutaDAO() {
		return new PromocionAbsolutaDAOImpl();
	}
	public static PromocionAxBDAO getPromocionAxBDAO() {
		return new PromocionAxBDAOImpl();
	}
	public static PromocionPorcentualDAO getPromocionPorcentualDAO() {
		return new PromocionPorcentualDAOImpl();
	}
	public static TipoAtraccionDAO getTipoAtraccionDAO() {
		return new TipoAtraccionDAOImpl();
	}
	public static UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAOImpl();
	}

}
