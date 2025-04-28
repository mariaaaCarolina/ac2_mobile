package com.example.ac2_treino;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TreinoActivity extends AppCompatActivity {

    TextView tvNomeExercicioAtual, tvContador;
    List<Exercicio> listaExercicios;
    int indiceAtual = 0;
    CountDownTimer timerAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);

        tvNomeExercicioAtual = findViewById(R.id.tvNomeExercicioAtual);
        tvContador = findViewById(R.id.tvContador);

        listaExercicios = ExercicioRepository.getInstancia().getListaExercicios();
        if (listaExercicios == null || listaExercicios.isEmpty()) {
            finish();
            return;
        }

        iniciarProximoExercicio();
    }

    private void iniciarProximoExercicio() {
        if (indiceAtual >= listaExercicios.size()) {
            enviarNotificacaoTreinoConcluido();
            finish();
            return;
        }

        Exercicio exercicio = listaExercicios.get(indiceAtual);
        tvNomeExercicioAtual.setText(exercicio.getNome());

        enviarNotificacaoExercicioAtual(exercicio.getNome());

        timerAtual = new CountDownTimer(exercicio.getDuracaoEmSegundos() * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int segundosRestantes = (int) (millisUntilFinished / 1000);
                tvContador.setText(formatarTempo(segundosRestantes));
            }

            @Override
            public void onFinish() {
                indiceAtual++;
                iniciarProximoExercicio();
            }
        }.start();
    }

    private String formatarTempo(int totalSegundos) {
        int minutos = totalSegundos / 60;
        int segundos = totalSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    private void enviarNotificacaoExercicioAtual(String nomeExercicio) {
        Intent serviceIntent = new Intent(this, TreinoService.class);
        serviceIntent.putExtra("nome_exercicio", nomeExercicio);
        startService(serviceIntent);
    }

    private void enviarNotificacaoTreinoConcluido() {
        Intent serviceIntent = new Intent(this, TreinoService.class);
        serviceIntent.putExtra("treino_concluido", true);
        startService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        if (timerAtual != null) {
            timerAtual.cancel();
            timerAtual = null;
        }
        super.onDestroy();
    }
}
