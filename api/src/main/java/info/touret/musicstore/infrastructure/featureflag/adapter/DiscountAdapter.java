package info.touret.musicstore.infrastructure.featureflag.adapter;

import info.touret.musicstore.domain.model.Instrument;
import info.touret.musicstore.domain.model.Result;
import info.touret.musicstore.domain.model.User;
import info.touret.musicstore.domain.port.DiscountPort;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.MutableContext;
import dev.openfeature.sdk.OpenFeatureAPI;

@ApplicationScoped
public class DiscountAdapter implements DiscountPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountAdapter.class);
    private final OpenFeatureAPI openFeatureAPI;

    public DiscountAdapter(OpenFeatureAPI openFeatureAPI) {
        this.openFeatureAPI = openFeatureAPI;
    }

    @Override
    public Result<Instrument> applyDiscount(Instrument instrument, User user) {
        // For now, let's keep it simple (manually toggle for testing if needed)

        boolean manualDiscount = false; // Toggle to true to test UI
 /*r       var evaluationDetails = this.openFeatureAPI.getClient().getBooleanDetails("discount-enabled", false);
        LOGGER.info(evaluationDetails.toString());
        boolean isDiscountEnabled = evaluationDetails.getValue();
*/
        var evaluationDetails = this.openFeatureAPI.getClient().getBooleanDetails("discount-enabled", false);
        LOGGER.info(evaluationDetails.toString());
        boolean isDiscountEnabled = evaluationDetails.getValue();

        if (isDiscountEnabled) {
            LOGGER.debug("Applying Discount");
            double originalPrice = instrument.price();
            double discountedPrice = originalPrice * 0.9; // 10% discount
            return Result.success(instrument.withDiscount(discountedPrice, originalPrice));
        }

        return Result.success(instrument);
    }
}
