package dao;

public class MissingDataException extends RuntimeException {
	
	private static final long serialVersionUID = 2342354436534543L;
	
	public MissingDataException(Exception e) {
		super(e);
	}
}
