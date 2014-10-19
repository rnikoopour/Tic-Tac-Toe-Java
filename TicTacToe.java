import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class TicTacToe extends JFrame
{
    private JPanel headerPanel;
    private JPanel headerLabel;
    private JPanel newGameSection;
    private JPanel playerSelectPanel;
    private JPanel gameBoard;
    private JPanel footerPanel;

    private JLabel winnerLabel;

    private JButton newGameButton;
    private JButton[] gameButtons;

    private JRadioButton computerRB;
    private JRadioButton humanRB;

    private ButtonGroup playerSelection;

    private GameButtonHandler gameButtonHandler;

    private boolean humanTurn = false;
    private boolean gameOver = false;

    private Random rand = new Random();

    public TicTacToe()
    {
	super("Tic Tac Toe");
	pack();
	gameButtonHandler = new GameButtonHandler();
	
	setLayout(new GridLayout(4,1));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	
	// Header panel is only a Centered JLabel
	headerPanel = new JPanel();
	headerPanel.setLayout(new GridLayout(0, 1));
	headerLabel = new JPanel();
	headerLabel.add(new JLabel("Tic Tac Toe by Reza"));
	headerPanel.add(headerLabel);

	// New Game Section holds the new game button and the radio button
	//  for the players.  It is four wide to not have the buttons
	//  look huge.
	newGameSection = new JPanel();
	newGameSection.setLayout(new FlowLayout());
	// This is a spacer label
	newGameSection.add(new JLabel());
	
	newGameButton = new JButton("New Game");
	newGameButton.addActionListener(gameButtonHandler);
	newGameSection.add(newGameButton);
	
	computerRB = new JRadioButton("Computer");
	humanRB = new JRadioButton("Human");
	humanRB.setSelected(true);
	
	playerSelection = new ButtonGroup();
	playerSelection.add(computerRB);
	playerSelection.add(humanRB);

	playerSelectPanel = new JPanel();
	playerSelectPanel.setLayout(new GridLayout(0,1));
			      
	playerSelectPanel.add(computerRB);
	playerSelectPanel.add(humanRB);
	newGameSection.add(playerSelectPanel);
	// This is a spacer label
	newGameSection.add(new JLabel());
	headerPanel.add(newGameSection);
	add(headerPanel);
	
	gameBoard = new JPanel();
	gameBoard.setLayout(new GridLayout(5, 7));
	gameBoard.add(new JLabel());

	gameButtons = new JButton[25];
	
	for( int i=0; i<25; ++i )
	    {
		gameButtons[i] = new JButton();
		gameButtons[i].addActionListener(gameButtonHandler);
		gameButtons[i].setEnabled(false);
		if(i != 0 && i%5 == 0)
		    {
			gameBoard.add(new JLabel());
			gameBoard.add(new JLabel());
		    }
		gameBoard.add(gameButtons[i]);
	    }
	add(gameBoard);

	footerPanel = new JPanel();
	footerPanel.setLayout(new GridLayout(0, 1));
	JPanel footerLabel = new JPanel();
	winnerLabel = new JLabel("Winner: ");
	footerLabel.add(winnerLabel);
	footerPanel.add(footerLabel);

	add(footerPanel);
	setSize(500,500);
    }
    
    private class GameButtonHandler implements ActionListener
    {
	private int currentTurn = 0;
	private int sameType = 0;

	private boolean CheckStalemate()
	{
	    int numDisabled = 0;
	    for( int i=0; i <25; i++)
		{
		    if( !gameButtons[i].isEnabled() && gameButtons[i].getText() != "")
			{
			    numDisabled++;
			}
		}
	    if (numDisabled == 25)
		{
		    if (CheckWinner())
			{
			    return false;
			}
		    return true;
		}
	    else
		{
		    return false;
		}
	}

	private boolean CheckWinner()
	{
	    // Check vertical wins
	    for (int i=0; i < 5; i++)
		{
		    sameType++;
		    for (int k=5; k <=20; k+=5)
			{
			    if( gameButtons[i+k].getText() == gameButtons[i].getText() && gameButtons[i+k].getText() != "")
				{
				    sameType++;
				}
			}
		    
		    if (sameType == 5)
			{
			    return true;
			}
		    
		    sameType = 0;
		}
	    
	    // Check horizontal wins
	    for (int i=0; i<20; i+=5)
		{
		    sameType++;
		    for(int k=1; k<5; k++)
			{
			    if( gameButtons[i+k].getText() == gameButtons[i].getText() && gameButtons[i+k].getText() != "")
				{
				    sameType++;
				}
			}
		    if (sameType == 5)
			{
			    return true;
			}
		    
		    sameType = 0;
		}
	    
	    sameType = 0;
	    // Check Top left to Bottom right win
	    if (gameButtons[0].getText() != "")
		{
		    sameType++;
		}
	    for (int i=6; i<25; i+=6)
		{
		    if (gameButtons[i].getText() == gameButtons[0].getText() && gameButtons[i].getText() != "")
			{
			    sameType++;
			}
		}
	    if (sameType == 5)
		{
		    return true;
		}
	    
	    sameType = 0;
	    // Check top Right to bottom left win
	    if (gameButtons[4].getText() != "")
		{
		    sameType++;
		}
	    for (int i=8; i<=20; i+=4)
		{
		    if (gameButtons[i].getText() == gameButtons[4].getText() && gameButtons[i].getText() != "")
			{
			    sameType++;
			}
		}
	    if (sameType == 5)
		{
		    return true;
		}
	     
	    sameType = 0;
	    return false;
	}

	public void actionPerformed(ActionEvent event)
	{
	    for( int i=0; i<25; ++i )
		{
		    if(event.getSource() == gameButtons[i])
			{
			    if (currentTurn%2 == 0)
				{
				    gameButtons[i].setText("X");
				}
			    else
				{
				    gameButtons[i].setText("O");
				}
			    gameButtons[i].setEnabled(false);
			    currentTurn++;
			    footerPanel.requestFocus();
			    humanTurn = !humanTurn;
			}
		}

	    if( CheckStalemate() )
		{
		    gameOver = !gameOver;
		    winnerLabel.setText("Stalemate");
		}

	    if (CheckWinner())
		{
		    gameOver = !gameOver;
		    for( int i=0; i<25; ++i )
			{
			    gameButtons[i].setEnabled(false);
			}
		    if( !humanTurn )
			{
			    winnerLabel.setText("Winner: Human!");
			}
		    else
			{
			    winnerLabel.setText("Winner: Computer!");
			}
		}
	    
	    if( event.getSource() == newGameButton )
		{
		    for( int i=0; i<25; i++)
			{
			    gameButtons[i].setEnabled(true);
			    gameButtons[i].setText("");
			    currentTurn = 0;
			}
		    
		    if( computerRB.isSelected() )
			{
			    humanTurn = false;
			    System.out.print("COMPUTER\n");
			}
		    else
			{
			    humanTurn = true;
			    System.out.print("HUMAN\n");
			}
		    winnerLabel.setText("Playing Game..");
		    gameOver = false;
		}

	    if(!humanTurn && !gameOver)
		{
		    int selected = rand.nextInt(24);
		    while(!gameButtons[selected].isEnabled())
			{
			    selected = rand.nextInt(24);
			}
		    gameButtons[selected].doClick();
		}
	}
    }
}
