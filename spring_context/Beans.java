//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.28 at 02:35:16 PM CST 
//


package spring_context;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="bean" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="property" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="abstract" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="autowire" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="dependency-check" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="lazy-init" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="singleton" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="default-autowire" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="default-dependency-check" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="default-lazy-init" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bean"
})
@XmlRootElement(name = "beans")
public class Beans {

    protected List<Beans.Bean> bean;
    @XmlAttribute(name = "default-autowire")
    protected String defaultAutowire;
    @XmlAttribute(name = "default-dependency-check")
    protected String defaultDependencyCheck;
    @XmlAttribute(name = "default-lazy-init")
    protected String defaultLazyInit;

    /**
     * Gets the value of the bean property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bean property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBean().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Beans.Bean }
     * 
     * 
     */
    public List<Beans.Bean> getBean() {
        if (bean == null) {
            bean = new ArrayList<Beans.Bean>();
        }
        return this.bean;
    }

    /**
     * Gets the value of the defaultAutowire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultAutowire() {
        return defaultAutowire;
    }

    /**
     * Sets the value of the defaultAutowire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultAutowire(String value) {
        this.defaultAutowire = value;
    }

    /**
     * Gets the value of the defaultDependencyCheck property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultDependencyCheck() {
        return defaultDependencyCheck;
    }

    /**
     * Sets the value of the defaultDependencyCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultDependencyCheck(String value) {
        this.defaultDependencyCheck = value;
    }

    /**
     * Gets the value of the defaultLazyInit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultLazyInit() {
        return defaultLazyInit;
    }

    /**
     * Sets the value of the defaultLazyInit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultLazyInit(String value) {
        this.defaultLazyInit = value;
    }


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
     *         &lt;element name="property" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="abstract" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="autowire" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="dependency-check" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="lazy-init" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="singleton" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "property"
    })
    public static class Bean {

        protected List<Beans.Bean.Property> property;
        @XmlAttribute(name = "abstract")
        protected String _abstract;
        @XmlAttribute(name = "autowire")
        protected String autowire;
        @XmlAttribute(name = "class")
        protected String clazz;
        @XmlAttribute(name = "dependency-check")
        protected String dependencyCheck;
        @XmlAttribute(name = "id")
        protected String id;
        @XmlAttribute(name = "lazy-init")
        protected String lazyInit;
        @XmlAttribute(name = "singleton")
        protected String singleton;

        /**
         * Gets the value of the property property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the property property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProperty().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Beans.Bean.Property }
         * 
         * 
         */
        public List<Beans.Bean.Property> getProperty() {
            if (property == null) {
                property = new ArrayList<Beans.Bean.Property>();
            }
            return this.property;
        }

        /**
         * Gets the value of the abstract property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAbstract() {
            return _abstract;
        }

        /**
         * Sets the value of the abstract property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAbstract(String value) {
            this._abstract = value;
        }

        /**
         * Gets the value of the autowire property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAutowire() {
            return autowire;
        }

        /**
         * Sets the value of the autowire property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAutowire(String value) {
            this.autowire = value;
        }

        /**
         * Gets the value of the clazz property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getClazz() {
            return clazz;
        }

        /**
         * Sets the value of the clazz property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setClazz(String value) {
            this.clazz = value;
        }

        /**
         * Gets the value of the dependencyCheck property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDependencyCheck() {
            return dependencyCheck;
        }

        /**
         * Sets the value of the dependencyCheck property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDependencyCheck(String value) {
            this.dependencyCheck = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the lazyInit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLazyInit() {
            return lazyInit;
        }

        /**
         * Sets the value of the lazyInit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLazyInit(String value) {
            this.lazyInit = value;
        }

        /**
         * Gets the value of the singleton property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSingleton() {
            return singleton;
        }

        /**
         * Sets the value of the singleton property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSingleton(String value) {
            this.singleton = value;
        }


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
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Property {

            @XmlElement(required = true)
            protected String value;
            @XmlAttribute(name = "name")
            protected String name;

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

        }

    }

}
