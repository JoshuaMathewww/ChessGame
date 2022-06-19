import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

//class for each button that is in the chess board
public class Piece {

  //sets the piece, the row, and column of the button
	private JButton piece = new JButton();
	private int column;
	private int row;

	public Piece(ChessPiece chess) {
		if (chess == ChessPiece.NO_PIECE) {
			piece.setIcon(new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));
			((ImageIcon) piece.getIcon()).setDescription(ChessPiece.NO_PIECE.toString());
		} else {
			try {
				BufferedImage b = ImageIO
						.read(new File("images/"
								+ chess.toString() + ".png"));
				Image scale = b.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
				ImageIcon img = new ImageIcon(scale);
				img.setDescription(chess.toString());
				piece.setIcon(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ChessPiece getPiece() {
		if(piece.getIcon() == null) {
			return ChessPiece.NO_PIECE;
		}
		return ChessPiece.valueOf(((ImageIcon) piece.getIcon()).getDescription());
	}

	public boolean hasPiece() {
		return getPiece() == ChessPiece.NO_PIECE ? false : true;
	}

	public void setPiece(ChessPiece chess) {
		if (chess == ChessPiece.NO_PIECE) {
			piece.setIcon(new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));
			((ImageIcon) piece.getIcon()).setDescription(ChessPiece.NO_PIECE.toString());
		} else {
			try {
				BufferedImage b = ImageIO
						.read(new File("images/"
								+ chess.toString() + ".png"));
				Image scale = b.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
				ImageIcon img = new ImageIcon(scale);
				img.setDescription(chess.toString());
				piece.setIcon(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getSide() {
		if (getPiece() == ChessPiece.NO_PIECE) {
			return "NO_PIECE";
		}
		return getPiece().toString().split("_")[0];
	}
  
  public String getType() {
		if (getPiece() == ChessPiece.NO_PIECE) {
			return "NO_PIECE";
		}
		return getPiece().toString().split("_")[1];
	}
	

	
	public JButton getButton() {
		return piece;
	}

	public void setButton(JButton b) {
		piece = b;
	}
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public void setColumn(int c) {
		column = c;
	}
	
	public void setRow(int r) {
		row = r;
	}
}
