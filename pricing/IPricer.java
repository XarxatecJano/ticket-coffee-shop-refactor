package pricing;

import domain.Size;

public interface IPricer {
    double calculateBasePrice(Size size);
}