package com.example.ac2_treino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView lvExercicios;
    Button btnCadastrar, btnIniciarTreino;
    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvExercicios = findViewById(R.id.lvExercicios);
        btnIniciarTreino = findViewById(R.id.btnIniciarTreino);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvExercicios.setAdapter(adaptador);

        carregarExercicios();

        btnIniciarTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ExercicioRepository.getInstancia().getListaExercicios().isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, TreinoActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void carregarExercicios() {
        adaptador.clear();
        for (Exercicio exercicio : ExercicioRepository.getInstancia().getListaExercicios()) {
            adaptador.add(exercicio.getNome() + " - " + exercicio.getDuracaoEmSegundos() + "s");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarExercicios();
    }
}
