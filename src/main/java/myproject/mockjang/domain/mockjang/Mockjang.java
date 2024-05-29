package myproject.mockjang.domain.mockjang;

public interface Mockjang {

  Mockjang getUpperGroup();

  void registerUpperGroup(Mockjang upperGroup);

  void changeUpperGroup(Mockjang mockjang);

  void removeOneOfUnderGroups(Mockjang mockjang);

}
