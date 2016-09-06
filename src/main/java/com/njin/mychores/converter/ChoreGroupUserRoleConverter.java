/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.converter;

import com.njin.mychores.model.ChoreGroupUserRole;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class ChoreGroupUserRoleConverter implements AttributeConverter<ChoreGroupUserRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ChoreGroupUserRole x) {
        switch(x) {
            case ADMIN:
                return 1;
            case OWNER:
                return 2;
            case MEMBER:
                return 3;                                
        }
        return null;
    }

    @Override
    public ChoreGroupUserRole convertToEntityAttribute(Integer y) {
        switch(y) {
            case 1:
                return ChoreGroupUserRole.ADMIN;
            case 2:
                return ChoreGroupUserRole.OWNER;
            case 3:
                return ChoreGroupUserRole.MEMBER;
        }
        return null;
    }    
}
