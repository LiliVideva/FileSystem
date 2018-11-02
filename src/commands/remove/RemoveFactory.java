package commands.remove;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class RemoveFactory extends ParserFactory {
    Map<String, RemoveParser> optionsList;

    public RemoveFactory(Map<String, RemoveParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }

}
