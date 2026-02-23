package extras;

public class ExtraPrice {
    public static IExtraPricer createExtra(String extra){
        switch(extra){
            case "milk" : return new MilkExtra();
            case "shot" : return new ShotExtra();
            case "syrup" : return new SyrupExtra();
            default : throw new IllegalArgumentException("Unknown extra: " + extra);
        }
    }
}