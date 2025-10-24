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

    static void comb(int[] v) {
        int n = v.length;
        int gap = n;
        int trocou = 1;
        while (gap > 1 || trocou == 1) {
            gap = (gap * 10) / 13;
            if (gap < 1) gap = 1;
            trocou = 0;
            for (int i = 0; i + gap < n; i++) {
                comparacoes++;
                if (v[i] > v[i + gap]) {
                    trocar(v, i, i + gap);
                    trocou = 1;
                }
            }
        }
    }

    static void gnome(int[] v) {
        int i = 1;
        int n = v.length;
        while (i < n) {
            comparacoes++;
            if (v[i] >= v[i - 1]) {
                i++;
            } else {
                trocar(v, i, i - 1);
                i--;
                if (i == 0) i = 1;
            }
        }
    }

    static void bucket(int[] v) {
        int n = v.length;
        if (n == 0) return;

        int min = v[0];
        int max = v[0];
        for (int i = 1; i < n; i++) {
            if (v[i] < min) min = v[i];
            if (v[i] > max) max = v[i];
        }
        if (min == max) return;

        int qtd = 10;
        int[][] B = new int[qtd][n];
        int[] tam = new int[qtd];
        for (int i = 0; i < qtd; i++) tam[i] = 0;

        for (int i = 0; i < n; i++) {
            int idx = ( (v[i] - min) * (qtd - 1) ) / (max - min);
            B[idx][tam[idx]] = v[i];
            tam[idx] = tam[idx] + 1;
        }

        for (int b = 0; b < qtd; b++) {
            for (int i = 1; i < tam[b]; i++) {
                int chave = B[b][i];
                int j = i - 1;
                while (j >= 0) {
                    comparacoes++;
                    if (B[b][j] > chave) {
                        B[b][j + 1] = B[b][j];
                        trocas++;
                        j--;
                    } else {
                        break;
                    }
                }
                B[b][j + 1] = chave;
            }
        }

        int k = 0;
        for (int b = 0; b < qtd; b++) {
            for (int i = 0; i < tam[b]; i++) {
                v[k] = B[b][i];
                k++;
            }
        }
    }

    static void executar(String nome, int[] original, int tipo) {
        int[] vetor = new int[original.length];
        for (int i = 0; i < original.length; i++) vetor[i] = original[i];

        zera();

        if (tipo == 1) bubbleFlag(vetor);
        if (tipo == 2) selection(vetor);
        if (tipo == 3) cocktail(vetor);
        if (tipo == 4) comb(vetor);
        if (tipo == 5) gnome(vetor);
        if (tipo == 6) bucket(vetor);
        

        System.out.print(nome + " | comps=" + comparacoes + " trocas=" + trocas + "\n");
    }


    static void rodar(String titulo, int[] vetor) {
        System.out.println("\n" + titulo );
        executar("BubbleFlag", vetor, 1);
        executar("Selection", vetor, 2);
        executar("Cocktail", vetor, 3);
        executar("Comb", vetor, 4);
        executar("Gnome", vetor, 5);
        executar("Bucket", vetor, 6);
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
