package com;

public class MopedDataPair {

    private final MopedDataType type;
    private final Integer value;

    public MopedDataPair(MopedDataType type, Integer value) {
        this.type = type;
        this.value = value;
    }

    public MopedDataType getType() { return type; }
    public Integer getValue() { return value; }

    @Override
    public int hashCode() { return type.hashCode() ^ value.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MopedDataPair)) return false;
        MopedDataPair pairo = (MopedDataPair) o;
        return this.type.equals(pairo.getType()) &&
                this.value.equals(pairo.getValue());
    }

}