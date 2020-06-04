package com.github.nl4.owl.cards.util

import com.github.nl4.owl.cards.domain.AccessRoleInfo
import com.github.nl4.owl.cards.domain.PersonInfo
import com.github.nl4.owl.common.data.Gender
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate
import java.time.OffsetDateTime

class CardUtilTest extends Specification {

    @Shared
    def rick

    @Shared
    def role

    def setup() {
        rick = PersonInfo.builder()
                .personId(UUID.fromString("9f0011f5-72d6-4275-8555-15e350362828"))
                .personName("Rick Sanchez")
                .personDetails(CardUtil.composePersonDetails(Gender.MALE, LocalDate.now().minusYears(70), null))
                .build()
        role = AccessRoleInfo.builder()
                .accessRoleId(UUID.fromString("373ea031-93da-46f0-b2d1-ebf6f851ddd7"))
                .locationId(UUID.fromString("5dda0159-ed5c-44d4-b5f7-efb68ffbe8f8"))
                .locationName("Gazorpazorp")
                .expiration(OffsetDateTime.now().plusYears(30))
                .build()
    }

    def "Barcode generated correctly when all required values are present"() {
        given:
            def role2 = AccessRoleInfo.builder()
                    .accessRoleId(UUID.fromString("f4cf559f-21bf-4818-82d3-3298fe482dc0"))
                    .locationId(UUID.fromString("584c3365-bb4a-4a78-a2d6-d9b6d256dc19"))
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