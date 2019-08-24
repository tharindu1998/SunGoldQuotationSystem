package com.heavenscode.sungoldsolar.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Inverters.
 */
@Document(collection = "inverters")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "inverters")
public class Inverters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("inverter_brand")
    private String inverterBrand;

    @Field("inverter_model")
    private String inverterModel;

    @Field("inverter_size")
    private Double inverterSize;

    @Field("inverter_price")
    private Double inverterPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInverterBrand() {
        return inverterBrand;
    }

    public Inverters inverterBrand(String inverterBrand) {
        this.inverterBrand = inverterBrand;
        return this;
    }

    public void setInverterBrand(String inverterBrand) {
        this.inverterBrand = inverterBrand;
    }

    public String getInverterModel() {
        return inverterModel;
    }

    public Inverters inverterModel(String inverterModel) {
        this.inverterModel = inverterModel;
        return this;
    }

    public void setInverterModel(String inverterModel) {
        this.inverterModel = inverterModel;
    }

    public Double getInverterSize() {
        return inverterSize;
    }

    public Inverters inverterSize(Double inverterSize) {
        this.inverterSize = inverterSize;
        return this;
    }

    public void setInverterSize(Double inverterSize) {
        this.inverterSize = inverterSize;
    }

    public Double getInverterPrice() {
        return inverterPrice;
    }

    public Inverters inverterPrice(Double inverterPrice) {
        this.inverterPrice = inverterPrice;
        return this;
    }

    public void setInverterPrice(Double inverterPrice) {
        this.inverterPrice = inverterPrice;
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
        Inverters inverters = (Inverters) o;
        if (inverters.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inverters.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inverters{" +
            "id=" + getId() +
            ", inverterBrand='" + getInverterBrand() + "'" +
            ", inverterModel='" + getInverterModel() + "'" +
            ", inverterSize=" + getInverterSize() +
            ", inverterPrice=" + getInverterPrice() +
            "}";
    }
}
