public interface Cronometravel {
    int getDuracaoSegundos();

    default String getDuracaoFormatada() {
        int total = getDuracaoSegundos();
        int minutos = total / 60;
        int segundos = total % 60;
        if (segundos < 10) {
            return minutos + ":0" + segundos;
        }
        return minutos + ":" + segundos;
    }
}