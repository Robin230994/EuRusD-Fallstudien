import java.text.DecimalFormat;
import java.util.Arrays;

public class FrequencySteadyTable {

    private final String[] HEAD = {"Ki","Hn(Ki)", "hn(Ki)", "H(i)", "h(i)"};
    private double classWidth;
    private double[] values;
    private String[] frequencies;
    private double[] frequencyVals;

    public FrequencySteadyTable(double classWidth, double[] values){
        this.classWidth = classWidth;
        this.values = values;
    }

    public void generateTable(){
        values = sort(values);
        printhead();
        int cntVals = values.length;
        int cntLocal = 0;
        int cntGlobal = 0;
        int freqCnt = 0;
        float ratio;
        int freqWidth;
        double freqW = (values[cntVals-1] - values[0])/classWidth;  // Highest Value - lowest value divided by classwidht
        DecimalFormat df = new DecimalFormat("#");          //
        freqW = Double.valueOf(df.format(freqW));                   // Need to round because of arraysize
        freqWidth = (int) freqW;                                    // (4,99 would be 4 instead of 5)
        frequencies = new String[freqWidth+1];
        frequencyVals = new double[freqWidth+1];
        double lowerBorder = values[0];
        double upperBorder = values[0] + classWidth;

        for(int i = 0; i < cntVals; i++) {
            if(values[i] < upperBorder) {
                cntLocal++;
                cntGlobal++;

            }else{
                ratio = cntLocal;
                frequencyVals[freqCnt] = ratio/cntVals;
                frequencies[freqCnt] = getClasslabel(lowerBorder, upperBorder);
                freqCnt++;
                printClass(lowerBorder, upperBorder, cntLocal, cntGlobal, cntVals);
                cntLocal = 0;
                lowerBorder = lowerBorder + classWidth;
                upperBorder = upperBorder + classWidth;
                cntGlobal++;
                cntLocal++;
            }

        }
        ratio = cntLocal;
        ratio = ratio/cntVals;
        frequencyVals[freqCnt] = ratio;
        frequencies[freqCnt] = getClasslabel(lowerBorder, upperBorder);
        frequencies = cleanArray(frequencies);
        frequencyVals = cleanArray(frequencyVals);
        printClass(lowerBorder, upperBorder, cntLocal, cntGlobal, cntVals);
    }

    public String[] getFrequencies(){
        return frequencies;
    }

    public double[] getFrequencyVals(){
        return frequencyVals;
    }

    private String getClasslabel(double lowerBorder, double upperBorder){
        return "[" + String.format("%.2f", lowerBorder) + ";" + String.format("%.2f", upperBorder) + "]";
    }

    private void printClass(double lowerBorder, double upperBorder, int cntLocal, int cntGlobal, int cntVals){
        System.out.println( getClasslabel(lowerBorder, upperBorder) + " | " + cntLocal + " | " + cntLocal + "/"
                + cntVals + " | " + cntGlobal + " | " + cntGlobal + "/" + cntVals);
    }

    private void printhead(){
        String header = "";
        for(int i = 0; i<HEAD.length; i++){
            header += HEAD[i] + " | ";
        }
        System.out.println(header);
    }

    private double[] cleanArray(double[] vals){
        String s = "";
        for (double d: vals) {
            if (d != 0.0){
                s += d + ":";
            }
        }
        String[] arr = s.split(":");
        vals = Arrays.stream(arr)
                .mapToDouble(Double::parseDouble)
                .toArray();
        return vals;
    }

    private String[] cleanArray(String[] vals){
        String tmp = "";
        for (String s: vals) {
            if (s != null && !s.equals("")){
                tmp += s + ":";
            }
        }
        String[] arr = tmp.split(":");
        return arr;
    }

    public static double[] sort(double[] vals){
        double temp;
        for(int i=1; i<vals.length; i++) {
            for(int j=0; j<vals.length-i; j++) {
                if(vals[j]>vals[j+1]) {
                    temp=vals[j];
                    vals[j]=vals[j+1];
                    vals[j+1]=temp;
                }

            }
        }
        return vals;
    }
}
