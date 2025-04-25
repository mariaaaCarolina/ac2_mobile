package com.example.ac2_treino;

import java.util.ArrayList;
import java.util.List;

public class ExercicioRepository {
    private static ExercicioRepository instancia;
    private List<Exercicio> listaExercicios = new ArrayList<>();

    ExercicioRepository() {}

    public static ExercicioRepository getInstancia() {
        if (instancia == null) {
            instancia = new ExercicioRepository();
        }
        return instancia;
    }

    public void setListaExercicios(List<Exercicio> lista) {
        this.listaExercicios = lista;
    }

    public List<Exercicio> getListaExercicios() {
        return listaExercicios;
    }
}
