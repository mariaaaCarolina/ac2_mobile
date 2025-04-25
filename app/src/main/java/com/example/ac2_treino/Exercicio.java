package com.example.ac2_treino;

public class Exercicio {
    private String nome;
    private int duracaoEmSegundos;

    public Exercicio(String nome, int duracaoEmSegundos) {
        this.nome = nome;
        this.duracaoEmSegundos = duracaoEmSegundos;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoEmSegundos() {
        return duracaoEmSegundos;
    }
}
