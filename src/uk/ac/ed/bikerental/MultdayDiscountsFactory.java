package uk.ac.ed.bikerental;

public class MultdayDiscountsFactory {
        private static PricingPolicy pricingPolicyInstance;

        public static PricingPolicy getPricingPolicy() {
            if (pricingPolicyInstance == null) {
                // Not implemented -- we are only interested in testing using the Mock.
                assert false;
            }
            return pricingPolicyInstance;
        }

        public static void setupMockDeliveryService() {
            // Should only be called in unit tests, not production code.
            pricingPolicyInstance = new MockMultidayDiscountsPolicy();
        }
}
