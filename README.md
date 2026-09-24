# Sonora – Fase 06: herança (Conteudo, Musica e Podcast)

Plataforma de músicas, podcasts, playlists e usuários feita em Java puro (sem build tool, sem
framework). Nesta fase (enunciado `L08 - Unidade 4`) o Sonora passa a reconhecer mais de um tipo
de conteúdo: a superclasse **`Conteudo`** concentra o que é comum e as subclasses **`Musica`** e
**`Podcast`** acrescentam só o que é delas.

> O enunciado chama a pasta de `sonora-fase05`, mas este repositório já usou "Fase 05" para a
> etapa anterior (associações e `ArrayList`, `L06 - Unidade 3`). Por isso aqui ela é a Fase 06.

## Estrutura

```
sonora/
├── App.java                 menu de texto (main) + demonstração da herança ao iniciar
├── Conteudo.java            superclasse: id, título, duração, reproduções, reproduzir()
├── Musica.java              subclasse de Conteudo: artista e álbum
├── Podcast.java             subclasse de Conteudo: apresentador e número do episódio
├── Usuario.java             (todas as classes ficam no pacote padrão)
├── Playlist.java
├── Plataforma.java
├── docs/
│   ├── diagrama-classes.png diagrama de classes da Fase 06, com a herança (entregável)
│   ├── diagrama-classes.svg mesma imagem, editável
│   ├── gerar_diagrama.py    script que gera o .svg
│   ├── MODELAGEM.md         a herança e os 7 relacionamentos, cada um justificado pelo código
│   ├── diagrama-fase05.png  diagrama da fase anterior (associações, sem herança)
│   └── diagrama-fase02.png  diagrama antigo (só as classes, sem associações)
├── lib/
│   └── junit-platform-console-standalone-6.0.0.jar   JUnit 6 (API + engine + launcher)
├── test/                    uma classe espelho por classe de produção
│   ├── ConteudoTest.java    PL14
│   ├── MusicaTest.java      PL01, PL02, PL07, PL08, PL15
│   ├── PodcastTest.java     PL16
│   ├── UsuarioTest.java     PL09, PL08, PL12
│   ├── PlaylistTest.java    PL03, PL04, PL05, PL10
│   └── PlataformaTest.java  PL06, PL11, PL13, PL17
├── prova/                   material de estudo para a prova (ver abaixo)
├── PLANOS_DE_TESTE.md       tabelas Caso / Descrição / Entrada / Saída esperada
└── L0x - Unidade_y.pdf      enunciados das fases
```

`App.java` não tem classe de teste: é só o menu (entrada/saída no console).

## Material de prova

A pasta [`prova/`](prova/) reúne o material de estudo. O arquivo principal é
**`prova/Questoes POO - prova.pdf`**: 28 páginas com as 88 questões respondidas — as 30 da lista
de revisão do professor e 58 extras escritas a partir do material da disciplina.

| Arquivo | Para quê |
|---|---|
| `prova/Questoes POO - prova.pdf` | **o principal** — as 88 questões respondidas, com índice e gabarito |
| [`prova/ERROS_PROVA_PRATICA.md`](prova/ERROS_PROVA_PRATICA.md) | os erros mais comuns da prática, com as mensagens reais do compilador, esqueletos de código e checklist |
| [`prova/material-do-professor/`](prova/material-do-professor/) | cópia offline do repositório da disciplina (teoria + código de aula) |

## O que mudou nesta fase (Fase 06: herança)

### 1. Diagrama primeiro (ver `docs/diagrama-classes.png` e `docs/MODELAGEM.md`)

O diagrama ganhou a hierarquia `Musica` —▷ `Conteudo` ◁— `Podcast` com a seta de
especialização (triângulo vazado na superclasse), o `# setId` marcado como `protected` e os
atributos no lugar certo: os comuns só em `Conteudo`, os específicos só em cada subclasse.

### 2. Superclasse `Conteudo`

- `id` gerado por um contador `static` (`contagem`), igual ao padrão de `Usuario` e `Playlist`.
  O contador é um só para músicas e podcasts, então dois conteúdos nunca têm o mesmo id.
- `titulo` (não pode ser nulo nem vazio) e `duracaoSegundos` (> 0), com getters e setters que
  validam e lançam `IllegalArgumentException`.
- `setId` é `protected`: as subclasses podem usar, o resto do sistema não.
- `reproduzir()`, `getReproducoes()` e `getDuracaoFormatada()` subiram de `Musica` para cá,
  porque servem para qualquer conteúdo.
- `toString()` sobrescreve o de `Object` e devolve `[id] titulo (duracaos)`.

### 3. Subclasses `Musica` e `Podcast`

- `Musica extends Conteudo`: guarda só `artista` e `album`. O construtor passou a ser
  `Musica(titulo, duracaoSegundos, artista, album)`.
- `Podcast extends Conteudo`: guarda `apresentador` e `numeroEpisodio` (>= 1).
- Os dois construtores chamam `super(titulo, duracaoSegundos)` como primeiro comando e depois
  cuidam só dos próprios campos. As validações comuns ficaram em `Conteudo`; as específicas,
  em cada subclasse.
- Os dois sobrescrevem `toString()` com `@Override`, chamando `super.toString()` e
  acrescentando artista/álbum ou episódio/apresentador. `reproduzir()` **não** é reescrito:
  vem pronto da superclasse.
- `Musica.informacoes()` saiu; quem lista músicas (`Plataforma` e `Playlist`) usa o `toString()`.

### 4. `App` e `Plataforma`

- Ao iniciar, o `main` cria duas músicas e um podcast, chama `reproduzir()` em cada um e
  imprime cada objeto (o `toString()` de cada subclasse), tudo dentro de `try/catch`. Esses
  três conteúdos ficam cadastrados na plataforma.
- Novo menu **[PODCAST(S)]**: criar, listar, deletar, buscar por id e reproduzir.
- A `Plataforma` guarda os podcasts numa `ArrayList<Podcast>` própria, separada das músicas.
  Sem `instanceof`, cast ou coleção de `Conteudo`: isso é assunto da fase de polimorfismo.

## O que mudou na Fase 05 (anterior)

### 1. Modelagem (ver `docs/MODELAGEM.md`)

Seis associações desenhadas com os quatro adornos. As quatro pedidas no enunciado —
Plataforma–Musica, Plataforma–Usuario, Usuario–Playlist e a reflexiva Usuario–Usuario — mais
Plataforma–Playlist (existe no código) e Playlist–Musica (o exemplo já resolvido). Todas são
unidirecionais, porque em nenhum par as duas classes guardam referência uma da outra.

### 2. Arrays viraram ArrayList

| Antes | Agora |
|---|---|
| `Playlist`: `Musica[100]` + campo `quantidade` | `ArrayList<Musica>`; `getQuantidade()` devolve `size()` |
| `Plataforma`: `Musica[500]`, `Usuario[500]`, `Playlist[500]` | `ArrayList<Musica>`, `ArrayList<Usuario>`, `ArrayList<Playlist>` |
| `Usuario`: nada | `ArrayList<Usuario> seguindo` (associação reflexiva) |

Efeitos diretos: **não existe mais "playlist cheia"** (`adicionar` sempre devolve `true`) nem
teto de 500 na plataforma, e a contagem manual sumiu — quem responde é o `size()`.

### 3. Seguir e deixar de seguir

Em `Usuario`: `seguir(outro)`, `deixarDeSeguir(outro)`, `getQuantidadeSeguindo()`, mais
`segue(outro)` e `getSeguindo()` para consulta. Ninguém segue a si mesmo nem segue duas vezes a
mesma pessoa — as duas tentativas levantam exceção, tratada no menu.

No `App`, dentro de **[USUÁRIO(S)]**: `5 - Seguir usuário`, `6 - Deixar de seguir usuário`,
`7 - Listar quem um usuário segue` e `8 - Listar os seguidores de um usuário`.

### 4. Os bugs apontados na Fase 03 foram corrigidos

A lista de "pontos de atenção" do README anterior saiu resolvida pela refatoração:

- A `Plataforma` **não usa mais os contadores `static`** das outras classes para posicionar nem
  para limitar nada. Ela guarda o que foi cadastrado e localiza tudo **pelo id**, percorrendo a
  lista. Criar duas músicas e só depois cadastrar as duas agora funciona.
- `Playlist.getTodasMusicas()` percorre a própria lista, não `Musica.getContagem()`.
- `excluirMusica` não mexe mais no contador de ids (o método `decContagem()` foi removido das
  três classes), então nenhum id é reaproveitado.
- Excluir um usuário agora limpa as referências a ele: quem o seguia deixa de segui-lo e as
  playlists de que ele era dono saem junto (playlist sem dono não pode existir).

Os ids continuam vindo de contadores `static` que não zeram entre os testes — por isso os
testes de id comparam valores relativos.

## Rodar o App

```bash
javac -encoding UTF-8 -d out *.java
java -cp out App
```

## Rodar os testes pela linha de comando

Precisa de JDK 17 ou mais novo (o JUnit 6 exige). Na raiz do projeto:

```bash
javac -encoding UTF-8 -d out -cp lib/junit-platform-console-standalone-6.0.0.jar *.java test/*.java
java -jar lib/junit-platform-console-standalone-6.0.0.jar execute --class-path out --scan-classpath
```

São **117 testes**, todos verdes. O relatório sai em árvore, com o `@DisplayName` de cada caso.
Pra rodar uma classe só:

```bash
java -jar lib/junit-platform-console-standalone-6.0.0.jar execute --class-path out --select-class PlaylistTest
```

No Windows os mesmos comandos funcionam no `cmd`/PowerShell (o `javac` expande o `*.java`).

## Rodar os testes na IDE

**IntelliJ IDEA** (roteiro dos slides):

1. `File > Project Structure` (ou botão direito no projeto > *Open Module Settings*).
2. Em *Libraries*, adicionar `lib/junit-platform-console-standalone-6.0.0.jar`.
3. Em *Modules > Sources*, marcar a pasta `test` como **Tests** (verde) e a raiz como *Sources*.
4. Botão direito em `test` > *Run 'All Tests'*.

**VS Code** (extensão *Extension Pack for Java*): o jar em `lib/` já é reconhecido como
*Referenced Library*; a pasta `test` aparece no painel *Testing* depois de abrir o projeto.
