package myproject.mockjang.api.service.mockjang;

import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.StringException;

public abstract class MockjangServiceAbstract {

  static final int MAXIMUM_LENGTH = 10;

  protected void codeIdFilter(String codeId) {
    if (codeId.isBlank()) {
      throw new StringException(Exceptions.COMMON_BLANK_STRING);
    }
    if (codeId.length() > MAXIMUM_LENGTH) {
      throw new StringException(Exceptions.COMMON_STRING_OVER_10);
    }
  }

  protected void unlinkUpperGroup(Mockjang mockjang) {
    Mockjang upperGroup = mockjang.getUpperGroup();
    upperGroup.removeOneOfUnderGroups(mockjang);
  }
}
