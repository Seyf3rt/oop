# -*- coding: utf-8 -*-
"""Gera docs/diagrama-classes.svg: classes do Sonora, a hierarquia de herança
(Conteudo -> Musica, Podcast) e as associações com os quatro adornos (papel,
nome com direção, multiplicidade e navegabilidade)."""

LINHA = 30
CAB = 60
PAD = 18
FS = 19

def esc(t):
    return t.replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;')

class Caixa:
    def __init__(self, nome, x, y, w, atributos, metodos):
        self.nome, self.x, self.y, self.w = nome, x, y, w
        self.atributos, self.metodos = atributos, metodos
        self.h = CAB + PAD * 2 + LINHA * len(atributos) + PAD * 2 + LINHA * len(metodos)

    @property
    def dir(self): return self.x + self.w
    @property
    def baixo(self): return self.y + self.h
    @property
    def cx(self): return self.x + self.w / 2
    @property
    def cy(self): return self.y + self.h / 2

    def svg(self):
        y1 = self.y + CAB
        y2 = y1 + PAD * 2 + LINHA * len(self.atributos)
        p = [f'<rect x="{self.x}" y="{self.y}" width="{self.w}" height="{self.h}" '
             f'fill="#ffffff" stroke="#111111" stroke-width="3"/>',
             f'<line x1="{self.x}" y1="{y1}" x2="{self.dir}" y2="{y1}" stroke="#111111" stroke-width="3"/>',
             f'<line x1="{self.x}" y1="{y2}" x2="{self.dir}" y2="{y2}" stroke="#111111" stroke-width="3"/>',
             f'<text x="{self.cx}" y="{self.y + 40}" text-anchor="middle" font-size="27" '
             f'font-weight="bold" font-family="DejaVu Sans, Arial, sans-serif">{esc(self.nome)}</text>']

        def bloco(itens, topo):
            saida = []
            for i, item in enumerate(itens):
                texto, estatico = (item, False) if isinstance(item, str) else item
                dec = ' text-decoration="underline"' if estatico else ''
                saida.append(
                    f'<text x="{self.x + 22}" y="{topo + PAD + LINHA * i + 21}" font-size="{FS}" '
                    f'font-family="DejaVu Sans, Arial, sans-serif" xml:space="preserve"{dec}>{esc(texto)}</text>')
            return saida

        p += bloco(self.atributos, y1)
        p += bloco(self.metodos, y2)
        return '\n'.join(p)


# ------------------------------------------------------------------
# Hierarquia: Conteudo (superclasse) -> Musica e Podcast (subclasses)
# ------------------------------------------------------------------

musica = Caixa('Musica', 1040, 1000, 580, [
    '- artista: String',
    '- album: String',
], [
    '+ Musica(titulo: String, duracaoSegundos: int,',
    '             artista: String, album: String)',
    '+ getArtista(): String',
    '+ setArtista(artista: String): void',
    '+ getAlbum(): String',
    '+ setAlbum(album: String): void',
    '+ toString(): String',
])

podcast = Caixa('Podcast', 1780, 1000, 640, [
    '- apresentador: String',
    '- numeroEpisodio: int',
], [
    '+ Podcast(titulo: String, duracaoSegundos: int,',
    '             apresentador: String, numeroEpisodio: int)',
    '+ getApresentador(): String',
    '+ setApresentador(apresentador: String): void',
    '+ getNumeroEpisodio(): int',
    '+ setNumeroEpisodio(numeroEpisodio: int): void',
    '+ toString(): String',
])

CONTEUDO_W = 680
conteudo = Caixa('Conteudo', (musica.cx + podcast.cx) / 2 - CONTEUDO_W / 2, 220, CONTEUDO_W, [
    ('- contagem: int', True),
    '- id: int',
    '- titulo: String',
    '- duracaoSegundos: int',
    '- reproducoes: int',
], [
    '+ Conteudo(titulo: String, duracaoSegundos: int)',
    '+ getId(): int',
    '# setId(id: int): void',
    '+ getTitulo(): String',
    '+ setTitulo(titulo: String): void',
    '+ getDuracaoSegundos(): int',
    '+ setDuracaoSegundos(duracaoSegundos: int): void',
    '+ getReproducoes(): int',
    '+ getDuracaoFormatada(): String',
    '+ reproduzir(): void',
    '+ toString(): String',
    ('+ getContagem(): int', True),
])

# ------------------------------------------------------------------
# Demais classes (associações da fase anterior)
# ------------------------------------------------------------------

playlist = Caixa('Playlist', 1040, 1560, 560, [
    ('- contagem: int', True),
    '- id: int',
    '- titulo: String',
    '- dono: Usuario',
    '- musicas: ArrayList<Musica>',
], [
    '+ Playlist(titulo: String, dono: Usuario)',
    '+ getId(): int',
    '+ getTitulo(): String',
    '+ getDono(): Usuario',
    '+ getQuantidade(): int',
    '+ adicionar(musica: Musica): boolean',
    '+ getNaPosicao(indice: int): Musica',
    '+ removerNaPosicao(indice: int): boolean',
    '+ posicaoDe(idMusica: int): int',
    '+ getDuracaoSegundos(): int',
    '+ getDuracaoFormatada(): String',
    '+ getTodasMusicas(): String',
    '+ reproduzirTudo(): void',
    '+ informacoes(): String',
    ('+ getContagem(): int', True),
])

usuario = Caixa('Usuario', 1840, 1560, 600, [
    ('- contagem: int', True),
    '- id: int',
    '- nome: String',
    '- email: String',
    '- seguindo: ArrayList<Usuario>',
], [
    '+ Usuario(nome: String, email: String)',
    '+ getId(): int',
    '+ getNome(): String',
    '+ getEmail(): String',
    '+ seguir(outro: Usuario): void',
    '+ deixarDeSeguir(outro: Usuario): void',
    '+ getQuantidadeSeguindo(): int',
    '+ segue(outro: Usuario): boolean',
    '+ getSeguindo(): ArrayList<Usuario>',
    '+ informacoes(): String',
    ('+ getContagem(): int', True),
])

plataforma = Caixa('Plataforma', 60, 220, 740, [
    '- musicas: ArrayList<Musica>',
    '- podcasts: ArrayList<Podcast>',
    '- usuarios: ArrayList<Usuario>',
    '- playlists: ArrayList<Playlist>',
], [
    '+ cadastrarMusica(musica: Musica): boolean',
    '+ getTotalMusicas(): int',
    '+ getTodasMusicas(): String',
    '+ buscarMusica(id: int): Musica',
    '+ buscarMusica(titulo: String): Musica',
    '+ excluirMusica(idMusica: int): boolean',
    '+ cadastrarPodcast(podcast: Podcast): boolean',
    '+ getTodosPodcasts(): String',
    '+ buscarPodcast(id: int): Podcast',
    '+ excluirPodcast(idPodcast: int): boolean',
    '+ cadastrarUsuario(usuario: Usuario): boolean',
    '+ getTotalUsuarios(): int',
    '+ buscarUsuario(id: int): Usuario',
    '+ getInfoUsuarios(): String',
    '+ getInfoUsuario(id: int): String',
    '+ excluirUsuario(id: int): boolean',
    '+ seguirUsuario(idSeguidor: int, idSeguido: int): void',
    '+ deixarDeSeguirUsuario(idSeguidor: int, idSeguido: int): void',
    '+ getSeguindo(id: int): String',
    '+ getSeguidores(id: int): String',
    '+ cadastrarPlaylist(nome: String, idDono: int): boolean',
    '+ getTotalPlaylists(): int',
    '+ buscarPlaylist(id: int): Playlist',
    '+ getInfoPlaylist(id: int): String',
    '+ getTodasPlaylists(): String',
    '+ getTodasMusicasPlaylist(idPlaylist: int): String',
    '+ addMusicaPlaylist(idPlaylist: int, idMusica: int): boolean',
    '+ excluirMusicaPlaylist(idPlaylist: int, idMusica: int): boolean',
    '+ excluirPlaylist(idPlaylist: int): boolean',
    '+ getMusicaPlaylist(idPlaylist: int, posicao: int): Musica',
    '+ tocarPlaylist(id: int): void',
    '- exigirUsuario(id: int): Usuario',
    '- exigirPlaylist(id: int): Playlist',
])

caixas = [plataforma, conteudo, musica, podcast, playlist, usuario]

partes = []

def linha(pontos, cor='#111111'):
    d = ' '.join(f'{x},{y}' for x, y in pontos)
    partes.append(f'<polyline points="{d}" fill="none" stroke="{cor}" stroke-width="3"/>')

def seta(ponta, direcao, cor='#111111'):
    """Seta ABERTA da UML: navegabilidade naquela direção."""
    x, y = ponta
    t = 20
    if direcao == 'direita':
        pts = [(x - t, y - t * 0.62), (x, y), (x - t, y + t * 0.62)]
    elif direcao == 'esquerda':
        pts = [(x + t, y - t * 0.62), (x, y), (x + t, y + t * 0.62)]
    elif direcao == 'cima':
        pts = [(x - t * 0.62, y + t), (x, y), (x + t * 0.62, y + t)]
    else:
        pts = [(x - t * 0.62, y - t), (x, y), (x + t * 0.62, y - t)]
    d = ' '.join(f'{px},{py}' for px, py in pts)
    partes.append(f'<polyline points="{d}" fill="none" stroke="{cor}" stroke-width="3" '
                  f'stroke-linecap="round" stroke-linejoin="round"/>')

def triangulo_heranca(ponta):
    """Triângulo VAZADO da UML: especialização, com a ponta na superclasse."""
    x, y = ponta
    alt, meia = 30, 18
    d = f'{x},{y} {x - meia},{y + alt} {x + meia},{y + alt}'
    partes.append(f'<polygon points="{d}" fill="#ffffff" stroke="#111111" stroke-width="3" '
                  f'stroke-linejoin="round"/>')
    return y + alt

def texto(x, y, t, anchor='middle', tam=19, peso='normal', estilo='normal', cor='#111111'):
    partes.append(f'<text x="{x}" y="{y}" text-anchor="{anchor}" font-size="{tam}" '
                  f'font-weight="{peso}" font-style="{estilo}" fill="{cor}" '
                  f'font-family="DejaVu Sans, Arial, sans-serif">{esc(t)}</text>')

def nome_assoc(x, y, t, anchor='middle'):
    texto(x, y, t, anchor=anchor, tam=21, estilo='italic')

# ------------------------------------------------------------------
# Herança: Musica e Podcast especializam Conteudo
#   uma única ponta de triângulo na superclasse, e a árvore desce até
#   cada subclasse ("Musica é um Conteudo", "Podcast é um Conteudo")
# ------------------------------------------------------------------
BARRA = 945
base = triangulo_heranca((conteudo.cx, conteudo.baixo))
linha([(conteudo.cx, base), (conteudo.cx, BARRA)])
linha([(musica.cx, musica.y), (musica.cx, BARRA), (podcast.cx, BARRA), (podcast.cx, podcast.y)])
texto(conteudo.cx + 16, BARRA - 18, 'é um(a)', anchor='start', tam=20, estilo='italic')

# ------------------------------------------------------------------
# 1. Plataforma cadastra ▶ Musica   (1 para 0..*, unidirecional)
# ------------------------------------------------------------------
linha([(plataforma.dir, 1200), (musica.x, 1200)])
seta((musica.x, 1200), 'direita')
nome_assoc((plataforma.dir + musica.x) / 2, 1180, 'cadastra ▶')
texto(plataforma.dir + 14, 1240, '1', anchor='start')
texto(musica.x - 14, 1240, '0..*', anchor='end')
texto(musica.x - 14, 1266, '- acervo', anchor='end')

# ------------------------------------------------------------------
# 2. Plataforma cadastra ▶ Podcast  (1 para 0..*, unidirecional)
#    sai pelo topo da Plataforma e contorna a Conteudo por cima
# ------------------------------------------------------------------
linha([(740, plataforma.y), (740, 170), (2570, 170), (2570, 1200), (podcast.dir, 1200)])
seta((podcast.dir, 1200), 'esquerda')
nome_assoc(1655, 156, 'cadastra ▶')
texto(756, plataforma.y - 12, '1', anchor='start')
texto(podcast.dir + 16, 1180, '0..*', anchor='start')
texto(podcast.dir + 16, 1154, '- podcasts', anchor='start')

# ------------------------------------------------------------------
# 3. Plataforma registra ▶ Usuario  (1 para 0..*, unidirecional)
# ------------------------------------------------------------------
linha([(640, plataforma.y), (640, 120), (2690, 120), (2690, 2000), (usuario.dir, 2000)])
seta((usuario.dir, 2000), 'esquerda')
nome_assoc(1665, 106, 'registra ▶')
texto(624, plataforma.y - 12, '1', anchor='end')
texto(usuario.dir + 18, 1980, '0..*', anchor='start')
texto(usuario.dir + 18, 1954, '- usuarios', anchor='start')

# ------------------------------------------------------------------
# 4. Plataforma hospeda ▶ Playlist  (1 para 0..*, unidirecional)
# ------------------------------------------------------------------
linha([(430, plataforma.baixo), (430, 1800), (playlist.x, 1800)])
seta((playlist.x, 1800), 'direita')
nome_assoc(735, 1780, 'hospeda ▶')
texto(446, plataforma.baixo + 30, '1', anchor='start')
texto(playlist.x - 14, 1840, '0..*', anchor='end')
texto(playlist.x - 14, 1866, '- playlists', anchor='end')

# ------------------------------------------------------------------
# 5. Playlist contém ▶ Musica  (0..* para 0..*, unidirecional)
# ------------------------------------------------------------------
linha([(1260, playlist.y), (1260, musica.baixo)])
seta((1260, musica.baixo), 'cima')
nome_assoc(1282, (playlist.y + musica.baixo) / 2 + 8, 'contém ▲', anchor='start')
texto(1240, playlist.y - 16, '0..*', anchor='end')
texto(1240, musica.baixo + 34, '0..*', anchor='end')
texto(1240, musica.baixo + 60, '- faixas', anchor='end')

# ------------------------------------------------------------------
# 6. Usuario cria ▶ Playlist  (1 dono para 0..* playlists, unidirecional
#    Playlist -> Usuario: só a Playlist guarda o dono)
# ------------------------------------------------------------------
linha([(playlist.dir, 1900), (usuario.x, 1900)])
seta((usuario.x, 1900), 'direita')
nome_assoc((playlist.dir + usuario.x) / 2, 1880, '◀ cria')
texto(playlist.dir + 14, 1940, '0..*', anchor='start')
texto(playlist.dir + 14, 1966, '- playlists', anchor='start')
texto(usuario.x - 14, 1940, '1', anchor='end')
texto(usuario.x - 14, 1966, '- dono', anchor='end')

# ------------------------------------------------------------------
# 7. Usuario segue ▶ Usuario  (reflexiva, 0..* dos dois lados)
# ------------------------------------------------------------------
linha([(1950, usuario.y), (1950, 1490), (2330, 1490), (2330, usuario.y)])
seta((2330, usuario.y), 'baixo')
nome_assoc(2140, 1474, 'segue ▶')
texto(1934, usuario.y - 18, '0..*', anchor='end')
texto(1934, usuario.y - 44, '- seguidores', anchor='end')
texto(2346, usuario.y - 18, '0..*', anchor='start')
texto(2346, usuario.y - 44, '- seguindo', anchor='start')

# ------------------------------------------------------------------
# Quadros de texto
# ------------------------------------------------------------------
def quadro(x, y, w, titulo, linhas, coluna=0):
    """Cada linha é um texto ou um par (símbolo, descrição) alinhado em duas colunas."""
    h = 86 + 30 * len(linhas) + 10
    partes.append(f'<rect x="{x}" y="{y}" width="{w}" height="{h}" fill="#f6f6f6" '
                  f'stroke="#111111" stroke-width="3"/>')
    texto(x + 24, y + 44, titulo, anchor='start', tam=24, peso='bold')
    for i, l in enumerate(linhas):
        ly = y + 86 + i * 30
        if isinstance(l, tuple):
            texto(x + 24, ly, l[0], anchor='start', tam=18)
            texto(x + 24 + coluna, ly, l[1], anchor='start', tam=18)
        else:
            texto(x + 24, ly, l, anchor='start', tam=18)
    return y + h

fim_legenda = quadro(1660, 2250, 1060, 'Como ler o diagrama', [
    ('- papel', 'nome do lado da associação (ex.: - faixas)'),
    ('nome ▶', 'verbo e direção de leitura (ex.: Plataforma cadastra ▶ Musica)'),
    ('0..* / 1', 'multiplicidade em cada ponta'),
    ('——>', 'navegabilidade: a seta aberta marca o único lado navegável'),
    ('——▷', 'especialização (herança): o triângulo vazado fica na superclasse'),
    ('#', 'protected: visível na própria classe e nas subclasses (# setId)'),
    ('sublinhado', 'membro static (contagem: um contador só para todo Conteudo)'),
    '',
    'id, titulo, duracaoSegundos e reproducoes ficam só em Conteudo; cada subclasse',
    'guarda apenas o que é dela. reproduzir() existe só em Conteudo e é herdado;',
    'toString() aparece nas três porque Musica e Podcast o sobrescrevem (@Override)',
    'reaproveitando super.toString().',
], coluna=130)

fim_justificativas = quadro(60, 1940, 920, 'Por que estas multiplicidades', [
    'cadastra: a plataforma é uma só (1) e o acervo pode estar vazio',
    'ou ter quantas músicas forem cadastradas (0..*). Os podcasts',
    'têm a mesma leitura, numa lista própria (- podcasts).',
    'registra / hospeda: mesma leitura, para usuários e playlists.',
    'contém: uma playlist pode estar vazia e a mesma música pode',
    'aparecer em várias playlists, então 0..* nas duas pontas.',
    'cria: toda playlist tem exatamente 1 dono (o construtor recusa',
    'dono nulo) e um usuário pode ter 0..* playlists.',
    'segue: quem entra na plataforma não segue ninguém, e não há',
    'teto para quantos segue ou por quantos é seguido (0..* / 0..*).',
    'Herança não leva multiplicidade: não liga objetos, diz que',
    'toda Musica e todo Podcast é um Conteudo.',
])

texto(60, 58, 'Sonora · Fase 06 — diagrama de classes com herança', anchor='start', tam=30, peso='bold')
texto(60, 90, 'especialização · # protected · papel · nome com direção · multiplicidade · navegabilidade',
      anchor='start', tam=18, estilo='italic')

W = 2780
H = max(fim_legenda, fim_justificativas) + 40
svg = [f'<svg xmlns="http://www.w3.org/2000/svg" width="{W}" height="{H}" viewBox="0 0 {W} {H}">',
       f'<rect width="{W}" height="{H}" fill="#ffffff"/>']
svg += partes
svg += [c.svg() for c in caixas]
svg.append('</svg>')

open('docs/diagrama-classes.svg', 'w', encoding='utf-8').write('\n'.join(svg))
print(f'svg gerado ({W}x{H})')
for c in caixas:
    print(f'{c.nome}: x={c.x} y={c.y} w={c.w} h={c.h} baixo={c.baixo} dir={c.dir}')
