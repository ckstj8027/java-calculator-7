package calculator.service;

import calculator.delimiterExtractor.DelimiterExtractor;
import calculator.numberExtractor.NumberExtractor;
import java.util.ArrayList;

public interface Service {


    public String extractDelimiter(DelimiterExtractor extractor, String input);


    public ArrayList<Integer> extractNumbers(String delimiter, NumberExtractor numberExtractor, String input);


    public String checkTypeOfInput(String input);


    public int addAll(ArrayList<Integer> extractNumbers);


}