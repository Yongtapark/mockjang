package myproject.mockjang.api.service.mockjang.barn;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.api.service.mockjang.barn.request.BarnCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.barn.response.BarnResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BarnService extends MockjangServiceAbstract {

    private final BarnRepository barnRepository;

    public BarnResponse createBarn(BarnCreateServiceRequest request) {
        String barnCode = request.getCodeId();
        codeIdFilter(barnCode);
        Barn barn = Barn.createBarn(barnCode);
        Barn savedBarn = barnRepository.save(barn);
        return BarnResponse.of(savedBarn);
    }

    public List<BarnResponse> findAll() {
        List<Barn> barns = barnRepository.findAll();
        return barns.stream().map(BarnResponse::of)
                .collect(Collectors.toList());
    }

    public BarnResponse findByCodeId(String codeId) {
        Barn barn = barnRepository.findByCodeId(codeId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, codeId));
        return BarnResponse.of(barn);
    }

    public void delete(Barn barn) {
        barnRepository.delete(barn);
    }

}
