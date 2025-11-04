package template;

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.MOUSE_BUTTON_RIGHT;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiGroup;
import br.com.davidbuzatto.jsge.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.imgui.GuiSlider;
import br.com.davidbuzatto.jsge.imgui.GuiSpinner;
import br.com.davidbuzatto.jsge.imgui.GuiToolTip;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class Main extends EngineFrame {

    private int[] array;

    private int copiaSelection;
    private int copiaInsertion;
    private int copiaShell;
    private int copiaMerge;

    private int tamanho;
    private int margem;
    private int xIni;
    private int yIni;

    private int groupSpace;
    private int groupWidth;
    private int groupHeight;
    private int groupIniX;
    private int groupIniY;

    private List<GuiComponent> components;

    private List<int[]> arraysSelection;
    private List<int[]> arraysInsertion;
    private List<int[]> arraysShell;
    private List<int[]> arraysMerge;

    private GuiLabel nomes;

    private GuiGroup groupSelection;
    private GuiGroup groupInsertion;
    private GuiGroup groupShell;
    private GuiGroup groupMerge;

    private GuiSlider sliderSelection;
    private GuiSlider sliderInsertion;
    private GuiSlider sliderShell;
    private GuiSlider sliderMerge;
    
    private List<GuiSlider> sliders;

    public Main() {

        super(
                1055, // largura 
                450, // algura         
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

        setDefaultFontSize(30);
        setDefaultFontStyle(FONT_BOLD_ITALIC);

        tamanho = 19;
        margem = 5;

        xIni = 14;
        yIni = (getScreenHeight()) / 2 + 80;
        groupSpace = 15;
        groupWidth = 245;
        groupHeight = 210;
        groupIniX = groupSpace + groupWidth;
        groupIniY = yIni - groupHeight + 5;

        components = new ArrayList<>();
        sliders = new ArrayList<>();

        arraysSelection = new ArrayList<>();
        arraysInsertion = new ArrayList<>();
        arraysShell = new ArrayList<>();
        arraysMerge = new ArrayList<>();

        groupSelection = new GuiGroup(xIni, groupIniY, groupWidth, groupHeight, "Selection Sort");
        groupInsertion = new GuiGroup(xIni + groupIniX, groupIniY, groupWidth, groupHeight, "Insertion Sort");
        groupShell = new GuiGroup(xIni + groupIniX * 2, groupIniY, groupWidth, groupHeight, "Shell Sort");
        groupMerge = new GuiGroup(xIni + groupIniX * 3, groupIniY, groupWidth, groupHeight, "Merge Sort");

        array = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        selectionSort(array.clone());
        insertionSort(array.clone());
        shellSort(array.clone());
        mergeSort(array.clone(), 0, array.clone().length - 1);

        nomes = new GuiLabel(getScreenWidth() - 260, getScreenHeight() - 30, 30, 30, "João Vitor Gregorio e Raissa Machado");

        sliderSelection = new GuiSlider(groupSelection.getX(), yIni, groupWidth, 50, 0, 0, (arraysSelection.size() - 1) / 2);
        sliderInsertion = new GuiSlider(groupInsertion.getX(), yIni, groupWidth, 50, 0, 0, (arraysInsertion.size() - 1));
        sliderShell = new GuiSlider(groupShell.getX(), yIni, groupWidth, 50, 0, 0, (arraysShell.size() - 1));
        sliderMerge = new GuiSlider(groupMerge.getX(), yIni, groupWidth, 50, 0, 0, (arraysMerge.size()) - 1);

        components.add(nomes);
        
        sliders.add(sliderSelection);
        sliders.add(sliderInsertion);
        sliders.add(sliderShell);
        sliders.add(sliderMerge);
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

        desenharArray(arraysSelection.get(copiaSelection), groupSelection.getX() + margem);
        desenharArray(arraysInsertion.get(copiaInsertion), groupInsertion.getX() + margem);
        desenharArray(arraysShell.get(copiaShell), groupShell.getX() + margem);
        desenharArray(arraysMerge.get(copiaMerge), groupMerge.getX() + margem);

        drawText("Projeto - Algoritmos de Ordenação", (getScreenWidth() / 2) - 300, 35, DARKGREEN);
    }

    private void desenharArray(int[] a, double n) {

        for (int i = 0; i < a.length; i++) {

            int altura = tamanho * a[i];

            fillRectangle(
                    n + (tamanho + margem) * i,
                    yIni - altura,
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

    public static void main(String[] args) {

        new Main();
    }
}
