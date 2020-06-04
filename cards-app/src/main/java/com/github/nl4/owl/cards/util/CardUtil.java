package com.github.nl4.owl.cards.util;

import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.PersonInfo;
import com.github.nl4.owl.common.data.Gender;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@UtilityClass
public class CardUtil {

    public String generateBarcode(PersonInfo personInfo, Set<AccessRoleInfo> accessRoles) {
        if (personInfo == null || accessRoles == null || accessRoles.isEmpty()) {
            return Base64.getEncoder().encodeToString("0".getBytes());
        }
        var string = new StringJoiner(":")
                .add(String.valueOf(personInfo.getPersonId()))
                .add(String.valueOf(accessRoles.size()));
        for (AccessRoleInfo accessRole : accessRoles) {
            string.add(String.valueOf(accessRole.getAccessRoleId()))
                    .add(String.valueOf(accessRole.getLocationId()));
        }
        return Base64.getEncoder().encodeToString(string.toString().getBytes());
    }

    public String composePersonName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public String composePersonDetails(Gender gender, LocalDate birthday, Set<String> addresses) {
        String addressesInfo = addresses.stream()
                .collect(Collectors.joining("; ", ", Addresses: [", "]"));

        return "Gender: " + (gender.equals(Gender.MALE) ? "M" : "F") +
                ", Age: " + Period.between(birthday, LocalDate.now()).getYears() +
                (addresses.isEmpty() ? "" : addressesInfo);
    }

}