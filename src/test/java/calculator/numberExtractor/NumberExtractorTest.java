package calculator.numberExtractor;

import calculator.delimiterExtractor.CustomDelimiterExtractor;
import calculator.dto.DelimiterDto;
import calculator.dto.NumberDto;
import calculator.validator.Validator;
import calculator.validator.ValidatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberExtractorTest {


    CustomNumberExtractor customNumberExtractor;
    DefaultNumberExtractor defaultNumberExtractor;

    @BeforeEach
    @Test
    void beforeEach() {
        Validator validator = new ValidatorImpl();
        customNumberExtractor = new CustomNumberExtractor(validator);
        defaultNumberExtractor = new DefaultNumberExtractor(validator);
    }


    @Test
    void 기본구분자_숫자추출() {
        NumberDto numberDTO = defaultNumberExtractor.extractNumbers("1,2:3", ",|:");
        ArrayList<Long> extractNumbers = numberDTO.getNumberRepository();
        ArrayList<Long> expectedNumbers = new ArrayList<Long>(List.of(1L, 2L, 3L));
        Assertions.assertEquals(expectedNumbers, extractNumbers);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4", "1:2:3:4", "1,2,3:4"})
    void 기본구분자_숫자추출_여러케이스(String input) {
        NumberDto numberDTO = defaultNumberExtractor.extractNumbers(input, ",|:");
        ArrayList<Long> extractNumbers = numberDTO.getNumberRepository();
        ArrayList<Long> expectedNumbers = new ArrayList<Long>(List.of(1L, 2L, 3L, 4L));
        Assertions.assertEquals(expectedNumbers, extractNumbers);
    }


    @ParameterizedTest
    @ValueSource(strings = {"//;\\n1;2;37", "//;as\\\\\\n1;as\\\\2;as\\\\37"})
    void 커스텀구분자_숫자추출(String input) {
        Validator validator = new ValidatorImpl();
        CustomDelimiterExtractor customDelimiterExtractor = new CustomDelimiterExtractor(validator);
        DelimiterDto delimiterDto = customDelimiterExtractor.extractDelimiter(input);

        String extractDelimiter = delimiterDto.getDelimiter();
        NumberDto numberDTO = customNumberExtractor.extractNumbers(input, extractDelimiter);
        ArrayList<Long> extractNumbers = numberDTO.getNumberRepository();
        ArrayList<Long> expectedNumbers = new ArrayList<Long>(List.of(1L, 2L, 37L));

        Assertions.assertEquals(expectedNumbers, extractNumbers);
    }

}
