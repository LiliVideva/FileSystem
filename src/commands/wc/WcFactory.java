package commands.wc;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class WcFactory extends ParserFactory {
    Map<String, WcParser> optionsList;

    public WcFactory(Map<String, WcParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }
}
