package model;

public class Item {

    private String nome;
    private String descricao;
    private int curaVida;
    private int curaSanidade;

    public Item(String nome, String descricao, int curaVida, int curaSanidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.curaVida = curaVida;
        this.curaSanidade = curaSanidade;
    }

    public String getNome()     { return nome; }
    public String getDescricao(){ return descricao; }

    public void usar(Alice alice) {
        if (curaVida > 0) {
            alice.curar(curaVida);
            System.out.println("  Voce usou " + nome + " e recuperou " + curaVida + " de vida!");
        }
        if (curaSanidade > 0) {
            alice.ganharSanidade(curaSanidade);
            System.out.println("  Sua sanidade recuperou " + curaSanidade + " pontos!");
        }
    }

    @Override
    public String toString() {
        return nome + " (" + descricao + ")";
    }
}
