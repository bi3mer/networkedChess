package ui;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import KLD.Loader;
import intf.ChessFactory;

public class UIPiece implements ChessFactory<Image>
{
	private static UIPiece instence;
	private ArrayList<Image> pieceImages; 
	private Loader loader; 
	
	private UIPiece()
	{
		pieceImages = new ArrayList<Image>();
		loader = new Loader(); 
		
		loader.setFolder("kchess\\");
		loader.setExtention(".png");
		loader.setAlpha(new Color(255, 174, 201));
	}
	
	public static UIPiece Instnece()
	{
		if(instence==null)
			instence = new UIPiece(); 
		
		return instence; 
	}
	
	public Image factor(int index) 
	{
		while(pieceImages.size() <= index )
			pieceImages.add(null); 
		
		if(pieceImages.get(index) == null)
			initlize(index); 
	
		return pieceImages.get(index);
	}
	
	private void initlize(int index) 
	{
		//index /= 2; 
		
		if(pieceImages.get(index)==null)
		{
			pieceImages.set(index, loader.loadImage(""+index)); 
		}
		
		
		/*if(index == Piece.TYPE_BISHOP)
			pieceImages.set(index, factorBishop()); 
		else if(index == Piece.TYPE_KING)
			pieceImages.set(index, factorKing()); 
		else if (index == Piece.TYPE_KNIGHT)
			pieceImages.set(index, factorKnight()); 
		else if (index == Piece.TYPE_QUEEN)
			pieceImages.set(index, factorQueen()); 
		else if (index == Piece.TYPE_PAWN)
			pieceImages.set(index, factorPawn()); 
		else if (index == Piece.TYPE_ROOK)
			pieceImages.set(index, factorRook()); 
		*/
	}

	@Override
	public Image factorKing() 
	{
		
		//pieceImages.set(Piece., factorBishop()); 
		//return black 
		return null;
	}

	@Override
	public Image factorQueen() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image factorRook() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image factorBishop() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image factorKnight() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image factorPawn() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
