package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiGroup;
import br.com.davidbuzatto.jsge.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.imgui.GuiSlider;
import java.util.ArrayList;
import java.util.List;

public class Main extends EngineFrame {

    private int[] array;

    private int copiaSelection;
    private int copiaInsertion;
    private int copiaShell;
    private int copiaMerge;
    private int copiaBucket;
    private int copiaCounting;

    private int tamanho;
    private int margem;
    private int xIni;
    private int yIni;

    private int groupWidth;
    private int groupHeight;

    private List<GuiComponent> components;

    private List<int[]> arraysSelection;
    private List<int[]> arraysInsertion;
    private List<int[]> arraysShell;
    private List<int[]> arraysMerge;
    private List<int[]> arraysBucket;
    private List<int[]> arraysCounting;

    private GuiLabel nomes;

    private GuiGroup groupSelection;
    private GuiGroup groupInsertion;
    private GuiGroup groupShell;
    private GuiGroup groupMerge;
    private GuiGroup groupBucket;
    private GuiGroup groupCounting;

    private GuiSlider sliderSelection;
    private GuiSlider sliderInsertion;
    private GuiSlider sliderShell;
    private GuiSlider sliderMerge;
    private GuiSlider sliderBucket;
    private GuiSlider sliderCounting;

    private List<GuiSlider> sliders;

    public Main() {

        super(
                900, // largura 
                650, // altura         
                "Algoritmos de Ordenação", // título         
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

        arraysSelection = new ArrayList<>();
        arraysInsertion = new ArrayList<>();
        arraysShell = new ArrayList<>();
        arraysMerge = new ArrayList<>();
        arraysBucket = new ArrayList<>();
        arraysCounting = new ArrayList<>();

        groupSelection = new GuiGroup(xIni, yIni, groupWidth, groupHeight, "Selection Sort");
        groupInsertion = new GuiGroup(groupSelection.getX() + groupWidth + xIni, yIni, groupWidth, groupHeight, "Insertion Sort");
        groupShell = new GuiGroup(groupInsertion.getX() + groupWidth + xIni, yIni, groupWidth, groupHeight, "Shell Sort");
        groupMerge = new GuiGroup(xIni, groupSelection.getY() + groupHeight + 70, groupWidth, groupHeight, "Merge Sort");
        groupBucket = new GuiGroup(groupMerge.getX() + groupWidth + xIni, groupMerge.getY(), groupWidth, groupHeight, "Bucket Sort");
        groupCounting = new GuiGroup(groupBucket.getX() + groupWidth + xIni, groupMerge.getY(), groupWidth, groupHeight, "Couting Sort");

        array = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        selectionSort(array.clone());
        insertionSort(array.clone());
        shellSort(array.clone());
        mergeSort(array.clone(), 0, array.clone().length - 1);
        bucketSort(array.clone());
        countingSort(array.clone(), 10);

        nomes = new GuiLabel(getScreenWidth() - 260, getScreenHeight() - 30, 30, 30, "João Vitor Gregorio e Raissa Machado");

        sliderSelection = new GuiSlider(groupSelection.getX(), yIni + groupHeight, groupWidth, 40, 0, 0, (arraysSelection.size() - 1) / 2);
        sliderInsertion = new GuiSlider(groupInsertion.getX(), yIni + groupHeight, groupWidth, 40, 0, 0, (arraysInsertion.size() - 1));
        sliderShell = new GuiSlider(groupShell.getX(), groupShell.getY() + groupHeight, groupWidth, 40, 0, 0, (arraysShell.size() - 1));
        sliderMerge = new GuiSlider(groupMerge.getX(), groupMerge.getY() + groupHeight, groupWidth, 40, 0, 0, (arraysMerge.size()) - 1);
        sliderBucket = new GuiSlider(groupBucket.getX(), groupBucket.getY() + groupHeight, groupWidth, 40, 0, 0, (arraysBucket.size()) - 1);
        sliderCounting = new GuiSlider(groupCounting.getX(), groupCounting.getY() + groupHeight, groupWidth, 40, 0, 0, (arraysCounting.size()) - 1);

        components.add(nomes);

        sliders.add(sliderSelection);
        sliders.add(sliderInsertion);
        sliders.add(sliderShell);
        sliders.add(sliderMerge);
        sliders.add(sliderBucket);
        sliders.add(sliderCounting);
    }

    @Override
    public void update(double delta) {

        for (GuiComponent c : sliders) {
            c.update(delta);

        }

        copiaSelection = (int) sliderSelection.getValue();
        copiaInsertion = (int) sliderInsertion.getValue();
        copiaShell = (int) sliderShell.getValue();
        copiaMerge = (int) sliderMerge.getValue();
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

        for (GuiComponent g : List.of(groupSelection, groupInsertion, groupShell, groupMerge, groupBucket, groupCounting)) {

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

        desenharArray(arraysSelection.get(copiaSelection), groupSelection.getX() + margem, groupSelection.getY());
        desenharArray(arraysInsertion.get(copiaInsertion), groupInsertion.getX() + margem, groupInsertion.getY());
        desenharArray(arraysShell.get(copiaShell), groupShell.getX() + margem, (double) groupShell.getY());
        desenharArray(arraysMerge.get(copiaMerge), groupMerge.getX() + margem, groupMerge.getY());
        desenharArray(arraysBucket.get(copiaBucket), groupBucket.getX() + margem, groupBucket.getY());
        desenharArray(arraysCounting.get(copiaCounting), groupCounting.getX() + margem, groupCounting.getY());

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

    private int[] copiarArray(int[] array) {

        int[] copia = new int[array.length];
        System.arraycopy(array, 0, copia, 0, array.length);
        return copia;
    }

    private void trocar(int[] array, int i, int min) {

        int t = array[i];
        array[i] = array[min];
        array[min] = t;
    }

    private void selectionSort(int[] array) {

        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }

            arraysSelection.add(copiarArray(array));
            trocar(array, i, min);
        }

        arraysSelection.add(copiarArray(array));
    }

    private void insertionSort(int[] array) {

        int chave = 0;

        for (int i = 0; i < array.length; i++) {
            chave = array[i];

            int j = i - 1;

            while (j >= 0 && array[j] > chave) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = chave;

            arraysInsertion.add(copiarArray(array));
        }

        arraysInsertion.add(copiarArray(array));
    }

    public void shellSort(int[] array) {

        arraysShell.add(copiarArray(array));

        int h = 1;
        int length = array.length;
        while (h < length / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < length; i++) {
                int j = i;
                while (j >= h && array[j - h] > array[j]) {
                    trocar(array, j - h, j);
                    arraysShell.add(copiarArray(array));
                    j = j - h;
                }
            }
            h = h / 3;
        }

        arraysShell.add(copiarArray(array));
    }

    private void mergeSort(int[] array, int p, int r) {

        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(array, p, q);
            mergeSort(array, q + 1, r);
            intercalaSemSentinela(array, p, q, r);
        }

        arraysMerge.add(copiarArray(array));
    }

    void intercalaSemSentinela(int[] array, int p, int q, int r) {

        int i, j;
        int[] arrayB = new int[r + 1];

        for (i = p; i <= q; i++) {
            arrayB[i] = array[i];
        }
        for (j = q + 1; j <= r; j++) {
            arrayB[r + q + 1 - j] = array[j];
        }
        i = p;
        j = r;
        for (int k = p; k <= r; k++) {
            if (arrayB[i] <= arrayB[j]) {
                array[k] = arrayB[i];
                i++;
            } else {
                array[k] = arrayB[j];
                j--;
            }
        }
        arraysMerge.add(copiarArray(array));
    }

    void bucketSort(int[] array) {
        arraysBucket.add(copiarArray(array));

        int n = array.length;

        final int K = 10;

        int[][] buckets = new int[K][n];
        int[] c = new int[K];

        int t1 = 10;
        int t2 = 1;

        int max = -1;

        boolean first = true;

        while (max < 0 || max / t2 != 0) {

            for (int i = 0; i < n; i++) {
                int p = array[i] % t1 / t2;
                buckets[p][c[p]++] = array[i];

                if (first) {
                    max = max < array[i] ? array[i] : max;
                }

                array[i] = 0;
                arraysBucket.add(copiarArray(array));
            }

            first = false;

            int k = 0;

            for (int i = 0; i < K; i++) {
                for (int j = 0; j < c[i]; j++) {
                    array[k++] = buckets[i][j];
                    arraysBucket.add(copiarArray(array));
                }

                c[i] = 0;
            }

            t2 = t1;
            t1 *= 10;
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

        new Main();
    }
}
