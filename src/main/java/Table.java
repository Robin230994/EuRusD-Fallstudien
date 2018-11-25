public class Table {

    private String[] head;
    private String[][] content;
    private int loc; //Lines of content;


    public Table(){}

    public Table(String[] head, String[][] content, int loc) {
        setHead(head);
        setContent(content);
        this.loc = loc;
    }

    public String[] getHead(){
        return this.head;
    }

    public String[][] getContent(){
        return this.content;
    }

    public void setContent(String[][] content){
        this.content = content;
    }

    public void setHead(String[] head){
        this.head = head;
    }

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
