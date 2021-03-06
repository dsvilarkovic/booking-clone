package xml.booking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.ColumnDefault;

import xml.booking.dto.CodeBookDTO;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="7"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "name", "deleted" })
@XmlRootElement(name = "AccommodationCategory")
@Entity
@SequenceGenerator(name = "seqAccCat", initialValue = 100, allocationSize = 50)
public class AccommodationCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAccCat")
	protected Long id;

	@Column(name = "name")
	protected String name;

	@Column(name = "deleted")
	@ColumnDefault(value = "false")
	protected boolean deleted;

	public AccommodationCategory() {
	}

	public AccommodationCategory(CodeBookDTO dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.deleted = false;

	}

	/**
	 * Gets the value of the id property.
	 * 
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 */
	public void setId(Long value) {
		this.id = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "AccommodationCategory [id=" + id + ", name=" + name + ", deleted=" + deleted + "]";
	}

}
