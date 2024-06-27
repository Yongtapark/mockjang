package myproject.mockjang.api.service.mockjang.barn;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.api.service.mockjang.barn.request.BarnCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.barn.request.BarnUpdateServiceRequest;
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

    public Long create(BarnCreateServiceRequest request) {
        String barnCode = request.getCodeId();
        codeIdFilter(barnCode);
        Barn barn = Barn.createBarn(barnCode);
        return barnRepository.save(barn).getId();
    }

    public void update(BarnUpdateServiceRequest request) {
        Barn barn = findById(request.getId());
        barn.updateCodeId(request.getCodeId());
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

    public void remove(Long id) {
        Barn barn = findById(id);
        barnRepository.delete(barn);
    }

    private Barn findById(Long id) {
        return barnRepository.findById(id)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST.formatMessage(Barn.class)));
    }

}
