import Models.PropertiesModel;

import javax.swing.*;
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
    //private static String FILE = "/Users/louisadort/Desktop/Robin_Uni/EuRusD/medizin.csv";
    private String LINE = "-----------------------";
    private final String ERR_TABLE_NOT_LOADED = "Tabelle muss erst geladen werden!";

    private PropertiesModel propertiesModel;

    private final int OP_ARI_MEAN = 0;
    private final int OP_MEDIAN = 1;
    private final int OP_SPAN = 2;
    private final int OP_MODE = 3;
    private final int OP_SPREAD = 4;
    private final int OP_STD_DEV = 5;
    private final int OP_QUARTILE = 6;
    private final int OP_CLASS_W = 7;

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
    private final String STEADY_DATA = "GEWICHT,ALTER,T0,T12,BD0,BD12,DIFF";
    private final String DISCREETDATA = "GESCHLECHT,BG,DZ";


    /**
     * constructor
     */
    public Main() {
        try {
            PropertiesModel.init("./properties.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        propertiesModel = new PropertiesModel();
        tr = new Tablereader(propertiesModel.getFilepath());
    }


    /**
     * main method to start the programdda
     * @param args
     */
    public static void main(String[] args){
        Main main = new Main();
        main.readTable();
        main.startLoop();
    }

    /**
     * first method which gets called to start the loop
     */
    private void startLoop()  {
        scanner = new Scanner(System.in);
        int x = 0;
            while (x == 0) {

                System.out.println("Bitte Eingabe machen:");
                System.out.println("[1] Tabelle ausgeben");
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
                String selection = scanner.next();
                x = mainSwitcher(selection);
            }

    }

    /**
     * method that runs different methods depending on what the user has tipped in the loop before
     * @param selection the user input that was given
     * @return the x variable -> x = 1 the menu will close
     */
    private int mainSwitcher(String selection){
        int x = 0;
        switch (selection) {

            case "1":
                printTable();
                break;

            case "2":
                if (tableIsNotLoaded())
                    break;

                opMakeOperation(OP_ARI_MEAN, "Arithmetisches Mittel");
                break;

            case "3":
                if (tableIsNotLoaded())
                    break;
                opMakeOperation(OP_MEDIAN, "Median");
                break;

            case "4":
                if (tableIsNotLoaded())
                    break;
                opMakeOperation(OP_SPAN, "Spannweite");
                break;

            case "5":
                if (tableIsNotLoaded())
                    break;
                opMakeOperation(OP_MODE, "Modus");
                break;

            case "6":
                if (tableIsNotLoaded())
                    break;
                opMakeOperation(OP_SPREAD, "Streuung");
                break;


            case "7":
                if (tableIsNotLoaded())
                    break;
                opMakeOperation(OP_STD_DEV, "Standartabweichung");
                break;

            case "8":
                if (tableIsNotLoaded())
                    break;
                opMakeOperation(OP_QUARTILE, "Quartile");
                break;

            case "9":
                if (tableIsNotLoaded())
                    break;
                chooseDataType();
                break;

            case "0":
                x = 1;
                break;
        }
        return x;
    }

    /**
     * method that asks the user which data type he wants to interact with
     * depending on what he chooses a different menu will be displayed after
     */
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

    /**
     * method that displays the menu for the discreet data
     */
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

    /**
     * method that displays the menu for the steady data
     */
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

    /**
     * method that calls the FrequencyDiscreetTable class
     * depending on what input was given a different array size and classification will be called in that class
     * @param selection the input the user has given before
     * @return the x variable -> x = 1 the menu will close
     */
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
        if(x!=1) {
            vals = tb.getRow(classifcation);
            fdt = new FrequencyDiscreetTable(vals);
            fdt.generateTable(maxElements);

            System.out.println("Möchten Sie die Klassen graphisch dargestellt bekommen?");
            System.out.println("Ja(1) | Nein(2)");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                String[] labels = fdt.getdata();
                double[] values = fdt.getrelativCount();
                showBarChart(head[classifcation], labels, values);
            }
        }
        return x;
    }

    /**
     * method that calls a method with different parameters depending on what the user has typed in before
     * @param selection the input that was given
     * @return the x variable -> x = 1 the menu will close
     */
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
                classification = 4;
                break;
            case 4:
                classification = 5;
                break;

            case 5:
                classification = 6;
                break;

            case 6:
                classification = 7;
                break;

            case 7:
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

    /**
     * method that asks the user how many decimals he wants to display
     * depending on what he chooses a different result in the calculations will be displayed
     * @return the amount of decimals the user has been choose (1-5)
     */
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

    private String[] getRows(){
        String[] arr;
        System.out.println("Spalte = ");
        String selection = scanner.next();
        if (selection.length() > 1) {
            arr = selection.split(",");
        }else{
            arr = new String[1];
            arr[0] = selection;
        }
        return arr;
    }

    private void opMakeOperation(int op, String opName){
        String result;
        String[] arr = getRows();
        System.out.println(LINE);
        System.out.println("Option: " + opName);
        for (String val:arr) {
            result = null;
            if(STEADY_DATA.contains(this.head[Integer.valueOf(val)]))
                System.out.println("Spalte: " + this.head[Integer.valueOf(val)]);
            tmp = tb.getRow(Integer.valueOf(val));
            if(op == OP_ARI_MEAN){
                if(STEADY_DATA.contains(this.head[Integer.valueOf(val)]))
                    result = String.valueOf(core.arithmeticMean(tmp));
            }else if(op == OP_SPAN){
                if(STEADY_DATA.contains(this.head[Integer.valueOf(val)]))
                    result = String.valueOf(core.spanRange(tmp));
            }else if(op == OP_SPREAD){
                if(STEADY_DATA.contains(this.head[Integer.valueOf(val)]))
                    result = String.valueOf(core.var(tmp));
            }else if(op == OP_MEDIAN){
                if(STEADY_DATA.contains(this.head[Integer.valueOf(val)]))
                    result = String.valueOf(core.median(tmp));
            }else if(op == OP_MODE){
                result = core.mode(tmp);
            }else if(op == OP_STD_DEV){
                if(STEADY_DATA.contains(this.head[Integer.valueOf(val)]))
                    result = String.valueOf(core.stddv(tmp));
            }else if(op == OP_QUARTILE){
                if(STEADY_DATA.contains(this.head[Integer.valueOf(val)])) {
                    int amountdecimal = setDecimal();
                    double classWidth = classWidth(Integer.valueOf(val), amountdecimal);
                    result = String.valueOf(core.quartile(tmp, classWidth));
                }
            }
            if(STEADY_DATA.contains(this.head[Integer.valueOf(val)]))
                System.out.println("Ergebnis: " + result);

        }
        System.out.println(LINE);
    }
    /**
     * method that calls the median method in our Core class
     */
    private void opMedian(){
        Double result;
        String[] arr = getRows();
        System.out.println(LINE);
        System.out.println("Option: Median");
        for (String val:arr) {
            if(STEADY_DATA.contains(this.head[Integer.valueOf(val)])) {
                System.out.println("Spalte: " + this.head[Integer.valueOf(val)]);
                tmp = tb.getRow(Integer.valueOf(val));
                result = core.median(tmp);
                System.out.println("Ergebnis: " + result);
            }
        }
        System.out.println(LINE);
    }

    /**
     * method that calls the arithmeticMean method in our Core class
     */
    private void opArithmeticMean(){
        Double result;
        String[] arr = getRows();
        System.out.println(LINE);
        System.out.println("Option: Arithmetisches Mittel");
        for (String val:arr) {
            if (STEADY_DATA.contains(this.head[Integer.valueOf(val)])) {

                System.out.println("Spalte: " + this.head[Integer.valueOf(val)]);
                tmp = tb.getRow(Integer.valueOf(val));
                result = core.arithmeticMean(tmp);
                System.out.println("Ergebnis: " + result);
            }
        }
        System.out.println(LINE);
    }

    /**
     * method that calls the spanRange method in our Core class
     */
    private void opSpanRange() {
        Double result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        if(STEADY_DATA.contains(this.head[selection])) {
            System.out.println(LINE);
            System.out.println("Option: Spannweite");
            System.out.println("Spalte: " + this.head[selection]);
            tmp = tb.getRow(selection);
            result = core.spanRange(tmp);
            System.out.println("Ergebnis: " + result);
            System.out.println(LINE);
        } else {
            System.out.println("Nur stetische Daten sind für die Spannweite zugelassen!");
        }
    }

    /**
     * method that calls the var method in our Core class (spread)
     */
    //Streuung
    private void opSpread() {
        Double result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        System.out.println(LINE);
        System.out.println("Option: Streuung");
        System.out.println("Spalte: " + this.head[selection]);
        tmp = tb.getRow(selection);
        result = core.var(tmp);
        System.out.println("Ergebnis: " + result);
        System.out.println(LINE);

    }

    /**
     * method that calls the stddv method in our Core class (standard deviation)
     */
    //Streuung
    private void opStandardDeviation() {
        Double result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
        System.out.println(LINE);
        System.out.println("Option: Standartabweichung");
        System.out.println("Spalte: " + this.head[selection]);
        tmp = tb.getRow(selection);
        result = core.stddv(tmp);
        System.out.println("Ergebnis: " + result);
        System.out.println(LINE);

    }

    /**
     * method that calls the quartile method in our Core class
     */
    //Quartile
    private void opQuartile() {
        Double result;
        System.out.println("Spalte = ");
        int row = scanner.nextInt();
        int amountdecimal = setDecimal();
        double classWidth = classWidth(row, amountdecimal);
        System.out.println(LINE);
        System.out.println("Option: AlphaQuantile");
        System.out.println("Spalte: " + this.head[row]);
        tmp = tb.getRow(row);
        result = core.quartile(tmp, classWidth);
        System.out.println("Ergebnis: " + result);
        System.out.println(LINE);

    }

    /**
     * method that calls the mode method in our Core class
     */
    private void opMode() {
        String result;
        System.out.println("Spalte = ");
        selection = scanner.nextInt();
            System.out.println(LINE);
            System.out.println("Option: Modus");
            System.out.println("Spalte: " + this.head[selection]);
            tmp = tb.getRow(selection);
            result = core.mode(tmp);
            System.out.println("Ergebnis: " + result);
            System.out.println(LINE);

    }

    /**
     * method that calculates the classification of our table in the medicine file
     * @return the calculated classification
     */
    private int classification() {
        int coloumnCount = tr.getColoumncount();
        double classification = (Math.sqrt(coloumnCount));
        int roundedClassification = (int) Math.round(classification);


        return roundedClassification;
    }

    /**
     * method that calculates the class width from a specific table column in our medicine file
     * @param classifcation the given calculated classification
     * @param amountDecimal the amount of decimals the user has been choose
     * @return the calculated class with of the table column
     */
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
        } else if(classifcation == 4) {
            minValue = tr.getMinT0();
            maxValue = tr.getMaxT0();
        } else if(classifcation == 5) {
            minValue = tr.getMinT12();
            maxValue = tr.getMaxT12();
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

    /**
     * method that calls the FrequencySteadyTable class
     * depending on what input was given a different class width and classification will be called in that class
     * @param classSpread the given class width
     * @param classifcation the calculated classification
     */
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

    /**
     * method that displays a Bar chart to a specific table column
     * @param title the title of the bar chart
     * @param labels intervals
     * @param values the data that will be displayed as a bar chart
     */
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

    /**
     * method for windows to set window to the systems UI
     */
    private void setSystemUI(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method that reads the medicine file
     */
    private void readTable(){
        tb = new Table();
        try {
            tb = tr.read();
            System.out.println("Tabelle wurde erfolgreich eingelesen!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        head = tb.getHead();
        content = tb.getContent();
    }

    /**
     * method that displays the table in the console
     */
    private void printTable(){
        if (!tableIsNotLoaded()) {
            System.out.println("Tabelle wird ausgegeben...");
            String header = "";
            String conLine = "";

            for (int i = 0; i < head.length; i++) {
                header += head[i] + " | ";
            }
            System.out.println(header);

            for (int i = 0; i < content.length; i++) {
                String[] tmp = content[i];
                conLine = "";
                for (int k = 0; k < tmp.length; k++) {
                    conLine += tmp[k] + " | ";
                }
                System.out.println(conLine);
            }
        }
    }

    /**
     * method that checks if the table is already loaded
     * @return true if it is loaded / false otherwise
     */
    private boolean tableIsNotLoaded(){
        if (this.content == null && this.head == null){
            System.out.println(ERR_TABLE_NOT_LOADED);
            return true;
        }else{
            return  false;
        }
    }
}
