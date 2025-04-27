package fr.uge.yams;
public class YamsBot {

    public static void playBotTurn(String botName, ScoreSheet scoreSheet) {
        System.out.println("\n--- " + botName + "'s Turn (BOT) ---");
        var board = new Board();
        System.out.println(board);

        for (var rerollCounter = 0; rerollCounter < 2; rerollCounter++) {
            int rerollChoice = chooseReroll(board);
            if (rerollChoice > 0) {
                System.out.println(botName + " decides to reroll die " + rerollChoice);
                board.reroll(rerollChoice);
                System.out.println(board);
            } else {
                break;
            }
        }

        var combination = chooseBestCombination(board, scoreSheet);
        scoreSheet.updateScore(combination, board);
        System.out.println(botName + " chooses: " + combination.getClass().getSimpleName());
        System.out.println("\n" + botName + "'s Current Score Sheet:");
        System.out.println(scoreSheet);
    }

    private static int chooseReroll(Board board) {
        // Exemple super simple: reroll the first die that's 1 or 2
        for (int i = 0; i < 5; i++) {
            if (board.getFiveDice().get(i).value() <= 2) {
                return i + 1;
            }
        }
        return 0; // otherwise don't reroll
    }

    private static Combination chooseBestCombination(Board board, ScoreSheet scoreSheet) {
        Combination[] priority = {
            new YamsCombination(),
            new FullHouse(),
            new SmallStraight(),
            new LargeStraight(),
            new ThreeOfAKind(),
            new DoublePair(),
            new Pair(),
            new Chance()
        };
        for (var comb : priority) {
            if (!scoreSheet.alreadyUsed(comb) && comb.isValid(board)) {
                return comb;
            }
        }
        // Si tout est pris fuck it we ball
        return new Chance();
    }
}