package synergy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JFrame {
    private static Game game_game;
    private static Image snowman;  // изображение снеговика
    private static Image carrot;   // изображение морковки

    // Координаты морковки
    private static float drop_left = 480;  // фиксированная X-позиция морковки
    private static float drop_top = 0;     // начальная Y-позиция (морковка стартует сверху)
    private static int score = 0;          // счётчик очков

    public static void main(String[] args) {
        try {
            // Загружаем изображения снеговика и морковки
            snowman = ImageIO.read(Game.class.getResourceAsStream("snowman.png"));
            carrot = ImageIO.read(Game.class.getResourceAsStream("carrot.png"));
        } catch (IOException | NullPointerException e) {
            System.err.println("Не удалось загрузить изображения: " + e.getMessage());
        }

        // Создаём главное окно
        game_game = new Game();
        game_game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_game.setLocation(200, 50);
        game_game.setSize(900, 600);
        game_game.setResizable(false);

        // Создаём игровое поле и добавляем его в окно
        GameField game_field = new GameField();
        game_game.add(game_field);
        game_game.setVisible(true);

        // Таймер для анимации падения морковки
        Timer timer = new Timer(20, e -> {
            drop_top += 2;  // увеличиваем Y-координату (морковка падает вниз)
            if (drop_top > 210) {  // если морковка "достигла" носа снеговика — останавливаем падение
                drop_top = 210;
            }
            game_field.repaint();  // перерисовываем окно
        });
        timer.start();

        // Добавляем обработчик клика мыши для "поимки" морковки
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();  // X-координата клика
                int y = e.getY();  // Y-координата клика

                // Вычисляем границы морковки
                float drop_right = drop_left + 150;   // ширина морковки 150
                float drop_bottom = drop_top + 150;   // высота морковки 150

                // Проверяем, попал ли клик по морковке
                boolean is_hit = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;

                if (is_hit) {
                    // Если попали: сбрасываем морковку вверх и увеличиваем счёт
                    drop_top = -100;  // морковка снова "падает" сверху
                    drop_left = 480;  // остаётся на том же X (как у тебя)
                    score++;          // увеличиваем счёт
                    game_game.setTitle("Score: " + score);  // обновляем заголовок окна
                }
            }
        });
    }

    // Метод для рисования изображений на панели
    public static void onRepaint(Graphics g) {
        if (snowman != null) {
            // Рисуем снеговика (на постоянной позиции)
            g.drawImage(snowman, 250, 30, 500, 500, null);
        }
        if (carrot != null) {
            // Рисуем морковку (меняется только Y-позиция при падении)
            g.drawImage(carrot, (int) drop_left, (int) drop_top, 150, 150, null);
        }
    }

    // Класс игрового поля (панель для рисования)
    public static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);  // вызываем метод для рисования объектов
        }
    }
}

