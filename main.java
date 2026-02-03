import java.util.Scanner;
public class main
{
    static double bal = 0;
	public static void main(String[] args) {
		
		boolean RUNNING = true;
		System.out.println("*****WELCOME*****");
		//make sure person has to deposit money before playing.
		displayBal(bal);
		while(RUNNING) {
			int option = chooseOption();
			if (option==1) {
				deposit();
			} else if (option==2) {
				playGame();
                displayBal(bal);
			} else if (option==3) {
				RUNNING=false;
                System.out.println("Thanks for playing!\nYou are leaving with $"+bal);
			}
		}
	}
	//Displays the bank balance as inputed
	public static void displayBal(double b) {
		System.out.println("You have $"+b+" in your bank");
	}
	public static void deposit() {
		Scanner kb = new Scanner(System.in);
		System.out.println("How much would you like to deposit?");
		double amount = kb.nextDouble();
		bal += amount;
		System.out.println();
		displayBal(bal);
	}

	public static void depositMan(double amount) {
		bal += amount;
	}

	public static int chooseOption() {
		Scanner kb = new Scanner(System.in);
		System.out.println("Please choose one of the following options:");
		System.out.println("(1) Deposit money into the bank");
		System.out.println("(2) Play the game!");
		System.out.println("(3) Quit the game.");
		return kb.nextInt();
	}

	public static void playGame() {
		Scanner kb = new Scanner(System.in);
		int cAns=(int)(Math.random()*2); // 0 == Tails, 1 == Heads
		int dAns=(int)(Math.random()*6)+1; 
		int sAns=(int)(Math.random()*4); // 0 == red, 1 == green, 2 == blue, 3 == yellow 
        int amountCorrect=0;
		System.out.println("How much would you like to bet?");
		double bet = kb.nextDouble();
		while (bet>bal) {
			System.out.println("Please enter an amount lower than "+bal);
			bet = kb.nextDouble();
		}
		System.out.println("What is your guess on the dice roll?");
		int diceGuess = kb.nextInt();
        kb.nextLine(); // reads the escape character \n
		System.out.println("What is your guess on the coin flip, Heads (H) or Tails (T)?");
		String coinGuess = kb.nextLine().toLowerCase();
		System.out.println("What is your guess on the spinner, red (r), green (g), blue (b), or yellow (y)?");
		String spinnerGuess = kb.nextLine().toLowerCase();
		System.out.println("\n");
		System.out.println("You rolled a "+ dAns);
		if (cAns==0){
		    System.out.println("You flipped tails");
		} else {
		    System.out.println("You flipped heads");
		    
		} if(sAns==0){
		    System.out.println("You spun red");
		} else if(sAns==1){
		    System.out.println("You spun green");
        } else if(sAns==2){
		    System.out.println("You spun blue");
        } else if(sAns==3){
		    System.out.println("You spun yellow");
        }
        //Check how many correct;
        boolean diceCorrect = (diceGuess == dAns);
        boolean coinCorrect = (cAns == 0 && coinGuess.equals("t")) || 
                      (cAns == 1 && coinGuess.equals("h"));
        boolean spinnerCorrect = (sAns == 0 && spinnerGuess.equals("r")) ||
                         (sAns == 1 && spinnerGuess.equals("g")) ||
                         (sAns == 2 && spinnerGuess.equals("b")) ||
                         (sAns == 3 && spinnerGuess.equals("y"));

        
        if (diceCorrect) {
            amountCorrect++;
        }
        if (coinCorrect) {
            amountCorrect++;
        }
        if (spinnerCorrect) {
            amountCorrect++;
        }

        if (amountCorrect==0) {
            depositMan(-bet);
        } else if (amountCorrect==3){
            depositMan(bet*3);
        } else if (amountCorrect==2){
            depositMan(bet*2);
        } else if (amountCorrect==1) {
            if (diceCorrect) {
                depositMan(bet);
            }
            if (coinCorrect) {
                depositMan(bet/4);
            }
            if (spinnerCorrect) {
                depositMan(bet/2);
            }
        }

        displayCorrect(coinCorrect, diceCorrect, spinnerCorrect, amountCorrect, bet);

	}

    public static void displayCorrect(boolean coin, boolean dice, boolean spinner, int c, double bet){
        System.out.println("\n");
        if (!coin&&!dice&&!spinner) {
            System.out.println("You did not get any correct, removing $"+bet+" from your account");
        } else if (c==3){
            System.out.println("You got all of them correct, adding $" +(bet*3)+ " to your account");
        } else if (c==2){
            if (coin&&dice) {
                System.out.println("You got the coin and dice correct! Adding $"+(bet*2)+ " to your bank!");
            } else if (coin&&spinner) {
                System.out.println("You got the coin and spinner correct! Adding $"+(bet*2)+ " to your bank!");
            } else if (dice&&spinner) {
                System.out.println("You got the dice and spinner correct! Adding $"+(bet*2)+ " to your bank!");
            }
        } else if (c==1) {
            if (coin) {
                System.out.println("You only got the coin correct.");
            } else if (spinner) {
                System.out.println("You only got the spinner correct.");
            } else if (dice) {
                System.out.println("you only got the dice correct.");
            }
        }
        System.out.println();
    }
}


