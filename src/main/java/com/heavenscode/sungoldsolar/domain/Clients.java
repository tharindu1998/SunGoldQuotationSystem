package com.heavenscode.sungoldsolar.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.heavenscode.sungoldsolar.domain.enumeration.Designation;

/**
 * A Clients.
 */
@Document(collection = "clients")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "clients")
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("created")
    private LocalDate created;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("designation")
    private Designation designation;

    @Field("nic_number")
    private String nicNumber;

    @Field("tel")
    private String tel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Clients created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getFirstName() {
        return firstName;
    }

    public Clients firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Clients lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Designation getDesignation() {
        return designation;
    }

    public Clients designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public Clients nicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
        return this;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    public String getTel() {
        return tel;
    }

    public Clients tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
        Clients clients = (Clients) o;
        if (clients.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clients.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clients{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", nicNumber='" + getNicNumber() + "'" +
            ", tel='" + getTel() + "'" +
            "}";
    }
}
