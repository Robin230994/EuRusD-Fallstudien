public class Core {

    /**
     * method to calculate the arithmetic mean for some column of our table
     * @param vals the String array which contains the data to calculate with
     * @return the arithmetic mean
     */
    public double arithmeticMean(String[] vals) {
        ArrayConverter converter = new ArrayConverter();
        double[] arr = converter.makeDouble(vals);
        double result = 0;

        for(int i = 0; i < arr.length; i++) {
            result += arr[i];
        }
        result = result / arr.length;   //Changed!

        return  result;
    }


    /**
     * method to calculate the median for some column of our table
     * @param arr the String array which contains the data to calculate with
     * @return the median
     */
    public double median(String[] arr){
        double result;
        int length;
        ArrayConverter converter = new ArrayConverter();
        double[] vals = converter.makeDouble(arr);
        double lMedian, uMedian;
        length = vals.length;   //Changed!


        if (length % 2 == 0) {
            lMedian = vals[length/2-1]; //Changed!
            uMedian = vals[length/2];
            result = (lMedian + uMedian)/2;

        } else {
            lMedian = vals[(length + 1)/2];
            result = lMedian;
        }

        return  result;
    }

    public double median(double[] arr){
        double result;
        int length;
        double[] vals = arr;
        double lMedian, uMedian;
        length = vals.length;   //Changed!


        if (length % 2 == 0) {
            lMedian = vals[length/2-1]; //Changed!
            uMedian = vals[length/2];
            result = (lMedian + uMedian)/2;

        } else {
            lMedian = vals[(length + 1)/2];
            result = lMedian;
        }

        return  result;
    }

    /**
     * method to calculate the span range for some column of our table
     * @param vals the String array which contains the data to calculate with
     * @return the span range 
     */
    public double spanRange(String[] vals) {
        ArrayConverter converter = new ArrayConverter();
        double span = 0.0;
            double doubleArray[] = converter.makeDouble(vals);
            doubleArray = FrequencySteadyTable.sort(doubleArray);
            span = doubleArray[doubleArray.length - 1] - doubleArray[0];
        return span;
    }

    public  String mode(String a[]) {
        String maxValue = "";
        int maxCount = 0;

        for (int i = 0; i < a.length-1; ++i) {
            int count = 0;
            for (int j = 0; j < a.length-1; ++j) {
                if (a[j].equals(a[i]))
                    ++count;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = a[i];
            }
        }

        return maxValue;
    }

    // Berechnet die Standardabweichung
    public double stddv(String[] a) {
        return Math.sqrt(var(a));
    }

    // Berechnet die korrigierte Stichprobenvarianz
    public double var(String[] var) {
        ArrayConverter ac = new ArrayConverter();
        double[] a = ac.makeDouble(var);
        double m = median(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + (a[i] - m) * (a[i] - m);
        }
        return sum / (a.length - 1);
    }

    public double var2(String[] var) {
        ArrayConverter ac = new ArrayConverter();
        double[] a = ac.makeDouble(var);
        double m = median(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            //sum += Math.pow(a[i], 2) - a.length * Math.pow(m, 2);
            sum += Math.pow(a[i] - m, 2);
        }
        int len = a.length-1;
        sum = sum/len;
        return sum ;
    }

    public double quartile(String[] var, double classWidth) { //, int lowerPercent, int upperPercent

        ArrayConverter ac = new ArrayConverter();
        double[] values = ac.makeDouble(var);
        double quantile = 0.0;
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("The data array either is null or does not contain any data.");
        }
        values = FrequencySteadyTable.sort(values);
        double median = median(values);
        int classOfMedian = 1;
        double upperBorder = values[0] + classWidth;
        for(int i = 0; i < 5; i++){
            if (median > upperBorder ){
                classOfMedian++;
                upperBorder += classWidth;
            }else{
                i = 5;
            }
        }
        double lowerborder = upperBorder - classWidth;
        int cntHiMinusOne = 0;
        int cnthi = 0;
        for(int i = 0; i < values.length; i++){
            if (values[i] < lowerborder) {
                cntHiMinusOne++;
            }
            if (values[i] < upperBorder) {
                cnthi++;
            }
        }
        cnthi = cnthi - cntHiMinusOne;

        double a = (double) cntHiMinusOne/(double) values.length;
        double b = (double) cnthi/ (double) values.length;
        quantile = ((0.5 - a)/(b)) * classWidth + lowerborder;

        return quantile;

    }
}
