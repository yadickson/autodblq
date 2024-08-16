/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.property;

import com.github.yadickson.autodblq.db.property.model.TablePropertyType;
import com.github.yadickson.autodblq.db.support.Support;

/**
 *
 * @author Yadickson Soto
 */
public interface DataBaseProperties extends Support {
    TablePropertyType get(DataBaseProperty column);
    TablePropertyType getPreInModeFunction();
    TablePropertyType getPreOutModeFunction();
    TablePropertyType getPreInOutModeFunction();
    TablePropertyType getPostInModeFunction();
    TablePropertyType getPostOutModeFunction();
    TablePropertyType getPostInOutModeFunction();
    TablePropertyType getPreValueFunction();
    TablePropertyType getInitFunction();
    TablePropertyType getEndFunction();
}
