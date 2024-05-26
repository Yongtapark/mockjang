package myproject.mockjang.api.service.mockjang.barn;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangService;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.StringException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BarnService extends MockjangService {

    private final BarnRepository barnRepository;

    public Barn createBarn(String barnId) {
        codeIdFilter(barnId);
        Barn barn = Barn.createBarn(barnId);
        return barnRepository.save(barn);
    }

    public void deleteBarn(Barn barn) {
        barnRepository.delete(barn);
    }



}
