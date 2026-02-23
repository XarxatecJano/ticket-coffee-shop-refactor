package domain;

public enum ProductType {
    COFFEE,
    TEA,
    MUFFIN;

    @Override
    public String toString(){
        return name().toLowerCase();
    }
}