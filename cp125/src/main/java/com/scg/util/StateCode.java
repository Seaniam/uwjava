package com.scg.util;

/**
 * U. S. Postal state codes.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/27/15
 */
public enum StateCode {
    AK("Alaska"),
    AL("Alabama"),
    AR("Arkansas"),
    AS("American Samoa"),
    AZ("Arizona"),
    CA("California"),
    CO("Colorado"),
    CT("Connecticut"),
    DC("District of Columbia"),
    DE("Delaware"),
    FL("Florida"),
    FM("Federated States of Micronesia"),
    GA("Georgia"),
    GU("Guam"),
    HI("Hawaii"),
    IA("Iowa"),
    ID("Idaho"),
    IL("Illinois"),
    IN("Indiana"),
    KS("Kansas"),
    KY("Kentucky"),
    LA("Louisiana"),
    MA("Massachusetts"),
    MD("Maryland"),
    ME("Maine"),
    MH("Marshall Islands"),
    MI("MICHIGAN"),
    MN("Minnesota"),
    MO("Missouri"),
    MP("Northern Mariana Islands"),
    MS("Mississippi"),
    MT("Montana"),
    NC("North Carolina"),
    ND("North Dakota"),
    NE("Nebraska"),
    NH("New Hampshire"),
    NJ("New Jersey"),
    NM("New Mexico"),
    NV("NEVADA"),
    NY("New York"),
    OH("Ohio"),
    OK("Oklahoma"),
    OR("Oregon"),
    PA("Pennsylvania"),
    PR("Puerto Rico"),
    PW("Palau"),
    RI("Rhode Island"),
    SC("South Carolina"),
    SD("South Dakota"),
    TN("Tennessee"),
    TX("Texas"),
    UT("Utah"),
    VA("Virginia"),
    VI("Virgin Islands"),
    VT("Vermont"),
    WA("Washington"),
    WI("Wisconsin"),
    WV("West Virginia"),
    WY("Wyoming");

    /** state code **/
    private String stateCode;

     StateCode(String stateCode) {
         this.stateCode = stateCode;
    }


    /**
     * Gets the state code in string format.
     *
     * @return the skill type in string format.
     */
    public String getStateCode() {
        return stateCode;
    }

}
