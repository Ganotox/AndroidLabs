package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;


import java.util.Random;

public class MainActivity extends ComponentActivity {

    // Масив з назвами кольорів
    private String[] colors = {"Червоний", "Зелений", "Синій", "Жовтий", "Білий", "Чорний"};

    // Масив з кодами кольорів
    private int[] colorCodes = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE, Color.BLACK};

    // Генератор випадкових чисел
    private Random random = new Random();

    // Компоненти інтерфейсу
    private TextView questionTextView;
    private TextView colorTextView;
    private TextView timerTextView;
    private TextView scoreTextView;
    private Button yesButton;
    private Button noButton;

    // Змінні для логіки гри
    private int colorIndex; // Індекс кольору тексту
    private int wordIndex; // Індекс слова з назвою кольору
    private int score; // Кількість правильних відповідей
    private boolean isPlaying; // Чи триває гра
    private CountDownTimer timer; // Таймер для обмеження часу гри

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Знаходимо компоненти інтерфейсу за їх id
        questionTextView = findViewById(R.id.questionTextView);
        colorTextView = findViewById(R.id.colorTextView);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);

        // Створюємо таймер на 3 хвилини
        timer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Оновлюємо текст таймера кожну секунду
                timerTextView.setText("Час: " + millisUntilFinished / 1000 + " с");
            }

            @Override
            public void onFinish() {
                // Завершуємо гру, коли час вийшов
                endGame();
            }
        };

        // Додаємо слухачі для кнопок
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перевіряємо відповідь, якщо гра триває
                if (isPlaying) {
                    checkAnswer(true);
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перевіряємо відповідь, якщо гра триває
                if (isPlaying) {
                    checkAnswer(false);
                }
            }
        });

        // Починаємо гру
        startGame();
    }

    // Метод для початку гри
    private void startGame() {
        // Скидаємо кількість правильних відповідей
        score = 0;
        // Оновлюємо текст з рахунком
        scoreTextView.setText("Рахунок: " + score);
        // Запускаємо таймер
        timer.start();
        // Встановлюємо статус гри
        isPlaying = true;
        // Генеруємо перше питання
        generateQuestion();
    }

    // Метод для завершення гри
    private void endGame() {
        // Зупиняємо таймер
        timer.cancel();
        // Встановлюємо статус гри
        isPlaying = false;
        // Показуємо повідомлення з результатом
        Toast.makeText(this, "Гра закінчена. Ви набрали " + score + " балів.", Toast.LENGTH_LONG).show();
    }

    // Метод для генерації питання
    private void generateQuestion() {
        // Вибираємо випадковий індекс кольору тексту
        colorIndex = random.nextInt(colors.length);
        // Вибираємо випадковий індекс слова з назвою кольору
        wordIndex = random.nextInt(colors.length);
        // Встановлюємо текст і колір для компонента colorTextView
        colorTextView.setText(colors[wordIndex]);
        colorTextView.setTextColor(colorCodes[colorIndex]);
    }

    // Метод для перевірки відповіді
    private void checkAnswer(boolean answer) {
        // Перевіряємо, чи співпадають індекси кольору і слова
        boolean isMatch = colorIndex == wordIndex;
        // Якщо відповідь вірна, збільшуємо рахунок
        if (answer == isMatch) {
            score++;
            // Показуємо повідомлення про правильну відповідь
            Toast.makeText(this, "Вірно!", Toast.LENGTH_SHORT).show();
        } else {
            // Показуємо повідомлення про неправильну відповідь
            Toast.makeText(this, "Невірно!", Toast.LENGTH_SHORT).show();
        }
        // Оновлюємо текст з рахунком
        scoreTextView.setText("Рахунок: " + score);
        // Генеруємо наступне питання
        generateQuestion();
    }
}
