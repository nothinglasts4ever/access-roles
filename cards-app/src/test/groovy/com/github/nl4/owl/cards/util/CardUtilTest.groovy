package com.github.nl4.owl.cards.util

import com.github.nl4.owl.cards.domain.AccessRoleInfo
import com.github.nl4.owl.cards.domain.PersonInfo
import spock.lang.Shared
import spock.lang.Specification

import java.time.OffsetDateTime

class CardUtilTest extends Specification {

    @Shared
    def rick

    @Shared
    def role

    def setup() {
        rick = PersonInfo.builder()
                .personId("1")
                .personName("Rick Sanchez")
                .personDetails("Gender: M, Age: 70")
                .build()
        role = AccessRoleInfo.builder()
                .accessRoleId(10L)
                .locationId(1L)
                .locationName("Gazorpazorp")
                .expiration(OffsetDateTime.now().plusYears(30))
                .build()
    }

    def "Barcode generated correctly when all required values are present"() {
        given:
            def role2 = AccessRoleInfo.builder()
                    .accessRoleId(11L)
                    .locationId(2L)
                    .locationName("Cronenberg World")
                    .expiration(OffsetDateTime.now().plusYears(10))
                    .build()
        when:
            def barcode = CardUtil.generateBarcode(rick, [role, role2].toSet())
        then:
            barcode == "MToyOjEwOjE6MTE6Mg=="                                  || "MToyOjExOjI6MTA6MQ=="
            new String(Base64.getDecoder().decode(barcode)) == "1:2:10:1:11:2" || "1:2:11:2:10:1"
    }

    def "Barcode is 0 when not all required values are present"() {
        expect:
            CardUtil.generateBarcode(person, roles) == barcode
        where:
            person | roles          | barcode
            null   | [role].toSet() | "MA=="
            rick   | null           | "MA=="
            rick   | [].toSet()     | "MA=="
    }

}