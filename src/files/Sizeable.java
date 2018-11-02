package files;

class Sizeable implements Comparable<Sizeable> {
    protected int size;

    Sizeable() {
        this.size = 0;
    }

    @Override
    public int compareTo(Sizeable o) {
        return (size < o.size) ? -1 : ((size == o.size) ? 0 : 1);
    }

    int getSize() {
        return size;
    }
}
