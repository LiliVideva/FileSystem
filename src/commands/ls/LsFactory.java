package commands.ls;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class LsFactory extends ParserFactory {
    Map<String, LsParser> optionsList;

    public LsFactory(Map<String, LsParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }

}
