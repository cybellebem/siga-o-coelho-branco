package model;

import java.util.ArrayList;
import java.util.List;

public class Alice extends Personagem {

    private int sanidade;
    private int sanidadeMaxima;
    private int pontuacao;
    private List<Item> inventario;
    private int poderAtaque;

    public Alice(String nome) {
        super(nome, 100);
        this.sanidade = 100;
        this.sanidadeMaxima = 100;
        this.pontuacao = 0;
        this.inventario = new ArrayList<>();
        this.poderAtaque = 15;
    }

    @Override
    public int atacar() {
        return poderAtaque + (int)(Math.random() * 10);
    }

    public int getSanidade()        { return sanidade; }
    public int getSanidadeMaxima()  { return sanidadeMaxima; }
    public int getPontuacao()       { return pontuacao; }
    public boolean estaLouca()      { return sanidade <= 0; }
    public boolean temItens()       { return !inventario.isEmpty(); }
    public int quantidadeItens()    { return inventario.size(); }

    public Item getItem(int indice) {
        if (indice < 0 || indice >= inventario.size()) return null;
        return inventario.get(indice);
    }

    public void adicionarPontos(int pontos) {
        this.pontuacao += pontos;
    }

    public void perderSanidade(int quantidade) {
        this.sanidade = Math.max(0, this.sanidade - quantidade);
    }

    public void ganharSanidade(int quantidade) {
        this.sanidade = Math.min(this.sanidadeMaxima, this.sanidade + quantidade);
    }

    public void adicionarItem(Item item) {
        inventario.add(item);
        System.out.println("  [Item obtido: " + item.getNome() + "]");
    }

    public boolean usarItem(int indice) {
        if (indice < 0 || indice >= inventario.size()) return false;
        Item item = inventario.get(indice);
        item.usar(this);
        inventario.remove(indice);
        return true;
    }

    @Override
    public String toString() {
        return getNome()
             + " [Vida: " + getVida() + "/" + getVidaMaxima()
             + " | Sanidade: " + sanidade + "/" + sanidadeMaxima
             + " | Pontos: " + pontuacao + "]";
    }
}
