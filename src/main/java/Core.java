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

    /**
     * method to calculate the median with a double array
     * @param arr the given double array which contains the data
     * @return the median
     */
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

    /**
     * method to calculate the mode for some column of our table
     * @param a the array which contains the data to calculate with
     * @return the mode
     */
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

    /**
     * method to calculate the spread for some column row of our table
     * @param var the String array which contains the data to calculate with
     * @return the spread
     */
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

    /**
     * method to calculate the quartile for some column of our table
     * @param var the String array which contains the data
     * @param classWidth the classwidth you calculated before
     * @return the quartile 
     */
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
