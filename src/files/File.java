package files;

import java.util.ArrayList;
import java.util.List;

class File extends Sizeable {
    private String name;
    private List<String> content;

    File() {
        super();
        content = new ArrayList<>();
    }

    File(List<String> content) {
        this.content = content;
        calculateSize();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        File other = (File) obj;

        return (((content == null && other.content == null) || content.equals(other.content))
                && ((name == null && other.name == null) || name.equals(other.name)) && size != other.size);
    }

    void reset() {
        content = new ArrayList<>();
        size = 0;
    }

    List<String> read() {
        return content;
    }

    int write(boolean toOverwrite, Line line) {
        int index = content.size();
        int lineNumber = line.getNumber();
        int oldSize = size;

        if (index < lineNumber) {
            for (int i = index; i < lineNumber; i++) {
                content.add(i, "");
            }
        }

        content.set(lineNumber - 1, (toOverwrite ? "" : content.get(lineNumber - 1)) + line.getText());
        calculateSize();
        return oldSize;
    }

    int delete(int beginLine, int endLine) {
        int oldSize = size;
        int i = beginLine - 1;

        while (i < endLine - 1) {
            content.remove(beginLine - 1);
            i++;
        }
        calculateSize();
        return oldSize;
    }

    int countCharacters() {
        int characters = 0;

        for (int i = 0; i < content.size(); i++) {
            characters += (!(content.get(i).equals("")) ? content.get(i).length() : 0);
        }

        return characters;
    }

    int countWords() {
        int words = 0;

        for (int i = 0; i < content.size(); i++) {
            words += (!(content.get(i).equals("")) ? content.get(i).split(" ").length : 0);
        }

        return words;
    }

    int countLines() {
        return content.size();
    }

    void setName(String name) {
        this.name = name;
    }

    void calculateSize() {
        size = content.size() + countCharacters();
    }
}
