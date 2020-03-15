package meru.erp.mdm.catalog;

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
}
