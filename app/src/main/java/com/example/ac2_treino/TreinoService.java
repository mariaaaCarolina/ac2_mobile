package com.example.ac2_treino;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.List;

public class TreinoService extends Service {

    private List<Exercicio> listaExercicios;
    private int indiceAtual = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        criarCanalNotificacao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Aqui usamos a instância já preenchida no MainActivity
        listaExercicios = ExercicioRepository.getInstancia().getListaExercicios();

        if (listaExercicios == null || listaExercicios.isEmpty()) {
            stopSelf();
            return START_NOT_STICKY;
        }

        iniciarProximoExercicio();

        return START_NOT_STICKY;
    }
    private void iniciarProximoExercicio() {
        if (indiceAtual >= listaExercicios.size()) {
            mostrarNotificacao("Treino concluído!", "Parabéns, você terminou seu treino!");
            stopSelf();
            return;
        }

        Exercicio exercicio = listaExercicios.get(indiceAtual);
        mostrarNotificacao("Exercício: " + exercicio.getNome(),
                "Tempo: " + exercicio.getDuracaoEmSegundos() + " segundos");

        new CountDownTimer(exercicio.getDuracaoEmSegundos() * 1000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Pode ser usado para atualizar o contador visual (se quiser)
            }

            @Override
            public void onFinish() {
                indiceAtual++;
                iniciarProximoExercicio();
            }
        }.start();
    }

    private void mostrarNotificacao(String titulo, String mensagem) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "canal_treino")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(titulo)
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.notify(indiceAtual + 1, builder.build());
    }

    private void criarCanalNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    "canal_treino",
                    "Notificações de Treino",
                    NotificationManager.IMPORTANCE_HIGH
            );
            canal.setDescription("Canal para notificações do aplicativo de treino");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
