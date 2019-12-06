import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math.*;

public class Taisai implements Game {
	private Dice[] taisaiDice;
	private String[] diceSymbol;
	private ArrayList<Integer>[] betList;
	private boolean betEnd;
	private int addBetMoney, totalEarnMoney;

	Taisai() {
		taisaiDice = new Dice[3];
		for (int i = 0; i < 3; i++) {
			taisaiDice[i] = new Dice();
		}
		diceSymbol = new String[7];
		diceSymbol[1] = "⚀";
		diceSymbol[2] = "⚁";
		diceSymbol[3] = "⚂";
		diceSymbol[4] = "⚃";
		diceSymbol[5] = "⚄";
		diceSymbol[6] = "⚅";
		betList = new ArrayList[12];
		betList[1] = new ArrayList<Integer>(Collections.nCopies(3, 0));
		betList[2] = new ArrayList<Integer>(Collections.nCopies(3, 0));
		betList[3] = new ArrayList<Integer>(Collections.nCopies(3, 0));
		betList[4] = new ArrayList<Integer>(Collections.nCopies(2, 0));
		betList[5] = new ArrayList<Integer>(Collections.nCopies(7, 0));
		betList[6] = new ArrayList<Integer>(Collections.nCopies(18, 0));
		betList[7] = new ArrayList<Integer>(Collections.nCopies(16, 0));
		betList[8] = new ArrayList<Integer>(Collections.nCopies(5, 0));
		betList[9] = new ArrayList<Integer>(Collections.nCopies(7, 0));
		betList[10] = new ArrayList<Integer>(Collections.nCopies(21, 0));
		betList[11] = new ArrayList<Integer>(Collections.nCopies(31, 0));
		betEnd = false;
		addBetMoney = 0;
		totalEarnMoney = 0;
	}

	public boolean initialize(int bet) {
		addBetMoney = bet;
		betOption(bet);
		return true;
	}

	public boolean isGameEnd() {
		return betEnd;
	}

	public boolean nextTurn(Scanner input) {
		System.out.println();
		System.out.flush();
		if (!isGameEnd()) {
			do {
				System.out.print("How much will you bet: ");
				addBetMoney = input.nextInt();
				if (addBetMoney >= 0) {
					break;
				} else {
					System.out.println("Cannot place bt below 0");
					continue;
				}
			} while (addBetMoney < 0);
			if (addBetMoney != 0) {
				betOption(addBetMoney);
			}
		} else {
			rollDice();
			// something();
			return false;
		}
		return true;
	}

	public boolean printOneTurn() {
		System.out.print("More bet? (Y/N) : ");
		Scanner input = new Scanner(System.in);
		betEnd = input.nextLine().trim().toUpperCase().equals("Y") ? false : true;
		// input.close();
		return true;
	}

	public int cheapGain(int bet) {
		return totalEarnMoney;
	}

	private void betOption(int betMoney) {
		int betnumber1, betnumber2;
		while (true) {
			System.out.println("1. Big / Small Bet (x1)");
			System.out.println("2. Even / Odd Bet (x1)");
			System.out.println("3. Pair Dice Bet (x8)");
			System.out.println("4. Any Triple Bet (x24)");
			System.out.println("5. Triple Bet (x150)");
			System.out.println("6. Total Number Bet (x6~50)");
			System.out.println("7. Domino Bet (x5)");
			System.out.println("8. Easy Combination Bet (x7)");
			System.out.println("9. Single Dice Bet (x1~3)");
			System.out.println("10. Hard Number Bet (x30)");
			System.out.println("11. Pair Plus Bet (x50)");
			System.out.print("Where to bet? : ");
			Scanner input = new Scanner(System.in);
			betnumber1 = input.nextInt();
			switch (betnumber1) {
			case 1: // big small
				while (true) {
					System.out.println("1. Small Bet: Sum of Dice = 4 ~ 10 (x1)");
					System.out.println("2. Big Bet: Sum of Dice = 11 ~ 17 (x1)");
					System.out.print("Where to bet? : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 2) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 2: // even odd
				while (true) {
					System.out.println("1. Odd Bet: Sum of Dice is Odd (x1)");
					System.out.println("2. Even Bet: Sum of Dice is Even (x1)");
					System.out.print("Where to bet? : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 2) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 3: // pair dice
				while (true) {
					System.out.println("Two Dice is Same (x8)");
					System.out.print("Which Number to bet? (1~6) : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 6) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 4: // any triple
				System.out.println("Three Dice is Same (x24)");
				betnumber2 = 1;
				break;
			case 5: // triple
				while (true) {
					System.out.println("Three Dice is Same (x150)");
					System.out.print("Which Number to bet? (1~6) : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 6) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 6: // total number
				while (true) {
					System.out.println("Three Dice Total Number");
					System.out.println("Total number is 4 or 17 (x50)");
					System.out.println("Total number is 5 or 16 (x30)");
					System.out.println("Total number is 6 or 15 (x18)");
					System.out.println("Total number is 7 or 14 (x12)");
					System.out.println("Total number is 8 or 13 (x8)");
					System.out.println("Total number is 9, 10, 11, 12 (x6)");
					System.out.print("Which Number to bet? (4~17) : ");
					betnumber2 = input.nextInt();
					if ((betnumber2 >= 4 && betnumber2 <= 17) || betnumber2 == 0) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 7: // domino 1 2 3 4 5 6
				while (true) {
					System.out.println("Two Dice Combination (x5)");
					System.out.println(" 1. 1 2 |  2. 1 3 |  3. 1 4 |  4. 1 5 |  5. 1 6");
					System.out.println(" 6. 2 3 |  7. 2 4 |  8. 2 5 |  9. 2 6");
					System.out.println("10. 3 4 | 11. 3 5 | 12. 3 6");
					System.out.println("13. 4 5 | 14. 4 6");
					System.out.println("15. 5 6");
					System.out.print("Where to bet? : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 15) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 8: // easy combination
				while (true) {
					System.out.println("Three Dice Combination included in Four Dices");
					System.out.println("1. 1 2 3 4 (x7)");
					System.out.println("2. 2 3 4 5 (x7)");
					System.out.println("3. 2 3 5 6 (x7)");
					System.out.println("4. 3 4 5 6 (x7)");
					System.out.print("Where to bet? : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 4) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 9: // single dice
				while (true) {
					System.out.println("One Number is included in Three Dices");
					System.out.println("Number matches One Dice (x1)");
					System.out.println("Number matches Two Dices (x2)");
					System.out.println("Number matches All Three Dices (x3)");
					System.out.print("Which Number to bet? (1~6) : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 6) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 10: // hard number 1 2 3 4 5 6
				while (true) {
					System.out.println("Three Different Dices (x30)");
					System.out.println(" 1. 1 2 6 |  2. 1 3 5 |  3. 2 3 4 |  4. 2 5 6 |  5. 3 4 6");
					System.out.println(" 6. 1 2 3 |  7. 1 3 6 |  8. 1 4 5 |  9. 2 3 5 | 10. 3 5 6");
					System.out.println("11. 1 2 4 | 12. 1 4 6 | 13. 2 3 6 | 14. 2 4 5 | 15. 4 5 6");
					System.out.println("16. 1 2 5 | 17. 1 3 4 | 18. 1 5 6 | 19. 2 4 6 | 20. 3 4 5");
					System.out.print("Where to bet? : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 20) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 11: // pair plus 1 2 3 4 5 6
				while (true) {
					System.out.println("One Pair Dice and Another Dice (x50)");
					System.out.println(" 1. 1 1 2 |  2. 1 1 3 |  3. 1 1 4 |  4. 1 1 5 |  5. 1 1 6");
					System.out.println(" 6. 2 2 1 |  7. 2 2 3 |  8. 2 2 4 |  9. 2 2 5 | 10. 2 2 6");
					System.out.println("11. 3 3 1 | 12. 3 3 2 | 13. 3 3 4 | 14. 3 3 5 | 15. 3 3 6");
					System.out.println("16. 4 4 1 | 17. 4 4 2 | 18. 4 4 3 | 19. 4 4 5 | 20. 4 4 6");
					System.out.println("21. 5 5 1 | 22. 5 5 2 | 23. 5 5 3 | 24. 5 5 4 | 25. 5 5 6");
					System.out.println("26. 6 6 1 | 27. 6 6 2 | 28. 6 6 3 | 29. 6 6 4 | 30. 6 6 5");
					System.out.print("Where to bet? : ");
					betnumber2 = input.nextInt();
					if (betnumber2 >= 0 && betnumber2 <= 30) {
						break;
					} else {
						System.out.println("Wrong Number!");
					}
				}
				break;
			case 0:
				System.out.println("Stop Bet!");
				// input.close();
				return;
			default:
				System.out.println("Wrong Number!");
				continue;
			}
			if (betnumber2 == 0) {
				System.out.println("Stop Bet!");
				// input.close();
				return;
			}
			betList[betnumber1].set(betnumber2, betList[betnumber1].get(betnumber2) + betMoney);
			System.out.println("Successsfully Bet " + addBetMoney + "!");
			// input.close();
			break;
		}
	}

	private void rollDice() {
		for (int i = 0; i < 100000; i++) {
			taisaiDice[0].roll();
			taisaiDice[1].roll();
			taisaiDice[2].roll();
			System.out.println(diceSymbol[taisaiDice[0].getDiceNum()] + diceSymbol[taisaiDice[1].getDiceNum()] + diceSymbol[taisaiDice[2].getDiceNum()]);
		}
	}
}