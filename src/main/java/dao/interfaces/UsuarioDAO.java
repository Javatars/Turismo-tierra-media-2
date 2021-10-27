package dao.interfaces;

import java.util.List;

import model.Atraccion;
import model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {
	public abstract Usuario findUsuarioByNombre(String nombre);
	public abstract void updateUsuariosItinerariosAtracciones(List<Usuario> usuarios, List<Atraccion> atracciones);
}
