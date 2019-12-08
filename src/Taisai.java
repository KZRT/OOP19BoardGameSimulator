import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.lang.Math.*;
import java.text.NumberFormat;

public class Taisai implements Game {
    private Dice[] taisaiDice;
    private String[] diceSymbol;
    private ArrayList<Integer>[] betList;
    private boolean betEnd;
    private int addBetMoney, totalBetMoney, totalLoseMoney, totalEarnMoney;

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
        betList[3] = new ArrayList<Integer>(Collections.nCopies(7, 0));
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
        totalBetMoney = bet;
        Player.getInstance().payWallet(addBetMoney);
        betOption(bet);
        return true;
    }

    public boolean isGameEnd() {
        return betEnd;
    }

    public boolean nextTurn(Scanner input) {
        System.out.flush();
        if (!isGameEnd()) {
            System.out.println();
            do {
                System.out.print("How much will you bet: ");
                addBetMoney = input.nextInt();
                if (addBetMoney >= 0) {
                    if (addBetMoney > Player.getInstance().getWallet()) {
                        System.out.println("You can place bet between $1 and $"
                                + NumberFormat.getInstance().format(Player.getInstance().getWallet()) + "\n");
                        addBetMoney = -1;
                        continue;
                    }
                    break;
                } else {
                    System.out.println("Cannot place bet below 0\n");
                    continue;
                }
            } while (addBetMoney < 0);
            if (addBetMoney != 0) {
                totalBetMoney += addBetMoney;
                Player.getInstance().payWallet(addBetMoney);
                betOption(addBetMoney);
            }
        } else {
            rollDice();
            totalLoseMoney = totalBetMoney;
            System.out.println("\n<Winning Bet>");
            totalEarnMoney = calcWinningBet();
            if (totalEarnMoney == 0) {
                System.out.println("Nothing exists.");
            }
            return false;
        }
        return true;
    }

    public boolean printOneTurn() {
        if (Player.getInstance().getWallet() == 0) {
            betEnd = true;
            return true;
        }

        System.out.print("More bet? (Y/N) : ");
        Scanner input = new Scanner(System.in);
        betEnd = input.nextLine().trim().toUpperCase().equals("Y") ? false : true;
        // input.close();
        return true;
    }

    public int cheapGain(int bet) {
        Player.getInstance().setWallet(Player.getInstance().getWallet() + totalBetMoney);
        Player.getInstance().setWallet(Player.getInstance().getWallet() - totalLoseMoney);
        Player.getInstance().setWallet(Player.getInstance().getWallet() + totalEarnMoney);
        return totalEarnMoney - totalLoseMoney;
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
            betList[betnumber1].set(0, 1);
            System.out.println("Successsfully Bet " + betMoney + "!");
            // input.close();
            break;
        }
    }

    private void rollDice() {
        /*
         * for (int i = 0; i < 100000; i++) { taisaiDice[0].roll();
         * taisaiDice[1].roll(); taisaiDice[2].roll();
         * System.out.println(diceSymbol[taisaiDice[0].getDiceNum()] +
         * diceSymbol[taisaiDice[1].getDiceNum()] +
         * diceSymbol[taisaiDice[2].getDiceNum()]); }
         */
        System.out.println("=============================");
        System.out.print("Dice Number :");
        for (int i = 0; i < 3; i++) {
            taisaiDice[i].roll();
            System.out.print(" [" + taisaiDice[i].getDiceNum() + "]");
        }
        System.out.println();
    }

    private int calcWinningBet() {
        final int d1 = taisaiDice[0].getDiceNum();
        final int d2 = taisaiDice[1].getDiceNum();
        final int d3 = taisaiDice[2].getDiceNum();
        // totalEarnMoney = 0;
        for (int i = 1; i < 12; i++) {
            if (betList[i].get(0) == 0) {
                continue;
            } else {
                int sum = d1 + d2 + d3;
                switch (i) {
                case 1:
                    if (sum >= 4 && sum <= 10 && betList[1].get(1) != 0) {
                        totalEarnMoney += betList[1].get(1) * 1;
                        System.out.println("You earn " + betList[1].get(1) * 1 + " from Small Bet!");
                        totalLoseMoney -= betList[1].get(1);
                    }
                    if (sum >= 11 && sum <= 17 && betList[1].get(2) != 0) {
                        totalEarnMoney += betList[1].get(2) * 1;
                        System.out.println("You earn " + betList[1].get(2) * 1 + " from Big Bet!");
                        totalLoseMoney -= betList[1].get(2);
                    }
                    break;
                case 2:
                    if (sum % 2 == 1 && betList[2].get(1) != 0) {
                        totalEarnMoney += betList[2].get(1) * 1;
                        System.out.println("You earn " + betList[2].get(1) * 1 + " from Odd Bet!");
                        totalLoseMoney -= betList[2].get(1);
                    }
                    if (sum % 2 == 0 && betList[2].get(2) != 0) {
                        totalEarnMoney += betList[2].get(2) * 1;
                        System.out.println("You earn " + betList[2].get(2) * 1 + " from Even Bet!");
                        totalLoseMoney -= betList[2].get(2);
                    }
                    break;
                case 3:
                    if (d1 == d2 && d2 != d3 && betList[3].get(d1) != 0) {
                        totalEarnMoney += betList[3].get(d1) * 8;
                        System.out.println("You earn " + betList[3].get(d1) * 8 + " from Pair Dice Bet(" + d1 + ")!");
                        totalLoseMoney -= betList[3].get(d1);
                    } else if (d1 == d3 && d1 != d2 && betList[3].get(d1) != 0) {
                        totalEarnMoney += betList[3].get(d1) * 8;
                        System.out.println("You earn " + betList[3].get(d1) * 8 + " from Pair Dice Bet(" + d1 + ")!");
                        totalLoseMoney -= betList[3].get(d1);
                    } else if (d2 == d3 && d1 != d2) {
                        totalEarnMoney += betList[3].get(d2) * 8;
                        System.out.println("You earn " + betList[3].get(d2) * 8 + " from Pair Dice Bet(" + d2 + ")!");
                        totalLoseMoney -= betList[3].get(d2);
                    }
                    break;
                case 4:
                    if (d1 == d2 && d1 == d3) {
                        totalEarnMoney += betList[4].get(1) * 24;
                        System.out.println("You earn " + betList[4].get(1) * 24 + " from Any Triple Bet!");
                        totalLoseMoney -= betList[4].get(1);
                        betList[4].set(0, 0);
                    }
                    break;
                case 5:
                    if (d1 == d2 && d2 == d3 && betList[5].get(d1) != 0) {
                        totalEarnMoney += betList[5].get(d1) * 150;
                        System.out.println("You earn " + betList[5].get(d1) * 150 + " from Triple Bet(" + d1 + ")!");
                        totalLoseMoney -= betList[5].get(d1);
                    }
                    break;
                case 6:
                    for (int j = 4; j <= 17; j++) {
                        if (sum == j && betList[6].get(j) != 0) {
                            int earnrate;
                            if (j == 4 || j == 17)
                                earnrate = 50;
                            else if (j == 5 || j == 16)
                                earnrate = 30;
                            else if (j == 6 || j == 15)
                                earnrate = 18;
                            else if (j == 7 || j == 14)
                                earnrate = 12;
                            else if (j == 8 || j == 13)
                                earnrate = 8;
                            else
                                earnrate = 6;
                            totalEarnMoney += betList[6].get(j) * earnrate;
                            System.out.println(
                                    "You earn " + betList[6].get(j) * earnrate + " from Triple Bet(" + j + ")!");
                            totalLoseMoney -= betList[6].get(d1);
                        }
                    }
                    break;
                case 7:
                    Set<Integer> diceSet = new HashSet<Integer>();
                    diceSet.add(d1);
                    diceSet.add(d2);
                    diceSet.add(d3);
                    int b1 = 1, b2 = 2;
                    for (int j = 1; j <= 15; j++, b2++) {
                        if (b2 > 6) {
                            b1++;
                            b2 = b1 + 1;
                        }
                        if (betList[7].get(j) == 0) {
                            continue;
                        } else {
                            if (diceSet.contains(b1) && diceSet.contains(b2)) {
                                totalEarnMoney += betList[7].get(j) * 5;
                                System.out.println("You earn " + betList[7].get(j) * 5 + " from Domino Bet(" + b1 + ","
                                        + b2 + ")!");
                                totalLoseMoney -= betList[7].get(j);
                            } else {
                                continue;
                            }
                        }
                    }
                    break;
                case 8:
                    if (!(d1 == d2 || d2 == d3 || d3 == d1)) {
                        for (int j = 1; j <= 4; j++) {
                            if (betList[8].get(j) == 0)
                                continue;
                            Set<Integer> betSet = new HashSet<Integer>();
                            betSet.add(j);
                            betSet.add(j + 2);
                            if (j == 3) {
                                betSet.add(2);
                                betSet.add(6);
                            } else if (j < 3) {
                                betSet.add(j + 1);
                                betSet.add(j + 3);
                            } else {
                                betSet.add(4);
                                betSet.add(6);
                            }
                            if (betSet.contains(d1) && betSet.contains(d2) && betSet.contains(d3)) {
                                List<Integer> bnl = new ArrayList<Integer>(betSet);
                                Collections.sort(bnl);
                                totalEarnMoney += betList[8].get(j) * 7;
                                System.out.println("You earn " + betList[8].get(j) * 7 + " from Easy Combination Bet("
                                        + bnl.get(0) + "," + bnl.get(1) + "," + bnl.get(2) + "," + bnl.get(3) + ")!");
                                totalLoseMoney -= betList[8].get(j);
                            }
                        }
                    }
                    break;
                case 9:
                    if (d1 == d2 && d2 == d3) {
                        if (betList[9].get(d1) != 0) {
                            totalEarnMoney += betList[9].get(d1) * 3;
                            System.out.println(
                                    "You earn " + betList[9].get(d1) * 3 + " from Single Dice Bet(" + d1 + ")!");
                            totalLoseMoney -= betList[9].get(d1);
                        }
                    } else if (d1 == d2 && d1 != d3) {
                        if (betList[9].get(d1) != 0) {
                            totalEarnMoney += betList[9].get(d1) * 2;
                            System.out.println(
                                    "You earn " + betList[9].get(d1) * 2 + " from Single Dice Bet(" + d1 + ")!");
                            totalLoseMoney -= betList[9].get(d1);
                        }
                        if (betList[9].get(d3) != 0) {
                            totalEarnMoney += betList[9].get(d3) * 1;
                            System.out.println(
                                    "You earn " + betList[9].get(d3) * 1 + " from Single Dice Bet(" + d3 + ")!");
                            totalLoseMoney -= betList[9].get(d3);
                        }
                    } else if (d1 == d3 && d1 != d2) {
                        if (betList[9].get(d1) != 0) {
                            totalEarnMoney += betList[9].get(d1) * 2;
                            System.out.println(
                                    "You earn " + betList[9].get(d1) * 2 + " from Single Dice Bet(" + d1 + ")!");
                            totalLoseMoney -= betList[9].get(d1);
                        }
                        if (betList[9].get(d2) != 0) {
                            totalEarnMoney += betList[9].get(d2) * 1;
                            System.out.println(
                                    "You earn " + betList[9].get(d2) * 1 + " from Single Dice Bet(" + d2 + ")!");
                            totalLoseMoney -= betList[9].get(d2);
                        }
                    } else if (d2 == d3 && d1 != d2) {
                        if (betList[9].get(d2) != 0) {
                            totalEarnMoney += betList[9].get(d2) * 2;
                            System.out.println(
                                    "You earn " + betList[9].get(d2) * 2 + " from Single Dice Bet(" + d2 + ")!");
                            totalLoseMoney -= betList[9].get(d2);
                        }
                        if (betList[9].get(d1) != 0) {
                            totalEarnMoney += betList[9].get(d1) * 1;
                            System.out.println(
                                    "You earn " + betList[9].get(d1) * 1 + " from Single Dice Bet(" + d1 + ")!");
                            totalLoseMoney -= betList[9].get(d1);
                        }
                    } else {
                        if (betList[9].get(d1) != 0) {
                            totalEarnMoney += betList[9].get(d1) * 1;
                            System.out.println(
                                    "You earn " + betList[9].get(d1) * 1 + " from Single Dice Bet(" + d1 + ")!");
                            totalLoseMoney -= betList[9].get(d1);
                        }
                        if (betList[9].get(d2) != 0) {
                            totalEarnMoney += betList[9].get(d2) * 1;
                            System.out.println(
                                    "You earn " + betList[9].get(d2) * 1 + " from Single Dice Bet(" + d2 + ")!");
                            totalLoseMoney -= betList[9].get(d2);
                        }
                        if (betList[9].get(d3) != 0) {
                            totalEarnMoney += betList[9].get(d3) * 1;
                            System.out.println(
                                    "You earn " + betList[9].get(d3) * 1 + " from Single Dice Bet(" + d3 + ")!");
                            totalLoseMoney -= betList[9].get(d3);
                        }
                    }
                    break;
                case 10:
                    int[][] optionSet = new int[20][3];
                    optionSet[0][0] = optionSet[1][0] = optionSet[5][0] = optionSet[6][0] = optionSet[7][0] = optionSet[10][0] = optionSet[11][0] = optionSet[15][0] = optionSet[16][0] = optionSet[17][0] = 1;
                    optionSet[0][1] = optionSet[2][0] = optionSet[3][1] = optionSet[5][1] = optionSet[8][0] = optionSet[10][1] = optionSet[12][0] = optionSet[13][0] = optionSet[15][1] = optionSet[18][0] = 2;
                    optionSet[1][1] = optionSet[2][1] = optionSet[4][0] = optionSet[5][2] = optionSet[6][1] = optionSet[8][1] = optionSet[9][0] = optionSet[12][1] = optionSet[16][1] = optionSet[19][0] = 3;
                    optionSet[2][2] = optionSet[4][1] = optionSet[7][1] = optionSet[10][2] = optionSet[11][1] = optionSet[13][1] = optionSet[14][0] = optionSet[16][2] = optionSet[18][1] = optionSet[19][1] = 4;
                    optionSet[1][2] = optionSet[3][1] = optionSet[7][2] = optionSet[8][2] = optionSet[9][1] = optionSet[13][2] = optionSet[14][1] = optionSet[15][2] = optionSet[17][1] = optionSet[19][2] = 5;
                    optionSet[0][2] = optionSet[3][2] = optionSet[4][2] = optionSet[6][2] = optionSet[9][2] = optionSet[11][2] = optionSet[12][2] = optionSet[14][2] = optionSet[17][2] = optionSet[18][2] = 6;
                    int[] diceArray = new int[3];
                    diceArray[0] = d1;
                    diceArray[1] = d2;
                    diceArray[2] = d3;
                    Arrays.sort(diceArray);
                    for (int j = 0; j < 20; j++) {
                        if (betList[10].get(j + 1) != 0) {
                            if (optionSet[j][0] == diceArray[0] && optionSet[j][1] == diceArray[1]
                                    && optionSet[j][2] == diceArray[2]) {
                                totalEarnMoney += betList[10].get(j + 1) * 30;
                                System.out.println("You earn " + betList[10].get(j + 1) * 30 + " from Hard Number Bet("
                                        + diceArray[0] + "," + diceArray[1] + "," + diceArray[2] + ")!");
                                totalLoseMoney -= betList[10].get(j + 1);
                                break;
                            }
                        }
                    }
                    break;
                case 11:
                    if (d1 == d2) {
                        if (betList[11].get((d1 - 1) * 5 + (d3 > d1 ? d3 - 1 : d3)) != 0) {
                            totalEarnMoney += betList[11].get((d1 - 1) * 5 + (d3 > d1 ? d3 - 1 : d3)) * 50;
                            System.out
                                    .println("You earn " + betList[11].get((d1 - 1) * 5 + (d3 > d1 ? d3 - 1 : d3)) * 50
                                            + " from Pair Plus Bet(" + d1 + "," + d2 + "," + d3 + ")!");
                            totalLoseMoney -= betList[11].get((d1 - 1) * 5 + (d3 > d1 ? d3 - 1 : d3));
                        }
                    } else if (d2 == d3) {
                        if (betList[11].get((d2 - 1) * 5 + (d1 > d2 ? d1 - 1 : d1)) != 0) {
                            totalEarnMoney += betList[11].get((d2 - 1) * 5 + (d1 > d2 ? d1 - 1 : d1)) * 50;
                            System.out
                                    .println("You earn " + betList[11].get((d2 - 1) * 5 + (d1 > d2 ? d1 - 1 : d1)) * 50
                                            + " from Pair Plus Bet(" + d2 + "," + d3 + "," + d1 + ")!");
                            totalLoseMoney -= betList[11].get((d2 - 1) * 5 + (d1 > d2 ? d1 - 1 : d1));
                        }
                    } else if (d3 == d1) {
                        if (betList[11].get((d1 - 1) * 5 + (d2 > d1 ? d2 - 1 : d2)) != 0) {
                            totalEarnMoney += betList[11].get((d1 - 1) * 5 + (d2 > d1 ? d2 - 1 : d2)) * 50;
                            System.out
                                    .println("You earn " + betList[11].get((d1 - 1) * 5 + (d2 > d1 ? d2 - 1 : d2)) * 50
                                            + " from Pair Plus Bet(" + d3 + "," + d1 + "," + d2 + ")!");
                            totalLoseMoney -= betList[11].get((d1 - 1) * 5 + (d2 > d1 ? d2 - 1 : d2));
                        }
                    }
                    break;
                }
            }
        }
        return totalEarnMoney;
    }
}