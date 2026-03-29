# Alice no País das Maravilhas — The Looking Glass Game

Jogo de aventura em texto desenvolvido em Java para a disciplina de Programação Orientada a Objetos.

---

## Descrição do Jogo

Alice atravessa o País das Maravilhas capítulo por capítulo. Em cada fase ela encontra um personagem, enfrenta um conflito e precisa resolvê-lo por meio de:

- **Combate** — batalha por turnos (ataque/use item)
- **Diálogo** — escolha as respostas certas para convencer o personagem
- **Enigma** — resolva a adivinha com até 3 tentativas
- **Escolha** — tome a decisão correta entre as opções apresentadas

Alice possui **Vida**, **Sanidade** e **Pontuação**. Perder toda a vida ou toda a sanidade resulta em derrota.

### Capítulos

| # | Capítulo | Desafio |
|---|----------|---------|
| 1 | O Salão das Portas | Escolha |
| 2 | O Jardim da Lagarta | Enigma |
| 3 | A Festa do Chá Maluco | Diálogo |
| 4 | A Floresta do Gato de Cheshire | Escolha |
| 5 | O Campo de Croquete da Rainha | Combate |
| 6 | O Julgamento da Rainha de Copas | Combate (chefe) |

---

## Classes Criadas

### `model.Personagem` — Classe abstrata base

| Atributos | Tipo | Descrição |
|-----------|------|-----------|
| `nome` | `String` | Nome do personagem |
| `vida` | `int` | Vida atual |
| `vidaMaxima` | `int` | Vida máxima |

| Métodos | Descrição |
|---------|-----------|
| `getNome()` | Retorna o nome |
| `getVida()` | Retorna a vida atual |
| `getVidaMaxima()` | Retorna a vida máxima |
| `estaVivo()` | Retorna `true` se vida > 0 |
| `setVida(int)` | Define a vida com validação (0 a vidaMaxima) |
| `receberDano(int)` | Reduz a vida |
| `curar(int)` | Aumenta a vida |
| `atacar()` | **Abstrato** — implementado nas subclasses |

---

### `model.Alice` — Jogadora (herda de `Personagem`)

| Atributos | Tipo | Descrição |
|-----------|------|-----------|
| `sanidade` | `int` | Sanidade atual |
| `sanidadeMaxima` | `int` | Sanidade máxima |
| `pontuacao` | `int` | Pontos acumulados |
| `inventario` | `List<Item>` | Lista de itens coletados |
| `poderAtaque` | `int` | Base de dano por ataque |

| Métodos | Descrição |
|---------|-----------|
| `atacar()` | **Sobrescreve** Personagem — retorna dano aleatório |
| `getSanidade()` | Retorna sanidade atual |
| `getPontuacao()` | Retorna pontuação |
| `estaLouca()` | Retorna `true` se sanidade ≤ 0 |
| `adicionarPontos(int)` | Incrementa pontuação |
| `perderSanidade(int)` | Reduz sanidade |
| `ganharSanidade(int)` | Aumenta sanidade |
| `adicionarItem(Item)` | Adiciona item ao inventário |
| `usarItem(int)` | Usa item pelo índice e o remove |
| `temItens()` | Verifica se o inventário não está vazio |

---

### `model.Inimigo` — Antagonista/obstáculo (herda de `Personagem`)

| Atributos | Tipo | Descrição |
|-----------|------|-----------|
| `poderAtaque` | `int` | Dano base por ataque |
| `descricao` | `String` | Descrição narrativa |
| `tipoDesafio` | `TipoDesafio` | Tipo de desafio que impõe |

| Métodos | Descrição |
|---------|-----------|
| `atacar()` | **Sobrescreve** Personagem — retorna dano aleatório |
| `getDescricao()` | Retorna a descrição |
| `getTipoDesafio()` | Retorna o tipo de desafio |

---

### `model.Item` — Item coletável

| Atributos | Tipo | Descrição |
|-----------|------|-----------|
| `nome` | `String` | Nome do item |
| `descricao` | `String` | Descrição do item |
| `curaVida` | `int` | Vida restaurada ao usar |
| `curaSanidade` | `int` | Sanidade restaurada ao usar |

| Métodos | Descrição |
|---------|-----------|
| `getNome()` | Retorna o nome |
| `getDescricao()` | Retorna a descrição |
| `usar(Alice)` | Aplica o efeito do item em Alice |

---

### `model.Enigma` — Adivinha para desafios ENIGMA

| Atributos | Tipo | Descrição |
|-----------|------|-----------|
| `pergunta` | `String` | Texto da adivinha |
| `resposta` | `String` | Resposta correta |
| `dica` | `String` | Dica para o jogador |

| Métodos | Descrição |
|---------|-----------|
| `getPergunta()` | Retorna a pergunta |
| `getDica()` | Retorna a dica |
| `verificarResposta(String)` | Valida a resposta (ignora maiúsculas) |

---

### `model.Dialogo` — Diálogo em múltiplas rodadas

| Atributos | Tipo | Descrição |
|-----------|------|-----------|
| `perguntas` | `String[]` | Falas do personagem por rodada |
| `opcoes` | `String[][]` | Opções de resposta por rodada |
| `respostasCorretas` | `int[]` | Índice da resposta correta por rodada |

| Métodos | Descrição |
|---------|-----------|
| `getNumRodadas()` | Retorna o número de rodadas |
| `getPergunta(int)` | Retorna a fala da rodada |
| `getOpcoes(int)` | Retorna as opções da rodada |
| `isCorreto(int, int)` | Verifica se a escolha é correta |

---

### `game.TipoDesafio` — Enum

Valores: `COMBATE`, `DIALOGO`, `ENIGMA`, `ESCOLHA`

---

### `game.Fase` — Capítulo do jogo

| Atributos | Tipo | Descrição |
|-----------|------|-----------|
| `titulo` | `String` | Título do capítulo |
| `narrativa` | `String` | Texto narrativo |
| `inimigo` | `Inimigo` | Personagem da fase |
| `enigma` | `Enigma` | Adivinha (fases ENIGMA) |
| `dialogo` | `Dialogo` | Diálogo (fases DIALOGO) |
| `escolhas` | `String[]` | Opções (fases ESCOLHA) |
| `recompensa` | `Item` | Item obtido ao completar |

| Métodos | Descrição |
|---------|-----------|
| `executar(Alice, Scanner)` | Executa a fase completa |
| `executarCombate(...)` | Lógica de combate por turnos |
| `executarDialogo(...)` | Lógica de diálogo |
| `executarEnigma(...)` | Lógica de enigma |
| `executarEscolha(...)` | Lógica de escolha |

---

### `game.Jogo` — Controlador principal

| Atributos | Tipo | Descrição |
|-----------|------|-----------|
| `alice` | `Alice` | O jogador |
| `fases` | `List<Fase>` | Lista de fases do jogo |
| `scanner` | `Scanner` | Leitura de entrada do usuário |

| Métodos | Descrição |
|---------|-----------|
| `iniciar()` | Inicia o jogo |
| `configurarFases()` | Cria e adiciona todas as fases |
| `executarJogo()` | Loop principal do jogo |
| `mostrarStatus()` | Exibe o estado atual de Alice |
| `vitoria(int)` | Exibe a tela de vitória |
| `gameOver()` | Exibe a tela de derrota |

---

## Encapsulamento

Todos os atributos das classes são declarados como **`private`**, impedindo acesso direto externo. O acesso é feito exclusivamente através de **getters** e **setters** com validação:

```java
// Exemplo em Personagem.java
private int vida;

public int getVida() { return vida; }

public void setVida(int vida) {
    this.vida = Math.max(0, Math.min(vida, vidaMaxima)); // valida range
}
```

Isso garante que valores inválidos (vida negativa, sanidade acima do máximo) nunca sejam atribuídos diretamente.

---

## Herança

O projeto implementa a seguinte hierarquia:

```
Personagem (abstrata)
├── Alice      (jogadora)
└── Inimigo    (antagonistas)
```

- **`Personagem`** define os atributos e comportamentos comuns (nome, vida, receberDano, curar).
- **`Alice`** e **`Inimigo`** **herdam** de `Personagem` e **sobrescrevem** o método abstrato `atacar()`, cada um com sua própria lógica de dano (polimorfismo).
- `Alice` adiciona atributos extras exclusivos: `sanidade`, `pontuacao` e `inventario`.
- `Inimigo` adiciona: `poderAtaque`, `descricao` e `tipoDesafio`.

```java
// Em Alice.java
public class Alice extends Personagem {
    @Override
    public int atacar() {
        return poderAtaque + (int)(Math.random() * 10); // ataque com variacao
    }
}

// Em Inimigo.java
public class Inimigo extends Personagem {
    @Override
    public int atacar() {
        return poderAtaque + (int)(Math.random() * 8);
    }
}
```

---

## Como Executar

### Pré-requisito
Java JDK 8 ou superior instalado.

### Compilar

```bash
# A partir da pasta siga-o-coelho-branco/
javac -encoding UTF-8 -d out src/model/Personagem.java src/model/Item.java src/model/Enigma.java src/model/Dialogo.java src/game/TipoDesafio.java src/model/Alice.java src/model/Inimigo.java src/game/Fase.java src/game/Jogo.java src/Main.java
```

### Executar

```bash
java -cp out Main
```

### No Windows (cmd) — para exibir acentos corretamente

```cmd
chcp 65001
java -cp out Main
```
