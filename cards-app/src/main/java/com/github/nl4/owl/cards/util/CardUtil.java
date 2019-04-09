package com.github.nl4.owl.cards.util;

import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.PersonInfo;

import java.util.Base64;
import java.util.Set;
import java.util.StringJoiner;

public class CardUtil {

    public static String generateBarcode(PersonInfo personInfo, Set<AccessRoleInfo> accessRoles) {
        if (personInfo == null || accessRoles == null || accessRoles.isEmpty()) {
            return Base64.getEncoder().encodeToString("0".getBytes());
        }
        StringJoiner string = new StringJoiner(":")
                .add(personInfo.getPersonId())
                .add(String.valueOf(accessRoles.size()));
        for (AccessRoleInfo accessRole : accessRoles) {
            string.add(String.valueOf(accessRole.getAccessRoleId()))
                    .add(String.valueOf(accessRole.getLocationId()));
        }
        return Base64.getEncoder().encodeToString(string.toString().getBytes());
    }

}