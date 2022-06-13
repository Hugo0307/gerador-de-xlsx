package exceptions;

public class LeitorExceptions {
	
	public static void nullException(String mensagem) {
		throw new NullPointerException(mensagem);
	}
	
	public static void illegalArgument(String mensagem) {
		throw new IllegalArgumentException(mensagem);
	}

}
