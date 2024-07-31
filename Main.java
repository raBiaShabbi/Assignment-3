import java.util.Random;
import java.util.Scanner;

public class Main {
    static final int overs = 20;
    static final int balls_per_over = 6;
    static final int maxBalls = overs * balls_per_over;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Enter the name of the first cricket team: ");
        String team1 = input.nextLine();
        System.out.print("Enter the name of the second cricket team: ");
        String team2 = input.nextLine();

        String[] teamOne = new String[11];
        System.out.println("Enter names of 11 players for " + team1 + ":");
        for (int i = 0; i < teamOne.length; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            teamOne[i] = input.nextLine();
        }

        String[] teamTwo = new String[11];
        System.out.println("Enter names of 11 players for " + team2 + ":");
        for (int i = 0; i < teamTwo.length; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            teamTwo[i] = input.nextLine();
        }

        boolean toss = random.nextBoolean();
        System.out.println("It's time for the toss.");
        System.out.println("If true, team 1 will bat first and vice versa.");
        String battingFirstTeam = toss ? team1 : team2;
        String bowlingFirstTeam = toss ? team2 : team1;
        System.out.println("Toss result: " + (toss ? "Team 1 will bat first" : "Team 2 will bat first"));

        int[] teamOneStats = simulateInnings(battingFirstTeam, teamOne, random);
        int[] teamTwoStats = simulateInnings(bowlingFirstTeam, teamTwo, random);

        displayScorecard(battingFirstTeam, teamOne, teamOneStats);
        displayScorecard(bowlingFirstTeam, teamTwo, teamTwoStats);

        if (teamOneStats[0] > teamTwoStats[0]) {
            System.out.println("\n" + team1 + " wins!");
        } else if (teamTwoStats[0] > teamOneStats[0]) {
            System.out.println("\n" + team2 + " wins!");
        } else {
            System.out.println("\nThe match is a draw!");
        }

        input.close();
    }

    public static int[] simulateInnings(String teamName, String[] players, Random random) {
        int totalRuns = 0;
        int wicketsFallen = 0;
        int ballsBowled = 0;

        int[] runsScored = new int[players.length];
        int[] ballsFaced = new int[players.length];
        boolean[] isOut = new boolean[players.length];

        System.out.println("\nBatting statistics for " + teamName + ":");
        for (int i = 0; i < players.length; i++) {
            String player = players[i];
            int playerBalls = random.nextInt(7);

            for (int j = 0; j < playerBalls; j++) {
                ballsBowled++;
                int runs = random.nextInt(7);
                boolean out = random.nextBoolean();

                runsScored[i] += runs;
                totalRuns += runs;
                ballsFaced[i]++;

                if (out) {
                    isOut[i] = true;
                    wicketsFallen++;
                    break;
                }
            }
            if (wicketsFallen >= 10) {
                break;
            }
        }

        System.out.println("\nScorecard:");
        for (int i = 0; i < players.length; i++) {
            String player = players[i];
            System.out.println("Player: " + player);
            System.out.println("Score: " + runsScored[i]);
            System.out.println("Balls faced: " + ballsFaced[i]);
            System.out.println("Out: " + (isOut[i] ? "Yes" : "No"));
            double strikeRate = ballsFaced[i] > 0 ? (runsScored[i] / (double) ballsFaced[i]) * 100 : 0;
            System.out.println("Strike Rate: " + String.format("%.2f", strikeRate));
            System.out.println();
        }

        System.out.println("Total score: " + totalRuns + "/" + wicketsFallen + " in " + (ballsBowled / 6) + "." + (ballsBowled % 6) + " overs");
        return new int[]{totalRuns, wicketsFallen, ballsBowled};
    }

    public static void displayScorecard(String teamName, String[] players, int[] stats) {
        int totalRuns = stats[0];
        int wicketsFallen = stats[1];
        int ballsBowled = stats[2];

        System.out.println("\nScorecard for " + teamName + ":");
        System.out.println("Total score: " + totalRuns + "/" + wicketsFallen + " in " + (ballsBowled / 6) + "." + (ballsBowled % 6) + " overs");
    }
}
