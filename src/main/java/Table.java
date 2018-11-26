public class Table {

    private String[] head;
    private String[][] content;
    private int loc; //Lines of content;


    /**
     * constructors
     * class sets the head of our medicine file into the head array and the content columns of our file into the content array 
     */
    public Table(){}

    public Table(String[] head, String[][] content, int loc) {
        setHead(head);
        setContent(content);
        this.loc = loc;
    }

    /**
     * get method
     * @return the head of our table
     */
    public String[] getHead(){
        return this.head;
    }

    /**
     * get method
     * @return the content of our table
     */
    public String[][] getContent(){
        return this.content;
    }

    /**
     * set method
     * @param content the content that will be set
     */
    public void setContent(String[][] content){
        this.content = content;
    }

    /**
     * set method
     * @param head the head that will be set
     */
    public void setHead(String[] head){
        this.head = head;
    }

    /**
     * get method
     * @param row the row you want to get
     * @return the given row
     */
    public String[] getRow(int row){
        String[] arr;
        //arr = this.content[row];
        arr = new String[loc];
        for(int i = 0; i<loc-1; i++){
            arr[i] = this.content[i][row];
        }

        return arr;
    }

}
