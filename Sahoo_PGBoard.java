/*Ankur Sahoo
 * Period 2
 * Patriot Games: This is a game that two players play in which they alternatively
 * place red and blue pieces on a board and whoever gets their color from their
 * base to the other side wins*/

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Sahoo_PGBoard {

	public static final int[] VERT_DISP =  {-1,-1,0,1,1,0};
	public static final int[] HORIZ_DISP = 	{-1,0,1,1,0,-1};
	private char[][] board;
	private int length;

	//one parameter constructor that builds an empty matrix with the parameter
	public Sahoo_PGBoard(int dimension)
	{
		board=new char[dimension][dimension];
		length=dimension;
	}

	//two parameter constructor that reads a file and builds the matrix
	public Sahoo_PGBoard(String fname, int dimension)
	{
		Scanner fileIn=null;

		board = new char[dimension][dimension];
		length=dimension;

		try
		{
			fileIn = new Scanner(new File(fname));
		}catch(IOException e){
			board=new char[dimension][dimension];
		}

		for(int row = 0; row<dimension; row++)
		{
			String line = fileIn.nextLine();
			for(int col = 0; col<dimension; col++)
			{
				board[row][col]=line.charAt(col);
			}
		}
	}

	//sets the spot at the parameters blue
	public void setBlue(int row, int col)
	{
		board[row][col]='b';
	}

	//sets the spot at the parameters white
	public void setWhite(int row, int col)
	{
		board[row][col]='w';
	}

	//returns true if the spot at the parameters is blue
	public boolean isBlue(int row, int col)
	{	
		return(board[row][col]=='b');
	}

	//returns true if the spot at the parameters is white
	public boolean isWhite(int row, int col)
	{
		return(board[row][col]=='w');
	}

	//returns true if the spot in the parameters is in bounds
	private boolean isInBounds(int row, int col)
	{
		return((row<length&&col<length)&&(row>=0 && col>=0));
	}

	//prints out the matrix 
	public String toString()
	{
		String toReturn = "";

		for(int row = 0; row<length; row++)
		{
			for(int col = 0; col<length; col++)
			{
				toReturn+=(board[row][col]) + " ";
			}

			toReturn+="\n";
		}

		return toReturn;
	}

	//makes a blob using the parameter
	public void fillBlob(int row, int col)
	{
		if(!isInBounds(row,col) || !isBlue(row,col))
			return;

		setWhite(row,col);

		//recursive calls for all the possible neighbors
		for (int i = 0; i<VERT_DISP.length; i++)
		{
			fillBlob(row+HORIZ_DISP[i], col+VERT_DISP[i]);
		}

	}

	//returns a PGBoard that consists of a blob that begins at the parameters
	public Sahoo_PGBoard getBoard(int row, int col)
	{
		Sahoo_PGBoard blob = new Sahoo_PGBoard(length);

		if(isBlue(row,col))
		{
			fillBlob(row,col);

			//go through the matrix to fill out the newly created PGBoard
			for(int r = 0; r<length; r++)
			{
				for(int c = 0; c<length; c++)
				{
					//if white, add it to the PGBoard as blue and make it blue in the original matrix
					if(isWhite(r,c))
					{
						blob.setBlue(r, c);
						setBlue(r,c);
					}
				}
			}
		}

		return blob;
	}

	//examines all the blobs from the matrix's first column and checks if blue won
	public boolean hasBlueWon()
	{
		//goes through the first column of the matrix to see if there are any blue pieces
		for(int row = 0; row<length; row++)
		{
			Sahoo_PGBoard blob=getBoard(row, 0);

			//if there was a blue piece in the first column of the blob
			//check if there are any blue pieces in the last column
			for(int r = 0; r<blob.length; r++)
			{
				if(blob.isBlue(r, length-1))
				{
					System.out.println("Blue won \n");
					System.out.println(blob);
					return true;
				}
			}
		}
		
		System.out.println("Blue lost");
		return false;
	}
}
