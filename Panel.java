import java.awt.*;
import javax.swing.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import java.util.Random;

//sets up the panel structure
public class Panel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private GameBoard gb = new GameBoard();
	private Piece[][] board = gb.getBoard();
  //panel for starting the game
	private JPanel panelButton = new JPanel();
  //panel for the chess game
	private JPanel chessBoard = new JPanel();
  //panel for choosing the chess type
	private JPanel chessType = new JPanel();
  //panel to read the settings
	private JPanel settings = new JPanel();
	private JButton chess = new JButton("Click here to play chess");
  //panel for when you promote a pawn
	private JPanel promotion = new JPanel();
  //sets up the emote
  private JPanel emote = new JPanel();
	private int promoterow;
	private int promotecol;
	private boolean white;
	
	public Panel() {
		this.setSize(600, 600);
    //setting the layouts for all the panels
		CardLayout c = new CardLayout();
		this.setLayout(c);
		panelButton.setLayout(new GridLayout(2, 2));
		chessBoard.setLayout(new GridBagLayout());
		chessType.setLayout(new FlowLayout());
		settings.setLayout(new FlowLayout());
    //setting up the settings panel
		settings.setBounds(50, 50, 500, 500);
		JTextArea instructions = new JTextArea();
    instructions.setPreferredSize(new Dimension(500,300));
    instructions.setBackground(Color.gray);
    instructions.setLineWrap(true);
    instructions.setWrapStyleWord(false);


    add(instructions,BorderLayout.CENTER);
    instructions.setText("Chess is a classical piece and board game with two players." + "\n" + "Commonly regarded as a war game, players must capture their opponent's king piece." + "\n" + "There are 32 pieces in total, each side starting with 16 pieces." + "\n" + "Played on a grid, each piece has a different rule for moving to another square." + "\n" + "Here are how each piece moves:" + "\n" + "-Kings move one square in any direction, so long as that square is not attacked by an" + "\n" + "enemy piece.Additionally, kings are able to make a special move, known as castling." + "\n" + "-Queens move diagonally, horizontally, or vertically any number of squares. They are" + "\n" + "unable to jump over pieces." + "\n" + "-Rooks move horizontally or vertically any number of squares.They are unable to jump" + "\n" + "over pieces. Rooks move when the king castles." + "\n" + "-Bishops move diagonally any number of squares. They are unable to jump over pieces." + "\n" + "-Knights move in an ‘L’ shape’: two squares in a horizontal or vertical direction, then " + "\n" + "move one square horizontally or vertically. They are the only piece able to jump over" + "\n" + "other pieces." + "\n" + "-Pawns move vertically forward one square, with the option to move two squares if they " + "\n" + "have not yet moved.Pawns are the only piece to capture different to how they move. The " + "\n" + "pawns capture one square diagonally in a forward direction." + "\n" + "Fischer Random Instructions:" + "\n" + "Fischer Random is almost the exact same as classical chess, however theres a slight" + "\n" + "change. In Fischer Random chess, all the starting pieces except pawns are randomized.");
    instructions.setEditable(false);
    instructions.setFont(new Font("Serif", Font. BOLD, 10));
		Dimension size = instructions.getPreferredSize();
    JLabel title = new JLabel("Classic and Fischer Random Instructions:");
		instructions.setSize(size);
		settings.setBounds(50, 500, 500, 500);
		settings.add(title);
    settings.add(instructions);
    //sets up other panels
		setUpButtons();
		chessType();
		setUpChess();
		promotion();
    setupEmote();
    //sets up main panel and adds other panels to main panel
		this.setBackground(Color.red);
		this.setBounds(0, 0, 600, 600);
		this.add(panelButton, "Button");
		this.add(chessBoard, "Chess");
		this.add(settings, "Setting");
		this.add(chessType, "Type");
		this.add(promotion, "Promote");
    this.add(emote, "Emote");
		c.show(this, "Button");
	}

  //sets up button panel
	public void setUpButtons() {
		panelButton.setBounds(50, 50, 500, 500);
		JButton[] buttons = { chess };
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setBackground(Color.black);
			buttons[i].setForeground(Color.white);
			panelButton.add(buttons[i]);
		}
    //when clicked it will bring you to the chess type panel
		chess.addActionListener(actionEvent -> {
			((CardLayout) this.getLayout()).show(this, "Type");
		});
	}
	
	//sets up the chess board panel
  Random rn = new Random();
  int rand = rn.nextInt(2) + 1;
	public void setUpChess() {
 		GridBagConstraints grid = new GridBagConstraints();
		chessBoard.setBounds(50, 50, 500, 500);
		chessBoard.setBorder(new LineBorder(Color.black));
		int c = 2;
    //makes 8 by 8 chess grid
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				grid.gridx = j;
				grid.gridy = i;
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(64, 64));
				button.setMargin(new Insets(0, 0, 0, 0));
				board[i][j] = new Piece(ChessPiece.NO_PIECE);
				if (c % 2 == 0) {
					button.setBackground(Color.white);
				} else {
					button.setBackground(Color.darkGray);
				}
				c++;
				chessBoard.add(button, grid);
				board[i][j].setButton(button);
				board[i][j].setColumn(j);
				board[i][j].setRow(i);
				final int row = i;
				final int col = j;
        //for when a pawn reaches the end of the board and needs to be promoted
				board[i][j].getButton().addActionListener(actionEvent -> {
					if(row == 0 && board[row][col].getPiece() == ChessPiece.WHITE_PAWN) {
						((CardLayout) this.getLayout()).show(this, "Promote");	
						white = true;
					} else if(row == 7 && board[row][col].getPiece() == ChessPiece.BLACK_PAWN) {
						((CardLayout) this.getLayout()).show(this, "Promote");
						white = false;
					}
					promoterow = row;
					promotecol = col; 
				});
			}
			c++;
		}
    JButton emote = new JButton("Emote");
    emote.setLocation(100, 100);
    //if emote button is pressed it shows a emote
    emote.addActionListener(actionEvent -> {
      setupEmote();
      ((CardLayout) this.getLayout()).show(this, "Emote");
       System.out.println("Heheheha");
    });
    chessBoard.add(emote);
	}

  //sets up chess type panel
	public void chessType() {
		chessType.setBounds(50, 50, 500, 500);
		JLabel label = new JLabel("Choose Chess Type: ");
		JButton classic = new JButton("Classic");
		JButton frand = new JButton("Fischer Random");
		JButton back = new JButton("↩️");
		JLabel label2 = new JLabel("Press I for information and E to exit");
    //normal chess
		classic.addActionListener(actionEvent -> {
			gb.setRandom(false);
			gb.setup();
			((CardLayout) this.getLayout()).show(this, "Chess");
		});
    //fischer random chess
		frand.addActionListener(actionEvent -> {
			gb.setRandom(true);
			gb.setup();
			((CardLayout) this.getLayout()).show(this, "Chess");
		});
    //goes back to main panel
		back.addActionListener(actionEvent -> {
			((CardLayout) this.getLayout()).show(this, "Button");
		});
		this.addKeyListener(this);
		this.setFocusable(true);
		chessType.add(label);
		chessType.add(classic);
		chessType.add(frand);
		chessType.add(back);
		chessType.add(label2);
	}

  //panel for when a pawn is promoted
	public void promotion() {
		this.setBounds(50, 50, 500, 500);
		JLabel choice = new JLabel("What do you want to promote your pawn to?");
		JButton queen = new JButton("Queen");
		JButton rook = new JButton("Rook");
		JButton bishop = new JButton("Bishop");
		JButton knight = new JButton("Knight");
    //each button when clicked turns the pawn into whatever the button name is
		queen.addActionListener(actionEvent -> {
			((CardLayout) this.getLayout()).show(this, "Chess");
			if(white) 
				board[promoterow][promotecol].setPiece(ChessPiece.WHITE_QUEEN);
			else
				board[promoterow][promotecol].setPiece(ChessPiece.BLACK_QUEEN);
			
		});
		rook.addActionListener(actionEvent -> {
			((CardLayout) this.getLayout()).show(this, "Chess");
			if(white) 
				board[promoterow][promotecol].setPiece(ChessPiece.WHITE_ROOK);
			else
				board[promoterow][promotecol].setPiece(ChessPiece.BLACK_ROOK);
		});
		bishop.addActionListener(actionEvent -> {
			((CardLayout) this.getLayout()).show(this, "Chess");
			if(white) 
				board[promoterow][promotecol].setPiece(ChessPiece.WHITE_BISHOP);
			else
				board[promoterow][promotecol].setPiece(ChessPiece.BLACK_BISHOP);
		});
		knight.addActionListener(actionEvent -> {
			((CardLayout) this.getLayout()).show(this, "Chess");
			if(white) 
				board[promoterow][promotecol].setPiece(ChessPiece.WHITE_KNIGHT);
			else
				board[promoterow][promotecol].setPiece(ChessPiece.BLACK_KNIGHT);
		});
		promotion.add(choice);
		promotion.add(queen);
		promotion.add(rook);
		promotion.add(bishop);
		promotion.add(knight);
	}

  //sets up emote panel
  public void setupEmote(){
    JButton goBack = new JButton("Go Back");
    goBack.setLocation(0, 600);
    emote.add(goBack);
    goBack.addActionListener(actionEvent -> {
      ((CardLayout) this.getLayout()).show(this, "Chess");
    });
    
    try {
				BufferedImage b = ImageIO.read(new File("images/1.jpg"));
				Image scale = b.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        JLabel jl = new JLabel(new ImageIcon(scale));
        emote.add(jl);
			} catch (IOException e) {
				e.printStackTrace();
			}
  }

  //detects keypress
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_I) {
			((CardLayout) this.getLayout()).show(this, "Setting");
		}
		if (keyCode == KeyEvent.VK_E) {
			((CardLayout) this.getLayout()).show(this, "Type");
		}
	}

	public void keyReleased(KeyEvent evt) {}
  
	public void keyTyped(KeyEvent evt) {}
}
