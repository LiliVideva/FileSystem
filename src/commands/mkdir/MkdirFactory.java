package commands.mkdir;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class MkdirFactory extends ParserFactory {
    Map<String, MkdirParser> optionsList;

    public MkdirFactory(Map<String, MkdirParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }

}
