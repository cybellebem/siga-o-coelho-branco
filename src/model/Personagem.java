package model;

public abstract class Personagem {

    private String nome;
    private int vida;
    private int vidaMaxima;

    public Personagem(String nome, int vida) {
        this.nome = nome;
        this.vida = vida;
        this.vidaMaxima = vida;
    }

    public String getNome()      { return nome; }
    public int getVida()         { return vida; }
    public int getVidaMaxima()   { return vidaMaxima; }
    public boolean estaVivo()    { return vida > 0; }

    public void setVida(int vida) {
        this.vida = Math.max(0, Math.min(vida, vidaMaxima));
    }

    public void receberDano(int dano) {
        setVida(this.vida - dano);
    }

    public void curar(int quantidade) {
        setVida(this.vida + quantidade);
    }

    public abstract int atacar();

    @Override
    public String toString() {
        return nome + " [Vida: " + vida + "/" + vidaMaxima + "]";
    }
}
