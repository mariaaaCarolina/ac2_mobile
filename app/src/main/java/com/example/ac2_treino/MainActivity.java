package com.example.ac2_treino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etNomeExercicio, etDuracaoEmSegundos;
    ListView lvExercicios;
    Button btnSalvarExercicio, btnCadastrar, btnIniciarTreino;

    ArrayList<Exercicio> listaExercicio = new ArrayList<>();
    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNomeExercicio = findViewById(R.id.etNomeExercicio);
        etDuracaoEmSegundos = findViewById(R.id.etDuracaoEmSegundos);
        lvExercicios = findViewById(R.id.lvExercicios);
        btnIniciarTreino = findViewById(R.id.btnIniciarTreino);
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvExercicios.setAdapter(adaptador);

        btnSalvarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeExercicio.getText().toString();
                if (nome.isEmpty() || etDuracaoEmSegundos.getText().toString().isEmpty()) return;

                int duracao = Integer.parseInt(etDuracaoEmSegundos.getText().toString());

                Exercicio novo = new Exercicio(nome, duracao);
                listaExercicio.add(novo);
                adaptador.add(nome + " - " + duracao + "s");

                etNomeExercicio.setText("");
                etDuracaoEmSegundos.setText("");
            }
        });

        btnIniciarTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExercicioRepository.getInstancia().setListaExercicios(listaExercicio);

                Intent intent = new Intent(MainActivity.this, TreinoService.class);
                ContextCompat.startForegroundService(MainActivity.this, intent);
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
}
