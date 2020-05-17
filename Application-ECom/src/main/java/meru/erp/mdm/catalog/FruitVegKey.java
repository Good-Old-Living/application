package meru.erp.mdm.catalog;

import java.util.ArrayList;
import java.util.List;

public enum FruitVegKey {

  
  OrganicFruits(101),
  OrganicVegetable(102),
  OrganicGreens(103);
  
  private long key;
  
  private FruitVegKey(long key) {
    this.key = key;
  }
  
  public long getKey() {
    return key;
  }
  
  public static List<Long> asList() {
    List<Long> list = new ArrayList<>(3);
    for (FruitVegKey key : FruitVegKey.values()) {
      list.add(key.getKey());
    }
    
    return list;
  }
}
