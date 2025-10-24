# Comparação com elgoritmos de comparação

## Como rodar
- Clone o repositório
- Abra com um editor de código que preferir
- Compile o Main.java
  ```java
  javac Main.java
  ```
- Rode o Main.java
    ```java
  java Main.java
  ```

## Explicação do Código

### Bubble Sort com flag de parada

```java

// Bubble Sort com flag 
static void bubbleFlag(int[] v) {
    int trocou = 1;                              // 1 = houve troca na última passada
    for (int i = 0; i < v.length - 1 && trocou == 1; i++) {
        trocou = 0;                              // zera a flag a cada passada
        for (int j = 0; j < v.length - 1 - i; j++) { // -i: fim já está “organizado”
            comparacoes++;                       // contamos a comparação
            if (v[j] > v[j + 1]) {               // do lado fora de ordem?
                trocar(v, j, j + 1);             // troca
                trocou = 1;                      // marcou que teve troca
            }
        }
        // se trocou ficar 0, não teve nenhuma troca -> já está ordenado
    }
}

```


### Selection Sort

```java
// Selection Sort (seleciona o menor e coloca na frente)
static void selection(int[] v) {
    for (int i = 0; i < v.length - 1; i++) {
        int posMenor = i;                       // assume que v[i] é o menor do resto
        for (int j = i + 1; j < v.length; j++) {
            comparacoes++;                      // conta a comparação abaixo
            if (v[j] < v[posMenor]) posMenor = j; // acha a posição do menor
        }
        trocar(v, i, posMenor);                 // coloca o menor na posição i
    }
}

```


### Cocktail Sort

```java
// Cocktail Sort -vai e volta
static void cocktail(int[] v) {
    int trocou = 1;
    int inicio = 0;
    int fim = v.length - 1;
    while (trocou == 1) {
        trocou = 0;
        // ida: empurra o maior para o fim
        for (int i = inicio; i < fim; i++) {
            comparacoes++;
            if (v[i] > v[i + 1]) {
                trocar(v, i, i + 1);
                trocou = 1;
            }
        }
        if (trocou == 0) break; // já ordenado
        trocou = 0;
        fim--;                  // último já está no lugar

        // volta: puxa o menor para o início
        for (int i = fim; i > inicio; i--) {
            comparacoes++;
            if (v[i - 1] > v[i]) {
                trocar(v, i - 1, i);
                trocou = 1;
            }
        }
        inicio++;               // primeiro já está no lugar
    }
}


```


### Comb Sort

```java

// Comb Sort – melhora o Bubble usando "gaps"
// Ideia: comparar e trocar elementos separados por um intervalo (gap).
// O gap começa grande (n) e vai diminuindo (aprox. /1.3) até chegar em 1.
// Quando gap = 1, o algoritmo vira um "bubble" final para ajustar o resto.
static void comb(int[] v) {
    int n = v.length;
    int gap = n;                  // intervalo inicial
    int trocou = 1;               // flag: 1 se houve troca na passada

    // continua enquanto ainda há gap > 1 OU houve trocas na última varredura
    while (gap > 1 || trocou == 1) {
        // reduz o gap (aproximação de dividir por 1.3, sem usar Math)
        gap = (gap * 10) / 13;
        if (gap < 1) gap = 1;

        trocou = 0;               // zera a flag antes da varredura
        for (int i = 0; i + gap < n; i++) {
            comparacoes++;        // conta comparação v[i] com v[i+gap]
            if (v[i] > v[i + gap]) {
                trocar(v, i, i + gap);  // troca se estiver fora de ordem
                trocou = 1;       // marca que houve troca
            }
        }
    }
}


```


### Gnome Sort

```java
// Gnome Sort – anda e corrige um passo de cada vez
static void gnome(int[] v) {
    int i = 1;              // começa no segundo elemento
    int n = v.length;
    while (i < n) {
        comparacoes++;      // estamos comparando v[i] com v[i-1]
        if (v[i] >= v[i - 1]) {
            i++;            // em ordem -> anda pra frente
        } else {
            trocar(v, i, i - 1); // fora de ordem -> troca
            i--;                 // volta um passo para checar novamente
            if (i == 0) i = 1;   // não deixa i ficar negativo
        }
    }
}
```

### Bucket Sort

```java
// Bucket Sort (versão "na unha" com 10 buckets fixos e insertion dentro de cada bucket)
static void bucket(int[] v) {
    int n = v.length;
    if (n == 0) return;

    // acha min e max para distribuir os valores no intervalo [min..max]
    int min = v[0];
    int max = v[0];
    for (int i = 1; i < n; i++) {
        if (v[i] < min) min = v[i];
        if (v[i] > max) max = v[i];
    }
    if (min == max) return; // tudo igual, já está "ordenado"

    int qtd = 10;                  // número fixo de buckets (simples)
    int[][] B = new int[qtd][n];   // cada bucket pode ter até n elementos (pior caso)
    int[] tam = new int[qtd];      // tamanho atual de cada bucket (tudo começa 0)
    for (int i = 0; i < qtd; i++) tam[i] = 0;

    // distribuição: mapeia cada v[i] para um bucket com base na posição relativa no intervalo
    for (int i = 0; i < n; i++) {
        int idx = ((v[i] - min) * (qtd - 1)) / (max - min); // escala linear sem Math
        B[idx][tam[idx]] = v[i];
        tam[idx] = tam[idx] + 1;
    }

    // ordena cada bucket com insertion sort "na mão"
    // (contando comparações e movimentações como trocas)
    for (int b = 0; b < qtd; b++) {
        for (int i = 1; i < tam[b]; i++) {
            int chave = B[b][i];
            int j = i - 1;
            while (j >= 0) {
                comparacoes++;              // comparação B[b][j] > chave
                if (B[b][j] > chave) {
                    B[b][j + 1] = B[b][j]; // desloca pra direita
                    trocas++;               // conta como movimentação
                    j--;
                } else {
                    break;                  // já achou posição
                }
            }
            B[b][j + 1] = chave;            // insere a chave no lugar certo
        }
    }

    // concatena os buckets de volta no vetor original
    int k = 0;
    for (int b = 0; b < qtd; b++) {
        for (int i = 0; i < tam[b]; i++) {
            v[k] = B[b][i];
            k++;
        }
    }
}

```

## Comparação de desempenho
# Aleatório

## Ranking por **trocas**
| Rank | Algoritmo    | Trocas |
|-----:|--------------|-------:|
| 1    | **Bucket**   | 4 |
| 2    | **Selection**| 18 |
| 3    | **Comb**     | 22 |
| 4 =  | BubbleFlag   | 78 |
| 4 =  | Cocktail     | 78 |
| 4 =  | Gnome        | 78 |

## Ranking por **comparações** (iterações)
| Rank | Algoritmo     | Comparações |
|-----:|---------------|------------:|
| 1    | **Bucket**    | 11 |
| 2    | **Comb**      | 129 |
| 3    | **Cocktail**  | 154 |
| 4    | **Gnome**     | 174 |
| 5    | **BubbleFlag**| 180 |
| 6    | **Selection** | 190 |

---

# Ordenado

## Ranking por **trocas**
| Rank | Algoritmo     | Trocas |
|-----:|---------------|-------:|
| 1 =  | BubbleFlag    | 0 |
| 1 =  | Selection     | 0 |
| 1 =  | Cocktail      | 0 |
| 1 =  | Comb          | 0 |
| 1 =  | Gnome         | 0 |
| 1 =  | **Bucket**    | 0 |

## Ranking por **comparações** (iterações)
| Rank | Algoritmo      | Comparações |
|-----:|----------------|------------:|
| 1    | **Bucket**     | 10 |
| 2 =  | BubbleFlag     | 19 |
| 2 =  | Cocktail       | 19 |
| 2 =  | Gnome          | 19 |
| 5    | **Comb**       | 110 |
| 6    | **Selection**  | 190 |

---

# Decrescente

## Ranking por **trocas**
| Rank | Algoritmo     | Trocas |
|-----:|---------------|-------:|
| 1    | **Selection** | 10 |
| 2    | **Comb**      | 18 |
| 3    | **Bucket**    | 47 |
| 4 =  | BubbleFlag    | 190 |
| 4 =  | Cocktail      | 190 |
| 4 =  | Gnome         | 190 |

## Ranking por **comparações** (iterações)
| Rank | Algoritmo      | Comparações |
|-----:|----------------|------------:|
| 1    | **Bucket**     | 47 |
| 2    | **Comb**       | 129 |
| 3 =  | BubbleFlag     | 190 |
| 3 =  | **Selection**  | 190 |
| 3 =  | Cocktail       | 190 |
| 6    | **Gnome**      | 380 |

