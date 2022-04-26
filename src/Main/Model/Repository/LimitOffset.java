package Main.Model.Repository;

public class LimitOffset {
    private  int LIMIT = 2;
    private int limit;
    private int offset;
    private int page;
    private int allPages = 100;
    private int items;

    public LimitOffset(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public LimitOffset(int page) {
        this.limit = this.LIMIT;
        this.setPage(page);
    }

    public LimitOffset() {
        this.limit = this.LIMIT;
        this.setPage(1);
    }

    public void setPage(int page) {
        if(page>= 1 ){
            this.offset = (page-1)*this.limit;
            this.page = page;
        }
    }

    public String getStringPages(){
        String p = " page";
        if(this.allPages>1) p+="s";
        return this.page + "/" + this.allPages+ p;
    }

    public String getPreparedSql(){
        return " LIMIT ? OFFSET ? ";
    }
    public String getSql(){
        return " LIMIT "+this.limit+" OFFSET "+this.offset+" ";
    }
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
        this.allPages = (int) Math.ceil((float)items/this.getLimit());
        if (this.allPages==0) this.allPages=1;
    }
    public void next(){
        if(this.page<this.allPages) this.setPage(this.page+1);
    }
    public void prev(){
        if(this.page>1) this.setPage(this.page-1);
    }
}
