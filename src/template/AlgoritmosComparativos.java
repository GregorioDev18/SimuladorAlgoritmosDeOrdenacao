package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLUE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.GREEN;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.ORANGE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.RED;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.YELLOW;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiGroup;
import br.com.davidbuzatto.jsge.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.imgui.GuiSlider;
import java.awt.Color;
import static java.awt.Color.CYAN;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmosComparativos extends EngineFrame {

    private int copiaSelection;
    private int copiaInsertion;
    private int copiaShell;
    private int copiaMerge;
    private int tamanho;
    private int margem;
    private int xIni;
    private int yIni;
    private int groupWidth;
    private int groupHeight;

    private int[] array;

    private List<int[]> arraysSelection;
    private List<int[]> arraysInsertion;
    private List<int[]> arraysShell;
    private List<int[]> arraysMerge;
    private List<GuiComponent> components;
    private List<GuiSlider> sliders;

    private GuiLabel nomes;
    private GuiGroup groupSelection;
    private GuiGroup groupInsertion;
    private GuiGroup groupShell;
    private GuiGroup groupMerge;
    private GuiSlider sliderSelection;
    private GuiSlider sliderInsertion;
    private GuiSlider sliderShell;
    private GuiSlider sliderMerge;
    private GuiButton btnVoltar;

    public AlgoritmosComparativos() {
        super(
                900, // largura 
                650, // altura         
                "Algoritmos de Ordenação Comparativos", // título         
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

        xIni = 160;
        yIni = 80;
        groupWidth = 245;
        groupHeight = 210;

        components = new ArrayList<>();
        sliders = new ArrayList<>();

        arraysSelection = new ArrayList<>();
        arraysInsertion = new ArrayList<>();
        arraysShell = new ArrayList<>();
        arraysMerge = new ArrayList<>();

        groupSelection = new GuiGroup(xIni, yIni, groupWidth, groupHeight, "Selection Sort");
        groupInsertion = new GuiGroup(groupSelection.getX() + groupWidth + 100, yIni, groupWidth, groupHeight, "Insertion Sort");
        groupShell = new GuiGroup(xIni, groupSelection.getY() + groupHeight + 70, groupWidth, groupHeight, "Shell Sort");
        groupMerge = new GuiGroup(groupInsertion.getX(), groupShell.getY(), groupWidth, groupHeight, "Merge Sort");

        array = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        selectionSort(array.clone());
        insertionSort(array.clone());
        shellSort(array.clone());
        mergeSort(array.clone(), 0, array.clone().length - 1);

        nomes = new GuiLabel(getScreenWidth() - 260, getScreenHeight() - 30, 30, 30, "João Vitor Gregorio e Raissa Machado");

        sliderSelection = new GuiSlider(groupSelection.getX(), yIni + groupHeight, groupWidth, 40, 0, 0, (arraysSelection.size() - 1) / 2);
        sliderInsertion = new GuiSlider(groupInsertion.getX(), yIni + groupHeight, groupWidth, 40, 0, 0, (arraysInsertion.size() - 1));
        sliderShell = new GuiSlider(groupShell.getX(), groupShell.getY() + groupHeight, groupWidth, 40, 0, 0, (arraysShell.size() - 1));
        sliderMerge = new GuiSlider(groupMerge.getX(), groupMerge.getY() + groupHeight, groupWidth, 40, 0, 0, (arraysMerge.size()) - 1);

        btnVoltar = new GuiButton(10, 10, 80, 20, "VOLTAR");

        components.add(nomes);
        components.add(btnVoltar);

        sliders.add(sliderSelection);
        sliders.add(sliderInsertion);
        sliders.add(sliderShell);
        sliders.add(sliderMerge);
    }

    @Override
    public void update(double delta) {
        for (GuiComponent s : sliders) {
            s.update(delta);
        }

        for (GuiComponent c : components) {
            c.update(delta);
        }

        if (btnVoltar.isMousePressed()) {
            Home homeWindow = new Home();
            homeWindow.setVisible(true);
            this.setVisible(false);
        }

        copiaSelection = (int) sliderSelection.getValue();
        copiaInsertion = (int) sliderInsertion.getValue();
        copiaShell = (int) sliderShell.getValue();
        copiaMerge = (int) sliderMerge.getValue();
    }

    @Override
    public void draw() {
        clearBackground(BLACK);

        for (GuiComponent c : components) {
            c.draw();
        }

        btnVoltar.setBackgroundColor(GRAY);
        btnVoltar.setBorderColor(GRAY);
        btnVoltar.setTextColor(BLACK);

        for (GuiSlider s : sliders) {

            s.setTrackFillColor(DARKGREEN);
            s.setBorderColor(BLACK);
            s.setBackgroundColor(WHITE);
            s.draw();
        }

        for (GuiComponent g : List.of(groupSelection, groupInsertion, groupShell, groupMerge)) {

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

        drawText("Algoritmos de Ordenação Comparativos", (getScreenWidth() / 2) - 260, 30, DARKGREEN);
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

    public static void main(String[] args) {

        new AlgoritmosComparativos();
    }
}
