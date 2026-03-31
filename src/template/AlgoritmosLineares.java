package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.DARKGREEN;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.DARKPURPLE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.FONT_BOLD_ITALIC;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiGroup;
import br.com.davidbuzatto.jsge.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.imgui.GuiSlider;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmosLineares extends EngineFrame {

    private int[] array;
    private int[] arrayAuxBucket;
    int[][] bucketsVisual;

    private int copiaBucket;
    private int copiaCounting;

    private int tamanho;
    private int margem;
    private int xIni;
    private int yIni;

    private int groupWidth;
    private int groupHeight;

    private List<GuiComponent> components;

    private List<int[]> arraysBucket;
    private List<int[]> arraysCounting;
    private List<int[]> arraysAuxBucket;
    private List<int[][]> arraysBucketsVisual;

    private GuiLabel nomes;

    private GuiGroup groupBucket;
    private GuiGroup groupCounting;

    private GuiSlider sliderBucket;
    private GuiSlider sliderCounting;

    private List<GuiSlider> sliders;

    public AlgoritmosLineares() {

        super(
                900, // largura 
                650, // altura         
                "Algoritmos de Ordenação Linear", // título         
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

        xIni = 40;
        yIni = 80;
        groupWidth = 245;
        groupHeight = 210;

        components = new ArrayList<>();
        sliders = new ArrayList<>();
        arraysBucket = new ArrayList<>();
        arraysCounting = new ArrayList<>();
        arraysAuxBucket = new ArrayList<>();
        arraysBucketsVisual = new ArrayList<>();

        groupBucket = new GuiGroup(30, 80, groupWidth, groupHeight, "Bucket Sort");
        groupCounting = new GuiGroup(30, groupBucket.getY() + groupHeight + 40, groupWidth, groupHeight, "Couting Sort");

        array = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        arrayAuxBucket = new int[10];

        for (int i = 0; i < arrayAuxBucket.length; i++) {
            arrayAuxBucket[i] = -1;
        }

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

        if (copiaBucket < arraysBucketsVisual.size()) {
            desenharBuckets(
                    arraysBucketsVisual.get(copiaBucket),
                    groupBucket.getX() + groupWidth + 30,
                    groupBucket.getY()
            );
        }

        drawText("Projeto - Algoritmos de Ordenação", (getScreenWidth() / 2) - 246, 30, DARKGREEN);
    }

    private void desenharArray(int[] a, double x, double y) {

        for (int i = 0; i < a.length; i++) {

            int altura = tamanho * a[i];

            fillRectangle(
                    x + (tamanho + margem) * i,
                    y + groupHeight - altura,
                    tamanho,
                    altura, DARKPURPLE
            );
        }
    }

    private void desenharArrayTexto(int[] a) {
        StringBuilder sb = new StringBuilder();

        boolean primeiro = true;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {
                if (!primeiro) {
                    sb.append(" | ");
                }
                sb.append(a[i]);
                primeiro = false;
            }
        }

        drawText(sb.toString(), groupBucket.getX() + groupWidth + 38, groupBucket.getY(), 20, WHITE);
    }

    private void desenharBuckets(int[][] buckets, double x, double y) {

        int larguraBucket = 30;
        int alturaMax = 100;

        for (int i = 0; i < buckets.length; i++) {

            int baseX = (int) x + i * (larguraBucket + 10);

            // desenha "caixa" do bucket
            drawRectangle(baseX, (int) y, larguraBucket, alturaMax, WHITE);

            // desenha elementos dentro
            for (int j = 0; j < buckets[i].length; j++) {

                if (buckets[i][j] != -1) {

                    int altura = 10;

                    fillRectangle(
                            baseX,
                            (int) y + alturaMax - (j + 1) * altura,
                            larguraBucket,
                            altura,
                            getColorByIndex(i)
                    );
                }
            }

            // label do bucket
            drawText(String.valueOf(i), baseX + 5, (int) y + alturaMax + 5, 15, WHITE);
        }
    }

    private Color getColorByIndex(int i) {

        switch (i) {
            case 0:
                return RED;
            case 1:
                return ORANGE;
            case 2:
                return YELLOW;
            case 3:
                return GREEN;
            case 4:
                return BLUE;
            case 5:
                return DARKBLUE;
            case 6:
                return PURPLE;
            case 7:
                return PINK;
            case 8:
                return BROWN;
            case 9:
                return GRAY;
            default:
                return WHITE;
        }
    }

    private int[] copiarArray(int[] array) {

        int[] copia = new int[array.length];
        System.arraycopy(array, 0, copia, 0, array.length);
        return copia;
    }

    void bucketSort(int[] array) {
        arraysBucket.add(copiarArray(array));
        arraysAuxBucket.add(copiarArray(arrayAuxBucket));

        int n = array.length;

        final int K = 10;

        int[][] buckets = new int[K][n];

        for (int i = 0; i < K; i++) {
            for (int j = 0; j < n; j++) {
                buckets[i][j] = -1;
            }
        }

        arraysBucketsVisual.add(copiarBuckets(buckets));

        int[] c = new int[K];

        int t1 = 10;
        int t2 = 1;

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

                arrayAuxBucket[i] = array[i];
                arraysAuxBucket.add(copiarArray(arrayAuxBucket));

                array[i] = 0;
                arraysBucket.add(copiarArray(array));
            }

            first = false;

            int k = 0;

            for (int i = 0; i < K; i++) {
                for (int j = 0; j < c[i]; j++) {
                    int valor = buckets[i][j];
                    array[k] = valor;

                    for (int x = 0; x < arrayAuxBucket.length; x++) {
                        if (arrayAuxBucket[x] == valor) {
                            arrayAuxBucket[x] = -1;
                            arraysAuxBucket.add(copiarArray(arrayAuxBucket));

                            break;
                        }
                    }

                    k++;
                    arraysBucket.add(copiarArray(array));
                }
            }

            t2 = t1;
            t1 *= 10;
        }
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

        int[] c = new int[k + 1];
        int[] b = new int[n];

        arraysCounting.add(copiarArray(array));

        for (int i = 0; i < n; i++) {
            c[array[i]]++;
        }

        for (int i = 1; i <= k; i++) {
            c[i] += c[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            c[array[i]]--;
            b[c[array[i]]] = array[i];
        }

        for (int i = 0; i < n; i++) {
            array[i] = b[i];
            arraysCounting.add(copiarArray(array));
        }
    }

    public static void main(String[] args) {

        new AlgoritmosLineares();
    }
}
