package commands.cd;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class CdFactory extends ParserFactory {
    Map<String, CdParser> optionsList;

    public CdFactory(Map<String, CdParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }
}
