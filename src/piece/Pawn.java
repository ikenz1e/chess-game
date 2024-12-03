package piece;

import main.GamePanel;
import main.Type;

public class Pawn extends Piece{

	public Pawn(int color, int col, int row) {
		super(color, col, row);
		
		type = Type.PAWN;
		
		if(color == GamePanel.WHITE) {
			image = getImage("w-pawn");
		}else {
			image = getImage("b-pawn");
		}
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		
		if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
			int moveValue;
			if(color == GamePanel.WHITE) {
				moveValue = -1;
			}else {
				moveValue = 1;
			}
			
			// check the hitting piece
			hittingPiece = getHittingPiece(targetCol, targetRow);
			
			// 1 square forward
			if(targetCol == preCol && targetRow == preRow + moveValue && hittingPiece == null) {
				return true;
			}
			
			// 2 square movement
			if(targetCol == preCol && targetRow == preRow + moveValue * 2 && hittingPiece == null && !moved && 
					!pieceIsOnStraightLine(targetCol, targetRow)) {
				return true;
			}
			
			// diagonal movement / capture
			if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveValue && hittingPiece != null && hittingPiece.color != color) {
				return true;
			}
			
			// En Passant
			if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveValue) {
				for(Piece piece : GamePanel.simPieces) {
					if(piece.col == targetCol && piece.row == preRow && piece.twoStepped) {
						hittingPiece = piece;
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
