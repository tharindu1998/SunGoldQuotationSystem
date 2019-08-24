package com.heavenscode.sungoldsolar.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Panels.
 */
@Document(collection = "panels")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "panels")
public class Panels implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("palen_brand")
    private String palenBrand;

    @Field("panel_model")
    private String panelModel;

    @Field("panel_size")
    private Double panelSize;

    @Field("panel_price")
    private Double panelPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPalenBrand() {
        return palenBrand;
    }

    public Panels palenBrand(String palenBrand) {
        this.palenBrand = palenBrand;
        return this;
    }

    public void setPalenBrand(String palenBrand) {
        this.palenBrand = palenBrand;
    }

    public String getPanelModel() {
        return panelModel;
    }

    public Panels panelModel(String panelModel) {
        this.panelModel = panelModel;
        return this;
    }

    public void setPanelModel(String panelModel) {
        this.panelModel = panelModel;
    }

    public Double getPanelSize() {
        return panelSize;
    }

    public Panels panelSize(Double panelSize) {
        this.panelSize = panelSize;
        return this;
    }

    public void setPanelSize(Double panelSize) {
        this.panelSize = panelSize;
    }

    public Double getPanelPrice() {
        return panelPrice;
    }

    public Panels panelPrice(Double panelPrice) {
        this.panelPrice = panelPrice;
        return this;
    }

    public void setPanelPrice(Double panelPrice) {
        this.panelPrice = panelPrice;
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
        Panels panels = (Panels) o;
        if (panels.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), panels.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Panels{" +
            "id=" + getId() +
            ", palenBrand='" + getPalenBrand() + "'" +
            ", panelModel='" + getPanelModel() + "'" +
            ", panelSize=" + getPanelSize() +
            ", panelPrice=" + getPanelPrice() +
            "}";
    }
}
