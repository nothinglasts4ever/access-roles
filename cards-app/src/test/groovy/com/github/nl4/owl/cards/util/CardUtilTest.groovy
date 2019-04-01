package com.github.nl4.owl.cards.util

import com.github.nl4.owl.cards.domain.AccessRoleInfo
import com.github.nl4.owl.cards.domain.PersonInfo
import spock.lang.Specification

import java.time.LocalDateTime

class CardUtilTest extends Specification {

    def "Barcode generated correctly when all required values are present"() {
        given:
            def rick = PersonInfo.builder()
                    .personId("1")
                    .personName("Rick Sanchez")
                    .personDetails("Gender: M, Age: 70")
                    .build()
            def role1 = AccessRoleInfo.builder()
                    .accessRoleId(10L)
                    .locationId(1L)
                    .locationName("Gazorpazorp")
                    .expiration(LocalDateTime.now().plusYears(30))
                    .build()
            def role2 = AccessRoleInfo.builder()
                    .accessRoleId(11L)
                    .locationId(2L)
                    .locationName("Cronenberg World")
                    .expiration(LocalDateTime.now().plusYears(10))
                    .build()
        when:
            def barcode = CardUtil.generateBarcode(rick, [role1, role2].toSet())
        then:
            barcode == "MToyOjEwOjE6MTE6Mg=="
            new String(Base64.getDecoder().decode(barcode)) == "1:2:10:1:11:2"
    }

}