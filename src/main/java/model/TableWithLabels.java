package model;

import java.util.HashMap;
import java.util.Map;

public class TableWithLabels extends Table {

    private Map<String, Integer> labelsToIndex;

    public TableWithLabels() {
        super();
        labelsToIndex = new HashMap<>();
    }

    @Override
    public RowWithLabel getRowAt(int rowNumber) {
        return (RowWithLabel) super.getRowAt(rowNumber);
    }

    public Integer getLabelAsInteger(String label) {
        if (!labelsToIndex.containsKey(label)) {
            labelsToIndex.put(label, labelsToIndex.size());
        }
        return labelsToIndex.get(label);
    }

    public int getNumRows() {
        return labelsToIndex.size();
    }
}