package commands.write;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class WriteFactory extends ParserFactory {
    Map<String, WriteParser> optionsList;

    public WriteFactory(Map<String, WriteParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }

}
