package excepciones;

public class SQLExceptionCreated extends RuntimeException {
	
	private static final long serialVersionUID = 2342354436534543L;
	
	public SQLExceptionCreated(Exception e) {
		super(e);
	}
}
