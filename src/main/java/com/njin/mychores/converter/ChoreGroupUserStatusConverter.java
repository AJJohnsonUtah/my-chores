/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.converter;

import com.njin.mychores.model.ChoreGroupUserStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class ChoreGroupUserStatusConverter implements AttributeConverter<ChoreGroupUserStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ChoreGroupUserStatus x) {
        switch(x) {
            case ACCEPTED:
                return 1;
            case DECLINED:
                return 2;
            case PENDING:
                return 3;
            case REMOVED:
                return 4;
        }
        return null;
    }

    @Override
    public ChoreGroupUserStatus convertToEntityAttribute(Integer y) {
        switch(y) {
            case 1:
                return ChoreGroupUserStatus.ACCEPTED;
            case 2:
                return ChoreGroupUserStatus.DECLINED;
            case 3:
                return ChoreGroupUserStatus.PENDING;
            case 4:
                return ChoreGroupUserStatus.REMOVED;                
        }
        return null;
    }    
}
