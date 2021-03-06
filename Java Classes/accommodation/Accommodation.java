//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.29 at 04:26:49 PM CEST 
//


package rs.ac.uns.ftn.tim1.accommodation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import rs.ac.uns.ftn.tim1.user.User;


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
})
@XmlRootElement(name = "Accommodation")
public class Accommodation {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String description;
    protected long id;
    @XmlElement(name = "AccommodationCategory", required = true)
    protected AccommodationCategory accommodationCategory;
    @XmlElement(name = "AccommodationType", required = true)
    protected AccommodationType accommodationType;
    @XmlElement(name = "AdditionalService")
    protected List<AdditionalService> additionalService;
    @XmlElement(name = "Image")
    protected List<Image> image;
    @XmlElement(name = "Location", required = true)
    protected Location location;
    @XmlElement(name = "User", namespace = "http://www.ftn.uns.ac.rs/tim1/user", required = true)
    protected User user;
    @XmlElement(name = "AccommodationUnit")
    protected List<AccommodationUnit> accommodationUnit;

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

}
