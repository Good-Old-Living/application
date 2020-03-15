package app;
public class Application_EComEntityClassRegistry extends meru.app.registry.EntityClassRegistry {
    protected void populateClassMap() {
        super.populateClassMap();
        addResourceClass("Client", app.domain.Client.class);
        addResourceClass("EntityImage", app.domain.image.EntityImage.class);
        addResourceClass("CityArea", app.domain.location.CityArea.class);
        addResourceClass("Country", app.domain.location.Country.class);
        addResourceClass("HousingComplex", app.domain.location.HousingComplex.class);
        addResourceClass("City", app.domain.location.City.class);
        addResourceClass("HousingComplexAddress", app.domain.location.HousingComplexAddress.class);
        addResourceClass("State", app.domain.location.State.class);
        addResourceClass("Address", app.domain.location.Address.class);
        addResourceClass("SequenceId", app.domain.SequenceId.class);
        addResourceClass("BusinessApplication", app.domain.BusinessApplication.class);
        addResourceClass("ScheduleTrigger", app.domain.schedule.ScheduleTrigger.class);
        addResourceClass("Schedule", app.domain.schedule.Schedule.class);
        addResourceClass("AppEntityState", app.domain.AppEntityState.class);
        addResourceClass("EntityFeatureValue", app.domain.EntityFeatureValue.class);
        addResourceClass("EntityFeature", app.domain.EntityFeature.class);
        addResourceClass("RegisteredNewUser", app.domain.account.RegisteredNewUser.class);
        addResourceClass("Property", app.domain.Property.class);
        addResourceClass("AppHierarchicalEntity", app.domain.AppHierarchicalEntity.class);
        addResourceClass("PropertyGroup", app.domain.PropertyGroup.class);
        addResourceClass("AuditableEntity", app.domain.AuditableEntity.class);
        addResourceClass("AppEntitySequence", app.domain.AppEntitySequence.class);
        addResourceClass("AppEntity", app.domain.AppEntity.class);
        addResourceClass("SendSMS", app.domain.comm.SendSMS.class);
        addResourceClass("Alert", app.domain.comm.Alert.class);
        addResourceClass("SendEmail", app.domain.comm.SendEmail.class);
        addResourceClass("Email", app.domain.comm.Email.class);
        addResourceClass("AppHierarchicalBaseEntity", app.domain.AppHierarchicalBaseEntity.class);
        addResourceClass("SalesOrderLineItem", app.erp.sales.SalesOrderLineItem.class);
        addResourceClass("SalesInvoice", app.erp.sales.SalesInvoice.class);
        addResourceClass("SalesOrder", app.erp.sales.SalesOrder.class);
        addResourceClass("ProductNotification", app.ecom.shopping.ProductNotification.class);
        addResourceClass("ShoppingCart", app.ecom.shopping.cart.ShoppingCart.class);
        addResourceClass("ShoppingCartLineItem", app.ecom.shopping.cart.ShoppingCartLineItem.class);
        addResourceClass("Customer", app.erp.mdm.bp.Customer.class);
        addResourceClass("BusinessPartnerGroup", app.erp.mdm.bp.BusinessPartnerGroup.class);
        addResourceClass("CustomerAddress", app.erp.mdm.bp.CustomerAddress.class);
        addResourceClass("BusinessPartner", app.erp.mdm.bp.BusinessPartner.class);
        addResourceClass("CustomerGroup", app.erp.mdm.bp.CustomerGroup.class);
        addResourceClass("ProductCategoryFeature", app.erp.mdm.catalog.ProductCategoryFeature.class);
        addResourceClass("VegetablePriceList", app.erp.mdm.catalog.VegetablePriceList.class);
        addResourceClass("ProductLineItemImage", app.erp.mdm.catalog.ProductLineItemImage.class);
        addResourceClass("ProductLineItem", app.erp.mdm.catalog.ProductLineItem.class);
        addResourceClass("ProductCategory", app.erp.mdm.catalog.ProductCategory.class);
        addResourceClass("Product", app.erp.mdm.catalog.Product.class);
        addResourceClass("ProductFeature", app.erp.mdm.catalog.ProductFeature.class);
        addResourceClass("ProductFeatureValue", app.erp.mdm.catalog.ProductFeatureValue.class);
        addResourceClass("ProductImage", app.erp.mdm.catalog.ProductImage.class);
        addResourceClass("Role", app.domain.security.Role.class);
        addResourceClass("ResourcePermission", app.domain.security.ResourcePermission.class);
        addResourceClass("ResourceSecurity", app.domain.security.ResourceSecurity.class);
        addResourceClass("NewUser", app.domain.security.NewUser.class);
        addResourceClass("UserRole", app.domain.security.UserRole.class);
        addResourceClass("User", app.domain.security.User.class);
        addResourceClass("EnterpriseAddress", app.erp.EnterpriseAddress.class);
        addResourceClass("Enterprise", app.erp.Enterprise.class);
        addResourceClass("SalesOrderComplimentaryItem", app.erp.marketing.SalesOrderComplimentaryItem.class);
        addResourceClass("SalesOffer", app.erp.marketing.SalesOffer.class);
        addResourceClass("SubscriptionDeviation", app.erp.subs.SubscriptionDeviation.class);
        addResourceClass("SubscriptionLineItem", app.erp.subs.SubscriptionLineItem.class);
        addResourceClass("Subscription", app.erp.subs.Subscription.class);
        addResourceClass("PaymentMethod", app.erp.finance.PaymentMethod.class);
        addResourceClass("Tax", app.erp.finance.Tax.class);
        addResourceClass("PurchaseOrderLineItem", app.erp.purchase.PurchaseOrderLineItem.class);
        addResourceClass("PurchaseOrder", app.erp.purchase.PurchaseOrder.class);
    }
}
