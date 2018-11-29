public class FrequencyDiscreetTable {

    private final String  HEADER[] = {"Hn(ai)","hn(ai)", "H(i)", "h(i)"};
    private String[] values;
    private String[] data;
    private int[] dataCnt;
    private double[] relativCount;

    public FrequencyDiscreetTable(){ }

    /**
     * constructor
     * @param values String array which will be sorted
     */
    public FrequencyDiscreetTable(String[] values){
        //String-sort after ASCII
        this.values = bubbleSort(values);
    }

    /**
     * method that generates our table
     * @param maxElements maximum size for data array
     */
    public void generateTable(int maxElements){
        printhead();
        int cntVals = values.length-1;
        dataCnt = new int[maxElements];
        data = new String[maxElements];
        relativCount = new double[maxElements];
        int cntLocal = 0;
        int cntGlobal = 0;
        int pointer = 0;
        float ratio = 0;
        data[0] = values[0]; //init data
        for(int i = 0; i < cntVals; i++) {

            if(values[i].equals(data[pointer])) {
                cntLocal++;
                cntGlobal++;

            }else{
                printClass(data[pointer], cntLocal, cntGlobal, cntVals);
                ratio = cntLocal;
                relativCount[pointer] = (ratio/cntVals);
                pointer++;
                data[pointer] = values[i];
                cntLocal = 1;
                cntGlobal++;
            }

        }
        ratio = cntLocal;
        relativCount[pointer] = (ratio/cntVals);
        printClass(data[pointer], cntLocal, cntGlobal, cntVals);
    }

    /**
     * get methods
     * @return the selected attribute
     */
    public String[] getdata(){
        return data;
    }

    public double[] getrelativCount(){
        return relativCount;
    }

    /**
     * method to print our table head into the console
     */
    private void printhead(){
        String header = "";
        for(int i = 0; i < HEADER.length; i++){
            header += HEADER[i] + " | ";
        }
        System.out.println(header);
    }

    /**
     * method to print the formula into the console
     * @param dataClass class of data
     * @param cntLocal local count of classes
     * @param cntGlobal global count of classes
     * @param cntVals count of our values
     */
    private void printClass(String dataClass, int cntLocal, int cntGlobal, int cntVals){
    System.out.println( dataClass + " | " + cntLocal + " | " + cntLocal + "/" + cntVals + " | " + cntGlobal + " | " + cntGlobal + "/" + cntVals);
    }


    /**
     * method to sort our array with a simple bubble sort
     * @param stringArray the String array which will be sorted
     * @return the sorted array 
     */
    public String[] bubbleSort(String[] stringArray) {
    int n = stringArray.length-1;
    String temp;
    for (int i = 0; i < n; i++) {
        for (int j = 1; j < (n - i); j++) {
            if (stringArray[j - 1].compareTo( stringArray[j] ) > 0) {
                temp = stringArray[j - 1];
                stringArray[j - 1] = stringArray[j];
                stringArray[j] = temp;
            }

        }
    }
        return stringArray;
    }
}
