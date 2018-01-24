/**
 * Chonete
 * Copyright (C) 2018 Carlos Ortiz.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jmpeax.cli.h2;

import com.zaxxer.hikari.HikariDataSource;
import org.jmpeax.cli.api.providers.DataSource;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.shell.table.TableModelBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * H2 Data source implementation.
 */
public class H2DBDataSource implements DataSource {

    /**
     * SQL datasource.
     */
    protected final HikariDataSource dataSource;

    /**
     * Creates H2DataSource.
     * @param sqlDataSource SQL Datasource to get the connections.
     */
    H2DBDataSource(final HikariDataSource sqlDataSource) {
        this.dataSource = sqlDataSource;
    }


    @Override
    public TableModel exec(final String command) {
        try (Connection conn = dataSource.getConnection()){
            final Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            if (stm.execute(command)) {
                final ResultSet rs = stm.getResultSet();
                if (rs != null) {
                    TableModelBuilder builder = new TableModelBuilder();
                    builder.addRow();
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        builder.addValue(rs.getMetaData().getColumnLabel(i + 1));
                    }
                    while (rs.next()){
                        builder.addRow();
                         for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                          builder.addValue(rs.getObject(i+1));
                        }
                    }
                  rs.close();
                    return builder.build();
                }
            } else if (stm.getUpdateCount()>=0){
                TableModelBuilder builder = new TableModelBuilder();
                builder.addRow();
                builder.addValue("Updated");
                builder.addRow().addValue(stm.getUpdateCount());
                return builder.build();
            }
            return null;
        } catch (SQLException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    @Override
    public void close() {
        if (!this.dataSource.isClosed()) {
            this.dataSource.close();
        }
    }
}
