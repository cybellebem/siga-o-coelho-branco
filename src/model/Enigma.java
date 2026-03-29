package model;

public class Enigma {

    private String pergunta;
    private String resposta;
    private String dica;

    public Enigma(String pergunta, String resposta, String dica) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.dica = dica;
    }

    public String getPergunta() { return pergunta; }
    public String getDica()     { return dica; }

    public boolean verificarResposta(String tentativa) {
        return resposta.equalsIgnoreCase(tentativa.trim());
    }
}
