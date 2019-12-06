import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Blackjack implements Game {
	private Deck blackDeck;
	private ArrayList<Card> playerCard;
	private ArrayList<Card> dealerCard;
	private ArrayList<Card> p2Card;
	private int betMoney, turn;
	private boolean check, bust1, bust2;
	private boolean endGame, splitCard;
	
	
	Blackjack() {
		blackDeck = new Deck(6);
		playerCard = new ArrayList<>(10);
		dealerCard = new ArrayList<>(10);
		p2Card = new ArrayList<>(10);
		turn = 0;
		betMoney = 0;
		
	}
	
	public boolean initialize(int bet) {
        betMoney = bet;
        turn = 1;
        endGame = false; check = false; splitCard = false; bust1=false; bust2=false;
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
		Scanner inputIn = new Scanner(System.in);
		System.out.println("Dealer Card");
		System.out.print("("+temp.getShape() + "\t");
		temp.print(); System.out.println(")");
		System.out.println("(*)");
		System.out.println(""); System.out.println("Your Card");
		for (int i = 0; i < playerCard.size(); i++) {
			System.out.print("("+playerCard.get(i).getShape() + " ");
			playerCard.get(i).print(); System.out.println(")");
		}
		System.out.println("");
		
		if (temp.getNum() == 1 && turn == 1) {
			if (sumOfNum(playerCard) == 21) {
				System.out.println("Will you pay even money? 1.Yes 2.No");
			}
			else if (sumOfNum(playerCard) < 21) {
				System.out.println("Will you pay half your bet money for Insurance? 1.Yes 2.No");
			}
			
	        int numIn = inputIn.nextInt();
	        if (numIn == 1) {
	        	if (sumOfNum(dealerCard) == 21) {
	        		System.out.println("Insurance succeeded. Your money is returned.");
	        		check = true; endGame = true;
	        		return true;
	        	} else {
	        		
	        		System.out.println("You lost your additional bet money.");
	        	}
	        } else {}
		}
		
		if (playerCard.get(0).getNum() == playerCard.get(1).getNum() && turn == 1) {
			System.out.println("Will you split your Card with twice more bet money? 1.Yes 2.No");
			int c = inputIn.nextInt();
			if (c==1) {
				p2Card.add(playerCard.get(0)); p2Card.add(blackDeck.popOneCard());
				playerCard.set(0, blackDeck.popOneCard()); splitCard = true;
				System.out.println("First Deck\n");
				for (int i = 0; i < 2; i++) {
					System.out.print("("+playerCard.get(i).getShape() + " ");
					playerCard.get(i).print(); System.out.println(")");
				}
				System.out.println("Second Deck\n");
				for (int i = 0; i < 2; i++) {
					System.out.print("("+p2Card.get(i).getShape() + " ");
					p2Card.get(i).print(); System.out.println(")");
				}
				betMoney *= 2;
			}
		}
		
		
		
		if (turn == 1 && !splitCard) {
			System.out.println("Will you play double down? 1. Yes 2. No");
			int tem = inputIn.nextInt();
			if (tem == 1) {
				betMoney *= 2;
				playerCard.add(blackDeck.popOneCard());
				if (sumOfNum(playerCard) > 21) bust1 = true;
				printResult(dealerCard, playerCard);
				return true;
			} else {}
			
		}
		boolean fir = false, sec = false;
		while (turn != 9) {
			
			if (fir == false) {
				if (splitCard) System.out.print("First deck : ");
				System.out.println("1.Hit 2.Stay");
				int hs = inputIn.nextInt();
				if (hs == 1) {
					playerCard.add(blackDeck.popOneCard());
					Card newCard = playerCard.get(playerCard.size()-1);
					System.out.println("Your new Card : " + newCard.getShape() + "\t"); newCard.print();
					System.out.println();
				} else if (hs==2) {
					if (!splitCard) {
						
						turn = 9;
					}
					fir = true;
				}
			}
			if (splitCard && sec == false) {
				System.out.println("Second Deck : 1.Hit 2.Stay");
				int hs = inputIn.nextInt();
				if (hs == 1) {
					p2Card.add(blackDeck.popOneCard());
					Card newCard = p2Card.get(playerCard.size()-1);
					System.out.println("Your new second deck Card : " + newCard.getShape() + "\t"); newCard.print(); System.out.println();
				} else if (hs==2) {
					sec = true;
				}
			}
			if (sumOfNum(playerCard) > 21) {
				fir = true; bust1=true;
			}
			if (sumOfNum(p2Card) > 21) {
				sec = true; bust2=true;
			}
			if (!splitCard && fir) {
				turn = 9;
				printResult(dealerCard,playerCard);
			}
			if (splitCard && fir && sec) {
				turn = 9;
				printResult(dealerCard, playerCard, p2Card);
			}
			if (turn != 9) turn++;
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
			} else if (tm2 <= 21) {
				temp = tm2;
			} else temp = tm1;
		}
		return temp;
	}
	
	public boolean nextTurn(Scanner input) {
		
        return false;
	}

	public boolean isGameEnd() {
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

	public void printResult(ArrayList<Card> dl, ArrayList<Card> pl) {
		if (!bust1) {
			while (sumOfNum(dl) <= 16) {
				dl.add(blackDeck.popOneCard());
			}
		}
		System.out.println("Your Card Deck");
		for (int i = 1; i <= pl.size(); i++) {
			System.out.println(i + "th card : " + pl.get(i-1).getShape() + "\t");
			pl.get(i-1).print(); System.out.println();
		}
		System.out.println("Dealer's Card Deck");
		for (int i = 1; i <= dl.size(); i++) {
			System.out.println(i + "th card : " + dl.get(i-1).getShape() + "\t");
			dl.get(i-1).print(); System.out.println();
		}
		if (bust1) {
			betMoney = 0; System.out.println("You bust."); endGame=true;
		}
		else {
			if (sumOfNum(dl) > 21) {
				System.out.println("Dealer Bust."); betMoney*=2; endGame=true;
			} else {
				int dScore = 21 - sumOfNum(dl); int pScore = 21 - sumOfNum(pl);
				if (dScore <= pScore) {
					System.out.println("You lose."); betMoney=0;
				} else {
					System.out.println("You win."); betMoney*=2;
				}
			}
		}
	}
	
public void printResult(ArrayList<Card> dl, ArrayList<Card> plF, ArrayList<Card> plS) {
	if (!bust1 || !bust2) {
		while (sumOfNum(dl) <= 16) {
			dl.add(blackDeck.popOneCard());
		}
	}
	System.out.println("Your First Card Deck");
	for (int i = 1; i <= plF.size(); i++) {
		System.out.println(i + "th card : " + plF.get(i-1).getShape() + "\t");
		plF.get(i-1).print(); System.out.println();
	}
	System.out.println("");
	System.out.println("Your Second Card Deck");
	for (int i = 1; i <= plS.size(); i++) {
		System.out.println(i + "th card : " + plS.get(i-1).getShape() + "\t");
		plS.get(i-1).print(); System.out.println();
	}
	System.out.println("");
	System.out.println("Dealer's Card Deck");
	for (int i = 1; i <= dl.size(); i++) {
		System.out.println(i + "th card : " + dl.get(i-1).getShape() + "\t");
		dl.get(i-1).print(); System.out.println();
	}
	if (bust1 && bust2) {
		System.out.println("Your both decks bust."); betMoney = 0; endGame = true;
	}
	else if (bust1 && !bust2) {
		System.out.println("Your first deck bust."); betMoney /= 2;
		int dScore = 21 - sumOfNum(dl); int pScore = 21 - sumOfNum(plS);
		if (dScore <= pScore) {
			System.out.println("You lose."); betMoney=0;
		} else {
			System.out.println("You win."); betMoney*=2;
		}
		endGame = true;
	}
	else if (!bust1 && bust2) {
		System.out.println("Your second deck bust."); betMoney /= 2;
		int dScore = 21 - sumOfNum(dl); int pScore = 21 - sumOfNum(plF);
		if (dScore <= pScore) {
			System.out.println("You lose."); betMoney=0;
		} else {
			System.out.println("You win."); betMoney*=2;
		}
		endGame = true;
	}
	else {
		if (sumOfNum(dl) > 21) {
			System.out.println("Dealer Bust."); betMoney*=2;
		} else {
			int dScore = 21 - sumOfNum(dl); int pScore = 21 - sumOfNum(plF);
			int p2Score = 21 - sumOfNum(plS);
			if (dScore <= pScore && dScore <= p2Score) {
				System.out.println("You lose."); betMoney=0;
			} else if (dScore <= pScore && dScore > p2Score){
				System.out.println("Your first deck wins.");
			} else if (dScore > pScore && dScore <= p2Score) {
				System.out.println("Your Second deck wins.");
			} else {
				System.out.println("Your both decks win."); betMoney*=2;;
			}
		}
		
	}
	}
	
}