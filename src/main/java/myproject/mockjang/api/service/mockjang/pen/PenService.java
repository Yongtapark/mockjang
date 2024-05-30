package myproject.mockjang.api.service.mockjang.pen;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PenService extends MockjangServiceAbstract {

  private final PenRepository penRepository;

  public Pen createPen(String penId, Barn barn) {
    codeIdFilter(penId);
    Pen pen = Pen.createPen(penId);
    pen.registerUpperGroup(barn);
    return penRepository.save(pen);
  }

  public void changeUpperGroup(Pen pen, Barn barn) {
    pen.changeUpperGroup(barn);
  }

  public List<Pen> findAll() {
    return penRepository.findAll();
  }

  public Pen findByCodeId(String codeId) {
    return penRepository.findByCodeId(codeId).orElseThrow(()->new NotExistException(COMMON_NOT_EXIST,codeId));
  }

  public void delete(Pen pen) {
    unlinkUpperGroup(pen);
    penRepository.delete(pen);
  }

}
