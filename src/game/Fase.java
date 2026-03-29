package game;

import model.Alice;
import model.Dialogo;
import model.Enigma;
import model.Inimigo;
import model.Item;

import java.util.Scanner;

/**
 * Representa um capitulo/fase do jogo.
 * Cada fase possui uma narrativa, um inimigo/obstaculo e um tipo de desafio.
 * A lista de fases e mantida em Jogo.java (uso de List<Fase>).
 */
public class Fase {

    // Atributos privados (encapsulamento)
    private String titulo;
    private String narrativa;
    private Inimigo inimigo;
    private Enigma enigma;
    private Dialogo dialogo;
    private String[] escolhas;
    private int escolhaCorreta;
    private Item recompensa;

    // =========================================================
    // Construtores para cada tipo de desafio
    // =========================================================

    /** Para fases de COMBATE */
    public Fase(String titulo, String narrativa, Inimigo inimigo, Item recompensa) {
        this.titulo = titulo;
        this.narrativa = narrativa;
        this.inimigo = inimigo;
        this.recompensa = recompensa;
    }

    /** Para fases de ENIGMA */
    public Fase(String titulo, String narrativa, Inimigo inimigo, Enigma enigma, Item recompensa) {
        this(titulo, narrativa, inimigo, recompensa);
        this.enigma = enigma;
    }

    /** Para fases de DIALOGO */
    public Fase(String titulo, String narrativa, Inimigo inimigo, Dialogo dialogo, Item recompensa) {
        this(titulo, narrativa, inimigo, recompensa);
        this.dialogo = dialogo;
    }

    /** Para fases de ESCOLHA */
    public Fase(String titulo, String narrativa, Inimigo inimigo,
                String[] escolhas, int escolhaCorreta, Item recompensa) {
        this(titulo, narrativa, inimigo, recompensa);
        this.escolhas = escolhas;
        this.escolhaCorreta = escolhaCorreta;
    }

    // Getter
    public String getTitulo() { return titulo; }

    // =========================================================
    // Execucao da fase
    // =========================================================

    /**
     * Executa a fase completa: exibe narrativa, roda o desafio e concede recompensa.
     * @return true se Alice completou o desafio com sucesso
     */
    public boolean executar(Alice alice, Scanner scanner) {
        separador();
        System.out.println("  " + titulo);
        separador();
        System.out.println(narrativa);
        System.out.println();
        System.out.println("Voce encontra: " + inimigo.getNome());
        System.out.println(inimigo.getDescricao());
        System.out.println();

        boolean sucesso;

        switch (inimigo.getTipoDesafio()) {
            case COMBATE: sucesso = executarCombate(alice, scanner); break;
            case DIALOGO: sucesso = executarDialogo(alice, scanner); break;
            case ENIGMA:  sucesso = executarEnigma(alice, scanner);  break;
            case ESCOLHA: sucesso = executarEscolha(alice, scanner); break;
            default:      sucesso = false;
        }

        if (sucesso) {
            alice.adicionarPontos(100);
            System.out.println("\n[+100 pontos! Total: " + alice.getPontuacao() + "]");
            if (recompensa != null) {
                alice.adicionarItem(recompensa);
            }
        }

        return sucesso;
    }

    // =========================================================
    // Metodos privados de cada tipo de desafio
    // =========================================================

    private boolean executarCombate(Alice alice, Scanner scanner) {
        System.out.println(">>> COMBATE <<<");
        System.out.println("Derrote " + inimigo.getNome() + " para continuar!\n");

        while (alice.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n" + alice);
            System.out.println(inimigo);
            System.out.println("\nO que fazer?");
            System.out.println("1. Atacar");
            System.out.println("2. Usar Item");
            System.out.print("> ");

            String entrada = scanner.nextLine().trim();

            if (entrada.equals("1")) {
                int dano = alice.atacar();
                inimigo.receberDano(dano);
                System.out.println("Alice ataca " + inimigo.getNome() + " causando " + dano + " de dano!");

            } else if (entrada.equals("2")) {
                if (!alice.temItens()) {
                    System.out.println("Seu inventario esta vazio!");
                    continue;
                }
                System.out.println("Inventario:");
                for (int i = 0; i < alice.quantidadeItens(); i++) {
                    System.out.println("  " + (i + 1) + ". " + alice.getItem(i));
                }
                System.out.print("Escolha: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    if (!alice.usarItem(idx)) System.out.println("Item invalido.");
                } catch (NumberFormatException e) {
                    System.out.println("Escolha invalida.");
                }
                continue;

            } else {
                System.out.println("Opcao invalida.");
                continue;
            }

            if (inimigo.estaVivo()) {
                int dano = inimigo.atacar();
                alice.receberDano(dano);
                alice.perderSanidade(5);
                System.out.println(inimigo.getNome() + " ataca Alice causando " + dano + " de dano!");
            }
        }

        if (alice.estaVivo()) {
            System.out.println("\nVitoria! Voce derrotou " + inimigo.getNome() + "!");
            return true;
        } else {
            System.out.println("\nAlice caiu...");
            return false;
        }
    }

    private boolean executarDialogo(Alice alice, Scanner scanner) {
        System.out.println(">>> DIALOGO <<<");
        System.out.println("Voce deve convencer " + inimigo.getNome() + " com suas palavras.\n");

        int acertos = 0;
        int totalRodadas = dialogo.getNumRodadas();

        for (int rodada = 0; rodada < totalRodadas; rodada++) {
            System.out.println("[Rodada " + (rodada + 1) + "/" + totalRodadas + "]");
            System.out.println(inimigo.getNome() + ": \"" + dialogo.getPergunta(rodada) + "\"");
            System.out.println("\nO que voce responde?");

            String[] opcoes = dialogo.getOpcoes(rodada);
            for (int i = 0; i < opcoes.length; i++) {
                System.out.println("  " + (i + 1) + ". " + opcoes[i]);
            }
            System.out.print("> ");

            try {
                int escolha = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (dialogo.isCorreto(rodada, escolha)) {
                    System.out.println(inimigo.getNome() + " parece satisfeito.");
                    acertos++;
                } else {
                    System.out.println(inimigo.getNome() + " franze a testa. Sua sanidade oscila...");
                    alice.perderSanidade(10);
                }
            } catch (NumberFormatException e) {
                System.out.println("Voce fica em silencio. " + inimigo.getNome() + " nao aprecia isso.");
                alice.perderSanidade(5);
            }
            System.out.println();
        }

        int necessario = (totalRodadas / 2) + 1;
        if (acertos >= necessario) {
            System.out.println(inimigo.getNome() + " esta convencido e deixa voce passar!");
            return true;
        } else {
            System.out.println(inimigo.getNome() + " recusa-se a deixar voce passar...");
            alice.receberDano(20);
            return false;
        }
    }

    private boolean executarEnigma(Alice alice, Scanner scanner) {
        System.out.println(">>> ENIGMA <<<");
        System.out.println(inimigo.getNome() + " propos um enigma:\n");
        System.out.println("\"" + enigma.getPergunta() + "\"");
        System.out.println("\n[Dica: " + enigma.getDica() + "]");
        System.out.println("\nVoce tem 3 tentativas.");

        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            System.out.print("\nTentativa " + tentativa + ": ");
            String resposta = scanner.nextLine().trim();

            if (enigma.verificarResposta(resposta)) {
                System.out.println("Correto! " + inimigo.getNome() + " fica impressionado!");
                return true;
            } else {
                System.out.println("Errado! Sua sanidade escorrega...");
                alice.perderSanidade(15);
                if (tentativa < 3) System.out.println("Tente novamente...");
            }
        }

        System.out.println("Voce nao conseguiu resolver o enigma. O mundo fica mais estranho...");
        alice.receberDano(30);
        return false;
    }

    private boolean executarEscolha(Alice alice, Scanner scanner) {
        System.out.println(">>> ESCOLHA <<<");
        System.out.println("Voce deve tomar uma decisao:\n");

        for (int i = 0; i < escolhas.length; i++) {
            System.out.println("  " + (i + 1) + ". " + escolhas[i]);
        }
        System.out.print("> ");

        try {
            int escolha = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (escolha == escolhaCorreta) {
                System.out.println("Uma escolha sabia! O caminho se abre a sua frente.");
                return true;
            } else {
                System.out.println("Uma escolha equivocada... as consequencias chegam.");
                alice.receberDano(25);
                alice.perderSanidade(20);
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Voce hesita. O Pais das Maravilhas pune a indecisao.");
            alice.receberDano(15);
            return false;
        }
    }

    private void separador() {
        System.out.println("================================================");
    }
}
