package model;

import game.TipoDesafio;

public class Inimigo extends Personagem {

    private int poderAtaque;
    private String descricao;
    private TipoDesafio tipoDesafio;

    public Inimigo(String nome, int vida, int poderAtaque, String descricao, TipoDesafio tipoDesafio) {
        super(nome, Math.max(vida, 1));
        this.poderAtaque = poderAtaque;
        this.descricao = descricao;
        this.tipoDesafio = tipoDesafio;
    }

    @Override
    public int atacar() {
        return poderAtaque + (int)(Math.random() * 8);
    }

    public String getDescricao()       { return descricao; }
    public TipoDesafio getTipoDesafio(){ return tipoDesafio; }
    public int getPoderAtaque()        { return poderAtaque; }
}
