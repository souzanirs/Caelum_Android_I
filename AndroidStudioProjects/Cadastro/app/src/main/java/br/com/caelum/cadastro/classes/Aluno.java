package br.com.caelum.cadastro.classes;

import java.io.Serializable;

/**
 * Created by android6275 on 25/10/16.
 */

public class Aluno implements Serializable{
    private long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private Double nota;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        if(this.id < 10) {
            return "0"+this.id+": "+this.getNome();
        } else {
            return this.id+": "+this.getNome();
        }
    }
}
