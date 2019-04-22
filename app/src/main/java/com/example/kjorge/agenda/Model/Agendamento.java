package com.example.kjorge.agenda.Model;

import java.io.Serializable;

public class Agendamento implements Serializable {

    private String nome, telefone, endereco, tipoConsulta, data;
    private int id;

    public Agendamento() {
    }

    public Agendamento(String nome, String telefone, String endereco, String tipoConsulta, String data) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipoConsulta = tipoConsulta;
        this.data = data;

    }

    public Agendamento(int id, String nome, String telefone, String endereco, String tipoConsulta, String data) {

        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipoConsulta = tipoConsulta;
        this.data = data;
    }



//    public Agendamento(void doPost) {
//    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
