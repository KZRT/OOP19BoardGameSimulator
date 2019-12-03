import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Blackjack implements Game {
	private Deck blackDeck;
	private ArrayList<Card> playerCard;
	private ArrayList<Card> dealerCard;
	private int betMoney, turn;
	private boolean check;
	private boolean endGame;
	
	
	Blackjack() {
		blackDeck = new Deck(3);
		playerCard = new ArrayList<>(10);
		dealerCard = new ArrayList<>(10);
		turn = 0;
		betMoney = 0;
		
	}
	
	public boolean initialize(int bet) {
        betMoney = bet;
        turn = 1;
        endGame = false; check = false;
        blackDeck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            playerCard.add(blackDeck.popOneCard());
            dealerCard.add(blackDeck.popOneCard());
        }
        return true;
    }
	
	public boolean printOneTurn() {
		if (turn == 1) Collections.shuffle(dealerCard);
		Card temp = dealerCard.get(0);
		System.out.println("Dealer Card");
		System.out.print("("+temp.getShape() + "\t");
		temp.print(); System.out.println(")");
		for (int i = 1; i < dealerCard.size(); i++) {
			System.out.println("(*) ");
		}
		System.out.println(""); System.out.println("Your Card");
		for (int i = 0; i < playerCard.size(); i++) {
			System.out.print("("+playerCard.get(i).getShape() + " ");
			playerCard.get(i).print(); System.out.println(")");
		}
		System.out.println("");
		
		
		if (dealerCard.get(0).getNum() == 1 && turn == 1 && sumOfNum(playerCard) <= 21) {
			if (sumOfNum(playerCard) == 21) {
				System.out.println("Will you pay even money? 1.Yes 2.No");
			}
			else if (sumOfNum(playerCard) < 21) {
				System.out.println("Will you pay half your bet money for Insurance? 1.Yes 2.No");
			}
			Scanner inputIn = new Scanner(System.in);
	        int numIn = inputIn.nextInt();
	        if (numIn == 1) {
	        	if (sumOfNum(dealerCard) == 21) {
	        		endGame = true;
	        		check = true;
	        		return true;
	        	} else {
	        		
	        		return true;
	        	}
	        }
		}
		
		return true;
	}
	
	
	public int sumOfNum(ArrayList<Card> c) {
		int temp = 0, count = 0;
		for (int i = 0; i < c.size();i++) {
			int j = c.get(i).getNum();
			if (j > 10) {
				temp+=10;
			} else if (j > 1 && j <= 10) {
				temp += j;
			} else {
				count++;
			}
		}
		if (count > 0) {
			int tm1 = temp + count; int tm2 = temp + (count * 11);
			if (tm1 >= 21) {
				temp = tm1;
			} else if (tm2 <= 21 && tm2 != 17) {
				temp = tm2;
			} else temp = tm1;
		}
		return temp;
	}
	
	public boolean nextTurn(Scanner input) {
		boolean checkIf = false;
		if (endGame == false) {
			if (sumOfNum(playerCard) > 21 && sumOfNum(dealerCard) > 21) {
				System.out.println("Both you and the dealer bust.");
				check = true;
				endGame = true;
				return true;
			} else if (sumOfNum(playerCard) > 21 && sumOfNum(dealerCard) < 21) {
				System.out.println("You bust. U lost your whole money. Haha!!!");
				betMoney = 0;
				endGame = true; return true;
			} else if (sumOfNum(playerCard) <= 21 && sumOfNum(dealerCard) > 21) {
				System.out.println("Dealer bust. U got money.");
				endGame = true; return true;
			}
			
			
			System.out.println("What will you do? \n 1.Hit 2.Stay");
	        int num = input.nextInt();
	        if (num == 1) {
	        	playerCard.add(blackDeck.popOneCard());
	        	Card newCard = new Card();
	        	newCard = playerCard.get(playerCard.size()-1);
	        	System.out.println("Your new Card : " + newCard.getShape()+" ");
	        	newCard.print(); System.out.println("");
	        }
	        else if (num == 2) {
	        	checkIf = true;
	        } else System.out.println("Wrong Input");
	        
	        if (sumOfNum(dealerCard) <= 16) {
	        	dealerCard.add(blackDeck.popOneCard());
	        	System.out.println("Dealer got one more card.");
	        }
	        
	        if (sumOfNum(playerCard) > 21 && sumOfNum(dealerCard) > 21) {
				System.out.println("Both you and the dealer bust.");
				check = true;
				endGame = true;
				return true;
			} else if (sumOfNum(playerCard) > 21 && sumOfNum(dealerCard) < 21) {
				System.out.println("You bust. U lost your whole money. Haha!!!");
				betMoney = 0;
				endGame = true; return true;
			} else if (sumOfNum(playerCard) <= 21 && sumOfNum(dealerCard) > 21) {
				System.out.println("Dealer bust. U got money.");
				endGame = true; return true;
			}
	        
	        if (checkIf == true && sumOfNum(dealerCard) > 16) {
	        	int checkD = 21 - sumOfNum(dealerCard);
	        	int checkP = 21 - sumOfNum(playerCard);
	        	if (checkD == checkP) {
	        		System.out.println("Fair.");
	        		check = true;
	        	} else if (checkD < checkP) {
	        		betMoney = 0;
	        		System.out.println("You lose.");
	        	} else {
	        		System.out.println("You win.");
	        	}
	        	endGame = true; return true;
	        }
		turn++;
		if (turn == 9) {
			endGame = true; return true;
		}
		}
		return true;
        
	}

	public boolean isGameEnd() {
		if (endGame == true) {
			System.out.println("Dealer's Card");
			for (int i = 0; i < dealerCard.size(); i++) {
				System.out.print("("+dealerCard.get(i).getShape() + " ");
				dealerCard.get(i).print(); System.out.println(")");
			}
			return true;
		}
		return false;
	}

	
	
	

	public int cheapGain(int bet) {
		if (betMoney != bet) {
			return betMoney;
		} else if (check == true) {
			return bet;
		} else {
			return bet*2;
		}
		
	}

	
	
}