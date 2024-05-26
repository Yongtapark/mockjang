package myproject.mockjang.api.service.mockjang.pen;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PenService {

  private final PenRepository penRepository;

  public Pen createPen(String penId,Barn barn) {
    Pen pen = Pen.createPen(penId);
    pen.registerBarn(barn);
    return penRepository.save(pen);
  }

  public void deletePen(Pen pen) {
    unlinkUpperGroup(pen);
    penRepository.delete(pen);
  }

  private void unlinkUpperGroup(Pen pen) {
    Barn barn = pen.getBarn();
    barn.deletePen(pen);
  }

  public void changeBarn(Pen pen,Barn barn) {
    pen.changeBarnTo(barn);
  }

}
