public class Main {

    static long comparacoes = 0;
    static long trocas = 0;

    static void trocar(int[] v, int i, int j) {
        if (i == j) return;
        int temp = v[i];
        v[i] = v[j];
        v[j] = temp;
        trocas++;
    }

    static void zera() {
        comparacoes = 0;
        trocas = 0;
    }

    static void bubbleFlag(int[] v) {
        int trocou = 1;
        for (int i = 0; i < v.length - 1 && trocou == 1; i++) {
            trocou = 0;
            for (int j = 0; j < v.length - 1 - i; j++) {
                comparacoes++;
                if (v[j] > v[j + 1]) {
                    trocar(v, j, j + 1);
                    trocou = 1;
                }
            }
        }
    }

    static void selection(int[] v) {
        for (int i = 0; i < v.length - 1; i++) {
            int posMenor = i;
            for (int j = i + 1; j < v.length; j++) {
                comparacoes++;
                if (v[j] < v[posMenor]) posMenor = j;
            }
            trocar(v, i, posMenor);
        }
    }

    static void cocktail(int[] v) {
        int trocou = 1;
        int inicio = 0;
        int fim = v.length - 1;
        while (trocou == 1) {
            trocou = 0;
            for (int i = inicio; i < fim; i++) {
                comparacoes++;
                if (v[i] > v[i + 1]) {
                    trocar(v, i, i + 1);
                    trocou = 1;
                }
            }
            if (trocou == 0) break;
            trocou = 0;
            fim--;
            for (int i = fim; i > inicio; i--) {
                comparacoes++;
                if (v[i - 1] > v[i]) {
                    trocar(v, i - 1, i);
                    trocou = 1;
                }
            }
            inicio++;
        }
    }


    static void executar(String nome, int[] original, int tipo) {
        int[] v = new int[original.length];
        for (int i = 0; i < original.length; i++) v[i] = original[i];

        zera();

        if (tipo == 1) bubbleFlag(v);
        if (tipo == 2) selection(v);
        if (tipo == 3) cocktail(v);

        System.out.print(nome + " | comps=" + comparacoes + " trocas=" + trocas + "\n");
    }


    static void rodar(String titulo, int[] vetor) {
        System.out.println("\n" + titulo );
        executar("BubbleFlag", vetor, 1);
        executar("Selection",  vetor, 2);
        executar("Cocktail",   vetor, 3);

    }

    public static void main(String[] args) {
        int[] vetor1= {12, 18, 9, 25, 17, 31, 22, 27, 16, 13, 19, 23, 20, 30, 14, 11, 15, 24, 26, 28};
        int[] vetor2 = {5, 7, 9, 10, 12, 14, 15, 17, 19, 21, 22, 23, 24, 25, 27, 28, 29, 30, 31, 32};
        int[] vetor3 = {99, 85, 73, 60, 50, 40, 35, 30, 25, 20, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6};

        rodar("aleatório", vetor1);
        rodar("ordenado", vetor2);
        rodar("decrescente", vetor3);
    }
}
