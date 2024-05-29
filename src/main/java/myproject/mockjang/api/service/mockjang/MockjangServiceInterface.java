package myproject.mockjang.api.service.mockjang;

import java.util.List;

public interface MockjangServiceInterface<T> {

  List<T> findAll();
  /*List<T> findAllWhereDeletedTrue();*/

  T findByCodeId(String codeId);

  void delete(T mockjang);
}
