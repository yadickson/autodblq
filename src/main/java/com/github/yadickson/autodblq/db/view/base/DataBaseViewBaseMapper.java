/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.util.StringToContentUtil;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.util.StringTrimUtil;
import com.github.yadickson.autodblq.db.view.base.model.ViewBase;
import com.github.yadickson.autodblq.db.view.base.model.ViewBaseBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseViewBaseMapper implements Function<List<ViewBaseBean>, List<ViewBase>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseViewBaseMapper.class);

    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToContentUtil stringToContentUtil;

    @Inject
    public DataBaseViewBaseMapper(final StringToLowerCaseUtil stringToLowerCaseUtil, StringToSnakeCaseUtil stringToSnakeCaseUtil, StringToContentUtil stringToContentUtil) {
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringToContentUtil = stringToContentUtil;
    }

    @Override
    public List<ViewBase> apply(final List<ViewBaseBean> viewBeans) {
        final List<ViewBase> views = new ArrayList<>();

        for (ViewBaseBean viewBean : viewBeans) {
            ViewBase view = processView(viewBean);
            views.add(view);
        }

        return views;
    }

    private ViewBase processView(final ViewBaseBean viewBean) {
        final String schema = stringToLowerCaseUtil.apply(viewBean.getSchema());
        final String name = stringToLowerCaseUtil.apply(viewBean.getName());
        final String content = stringToContentUtil.apply(viewBean.getContent());
        final String newSchema = stringToSnakeCaseUtil.apply(viewBean.getSchema());
        final String newName = stringToSnakeCaseUtil.apply(viewBean.getName());

        LOGGER.debug("[DataBaseViewBaseMapper] Schema: " + schema);
        LOGGER.debug("[DataBaseViewBaseMapper] Name: " + name);
        LOGGER.debug("[DataBaseViewBaseMapper] Content: " + content);

        return new ViewBase(schema, name, content, newSchema, newName);
    }

}
