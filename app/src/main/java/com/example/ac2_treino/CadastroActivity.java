package com.example.ac2_treino;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    EditText etNomeExercicio, etDuracaoEmSegundos;
    Button btnSalvarExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro); // usa o layout de cadastro

        etNomeExercicio = findViewById(R.id.etNomeExercicio);
        etDuracaoEmSegundos = findViewById(R.id.etDuracaoEmSegundos);
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio);

        btnSalvarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeExercicio.getText().toString();
                String duracaoStr = etDuracaoEmSegundos.getText().toString();

                if (nome.isEmpty() || duracaoStr.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int duracao = Integer.parseInt(duracaoStr);
                Exercicio novo = new Exercicio(nome, duracao);

                // Salva no repositório
                ExercicioRepository.getInstancia().getListaExercicios().add(novo);
                Toast.makeText(CadastroActivity.this, "Exercício cadastrado!", Toast.LENGTH_SHORT).show();
                finish(); // Fecha a tela e volta para a principal
            }
        });
    }
}
