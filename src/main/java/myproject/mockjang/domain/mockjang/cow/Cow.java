package myproject.mockjang.domain.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.COMMON_ALREADY_EXIST;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UNDER_GROUP;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UPPER_GROUP;
import static myproject.mockjang.exception.Exceptions.DOMAIN_ONLY_SLAUGHTERED_ERROR;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import myproject.mockjang.exception.common.ThereIsNoGroupException;
import myproject.mockjang.exception.common.UpperGroupAlreadyExistException;
import myproject.mockjang.exception.cow.CowParentsException;
import myproject.mockjang.exception.cow.CowStatusException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE cow SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@EntityListeners(AuditingEntityListener.class)
public class Cow implements Mockjang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeId;

    private LocalDateTime birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private CowStatus cowStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Barn barn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pen pen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mom_id")
    private Cow mom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dad_id")
    private Cow dad;

    @OneToMany(mappedBy = "mom")
    private final List<Cow> children = new ArrayList<>();

    @OneToMany(mappedBy = "cow")
    private List<FeedConsumption> feedConsumptions = new ArrayList<>();

    @OneToMany(mappedBy = "cow")
    private List<CowRecord> records = new ArrayList<>();

    private Integer unitPrice;

    private boolean deleted = false;

    @Builder
    private Cow(String codeId, LocalDateTime birthDate, Gender gender, Barn barn, Pen pen,
                CowStatus cowStatus,
                List<FeedConsumption> feedConsumptions, List<CowRecord> records, Integer unitPrice) {

        this.codeId = codeId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.barn = barn;
        this.pen = pen;
        this.cowStatus = cowStatus;
        this.unitPrice = unitPrice;
        if (feedConsumptions != null) {
            this.feedConsumptions = feedConsumptions;
        }
        if (records != null) {
            this.records = records;
        }
    }

    public static Cow createCow(String cowCode, Gender gender, CowStatus cowStatus,
                                LocalDateTime birthDate) {
        return Cow.builder()
                .codeId(cowCode)
                .birthDate(birthDate)
                .gender(gender)
                .cowStatus(cowStatus)
                .build();
    }

    public void registerBarn(Barn barn) {
        if (this.barn != null) {
            throw new UpperGroupAlreadyExistException(barn, COMMON_ALREADY_EXIST);
        }
        this.barn = barn;
    }

    private void changeBarn(Barn barn) {
        this.barn = barn;
    }

    @Override
    public void registerUpperGroup(Mockjang upperGroup) {
        if (upperGroup instanceof Pen pen) {
            if (this.pen != null) {
                throw new UpperGroupAlreadyExistException(pen, COMMON_ALREADY_EXIST);
            }
            this.pen = pen;
            pen.addCow(this);
        }
    }

    @Override
    public void changeUpperGroup(Mockjang mockjang) {
        if (mockjang instanceof Pen pen) {
            this.pen.removeOneOfUnderGroups(this);
            this.pen = null;
            changeBarn(pen.getBarn());
            registerUpperGroup(pen);
        }
    }

    public void registerAllChildren(List<Cow> children) {
        for (Cow child : children) {
            child.registerParent(this);
        }
    }

    public void registerParent(Cow parent) {
        if (parent.gender.equals(Gender.FEMALE)) {
            this.mom = parent;
        } else {
            this.dad = parent;
        }
        parent.children.add(this);
    }

    public void removeParent(Cow parent) {
        try {
            if (parent == null) {
                throw new NullPointerException("parent is null");
            }

            if (removeIfParent(parent, "mom")) {
                return;
            } else if (removeIfParent(parent, "dad")) {
                return;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(String.format("classLoader error : %s", e));
        }

    }

    private boolean removeIfParent(Cow parent, String parentType)
            throws NoSuchFieldException, IllegalAccessException {
        Field findField = this.getClass().getDeclaredField(parentType);
        findField.setAccessible(true);
        Cow actualParent = (Cow) findField.get(this);

        if (actualParent == null) {
            return false;
        }
        if (actualParent.equals(parent)) {
            actualParent.removeChild(this);
            findField.set(this, null);
            return true;
        }
        throw new CowParentsException(Exceptions.DOMAIN_PARENTS_ERROR.formatMessage(parent.getCodeId(), actualParent.getCodeId()));
    }

    public void registerRecord(CowRecord record) {
        records.add(record);
    }

    public void registerFeedConsumptions(FeedConsumption feedConsumption) {
        feedConsumptions.add(feedConsumption);
    }

    public void changeCowStatus(CowStatus cowStatus) {
        this.cowStatus = cowStatus;
    }

    public void registerUnitPrice(Integer unitPrice) {
        if (getCowStatus() == null || !getCowStatus().equals(CowStatus.SLAUGHTERED)) {
            throw new CowStatusException(DOMAIN_ONLY_SLAUGHTERED_ERROR);
        }
        this.unitPrice = unitPrice;
    }

    @Override
    public Mockjang getUpperGroup() {
        if (pen == null) {
            throw new ThereIsNoGroupException(COMMON_NO_UPPER_GROUP, this);
        }
        return pen;
    }

    @Override
    public void removeOneOfUnderGroups(Mockjang mockjang) {
        throw new ThereIsNoGroupException(COMMON_NO_UNDER_GROUP, this);
    }

    public void removeChild(Cow child) {
        if (!children.remove(child)) {
            throw new NotExistException(COMMON_NOT_EXIST, child.getCodeId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cow cow = (Cow) o;
        return deleted == cow.deleted && Objects.equals(id, cow.id) && Objects.equals(codeId,
                cow.codeId) && Objects.equals(birthDate, cow.birthDate) && gender == cow.gender
                && cowStatus == cow.cowStatus && Objects.equals(barn, cow.barn) && Objects.equals(pen,
                cow.pen) && Objects.equals(mom, cow.mom) && Objects.equals(dad, cow.dad)
                && Objects.equals(children, cow.children) && Objects.equals(feedConsumptions,
                cow.feedConsumptions) && Objects.equals(records, cow.records) && Objects.equals(
                unitPrice, cow.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeId, birthDate, gender, cowStatus, barn, pen, mom, dad, children, feedConsumptions,
                records, unitPrice, deleted);
    }
}
