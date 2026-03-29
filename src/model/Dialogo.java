package model;

public class Dialogo {

    private String[] perguntas;
    private String[][] opcoes;
    private int[] respostasCorretas;

    public Dialogo(String[] perguntas, String[][] opcoes, int[] respostasCorretas) {
        this.perguntas = perguntas;
        this.opcoes = opcoes;
        this.respostasCorretas = respostasCorretas;
    }

    public int getNumRodadas()              { return perguntas.length; }
    public String getPergunta(int rodada)   { return perguntas[rodada]; }
    public String[] getOpcoes(int rodada)   { return opcoes[rodada]; }

    public boolean isCorreto(int rodada, int escolha) {
        return escolha == respostasCorretas[rodada];
    }
}
