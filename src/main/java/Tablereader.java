import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Tablereader {

    // NORMAL VARIABLES
    private String[][] table;
    private String[] head;
    private String[] tmp;
    private int count;
    private int rowcount;
    File file;

    // COUNT VARIABLES
    private double minWeight = 0.0;
    private double maxWeight = 0.0;
    private double minAge = 0.0;
    private double maxAge = 0.0;
    private double minBD0 = 0.0;
    private double maxBD0 = 0.0;
    private double minBD12 = 0.0;
    private double maxBD12 = 0.0;
    private double minDif = 0.0;
    private double maxDif = 0.0;
    private int anzZero = 0;
    private int anzOne = 0;
    private int anzBG0 = 0;
    private int anzBGA = 0;
    private int anzBGB = 0;
    private int anzBGAB = 0;
    private int anzDZ1 = 0;
    private int anzDZ2 = 0;
    private int anzDZ3 = 0;

    // FINAL VARIABLES
    private final int firstValue = 0;
    private final int lastValue = 29;
    private final int GESCHLECHT = 1;
    private final int GEWICHT = 2;
    private final int BG = 3;
    private final int ALTER = 4;
    private final int BD0 = 7;
    private final int BD12 = 8;
    private final int DZ = 9;
    private final int DIF = 10;


    /**
     * constructor
     * @param path path to the file that will be read
     */
    public Tablereader(String path){
        this.file = new File(path);
    }


    /**
     * method that reads our medicine file and sets the different variables to calculate with them
     * @return the read table of our file
     * @throws IOException
     */
    public Table read() throws IOException {

        int[] res = getResolution();
        int lines = res[0];
        int rows = res[1];

        table = new String[lines-1][rows];
        head = new String[rows];

        Table tb;
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        count = -1;

        while ((st = br.readLine()) != null) {
            if (count == -1) {
                head = st.split(";");
            } else {
                rowcount = 0;
                tmp = st.split(";");
                for (String val: tmp) {
                    table[count][rowcount] = val;
                    rowcount++;
                    String valueWithoutComma = val.replaceAll(",",".");
                    double valToDouble = 0.0;
                    switch (rowcount) {
                        //min/max Gewicht
                        case GESCHLECHT:
                            if (val.equals("0")) {
                                anzZero++;
                            } else {
                                anzOne++;
                            }
                            break;
                        case GEWICHT:
                            valToDouble = Double.valueOf(valueWithoutComma);
                            if(count == firstValue) {
                                minWeight = valToDouble;
                            }

                            if (count == lastValue ) {
                                maxWeight = valToDouble;
                            }
                        break;

                        case BG:
                            if(val.equals("0")) {
                                anzBG0++;
                            } else if (val.equals("A")) {
                                anzBGA++;
                            } else if (val.equals("B")) {
                                anzBGB++;
                            } else if (val.equals("AB")){
                                anzBGAB++;
                            }
                            break;
                        //min/max Alter
                        case ALTER:
                            valToDouble = Double.valueOf(valueWithoutComma);
                            if (count == firstValue) {
                                minAge = valToDouble;
                                maxAge = valToDouble;
                            }
                            if (valToDouble > maxAge) {
                                maxAge = valToDouble;
                            }
                            if (valToDouble < minAge) {
                                minAge = valToDouble;
                            }
                        break;
                        //min/max BD0
                        case BD0:
                            valToDouble = Double.valueOf(valueWithoutComma);
                            if (count == firstValue) {
                                minBD0 = valToDouble;
                                maxBD0 = valToDouble;
                            }
                            if (valToDouble > maxBD0) {
                                maxBD0 = valToDouble;
                            }
                            if (valToDouble < minBD0) {
                                minBD0 = valToDouble;
                            }
                            break;

                        case BD12:
                            valToDouble = Double.valueOf(valueWithoutComma);
                            if (count == firstValue) {
                                minBD12 = valToDouble;
                                maxBD12 = valToDouble;
                            }
                            if (valToDouble > maxBD12) {
                                maxBD12 = valToDouble;
                            }
                            if (valToDouble < minBD12) {
                                minBD12 = valToDouble;
                            }
                            break;

                        case DZ:
                            if (val.equals("1")) {
                                anzDZ1++;
                            } else if (val.equals("2")) {
                                anzDZ2++;
                            } else if (val.equals("3")) {
                                anzDZ3++;
                            }
                            break;

                        case DIF:
                            valToDouble = Double.valueOf(valueWithoutComma);
                            if (count == firstValue) {
                                minDif = valToDouble;
                                maxDif = valToDouble;
                            }
                            if (valToDouble > maxDif) {
                                maxDif = valToDouble;
                            }
                            if (valToDouble < minDif) {
                                minDif = valToDouble;
                            }
                            break;

                    }

                }
                //table[count][rowcount] = Integer.valueOf(tmp);
            }
            count++;
            //System.out.println(st);
        }
        setColoumnCount(count);
        tb = new Table(head, table, lines);

        return tb;
    }

    /**
     * method that calculates the needed array size
     * @return the calculated size
     * @throws IOException
     */
    private int[] getResolution() throws IOException {

        int[] res = new int[2];
        String st;
        int lines = 0;
        int rows = 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((st = br.readLine()) != null) {
            lines++;
            rows = st.length() - st.replace(";", "").length() +1;
        }

        res[0] = lines;
        res[1] = rows;

        return res;
    }

    /**
     * set and get methods
     */
    private void setColoumnCount(int count) {
        this.count = count;
    }

    public int getColoumncount() {
        return this.count;
    }

    public double getMinWeight() {
        return this.minWeight;
    }

    public double getMaxWeight() {
        return this.maxWeight;
    }

    public double getMinAge() {
        return this.minAge;
    }

    public double getMaxAge() {
        return this.maxAge;
    }

    public double getMinBD0() {
        return this.minBD0;
    }

    public double getMaxBD0() {
        return this.maxBD0;
    }

    public double getMinBD12() {
        return this.minBD12;
    }

    public double getMaxBD12() {
        return this.maxBD12;
    }

    public double getMinDif() {
        return this.minDif;
    }

    public double getMaxDif() {
        return this.maxDif;
    }

    public int getAnzZero() {
        return this.anzZero;
    }

    public int getAnzOne() {
        return this.anzOne;
    }

    public int getAnzBG0() {
        return this.anzBG0;
    }

    public int getAnzBGA() {
        return this.anzBGA;
    }

    public int getAnzBGB() {
        return this.anzBGB;
    }

    public int getAnzBGAB() {
        return this.anzBGAB;
    }

    public int getAnzDZ1() {
        return this.anzDZ1;
    }

    public int getAnzDZ2() {
        return this.anzDZ2;
    }

    public int getAnzDZ3() {
        return this.anzDZ3;
    }

    public String[][] getTable() {
        return this.table;
    }
}
