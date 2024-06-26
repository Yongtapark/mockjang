package myproject.mockjang.domain.mockjang.pen;

import static myproject.mockjang.exception.Exceptions.COMMON_ALREADY_EXIST;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UNDER_GROUP;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UPPER_GROUP;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.records.mockjang.pen.PenRecord;
import myproject.mockjang.exception.common.AlreadyExistException;
import myproject.mockjang.exception.common.ThereIsNoGroupException;
import myproject.mockjang.exception.common.UpperGroupAlreadyExistException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE pen SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@EntityListeners(AuditingEntityListener.class)
public class Pen implements Mockjang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Barn barn;

    @OneToMany(mappedBy = "pen")
    private List<Cow> cows = new ArrayList<>();

    @OneToMany(mappedBy = "pen")
    private List<PenRecord> records = new ArrayList<>();

    private final boolean deleted = false;

    @Builder
    private Pen(String codeId, Barn barn, List<Cow> cows, List<PenRecord> records) {
        this.codeId = codeId;
        this.barn = barn;
        if (cows != null) {
            this.cows = cows;
        }
        if (records != null) {
            this.records = records;
        }
    }

    public static Pen createPen(String penCode) {
        return Pen.builder()
                .codeId(penCode)
                .build();
    }

    public void registerRecord(PenRecord record) {
        if(!records.contains(record)){
            records.add(record);
        }
    }

    public void removeRecord(PenRecord record){
        records.remove(record);
    }

    @Override
    public void registerUpperGroup(Mockjang mockjang) {
        if (mockjang instanceof Barn barn) {
            if (this.barn != null) {
                throw new UpperGroupAlreadyExistException(barn, COMMON_ALREADY_EXIST);
            }
            barn.addPen(this);
            this.barn = barn;
        }
    }

    @Override
    public void changeUpperGroup(Mockjang mockjang) {
        if (mockjang instanceof Barn barn) {
            this.barn.removeOneOfUnderGroups(this);
            this.barn = null;
            registerUpperGroup(barn);
        }
    }

    public void registerDailyRecord(PenRecord record) {
        if (!records.contains(record)) {
            records.add(record);
        }
    }

    public void updateCodeId(String codeId){
        this.codeId = codeId;
    }

    @Override
    public Mockjang getUpperGroup() {
        if (barn == null) {
            throw new ThereIsNoGroupException(COMMON_NO_UPPER_GROUP, this);
        }
        return barn;
    }

    @Override
    public void removeOneOfUnderGroups(Mockjang mockjang) {
        if (!cows.remove(mockjang)) {
            throw new ThereIsNoGroupException(COMMON_NO_UNDER_GROUP, this);
        }
    }

    public void addCow(Cow cow) {
        if (cows.contains(cow)) {
            throw new AlreadyExistException(COMMON_ALREADY_EXIST.formatMessage(cow.getCodeId()));
        }
        cows.add(cow);
    }
}
