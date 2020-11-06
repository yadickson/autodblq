/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sqlquery;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBaseBean;
import com.github.yadickson.autodblq.db.table.base.model.TableBaseBean;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;
import com.github.yadickson.autodblq.db.table.definitions.model.TableColumnBean;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyBean;
import com.github.yadickson.autodblq.db.table.constraint.increments.model.TableIncrementBean;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndexBean;
import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKeyBean;
import com.github.yadickson.autodblq.db.table.constraint.uniques.model.TableUniqueBean;
import com.github.yadickson.autodblq.db.view.base.model.ViewBaseBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class SqlExecuteToGetListFactory implements Function<DataBaseGeneratorType, SqlExecuteToGetList> {

    @Override
    public SqlExecuteToGetList apply(final DataBaseGeneratorType type) {

        switch (type) {
            case TABLE_BASE:
                return new SqlExecuteToGetList<>(TableBaseBean.class);
            case TABLE_DEFINITION:
                return new SqlExecuteToGetList<>(TableColumnBean.class);
            case TABLE_PRIMARY_KEYS:
                return new SqlExecuteToGetList<>(TablePrimaryKeyBean.class);
            case TABLE_FOREIGN_KEYS:
                return new SqlExecuteToGetList<>(TableForeignKeyBean.class);
            case TABLE_UNIQUES:
                return new SqlExecuteToGetList<>(TableUniqueBean.class);
            case TABLE_INDEXES:
                return new SqlExecuteToGetList<>(TableIndexBean.class);
            case TABLE_DEFAULTS:
                return new SqlExecuteToGetList<>(TableDefaultBean.class);
            case TABLE_INCREMENTS:
                return new SqlExecuteToGetList<>(TableIncrementBean.class);
            case VIEW_DEFINITION:
                return new SqlExecuteToGetList<>(ViewBaseBean.class);
            case FUNCTION_DEFINITION:
                return new SqlExecuteToGetList<>(FunctionBaseBean.class);
        }

        throw new SqlExecuteToGetListNotSupportException("Data base generator type query [" + type + "] not supported");
    }

}
