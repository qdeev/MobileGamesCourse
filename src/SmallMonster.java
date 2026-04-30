import java.util.Random;
import java.util.Scanner;

public class SmallMonster extends Monster {

    private String image = "\uD83D\uDC7B";
    private static final int MAX_ATTEMPTS = 3;
    private static final int NUMBER_RANGE = 10;

    public SmallMonster(int sizeBoard) {
        super(sizeBoard);
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    /**
     Игрок должен угадать число от 1 до 10 за 3 попытки.
     После каждой неправильной попытки даётся подсказка: больше/меньше.
     */
    @Override
    public boolean taskMonster(int difficultGame) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        // Сложность влияет на диапазон чисел
        int range = NUMBER_RANGE * difficultGame;
        int secretNumber = random.nextInt(range) + 1;
        int attemptsLeft = MAX_ATTEMPTS;

        System.out.println("Маленький монстр бросает вызов!");
        System.out.println("Угадай число от 1 до " + range + " за " + MAX_ATTEMPTS + " попытки.");

        while (attemptsLeft > 0) {
            System.out.print("Твоя попытка (осталось " + attemptsLeft + "): ");

            if (!sc.hasNextInt()) {
                sc.next();
                System.out.println("Введите целое число!");
                continue;
            }

            int guess = sc.nextInt();

            if (guess == secretNumber) {
                System.out.println("Верно! Ты победил маленького монстра!");
                return true;
            } else if (guess < secretNumber) {
                System.out.println("Загаданное число БОЛЬШЕ.");
            } else {
                System.out.println("Загаданное число МЕНЬШЕ.");
            }

            attemptsLeft--;
        }

        System.out.println("Ты проиграл эту битву! Правильный ответ: " + secretNumber);
        return false;
    }
}