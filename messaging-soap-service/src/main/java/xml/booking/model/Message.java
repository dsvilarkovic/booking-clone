//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.15 at 09:56:20 PM CEST 
//


package xml.booking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="value">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="280"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/tim1/messagingsoap}User"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", propOrder = {
    "id",
    "value",
    "date",
    "user"
},namespace = "http://www.ftn.uns.ac.rs/tim1/messagingsoap")
//@XmlRootElement(name = "Message")
@Entity
@SequenceGenerator(name="seqMessage", initialValue=100, allocationSize=50)
@EqualsAndHashCode
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMessage")
	@XmlElement(namespace = "http://www.ftn.uns.ac.rs/tim1/messagingsoap")
    protected Long id;
	
    @Column(name = "value")
    @XmlElement(required = true, namespace = "http://www.ftn.uns.ac.rs/tim1/messagingsoap")
    protected String value;
    
    @Column(name = "date")
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/tim1/messagingsoap")
    protected Long date;
    
    @XmlElement(name = "User", required = true, namespace = "http://www.ftn.uns.ac.rs/tim1/messagingsoap")
    @ManyToOne
    protected User user;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDate(Long value) {
        this.date = value;
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

}
