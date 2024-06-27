package myproject.mockjang.domain.records.mockjang.pen;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PenRecordTest extends IntegrationTestSupport {

    @DisplayName("단일 메모를 입력하면 축사칸의 메모 리스트에 저장된다.")
    @Test
    void writeOneRecord() {
        //given
        Barn barn = Barn.builder().codeId(BARN_CODE_ID_1).build();
        Pen pen = Pen.builder().codeId(PEN_CODE_ID_1).barn(barn).build();
        PenRecord penRecord1 = PenRecord.createRecord(pen, RecordType.DAILY, TEMP_DATE);
        PenRecord penRecord2 = PenRecord.createRecord(pen, RecordType.DAILY, TEMP_DATE);

        //when
        penRecord1.writeNote("test1");
        penRecord2.writeNote("test2");
        List<PenRecord> records = pen.getRecords();

        //then
        assertThat(records).hasSize(2);
        assertThat(records).contains(penRecord1, penRecord2);
    }

}