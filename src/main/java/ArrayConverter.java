public class ArrayConverter {


    /**
     * method that converts a String array into a double array
     * @param vals the given String array
     * @return the converted double array
     */
    public double[] makeDouble(String[] vals){
        try {
            double[] result = new double[vals.length - 1];

            for (int i = 0; i < vals.length - 1; i++) {
                vals[i] = vals[i].replace(",", ".");
                result[i] = Double.parseDouble(vals[i]);
            }
            return result;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * method that converts a String array into a integer array
     * @param vals the given String array
     * @return the converted integer array
     */
    public int[] makeInt(String[] vals){
        int[] result = new int[vals.length];

        for(int i = 0; i<=vals.length; i++){
            result[i] = Integer.parseInt(vals[i]);

        }

        return result;
    }


}
