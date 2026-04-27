package practica3;

import practica1.Table;

public abstract class ReaderTemplate<T extends Table> {
    protected T table;
    protected String source;
    abstract void openSource(String source);
    abstract void processHeaders(String headers);
    abstract void processData(String data);
    abstract void closeSource();
    abstract boolean hasMoreData();
    abstract String getNextData();
    public ReaderTemplate(T table, String source) {
        this.table = table;
        this.source = source;
    }

    public final T readTableFromSource(){
        openSource(source);
        String headers=getNextData();
        processHeaders(headers);
        while(hasMoreData()){
            String data=getNextData();
            processData(data);
        }
        closeSource();
        return table;
    }
    public ReaderTemplate(T table) {
        this.table = table;
    }


}
