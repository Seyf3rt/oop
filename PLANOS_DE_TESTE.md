# Sonora – Fase 06: Planos de teste

Cada linha vira um método de teste em `test/` (o `@DisplayName` do método é o texto da coluna
**Descrição**). Os planos PL01 e PL02 vieram prontos no enunciado da Fase 03; PL03 a PL08 são os
pedidos de lá; PL09 a PL11 cobrem o resto do contrato da Fase 02 (construtores de `Usuario` e
`Playlist` e o cadastro na `Plataforma`). **PL12 e PL13 são da Fase 05**: a associação reflexiva
(seguir usuários) e o acervo em `ArrayList`. **PL14 a PL17 são da Fase 06**: a herança
`Conteudo` → `Musica`, `Podcast` e os podcasts na `Plataforma`.

> **Sobre o `buscarMusicaPorId(id)` do enunciado:** neste projeto a busca por id é a sobrecarga
> `Plataforma.buscarMusica(int id)`. O PL06 foi ajustado pra esse nome.
>
> **Sobre os ids:** os contadores de id (`Conteudo.contagem`, `Usuario.contagem`, `Playlist.contagem`)
> são `static` e não são zerados entre os testes, que rodam todos na mesma JVM. Por isso o PL08
> compara ids **relativos** (o 2º id é o 1º + 1) em vez de valores absolutos (1, 2, 3). Desde a
> Fase 06 o id de `Musica` vem de `Conteudo`, contador que também é usado por `Podcast`.
>
> **Sobre o construtor de `Musica` (mudou na Fase 06):** agora é
> `Musica(titulo, duracaoSegundos, artista, album)`. Nas tabelas abaixo, onde a entrada não cita
> o álbum, os testes usam um álbum válido (ex.: `"A Night at the Opera"` para Queen).
>
> **Sobre a capacidade (mudou na Fase 05):** com `ArrayList` no lugar dos arrays, a playlist não
> tem mais teto de 100 nem a plataforma de 500. Os casos PL03-3 e PL03-4, que testavam "encheu"
> e "passou do limite", foram reescritos: agora verificam que a lista **cresce** além de 100.

---

## PL01 – Validar `Musica.getDuracaoFormatada()`

Classe de teste: `MusicaTest`

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Duração com minutos e segundos | Música de 125 segundos | Deve resultar em `"02:05"` |
| 2 | Duração redonda em minutos | Música de 90 segundos | Deve resultar em `"01:30"` |
| 3 | Menos de um minuto, com zero à esquerda | Música de 5 segundos | Deve resultar em `"00:05"` |
| 4 | Dois dígitos nos minutos | Música de 600 segundos | Deve resultar em `"10:00"` |
| 5 | Valor logo abaixo de dez minutos | Música de 599 segundos | Deve resultar em `"09:59"` |

## PL02 – Validar construtor de `Musica` com dados inválidos

Classe de teste: `MusicaTest`

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Título vazio deve ser rejeitado | título `""`, artista `"Queen"`, duração 355 | Deve lançar `IllegalArgumentException` |
| 2 | Título nulo deve ser rejeitado | título `null`, artista `"Queen"`, duração 355 | Deve lançar `IllegalArgumentException` |
| 3 | Artista vazio deve ser rejeitado | título `"Bohemian Rhapsody"`, artista `""`, duração 355 | Deve lançar `IllegalArgumentException` |
| 4 | Duração zero deve ser rejeitada | título válido, artista válido, duração 0 | Deve lançar `IllegalArgumentException` |
| 5 | Duração negativa deve ser rejeitada | título válido, artista válido, duração -10 | Deve lançar `IllegalArgumentException` |
| 6 | Dados válidos criam a música | título `"Bohemian Rhapsody"`, artista `"Queen"`, duração 355 | Objeto criado, com id maior que zero e getters devolvendo os valores informados |
| 7 *(adicional)* | Título só com espaços deve ser rejeitado | título `"   "`, artista `"Queen"`, duração 355 | Deve lançar `IllegalArgumentException` |
| 8 *(adicional)* | A mensagem da exceção descreve o erro | título válido, artista válido, duração -30 | `IllegalArgumentException` com mensagem não vazia que cita o valor `-30` |

## PL03 – Validar `Playlist.adicionar(musica)`

Classe de teste: `PlaylistTest` · Cenário base (`@BeforeEach`): playlist vazia `"Clássicos do Rock"` com dono válido e três músicas criadas (Bohemian Rhapsody 355 s, Hotel California 391 s, Stairway to Heaven 482 s).

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Adicionar em playlist vazia retorna true e a quantidade vira um | `adicionar(bohemian)` na playlist vazia | Retorna `true`; `getQuantidade()` == 1 |
| 2 | Adicionar três músicas sobe a quantidade para três e mantém a ordem | `adicionar` de bohemian, hotel e stairway, nessa ordem | `getQuantidade()` == 3; posições 0, 1 e 2 devolvem bohemian, hotel e stairway |
| 3 | Adicionar 100 músicas: todas as adições retornam true | 100 chamadas de `adicionar` | Todas retornam `true`; `getQuantidade()` == 100 |
| 4 | A playlist não tem tamanho máximo: passar de 100 músicas continua funcionando | Playlist com 100 músicas, `adicionar(hotel)` | Retorna `true`; `getQuantidade()` == 101 e a posição 100 devolve hotel |
| 5 | Adicionar música nula lança IllegalArgumentException e nada é adicionado | `adicionar(null)` | Deve lançar `IllegalArgumentException`; `getQuantidade()` continua 0 |

## PL04 – Validar `Playlist.getNaPosicao(indice)`

Classe de teste: `PlaylistTest` · Playlist com `[bohemian, hotel, stairway]`, salvo onde indicado.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Posição zero devolve a primeira música adicionada | `getNaPosicao(0)` | Devolve bohemian |
| 2 | Última posição válida devolve a última música adicionada | `getNaPosicao(getQuantidade() - 1)` | Devolve stairway |
| 3 | Índice negativo lança IndexOutOfBoundsException | `getNaPosicao(-1)` | Deve lançar `IndexOutOfBoundsException` |
| 4 | Índice igual à quantidade lança IndexOutOfBoundsException | `getNaPosicao(3)` numa playlist de 3 músicas | Deve lançar `IndexOutOfBoundsException` |
| 5 | Playlist vazia: posição zero lança IndexOutOfBoundsException | `getNaPosicao(0)` numa playlist vazia | Deve lançar `IndexOutOfBoundsException` |

## PL05 – Validar `Playlist.removerNaPosicao(indice)`

Classe de teste: `PlaylistTest` · Playlist com `[bohemian, hotel, stairway]`.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Remover do meio: a música seguinte assume a posição e a quantidade cai em um | `removerNaPosicao(1)` | `getQuantidade()` == 2; posição 0 é bohemian e posição 1 é stairway (sem buraco) |
| 2 | Remover a primeira: a segunda música passa a ocupar a posição zero | `removerNaPosicao(0)` | `getQuantidade()` == 2; posição 0 é hotel e posição 1 é stairway |
| 3 | Remover a última: a quantidade cai e a antiga posição passa a ser inválida | `removerNaPosicao(2)` | `getQuantidade()` == 2; posição 1 é hotel; `getNaPosicao(2)` lança `IndexOutOfBoundsException` |
| 4 | Remoção de posição válida retorna true | `removerNaPosicao(1)` | Retorna `true` |
| 5 | Índice negativo lança IndexOutOfBoundsException e nada é removido | `removerNaPosicao(-1)` | Deve lançar `IndexOutOfBoundsException`; `getQuantidade()` continua 3 |
| 6 | Índice além da quantidade lança IndexOutOfBoundsException e nada é removido | `removerNaPosicao(3)` | Deve lançar `IndexOutOfBoundsException`; `getQuantidade()` continua 3 |
| 7 | A duração total acompanha a remoção | Duração total 1228 s; `removerNaPosicao(1)` (tira os 391 s de Hotel California) | `getDuracaoSegundos()` == 837 e `getDuracaoFormatada()` == `"13:57"` |

## PL06 – Validar `Plataforma.buscarMusica(titulo)` e `buscarMusica(id)`

Classe de teste: `PlataformaTest` · Cenário base (`@BeforeEach`): `Plataforma` nova com Bohemian Rhapsody e Hotel California cadastradas e o usuário Lucas cadastrado.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Buscar por id de música cadastrada devolve a música | `buscarMusica(bohemian.getId())` e `buscarMusica(hotel.getId())` | Devolve bohemian e hotel, respectivamente |
| 2 | Buscar por título de música cadastrada devolve a música | `buscarMusica("Hotel California")` | Devolve hotel |
| 3 | Buscar por título ignora maiúsculas e minúsculas | `buscarMusica("bohemian rhapsody")` e `buscarMusica("BOHEMIAN RHAPSODY")` | Devolve bohemian nas duas |
| 4 | Buscar por id inexistente devolve null | `buscarMusica(Conteudo.getContagem() + 1)` (id que nunca foi gerado) | Devolve `null` |
| 5 | Buscar por título inexistente devolve null | `buscarMusica("Música que não existe")` | Devolve `null` |

## PL07 – Validar `Musica.reproduzir()`

Classe de teste: `MusicaTest` · Cenário base: `bohemian` = Bohemian Rhapsody (355 s), recém-criada.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Música recém-criada começa com zero reproduções | `bohemian.getReproducoes()` sem nenhuma chamada | Deve resultar em 0 |
| 2 | Uma chamada de reproduzir() aumenta o contador em um | Uma chamada de `reproduzir()` | `getReproducoes()` == valor anterior + 1 |
| 3 | Três chamadas de reproduzir() resultam em três reproduções | Três chamadas de `reproduzir()` | `getReproducoes()` == 3 |
| 4 | Reproduzir uma música não altera o contador de outra | Duas chamadas de `reproduzir()` em bohemian; outra música sem chamadas | bohemian com 2 reproduções; a outra com 0 |

## PL08 (bônus) – Contadores de id

Classes de teste: `MusicaTest` (casos 1 a 4) e `UsuarioTest` (casos 5 e 6).

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | A segunda música criada recebe o id da primeira mais um | Duas músicas criadas em sequência | `segunda.getId()` == `primeira.getId() + 1` |
| 2 | Três músicas criadas em sequência têm ids consecutivos | Três músicas criadas em sequência | Ids são `id1`, `id1 + 1` e `id1 + 2` |
| 3 | Criar um usuário entre duas músicas não interfere nos ids de Música | Música, depois um `Usuario`, depois outra música | A 2ª música tem id da 1ª + 1 (o usuário não consumiu id de música) |
| 4 | O id da música é o valor atual do contador de Conteudo | Uma música nova | `getId()` == `Conteudo.getContagem()` |
| 5 | Dois usuários criados em sequência têm ids consecutivos | Dois usuários criados em sequência | `segundo.getId()` == `primeiro.getId() + 1` |
| 6 | Criar uma música entre dois usuários não interfere nos ids de Usuário | Usuário, depois uma `Musica`, depois outro usuário | O 2º usuário tem id do 1º + 1 |

## PL09 – Validar construtor de `Usuario` com dados inválidos

Classe de teste: `UsuarioTest`

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Nome vazio deve ser rejeitado | nome `""`, e-mail `"lucas@sonora.com"` | Deve lançar `IllegalArgumentException` |
| 2 | Nome nulo deve ser rejeitado | nome `null`, e-mail `"lucas@sonora.com"` | Deve lançar `IllegalArgumentException` |
| 3 | E-mail vazio deve ser rejeitado | nome `"Lucas"`, e-mail `""` | Deve lançar `IllegalArgumentException` |
| 4 | E-mail nulo deve ser rejeitado | nome `"Lucas"`, e-mail `null` | Deve lançar `IllegalArgumentException` |
| 5 | E-mail sem @ deve ser rejeitado | nome `"Lucas"`, e-mail `"lucas.sonora.com"` | Deve lançar `IllegalArgumentException` com mensagem não vazia |
| 6 | Dados válidos criam o usuário | nome `"Lucas"`, e-mail `"lucas@sonora.com"` | Objeto criado, id maior que zero, `getNome()` e `getEmail()` devolvem os valores informados |

## PL10 – Validar construtor de `Playlist`

Classe de teste: `PlaylistTest`

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Título vazio deve ser rejeitado | título `""`, dono válido | Deve lançar `IllegalArgumentException` |
| 2 | Título nulo deve ser rejeitado | título `null`, dono válido | Deve lançar `IllegalArgumentException` |
| 3 | Dono nulo deve ser rejeitado | título `"Clássicos do Rock"`, dono `null` | Deve lançar `IllegalArgumentException` |
| 4 | Dados válidos criam a playlist vazia | título `"Clássicos do Rock"`, dono válido | Id maior que zero, `getTitulo()` e `getDono()` corretos, `getQuantidade()` == 0 e `getDuracaoSegundos()` == 0 |

## PL11 – Validar `Plataforma.cadastrarMusica` e `cadastrarUsuario`

Classe de teste: `PlataformaTest` · Mesmo cenário base do PL06.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Música nula não é cadastrada (retorna false) | `cadastrarMusica(null)` | Retorna `false` |
| 2 | Música válida é cadastrada (retorna true) e passa a ser encontrada | `cadastrarMusica(stairway)` | Retorna `true`; `buscarMusica(id)` e `buscarMusica("Stairway to Heaven")` devolvem stairway |
| 3 | Usuário nulo não é cadastrado (retorna false) | `cadastrarUsuario(null)` | Retorna `false` |
| 4 | Usuário válido é cadastrado (retorna true) | `cadastrarUsuario(ana)` | Retorna `true` |

> O caso "estrutura cheia retorna false" saiu de cena na Fase 05: com `ArrayList` não existe mais
> limite de 500 na `Plataforma`. O que entrou no lugar está no PL13 — a plataforma passou a guardar
> o próprio acervo e a localizar tudo pelo id, em vez de usar os contadores `static` das outras
> classes como posição e como teto.

---

## PL12 – Associação reflexiva: seguir e deixar de seguir

Classe de teste: `UsuarioTest` · Cenário base (`@BeforeEach`): `lucas` = Usuário "Lucas" recém-criado.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Usuário recém-criado não segue ninguém | `lucas.getQuantidadeSeguindo()` | Deve resultar em 0 |
| 2 | Seguir outro usuário sobe a quantidade para um | `lucas.seguir(ana)` | `getQuantidadeSeguindo()` == 1 e `lucas.segue(ana)` é `true` |
| 3 | Seguir é de mão única: quem é seguido não passa a seguir de volta | `lucas.seguir(ana)` | `lucas.segue(ana)` é `true`, `ana.segue(lucas)` é `false` e `ana.getQuantidadeSeguindo()` == 0 |
| 4 | Seguir vários usuários acumula na lista | `lucas.seguir(ana)` e `lucas.seguir(bruno)` | `getQuantidadeSeguindo()` == 2; segue os dois |
| 5 | Seguir a si mesmo lança IllegalArgumentException e nada é adicionado | `lucas.seguir(lucas)` | Deve lançar `IllegalArgumentException`; quantidade continua 0 |
| 6 | Seguir o mesmo usuário duas vezes lança IllegalStateException e não duplica | `lucas.seguir(ana)` duas vezes | A 2ª lança `IllegalStateException`; quantidade continua 1 |
| 7 | Seguir usuário nulo lança IllegalArgumentException | `lucas.seguir(null)` | Deve lançar `IllegalArgumentException`; quantidade continua 0 |
| 8 | Deixar de seguir remove o usuário e a quantidade cai | Seguindo ana e bruno, `deixarDeSeguir(ana)` | Quantidade == 1; não segue mais ana; continua seguindo bruno |
| 9 | Deixar de seguir quem não é seguido lança IllegalStateException | `lucas.deixarDeSeguir(ana)` sem ter seguido | Deve lançar `IllegalStateException` |
| 10 | Deixar de seguir usuário nulo lança IllegalArgumentException | `lucas.deixarDeSeguir(null)` | Deve lançar `IllegalArgumentException` |
| 11 | A lista devolvida por getSeguindo é uma cópia: mexer nela não afeta o usuário | `getSeguindo()` seguido de `clear()` na cópia | `getQuantidadeSeguindo()` continua 1 |

## PL13 – Acervo em `ArrayList` e a associação reflexiva pela `Plataforma`

Classe de teste: `PlataformaTest` · Mesmo cenário base do PL06.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | O total de músicas conta só o que a plataforma guarda | `getTotalMusicas()` antes e depois de cadastrar mais uma | 2, depois 3 |
| 2 | Uma plataforma nova nasce com o acervo vazio, mesmo com músicas já criadas | `new Plataforma()` | Totais em 0; `buscarMusica(id)` devolve `null` |
| 3 | Músicas criadas antes do cadastro não se sobrescrevem | Criar duas músicas e só então cadastrar as duas | As duas são encontradas por id (era o bug da Fase 03) |
| 4 | A mesma música não é cadastrada duas vezes | `cadastrarMusica(bohemian)` de novo | Retorna `false`; total continua 2 |
| 5 | Excluir música tira do acervo sem mexer nos ids das próximas | `excluirMusica(hotel.getId())` e criar outra música | Busca devolve `null`, total == 1 e a nova música recebe o id seguinte (sem repetir) |
| 6 | Um usuário passa a seguir outro pela plataforma | `seguirUsuario(lucas.getId(), ana.getId())` | `lucas.segue(ana)` é `true`; quantidade == 1 |
| 7 | Seguir usuário inexistente lança IllegalArgumentException | `seguirUsuario(lucas.getId(), idInexistente)` | Deve lançar `IllegalArgumentException` |
| 8 | Deixar de seguir pela plataforma desfaz a ligação | `deixarDeSeguirUsuario(lucas.getId(), ana.getId())` | `lucas.segue(ana)` é `false`; quantidade == 0 |
| 9 | Excluir um usuário também remove as ligações de quem o seguia | Lucas segue Ana; `excluirUsuario(ana.getId())` | Retorna `true`; Lucas não segue mais ninguém |
| 10 | Excluir um usuário leva junto as playlists de que ele era dono | Playlist de Lucas; `excluirUsuario(lucas.getId())` | Retorna `true`; `getTotalPlaylists()` == 0 |
| 11 | A playlist criada pela plataforma recebe o dono informado | `cadastrarPlaylist("Clássicos do Rock", lucas.getId())` | Dono é Lucas e o título confere |
| 12 | Adicionar música à playlist pela plataforma usa o id, não a posição | `addMusicaPlaylist(playlist.getId(), hotel.getId())` | Retorna `true`; a playlist tem 1 música e a posição 0 é hotel |
| 13 | Remover da playlist uma música que não está nela devolve false | `excluirMusicaPlaylist(playlist.getId(), bohemian.getId())` | Retorna `false`; a quantidade não muda |

---

## PL14 – Superclasse `Conteudo`

Classe de teste: `ConteudoTest` · Cenário base (`@BeforeEach`): `conteudo` = Conteudo "Faixa bônus" de 125 s. `Conteudo` é concreta (sem `abstract` nesta fase), então pode ser instanciada direto.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Título vazio deve ser rejeitado | título `""`, duração 125 | Deve lançar `IllegalArgumentException` |
| 2 | Título nulo deve ser rejeitado | título `null`, duração 125 | Deve lançar `IllegalArgumentException` |
| 3 | Duração zero deve ser rejeitada | título válido, duração 0 | Deve lançar `IllegalArgumentException` |
| 4 | Dados válidos criam o conteúdo sem nenhuma reprodução | título `"Faixa bônus"`, duração 125 | Id maior que zero, getters corretos, `getReproducoes()` == 0 |
| 5 | setTitulo com valor inválido lança exceção e mantém o título anterior | `setTitulo("   ")` | Deve lançar `IllegalArgumentException`; título continua `"Faixa bônus"` |
| 6 | setDuracaoSegundos negativo lança exceção e mantém a duração anterior | `setDuracaoSegundos(-1)` | Deve lançar `IllegalArgumentException`; duração continua 125 |
| 7 | toString mostra id, título e duração em segundos | `toString()` | `"[id] Faixa bônus (125s)"` |
| 8 | reproduzir() soma uma reprodução a cada chamada | Duas chamadas de `reproduzir()` | `getReproducoes()` == 2 |
| 9 | getDuracaoFormatada fica na superclasse e formata mm:ss | `getDuracaoFormatada()` | `"02:05"` |

## PL15 – `Musica` como subclasse de `Conteudo`

Classe de teste: `MusicaTest` · Cenário base: `bohemian` = Bohemian Rhapsody, 355 s, Queen, álbum "A Night at the Opera".

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Álbum vazio deve ser rejeitado | álbum `""` | Deve lançar `IllegalArgumentException` |
| 2 | Álbum nulo deve ser rejeitado | álbum `null` | Deve lançar `IllegalArgumentException` |
| 3 | Artista nulo deve ser rejeitado | artista `null` | Deve lançar `IllegalArgumentException` |
| 4 | Dados válidos guardam artista e álbum | `bohemian` | `getArtista()` == `"Queen"`, `getAlbum()` == `"A Night at the Opera"` |
| 5 | toString reaproveita a parte comum de Conteudo e acrescenta artista e álbum | `bohemian.toString()` | `"[id] Bohemian Rhapsody (355s) - Queen (A Night at the Opera)"` |
| 6 | setAlbum com valor inválido lança exceção e mantém o álbum anterior | `setAlbum("  ")` | Deve lançar `IllegalArgumentException`; álbum não muda |
| 7 | Setters herdados de Conteudo funcionam na música | `setTitulo(...)` e `setDuracaoSegundos(354)` | Novo título, 354 s e `"05:54"` |

## PL16 – `Podcast` como subclasse de `Conteudo`

Classe de teste: `PodcastTest` · Cenário base: `podcast` = "Café com Código", 2700 s, apresentadora Ana Souza, episódio 42.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Episódio zero deve ser rejeitado | episódio 0 | Deve lançar `IllegalArgumentException` |
| 2 | Episódio negativo deve ser rejeitado | episódio -3 | Deve lançar `IllegalArgumentException` |
| 3 | Episódio 1 é o menor valor aceito | episódio 1 | Objeto criado; `getNumeroEpisodio()` == 1 |
| 4 | Apresentador vazio deve ser rejeitado | apresentador `""` | Deve lançar `IllegalArgumentException` |
| 5 | Apresentador nulo deve ser rejeitado | apresentador `null` | Deve lançar `IllegalArgumentException` |
| 6 | A validação de título herdada de Conteudo vale para o podcast | título `""` | Deve lançar `IllegalArgumentException` |
| 7 | A validação de duração herdada de Conteudo vale para o podcast | duração 0 | Deve lançar `IllegalArgumentException` |
| 8 | Dados válidos criam o podcast com as partes herdada e própria | `podcast` | Id maior que zero; título, duração, apresentador e episódio corretos |
| 9 | toString reaproveita a parte comum de Conteudo e acrescenta episódio e apresentador | `podcast.toString()` | `"[id] Café com Código (2700s) - Ep. 42, com Ana Souza"` |
| 10 | reproduzir() herdado de Conteudo conta as reproduções do podcast | Uma chamada de `reproduzir()` | `getReproducoes()` == 1 |
| 11 | setNumeroEpisodio(0) lança exceção e mantém o episódio anterior | `setNumeroEpisodio(0)` | Deve lançar `IllegalArgumentException`; episódio continua 42 |
| 12 | Música e podcast compartilham o mesmo contador de id | Música, depois podcast, depois música | Ids consecutivos: `id`, `id + 1`, `id + 2` |

## PL17 – Podcasts na `Plataforma`

Classe de teste: `PlataformaTest` · Mesmo cenário base do PL06.

| Caso | Descrição | Entrada | Saída esperada |
|---|---|---|---|
| 1 | Podcast nulo não é cadastrado (retorna false) | `cadastrarPodcast(null)` | Retorna `false` |
| 2 | Podcast válido é cadastrado e passa a ser encontrado pelo id | `cadastrarPodcast(podcast)` | Retorna `true`; `buscarPodcast(id)` devolve o podcast |
| 3 | O mesmo podcast não é cadastrado duas vezes | `cadastrarPodcast(podcast)` de novo | Retorna `false` |
| 4 | Excluir podcast tira da plataforma; excluir de novo devolve false | `excluirPodcast(id)` duas vezes | 1ª retorna `true` e a busca passa a devolver `null`; 2ª retorna `false` |
| 5 | Podcasts e músicas ficam em listas separadas | Podcast cadastrado | `buscarMusica(podcast.getId())` e `buscarPodcast(bohemian.getId())` devolvem `null`; total de músicas continua 2 |
| 6 | A listagem de podcasts usa o toString do Podcast | `getTodosPodcasts()` | O texto contém `podcast.toString()` |
