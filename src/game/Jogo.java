package game;

import model.Alice;
import model.Dialogo;
import model.Enigma;
import model.Inimigo;
import model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogo {

    private Alice alice;
    private List<Fase> fases;
    private Scanner scanner;

    public Jogo() {
        this.scanner = new Scanner(System.in);
        this.fases = new ArrayList<>();
    }

    public void iniciar() {
        mostrarIntro();
        configurarJogador();
        configurarFases();
        executarJogo();
    }

    private void mostrarIntro() {
        System.out.println("================================================");
        System.out.println("    ALICE NO PAIS DAS MARAVILHAS");
        System.out.println("        The Looking Glass Game");
        System.out.println("================================================");
        System.out.println();
        System.out.println("Um coelho branco passa correndo por Alice...");
        System.out.println("\"Oh meu! Oh meu! Vou chegar tarde!\"");
        System.out.println();
        System.out.println("Sem pensar, Alice o segue pelo buraco da toca.");
        System.out.println("O mundo que ela conhecia desapareceu.");
        System.out.println("O Pais das Maravilhas a espera...");
        System.out.println();
    }

    private void configurarJogador() {
        System.out.print("Digite seu nome (ou pressione Enter para 'Alice'): ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) nome = "Alice";
        alice = new Alice(nome);
        System.out.println("\nBem-vinda, " + alice.getNome() + "! Sua aventura comeca...\n");
        aguardarEnter();
    }

    private void configurarFases() {

        // Fase 1: Salao das Portas
        fases.add(new Fase(
            "Capitulo 1: O Salao das Portas",
            "Voce pousa num longo corredor baixo cheio de portas de todos os tamanhos.\n"
            + "Uma pequena chave dourada esta sobre uma mesa de vidro.\n"
            + "Tres portas se abrem a sua frente. Apenas uma tem o tamanho certo.",
            new Inimigo("As Portas Trancadas", 1, 0,
                "Cada porta leva a algum lugar diferente. Escolha com sabedoria.",
                TipoDesafio.ESCOLHA),
            new String[]{
                "A porta minuscula atras da cortina (45 cm de altura)",
                "A grande porta de mogno (parece importante)",
                "A porta sem machaneta"
            },
            0,
            new Item("Frasco 'BEBA-ME'", "Uma misteriosa pocao encolhedora", 0, 20)
        ));

        // Fase 2: Jardim da Lagarta
        fases.add(new Fase(
            "Capitulo 2: O Jardim da Lagarta",
            "Sobre um enorme cogumelo esta uma grande lagarta azul.\n"
            + "Ela fuma seu nargile e olha para voce com olhos frios.\n"
            + "\"Quem e VOCE?\" ela pergunta.",
            new Inimigo("A Lagarta Azul", 1, 0,
                "A Lagarta fala em enigmas e nao deixara voce passar sem uma resposta correta.",
                TipoDesafio.ENIGMA),
            new Enigma(
                "Tenho cidades, mas nenhuma casa. Tenho montanhas, mas nenhuma arvore.\n"
                + "Tenho agua, mas nenhum peixe. Tenho estradas, mas nenhum carro. O que sou?",
                "mapa",
                "E algo que voce olha para encontrar seu caminho"
            ),
            new Item("Pedaco de Cogumelo", "Um lado faz crescer, o outro faz encolher", 25, 10)
        ));

        // Fase 3: Festa do Cha
        fases.add(new Fase(
            "Capitulo 3: A Festa do Cha Maluco",
            "Uma grande mesa esta posta sob uma arvore. O Lebre de Marco e o Chapeleiro\n"
            + "tomam cha sem parar. Alice senta sem ser convidada.\n"
            + "\"Nao ha lugar! Nao ha lugar!\" gritam eles.",
            new Inimigo("O Chapeleiro Louco", 30, 8,
                "O Chapeleiro fala em contradicoes e absurdos. Escolha suas palavras com cuidado.",
                TipoDesafio.DIALOGO),
            new Dialogo(
                new String[]{
                    "Por que um corvo se parece com uma escrivaninha?",
                    "Hoje e meu nao-aniversario! E o seu?",
                    "O Tempo e eu brigamos. Agora sao sempre seis horas da tarde!"
                },
                new String[][]{
                    {
                        "\"Nao faco ideia. Por que?\" (com curiosidade genuina)",
                        "\"Isso nao faz sentido algum!\" (com raiva)",
                        "\"Quero ir embora daqui.\" (ignorando)"
                    },
                    {
                        "\"Meu nao-aniversario tambem! Que coincidencia feliz!\"",
                        "\"Nao sei o que e um nao-aniversario.\"",
                        "\"Isso e ridiculo.\""
                    },
                    {
                        "\"Que situacao terrivel! Posso ajudar de alguma forma?\"",
                        "\"O Tempo nao pode brigar com ninguem, e uma absurdidade!\"",
                        "\"Voce deveria ter se desculpado primeiro.\""
                    }
                },
                new int[]{ 0, 0, 0 }
            ),
            new Item("Relogio de Bolso", "Um presente de nao-aniversario que restaura a sanidade", 0, 30)
        ));

        // Fase 4: Floresta do Gato
        fases.add(new Fase(
            "Capitulo 4: A Floresta do Gato de Cheshire",
            "Um enorme gato sorri numa arvore. Ele vai desaparecendo lentamente,\n"
            + "ate que so o sorriso permanece no ar.",
            new Inimigo("O Gato de Cheshire", 1, 0,
                "O gato oferece conselhos cripticos. Sua escolha determinara o caminho para a Rainha.",
                TipoDesafio.ESCOLHA),
            new String[]{
                "\"Por la\" -- em direcao a casa do Lebre de Marco (voltar atras)",
                "\"Por aqui\" -- em direcao ao palacio da Rainha (o gato sorri mais largo)",
                "Andar em circulos ate algo acontecer"
            },
            1,
            new Item("Sorriso do Gato", "Um fragmento de logica impossivel. Restaura vida.", 30, 15)
        ));

        // Fase 5: Campo de Croquete
        fases.add(new Fase(
            "Capitulo 5: O Campo de Croquete da Rainha",
            "Voce entra no jardim da Rainha de Copas. Soldados-carta avancam em sua direcao!\n"
            + "\"CORTEM-LHE A CABECA!\" grita a Rainha ao longe.\n"
            + "Os soldados avancam. Nao ha como raciocinar com eles.",
            new Inimigo("Soldados-Carta", 60, 12,
                "Os soldados da Rainha atacam sem piedade. Lute para abrir caminho!",
                TipoDesafio.COMBATE),
            new Item("Taco de Flamingo", "Uma arma bizarra. Aumenta sua resistencia.", 15, 0)
        ));

        // Fase 6: Julgamento Final
        fases.add(new Fase(
            "Capitulo 6: O Julgamento da Rainha de Copas",
            "Voce esta diante da Rainha de Copas em seu trono dourado.\n"
            + "\"Esta crianca esta em julgamento! Ela desafiou o Pais das Maravilhas!\"\n"
            + "O Valete le acusacoes interminaveis. A furia da Rainha e absoluta.\n"
            + "Nao ha escapatoria pelo dialogo. E hora de lutar pela liberdade!",
            new Inimigo("A Rainha de Copas", 100, 20,
                "O chefe final do Pais das Maravilhas. Derrote-a para se libertar!",
                TipoDesafio.COMBATE),
            null
        ));
    }

    private void executarJogo() {
        int fasesCompletas = 0;

        for (Fase fase : fases) {
            if (!alice.estaVivo() || alice.estaLouca()) {
                gameOver();
                return;
            }

            boolean sucesso = fase.executar(alice, scanner);
            if (sucesso) fasesCompletas++;

            aguardarEnter();
            mostrarStatus();
            aguardarEnter();
        }

        vitoria(fasesCompletas);
    }

    private void mostrarStatus() {
        System.out.println("--- Status de Alice ---");
        System.out.println(alice);
        System.out.print("Inventario: ");
        if (!alice.temItens()) {
            System.out.println("vazio");
        } else {
            System.out.println();
            for (int i = 0; i < alice.quantidadeItens(); i++) {
                System.out.println("  - " + alice.getItem(i));
            }
        }
        System.out.println("----------------------");
    }

    private void gameOver() {
        System.out.println();
        System.out.println("================================================");
        System.out.println("               FIM DE JOGO                      ");
        System.out.println("================================================");
        if (!alice.estaVivo()) {
            System.out.println("A forca de Alice se esgotou no Pais das Maravilhas...");
        } else {
            System.out.println("A mente de Alice se fragmentou completamente.");
            System.out.println("Ela se perdeu no Pais das Maravilhas para sempre.");
        }
        System.out.println("\nPontuacao final: " + alice.getPontuacao());
        System.out.println("O Pais das Maravilhas a consumiu.");
        System.out.println("================================================");
    }

    private void vitoria(int fasesCompletas) {
        System.out.println();
        System.out.println("================================================");
        System.out.println("           ALICE ACORDA!                        ");
        System.out.println("================================================");
        System.out.println();
        System.out.println("Com um grito final, Alice se ergue perante a Rainha.");
        System.out.println("\"Voces nao sao nada alem de um baralho de cartas!\"");
        System.out.println();
        System.out.println("Todo o baralho se levanta e voa em sua direcao...");
        System.out.println("E Alice acorda na beira do rio.");
        System.out.println("Tudo nao passou de um sonho.");
        System.out.println();
        System.out.println("================================================");
        System.out.println("  Pontuacao Final:       " + alice.getPontuacao());
        System.out.println("  Capitulos conquistados: " + fasesCompletas + "/" + fases.size());
        System.out.println("  Vida restante:          " + alice.getVida() + "/" + alice.getVidaMaxima());
        System.out.println("  Sanidade restante:      " + alice.getSanidade() + "/" + alice.getSanidadeMaxima());

        String avaliacao;
        if (fasesCompletas == fases.size())  avaliacao = "PERFEITO - Mestre do Pais das Maravilhas!";
        else if (fasesCompletas >= 4)        avaliacao = "OTIMO    - Quase impecavel!";
        else if (fasesCompletas >= 2)        avaliacao = "BOM      - Voce sobreviveu!";
        else                                 avaliacao = "SOBREVIVEU - Por muito pouco...";

        System.out.println("  Avaliacao:              " + avaliacao);
        System.out.println("================================================");
    }

    private void aguardarEnter() {
        System.out.print("\n[Pressione ENTER para continuar...]");
        scanner.nextLine();
    }
}