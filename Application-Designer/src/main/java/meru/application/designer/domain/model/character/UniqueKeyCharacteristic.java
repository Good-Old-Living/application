package meru.application.designer.domain.model.character;

import java.util.ArrayList;
import java.util.List;

import meru.sys.lang.StringHelper;


public class UniqueKeyCharacteristic extends Characteristic<List<String[]>> {

  public static final String NAME = "uniqueKey";
  public static final String SHORT_NAME = "UK";
  
  public UniqueKeyCharacteristic(String values) {
   super(NAME, parseValues(values));
  }
  
  
  private static List<String[]> parseValues(String values) {
    
    List<String[]> keyList = new ArrayList<String[]>(1);
    
    String[] keys = values.split("[|]");
    for (String key : keys) {
      
      String[] vals = key.split("[,]");
      for (int i=0; i<vals.length; i++) {
        vals[i] = vals[i].trim();
      }
      
      keyList.add(vals);
    }

    
    return keyList;
  }
  
  public String toString() {
      
      StringBuilder strBuilder = new StringBuilder(mName+" : ");
      for (String[] strs : mValue) {
          strBuilder.append(StringHelper.toCommaSeperatedString(strs)).append(" | ");
      }
      return strBuilder.toString();
  }
  
  protected boolean valueEquals(List<String[]> value) {
      
      if (mValue.size() != value.size()) {
          return false;
      }
      
      for (int i=0; i<mValue.size(); i++) {
          String[] strs1 = mValue.get(i);
          String[] strs2 = value.get(i);
      
          if (strs1.length != strs2.length) {
              return false;
          }
          
          for (int j=0; j<strs1.length; j++) {
              if (!strs1[j].equals(strs2[j])) {
                  return false;
              }
          }
          
      }
      
      return true;
      
  }
}
