import java.util.Arrays;

public class FrequencyDiscreetTable {

        private final String  HEADER[] = {"Hn(ai)","hn(ai)", "H(i)", "h(i)"};
        private String[] values;

        private String[] data;
        private int[] dataCnt;
        private double[] relativCount;

    public FrequencyDiscreetTable(){ }

        public FrequencyDiscreetTable(String[] values){
                //String-sort after ASCII
            this.values = bubbleSort(values);
        }

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

        public String[] getdata(){
            return data;
        }

        public double[] getrelativCount(){
            return relativCount;
        }


        private void printhead(){
            String header = "";
            for(int i = 0; i < HEADER.length; i++){
                header += HEADER[i] + " | ";
            }
            System.out.println(header);
        }

    private void printClass(String dataClass, int cntLocal, int cntGlobal, int cntVals){
        System.out.println( dataClass + " | " + cntLocal + " | " + cntLocal + "/" + cntVals + " | " + cntGlobal + " | " + cntGlobal + "/" + cntVals);
    }

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
