package com.heavenscode.sungoldsolar.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


/**
 * A Quotation.
 */
@Document(collection = "quotation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "quotation")
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @org.springframework.data.annotation.Id
    private String id;


    public Integer getNumberOfInverters() {
        return numberOfInverters;
    }

    public void setNumberOfInverters(Integer numberOfInverters) {
        this.numberOfInverters = numberOfInverters;
    }

    @Field("index_id")
    private Integer indexId;

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Integer getIndexId() {

        return indexId;
    }

    @Field("client_id")
    private String clientId;

    @Field("address_id")
    private String addressId;

    @Field("created")
    private LocalDate created;

    @Field("system_size")
    private Double systemSize;

    @Field("unit_usage")
    private String unitUsage;

    @Field("units_generates")
    private Double unitsGenerates;

    @Field("panel_brand")
    private String panelBrand;

    @Field("panel_model")
    private String panelModel;

    @Field("numberof_panels")
    private Double numberofPanels;

    @Field("panel_capacity")
    private Double panelCapacity;

    @Field("panel_price")
    private Double panelPrice;


    @Field("inverter_brand")
    private String inverterBrand;
    @Field("inverter_price")
    private double inverterPrice;


    @Field("inverter_model")
    private String inverterModel;

    @Field("inverter_capacity")
    private Double inverterCapacity;

    @Field("number_of_inverters")
    private Integer numberOfInverters;


    //*********************************************************


    @Field("inverter_model2")
    private String inverterModel2;

    @Field("inverter_capacity2")
    private Double inverterCapacity2;

    @Field("number_of_inverters2")
    private Integer numberOfInverters2;


    @Field("area_needed")
    private Double areaNeeded;

    @Field("mounting_structure")
    private String mountingStructure;

    @Field("construction_price")
    private String constructionPrice;

    @Field("profit")
    private String profit;

    @Field("commission")
    private String commission;

    @Field("system_price")
    private Double systemPrice;

    @Field("issued_by")
    private String issuedBy;

    @Field("telephone")
    private String issuersTelephone;

    public String getIssuersTelephone() {
        return issuersTelephone;
    }

    public void setIssuersTelephone(String issuersTelephone) {
        this.issuersTelephone = issuersTelephone;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Double getPanelPrice() {
        return panelPrice;
    }

    public void setPanelPrice(Double panelPrice) {
        this.panelPrice = panelPrice;
    }

    public double getInverterPrice() {
        return inverterPrice;
    }

    public void setInverterPrice(double inverterPrice) {
        this.inverterPrice = inverterPrice;
    }


    public String getInverterModel2() {
        return inverterModel2;
    }

    public void setInverterModel2(String inverterModel2) {
        this.inverterModel2 = inverterModel2;
    }

    public Double getInverterCapacity2() {
        return inverterCapacity2;
    }

    public void setInverterCapacity2(Double inverterCapacity2) {
        this.inverterCapacity2 = inverterCapacity2;
    }

    public Integer getNumberOfInverters2() {
        return numberOfInverters2;
    }

    public void setNumberOfInverters2(Integer numberOfInverters2) {
        this.numberOfInverters2 = numberOfInverters2;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public Quotation clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAddressId() {
        return addressId;
    }

    public Quotation addressId(String addressId) {
        this.addressId = addressId;
        return this;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Quotation created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Double getSystemSize() {
        return systemSize;
    }

    public Quotation systemSize(Double systemSize) {
        this.systemSize = systemSize;
        return this;
    }

    public void setSystemSize(Double systemSize) {
        this.systemSize = systemSize;
    }

    public String getUnitUsage() {
        return unitUsage;
    }

    public Quotation unitUsage(String unitUsage) {
        this.unitUsage = unitUsage;
        return this;
    }

    public void setUnitUsage(String unitUsage) {
        this.unitUsage = unitUsage;
    }

    public Double getUnitsGenerates() {
        return unitsGenerates;
    }

    public Quotation unitsGenerates(Double unitsGenerates) {
        this.unitsGenerates = unitsGenerates;
        return this;
    }

    public void setUnitsGenerates(Double unitsGenerates) {
        this.unitsGenerates = unitsGenerates;
    }

    public String getPanelBrand() {
        return panelBrand;
    }

    public Quotation panelBrand(String panelBrand) {
        this.panelBrand = panelBrand;
        return this;
    }

    public void setPanelBrand(String panelBrand) {
        this.panelBrand = panelBrand;
    }

    public String getPanelModel() {
        return panelModel;
    }

    public Quotation panelModel(String panelModel) {
        this.panelModel = panelModel;
        return this;
    }

    public void setPanelModel(String panelModel) {
        this.panelModel = panelModel;
    }

    public Double getNumberofPanels() {
        return numberofPanels;
    }

    public Quotation numberofPanels(Double numberofPanels) {
        this.numberofPanels = numberofPanels;
        return this;
    }

    public void setNumberofPanels(Double numberofPanels) {
        this.numberofPanels = numberofPanels;
    }

    public Double getPanelCapacity() {
        return panelCapacity;
    }

    public Quotation panelCapacity(Double panelCapacity) {
        this.panelCapacity = panelCapacity;
        return this;
    }

    public void setPanelCapacity(Double panelCapacity) {
        this.panelCapacity = panelCapacity;
    }

    public String getInverterBrand() {
        return inverterBrand;
    }

    public Quotation inverterBrand(String inverterBrand) {
        this.inverterBrand = inverterBrand;
        return this;
    }

    public void setInverterBrand(String inverterBrand) {
        this.inverterBrand = inverterBrand;
    }

    public String getInverterModel() {
        return inverterModel;
    }

    public Quotation inverterModel(String inverterModel) {
        this.inverterModel = inverterModel;
        return this;
    }

    public void setInverterModel(String inverterModel) {
        this.inverterModel = inverterModel;
    }

    public Double getInverterCapacity() {
        return inverterCapacity;
    }

    public Quotation inverterCapacity(Double inverterCapacity) {
        this.inverterCapacity = inverterCapacity;
        return this;
    }

    public void setInverterCapacity(Double inverterCapacity) {
        this.inverterCapacity = inverterCapacity;
    }

    public Double getAreaNeeded() {
        return areaNeeded;
    }

    public Quotation areaNeeded(Double areaNeeded) {
        this.areaNeeded = areaNeeded;
        return this;
    }

    public void setAreaNeeded(Double areaNeeded) {
        this.areaNeeded = areaNeeded;
    }

    public String getMountingStructure() {
        return mountingStructure;
    }

    public Quotation mountingStructure(String mountingStructure) {
        this.mountingStructure = mountingStructure;
        return this;
    }

    public void setMountingStructure(String mountingStructure) {
        this.mountingStructure = mountingStructure;
    }

    public String getConstructionPrice() {
        return constructionPrice;
    }

    public Quotation constructionPrice(String constructionPrice) {
        this.constructionPrice = constructionPrice;
        return this;
    }

    public void setConstructionPrice(String constructionPrice) {
        this.constructionPrice = constructionPrice;
    }

    public String getProfit() {
        return profit;
    }

    public Quotation profit(String profit) {
        this.profit = profit;
        return this;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getCommission() {
        return commission;
    }

    public Quotation commission(String commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public Double getSystemPrice() {
        return systemPrice;
    }

    public Quotation systemPrice(Double systemPrice) {
        this.systemPrice = systemPrice;
        return this;
    }

    public void setSystemPrice(Double systemPrice) {
        this.systemPrice = systemPrice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quotation quotation = (Quotation) o;
        if (quotation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Quotation{" +
            "id=" + getId() +
            "indexId=" + getIndexId() +
            "numberOfInverters=" + getNumberOfInverters() +
            ", clientId='" + getClientId() + "'" +
            ", addressId='" + getAddressId() + "'" +
            ", created='" + getCreated() + "'" +
            ", systemSize=" + getSystemSize() +
            ", unitUsage='" + getUnitUsage() + "'" +
            ", unitsGenerates=" + getUnitsGenerates() +
            ", panelBrand='" + getPanelBrand() + "'" +
            ", panelModel='" + getPanelModel() + "'" +
            ", numberofPanels=" + getNumberofPanels() +
            ", panelCapacity=" + getPanelCapacity() +
            ", panelPrice=" + getPanelPrice() +

            ", inverterBrand='" + getInverterBrand() + "'" +
            ", inverterModel='" + getInverterModel() + "'" +
            ", inverterCapacity=" + getInverterCapacity() +
            ", inverterPrice=" + getInverterPrice() +

            ", inverterModel2='" + getInverterModel2() + "'" +
            ", inverterCapacity2=" + getInverterCapacity2() +
            ",numberOfInverters2=" + getNumberOfInverters2() +


            ", areaNeeded=" + getAreaNeeded() +
            ", mountingStructure='" + getMountingStructure() + "'" +
            ", constructionPrice='" + getConstructionPrice() + "'" +
            ", profit='" + getProfit() + "'" +
            ", commission='" + getCommission() + "'" +
            ", systemPrice=" + getSystemPrice() +

            ", issuedBy=" + getIssuedBy() +
            ", issuerstelephone=" + getIssuersTelephone() +


            "}";
    }
}
