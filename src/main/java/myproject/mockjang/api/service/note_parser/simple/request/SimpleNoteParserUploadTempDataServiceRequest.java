package myproject.mockjang.api.service.note_parser.simple.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.note_parser.simple.SimpleRecordContainer;

@Getter
@NoArgsConstructor
public class SimpleNoteParserUploadTempDataServiceRequest {
    private SimpleRecordContainer simpleRecordContainer;


    @Builder
    private SimpleNoteParserUploadTempDataServiceRequest(SimpleRecordContainer simpleRecordContainer) {
        this.simpleRecordContainer = simpleRecordContainer;
    }
}
