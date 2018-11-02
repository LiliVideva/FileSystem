package commands.rm;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class RmFactory extends ParserFactory {
    Map<String, RmParser> optionsList;

    public RmFactory(Map<String, RmParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }

}
