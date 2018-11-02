package commands.create_file;

import java.util.Map;

import commands.Parser;
import commands.ParserFactory;

public class CreateFileFactory extends ParserFactory {
    Map<String, CreateFileParser> optionsList;

    public CreateFileFactory(Map<String, CreateFileParser> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    protected Parser getOptionsParser(String option) throws NullPointerException {
        return optionsList.get(option);
    }
}
