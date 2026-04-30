import java.util.Scanner;

public class BigMonster extends Monster {

    private String image = "\uD83D\uDC79";
    private static final int MAX_ATTEMPTS = 2;

    public BigMonster(int sizeBoard) {
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
     Игрок решает выражение вида: a × b ± c
     Сложность влияет на диапазон чисел и количество попыток.
     */
    @Override
    public boolean taskMonster(int difficultGame) {
        System.out.println("Большой монстр требует решить задачу!");

        if (difficultGame == 1) {
            // Лёгкий режим: простое сложение из базового класса
            return super.taskMonster(0);
        }

        // Генерация выражения с учётом сложности
        int range = 10 * difficultGame;
        int a = r.nextInt(range) + 1;
        int b = r.nextInt(range) + 1;
        int c = r.nextInt(range * 2);
        boolean isAddition = r.nextBoolean();

        int correctAnswer = isAddition ? (a * b + c) : (a * b - c);
        String operation = isAddition ? "+" : "-";

        System.out.println("Реши пример: " + a + " × " + b + " " + operation + " " + c + " = ?");
        System.out.println("У тебя есть " + MAX_ATTEMPTS + " попытки(ок).");

        Scanner sc = new Scanner(System.in);
        int attemptsLeft = MAX_ATTEMPTS;

        while (attemptsLeft > 0) {
            System.out.print("Твой ответ: ");

            if (!sc.hasNextInt()) {
                sc.next();
                System.out.println("⚠️ Введите целое число!");
                continue;
            }

            int playerAnswer = sc.nextInt();

            if (playerAnswer == correctAnswer) {
                System.out.println("Верно! Ты победил большого монстра!");
                return true;
            } else {
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    System.out.println("Неверно. Осталось попыток: " + attemptsLeft);
                }
            }
        }

        System.out.println("Ты проиграл эту битву! Правильный ответ: " + correctAnswer);
        return false;
    }
}