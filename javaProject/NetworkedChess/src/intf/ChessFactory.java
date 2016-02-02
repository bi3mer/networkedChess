package intf;

/**
 * Abstract to whatever piece related factory 
 * @author KLD
 *
 * @param <T> piece related creation type 
 */
public interface ChessFactory<T>
{
	public abstract T factor(int index);
	
	public abstract T factorKing();
	public abstract T factorQueen();
	public abstract T factorRook();
	public abstract T factorBishop();
	public abstract T factorKnight();
	public abstract T factorPawn();
	
}
