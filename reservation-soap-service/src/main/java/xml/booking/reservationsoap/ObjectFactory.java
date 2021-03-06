//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.15 at 12:14:54 PM CEST 
//


package xml.booking.reservationsoap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import xml.booking.model.Accommodation;
import xml.booking.model.AccommodationCategory;
import xml.booking.model.AccommodationType;
import xml.booking.model.AccommodationUnit;
import xml.booking.model.AdditionalService;
import xml.booking.model.Comment;
import xml.booking.model.Day;
import xml.booking.model.Image;
import xml.booking.model.Location;
import xml.booking.model.Message;
import xml.booking.model.Rating;
import xml.booking.model.Reservation;
import xml.booking.model.User;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.ac.uns.ftn.tim1.reservationsoap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetReservationListRequest_QNAME = new QName("http://www.ftn.uns.ac.rs/tim1/reservationsoap", "getReservationListRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.tim1.reservationsoap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link GetReservationRequest }
     * 
     */
    public GetReservationRequest createGetReservationRequest() {
        return new GetReservationRequest();
    }

    /**
     * Create an instance of {@link GetReservationResponse }
     * 
     */
    public GetReservationResponse createGetReservationResponse() {
        return new GetReservationResponse();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link Rating }
     * 
     */
    public Rating createRating() {
        return new Rating();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link Comment }
     * 
     */
    public Comment createComment() {
        return new Comment();
    }

    /**
     * Create an instance of {@link AccommodationUnit }
     * 
     */
    public AccommodationUnit createAccommodationUnit() {
        return new AccommodationUnit();
    }

    /**
     * Create an instance of {@link Day }
     * 
     */
    public Day createDay() {
        return new Day();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link GetMessagesRequest }
     * 
     */
    public GetMessagesRequest createGetMessagesRequest() {
        return new GetMessagesRequest();
    }

    /**
     * Create an instance of {@link GetMessagesResponse }
     * 
     */
    public GetMessagesResponse createGetMessagesResponse() {
        return new GetMessagesResponse();
    }

    /**
     * Create an instance of {@link CheckinReservationRequest }
     * 
     */
    public CheckinReservationRequest createCheckinReservationRequest() {
        return new CheckinReservationRequest();
    }

    /**
     * Create an instance of {@link Image }
     * 
     */
    public Image createImage() {
        return new Image();
    }

    /**
     * Create an instance of {@link AdditionalService }
     * 
     */
    public AdditionalService createAdditionalService() {
        return new AdditionalService();
    }

    /**
     * Create an instance of {@link Accommodation }
     * 
     */
    public Accommodation createAccommodation() {
        return new Accommodation();
    }

    /**
     * Create an instance of {@link AccommodationCategory }
     * 
     */
    public AccommodationCategory createAccommodationCategory() {
        return new AccommodationCategory();
    }

    /**
     * Create an instance of {@link AccommodationType }
     * 
     */
    public AccommodationType createAccommodationType() {
        return new AccommodationType();
    }

    /**
     * Create an instance of {@link GetReservationListResponse }
     * 
     */
    public GetReservationListResponse createGetReservationListResponse() {
        return new GetReservationListResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/tim1/reservationsoap", name = "getReservationListRequest")
    public JAXBElement<Object> createGetReservationListRequest(Object value) {
        return new JAXBElement<Object>(_GetReservationListRequest_QNAME, Object.class, null, value);
    }

}
