package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.DARKGREEN;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.FONT_BOLD_ITALIC;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiGroup;
import br.com.davidbuzatto.jsge.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.imgui.GuiSlider;
import java.awt.Color;
import static java.awt.Color.CYAN;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmosLineares extends EngineFrame {

    private int copiaBucket;
    private int copiaCounting;
    private int tamanho;
    private int margem;
    private int groupWidth;
    private int groupHeight;

    private int[] array;

    private List<int[]> arraysBucket;
    private List<int[]> arraysCounting;
    private List<int[]> arraysC;
    private List<int[]> arraysB;
    private List<int[][]> arraysBucketsVisual;
    private List<boolean[]> arraysExiste;
    private List<String> etapasCounting;
    private List<String> etapasBucket;
    private List<GuiComponent> components;
    private List<GuiSlider> sliders;

    private GuiLabel nomes;
    private GuiGroup groupBucket;
    private GuiGroup groupCounting;
    private GuiSlider sliderBucket;
    private GuiSlider sliderCounting;

    public AlgoritmosLineares() {
        super(
                900, // largura 
                650, // altura         
                "Algoritmos de Ordenação Lineares", // título         
                60, // quadros por segundo desejado 
                true, // suavização                 
                false, // redimensionável            
                false, // tela cheia                   
                false, // sem decoração               
                false, // sempre no topo             
                false // fundo invisível            
        );
    }

    @Override
    public void create() {
        useAsDependencyForIMGUI();

        setDefaultFontSize(25);
        setDefaultFontStyle(FONT_BOLD_ITALIC);

        tamanho = 19;
        margem = 5;

        groupWidth = 245;
        groupHeight = 210;

        components = new ArrayList<>();
        sliders = new ArrayList<>();
        arraysBucket = new ArrayList<>();
        arraysCounting = new ArrayList<>();
        arraysBucketsVisual = new ArrayList<>();
        etapasBucket = new ArrayList<>();
        arraysC = new ArrayList<>();
        arraysB = new ArrayList<>();
        etapasCounting = new ArrayList<>();
        arraysExiste = new ArrayList<>();

        groupBucket = new GuiGroup(120, 90, groupWidth, groupHeight, "Bucket Sort");
        groupCounting = new GuiGroup(120, groupBucket.getY() + groupHeight + 50, groupWidth, groupHeight, "Counting Sort");

        array = new int[]{10, 10, 9, 7, 6, 5, 5, 3, 1, 1};

        bucketSort(array.clone());
        countingSort(array.clone(), 10);

        nomes = new GuiLabel(getScreenWidth() - 260, getScreenHeight() - 30, 30, 30, "João Vitor Gregorio e Raissa Machado");

        sliderBucket = new GuiSlider(groupBucket.getX(), groupBucket.getY() + groupHeight, groupWidth, 40, 0, 0, (arraysBucket.size()) - 1);
        sliderCounting = new GuiSlider(groupCounting.getX(), groupCounting.getY() + groupHeight, groupWidth, 40, 0, 0, (arraysCounting.size()) - 1);

        components.add(nomes);

        sliders.add(sliderBucket);
        sliders.add(sliderCounting);
    }

    @Override
    public void update(double delta) {
        for (GuiComponent c : sliders) {
            c.update(delta);

        }

        copiaBucket = (int) sliderBucket.getValue();
        copiaCounting = (int) sliderCounting.getValue();
    }

    @Override
    public void draw() {
        clearBackground(BLACK);

        for (GuiComponent c : components) {
            c.draw();
        }

        for (GuiSlider s : sliders) {

            s.setTrackFillColor(DARKGREEN);
            s.setBorderColor(BLACK);
            s.setBackgroundColor(WHITE);
            s.draw();
        }

        for (GuiComponent g : List.of(groupBucket, groupCounting)) {

            fillRectangle(
                    g.getX(),
                    g.getY(),
                    g.getWidth(),
                    g.getHeight(),
                    BLACK
            );
            g.setBorderColor(WHITE);
            g.setTextColor(WHITE);
            g.draw();
        }

        desenharArray(arraysBucket.get(copiaBucket), groupBucket.getX() + margem, groupBucket.getY());
        desenharArray(arraysCounting.get(copiaCounting), groupCounting.getX() + margem, groupCounting.getY());

        drawText("Algoritmos de Ordenação Lineares", (getScreenWidth() / 2) - 233, 30, DARKGREEN);

        if (copiaBucket < etapasBucket.size()) {
            drawText(
                    "Etapa: " + etapasBucket.get(copiaBucket),
                    groupBucket.getX() + groupWidth + 30,
                    groupBucket.getY(),
                    20,
                    WHITE
            );
        }

        if (copiaBucket < arraysBucketsVisual.size()) {
            desenharBuckets(
                    arraysBucketsVisual.get(copiaBucket),
                    groupBucket.getX() + groupWidth + 30,
                    groupBucket.getY() + 30
            );
        }

        drawText(
                "Etapa: " + etapasCounting.get(copiaCounting),
                groupCounting.getX() + groupWidth + 30,
                groupCounting.getY(),
                20,
                WHITE
        );

        desenharCounting(
                arraysC.get(copiaCounting),
                groupCounting.getX() + groupWidth + 30,
                groupCounting.getY() + 30
        );

        desenharArrayPequeno(
                arraysB.get(copiaCounting),
                groupCounting.getX() + groupWidth + 30,
                groupCounting.getY() + 100
        );
    }

    private void desenharArray(int[] a, double x, double y) {
        for (int i = 0; i < a.length; i++) {

            int altura = tamanho * a[i];

            fillRectangle(
                    x + (tamanho + margem) * i,
                    y + groupHeight - altura,
                    tamanho,
                    altura,
                    getColorByIndex(a[i])
            );
        }
    }

    private void desenharBuckets(int[][] buckets, double x, double y) {
        int larguraBucket = 30;
        int alturaMax = groupHeight - 30;

        for (int i = 0; i < buckets.length; i++) {

            int baseX = (int) x + i * (larguraBucket + 10);

            drawRectangle(baseX, (int) y, larguraBucket, alturaMax, WHITE);

            for (int j = 0; j < buckets[i].length; j++) {

                if (buckets[i][j] != -1) {

                    int altura = 20;

                    fillRectangle(
                            baseX + 1,
                            (int) y + alturaMax - (j + 1) * altura,
                            larguraBucket - 1,
                            altura,
                            getColorByIndex(buckets[i][j])
                    );
                }
            }

            drawText(String.valueOf(i), baseX + 10, (int) y + alturaMax + 5, 15, WHITE);
        }
    }

    private void desenharCounting(int[] a, double x, double y) {
        int largura = 35;
        int altura = 30;

        for (int i = 0; i < a.length; i++) {

            boolean[] existeAtual = arraysExiste.get(copiaCounting);

            Color cor;

            if (existeAtual[i]) {
                cor = getColorByIndex(i);
            } else {
                cor = BLACK;
            }

            int baseX = (int) x + i * largura;

            fillRectangle(
                    baseX,
                    y,
                    largura,
                    altura,
                    cor
            );

            drawRectangle(
                    baseX,
                    y,
                    largura,
                    altura,
                    WHITE
            );

            drawText(
                    String.valueOf(i),
                    x + i * largura + 12,
                    y + 12,
                    15,
                    WHITE
            );

            drawText(String.valueOf(a[i]), baseX + 10, (int) y + altura + 5, 15, WHITE);
        }
    }

    private void desenharArrayPequeno(int[] a, double x, double y) {
        int largura = 35;
        int altura = 30;

        for (int i = 0; i < a.length; i++) {

            Color cor = (a[i] > 0)
                    ? getColorByIndex(a[i])
                    : BLACK;

            int baseX = (int) x + i * largura;

            fillRectangle(
                    baseX,
                    y,
                    largura,
                    altura,
                    cor
            );

            drawRectangle(
                    baseX,
                    y,
                    largura,
                    altura,
                    WHITE
            );

            drawText(String.valueOf(a[i]),
                    x + i * largura + 12,
                    y + 12,
                    15,
                    WHITE
            );

            drawText(String.valueOf(i), baseX + 10, (int) y + altura + 5, 15, WHITE);
        }
    }

    private Color getColorByIndex(int i) {
        return switch (i) {
            case 0 ->
                RED;
            case 1 ->
                ORANGE;
            case 2 ->
                YELLOW;
            case 3 ->
                GREEN;
            case 4 ->
                CYAN;
            case 5 ->
                BLUE;
            case 6 ->
                new Color(75, 0, 130);
            case 7 ->
                new Color(148, 0, 211);
            case 8 ->
                new Color(255, 105, 180);
            case 9 ->
                new Color(255, 20, 147);
            case 10 ->
                new Color(255, 0, 255);
            default ->
                WHITE;
        };
    }

    private String getEtapa(int t2) {
        return switch (t2) {
            case 1 ->
                "Unidades";
            case 10 ->
                "Dezenas";
            case 100 ->
                "Centenas";
            default ->
                "Casa " + t2;
        };
    }

    private int[] copiarArray(int[] array) {
        int[] copia = new int[array.length];
        System.arraycopy(array, 0, copia, 0, array.length);
        return copia;
    }

    void bucketSort(int[] array) {
        int t1 = 10;
        int t2 = 1;

        int n = array.length;

        final int K = 10;

        int[][] buckets = new int[K][n];

        for (int i = 0; i < K; i++) {
            for (int j = 0; j < n; j++) {
                buckets[i][j] = -1;
            }
        }

        etapasBucket.add("Inicial");
        arraysBucket.add(copiarArray(array));
        arraysBucketsVisual.add(copiarBuckets(buckets));

        int[] c = new int[K];

        int max = -1;

        boolean first = true;

        while (max < 0 || max / t2 != 0) {

            limparBuckets(buckets, c);

            for (int i = 0; i < n; i++) {
                int p = array[i] % t1 / t2;
                buckets[p][c[p]++] = array[i];
                arraysBucketsVisual.add(copiarBuckets(buckets));

                if (first) {
                    max = max < array[i] ? array[i] : max;
                }

                array[i] = 0;
                etapasBucket.add(getEtapa(t2));
                arraysBucket.add(copiarArray(array));
            }

            first = false;

            boolean ultimaIteracao = (max / t1 == 0);

            int k = 0;

            for (int i = 0; i < K; i++) {
                for (int j = 0; j < c[i]; j++) {
                    int valor = buckets[i][j];

                    buckets[i][j] = -1;
                    arraysBucketsVisual.add(copiarBuckets(buckets));

                    array[k] = valor;

                    k++;
                    if (ultimaIteracao) {
                        etapasBucket.add("Final");
                    } else {
                        etapasBucket.add(getEtapa(t2));
                    }

                    arraysBucket.add(copiarArray(array));
                }
            }

            t2 = t1;
            t1 *= 10;
        }

        etapasBucket.add("Final");
        arraysBucket.add(copiarArray(array));
        arraysBucketsVisual.add(copiarBuckets(buckets));
    }

    private int[][] copiarBuckets(int[][] buckets) {
        int[][] copia = new int[buckets.length][buckets[0].length];

        for (int i = 0; i < buckets.length; i++) {
            System.arraycopy(buckets[i], 0, copia[i], 0, buckets[i].length);
        }

        return copia;
    }

    private void limparBuckets(int[][] buckets, int[] c) {
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].length; j++) {
                buckets[i][j] = -1;
            }
            c[i] = 0;
        }
    }

    void countingSort(int[] array, int k) {
        int n = array.length;

        int[] freq = new int[k + 1];

        boolean[] existe = new boolean[k + 1];

        int[] c = new int[k + 1];
        int[] b = new int[n];

        int[] arrayVisual = copiarArray(array);

        etapasCounting.add("Inicial");
        arraysCounting.add(copiarArray(arrayVisual));
        arraysC.add(copiarArray(c));
        arraysB.add(copiarArray(b));
        arraysExiste.add(existe.clone());

        for (int i = 0; i < n; i++) {
            c[array[i]]++;

            freq[array[i]]++;

            existe[array[i]] = true;

            arrayVisual[i] = 0;

            etapasCounting.add("Contagem");
            arraysCounting.add(copiarArray(arrayVisual));
            arraysC.add(copiarArray(c));
            arraysB.add(copiarArray(b));
            arraysExiste.add(existe.clone());
        }

        for (int i = 1; i <= k; i++) {
            c[i] += c[i - 1];

            etapasCounting.add("Acumulação");
            arraysCounting.add(copiarArray(arrayVisual));
            arraysC.add(copiarArray(c));
            arraysB.add(copiarArray(b));
            arraysExiste.add(existe.clone());
        }

        for (int i = n - 1; i >= 0; i--) {
            c[array[i]]--;
            b[c[array[i]]] = array[i];

            freq[array[i]]--;

            if (freq[array[i]] == 0) {
                existe[array[i]] = false;
            }

            arrayVisual[i] = 0;

            etapasCounting.add("Construção");
            arraysCounting.add(copiarArray(arrayVisual));
            arraysC.add(copiarArray(c));
            arraysB.add(copiarArray(b));
            arraysExiste.add(existe.clone());
        }

        for (int i = 0; i < n; i++) {
            array[i] = b[i];

            arrayVisual[i] = b[i];
            b[i] = 0;

            etapasCounting.add("Final");
            arraysCounting.add(copiarArray(arrayVisual));
            arraysC.add(copiarArray(c));
            arraysB.add(copiarArray(b));
            arraysExiste.add(existe.clone());
        }
    }

    public static void main(String[] args) {
        new AlgoritmosLineares();
    }
}
