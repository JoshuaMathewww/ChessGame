import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;

//control the board itself
public class GameBoard {
	private Piece[][] board = new Piece[8][8];
  //check if game is in fischer random mode or not
	private boolean random;
  //check if piece is moved
	private boolean moved;
  //tracks whos turn it is
	private int turn = 0;

  //auto sets random to false
	public GameBoard() {
		random = false;
	}

  //if you clicked the button or not
	boolean clicked;
  //temporary variable to reset the background
	Color temp;
  //gets previous click
	private JButton previousClick;
  //checks for first move
	int counter = 0;
  //turns button blue when clicked and back to original color when released
	public void lightButtons(Piece piece) {
		piece.getButton().addActionListener(actionEvent -> {
			clearBoard();
			counter++;
			if (piece.getPiece() != ChessPiece.NO_PIECE) {
				if (!clicked) {
					temp = piece.getButton().getBackground();
					piece.getButton().setBackground(Color.cyan);
					previousClick = piece.getButton();
					clicked = true;
					if (counter == 1) {
						movementOptions(piece);
					}
				} else {
					previousClick.setBackground(temp);
					temp = piece.getButton().getBackground();
					piece.getButton().setBackground(Color.cyan);
					previousClick = piece.getButton();
					movementOptions(piece);
				}
			}
			moved = false;
		});
	}
  //starting piece
	Piece startP;

  //shows all possible options that the bishop can move to in gray 
  //shows all the pieces that the bishop can take
	public void bishopMovement(int r, int c) {
		int count = 1;
		int check = 0;
		while (r + count <= 7 && c + count <= 7 && (board[r + count][c + count].getPiece() == ChessPiece.NO_PIECE
				|| !board[r + count][c + count].getSide().equals(startP.getSide()))) {
			if (!board[r + count][c + count].getSide().equals(startP.getSide())
					&& board[r + count][c + count].getPiece() != ChessPiece.NO_PIECE) {
				check++;
			}
			if (check > 1) {
				break;
			}
			checkBackground(r + count, c + count, true);
			count++;
		}
		count = 1;
		check = 0;
		while (r + count <= 7 && c - count >= 0 && (board[r + count][c - count].getPiece() == ChessPiece.NO_PIECE
				|| !board[r + count][c - count].getSide().equals(startP.getSide()))) {
			if (!board[r + count][c - count].getSide().equals(startP.getSide())
					&& board[r + count][c - count].getPiece() != ChessPiece.NO_PIECE) {
				check++;
			}
			if (check > 1) {
				break;
			}
			checkBackground(r + count, c - count, true);
			count++;
		}
		count = 1;
		check = 0;
		while (r - count >= 0 && c + count <= 7 && (board[r - count][c + count].getPiece() == ChessPiece.NO_PIECE
				|| !board[r - count][c + count].getSide().equals(startP.getSide()))) {
			if (!board[r - count][c + count].getSide().equals(startP.getSide())
					&& board[r - count][c + count].getPiece() != ChessPiece.NO_PIECE) {
				check++;
			}
			if (check > 1) {
				break;
			}
			checkBackground(r - count, c + count, true);
			count++;
		}
		count = 1;
		check = 0;
		while (r - count >= 0 && c - count >= 0 && (board[r - count][c - count].getPiece() == ChessPiece.NO_PIECE
				|| !board[r - count][c - count].getSide().equals(startP.getSide()))) {
			if (!board[r - count][c - count].getSide().equals(startP.getSide())
					&& board[r - count][c - count].getPiece() != ChessPiece.NO_PIECE) {
				check++;
			}
			if (check > 1) {
				break;
			}
			checkBackground(r - count, c - count, true);
			count++;
		}
	}

  //shows all possible options that the rook can move to in gray 
  //shows all the pieces that the rook can take
	public void rookMovement(int r, int c) {
		int count = 1;
		int check = 0;
		while (r + count <= 7 && (board[r + count][c].getPiece() == ChessPiece.NO_PIECE
				|| !board[r + count][c].getSide().equals(startP.getSide()))) {
			if (!board[r + count][c].getSide().equals(startP.getSide())
					&& board[r + count][c].getPiece() != ChessPiece.NO_PIECE) {
				check++;
			}
			if (check > 1) {
				break;
			}
			checkBackground(r + count, c, true);
			count++;
		}
		count = 1;
		check = 0;
		while (r - count >= 0 && (board[r - count][c].getPiece() == ChessPiece.NO_PIECE
				|| !board[r - count][c].getSide().equals(startP.getSide()))) {
			if (!board[r - count][c].getSide().equals(startP.getSide())
					&& board[r - count][c].getPiece() != ChessPiece.NO_PIECE) {
				check++;
			}
			if (check > 1) {
				break;
			}
			checkBackground(r - count, c, true);
			count++;
		}
		count = 1;
		check = 0;
		while (c + count <= 7 && (board[r][c + count].getPiece() == ChessPiece.NO_PIECE
				|| !board[r][c + count].getSide().equals(startP.getSide()))) {
			if (!board[r][c + count].getSide().equals(startP.getSide())
					&& board[r][c + count].getPiece() != ChessPiece.NO_PIECE) {
				check++;
			}
			if (check > 1) {
				break;
			}
			checkBackground(r, c + count, true);
			count++;
		}
		count = 1;
		check = 0;
		while (c - count >= 0 && (board[r][c - count].getPiece() == ChessPiece.NO_PIECE
				|| !board[r][c - count].getSide().equals(startP.getSide()))) {
			if (!board[r][c - count].getSide().equals(startP.getSide())
					&& board[r][c - count].getPiece() != ChessPiece.NO_PIECE) {
				check++;
			}
			if (check > 1) {
				break;
			}
			checkBackground(r, c - count, true);
			count++;
		}
	}

  //checks if it is the first move to calibrate turns
	boolean firstmove = true;
  //movement options for all pieces except bishop and rook (made them methods so i didnt have to copy and paste for the queen)
  //shows all possible options that the piece clicked can move to in gray 
  //shows all the pieces that the piece clicked can take
	public void movementOptions(Piece piece) {
		startP = piece;
		int r = piece.getRow();
		int c = piece.getColumn();
		if (firstmove) {
			if (startP.getSide().equals("BLACK")) {
				return;
			}
			firstmove = false;
		} else {
      //if the wrong side tries to move it returns causing the piece to not be able to move
			if (turn % 2 == 0 && startP.getSide().equals("BLACK")) {
				return;
			} else if (turn % 2 != 0 && startP.getSide().equals("WHITE")) {
				return;
			}
		}
		if (piece.getPiece() == ChessPiece.BLACK_PAWN) {
			checkBackground(r + 1, c, false);
			if (r == 1) {
				checkBackground(r + 2, c, false);
			}
			checkBackground(r + 1, c - 1, true);
			checkBackground(r + 1, c + 1, true);
		} else if (piece.getPiece() == ChessPiece.WHITE_PAWN) {
			checkBackground(r - 1, c, false);
			if (r == 6) {
				checkBackground(r - 2, c, false);
			}
			checkBackground(r - 1, c - 1, true);
			checkBackground(r - 1, c + 1, true);
		} else if (piece.getPiece() == ChessPiece.BLACK_KNIGHT || piece.getPiece() == ChessPiece.WHITE_KNIGHT) {
			checkBackground(r + 2, c - 1, true);
			checkBackground(r + 2, c + 1, true);
			checkBackground(r - 2, c - 1, true);
			checkBackground(r - 2, c + 1, true);
			checkBackground(r - 1, c - 2, true);
			checkBackground(r + 1, c - 2, true);
			checkBackground(r - 1, c + 2, true);
			checkBackground(r + 1, c + 2, true);
		} else if (piece.getPiece() == ChessPiece.BLACK_BISHOP || piece.getPiece() == ChessPiece.WHITE_BISHOP) {
			bishopMovement(r, c);
		} else if (piece.getPiece() == ChessPiece.BLACK_KING || piece.getPiece() == ChessPiece.WHITE_KING) {
      try {
      if(board[r][c-1].getPiece() == ChessPiece.NO_PIECE&&board[r][c-2].getPiece() == ChessPiece.NO_PIECE&&board[r][c-3].getPiece() == ChessPiece.NO_PIECE&&(board[r][c-4].getPiece() == ChessPiece.WHITE_ROOK||board[r][c-4].getPiece() == ChessPiece.BLACK_ROOK)) {
        if((startP.getRow() == 7 || startP.getRow()==0) && startP.getColumn() == 4){
        checkBackground(r, c-2, false);
        }
      }
      } catch (ArrayIndexOutOfBoundsException e) {}
      try {
      if(board[r][c+1].getPiece() == ChessPiece.NO_PIECE&&board[r][c+2].getPiece() == ChessPiece.NO_PIECE&&(board[r][c+3].getPiece() == ChessPiece.WHITE_ROOK||board[r][c+3].getPiece() == ChessPiece.BLACK_ROOK)) {
        if((startP.getRow() == 7 || startP.getRow()==0) && startP.getColumn() == 4){
        checkBackground(r, c+2, false);
        }
      }
      } catch (ArrayIndexOutOfBoundsException e) {}
			checkBackground(r - 1, c - 1, true);
			checkBackground(r - 1, c, true);
			checkBackground(r - 1, c + 1, true);
			checkBackground(r, c + 1, true);
			checkBackground(r + 1, c + 1, true);
			checkBackground(r + 1, c, true);
			checkBackground(r + 1, c - 1, true);
			checkBackground(r, c - 1, true);
		} else if (piece.getPiece() == ChessPiece.BLACK_ROOK || piece.getPiece() == ChessPiece.WHITE_ROOK) {
			rookMovement(r, c);
		} else if (piece.getPiece() == ChessPiece.BLACK_QUEEN || piece.getPiece() == ChessPiece.WHITE_QUEEN) {
			bishopMovement(r, c);
			rookMovement(r, c);
		}
	}

  //checks if the possible spaces follow chess rules and dont break the rules of the game
	public void checkBackground(int row, int col, boolean capture) {
    //makes sure that the button specified is on the board (checks for ArrayIndexOutOfBounds exception)
		if (row > 7 || row < 0) {
			return;
		}
		if (col > 7 || col < 0) {
			return;
		}

    //checks if u havent moved and if you can take a piece in that spot
		if (capture && moved == false) {
			if (board[row][col].getPiece() != ChessPiece.NO_PIECE && !board[row][col].getSide().equals(startP.getSide())) {
				board[row][col].getButton().setBackground(Color.red);
			} else if (board[row][col].getPiece() == ChessPiece.NO_PIECE && !(startP.getPiece() == ChessPiece.BLACK_PAWN || startP.getPiece() == ChessPiece.WHITE_PAWN)) {
				board[row][col].getButton().setBackground(Color.gray);
			}
		}
    //checks if the spot isnt capturable, checks if there are no pieces there, and checks if u havent moved
		if (!capture && board[row][col].getPiece() == ChessPiece.NO_PIECE && moved == false) {
			board[row][col].getButton().setBackground(Color.gray);
		}
    //if u click on the button this action listener is fired
		board[row][col].getButton().addActionListener(actionEvent -> {
      //checks if your allowed to move to this button
			if (board[row][col].getButton().getBackground() == Color.gray || board[row][col].getButton().getBackground() == Color.red) {
        //checks for king side castling
        if (startP.getColumn() + 3 < 8 && startP.getType().equals("KING") && board[startP.getRow()][startP.getColumn() + 3].getType().equals("ROOK") && startP.getColumn() + 2 == col) {
          movePiece(startP, board[startP.getRow()][startP.getColumn() + 2]);
          movePiece(board[startP.getRow()][startP.getColumn() + 3], board[startP.getRow()][startP.getColumn() + 1]);
          turn--;
        }
          //checks for queen side castling
          else if(startP.getColumn()-4 > 0 && startP.getType().equals("KING") && board[startP.getRow()][startP.getColumn()-4].getType().equals("ROOK") && startP.getColumn() - 2 == col) {
          movePiece(startP, board[startP.getRow()][startP.getColumn() - 2]);
          movePiece(board[startP.getRow()][startP.getColumn() - 4], board[startP.getRow()][startP.getColumn() - 1]);
          turn--;
        } else {
				  movePiece(startP, board[row][col]);
				  moved = true;
        }
      }
		});
	}

  //moves piece from p1 to p2
	public void movePiece(Piece p1, Piece p2) {
    p2.setPiece(p1.getPiece());
		p1.setPiece(ChessPiece.NO_PIECE);
		clearBoard();
		turn++;
	}

  //resets the board colors
	public void clearBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 1) {
					board[i][j].getButton().setBackground(Color.darkGray);
				} else {
					board[i][j].getButton().setBackground(Color.white);
				}
			}
		}
	}

  //when the game is started, this sets up the pieces in the starting posistions
	public void setup() {
		ChessPiece[] pieces = ChessPiece.values();
    //makes the pieces randomized if fischer random is enabled
		if (random) {
			String[] BlackRandPieces = new String[8];
			String[] WhiteRandPieces = new String[8];
			for (int i = 0; i <= 5; i++) {
				BlackRandPieces[i] = pieces[(i % 3) + 1].toString();
			}
			BlackRandPieces[6] = ChessPiece.BLACK_QUEEN.toString();
			BlackRandPieces[7] = ChessPiece.BLACK_KING.toString();

			List<String> chessList = Arrays.asList(BlackRandPieces);
			Collections.shuffle(chessList);
			BlackRandPieces = chessList.toArray(new String[chessList.size()]);
			for (int i = 0; i < BlackRandPieces.length; i++) {
				WhiteRandPieces[i] = "WHITE_" + BlackRandPieces[i].substring(6);
			}
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (i == 1) {
						board[i][j].setPiece(ChessPiece.BLACK_PAWN);
					} else if (i == 6) {
						board[i][j].setPiece(ChessPiece.WHITE_PAWN);
					} else if (i == 0) {
						board[i][j].setPiece(ChessPiece.valueOf(BlackRandPieces[j]));
					} else if (i == 7) {
						board[i][j].setPiece(ChessPiece.valueOf(WhiteRandPieces[j]));
					}
				}
			}

		} else {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (i == 1) {
						board[i][j].setPiece(ChessPiece.BLACK_PAWN);
					} else if (i == 6) {
						board[i][j].setPiece(ChessPiece.WHITE_PAWN);
					} else if (i == 0) {
						if (j == 0 || j == 7)
							board[i][j].setPiece(ChessPiece.BLACK_ROOK);
						else if (j == 1 || j == 6)
							board[i][j].setPiece(ChessPiece.BLACK_KNIGHT);
						else if (j == 2 || j == 5)
							board[i][j].setPiece(ChessPiece.BLACK_BISHOP);
						else if (j == 3)
							board[i][j].setPiece(ChessPiece.BLACK_QUEEN);
						else if (j == 4)
							board[i][j].setPiece(ChessPiece.BLACK_KING);
					} else if (i == 7) {
						if (j == 0 || j == 7)
							board[i][j].setPiece(ChessPiece.WHITE_ROOK);
						else if (j == 1 || j == 6)
							board[i][j].setPiece(ChessPiece.WHITE_KNIGHT);
						else if (j == 2 || j == 5)
							board[i][j].setPiece(ChessPiece.WHITE_BISHOP);
						else if (j == 3)
							board[i][j].setPiece(ChessPiece.WHITE_QUEEN);
						else if (j == 4)
							board[i][j].setPiece(ChessPiece.WHITE_KING);
					}
				}
			}
		}
		for (int a = 0; a < board.length; a++) {
			for (int b = 0; b < board[a].length; b++) {
				lightButtons(board[a][b]);
			}
		}
	}

  //returns the board array
	public Piece[][] getBoard() {
		return board;
	}

  //sets random to true or false
	public void setRandom(boolean r) {
		random = r;
	}

  //returns firstmove variable
  public boolean isFirstMove(){
    return firstmove;
  }

}

