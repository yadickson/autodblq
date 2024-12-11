/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.type.base.model.TypeBase;
import com.github.yadickson.autodblq.db.type.base.model.TypeBaseBean;
import com.github.yadickson.autodblq.util.StringToContentUtil;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTypeBaseMapper implements Function<List<TypeBaseBean>, List<TypeBase>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTypeBaseMapper.class);

    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringTrimUtil stringTrimUtil;
    private final StringToContentUtil stringToContentUtil;

    @Inject
    public DataBaseTypeBaseMapper(final StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringTrimUtil stringTrimUtil, final StringToContentUtil stringToContentUtil) {
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
        this.stringToContentUtil = stringToContentUtil;
    }

    @Override
    public List<TypeBase> apply(final List<TypeBaseBean> typeBeans) {
        final List<TypeBase> types = new ArrayList<>();

        for (TypeBaseBean typeBean : typeBeans) {
            TypeBase type = processType(typeBean);
            types.add(type);
        }

        return types;
    }

    private TypeBase processType(final TypeBaseBean typeBean) {
        final String realSchema = stringTrimUtil.apply(typeBean.getSchema());
        final String realName = stringTrimUtil.apply(typeBean.getName());
        final String schema = stringToLowerCaseUtil.apply(typeBean.getSchema());
        final String name = stringToLowerCaseUtil.apply(typeBean.getName());
        final String newSchema = stringToSnakeCaseUtil.apply(typeBean.getSchema());
        final String newName = stringToSnakeCaseUtil.apply(typeBean.getName());
        final String type = stringTrimUtil.apply(typeBean.getType());
        final String realContent = stringTrimUtil.apply(typeBean.getContent());
        final String newContent = stringToContentUtil.apply(stringToSnakeCaseUtil.apply(typeBean.getContent()));

        LOGGER.debug("[DataBaseTypeBaseMapper] Schema: " + realSchema);
        LOGGER.debug("[DataBaseTypeBaseMapper] Name: " + realName);
        LOGGER.debug("[DataBaseTypeBaseMapper] Type: " + type);
        LOGGER.debug("[DataBaseTypeBaseMapper] Content: " + realContent);

        return new TypeBase(realSchema, realName, schema, name, newSchema, newName, type, realContent, newContent);
    }

}
