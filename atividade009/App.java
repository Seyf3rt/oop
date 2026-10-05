import java.util.Scanner;

class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Plataforma plataforma = new Plataforma();

        boolean fim = false;

        do {
            System.out.println("\n=== Sonora ===\n");
            System.out.println(""
                    + "1 - Música(s) \n"
                    + "2 - Playlist(s) \n"
                    + "3 - Usuário(s)\n"
                    + "4 - Podcast(s)\n"
                    + "5 - Sair\n");

            int opcao = lerInt(sc, "Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    System.out.println(""
                            + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                            + "[MÚSICA(S)]\n"
                            + "1 - Criar\n"
                            + "2 - Listar\n"
                            + "3 - Deletar\n"
                            + "4 - Buscar\n"
                            + "5 - Reproduzir\n"
                            + "6 - Voltar\n");

                    switch (lerInt(sc, "Opcao: ")) {
                        case 1: {
                            String titulo = lerTexto(sc, "Digite o título da música: ");
                            String artista = lerTexto(sc, "Digite o artista: ");
                            String album = lerTexto(sc, "Digite o álbum: ");
                            int duracao = lerInt(sc, "Digite a duração (segundos): ");

                            try {
                                Musica musica = new Musica(titulo, duracao, artista, album);
                                plataforma.cadastrarMusica(musica);
                                System.out.println("Musica cadastrada!");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Nao foi possivel cadastrar: " + e.getMessage());
                            }
                            break;
                        }
                        case 2:
                            System.out.println(plataforma.getTodasMusicas());
                            break;
                        case 3: {
                            System.out.println(plataforma.getTodasMusicas());
                            int id = lerInt(sc, "\nDigite o id da música a deletar: ");
                            if (plataforma.excluirMusica(id)) {
                                System.out.println("Musica excluida.");
                            } else {
                                System.out.println("Musica nao encontrada.");
                            }
                            break;
                        }
                        case 4: {
                            System.out.println("Buscar por:\n"
                                    + "1 - Id\n"
                                    + "2 - Título\n");

                            Musica encontrada = null;
                            switch (lerInt(sc, "Opcao: ")) {
                                case 1:
                                    encontrada = plataforma.buscarMusica(lerInt(sc, "Digite o id: "));
                                    break;
                                case 2:
                                    encontrada = plataforma.buscarMusica(lerTexto(sc, "Digite o título: "));
                                    break;
                                default:
                                    System.out.println("Opção invalida");
                                    break;
                            }

                            if (encontrada == null) {
                                System.out.println("Nenhuma música encontrada.");
                            } else {
                                System.out.println(encontrada);
                            }
                            break;
                        }
                        case 5: {
                            try {
                                int id = Integer.parseInt(lerTexto(sc, "Digite o id: ").trim());
                                Musica m = plataforma.buscarMusica(id);
                                if (m == null) {
                                    System.out.println("Essa música não existe.");
                                } else {
                                    m.reproduzir();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("O id precisa ser um numero.");
                            } finally {
                                System.out.println("--- operação finalizada ---");
                            }
                            break;
                        }
                        case 6:
                            continue;
                        default:
                            System.out.println("Valor incorreto!");
                            break;
                    }

                    break;

                case 2:

                    System.out.println(""
                            + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                            + "[PLAYLIST]\n"
                            + "1 - Criar\n"
                            + "2 - Add música\n"
                            + "3 - Listar\n"
                            + "4 - Deletar música\n"
                            + "5 - Deletar playlist\n"
                            + "6 - Reproduzir playlist inteira\n"
                            + "7 - Reproduzir música (por posição)\n"
                            + "8 - Buscar\n"
                            + "9 - Voltar\n");
                    switch (lerInt(sc, "Opcao: ")) {
                        case 1: {
                            System.out.println("Selecione o proprietário da playlist:\n");
                            System.out.println(plataforma.getInfoUsuarios());

                            int id = lerInt(sc, "Digite o dono (id): ");
                            String nome = lerTexto(sc, "Digite o nome da playlist: ");

                            try {
                                if (plataforma.cadastrarPlaylist(nome, id)) {
                                    System.out.println("Playlist criada com sucesso!");
                                } else {
                                    System.out.println("Não foi possível criar a playlist..");
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Não foi possível criar a playlist: " + e.getMessage());
                            }
                            break;
                        }
                        case 2: {
                            System.out.println(plataforma.getTodasPlaylists());
                            int idPlaylist = lerInt(sc, "\nDigite o ID da playlist: ");

                            System.out.println(plataforma.getTodasMusicas());
                            int idMusica = lerInt(sc, "\nDigite o ID da música a ser adicionada: ");

                            try {
                                plataforma.addMusicaPlaylist(idPlaylist, idMusica);
                                System.out.println("Música adicionada à playlist.");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Não foi possível adicionar: " + e.getMessage());
                            }
                            break;
                        }
                        case 3:
                            System.out.println(plataforma.getTodasPlaylists());
                            break;
                        case 4: {
                            System.out.println(plataforma.getTodasPlaylists());
                            int idPlaylist = lerInt(sc, "\nDigite o ID da playlist: ");

                            try {
                                System.out.println(plataforma.getTodasMusicasPlaylist(idPlaylist));
                                int idMusica = lerInt(sc, "\nDigite o id da música a ser deletada: ");

                                if (plataforma.excluirMusicaPlaylist(idPlaylist, idMusica)) {
                                    System.out.println("Música removida da playlist.");
                                } else {
                                    System.out.println("Essa música não está na playlist.");
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Não foi possível remover: " + e.getMessage());
                            }
                            break;
                        }
                        case 5: {
                            System.out.println(plataforma.getTodasPlaylists());
                            int idPlaylist = lerInt(sc, "\nDigite o id da playlist a ser excluida: ");

                            if (plataforma.excluirPlaylist(idPlaylist)) {
                                System.out.println("Playlist excluida.");
                            } else {
                                System.out.println("Playlist não encontrada.");
                            }
                            break;
                        }
                        case 6: {
                            System.out.println(plataforma.getTodasPlaylists());
                            int idPlaylist = lerInt(sc, "\nDigite o id da playlist a ser tocada: ");

                            try {
                                plataforma.tocarPlaylist(idPlaylist);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Não foi possível tocar: " + e.getMessage());
                            }
                            break;
                        }
                        case 7: {

                            System.out.println(plataforma.getTodasPlaylists());
                            try {
                                int idPlaylist = Integer.parseInt(lerTexto(sc, "Id da playlist: ").trim());
                                int pos = Integer.parseInt(lerTexto(sc, "Posição da música (começa em 0): ").trim());
                                Musica m = plataforma.getMusicaPlaylist(idPlaylist, pos);
                                m.reproduzir();
                            } catch (NumberFormatException e) {
                                System.out.println("A posição precisa ser um número.");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Essa posição não existe na playlist. " + e.getMessage());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Não foi possível reproduzir: " + e.getMessage());
                            } finally {
                                System.out.println("--- reprodução finalizada ---");
                            }
                            break;
                        }
                        case 8: {
                            int idPlaylist = lerInt(sc, "Digite o id da playlist a pesquisar: ");
                            try {
                                System.out.println(plataforma.getInfoPlaylist(idPlaylist));
                                System.out.println(plataforma.getTodasMusicasPlaylist(idPlaylist));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Playlist não encontrada.");
                            }
                            break;
                        }
                        case 9:
                            continue;
                        default:
                            System.out.println("Opção invalida");
                            break;
                    }
                    break;

                case 3:
                    System.out.println(""
                            + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                            + "[USUÁRIO(S)]\n"
                            + "1  - Criar\n"
                            + "2  - Listar\n"
                            + "3  - Deletar\n"
                            + "4  - Buscar\n"
                            + "5  - Seguir usuário\n"
                            + "6  - Deixar de seguir usuário\n"
                            + "7  - Listar quem um usuário segue\n"
                            + "8  - Listar os seguidores de um usuário\n"
                            + "9  - Trocar de plano\n"
                            + "10 - Verificar plano\n"
                            + "11 - Voltar\n");

                    switch (lerInt(sc, "Opcao: ")) {
                        case 1: {
                            String nome = lerTexto(sc, "Digite o nome: ");
                            String email = lerTexto(sc, "Digite o email: ");

                            try {
                                Usuario usuario = new Usuario(nome, email);
                                plataforma.cadastrarUsuario(usuario);
                                System.out.println("Usuario cadastrado!");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Nao foi possivel cadastrar: " + e.getMessage());
                            }
                            break;
                        }
                        case 2:
                            System.out.println(plataforma.getInfoUsuarios());
                            break;
                        case 3: {
                            System.out.println(plataforma.getInfoUsuarios());
                            int id = lerInt(sc, "\nDigite o id do usuário a ser excluido: ");

                            if (plataforma.excluirUsuario(id)) {
                                System.out.println("Usuario excluido.");
                            } else {
                                System.out.println("Usuario não encontrado.");
                            }
                            break;
                        }
                        case 4: {
                            int id = lerInt(sc, "Digite o id do usuário a ser buscado: ");
                            try {
                                System.out.println(plataforma.getInfoUsuario(id));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Usuario não encontrado.");
                            }
                            break;
                        }
                        case 5: {
                            System.out.println(plataforma.getInfoUsuarios());
                            int idSeguidor = lerInt(sc, "\nDigite o id de quem vai seguir: ");
                            int idSeguido = lerInt(sc, "Digite o id de quem será seguido: ");

                            try {
                                plataforma.seguirUsuario(idSeguidor, idSeguido);
                                System.out.println("Pronto, agora está seguindo!");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Não foi possível seguir: " + e.getMessage());
                            } catch (IllegalStateException e) {
                                System.out.println("Nada a fazer: " + e.getMessage());
                            }
                            break;
                        }
                        case 6: {
                            System.out.println(plataforma.getInfoUsuarios());
                            int idSeguidor = lerInt(sc, "\nDigite o id de quem vai deixar de seguir: ");
                            int idSeguido = lerInt(sc, "Digite o id de quem será deixado de seguir: ");

                            try {
                                plataforma.deixarDeSeguirUsuario(idSeguidor, idSeguido);
                                System.out.println("Pronto, deixou de seguir.");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Não foi possível deixar de seguir: " + e.getMessage());
                            } catch (IllegalStateException e) {
                                System.out.println("Nada a fazer: " + e.getMessage());
                            }
                            break;
                        }
                        case 7: {
                            int id = lerInt(sc, "Digite o id do usuário: ");
                            try {
                                System.out.println(plataforma.getSeguindo(id));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Usuario não encontrado.");
                            }
                            break;
                        }
                        case 8: {
                            int id = lerInt(sc, "Digite o id do usuário: ");
                            try {
                                System.out.println(plataforma.getSeguidores(id));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Usuario não encontrado.");
                            }
                            break;
                        }
                        case 9: {
                            int id = lerInt(sc, "Digite o id do usuário: ");

                            try {
                                Usuario usuario = plataforma.buscarUsuario(id);
                                String texto = "O plano atualmente é: " + usuario.getPlano().getNome()
                                        + "\n\nDeseja alterar para:\n1-Individual\n2-Familia\n3-Gratuito\n";
                                int valor = lerInt(sc, texto);

                                if (valor == 1 && !(usuario.getPlano() instanceof PlanoIndividual)) {
                                    PlanoIndividual plano = new PlanoIndividual(90);
                                    usuario.assinar(plano);
                                } else if (valor == 2 && !(usuario.getPlano() instanceof PlanoFamilia)) {
                                    System.out.println("Digite a quantidade de membros:");

                                    PlanoFamilia plano = new PlanoFamilia(120, sc.nextInt());
                                    usuario.assinar(plano);
                                } else if (valor == 3 && !(usuario.getPlano() instanceof PlanoGratuito)) {

                                    PlanoGratuito plano = new PlanoGratuito();
                                    usuario.assinar(plano);
                                } else {
                                    System.out.println("Você ja tem este plano!");
                                }

                            } catch (IllegalArgumentException e) {
                                System.out.println("Não foi possível assinar um plano");
                            }

                            break;
                        }
                        case 10:
                            int id = lerInt(sc, "Digite o id do usuário: ");
                            Usuario usuario = plataforma.buscarUsuario(id);

                            System.out.println("O plano atualmente é: " + usuario.getPlano().getNome());
                            break;
                        case 11:
                            continue;
                        default:
                            System.out.println("Opção invalida");
                            break;
                    }
                    break;
                case 4:
                    System.out.println(""
                            + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                            + "[PODCAST(S)]\n"
                            + "1 - Criar\n"
                            + "2 - Listar\n"
                            + "3 - Deletar\n"
                            + "4 - Buscar\n"
                            + "5 - Reproduzir\n"
                            + "6 - Voltar\n");

                    switch (lerInt(sc, "Opcao: ")) {
                        case 1: {
                            String titulo = lerTexto(sc, "Digite o título do podcast: ");
                            String apresentador = lerTexto(sc, "Digite o apresentador: ");
                            int duracao = lerInt(sc, "Digite a duração (segundos): ");
                            int numeroEpisodio = lerInt(sc, "Digite o número do episódio: ");

                            try {
                                Podcast podcast = new Podcast(titulo, duracao, apresentador, numeroEpisodio);
                                plataforma.cadastrarPodcast(podcast);
                                System.out.println("Podcast cadastrado!");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Nao foi possivel cadastrar: " + e.getMessage());
                            }
                            break;
                        }
                        case 2:
                            System.out.println(plataforma.getTodosPodcasts());
                            break;
                        case 3: {
                            System.out.println(plataforma.getTodosPodcasts());
                            int id = lerInt(sc, "\nDigite o id do podcast a deletar: ");
                            if (plataforma.excluirPodcast(id)) {
                                System.out.println("Podcast excluido.");
                            } else {
                                System.out.println("Podcast nao encontrado.");
                            }
                            break;
                        }
                        case 4: {
                            Podcast encontrado = plataforma.buscarPodcast(lerInt(sc, "Digite o id: "));
                            if (encontrado == null) {
                                System.out.println("Nenhum podcast encontrado.");
                            } else {
                                System.out.println(encontrado);
                            }
                            break;
                        }
                        case 5: {
                            Podcast podcast = plataforma.buscarPodcast(lerInt(sc, "Digite o id: "));
                            if (podcast == null) {
                                System.out.println("Esse podcast não existe.");
                            } else {
                                podcast.reproduzir();
                            }
                            break;
                        }
                        case 6:
                            continue;
                        default:
                            System.out.println("Valor incorreto!");
                            break;
                    }
                    break;
                case 5:
                    fim = true;
                    break;
                default:
                    System.out.println("Opção invalida");
                    break;
            }

        } while (!fim);

        sc.close();
    }

    static int lerInt(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Digite um numero.");
            }
        }
    }

    static String lerTexto(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
