package commands.cat;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class CatFactory extends ParserFactory {
    Map<String, CatParser> optionsList;

    public CatFactory(Map<String, CatParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }
}
