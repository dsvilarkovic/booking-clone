//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.25 at 02:12:16 PM CEST 
//


package xml.booking.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.EqualsAndHashCode;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="60"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="description">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/tim1/accommodation}AccommodationCategory"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/tim1/accommodation}AccommodationType"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/tim1/accommodation}AdditionalService" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/tim1/accommodation}Image" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/tim1/accommodation}Location"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/tim1/user}User"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/tim1/accommodation}AccommodationUnit" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "description",
    "id",
    "accommodationCategory",
    "accommodationType",
    "additionalService",
    "image",
    "location",
    "user",
    "accommodationUnit"
}, namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
@XmlRootElement(name = "Accommodation")
@Entity
@SequenceGenerator(name="seqLocation", initialValue=100, allocationSize=50)
@EqualsAndHashCode
public class Accommodation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "seqLocation")
	@XmlElement(namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    protected long id;
	
    @XmlElement(required = true,namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @Column
    protected String name;
    
    @XmlElement(namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @Column
    protected String description;
    
    
    @XmlElement(name = "AccommodationCategory", required = true, namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @ManyToOne(fetch = FetchType.EAGER) 
    protected AccommodationCategory accommodationCategory;
    
    
    @XmlElement(name = "AccommodationType", required = true, namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @ManyToOne(fetch = FetchType.EAGER) 
    protected AccommodationType accommodationType;
    
    @XmlElement(name = "AdditionalService",namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @OneToMany(fetch = FetchType.EAGER)    
    protected List<AdditionalService> additionalService;
    
    @XmlElement(name = "Image",namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    protected List<Image> image;
    
    @XmlElement(name = "Location", required = true, namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @OneToOne
    protected Location location;
    
    @XmlElement(name = "User", required = true,namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @ManyToOne
    protected User user;
    
    @XmlElement(name = "AccommodationUnit", namespace="http://www.ftn.uns.ac.rs/tim1/accommodationsoap")
    @OneToMany(fetch = FetchType.EAGER)   
    @Fetch(value = FetchMode.SUBSELECT)
    protected List<AccommodationUnit> accommodationUnit;
    
    @XmlTransient
    @Column(name = "deleted")
 	protected boolean deleted;
     

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the accommodationCategory property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationCategory }
     *     
     */
    public AccommodationCategory getAccommodationCategory() {
        return accommodationCategory;
    }

    /**
     * Sets the value of the accommodationCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationCategory }
     *     
     */
    public void setAccommodationCategory(AccommodationCategory value) {
        this.accommodationCategory = value;
    }

    /**
     * Gets the value of the accommodationType property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationType }
     *     
     */
    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    /**
     * Sets the value of the accommodationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationType }
     *     
     */
    public void setAccommodationType(AccommodationType value) {
        this.accommodationType = value;
    }

    /**
     * Gets the value of the additionalService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalService }
     * 
     * 
     */
    public List<AdditionalService> getAdditionalService() {
        if (additionalService == null) {
            additionalService = new ArrayList<AdditionalService>();
        }
        return this.additionalService;
    }

    /**
     * Gets the value of the image property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the image property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Image }
     * 
     * 
     */
    public List<Image> getImage() {
        if (image == null) {
            image = new ArrayList<Image>();
        }
        return this.image;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setLocation(Location value) {
        this.location = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

    /**
     * Gets the value of the accommodationUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accommodationUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccommodationUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccommodationUnit }
     * 
     * 
     */
    public List<AccommodationUnit> getAccommodationUnit() {
        if (accommodationUnit == null) {
            accommodationUnit = new ArrayList<AccommodationUnit>();
        }
        return this.accommodationUnit;
    }

	public void setImage(List<Image> image) {
		this.image = image;		
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setAdditionalService(List<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public void setAccommodationUnit(List<AccommodationUnit> accommodationUnit) {
		this.accommodationUnit = accommodationUnit;
	}
	

}
