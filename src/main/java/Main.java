import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @AUTHOR Robin Dort, 3693430
 * @AUTHOR Joshua Wiesen, 3693740
 *
 */

public class Main {

    //CONSTANTS
    //private static String FILE = "C:\\Users\\Kodiak\\Documents\\medizin.csv";
    private static String file = "./src/main/resources/Textdatei/medizin.csv";
    //private static String FILE = "/Users/louisadort/Desktop/Robin_Uni/EuRusD/medizin.csv";
    private String LINE = "-----------------------";
    private String ERR_TABLE_NOT_LOADED = "Tabelle muss erst geladen werden!";

    //VARIABLES
    private String[] head;
    private String[][] content;
    private Table tb;
    private Tablereader tr;
    private static Scanner scanner;
    private final double baseToRound = 10.0;
    private int selection = 0;
    private String[] tmp;
    private Core core = new Core();
    private final String STEADY_DATA = "GEWICHT,ALTER,BD0,BD12,DIFF";
    private final String DISCREETDATA = "GESCHLECHT,BG,DZ";


    /**
     * constructor
     */
    public Main() {
        tr = new Tablereader(file);
    }


    public static void main(String[] args){
        Main main = new Main();
        main.startLoop();
    }

    private void startLoop(){
        scanner = new Scanner(System.in);
        int x = 0;
        while (x == 0) {
            System.out.println("Bitte Eingabe machen:");
            System.out.println("[1] Tabellenoptionen");
            System.out.println("[2] Arithmetisches Mittel");
            System.out.println("[3] Median");
            System.out.println("[4] Spannweite");
            System.out.println("[5] Modus");
            System.out.println("[6] Streuung");
            System.out.println("[7] Standartabweichung");
            System.out.println("[8] Alpha-Quartile");
            System.out.println("[9] Klassenbreite");
            System.out.println("[0] Beenden");
            System.out.println("Selection: ");
            int selection = scanner.nextInt();

           x = mainSwitcher(selection);
        }
    }

    private int mainSwitcher(int selection){
        int x = 0;
        switch (selection) {

            case 1:
                tableOptions();
                break;

            case 2:
                if (tableIsNotLoaded())
                    break;

                opArithmeticMean();
                break;

            case 3:
                if (tableIsNotLoaded())
                    break;
                opMedian();
                break;

            case 4:
                if (tableIsNotLoaded())
                    break;
               opSpanRange();
                break;

            case 5:
                if (tableIsNotLoaded())
                    break;
                opMode();
                break;

            case 6:
                if (tableIsNotLoaded())
                    break;
                opSpread();
                break;


            case 7:
                if (tableIsNotLoaded())
                    break;
                opStandardDeviation();
                break;

            case 8:
                if (tableIsNotLoaded())
                    break;
                opQuartile();
                break;

            case 9:
                if (tableIsNotLoaded())
                    break;
                chooseDataType();
                break;

            case 0:
                x = 1;
                break;
        }
        return x;
    }

    private void chooseDataType() {
        scanner = new Scanner(System.in);
        int x = 0;
        while (x == 0) {
            System.out.println("stetische Daten[1] \t diskrete Daten[2]");
            int selection = scanner.nextInt();
            switch (selection) {
                case 1:
                    menueFrequencyTableSteady();
                    x = 1;
                    break;
                case 2:
                    menueFrequencyTableDiscreet();
                    x = 1;
                    break;

                default: System.out.println("Bitte 1 für stetische Daten oder 2 für diskrete Daten eingeben");
            }
        }
    }

    private void menueFrequencyTableDiscreet() {
        int selection = 0;
        int x = 0;
        scanner = new Scanner(System.in);
        while (x == 0) {
            int cnt = 1;
            System.out.println("Bitte gewünschte Tabelle wählen");
            for (String header : head) {
                if (DISCREETDATA.contains(header)) {
                    System.out.println("[" + cnt + "] " + header);
                    cnt++;
                }
            }
            System.out.println("[0] Zurück");
            System.out.println("Selection: ");
            selection = scanner.nextInt();

            x = showDT(selection);
        }
    }

    private void menueFrequencyTableSteady() {
        int x = 0;
        while (x == 0) {
            int cnt = 1;
            System.out.println("Bitte gewünschte Tabelle wählen:");
            for (String name : head) {
                if(STEADY_DATA.contains(name)) {
                    System.out.println("[" + cnt + "] " + name);
                    cnt++;
                }
            }
            System.out.println("[0] Zurück");
            System.out.println("Selection: ");
            int selection = scanner.nextInt();

            x = scSteadyTable(selection);
        }
    }

    private int showDT(int selection) {
        FrequencyDiscreetTable fdt = new FrequencyDiscreetTable();
        String[] vals;
        scanner = new Scanner(System.in);
        int maxElements = 0;
        int x = 0;
        int classifcation = 0;
        switch (selection) {
            case 1:
                classifcation = 0;
                maxElements = 2;
                break;
            case 2:
                classifcation = 2;
                maxElements = 4;
                break;
            case 3:
                classifcation = 8;
                maxElements = 3;
                break;

            case 0:
                x = 1;
                break;
        }
        vals = tb.getRow(classifcation);
        fdt = new FrequencyDiscreetTable(vals);
        fdt.generateTable(maxElements);

        System.out.println("Möchten Sie die Klassen graphisch dargestellt bekommen?");
        System.out.println("Ja(1) | Nein(2)");
        String input = scanner.nextLine();
        if(input.equals("1")){
            String[] labels = fdt.getdata();
            double[] values = fdt.getrelativCount();
            showBarChart(head[classifcation], labels, values);
        }

        return x;
    }


    private int scSteadyTable(int selection) {
        int x = 0;
        int classification = 0;
        int amountDecimal = 0;

        if (selection != 0 ) {
            amountDecimal = setDecimal();
        }
        switch (selection) {

            case 1:
                classification = 1;
                break;

            case 2:
                classification = 3;
                break;

            case 3:
                classification = 6;
                break;

            case 4:
                classification = 7;
                break;

            case 5:
                classification = 9;
                break;

            case 0:
                x = 1;
                break;
        }
        if(x != 1) {
            double classSpread = classWidth(classification, amountDecimal);
            showST(classSpread, classification);
        }

        return x;
    }

    private void tableOptions(){
        int x = 0;
        while (x == 0) {
            System.out.println("Bitte Eingabe machen:");
            System.out.println("[1] Tabelle einlesen");
            System.out.println("[2] Tabelle ausgeben");
            System.out.println("[3] Tabellenpfad bestimmen");
            System.out.println("[4] Zurück");
            System.out.println("Selection: ");
            int selection = scanner.nextInt();

            x = scTableOptions(selection);
        }
    }

    private int setDecimal() {
        int amount = 0;
        int x = 0;
        while (x != 1) {
            try {
                System.out.println("Bitte gewünschte Anzahl an Nachkommastellen angeben: (1-5)");
                amount = scanner.nextInt();
                if (1 <= amount && amount <= 5)
                    x = 1;
            } catch (InputMismatchException e ) {
                e.printStackTrace();
            }
        }
        return amount;
    }

    private int scTableOptions(int selection){
        int x = 0;
        switch (selection) {

            case 1:
                System.out.println("Tabelle wird eingelesen...");
                readTable();
                break;

            case 2:
                if (tableIsNotLoaded())
                    break;
                System.out.println("Tabelle wird ausgegeben...");
                printTable();

                break;

            case 3:
                System.out.println("Pfad = ");
                String path = scanner.next();
                if(path != ""){
                    this.file = path;
                }else {
                    System.out.println("Pfad ungültig; wurde nicht geändert!");
                }
                break;

            case 4:
                x = 1;
                break;
        }
        return x;
    }

    private void opMedian(){
        Double result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        System.out.println("-----------------------");
        System.out.println("Spalte: " + this.head[selection]);
        tmp = tb.getRow(selection);
        result = core.median(tmp);
        System.out.println("Ergebnis: " + result);
        System.out.println("-----------------------");
    }

    private void opArithmeticMean(){
        Double result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        System.out.println(LINE);
        System.out.println("Wahl: " + this.head[selection]);
        System.out.println("Option: Artithmetisches Mittel");
        tmp = tb.getRow(selection);
        result = core.arithmeticMean(tmp);
        System.out.println("Ergebnis: " + result);
        System.out.println(LINE);
    }

    private void opSpanRange() {
        Double result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        System.out.println("Wahl: " + this.head[selection]);
        if(STEADY_DATA.contains(this.head[selection])) {
            System.out.println(LINE);
            System.out.println("Option: Spannweite");
            tmp = tb.getRow(selection);
            result = core.spanRange(tmp);
            System.out.println("Ergebnis: " + result);
            System.out.println(LINE);
        } else {
            System.out.println("Nur stetische Daten sind für die Spannweite zugelassen!");
        }
    }

    //Streuung
    private void opSpread() {
        Double result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        System.out.println("Wahl: " + this.head[selection]);
            System.out.println(LINE);
            System.out.println("Option: Streuung");
            tmp = tb.getRow(selection);
            result = core.var(tmp);
            System.out.println("Ergebnis: " + result);
            System.out.println(LINE);

    }

    //Streuung
    private void opStandardDeviation() {
        Double result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        System.out.println("Wahl: " + this.head[selection]);
        System.out.println(LINE);
        System.out.println("Option: Standartabweichung");
        tmp = tb.getRow(selection);
        result = core.stddv(tmp);
        System.out.println("Ergebnis: " + result);
        System.out.println(LINE);

    }

    //Quartile
    private void opQuartile() {
        Double result;
        //int lowerQuant, upperQuant;
        System.out.println("Spalte = ");
        int row = scanner.nextInt();
        int amountdecimal = setDecimal();
        double classWidth = classWidth(row, amountdecimal);
        //System.out.println("Unteres Quartil = ");
        //lowerQuant = scanner.nextInt();
        //System.out.println("Oberes Quartil = ");
        //upperQuant = scanner.nextInt();
        System.out.println("Wahl: " + this.head[row]);
        System.out.println(LINE);
        System.out.println("Option: AlphaQuantile");
        tmp = tb.getRow(row);
        result = core.quartile(tmp, classWidth);
        System.out.println("Ergebnis: " + result);
        System.out.println(LINE);

    }

    private void opMode() {
        String result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        System.out.println("Wahl: " + this.head[selection]);
        if(DISCREETDATA.contains(this.head[selection])) {
            System.out.println(LINE);
            System.out.println("Option: Modus");
            tmp = tb.getRow(selection);
            result = core.mode(tmp);
            System.out.println("Ergebnis: " + result);
            System.out.println(LINE);
        } else {
            System.out.println("Nur diskrete Daten sind für den Modus zugelassen!");
        }
    }

    private int classification() {
        int coloumnCount = tr.getColoumncount();
        double classification = (Math.sqrt(coloumnCount));
        int roundedClassification = (int) Math.round(classification);


        return roundedClassification;
    }

    private double classWidth(int classifcation, int amountDecimal) {
        int k = classification();
        double minValue = 0.0;
        double maxValue = 0.0;
        if (classifcation == 1) {
            minValue = tr.getMinWeight();
            maxValue = tr.getMaxWeight();
        } else if(classifcation == 3) {
            minValue = tr.getMinAge();
            maxValue = tr.getMaxAge();
        } else if(classifcation == 6) {
            minValue = tr.getMinBD0();
            maxValue = tr.getMaxBD0();
        } else if(classifcation == 7) {
            minValue = tr.getMinBD12();
            maxValue = tr.getMaxBD12();
        } else if(classifcation == 9) {
            minValue = tr.getMinDif();
            maxValue = tr.getMaxDif();
        }

        double b = (maxValue - minValue)/k;
        double epsilon = Math.pow(baseToRound, (double)amountDecimal);
        double roundedB = Math.round(b * epsilon)/ epsilon;
        System.out.println("Klassenbreite für " + head[classifcation] + " ist " + roundedB);

        return roundedB;
    }

    private void showST(double classSpread, int classifcation){
        scanner = new Scanner(System.in);
        ArrayConverter ac = new ArrayConverter();
        double[] vals = ac.makeDouble(tb.getRow(classifcation));
        FrequencySteadyTable cft = new FrequencySteadyTable(classSpread, vals);
        cft.generateTable();

        System.out.println("Möchten Sie die Klassen graphisch dargestellt bekommen?");
        System.out.println("Ja(1) | Nein(2)");
        String input = scanner.nextLine();
        if(input.equals("1")){
            String[] labels = cft.getFrequencies();
            double[] values = cft.getFrequencyVals();
            showBarChart(head[classifcation], labels, values);
        }
    }

    private void showBarChart(String title, String[] labels, double[] values){
        setSystemUI();
        //JFrame.setDefaultLookAndFeelDecorated(true);
        final JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ChartController tc = new ChartController(values, labels, title);
        frame.add(tc);
        frame.setVisible(true);
    }

    private void setSystemUI(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void readTable(){
        tb = new Table();
        try {
            tb = tr.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        head = tb.getHead();
        content = tb.getContent();
    }

    private void printTable(){
        String header = "";
        String conLine  = "";

        for(int i = 0; i<head.length; i++){
            header +=head[i] + " | ";
        }
        System.out.println(header);

        for(int i = 0; i<content.length; i++){
            String[] tmp = content[i];
            conLine  = "";
            for(int k = 0; k<tmp.length; k++){
                conLine+= tmp[k] + " | ";
            }
            System.out.println(conLine);
        }
    }

    private boolean tableIsNotLoaded(){
        if (this.content == null && this.head == null){
            System.out.println(ERR_TABLE_NOT_LOADED);
            return true;
        }else{
            return  false;
        }
    }
}
