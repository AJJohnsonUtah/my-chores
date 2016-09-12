/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.converter;

import com.njin.mychores.model.ChoreStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class ChoreStatusConverter implements AttributeConverter<ChoreStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ChoreStatus x) {
        switch(x) {
            case TODO:
                return 1;
            case IN_PROGRESS:
                return 2;
            case COMPLETED:
                return 3;                                
        }
        return null;
    }

    @Override
    public ChoreStatus convertToEntityAttribute(Integer y) {
        switch(y) {
            case 1:
                return ChoreStatus.TODO;
            case 2:
                return ChoreStatus.IN_PROGRESS;
            case 3:
                return ChoreStatus.COMPLETED;
        }
        return null;
    }    
}
