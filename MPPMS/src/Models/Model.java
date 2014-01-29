package Models;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Model {
    
    public abstract boolean save();
    
    protected abstract HashMap<String, String> getAttributesAndValues(final boolean includeId);
    
    protected abstract ArrayList<HashMap<String, Object>> getInnerAttributesAndValues();
    
    protected String wrapInSingleQuotes(String str) {
        return "'" + str + "'";
    }
}
