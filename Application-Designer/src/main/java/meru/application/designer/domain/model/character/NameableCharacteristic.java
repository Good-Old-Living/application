package meru.application.designer.domain.model.character;


public class NameableCharacteristic extends Characteristic<String> {

  public static final String NAME = "nameable";
  
  public NameableCharacteristic(String value) {
   super(NAME, value);
  }
  
}
